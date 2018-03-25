/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.wxcard.web;

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
import com.thinkgem.jeesite.modules.wx.wxcard.entity.WxCard;
import com.thinkgem.jeesite.modules.wx.wxcard.service.WxCardService;

/**
 * 微信卡券Controller
 * @author tanghaobo
 * @version 2017-03-23
 */
@Controller
@RequestMapping(value = "${adminPath}/wxcard/wxCard")
public class WxCardController extends BaseController {

	@Autowired
	private WxCardService wxCardService;
	
	@ModelAttribute
	public WxCard get(@RequestParam(required=false) String id) {
		WxCard entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxCardService.get(id);
		}
		if (entity == null){
			entity = new WxCard();
		}
		return entity;
	}
	
	@RequiresPermissions("wxcard:wxCard:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxCard wxCard, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxCard> page = wxCardService.findPage(new Page<WxCard>(request, response), wxCard); 
		model.addAttribute("page", page);
		return "modules/wx/wxcard/wxCardList.jsp";
	}

	@RequiresPermissions("wxcard:wxCard:view")
	@RequestMapping(value = "form")
	public String form(WxCard wxCard, Model model) {
		model.addAttribute("wxCard", wxCard);
		return "modules/wx/wxcard/wxCardForm.jsp";
	}

	@RequiresPermissions("wxcard:wxCard:edit")
	@RequestMapping(value = "save")
	public String save(WxCard wxCard, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxCard)){
			return form(wxCard, model);
		}
		wxCardService.save(wxCard);
		addMessage(redirectAttributes, "保存微信卡券成功");
		return "redirect:"+Global.getAdminPath()+"/wxcard/wxCard/?repage";
	}
	
	@RequiresPermissions("wxcard:wxCard:edit")
	@RequestMapping(value = "delete")
	public String delete(WxCard wxCard, RedirectAttributes redirectAttributes) {
		wxCardService.delete(wxCard);
		addMessage(redirectAttributes, "删除微信卡券成功");
		return "redirect:"+Global.getAdminPath()+"/wxcard/wxCard/?repage";
	}

}