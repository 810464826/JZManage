/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.activity.activityaudit.web;

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
import com.thinkgem.jeesite.modules.activity.activityaudit.entity.ActivityAudit;
import com.thinkgem.jeesite.modules.activity.activityaudit.service.ActivityAuditService;
import com.thinkgem.jeesite.modules.activity.signgoods.entity.SignGoods;
import com.thinkgem.jeesite.modules.activity.signgoods.service.SignGoodsService;
import com.thinkgem.jeesite.modules.activityorder.entity.Activityorder;
import com.thinkgem.jeesite.modules.activityorder.service.ActivityorderService;
import com.thinkgem.jeesite.modules.prize.turntableraffle.entity.TurntableRaffle;
import com.thinkgem.jeesite.modules.prize.turntableraffle.service.TurntableRaffleService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 活动审核Controller
 * @author tanghaobo
 * @version 2017-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/activityaudit/activityAudit")
public class ActivityAuditController extends BaseController {

	@Autowired
	private ActivityAuditService activityAuditService;
	@Autowired
	private SignGoodsService signGoodsService;
	@Autowired
	private TurntableRaffleService turntableRaffleService;
	@Autowired
	private ActivityorderService activityorderService;
	
	@ModelAttribute
	public ActivityAudit get(@RequestParam(required=false) String id) {
		ActivityAudit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = activityAuditService.get(id);
		}
		if (entity == null){
			entity = new ActivityAudit();
		}
		return entity;
	}
	
	@RequiresPermissions("activityaudit:activityAudit:view")
	@RequestMapping(value = {"list", ""})
	public String list(ActivityAudit activityAudit, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		String userId = user.getId();
		Page<ActivityAudit> page = activityAuditService.findPage(new Page<ActivityAudit>(request, response), activityAudit); 
		if(!userId.equals("1")){
			List<ActivityAudit> allNoMe = activityAuditService.getAllNoMe(user.getName());
			page.setList(allNoMe);
		}
		model.addAttribute("page", page);
		return "modules/activity/activityaudit/activityAuditList.jsp";
	}

	@RequiresPermissions("activityaudit:activityAudit:view")
	@RequestMapping(value = "form")
	public String form(ActivityAudit activityAudit, Model model) {
		model.addAttribute("activityAudit", activityAudit);
		return "modules/activity/activityaudit/activityAuditForm.jsp";
	}

	@RequiresPermissions("activityaudit:activityAudit:edit")
	@RequestMapping(value = "save")
	public String save(ActivityAudit activityAudit, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, activityAudit)){
			return form(activityAudit, model);
		}
		activityAuditService.save(activityAudit);
		addMessage(redirectAttributes, "保存活动审核成功");
		return "redirect:"+Global.getAdminPath()+"/activityaudit/activityAudit/?repage";
	}
	
	@RequiresPermissions("activityaudit:activityAudit:edit")
	@RequestMapping(value = "delete")
	public String delete(ActivityAudit activityAudit, RedirectAttributes redirectAttributes) {
		activityAuditService.delete(activityAudit);
		addMessage(redirectAttributes, "删除活动审核成功");
		return "redirect:"+Global.getAdminPath()+"/activityaudit/activityAudit/?repage";
	}
	//去审核页面
	@RequestMapping(value = "activityAuditStatement")
	public String activityAuditStatement(ActivityAudit activityAudit, Model model, HttpServletRequest request, HttpServletResponse response) {
		//查询出运单
//		List<SignGoods> sign = signGoodsService.SignGoodslist(activityAudit.getId());
//		List<SellerOutInfo> soList=new ArrayList<SellerOutInfo>();
//		List<Activityorder> activityOrderList = activityorderService.findActivityOrder(activityAudit.getId());
//		for (Activityorder activityorder : activityOrderList) {
//			String orderid = activityorder.getOrderid();
//			SellerOutInfo sellerOutInfo = sellerOutInfoService.getOrderByOutNo(orderid);
//			soList.add(sellerOutInfo);
//		}
//		//查询出奖品
//		List<TurntableRaffle> turn = turntableRaffleService.TurntableRaffleList(activityAudit.getId());
//		model.addAttribute(activityAudit);
//		request.setAttribute("sign", soList);
//		request.setAttribute("turn", turn);
		String id = activityAudit.getId();
		String[] split = id.split(",");
		for (int i = 0; i < split.length; i++) {
			if(split[i]!=""){
				id=split[i];
			}
		}
		System.out.println("活动的ID**********>"+id);
		List<SignGoods> sign = signGoodsService.SignGoodslistF(id);
		//查询出奖品
		List<TurntableRaffle> turn = turntableRaffleService.TurntableRaffleList(id);
		//奖品类型 1卡券商品 2积分商品 3物流商品 
		for (TurntableRaffle turntableRaffle : turn) {
			String prizeType = turntableRaffle.getPrizeType();
			if("1".equals(prizeType)){
				turntableRaffle.setPrizeType("卡券商品");
			}else if("2".equals(prizeType)){
				turntableRaffle.setPrizeType("积分商品");
			}else if("3".equals(prizeType)){
				turntableRaffle.setPrizeType("物流商品 ");
			}
		}
		activityAudit = activityAuditService.get(id);
		model.addAttribute(activityAudit);
		request.setAttribute("sign", sign);
		request.setAttribute("turn", turn);
		return "modules/activity/activityaudit/activityAuditStatement.jsp";
	}
	//保存审核信息
	@RequestMapping(value = "saveActivityAudit")
	public void saveActivityAudit(ActivityAudit activityAudit, Model model, HttpServletRequest request, HttpServletResponse response) {
		String Remarks = request.getParameter("Remarks");
		String activityAuditId = request.getParameter("activityAuditId");
		String state = request.getParameter("state");
		//状态  1待审批   2已通过  3未通过  4已撤销
		activityAudit.setExamineUser(UserUtils.getUser().getId());
		activityAudit.setRemarks(Remarks);
		activityAudit.setId(activityAuditId);
		activityAudit.setState(state);
		activityAuditService.updateState(activityAudit);
		List<Activityorder> findActivityOrder = activityorderService.findActivityOrder(activityAuditId);
		//如果将驳回的活动 将相关联的活动删除
		if(state.equals("3")){
			for (Activityorder activityorder : findActivityOrder) {
				activityorder.setId(activityorder.getId());
				//0是活动取消  1活动进行
				activityorder.setState("0");
				activityorderService.save(activityorder);
			}
		}else if(state.equals("2")){
			for (Activityorder activityorder : findActivityOrder) {
				activityorder.setId(activityorder.getId());
				//0是活动取消  1活动进行
				activityorder.setState("1");
				activityorderService.save(activityorder);
			}
		}
		renderString(response,"保存成功");
	}
}