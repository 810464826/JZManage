/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.activity.revokeaudit.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.activity.revokeaudit.entity.RevokeAudit;
import com.thinkgem.jeesite.modules.activity.revokeaudit.service.RevokeAuditService;
import com.thinkgem.jeesite.modules.activityorder.entity.Activityorder;
import com.thinkgem.jeesite.modules.activityorder.service.ActivityorderService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 撤销审核Controller
 * @author tanghaobo
 * @version 2017-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/revokeaudit/revokeAudit")
public class RevokeAuditController extends BaseController {

	@Autowired
	private RevokeAuditService revokeAuditService;
	@Autowired
	private ActivityorderService activityorderService;
	
	@ModelAttribute
	public RevokeAudit get(@RequestParam(required=false) String id) {
		RevokeAudit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = revokeAuditService.get(id);
		}
		if (entity == null){
			entity = new RevokeAudit();
		}
		return entity;
	}
	
	@RequiresPermissions("revokeaudit:revokeAudit:view")
	@RequestMapping(value = {"list", ""})
	public String list(RevokeAudit revokeAudit, HttpServletRequest request, HttpServletResponse response, Model model) {
		revokeAudit.setApplyUser(UserUtils.getUser().getId());
		Page<RevokeAudit> page = revokeAuditService.findPage(new Page<RevokeAudit>(request, response), revokeAudit); 
		model.addAttribute("page", page);
		return "modules/activity/revokeaudit/revokeAuditList.jsp";
	}

	@RequiresPermissions("revokeaudit:revokeAudit:view")
	@RequestMapping(value = "form")
	public String form(RevokeAudit revokeAudit, Model model) {
		model.addAttribute("revokeAudit", revokeAudit);
		return "modules/activity/revokeaudit/revokeAuditForm.jsp";
	}

	@RequiresPermissions("revokeaudit:revokeAudit:edit")
	@RequestMapping(value = "save")
	public String save(RevokeAudit revokeAudit, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, revokeAudit)){
			return form(revokeAudit, model);
		}
		revokeAuditService.save(revokeAudit);
		addMessage(redirectAttributes, "保存撤销审核成功");
		return "redirect:"+Global.getAdminPath()+"/revokeaudit/revokeAudit/?repage";
	}
	
	@RequiresPermissions("revokeaudit:revokeAudit:edit")
	@RequestMapping(value = "delete")
	public String delete(RevokeAudit revokeAudit, RedirectAttributes redirectAttributes) {
		String id = revokeAudit.getId();
		String[] split = id.split(",");
		for (int i = 0; i < split.length; i++) {
			if(split[i]!=""){
				id=split[i];
			}
		}
		revokeAudit.setId(id);
		revokeAuditService.delete(revokeAudit);
		//这里查询的是在活动中的运单
		List<Activityorder> findActivityOrder = activityorderService.findActivityOrderRecordIdByState(id);
		//如果将驳回的活动 将相关联的活动删除
		for (Activityorder activityorder : findActivityOrder) {
			activityorder.setId(activityorder.getId());
			//0是活动取消  1活动进行
			activityorder.setState("0");
			activityorderService.save(activityorder);
		}
		addMessage(redirectAttributes, "撤销审核成功");
		return "redirect:"+Global.getAdminPath()+"/revokeaudit/revokeAudit/?repage";
	}

}