/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.accountsapply.web;

import java.text.SimpleDateFormat;
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
import com.thinkgem.jeesite.modules.accountsapply.entity.Accountsapply;
import com.thinkgem.jeesite.modules.accountsapply.service.AccountsapplyService;
import com.thinkgem.jeesite.modules.cancel.localcancel.entity.LocalCancel;
import com.thinkgem.jeesite.modules.cancel.localcancel.service.LocalCancelService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 对账申请Controller
 * @author cxb
 * @version 2017-09-25
 */
@Controller
@RequestMapping(value = "${adminPath}/accountsapply/accountsapply")
public class AccountsapplyController extends BaseController {

	@Autowired
	private AccountsapplyService accountsapplyService;
	@Autowired
	private LocalCancelService localCancelService;
	@ModelAttribute
	public Accountsapply get(@RequestParam(required=false) String id) {
		Accountsapply entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = accountsapplyService.get(id);
		}
		if (entity == null){
			entity = new Accountsapply();
		}
		return entity;
	}
	
	@RequiresPermissions("accountsapply:accountsapply:view")
	@RequestMapping(value = {"list", ""})
	public String list(Accountsapply accountsapply, HttpServletRequest request, HttpServletResponse response, Model model) {
		String applyusercode = UserUtils.getUser().getId();
		String result="";
		List<LocalCancel> queryLocalByCancelUser = localCancelService.QueryLocalByCancelUser(applyusercode);
		if(queryLocalByCancelUser.size()>0){
			result="true";
		}else{
			result="false";
		}
		if(!applyusercode.equals("1")){
			accountsapply.setApplyusercode(applyusercode);
		}
		Page<Accountsapply> page = accountsapplyService.findPage(new Page<Accountsapply>(request, response), accountsapply); 
		model.addAttribute("page", page);
		model.addAttribute("result", result);
		return "modules/accountsapply/accountsapplyList.jsp";
	}
	
	@RequiresPermissions("accountsapply:accountsapply:view")
	@RequestMapping(value = "form")
	public String form(Accountsapply accountsapply, Model model) {
		model.addAttribute("accountsapply", accountsapply);
		return "modules/accountsapply/accountsapplyForm.jsp";
	}

	/**
	 * 这里是默认保存对账申请
	 */
	@RequiresPermissions("accountsapply:accountsapply:edit")
	@RequestMapping(value = "save")
	public String save(Accountsapply accountsapply, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, accountsapply)){
			return form(accountsapply, model);
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
		User user = UserUtils.getUser();
		String id = user.getId();
		String name = user.getName();
		String applyTime = sdf.format(new Date());
		accountsapply.setApplytime(applyTime);
		accountsapply.setApplyusercode(id);
		accountsapply.setApplyusername(name);
		Office office = user.getOffice();
		accountsapply.setApplyoffice(office.getName());
		if(accountsapply.getState()==null||"".equals(accountsapply.getState())){
			//默认是待处理
			accountsapply.setState("1");
		}
		accountsapplyService.save(accountsapply);
		addMessage(redirectAttributes, "保存对账申请成功");
		return "redirect:"+Global.getAdminPath()+"/accountsapply/accountsapply/?repage";
	}
	
	/**
	 * 修改
	 */
	@RequiresPermissions("accountsapply:accountsapply:edit")
	@RequestMapping(value = "update")
	public String update(Accountsapply accountsapply, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, accountsapply)){
			return form(accountsapply, model);
		}
		accountsapplyService.save(accountsapply);
		addMessage(redirectAttributes, "保存对账申请成功");
		return "redirect:"+Global.getAdminPath()+"/accountsapply/accountsapply/?repage";
	}
	
	@RequiresPermissions("accountsapply:accountsapply:edit")
	@RequestMapping(value = "delete")
	public String delete(Accountsapply accountsapply, RedirectAttributes redirectAttributes) {
		accountsapplyService.delete(accountsapply);
		addMessage(redirectAttributes, "删除对账申请成功");
		return "redirect:"+Global.getAdminPath()+"/accountsapply/accountsapply/?repage";
	}

}