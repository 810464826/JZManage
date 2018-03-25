/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.userrole.web;

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
import com.thinkgem.jeesite.modules.userrole.entity.SysUserRole;
import com.thinkgem.jeesite.modules.userrole.service.SysUserRoleService;

/**
 * 人员角色关系Controller
 * @author 陈小兵
 * @version 2017-08-06
 */
@Controller
@RequestMapping(value = "${adminPath}/userrole/sysUserRole")
public class SysUserRoleController extends BaseController {

	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	@ModelAttribute
	public SysUserRole get(@RequestParam(required=false) String id) {
		SysUserRole entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysUserRoleService.get(id);
		}
		if (entity == null){
			entity = new SysUserRole();
		}
		return entity;
	}
	
	@RequiresPermissions("userrole:sysUserRole:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysUserRole sysUserRole, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysUserRole> page = sysUserRoleService.findPage(new Page<SysUserRole>(request, response), sysUserRole); 
		model.addAttribute("page", page);
		return "modules/userrole/sysUserRoleList";
	}

	@RequiresPermissions("userrole:sysUserRole:view")
	@RequestMapping(value = "form")
	public String form(SysUserRole sysUserRole, Model model) {
		model.addAttribute("sysUserRole", sysUserRole);
		return "modules/userrole/sysUserRoleForm";
	}

	@RequiresPermissions("userrole:sysUserRole:edit")
	@RequestMapping(value = "save")
	public String save(SysUserRole sysUserRole, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysUserRole)){
			return form(sysUserRole, model);
		}
		sysUserRoleService.save(sysUserRole);
		addMessage(redirectAttributes, "保存人员角色关系成功");
		return "redirect:"+Global.getAdminPath()+"/userrole/sysUserRole/?repage";
	}
	
	@RequiresPermissions("userrole:sysUserRole:edit")
	@RequestMapping(value = "delete")
	public String delete(SysUserRole sysUserRole, RedirectAttributes redirectAttributes) {
		sysUserRoleService.delete(sysUserRole);
		addMessage(redirectAttributes, "删除人员角色关系成功");
		return "redirect:"+Global.getAdminPath()+"/userrole/sysUserRole/?repage";
	}

}