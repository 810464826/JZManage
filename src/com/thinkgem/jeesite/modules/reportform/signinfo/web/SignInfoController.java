package com.thinkgem.jeesite.modules.reportform.signinfo.web;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.thinkgem.jeesite.modules.reportform.signinfo.entity.SignInfo;
import com.thinkgem.jeesite.modules.reportform.signinfo.service.SignInfoService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 扫码中奖统计Controller
 * @author tanghaobo
 * @version 2017-06-23
 */
@Controller
@RequestMapping(value = "${adminPath}/signinfo/signInfo")
public class SignInfoController extends BaseController {

	@Autowired
	private SignInfoService signInfoService;
	
	@Autowired
	private ActivityReportService activityReportService;
	@ModelAttribute
	public SignInfo get(@RequestParam(required=false) String id) {
		SignInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = signInfoService.get(id);
		}
		if (entity == null){
			entity = new SignInfo();
		}
		return entity;
	}
	
	//	查询时
	@RequiresPermissions("signinfo:signInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(SignInfo signInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		//Page<SignInfo> page = signInfoService.findPage(new Page<SignInfo>(request, response), signInfo);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		if(startTime != null && endTime != null){
			try {
				signInfo.setStartTime(sdf.parse(startTime));
				signInfo.setEndTime(sdf.parse(endTime));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//先将时间剔除出来 修改一下 时间的格式
		System.err.println(startTime+"----"+endTime);
		List<SignInfo> info1 = signInfoService.findList(signInfo);
		List<SignInfo> info =new ArrayList<>();
		for (SignInfo s : info1) {
			if(s.getActivityId()!=null&&!"".equals(s.getActivityId())){
				info.add(s);
			}
		}
		//获得数量
		for(SignInfo signinfo : info ){
			for(SignInfo si : info){
				//判断是否属于同一活动
				if(signinfo.getActivityId().equals(si.getActivityId())){
					//判断是否相同奖品
					if(signinfo.getPrizeName().equals(si.getPrizeName())){
						if(signinfo.getNumber()==null){
							signinfo.setNumber("1");
						}else{
							int number = Integer.parseInt(signinfo.getNumber())+1;
							signinfo.setNumber(number+"");
						}
					}
				}
			}
		}
		//去重复
        for (int i = 0; i < info.size(); i++){  //外循环是循环的次数
            for (int j = info.size() - 1 ; j > i; j--){	//内循环是 外循环一次比较的次数
                if (info.get(i).getActivityId().equals(info.get(j).getActivityId())){
                	if(info.get(i).getPrizeName().equals(info.get(j).getPrizeName())){
                		info.remove(j);
                	}
                }
            }
        }
        //这里同样需要筛选一下的  根据当前登录用户
        List<SignInfo> proList=new ArrayList<>();
        User user = UserUtils.getUser();
        if(!user.getId().equals("1")){
        	//这里根据当前登录人来判断他是否有申请活动  
            List<ActivityReport> findListByApplyUser = activityReportService.findListByApplyUser(user.getId());
            for (SignInfo c : info) {
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
        	proList=info;
        }
		model.addAttribute("page", proList);
		return "modules/reportform/signinfo/signInfoList.jsp";
	}

	@RequiresPermissions("signinfo:signInfo:view")
	@RequestMapping(value = "form")
	public String form(SignInfo signInfo, Model model) {
		model.addAttribute("signInfo", signInfo);
		return "modules/reportform/signinfo/signInfoForm.jsp";
	}
	
	
	@RequiresPermissions("signinfo:signInfo:view")
	@RequestMapping(value = "export", method = RequestMethod.GET)
	public String exportNum(SignInfo signInfo,HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		List<SignInfo> info = signInfoService.findList(signInfo);
		//获得数量
		for(SignInfo signinfo : info ){
			for(SignInfo si : info){
				//判断是否属于同一活动
				if(signinfo.getActivityId().equals(si.getActivityId())){
					//判断是否相同奖品
					if(signinfo.getPrizeName().equals(si.getPrizeName())){
						if(signinfo.getNumber()==null){
							signinfo.setNumber("1");
						}else{
							int number = Integer.parseInt(signinfo.getNumber())+1;
							signinfo.setNumber(number+"");
						}
					}
				}
			}
		}
		//去重复
        for (int i = 0; i < info.size(); i++){  //外循环是循环的次数
            for (int j = info.size() - 1 ; j > i; j--){	//内循环是 外循环一次比较的次数
                if (info.get(i).getActivityId().equals(info.get(j).getActivityId())){
                	if(info.get(i).getPrizeName().equals(info.get(j).getPrizeName())){
                		info.remove(j);
                	}
                }
            }
        }
		try {
			String fileName = "扫码中奖记录" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			List<String> headerList = Lists.newArrayList();
			headerList.add("活动名称");
			headerList.add("经销商名称");
			headerList.add("区域");
			headerList.add("商品名称");
			headerList.add("统计数量");
			ExportExcel ee = new ExportExcel("扫码中奖记录", headerList);
			for(SignInfo si : info){
				Row row = ee.addRow();
				ee.addCell(row, 0, si.getActivityName());
				ee.addCell(row, 1, si.getUserName());
				ee.addCell(row, 2, si.getActivityAddress());
				ee.addCell(row, 3, si.getPrizeName());
				ee.addCell(row, 4, si.getNumber());
			}
			System.out.println("！！！！！导出列表长度为："+info.size()+"！！！！！");
			ee.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出信息失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/signinfo/signInfoList.jsp";
	}
	
	@RequiresPermissions("signinfo:signInfo:edit")
	@RequestMapping(value = "save")
	public String save(SignInfo signInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, signInfo)){
			return form(signInfo, model);
		}
		signInfoService.save(signInfo);
		addMessage(redirectAttributes, "保存扫码中奖统计成功");
		return "redirect:"+Global.getAdminPath()+"/signinfo/signInfo/?repage";
	}
	
	@RequiresPermissions("signinfo:signInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(SignInfo signInfo, RedirectAttributes redirectAttributes) {
		signInfoService.delete(signInfo);
		addMessage(redirectAttributes, "删除扫码中奖统计成功");
		return "redirect:"+Global.getAdminPath()+"/signinfo/signInfo/?repage";
	}

}