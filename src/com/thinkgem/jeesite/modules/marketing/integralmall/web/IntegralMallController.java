/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.integralmall.web;

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
import com.thinkgem.jeesite.modules.marketing.integralmall.entity.IntegralMall;
import com.thinkgem.jeesite.modules.marketing.integralmall.service.IntegralMallService;
import com.thinkgem.jeesite.modules.product.productinfo.entity.ProductInfo;
import com.thinkgem.jeesite.modules.product.productinfo.service.ProductInfoService;

/**
 * 积分商城Controller
 * @author tanghaobo
 * @version 2017-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/integralmall/integralMall")
public class IntegralMallController extends BaseController {

	@Autowired
	private IntegralMallService integralMallService;
	@Autowired
	private ProductInfoService productInfoService;
	
	@ModelAttribute
	public IntegralMall get(@RequestParam(required=false) String id) {
		IntegralMall entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = integralMallService.get(id);
		}
		if (entity == null){
			entity = new IntegralMall();
		}
		return entity;
	}
	
	@RequiresPermissions("integralmall:integralMall:view")
	@RequestMapping(value = {"list", ""})
	public String list(IntegralMall integralMall, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<IntegralMall> page = integralMallService.findPage(new Page<IntegralMall>(request, response), integralMall); 
		model.addAttribute("page", page);
		return "modules/marketing/integralmall/integralMallList.jsp";
	}

	@RequiresPermissions("integralmall:integralMall:view")
	@RequestMapping(value = "form")
	public String form(IntegralMall integralMall, Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("integralMall", integralMall);
		ProductInfo productInfo = new ProductInfo();
		Page<ProductInfo> page = productInfoService.findPage(new Page<ProductInfo>(request, response), productInfo); 
		model.addAttribute("page", page);
		model.addAttribute("product", page);
		return "modules/marketing/integralmall/integralMallForm.jsp";
	}

	@RequiresPermissions("integralmall:integralMall:edit")
	@RequestMapping(value = "save")
	public String save(IntegralMall integralMall, Model model,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, integralMall)){
			return form(integralMall, model,null,null);
		}
		ProductInfo  info = productInfoService.get(integralMall.getName());
		if(info==null){
			integralMall.setCardId(null);
		}else{
			integralMall.setCardId(info.getCardId());
		}
		integralMall.setPrizeType(info.getType());
		integralMallService.save(integralMall);
		addMessage(redirectAttributes, "保存积分商城成功");
		return "redirect:"+Global.getAdminPath()+"/integralmall/integralMall/?repage";
	}
	
	@RequiresPermissions("integralmall:integralMall:edit")
	@RequestMapping(value = "delete")
	public String delete(IntegralMall integralMall, RedirectAttributes redirectAttributes) {
		integralMallService.delete(integralMall);
		addMessage(redirectAttributes, "删除积分商城成功");
		return "redirect:"+Global.getAdminPath()+"/integralmall/integralMall/?repage";
	}

}