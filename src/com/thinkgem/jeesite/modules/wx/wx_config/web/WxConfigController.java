/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.wx_config.web;

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
import com.thinkgem.jeesite.modules.wx.wx_config.entity.WxConfig;
import com.thinkgem.jeesite.modules.wx.wx_config.service.WxConfigService;

/**
 * 调用微信需要的一些配置参数Controller
 * @author tanghaobo
 * @version 2017-03-16
 */
@Controller
@RequestMapping(value = "${adminPath}/wx_config/wxConfig")
public class WxConfigController extends BaseController {

	@Autowired
	private WxConfigService wxConfigService;
	
	@ModelAttribute
	public WxConfig get(@RequestParam(required=false) String id) {
		WxConfig entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxConfigService.get(id);
		}
		if (entity == null){
			entity = new WxConfig();
		}
		return entity;
	}
	
	@RequiresPermissions("wx_config:wxConfig:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxConfig wxConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxConfig> page = wxConfigService.findPage(new Page<WxConfig>(request, response), wxConfig); 
		model.addAttribute("page", page);
		return "modules/wx/wx_config/wxConfigList.jsp";
	}

	@RequiresPermissions("wx_config:wxConfig:view")
	@RequestMapping(value = "form")
	public String form(WxConfig wxConfig, Model model) {
		model.addAttribute("wxConfig", wxConfig);
		return "modules/wx/wx_config/wxConfigForm.jsp";
	}

	@RequiresPermissions("wx_config:wxConfig:edit")
	@RequestMapping(value = "save")
	public String save(WxConfig wxConfig, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxConfig)){
			return form(wxConfig, model);
		}
		wxConfigService.save(wxConfig);
		addMessage(redirectAttributes, "保存微信配置成功");
		return "redirect:"+Global.getAdminPath()+"/wx_config/wxConfig/?repage";
	}
	
	@RequiresPermissions("wx_config:wxConfig:edit")
	@RequestMapping(value = "delete")
	public String delete(WxConfig wxConfig, RedirectAttributes redirectAttributes) {
		wxConfigService.delete(wxConfig);
		addMessage(redirectAttributes, "删除微信配置成功");
		return "redirect:"+Global.getAdminPath()+"/wx_config/wxConfig/?repage";
	}

}