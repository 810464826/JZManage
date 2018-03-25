/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.logistics.goodsquery.web;

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
import com.thinkgem.jeesite.modules.logistics.goodsquery.entity.GoodsQuery;
import com.thinkgem.jeesite.modules.logistics.goodsquery.service.GoodsQueryService;

/**
 * 发货查询Controller
 * @author tanghaobo
 * @version 2017-03-31
 */
@Controller
@RequestMapping(value = "${adminPath}/goodsquery/goodsQuery")
public class GoodsQueryController extends BaseController {

	@Autowired
	private GoodsQueryService goodsQueryService;
	
	@ModelAttribute
	public GoodsQuery get(@RequestParam(required=false) String id) {
		GoodsQuery entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsQueryService.get(id);
		}
		if (entity == null){
			entity = new GoodsQuery();
		}
		return entity;
	}
	
	@RequiresPermissions("goodsquery:goodsQuery:view")
	@RequestMapping(value = {"list", ""})
	public String list(GoodsQuery goodsQuery, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsQuery> page = goodsQueryService.findPage(new Page<GoodsQuery>(request, response), goodsQuery); 
		model.addAttribute("page", page);
		return "modules/logistics/goodsquery/goodsQueryList.jsp";
	}

	@RequiresPermissions("goodsquery:goodsQuery:view")
	@RequestMapping(value = "form")
	public String form(GoodsQuery goodsQuery, Model model) {
		model.addAttribute("goodsQuery", goodsQuery);
		return "modules/logistics/goodsquery/goodsQueryForm.jsp";
	}

	@RequiresPermissions("goodsquery:goodsQuery:edit")
	@RequestMapping(value = "save")
	public String save(GoodsQuery goodsQuery, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, goodsQuery)){
			return form(goodsQuery, model);
		}
		goodsQueryService.save(goodsQuery);
		addMessage(redirectAttributes, "保存发货查询成功");
		return "redirect:"+Global.getAdminPath()+"/goodsquery/goodsQuery/?repage";
	}
	
	@RequiresPermissions("goodsquery:goodsQuery:edit")
	@RequestMapping(value = "delete")
	public String delete(GoodsQuery goodsQuery, RedirectAttributes redirectAttributes) {
		goodsQueryService.delete(goodsQuery);
		addMessage(redirectAttributes, "删除发货查询成功");
		return "redirect:"+Global.getAdminPath()+"/goodsquery/goodsQuery/?repage";
	}

}