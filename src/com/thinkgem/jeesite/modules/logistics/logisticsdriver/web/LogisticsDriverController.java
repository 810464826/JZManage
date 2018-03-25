/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.logistics.logisticsdriver.web;

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
import com.thinkgem.jeesite.modules.logistics.logisticsdriver.entity.LogisticsDriver;
import com.thinkgem.jeesite.modules.logistics.logisticsdriver.service.LogisticsDriverService;

/**
 * 物流司机Controller
 * @author tanghaobo
 * @version 2017-03-31
 */
@Controller
@RequestMapping(value = "${adminPath}/logisticsdriver/logisticsDriver")
public class LogisticsDriverController extends BaseController {

	@Autowired
	private LogisticsDriverService logisticsDriverService;
	
	@ModelAttribute
	public LogisticsDriver get(@RequestParam(required=false) String id) {
		LogisticsDriver entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = logisticsDriverService.get(id);
		}
		if (entity == null){
			entity = new LogisticsDriver();
		}
		return entity;
	}
	
	@RequiresPermissions("logisticsdriver:logisticsDriver:view")
	@RequestMapping(value = {"list", ""})
	public String list(LogisticsDriver logisticsDriver, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LogisticsDriver> page = logisticsDriverService.findPage(new Page<LogisticsDriver>(request, response), logisticsDriver); 
		model.addAttribute("page", page);
		model.addAttribute("truck", logisticsDriver.getTruckId());
		return "modules/logistics/logisticsdriver/logisticsDriverList.jsp";
	}

	@RequiresPermissions("logisticsdriver:logisticsDriver:view")
	@RequestMapping(value = "form")
	public String form(LogisticsDriver logisticsDriver, Model model) {
		model.addAttribute("logisticsDriver", logisticsDriver);
		model.addAttribute("truckid", logisticsDriver.getTruckId());
		return "modules/logistics/logisticsdriver/logisticsDriverForm.jsp";
	}

	@RequiresPermissions("logisticsdriver:logisticsDriver:edit")
	@RequestMapping(value = "save")
	public String save(LogisticsDriver logisticsDriver, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, logisticsDriver)){
			return form(logisticsDriver, model);
		}
		logisticsDriverService.save(logisticsDriver);
		addMessage(redirectAttributes, "保存物流司机成功");
		return "redirect:"+Global.getAdminPath()+"/logisticsdriver/logisticsDriver/?repage&truckId="+logisticsDriver.getTruckId();
	}
	
	@RequiresPermissions("logisticsdriver:logisticsDriver:edit")
	@RequestMapping(value = "delete")
	public String delete(LogisticsDriver logisticsDriver, RedirectAttributes redirectAttributes) {
		logisticsDriverService.delete(logisticsDriver);
		addMessage(redirectAttributes, "删除物流司机成功");
		return "redirect:"+Global.getAdminPath()+"/logisticsdriver/logisticsDriver/?repage&truckId="+logisticsDriver.getTruckId();
	}

}