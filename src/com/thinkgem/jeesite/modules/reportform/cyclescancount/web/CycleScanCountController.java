/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.reportform.cyclescancount.web;

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
import com.thinkgem.jeesite.modules.reportform.cyclescancount.entity.CycleScanCount;
import com.thinkgem.jeesite.modules.reportform.cyclescancount.service.CycleScanCountService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 周期扫码次数统计Controller
 * @author tanghaobo
 * @version 2017-07-06
 */
@Controller
@RequestMapping(value = "${adminPath}/cyclescancount/cycleScanCount")
public class CycleScanCountController extends BaseController {

	@Autowired
	private CycleScanCountService cycleScanCountService;
	
	@Autowired
	private ActivityReportService activityReportService;
	
	@ModelAttribute
	public CycleScanCount get(@RequestParam(required=false) String id) {
		CycleScanCount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cycleScanCountService.get(id);
		}
		if (entity == null){
			entity = new CycleScanCount();
		}
		return entity;
	}
	
	@RequiresPermissions("cyclescancount:cycleScanCount:view")
	@RequestMapping(value = {"list", ""})
	public String list(CycleScanCount cycleScanCount, HttpServletRequest request, HttpServletResponse response, Model model) {
		//Page<CycleScanCount> page = cycleScanCountService.findPage(new Page<CycleScanCount>(request, response), cycleScanCount); 
		String scanNumber = cycleScanCount.getScanNumber();
		System.out.println(scanNumber);
		List<CycleScanCount> prov = cycleScanCountService.findList(cycleScanCount);
		//获得数量
		for(CycleScanCount turn : prov ){
			for(CycleScanCount tp : prov){
				//判断是否属于同一用户
				if(turn.getOpenid().equals(tp.getOpenid())){
					//判断是否经销商省份相同
					if(turn.getProvince().equals(tp.getProvince())){
						if(turn.getCity().equals(tp.getCity())){
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
                if (prov.get(i).getOpenid().equals(prov.get(j).getOpenid())){
                	if(prov.get(i).getProvince().equals(prov.get(j).getProvince())){
                		if(prov.get(i).getCity().equals(prov.get(j).getCity())){
                			prov.remove(j);
                    	}
                	}
                }
            }
        }
        for(int e = 0 ;e<prov.size(); e++){
    		//判断是否查询扫码次数
    		if((cycleScanCount.getScanNumber()!=null)&&(!"".equals(cycleScanCount.getScanNumber()))){
    			//这里是计算了总次数之后的集合  scanNumber是总的
    			int parseInt = Integer.parseInt(prov.get(e).getScanNumber());
    			//这个是查询的次数   
    			int parseInt2 = Integer.parseInt(cycleScanCount.getScanNumber());
    			if(parseInt!=parseInt2){
    				prov.remove(e);
    				e--;
    			}
    		}else{
    			
    		}
        }
        //根据传递过来的扫码的次数 剔除掉不满足条件的扫码记录
        if(scanNumber!=null&&!"".equals(scanNumber)){
        	
        }
        List<CycleScanCount> proList=new ArrayList<>();
        User user = UserUtils.getUser();
        model.addAttribute("userType", user.getUserType());
        if(!user.getId().equals("1")){
        	//这里根据当前登录人来判断他是否有申请活动  
            List<ActivityReport> findListByApplyUser = activityReportService.findListByApplyUser(user.getId());
            for (CycleScanCount c : prov) {
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
		return "modules/reportform/cyclescancount/cycleScanCountList.jsp";
	}

	@RequiresPermissions("cyclescancount:cycleScanCount:view")
	@RequestMapping(value = "export", method = RequestMethod.GET)
	public String exportNum(CycleScanCount cycleScanCount,HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		List<CycleScanCount> prov = cycleScanCountService.findList(cycleScanCount);
		//获得数量
		for(CycleScanCount turn : prov ){
			for(CycleScanCount tp : prov){
				//判断是否属于同一用户
				if(turn.getOpenid().equals(tp.getOpenid())){
					//判断是否经销商省份相同
					if(turn.getProvince().equals(tp.getProvince())){
						if(turn.getCity().equals(tp.getCity())){
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
                if (prov.get(i).getOpenid().equals(prov.get(j).getOpenid())){
                	if(prov.get(i).getProvince().equals(prov.get(j).getProvince())){
                		if(prov.get(i).getCity().equals(prov.get(j).getCity())){
                			prov.remove(j);
                    	}
                	}
                }
            }
        }
        for(int e = 0 ;e<prov.size(); e++){
    		//判断是否查询扫码次数
    		if(cycleScanCount.getScanNumber()!=null){
    			if(Integer.parseInt(prov.get(e).getScanNumber())<Integer.parseInt(cycleScanCount.getScanNumber())){
    				prov.remove(e);
    			}
    		}else{
    			
    		}
        }
		try {
			String fileName = "周期扫码次数统计" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			List<String> headerList = Lists.newArrayList();
			headerList.add("呢称");
			/*headerList.add("电话号码");*/
			headerList.add("省");
			headerList.add("城市");
			headerList.add("扫码次数");
			headerList.add("扫码时间");
			ExportExcel ee = new ExportExcel("周期扫码次数统计记录", headerList);
			for(CycleScanCount si : prov){
				Row row = ee.addRow();
				ee.addCell(row, 0, si.getUserName());
				/*ee.addCell(row, 1, si.getPhone());*/
				ee.addCell(row, 1, si.getProvince());
				ee.addCell(row, 2, si.getCity());
				ee.addCell(row, 3, si.getScanNumber());
				ee.addCell(row, 4, si.getScanTime());
			}
			System.out.println("！！！！！导出列表长度为："+prov.size()+"！！！！！");
			ee.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出信息失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/cyclescancount/cycleScanCountList.jsp";
	}
	
	@RequiresPermissions("cyclescancount:cycleScanCount:view")
	@RequestMapping(value = "form")
	public String form(CycleScanCount cycleScanCount, Model model) {
		model.addAttribute("cycleScanCount", cycleScanCount);
		return "modules/reportform/cyclescancount/cycleScanCountForm.jsp";
	}

	@RequiresPermissions("cyclescancount:cycleScanCount:edit")
	@RequestMapping(value = "save")
	public String save(CycleScanCount cycleScanCount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cycleScanCount)){
			return form(cycleScanCount, model);
		}
		cycleScanCountService.save(cycleScanCount);
		addMessage(redirectAttributes, "保存周期扫码次数统计成功");
		return "redirect:"+Global.getAdminPath()+"/cyclescancount/cycleScanCount/?repage";
	}
	
	@RequiresPermissions("cyclescancount:cycleScanCount:edit")
	@RequestMapping(value = "delete")
	public String delete(CycleScanCount cycleScanCount, RedirectAttributes redirectAttributes) {
		cycleScanCountService.delete(cycleScanCount);
		addMessage(redirectAttributes, "删除周期扫码次数统计成功");
		return "redirect:"+Global.getAdminPath()+"/cyclescancount/cycleScanCount/?repage";
	}

}