/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.productintegral.web;

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
import com.thinkgem.jeesite.modules.productintegral.entity.Degree;
import com.thinkgem.jeesite.modules.productintegral.entity.KindName;
import com.thinkgem.jeesite.modules.productintegral.entity.Productintegral;
import com.thinkgem.jeesite.modules.productintegral.entity.Spec;
import com.thinkgem.jeesite.modules.productintegral.entity.Volume;
import com.thinkgem.jeesite.modules.productintegral.service.DegreeService;
import com.thinkgem.jeesite.modules.productintegral.service.KindNameService;
import com.thinkgem.jeesite.modules.productintegral.service.ProductintegralService;
import com.thinkgem.jeesite.modules.productintegral.service.SpecService;
import com.thinkgem.jeesite.modules.productintegral.service.VolumeService;

/**
 * 产品积分关系Controller
 * @author cxb
 * @version 2017-10-30
 */
@Controller
@RequestMapping(value = "${adminPath}/productintegral/productintegral")
public class ProductintegralController extends BaseController {

	@Autowired
	private ProductintegralService productintegralService;
	@Autowired
	private SpecService specService;
	@Autowired
	private DegreeService degreeService;
	@Autowired
	private KindNameService kindNameService;
	@Autowired
	private VolumeService volumeService;
	
	@ModelAttribute
	public Productintegral get(@RequestParam(required=false) String id) {
		Productintegral entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productintegralService.get(id);
		}
		if (entity == null){
			entity = new Productintegral();
		}
		return entity;
	}
	
	@RequiresPermissions("productintegral:productintegral:view")
	@RequestMapping(value = {"list", ""})
	public String list(Productintegral productintegral, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Productintegral> page = productintegralService.findPage(new Page<Productintegral>(request, response), productintegral); 
		model.addAttribute("page", page);
		return "modules/productintegral/productintegralList.jsp";
	}

	@RequiresPermissions("productintegral:productintegral:view")
	@RequestMapping(value = "form")
	public String form(Productintegral productintegral, Model model) {
		model.addAttribute("productintegral", productintegral);
		return "modules/productintegral/productintegralForm.jsp";
	}
	
	/**
	 * 进入添加页面的时候
	 * @param productintegral
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("productintegral:productintegral:view")
	@RequestMapping(value = "goToAddForm")
	public String goToAddForm(Productintegral productintegral, Model model) {
		//讲所有的名称  度数 容量 规格 (查询传递)           所送积分(自己设置)
		List<Volume> allVolume = volumeService.getAllVolume();
		List<Degree> allDegree = degreeService.getAllDegree();
		List<KindName> allKindName = kindNameService.getAllKindName();
		List<Spec> allSpec = specService.getAllSpec();
		model.addAttribute("allVolume", allVolume);
		model.addAttribute("allDegree", allDegree);
		model.addAttribute("allKindName", allKindName);
		model.addAttribute("allSpec", allSpec);
		return "modules/productintegral/productintegralAddForm.jsp";
	}
	
	/**
	 * 保存产品积分
	 * @param productintegral
	 * @param model
	 * @param redirectAttributes
	 * @param request
	 * @return
	 */
	@RequiresPermissions("productintegral:productintegral:edit")
	@RequestMapping(value = "saveIntegral")
	public String saveIntegral(Productintegral productintegral, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if (!beanValidator(model, productintegral)){
			return form(productintegral, model);
		}
		String kindName = request.getParameter("kindName");
		String degree = request.getParameter("degree");
		String volume = request.getParameter("volume");
		String spec = request.getParameter("spec");
		String integral = request.getParameter("integral");
		productintegral.setName(kindName);
		productintegral.setDegree(degree);
		productintegral.setVolume(volume);
		productintegral.setSpec(spec);
		//保存前先判断一下该积分产品是否已配置过了  配置过了就保存不起
		Productintegral productintegral2 = productintegralService.findProductByCondition(productintegral);
		if(productintegral2==null){
			productintegral.setIntegreal(integral);
			productintegralService.save(productintegral);
			addMessage(redirectAttributes, "保存产品积分关系成功");
		}else{
			addMessage(redirectAttributes, "该产品积分关系已配置");
		}
		return "redirect:"+Global.getAdminPath()+"/productintegral/productintegral/?repage";
	}

	@RequiresPermissions("productintegral:productintegral:edit")
	@RequestMapping(value = "save")
	public String save(Productintegral productintegral, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, productintegral)){
			return form(productintegral, model);
		}
		productintegralService.save(productintegral);
		addMessage(redirectAttributes, "保存产品积分关系成功");
		return "redirect:"+Global.getAdminPath()+"/productintegral/productintegral/?repage";
	}
	
	@RequiresPermissions("productintegral:productintegral:edit")
	@RequestMapping(value = "delete")
	public String delete(Productintegral productintegral, RedirectAttributes redirectAttributes) {
		productintegralService.delete(productintegral);
		addMessage(redirectAttributes, "删除产品积分关系成功");
		return "redirect:"+Global.getAdminPath()+"/productintegral/productintegral/?repage";
	}

}