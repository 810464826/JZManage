/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.logistics.logisticsfirm.web;

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
import com.thinkgem.jeesite.modules.logistics.logisticsfirm.entity.LogisticsFirm;
import com.thinkgem.jeesite.modules.logistics.logisticsfirm.service.LogisticsFirmService;

/**
 * 物流信息Controller
 * @author tanghaobo
 * @version 2017-03-30
 */
@Controller
@RequestMapping(value = "${adminPath}/logisticsfirm/logisticsFirm")
public class LogisticsFirmController extends BaseController {

	@Autowired
	private LogisticsFirmService logisticsFirmService;
	
	@ModelAttribute
	public LogisticsFirm get(@RequestParam(required=false) String id) {
		LogisticsFirm entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = logisticsFirmService.get(id);
		}
		if (entity == null){
			entity = new LogisticsFirm();
		}
		return entity;
	}
	
	@RequiresPermissions("logisticsfirm:logisticsFirm:view")
	@RequestMapping(value = {"list", ""})
	public String list(LogisticsFirm logisticsFirm, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LogisticsFirm> page = logisticsFirmService.findPage(new Page<LogisticsFirm>(request, response), logisticsFirm); 
		model.addAttribute("page", page);
		return "modules/logistics/logisticsfirm/logisticsFirmList.jsp";
	}

	@RequiresPermissions("logisticsfirm:logisticsFirm:view")
	@RequestMapping(value = "form")
	public String form(LogisticsFirm logisticsFirm, Model model) {
		model.addAttribute("logisticsFirm", logisticsFirm);
		return "modules/logistics/logisticsfirm/logisticsFirmForm.jsp";
	}

	@RequiresPermissions("logisticsfirm:logisticsFirm:edit")
	@RequestMapping(value = "save")
	public String save(LogisticsFirm logisticsFirm, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, logisticsFirm)){
			return form(logisticsFirm, model);
		}
		logisticsFirmService.save(logisticsFirm);
		addMessage(redirectAttributes, "保存物流信息成功");
		return "redirect:"+Global.getAdminPath()+"/logisticsfirm/logisticsFirm/?repage";
	}
	
	@RequiresPermissions("logisticsfirm:logisticsFirm:edit")
	@RequestMapping(value = "delete")
	public String delete(LogisticsFirm logisticsFirm, RedirectAttributes redirectAttributes) {
		logisticsFirmService.delete(logisticsFirm);
		addMessage(redirectAttributes, "删除物流信息成功");
		return "redirect:"+Global.getAdminPath()+"/logisticsfirm/logisticsFirm/?repage";
	}

}