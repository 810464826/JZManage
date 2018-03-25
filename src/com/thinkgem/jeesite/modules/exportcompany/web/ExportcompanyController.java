/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exportcompany.web;

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
import com.thinkgem.jeesite.modules.exportcompany.entity.Exportcompany;
import com.thinkgem.jeesite.modules.exportcompany.service.ExportcompanyService;

/**
 * 终端门店Controller
 * @author cxb
 * @version 2017-09-04
 */
@Controller
@RequestMapping(value = "${adminPath}/exportcompany/exportcompany")
public class ExportcompanyController extends BaseController {

	@Autowired
	private ExportcompanyService exportcompanyService;
	
	@ModelAttribute
	public Exportcompany get(@RequestParam(required=false) String id) {
		Exportcompany entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = exportcompanyService.get(id);
		}
		if (entity == null){
			entity = new Exportcompany();
		}
		return entity;
	}
	
	@RequiresPermissions("exportcompany:exportcompany:view")
	@RequestMapping(value = {"list", ""})
	public String list(Exportcompany exportcompany, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Exportcompany> page = exportcompanyService.findPage(new Page<Exportcompany>(request, response), exportcompany); 
		model.addAttribute("page", page);
		return "modules/exportcompany/exportcompanyList";
	}

	@RequiresPermissions("exportcompany:exportcompany:view")
	@RequestMapping(value = "form")
	public String form(Exportcompany exportcompany, Model model) {
		model.addAttribute("exportcompany", exportcompany);
		return "modules/exportcompany/exportcompanyForm";
	}

	@RequiresPermissions("exportcompany:exportcompany:edit")
	@RequestMapping(value = "save")
	public String save(Exportcompany exportcompany, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, exportcompany)){
			return form(exportcompany, model);
		}
		exportcompanyService.save(exportcompany);
		addMessage(redirectAttributes, "保存终端门店成功");
		return "redirect:"+Global.getAdminPath()+"/exportcompany/exportcompany/?repage";
	}
	
	@RequiresPermissions("exportcompany:exportcompany:edit")
	@RequestMapping(value = "delete")
	public String delete(Exportcompany exportcompany, RedirectAttributes redirectAttributes) {
		exportcompanyService.delete(exportcompany);
		addMessage(redirectAttributes, "删除终端门店成功");
		return "redirect:"+Global.getAdminPath()+"/exportcompany/exportcompany/?repage";
	}

}