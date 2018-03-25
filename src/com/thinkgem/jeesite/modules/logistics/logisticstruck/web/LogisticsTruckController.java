/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.logistics.logisticstruck.web;

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
import com.thinkgem.jeesite.modules.logistics.logisticstruck.entity.LogisticsTruck;
import com.thinkgem.jeesite.modules.logistics.logisticstruck.service.LogisticsTruckService;

/**
 * 物流车辆Controller
 * @author tanghaobo
 * @version 2017-03-31
 */
@Controller
@RequestMapping(value = "${adminPath}/logisticstruck/logisticsTruck")
public class LogisticsTruckController extends BaseController {

	@Autowired
	private LogisticsTruckService logisticsTruckService;
	
	@ModelAttribute
	public LogisticsTruck get(@RequestParam(required=false) String id) {
		LogisticsTruck entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = logisticsTruckService.get(id);
		}
		if (entity == null){
			entity = new LogisticsTruck();
		}
		return entity;
	}
	
	@RequiresPermissions("logisticstruck:logisticsTruck:view")
	@RequestMapping(value = {"list", ""})
	public String list(LogisticsTruck logisticsTruck, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LogisticsTruck> page = logisticsTruckService.findPage(new Page<LogisticsTruck>(request, response), logisticsTruck); 
		model.addAttribute("page", page);
		model.addAttribute("firmid", logisticsTruck.getFirmId());
		return "modules/logistics/logisticstruck/logisticsTruckList.jsp";
	}

	@RequiresPermissions("logisticstruck:logisticsTruck:view")
	@RequestMapping(value = "form")
	public String form(LogisticsTruck logisticsTruck, Model model) {
		model.addAttribute("logisticsTruck", logisticsTruck);
		model.addAttribute("firmid", logisticsTruck.getFirmId());
		return "modules/logistics/logisticstruck/logisticsTruckForm.jsp";
	}

	@RequiresPermissions("logisticstruck:logisticsTruck:edit")
	@RequestMapping(value = "save")
	public String save(LogisticsTruck logisticsTruck, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, logisticsTruck)){
			return form(logisticsTruck, model);
		}
		logisticsTruckService.save(logisticsTruck);
		addMessage(redirectAttributes, "保存物流车辆成功");
		return "redirect:"+Global.getAdminPath()+"/logisticstruck/logisticsTruck/?repage&firmId="+logisticsTruck.getFirmId();
	}
	
	@RequiresPermissions("logisticstruck:logisticsTruck:edit")
	@RequestMapping(value = "delete")
	public String delete(LogisticsTruck logisticsTruck, RedirectAttributes redirectAttributes) {
		logisticsTruckService.delete(logisticsTruck);
		addMessage(redirectAttributes, "删除物流车辆成功");
		return "redirect:"+Global.getAdminPath()+"/logisticstruck/logisticsTruck/?repage&firmId="+logisticsTruck.getFirmId();
	}

}