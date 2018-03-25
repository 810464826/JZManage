/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.salesman.web;

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
import com.thinkgem.jeesite.modules.exportcompany.entity.Exportcompany;
import com.thinkgem.jeesite.modules.exportcompany.service.ExportcompanyService;
import com.thinkgem.jeesite.modules.salesman.entity.Salesman;
import com.thinkgem.jeesite.modules.salesman.service.SalesmanService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.RoleService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.userrole.entity.SysUserRole;
import com.thinkgem.jeesite.modules.userrole.service.SysUserRoleService;

/**
 * 业务员Controller
 * @author cxb
 * @version 2017-09-04
 */
@Controller
@RequestMapping(value = "${adminPath}/salesman/salesman")
public class SalesmanController extends BaseController {

	@Autowired
	private SalesmanService salesmanService;
	
	@Autowired
	private OfficeService officeService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private SysUserRoleService userRoleService;
	@Autowired
	private ExportcompanyService exportcompanyService;
	
	@ModelAttribute
	public Salesman get(@RequestParam(required=false) String id) {
		Salesman entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = salesmanService.get(id);
		}
		if (entity == null){
			entity = new Salesman();
		}
		return entity;
	}
	
	@RequiresPermissions("salesman:salesman:view")
	@RequestMapping(value = { "index" })
	public String index(Salesman salesman, Model model) {
		return "modules/salesman/salesmanIndex.jsp";
	}
	
	@RequiresPermissions("salesman:salesman:view")
	@RequestMapping(value = {"list"})
	public String list(Salesman salesman, HttpServletRequest request, HttpServletResponse response, Model model) {
		//这里的officeid是点击的office   数据库查询的是业务员所属的officeid
		User user = UserUtils.getUser();
		//如果当前登录的用户不是超级管理员  则只能查询当前登录人自己的业务员
		if(!"1".equals(user.getId())){
			salesman.setOfficeid(user.getOffice().getId());
			salesman.setOfficename(user.getOffice().getName());
		}
		Page<Salesman> page = salesmanService.findPage(new Page<Salesman>(request, response), salesman); 
		model.addAttribute("page", page);
		return "modules/salesman/salesmanList.jsp";
	}

	@RequiresPermissions("salesman:salesman:view")
	@RequestMapping(value = "form")
	public String form(Salesman salesman, Model model) {
		model.addAttribute("salesman", salesman);
		return "modules/salesman/salesmanForm.jsp";
	}

	@RequiresPermissions("salesman:salesman:edit")
	@RequestMapping(value = "save")
	public String save(Salesman salesman, Model model, RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		Office office2 = user.getOffice();
		//这是业务员管理的门店
		Office manageOffice = officeService.get(salesman.getManangeofficeid());
		salesman.setOfficeid(office2.getId());
		salesman.setManangeofficename(manageOffice.getName());
		salesman.setOfficename(office2.getName());
		if (!beanValidator(model, salesman)){
			return form(salesman, model);
		}
		//保存前先判断一下当前业务员是否存在
		String phone = salesman.getPhone();
		String name = salesman.getName();
		String id = salesman.getId();
		//根据名字和手机号查询业务员  如果有则代表是同一个人
		List<Salesman> findSalesManByPhone = salesmanService.findSalesManByPhone(phone);
		if(id!=null&&!"".equals(id)){
			salesman.setId(salesman.getId());
			salesmanService.save(salesman);
			//将该业务员的手机号user表里
			User user2 = systemService.getUser(id);
			user2.setName(name);
			user2.setPhone(phone);
			user2.setMobile(phone);
			user2.setLoginName(phone);
			systemService.updateUserInfo(user2);
		}
		if(findSalesManByPhone.size()==0&&(id==null||"".equals(id))){
			salesmanService.save(salesman);
			//保存业务员的时候  还要给当前业务员创建一个登陆的用户
			User userSales=new User();
			userSales.setId(salesman.getId());
			userSales.setCompany(office2);
			userSales.setOffice(office2);
			userSales.setLoginName(salesman.getPhone());
			userSales.setPassword(SystemService.entryptPassword("123456"));
			userSales.setName(salesman.getName());
			userSales.setPhone(salesman.getPhone());
			userSales.setMobile(salesman.getPhone());
			userSales.setDelFlag("0");
			userSales.setUpdateBy(user);
			userSales.setUpdateDate(new Date());
			userSales.setCreateDate(new Date());
			userSales.setCreateBy(user);
			String userSalesType = "业务员";
			userSales.setUserType("5");
			systemService.save(userSales);
			//这里保存业务员的角色
			Role role1 = roleService.getRoleByName(userSalesType);
			SysUserRole sysUserRole1 = new SysUserRole();
			sysUserRole1.setUser(userSales);
			sysUserRole1.setRoleId(role1.getId());
			userRoleService.save(sysUserRole1);
			addMessage(redirectAttributes, "保存业务员成功");
			//这里保存到导出终端表里
			Exportcompany ec=new Exportcompany();
			//这里是业务员管理的门店的信息
			ec.setCompanyallname(manageOffice.getName());
			ec.setCompanyaddress(manageOffice.getArea().getName());
			ec.setCaddress(manageOffice.getAddress());
			//门店类型 1餐饮店 2烟酒店 3商超店 4KA专卖店 5夜店
			String type = manageOffice.getType();
			if(type.equals("1")){
				ec.setCompanytype("餐饮店");
			}else if(type.equals("2")){
				ec.setCompanytype("烟酒店");
			}else if(type.equals("3")){
				ec.setCompanytype("商超店");
			}else if(type.equals("4")){
				ec.setCompanytype("KA专卖店");
			}else if(type.equals("5")){
				ec.setCompanytype("夜店");
			}
			//核心店 1核心 2非核心
			String coretype = manageOffice.getCoretype();
			if(coretype.equals("1")){
				ec.setCoretype("核心店");
			}else if(coretype.equals("2")){
				ec.setCoretype("非核心店");
			}
			ec.setName(user.getName());
			ec.setPhone(user.getPhone());
			ec.setOfficeid(office2.getId());
			ec.setParentcompany(office2.getName());
			ec.setSalesmanname(salesman.getName());
			ec.setSalesmanphone(salesman.getPhone());
			ec.setUserid(salesman.getId());
			exportcompanyService.save(ec);
		}else{
			System.out.println("该业务员已存在！");
		}
		return "redirect:"+Global.getAdminPath()+"/salesman/salesman/?repage";
	}
	
	@RequiresPermissions("salesman:salesman:edit")
	@RequestMapping(value = "delete")
	public String delete(Salesman salesman, RedirectAttributes redirectAttributes) {
		//删除业务员的时候 既要删除user表里的  Exportcompany的相关数据也要删除的
		String id = salesman.getId();
		systemService.deleteUserById(id);
		exportcompanyService.deleteExportCompanyByUserId(id);
		salesmanService.delete(salesman);
		addMessage(redirectAttributes, "删除业务员成功");
		return "redirect:"+Global.getAdminPath()+"/salesman/salesman/?repage";
	}

}