/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cancel.localcancel.web;
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
import com.thinkgem.jeesite.modules.cancel.localcancel.entity.LocalCancel;
import com.thinkgem.jeesite.modules.cancel.localcancel.service.LocalCancelService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 核销统计Controller
 * @author tanghaobo
 * @version 2017-07-06
 */
@Controller
@RequestMapping(value = "${adminPath}/localcancel/localCancel")
public class LocalCancelController extends BaseController {

	@Autowired
	private LocalCancelService localCancelService;
	@Autowired
	private OfficeService officeService;
	@ModelAttribute
	public LocalCancel get(@RequestParam(required=false) String id) {
		LocalCancel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = localCancelService.get(id);
		}
		if (entity == null){
			entity = new LocalCancel();
		}
		return entity;
	}
	
	
	
	@RequiresPermissions("localcancel:localCancel:view")
	@RequestMapping(value = {"list", ""})
	public String list(LocalCancel localCancel, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user =  UserUtils.getUser();
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		//这里是前端选择的核销的经销商     
		String companyName = request.getParameter("companyName");
		//如果是超级管理员就不要判断了
		if(user.getId().equals("1")){
			if((companyName!=null)&&(!"".equals(companyName))){
				localCancel.setCancelOffice(companyName);
			}
		}
		//这里需要判断一下当前登录的用户是不是他的上级或者上上级
		else{
//			localCancel.setActivityPrizesId(user.getOffice().getId());
			if((companyName!=null)&&(!"".equals(companyName))){
				// 根据经销商的名称查询经销商Office的信息
				Office officeByName = officeService.getOfficeByName(companyName);
				//当前登录用户的公司id
				String id = user.getOffice().getId();
				//查询的经销商的officeid
				String id1 = officeByName.getId();
				//查询的经销商的office父级id,
				String parentIds = officeByName.getParentIds();
				String[] ids = parentIds.split(",");
				boolean canLook = isCanLook(ids, id);
				boolean same = isSame(id, id1);
				if(canLook||same){
					localCancel.setCancelOffice(companyName);
				}else{
					//不是同一级 或者不是上级  查看不了的
					localCancel.setCancelOffice("不是同一级 或者不是上级  查看不了的");
				}
			}else{
				//没有输入查询的核销经销商
			}
		}
		if((startTime != null &&!"".equals(startTime)) && (endTime != null&&!"".equals(endTime))){
			localCancel.setStartTime(startTime);
			localCancel.setEndTime(endTime);
		}
		
		Page<LocalCancel> page = new Page<>();
		List<LocalCancel> list=new ArrayList<>();
		page=localCancelService.findPage(new Page<LocalCancel>(request, response), localCancel); 
		//这里需要过滤一下的如果是超级管理员就不需要过滤了
		if(!user.getId().equals("1")){
			//当前登录人的userid
			String userid = user.getId();
			//这里是查询出来的核销记录还未筛选
			List<LocalCancel> list1 = page.getList();
			for (LocalCancel lc : list1) {
				//如果核销记录中的核销用户的id和当前登录人的officeid相同的话就加进去
				if(lc.getCancelUser().equals(userid)){
					list.add(lc);
				}
			}
			//重新给page设置新的list
			page.setList(list);
		}
		model.addAttribute("page", page);
		return "modules/cancel/localcancel/localCancelList.jsp";
	}
	
	/**
	 * 
	 * @param ids  查询经销商的office父级ID
	 * @param id   当前登录用的的officeID
	 * @return   true 代表当前登录用户是查询的上级
	 */
	public boolean isCanLook(String[] ids,String id){
		for (int i = 0; i < ids.length; i++) {
			if(id.equals(ids[i])){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param id 当前登录用户的officeID
	 * @param id1 查询经销商的ID
	 * @return true 两个经销商是同一级的
	 */
	public boolean isSame(String id,String id1){
		if(id.equals(id1)){
			return true;
		}
		return false;
	}

	@RequiresPermissions("localcancel:localCancel:view")
	@RequestMapping(value = "export", method = RequestMethod.GET)
	public String exportNum(LocalCancel localCancel,HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		List<LocalCancel> info = localCancelService.findList(localCancel);
		try {
			String fileName = "核销统计" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			List<String> headerList = Lists.newArrayList();
			headerList.add("发奖经销商名称");
			headerList.add("发奖经销商区域");
			headerList.add("核销经销商");
			headerList.add("核销经销商区域");
			headerList.add("核销人姓名");
			headerList.add("核销人电话");
			headerList.add("商品名称");
			headerList.add("统计数量");
			ExportExcel ee = new ExportExcel("核销统计记录", headerList);
			for(LocalCancel si : info){
				Row row = ee.addRow();
				ee.addCell(row, 0, si.getActivityPrizesOffice());
				ee.addCell(row, 1, si.getActivityPrizesAddress());
				ee.addCell(row, 2, si.getCancelOffice());
				ee.addCell(row, 3, si.getCancelAddress());
				ee.addCell(row, 4, si.getCancelUserName());
				ee.addCell(row, 5, si.getCancelUserPhone());
				ee.addCell(row, 6, si.getPrizeName());
				ee.addCell(row, 7, si.getCancelNumber());
			}
			System.out.println("！！！！！导出列表长度为："+info.size()+"！！！！！");
			ee.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出信息失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/localcancel/localCancelList.jsp";
	}
	
	@RequiresPermissions("localcancel:localCancel:view")
	@RequestMapping(value = "form")
	public String form(LocalCancel localCancel, Model model) {
		model.addAttribute("localCancel", localCancel);
		return "modules/cancel/localcancel/localCancelForm.jsp";
	}

	@RequiresPermissions("localcancel:localCancel:edit")
	@RequestMapping(value = "save")
	public String save(LocalCancel localCancel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, localCancel)){
			return form(localCancel, model);
		}
		localCancelService.save(localCancel);
		addMessage(redirectAttributes, "保存核销统计成功");
		return "redirect:"+Global.getAdminPath()+"/localcancel/localCancel/?repage";
	}
	
	@RequiresPermissions("localcancel:localCancel:edit")
	@RequestMapping(value = "delete")
	public String delete(LocalCancel localCancel, RedirectAttributes redirectAttributes) {
		localCancelService.delete(localCancel);
		addMessage(redirectAttributes, "删除核销统计成功");
		return "redirect:"+Global.getAdminPath()+"/localcancel/localCancel/?repage";
	}

}