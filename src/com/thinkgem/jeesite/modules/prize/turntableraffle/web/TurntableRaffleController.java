/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.prize.turntableraffle.web;

import java.util.Date;
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
import com.thinkgem.jeesite.modules.prize.turntableraffle.entity.TurntableRaffle;
import com.thinkgem.jeesite.modules.prize.turntableraffle.service.TurntableRaffleService;
import com.thinkgem.jeesite.modules.product.productinfo.entity.ProductInfo;
import com.thinkgem.jeesite.modules.product.productinfo.service.ProductInfoService;
import com.thinkgem.jeesite.modules.wx.wxcard.entity.WxCard;
import com.thinkgem.jeesite.modules.wx.wxcard.service.WxCardService;

/**
 * 转盘抽奖Controller
 * @author tanghaobo
 * @version 2017-03-31
 */
@Controller
@RequestMapping(value = "${adminPath}/turntableraffle/turntableRaffle")
public class TurntableRaffleController extends BaseController {

	@Autowired
	private TurntableRaffleService turntableRaffleService;
	@Autowired
	private WxCardService wxCardService;
	@Autowired
	private ProductInfoService productInfoService;
	
	@ModelAttribute
	public TurntableRaffle get(@RequestParam(required=false) String id) {
		TurntableRaffle entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = turntableRaffleService.get(id);
		}
		if (entity == null){
			entity = new TurntableRaffle();
		}
		return entity;
	}
	
	@RequiresPermissions("turntableraffle:turntableRaffle:view")
	@RequestMapping(value = {"list", ""})
	public String list(TurntableRaffle turntableRaffle, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TurntableRaffle> page = turntableRaffleService.findPage(new Page<TurntableRaffle>(request, response), turntableRaffle); 
		model.addAttribute("page", page);
		return "modules/prize/turntableraffle/turntableRaffleList.jsp";
	}

	@RequiresPermissions("turntableraffle:turntableRaffle:view")
	@RequestMapping(value = "form")
	public String form(TurntableRaffle turntableRaffle, Model model, HttpServletRequest request, HttpServletResponse response) {
		WxCard card = new WxCard();
		List<WxCard> wxcard =  wxCardService.findList(card);
		card = wxCardService.get(turntableRaffle.getWxCardId());
		model.addAttribute("card", card);
		model.addAttribute("wxcard", wxcard);
		ProductInfo productInfo = new ProductInfo();
		Page<ProductInfo> page = productInfoService.findPage(new Page<ProductInfo>(request, response), productInfo); 
		model.addAttribute("page", page);
		model.addAttribute("product", page);
		model.addAttribute("turntableRaffle", turntableRaffle);
		return "modules/prize/turntableraffle/turntableRaffleForm.jsp";
	}

	@RequiresPermissions("turntableraffle:turntableRaffle:edit")
	@RequestMapping(value = "save")
	public String save(TurntableRaffle turntableRaffle, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, turntableRaffle)){
			return form(turntableRaffle, model,null,null);
		}
		turntableRaffle.setCreateDate(new Date());
		turntableRaffle.setTurntable("0");
		String winningProbability = turntableRaffle.getWinningProbability();
		turntableRaffle.setNewProbability(winningProbability);
		turntableRaffleService.save(turntableRaffle);
		addMessage(redirectAttributes, "保存转盘抽奖成功");
		return "redirect:"+Global.getAdminPath()+"/turntableraffle/turntableRaffle/?repage";
	}
	
	@RequiresPermissions("turntableraffle:turntableRaffle:edit")
	@RequestMapping(value = "delete")
	public String delete(TurntableRaffle turntableRaffle, RedirectAttributes redirectAttributes) {
		turntableRaffleService.delete(turntableRaffle);
		addMessage(redirectAttributes, "删除转盘抽奖成功");
		return "redirect:"+Global.getAdminPath()+"/turntableraffle/turntableRaffle/?repage";
	}

}