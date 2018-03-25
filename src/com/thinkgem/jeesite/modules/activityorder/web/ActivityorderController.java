/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.activityorder.web;

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
import com.thinkgem.jeesite.modules.activityorder.entity.Activityorder;
import com.thinkgem.jeesite.modules.activityorder.service.ActivityorderService;

/**
 * 活动运单表Controller
 * @author 陈小兵
 * @version 2017-08-10
 */
@Controller
@RequestMapping(value = "${adminPath}/activityorder/activityorder")
public class ActivityorderController extends BaseController {

	@Autowired
	private ActivityorderService activityorderService;
	
	@ModelAttribute
	public Activityorder get(@RequestParam(required=false) String id) {
		Activityorder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = activityorderService.get(id);
		}
		if (entity == null){
			entity = new Activityorder();
		}
		return entity;
	}
	
	@RequiresPermissions("activityorder:activityorder:view")
	@RequestMapping(value = {"list", ""})
	public String list(Activityorder activityorder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Activityorder> page = activityorderService.findPage(new Page<Activityorder>(request, response), activityorder); 
		model.addAttribute("page", page);
		return "modules/activityorder/activityorderList";
	}

	@RequiresPermissions("activityorder:activityorder:view")
	@RequestMapping(value = "form")
	public String form(Activityorder activityorder, Model model) {
		model.addAttribute("activityorder", activityorder);
		return "modules/activityorder/activityorderForm";
	}

	@RequiresPermissions("activityorder:activityorder:edit")
	@RequestMapping(value = "save")
	public String save(Activityorder activityorder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, activityorder)){
			return form(activityorder, model);
		}
		activityorderService.save(activityorder);
		addMessage(redirectAttributes, "保存活动运单表成功");
		return "redirect:"+Global.getAdminPath()+"/activityorder/activityorder/?repage";
	}
	
	@RequiresPermissions("activityorder:activityorder:edit")
	@RequestMapping(value = "delete")
	public String delete(Activityorder activityorder, RedirectAttributes redirectAttributes) {
		activityorderService.delete(activityorder);
		addMessage(redirectAttributes, "删除活动运单表成功");
		return "redirect:"+Global.getAdminPath()+"/activityorder/activityorder/?repage";
	}

}