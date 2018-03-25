/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.followuser.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

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
import com.thinkgem.jeesite.modules.wx.followuser.entity.WxUser;
import com.thinkgem.jeesite.modules.wx.followuser.service.WxUserService;

/**
 * 微信公众号上的关注用户Controller
 * @author tanghaobo
 * @version 2017-03-13
 */
@Controller
@RequestMapping(value = "${adminPath}/followuser/wxUser")
public class WxUserController extends BaseController {

	@Autowired
	private WxUserService wxUserService;
	
	@ModelAttribute
	public WxUser get(@RequestParam(required=false) String id) {
		WxUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxUserService.get(id);
		}
		if (entity == null){
			entity = new WxUser();
		}
		return entity;
	}
	
	@RequiresPermissions("followuser:wxUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxUser wxUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxUser> page = wxUserService.findPage(new Page<WxUser>(request, response), wxUser); 
		List<WxUser> wx = page.getList();
		for(WxUser wxuser : wx){
			try {
				String urlStr = URLDecoder.decode(wxuser.getNickname(), "UTF-8");
				wxuser.setNickname(urlStr);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		model.addAttribute("page", page);
		return "modules/wx/followuser/wxUserList.jsp";
	}

	@RequiresPermissions("followuser:wxUser:view")
	@RequestMapping(value = "form")
	public String form(WxUser wxUser, Model model) {
		model.addAttribute("wxUser", wxUser);
		return "modules/wx/followuser/wxUserForm.jsp";
	}

	@RequiresPermissions("followuser:wxUser:edit")
	@RequestMapping(value = "save")
	public String save(WxUser wxUser, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxUser)){
			return form(wxUser, model);
		}
		
		addMessage(redirectAttributes, "保存关注用户成功");
		return "redirect:"+Global.getAdminPath()+"/followuser/wxUser/?repage";
	}
	
	@RequiresPermissions("followuser:wxUser:edit")
	@RequestMapping(value = "delete")
	public String delete(WxUser wxUser, RedirectAttributes redirectAttributes) {
		wxUserService.delete(wxUser);
		addMessage(redirectAttributes, "删除关注用户成功");
		return "redirect:"+Global.getAdminPath()+"/followuser/wxUser/?repage";
	}

}