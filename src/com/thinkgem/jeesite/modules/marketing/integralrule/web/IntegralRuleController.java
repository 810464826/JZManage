/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.integralrule.web;

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
import com.thinkgem.jeesite.modules.marketing.integralrule.entity.IntegralRule;
import com.thinkgem.jeesite.modules.marketing.integralrule.service.IntegralRuleService;

/**
 * 积分规则Controller
 * @author tanghaobo
 * @version 2017-03-16
 */
@Controller
@RequestMapping(value = "${adminPath}/integralrule/integralRule")
public class IntegralRuleController extends BaseController {

	@Autowired
	private IntegralRuleService integralRuleService;
	
	@ModelAttribute
	public IntegralRule get(@RequestParam(required=false) String id) {
		IntegralRule entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = integralRuleService.get(id);
		}
		if (entity == null){
			entity = new IntegralRule();
		}
		return entity;
	}
	
	@RequiresPermissions("integralrule:integralRule:view")
	@RequestMapping(value = {"list", ""})
	public String list(IntegralRule integralRule, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<IntegralRule> page = integralRuleService.findPage(new Page<IntegralRule>(request, response), integralRule); 
		model.addAttribute("page", page);
		return "modules/marketing/integralrule/integralRuleList.jsp";
	}

	@RequiresPermissions("integralrule:integralRule:view")
	@RequestMapping(value = "form")
	public String form(IntegralRule integralRule, Model model) {
		model.addAttribute("integralRule", integralRule);
		return "modules/marketing/integralrule/integralRuleForm.jsp";
	}

	@RequiresPermissions("integralrule:integralRule:edit")
	@RequestMapping(value = "save")
	public String save(IntegralRule integralRule, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, integralRule)){
			return form(integralRule, model);
		}
		integralRuleService.save(integralRule);
		addMessage(redirectAttributes, "保存积分规则成功");
		return "redirect:"+Global.getAdminPath()+"/integralrule/integralRule/?repage";
	}
	
	@RequiresPermissions("integralrule:integralRule:edit")
	@RequestMapping(value = "delete")
	public String delete(IntegralRule integralRule, RedirectAttributes redirectAttributes) {
		integralRuleService.delete(integralRule);
		addMessage(redirectAttributes, "删除积分规则成功");
		return "redirect:"+Global.getAdminPath()+"/integralrule/integralRule/?repage";
	}

}