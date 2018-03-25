/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cancel.remotecancel.web;

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
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.activity.activityreport.entity.ActivityReport;
import com.thinkgem.jeesite.modules.activity.activityreport.service.ActivityReportService;
import com.thinkgem.jeesite.modules.cancel.remotecancel.entity.RemoteCancel;
import com.thinkgem.jeesite.modules.cancel.remotecancel.service.RemoteCancelService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 异地核销Controller
 * @author tanghaobo
 * @version 2017-06-18
 */
@Controller
@RequestMapping(value = "${adminPath}/remotecancel/remoteCancel")
public class RemoteCancelController extends BaseController {

	@Autowired
	private RemoteCancelService remoteCancelService;
	
	@Autowired
	private ActivityReportService activityReportService;
	
	@ModelAttribute
	public RemoteCancel get(@RequestParam(required=false) String id) {
		RemoteCancel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = remoteCancelService.get(id);
		}
		if (entity == null){
			entity = new RemoteCancel();
		}
		return entity;
	}
	
	@RequiresPermissions("remotecancel:remoteCancel:view")
	@RequestMapping(value = {"list", ""})
	public String list(RemoteCancel remoteCancel, HttpServletRequest request, HttpServletResponse response, Model model) {
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		if(startTime != null && endTime != null){
			remoteCancel.setStartTime(startTime);
			remoteCancel.setEndTime(endTime);
		}
		Page<RemoteCancel> page = new Page<>();
		page = remoteCancelService.findPage(new Page<RemoteCancel>(request, response), remoteCancel); 
		List<RemoteCancel> list=new ArrayList<>();
        User user = UserUtils.getUser();
        model.addAttribute("userType", user.getUserType());
        if(!user.getId().equals("1")){
        	//这里根据当前登录人来判断他是否有申请活动  
            List<ActivityReport> findListByApplyUser = activityReportService.findListByApplyUser(user.getId());
            List<RemoteCancel> list2 = page.getList();
            for (RemoteCancel c : list2) {
            	for (ActivityReport activityReport : findListByApplyUser) {
            		//这里扫码记录所属的活动
            		String activityId = c.getActivityId();
            		//如果相同的时候就加入
            		if(activityId.equals(activityReport.getId())){
            			list.add(c);
            		}
				}
    		}
            page.setList(list);
        }
		model.addAttribute("page", page);
		return "modules/cancel/remotecancel/remoteCancelList.jsp";
	}
	
	@RequiresPermissions("remotecancel:remoteCancel:view")
	@RequestMapping(value = "export", method = RequestMethod.GET)
	public String exportNum(RemoteCancel remoteCancel,HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		List<RemoteCancel> info = remoteCancelService.findList(remoteCancel);
		try {
			String fileName = "跨区域核销统计" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			List<String> headerList = Lists.newArrayList();
			headerList.add("活动名称");
			headerList.add("发奖经销商名称");
			headerList.add("发奖经销商区域");
			headerList.add("核销经销商");
			headerList.add("核销经销商区域");
			headerList.add("核销人姓名");
			headerList.add("核销人电话");
			headerList.add("商品名称");
			headerList.add("统计数量");
			ExportExcel ee = new ExportExcel("跨区域核销统计记录", headerList);
			for(RemoteCancel si : info){
				Row row = ee.addRow();
				ee.addCell(row, 0, si.getActivityName());
				ee.addCell(row, 1, si.getActivityPrizesOffice());
				ee.addCell(row, 2, si.getActivityPrizesAddress());
				ee.addCell(row, 3, si.getCancelOffice());
				ee.addCell(row, 4, si.getCancelAddress());
				ee.addCell(row, 5, si.getCancelUserName());
				ee.addCell(row, 6, si.getCancelUserPhone());
				ee.addCell(row, 7, si.getPrizeName());
				ee.addCell(row, 8, si.getCancelNumber());
			}
			System.out.println("！！！！！导出列表长度为："+info.size()+"！！！！！");
			ee.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出信息失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/remotecancel/remoteCancelList.jsp";
	}
	
	@RequiresPermissions("remotecancel:remoteCancel:view")
	@RequestMapping(value = "form")
	public String form(RemoteCancel remoteCancel, Model model) {
		model.addAttribute("remoteCancel", remoteCancel);
		return "modules/cancel/remotecancel/remoteCancelForm.jsp";
	}

	@RequiresPermissions("remotecancel:remoteCancel:edit")
	@RequestMapping(value = "save")
	public String save(RemoteCancel remoteCancel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, remoteCancel)){
			return form(remoteCancel, model);
		}
		remoteCancelService.save(remoteCancel);
		addMessage(redirectAttributes, "保存异地核销成功");
		return "redirect:"+Global.getAdminPath()+"/remotecancel/remoteCancel/?repage";
	}
	
	@RequiresPermissions("remotecancel:remoteCancel:edit")
	@RequestMapping(value = "delete")
	public String delete(RemoteCancel remoteCancel, RedirectAttributes redirectAttributes) {
		remoteCancelService.delete(remoteCancel);
		addMessage(redirectAttributes, "删除异地核销成功");
		return "redirect:"+Global.getAdminPath()+"/remotecancel/remoteCancel/?repage";
	}

}