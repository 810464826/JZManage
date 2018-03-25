/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.wxmenu.web;

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
import com.thinkgem.jeesite.modules.wx.wxmenu.entity.WxMenu;
import com.thinkgem.jeesite.modules.wx.wxmenu.service.WxMenuService;

/**
 * 微信自定义菜单Controller
 * @author tanghaobo
 * @version 2017-03-20
 */
@Controller
@RequestMapping(value = "${adminPath}/wxmenu/wxMenu")
public class WxMenuController extends BaseController {

	@Autowired
	private WxMenuService wxMenuService;
	
	@ModelAttribute
	public WxMenu get(@RequestParam(required=false) String id) {
		WxMenu entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxMenuService.get(id);
		}
		if (entity == null){
			entity = new WxMenu();
		}
		return entity;
	}
	
	@RequiresPermissions("wxmenu:wxMenu:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxMenu wxMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMenu> page = wxMenuService.findPage(new Page<WxMenu>(request, response), wxMenu); 
		model.addAttribute("page", page);
		return "modules/wx/wxmenu/wxMenuList.jsp";
	}

	@RequiresPermissions("wxmenu:wxMenu:view")
	@RequestMapping(value = "form")
	public String form(WxMenu wxMenu, Model model) {
		model.addAttribute("wxMenu", wxMenu);
		return "modules/wx/wxmenu/wxMenuForm.jsp";
	}

	@RequiresPermissions("wxmenu:wxMenu:edit")
	@RequestMapping(value = "save")
	public String save(WxMenu wxMenu, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxMenu)){
			return form(wxMenu, model);
		}
		WxMenu meun = new WxMenu();
		meun.setId("1");
		wxMenu.setParent(meun);
		wxMenuService.save(wxMenu);
		addMessage(redirectAttributes, "保存微信菜单成功");
		return "redirect:"+Global.getAdminPath()+"/wxmenu/wxMenu/?repage";
	}
	
	@RequiresPermissions("wxmenu:wxMenu:edit")
	@RequestMapping(value = "delete")
	public String delete(WxMenu wxMenu, RedirectAttributes redirectAttributes) {
		wxMenuService.delete(wxMenu);
		addMessage(redirectAttributes, "删除微信菜单成功");
		return "redirect:"+Global.getAdminPath()+"/wxmenu/wxMenu/?repage";
	}

}