/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.reportform.provincescount.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.activity.activityreport.entity.ActivityReport;
import com.thinkgem.jeesite.modules.activity.activityreport.service.ActivityReportService;
import com.thinkgem.jeesite.modules.reportform.provincescount.entity.LongLatEntity;
import com.thinkgem.jeesite.modules.reportform.provincescount.entity.ProvincesCount;
import com.thinkgem.jeesite.modules.reportform.provincescount.service.ProvincesCountService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 跨省份扫码统计Controller
 * @author tanghaobo
 * @version 2017-07-06
 */
@Controller
@RequestMapping(value = "${adminPath}/provincescount/provincesCount")
public class ProvincesCountController extends BaseController {

	@Autowired
	private ProvincesCountService provincesCountService;
	
	@Autowired
	private ActivityReportService activityReportService;
	
	@ModelAttribute
	public ProvincesCount get(@RequestParam(required=false) String id) {
		ProvincesCount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = provincesCountService.get(id);
		}
		if (entity == null){
			entity = new ProvincesCount();
		}
		return entity;
	}
	
	@RequiresPermissions("provincescount:provincesCount:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProvincesCount provincesCount, HttpServletRequest request, HttpServletResponse response, Model model) {
		//Page<ProvincesCount> page = provincesCountService.findPage(new Page<ProvincesCount>(request, response), provincesCount); 
		List<ProvincesCount> prov = provincesCountService.findList(provincesCount);
		//获得数量
		for(ProvincesCount turn : prov ){
			for(ProvincesCount tp : prov){
				//判断是否属于经销商名称是否相同
				if(turn.getActivityPrizesName().equals(tp.getActivityPrizesName())){
					//判断是否经销商省份相同
					if(turn.getActivityPrizesAddress().equals(tp.getActivityPrizesAddress())){
						if(turn.getProvince().equals(tp.getProvince())){
							if(turn.getScanNumber()==null){
								turn.setScanNumber("1");
							}else{
								int number = Integer.parseInt(turn.getScanNumber())+1;
								turn.setScanNumber(number+"");
							}
							
						}
					}
				}
			}
		}
		//去重复
        for (int i = 0; i < prov.size(); i++){  //外循环是循环的次数
            for (int j = prov.size() - 1 ; j > i; j--){	//内循环是 外循环一次比较的次数
                if (prov.get(i).getActivityPrizesName().equals(prov.get(j).getActivityPrizesName())){
                	if(prov.get(i).getActivityPrizesAddress().equals(prov.get(j).getActivityPrizesAddress())){
                		if(prov.get(i).getProvince().equals(prov.get(j).getProvince())){
                			prov.remove(j);
//                			i--;
                    	}
                	}
                }
            }
        }
        List<ProvincesCount> proList=new ArrayList<>();
        User user = UserUtils.getUser();
        model.addAttribute("userType", user.getUserType());
        if(!user.getId().equals("1")){
        	//这里根据当前登录人来判断他是否有申请活动  
            List<ActivityReport> findListByApplyUser = activityReportService.findListByApplyUser(user.getId());
            for (ProvincesCount c : prov) {
            	for (ActivityReport activityReport : findListByApplyUser) {
            		//这里扫码记录所属的活动
            		String activityId = c.getActivityId();
            		//如果相同的时候就加入
            		if(activityId.equals(activityReport.getId())){
            			proList.add(c);
            		}
				}
    		}
        }else{
        	proList=prov;
        }
		model.addAttribute("page", proList);
		return "modules/reportform/provincescount/provincesCountList.jsp";
	}

	@RequiresPermissions("provincescount:provincesCount:view")
	@RequestMapping(value = "form")
	public String form(ProvincesCount provincesCount, Model model) {
		model.addAttribute("provincesCount", provincesCount);
		return "modules/reportform/provincescount/provincesCountForm.jsp";
	}
	
	//扫码地图显示
	@RequiresPermissions("provincescount:provincesCount:view")
	@RequestMapping(value = "provincesMapIndex")
	public String provincesMapIndex(ProvincesCount provincesCount, Model model) {
		return "modules/reportform/provincescount/longitudeIndex.jsp";
	}
	
	//扫码地图显示
	@RequiresPermissions("provincescount:provincesCount:view")
	@RequestMapping(value = "provincesMap")
	public String provincesMap(ProvincesCount provincesCount, Model model, HttpServletRequest request) {
		User user = UserUtils.getUser();
		//这里根据点击的officeid查询相应的扫码记录
		String officeid = request.getParameter("officeid");
		System.out.println(officeid);
		//如果第一次进来默认是为空的    就是当前登录人的officeid了
		if(officeid==null||"".equals(officeid)){
			officeid=user.getOffice().getId();
		}
		//这里需要查询该officeid所开展的活动
		List<ActivityReport> findList = activityReportService.findListByOfficeId(officeid);
		List<ProvincesCount> prov =new ArrayList<>();
		for (ActivityReport a : findList) {
			provincesCount.setActivityId(a.getId());
			List<ProvincesCount> list = provincesCountService.findList(provincesCount);
			prov.addAll(list);
		}
		List<LongLatEntity> lngList=new ArrayList<LongLatEntity>();
		for (ProvincesCount p : prov) {
			LongLatEntity l=new LongLatEntity();
			l.setLatitude(p.getLatitude());
			l.setLongitude(p.getLongitude());
			lngList.add(l);
		}
		model.addAttribute("lngList", lngList);
		return "modules/reportform/provincescount/longitude.jsp";
	}

	@RequiresPermissions("provincescount:provincesCount:view")
	@RequestMapping(value = "export", method = RequestMethod.GET)
	public String exportNum(ProvincesCount provincesCount,HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		List<ProvincesCount> prov = provincesCountService.findList(provincesCount);
		//获得数量
		for(ProvincesCount turn : prov ){
			for(ProvincesCount tp : prov){
				//判断是否属于经销商名称是否相同
				if(turn.getActivityPrizesName().equals(tp.getActivityPrizesName())){
					//判断是否经销商省份相同
					if(turn.getActivityPrizesAddress().equals(tp.getActivityPrizesAddress())){
						if(turn.getProvince().equals(tp.getProvince())){
							if(turn.getScanNumber()==null){
								turn.setScanNumber("1");
							}else{
								int number = Integer.parseInt(turn.getScanNumber())+1;
								turn.setScanNumber(number+"");
							}
							
						}
					}
				}
			}
		}
		//去重复
        for (int i = 0; i < prov.size(); i++){  //外循环是循环的次数
            for (int j = prov.size() - 1 ; j > i; j--){	//内循环是 外循环一次比较的次数
                if (prov.get(i).getActivityPrizesName().equals(prov.get(j).getActivityPrizesName())){
                	if(prov.get(i).getActivityPrizesAddress().equals(prov.get(j).getActivityPrizesAddress())){
                		if(prov.get(i).getProvince().equals(prov.get(j).getProvince())){
                			prov.remove(j);
                    	}
                	}
                }
            }
        }
		try {
			String fileName = "跨省份扫码统计" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			List<String> headerList = Lists.newArrayList();
			headerList.add("经销商名称");
			headerList.add("经销商省份");
			headerList.add("扫码省份");
			headerList.add("扫码次数");
			ExportExcel ee = new ExportExcel("跨省份扫码统计记录", headerList);
			for(ProvincesCount si : prov){
				Row row = ee.addRow();
				ee.addCell(row, 0, si.getActivityPrizesName());
				ee.addCell(row, 1, si.getActivityPrizesAddress());
				ee.addCell(row, 2, si.getProvince());
				ee.addCell(row, 3, si.getScanNumber());
			}
			System.out.println("！！！！！导出列表长度为："+prov.size()+"！！！！！");
			ee.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出信息失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/provincescount/provincesCountList.jsp";
	}
	
	
	@RequiresPermissions("provincescount:provincesCount:edit")
	@RequestMapping(value = "save")
	public String save(ProvincesCount provincesCount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, provincesCount)){
			return form(provincesCount, model);
		}
		provincesCountService.save(provincesCount);
		addMessage(redirectAttributes, "保存跨省份扫码统计成功");
		return "redirect:"+Global.getAdminPath()+"/provincescount/provincesCount/?repage";
	}
	
	@RequiresPermissions("provincescount:provincesCount:edit")
	@RequestMapping(value = "delete")
	public String delete(ProvincesCount provincesCount, RedirectAttributes redirectAttributes) {
		provincesCountService.delete(provincesCount);
		addMessage(redirectAttributes, "删除跨省份扫码统计成功");
		return "redirect:"+Global.getAdminPath()+"/provincescount/provincesCount/?repage";
	}

}