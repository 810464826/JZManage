/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exportuser.web;

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
import com.thinkgem.jeesite.modules.exportuser.entity.Exportuser;
import com.thinkgem.jeesite.modules.exportuser.service.ExportuserService;

/**
 * 经销商Controller
 * @author cxb
 * @version 2017-09-04
 */
@Controller
@RequestMapping(value = "${adminPath}/exportuser/exportuser")
public class ExportuserController extends BaseController {

	@Autowired
	private ExportuserService exportuserService;
	
	@ModelAttribute
	public Exportuser get(@RequestParam(required=false) String id) {
		Exportuser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = exportuserService.get(id);
		}
		if (entity == null){
			entity = new Exportuser();
		}
		return entity;
	}
	
	@RequiresPermissions("exportuser:exportuser:view")
	@RequestMapping(value = {"list", ""})
	public String list(Exportuser exportuser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Exportuser> page = exportuserService.findPage(new Page<Exportuser>(request, response), exportuser); 
		model.addAttribute("page", page);
		return "modules/exportuser/exportuserList";
	}

	@RequiresPermissions("exportuser:exportuser:view")
	@RequestMapping(value = "form")
	public String form(Exportuser exportuser, Model model) {
		model.addAttribute("exportuser", exportuser);
		return "modules/exportuser/exportuserForm";
	}

	@RequiresPermissions("exportuser:exportuser:edit")
	@RequestMapping(value = "save")
	public String save(Exportuser exportuser, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, exportuser)){
			return form(exportuser, model);
		}
		exportuserService.save(exportuser);
		addMessage(redirectAttributes, "保存经销商成功");
		return "redirect:"+Global.getAdminPath()+"/exportuser/exportuser/?repage";
	}
	
	@RequiresPermissions("exportuser:exportuser:edit")
	@RequestMapping(value = "delete")
	public String delete(Exportuser exportuser, RedirectAttributes redirectAttributes) {
		exportuserService.delete(exportuser);
		addMessage(redirectAttributes, "删除经销商成功");
		return "redirect:"+Global.getAdminPath()+"/exportuser/exportuser/?repage";
	}

}