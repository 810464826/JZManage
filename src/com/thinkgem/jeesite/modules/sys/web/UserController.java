/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.exportcompany.entity.Exportcompany;
import com.thinkgem.jeesite.modules.exportcompany.service.ExportcompanyService;
import com.thinkgem.jeesite.modules.exportuser.entity.Exportuser;
import com.thinkgem.jeesite.modules.exportuser.service.ExportuserService;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.RoleService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.AddressUtil;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.userrole.entity.SysUserRole;
import com.thinkgem.jeesite.modules.userrole.service.SysUserRoleService;

/**
 * 用户Controller
 * 
 * @author ThinkGem
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/user")
public class UserController extends BaseController {
	// 这里是保存用户的信息
	@Autowired
	private SystemService systemService;
	// 这里是保存经销商的信息
	@Autowired
	private OfficeService officeService;

	@Autowired
	private ExportuserService exportuserService;
	
	@Autowired
	private ExportcompanyService exportcompanyService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private SysUserRoleService userRoleService;

	@Autowired
	private RoleService roleService;

	@ModelAttribute
	public User get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return systemService.getUser(id);
		} else {
			return new User();
		}
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = { "index" })
	public String index(User user, Model model) {
		return "modules/sys/userIndex.jsp";
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = { "list", "" })
	public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
		model.addAttribute("page", page);
		return "modules/sys/userList.jsp";
	}

	@ResponseBody
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = { "listData" })
	public Page<User> listData(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
		return page;
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "form")
	public String form(User user, Model model) {
		if (user.getCompany() == null || user.getCompany().getId() == null) {
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice() == null || user.getOffice().getId() == null) {
			user.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("user", user);
		model.addAttribute("allRoles", systemService.findAllRole());
		return "modules/sys/userForm.jsp";
	}

	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "save")
	public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		String companyId = request.getParameter("company.id");
		String roles = request.getParameter("roles");
		String role="";
		if(roles.equals("1")){
			role="平台商";
		}else if(roles.equals("2")){
			role="一级经销商";
		}
		else if(roles.equals("3")){
			role="二级经销商";
		}
		else if(roles.equals("4")){
			role="门店";
		}
		else if(roles.equals("5")){
			role="业务员";
		}
		Role roleByName = roleService.getRoleByName(role);
		List<String> roleIdList2 = new ArrayList<>();
		roleIdList2.add(roleByName.getId());
		user.setRoleIdList(roleIdList2);
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		Office officeById = officeService.getOfficeById(companyId);
		user.setCompany(officeById);
		user.setOffice(officeById);
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(user.getNewPassword())) {
			user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
		}
		if (!beanValidator(model, user)) {
			return form(user, model);
		}
		if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))) {
			addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
			return form(user, model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = user.getRoleIdList();
		List<Role> findAllRole = systemService.findAllRole();
		for (Role r : findAllRole) {
			if (roleIdList.contains(r.getId())) {
				roleList.add(r);
			}
		}
		user.setRoleList(roleList);
		user.setMobile(user.getPhone());
		System.out.println(user);
		//经销商类型 0系统管理员 1平台商 2一级经销商 3二级经销商 4门店
		user.setUserType(roles);
		// 保存用户信息
		systemService.saveUser(user);
		String userid = user.getId();
		//默认一个空的  如果后边查找到了则覆盖
		Exportuser exU=new Exportuser();
		//通过userId查找导出的用户
		Exportuser eu = exportuserService.findUserByUserId(userid);
		if(eu!=null){
			exU=eu;
		}
		// 添加用户的时候添加 保存 ExportUser
		// eu.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		Office office = officeService.get(companyId);
		// 地址
		String address = office.getAddress();
		String officeName = office.getName();
		Area area = office.getArea();
		exU.setCompanyallname(officeName);
		// eu.setCompanyjianname(officeName);
		// 这是区域
		exU.setCompanyaddress(area.getName());
		// 这是地址
		exU.setCaddress(address);
		exU.setPhone(user.getPhone());
		// eu.setGps("");
		exU.setName(user.getName());
		exU.setUserid(user.getId());
		exU.setUsertype(roles);
		// 这是添加用户 里边没有店名
		// eu.setShop(roleList.get(0).getName());
		String parentId = office.getParentId();
		if ((parentId != null)&&(!"0".equals(parentId))) {
			Office officeByParentId = officeService.get(parentId);
			exU.setParentcompany(officeByParentId.getName());
		} else {
			exU.setParentcompany("");
		}
		// eu.setRemake("");
		//外面加一个大的条件 如果是门店的用户就不需要添加到这个导出表里的
		//officeType为空的时候 则是添加的经销商 否则则是添加的门店的管理员
		String officeType = office.getType();
		if(("0".equals(officeType)) || (officeType==null)){
			if(eu!=null){
				exU.setId(eu.getId());
				exportuserService.save(exU);
			}else{
				exportuserService.save(exU);
			}
		}
		//如果是门店  则需要存入到导出终端的店的表里的
		else{
			Exportcompany ec=new Exportcompany();
			Exportcompany copmpany = exportcompanyService.findCopmpanyByUserId(userid);
			if(copmpany!=null){
				ec=copmpany;
			}
			String parentId2 = office.getParentId();
			Office office2 = officeService.getOfficeById(parentId2);
			
			ec.setCompanyallname(officeName);
			ec.setCompanyaddress(area.getName());
			ec.setCaddress(address);
			ec.setCompanytype(roles);
			String coreType = office.getCoreType();
			if("1".equals(coreType)){
				ec.setCoretype("核心店");
			}else if("2".equals(coreType)){
				ec.setCoretype("非核心店");
			}
			ec.setName(user.getName());
			ec.setPhone(user.getPhone());
			//门店类型 1餐饮店 2烟酒店 3商超店 4KA专卖店 5夜店
			String type = office.getType();
			if("1".equals(type)){
				ec.setCompanytype("餐饮店");
			}else if("2".equals(type)){
				ec.setCompanytype("烟酒店");
			}
			else if("3".equals(type)){
				ec.setCompanytype("商超店");
			}
			else if("4".equals(type)){
				ec.setCompanytype("KA专卖店");
			}
			else if("5".equals(type)){
				ec.setCompanytype("夜店");
			}
			ec.setParentcompany(office2.getName());
			ec.setOfficeid(office.getId());
			ec.setUserid(userid);
			if(copmpany!=null){
				ec.setId(copmpany.getId());
				exportcompanyService.save(ec);
			}else{
				exportcompanyService.save(ec);
			}
			
		}
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())) {
			UserUtils.clearCache();
			// UserUtils.getCacheMap().clear();
		}
		addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "delete")
	public String delete(User user, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		if (UserUtils.getUser().getId().equals(user.getId())) {
			addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
		} else if (User.isAdmin(user.getId())) {
			addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
		} else {
			String id = user.getId();
			systemService.deleteUser(user);
			// 删除用户的时候 删除ExportUser        根据userid
			exportuserService.deleteExportuserByUserId(id);
			exportcompanyService.deleteExportCompanyByUserId(id);
			addMessage(redirectAttributes, "删除用户成功");
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	/**
	 * 导出用户数据
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(Exportuser user, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		List<Exportuser> userList = exportuserService.findList(user);
		try {
			// // String fileName =
			// "经销商信息填写模版"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			// String fileName = "经销商信息.xlsx";
			// Page<Exportuser> page = exportuserService.findExportUser(new
			// Page<Exportuser>(request, response, -1), user);
			// new ExportExcel("经销商信息",
			// Exportuser.class).setDataList(page.getList()).write(response,
			// fileName).dispose();
			String fileName = "经销商信息.xlsx";
			List<String> headerList = Lists.newArrayList();
			headerList.add("经销商全称");
			headerList.add("所属区域");
			headerList.add("地址");
			headerList.add("经销商类型");
			headerList.add("联系人姓名");
			headerList.add("联系人手机号");
			headerList.add("上级经销商(选填)");
			headerList.add("备注(选填)");
			ExportExcel ee = new ExportExcel("经销商信息", headerList);
			for (Exportuser eu : userList) {
				Row row = ee.addRow();
				ee.addCell(row, 0, eu.getCompanyallname());
				ee.addCell(row, 1, eu.getCompanyaddress());
				ee.addCell(row, 2, eu.getCaddress());
				ee.addCell(row, 3, eu.getUsertype());
				ee.addCell(row, 4, eu.getName());
				ee.addCell(row, 5, eu.getPhone());
				ee.addCell(row, 6, eu.getParentcompany());
				ee.addCell(row, 7, eu.getRemakes());
			}
			System.err.println("！！！！！导出列表长度为：" + userList.size() + "！！！！！");
			ee.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	/**
	 * 这里是经销商导入      用户数据 新增：导入的时候验证为经销商全名和手机号一起的验证
	 * 
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Exportuser> list = ei.getDataList(Exportuser.class);
			for (Exportuser exportUser : list) {
				try {
					if ("true".equals(checkLoginName("", exportUser.getPhone()))) {
						Office office = new Office();
						// 导入用户的上级经销商
						String parentcompany = exportUser.getParentcompany();
						// 根据上级经销商的名称查询经销商Office的信息   1
						Office officeByName = officeService.getOfficeByName(parentcompany);
						if (officeByName != null) {
							office.setParent(officeByName);
						} else {
							// 这里如果上级经销商为空的时候 说明该经销商的上级经销商就是绵柔尖庄公司了   绵柔尖庄总公司   测试厂商
							String name = "测试厂商";
							office.setParent(officeService.getOfficeByName(name));
						}
						office.setSort(30);
						// 设置公司的名称
						office.setName(exportUser.getCompanyallname());
						// 区域
						String companyaddress = exportUser.getCompanyaddress();
						//2
						Area area = areaService.findAreaByName(companyaddress);
						office.setArea(area);
						// 地址
						office.setAddress(exportUser.getCaddress());
						//公司的电话  这里就和经销商绑定了
						office.setPhone(exportUser.getPhone());
						office.setAddressname(area.getName()+exportUser.getCaddress());
						//
						Map<String, Double> lngAndLat = AddressUtil.getLngAndLat(area.getName()+exportUser.getCaddress());
						Double lng = lngAndLat.get("lng");
						Double lat = lngAndLat.get("lat");
						office.setLongitude(lng+"");
						office.setLatitude(lat+"");
						office.setUseable("1");
						BeanValidators.validateWithException(validator, office);
						// 先查询该经销商是否存在 如果存在就不需要保存了 这是测试的时候做的 上线了就直接保存了 因为名称可以相同的   3
						Office officeByName3 = officeService.getOfficeByName(exportUser.getCompanyallname());
						if (officeByName3 == null) {
							office.setType("0");
							//4
							officeService.save(office);
						} else {
							String address = officeByName3.getAddress();
							//如果有同名，再判断地址是不是一样 地址不一样 才保存该office
							if(!address.equals(exportUser.getCaddress())){
								office.setType("0");
								//这里保存了经销商
								officeService.save(office);
							}
							System.err.println(officeByName3+"该经销商已经存在！");
						}
						// 经销商类型userType 0系统管理员 1平台商 2一级分销商 3二级分销商 4门店
						User user = new User();
						// 根据导入用户的经销商的名称去获取该office
						//shengyue
						Office officeByName2 = officeService.getOfficeByName(exportUser.getCompanyallname());
						String userid = UUID.randomUUID().toString().replaceAll("-", "");
						user.setId(userid);
						user.setCompany(officeByName2);
						user.setOffice(officeByName2);
						user.setLoginName(exportUser.getPhone());
						user.setPassword(SystemService.entryptPassword("123456"));
						user.setName(exportUser.getName());
						user.setPhone(exportUser.getPhone());
						user.setMobile(exportUser.getPhone());
						user.setDelFlag("0");
						// 这里是设置当前登录的用户
						User user2 = UserUtils.getUser();
						user.setUpdateBy(user2);
						user.setUpdateDate(new Date());
						user.setCreateDate(new Date());
						user.setCreateBy(user2);
						BeanValidators.validateWithException(validator, user);
						// 保存用户信息   5
						User userByLoginName = systemService.getUserByLoginName(exportUser.getPhone());
						if (userByLoginName == null) {
							// 经销商类型userType 0系统管理员 1平台商 2一级分销商 3二级分销商 4门店
							String userType = exportUser.getUsertype();
							String condtion = "";
							if(userType.equals("平台商")){
								user.setUserType("1");
								condtion= "平台商";
								officeByName2.setRoletype("1");
							}else if(userType.equals("一级经销商")){
								user.setUserType("2");
								condtion= "一级经销商";
								officeByName2.setRoletype("2");
							}else if(userType.equals("二级经销商")){
								user.setUserType("3");
								condtion= "二级经销商";
								officeByName2.setRoletype("3");
							}
							//6
							systemService.save(user);
							officeByName2.setId(officeByName2.getId());
							Map<String, Double> lngAndLat1 = AddressUtil.getLngAndLat(officeByName2.getArea().getName()+officeByName2.getAddress());
							Double lng1 = lngAndLat1.get("lng");
							Double lat1 = lngAndLat1.get("lat");
							office.setLongitude(lng1+"");
							office.setLatitude(lat1+"");
							officeService.save(officeByName2);
							//7
							Role role = roleService.getRoleByName(condtion);
							SysUserRole sysUserRole = new SysUserRole();
							sysUserRole.setUser(user);
							sysUserRole.setRoleId(role.getId());
							userRoleService.save(sysUserRole);
						} else {
							System.err.println("该用户已经存在！");
						}

						if (officeByName3 == null || userByLoginName == null) {
							successNum++;
							// 保存导入导出用户的信息
							exportUser.setUserid(userid);
							exportuserService.save(exportUser);
						}
					} else {
						failureMsg.append("<br/>登录名 " + exportUser.getPhone() + " 已存在; ");
						failureNum++;
					}
				} catch (ConstraintViolationException ex) {
					failureMsg.append("<br/>登录名 " + exportUser.getPhone() + " 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/>登录名 " + exportUser.getPhone() + " 导入失败：" + ex.getMessage());
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条经销商，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条经销商" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	/**
	 * 下载导入经销商信息填写模版
	 * 
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "经销商信息填写模版.xlsx";
			List<User> list = Lists.newArrayList();
			// list.add(UserUtils.getUser());
			// 第三个参数设置为“2”表示输出为导入模板（1:导出数据；2：导入模板）
			new ExportExcel("经销商信息填写模版", Exportuser.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	/**
	 * 验证登录名是否有效
	 * 
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName != null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName != null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}

	/**
	 * 用户信息显示及保存
	 * 
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "info")
	public String info(User user, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getName())) {
			if (Global.isDemoMode()) {
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userInfo.jsp";
			}
			currentUser.setEmail(user.getEmail());
			currentUser.setPhone(user.getPhone());
			currentUser.setMobile(user.getMobile());
			currentUser.setRemarks(user.getRemarks());
			currentUser.setPhoto(user.getPhoto());
			systemService.updateUserInfo(currentUser);
			model.addAttribute("message", "保存用户信息成功");
		}
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "modules/sys/userInfo.jsp";
	}

	/**
	 * 返回用户信息
	 * 
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "infoData")
	public User infoData() {
		return UserUtils.getUser();
	}

	/**
	 * 修改个人用户密码
	 * 
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "modifyPwd")
	public String modifyPwd(String oldPassword, String newPassword, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)) {
			if (Global.isDemoMode()) {
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userModifyPwd";
			}
			if (SystemService.validatePassword(oldPassword, user.getPassword())) {
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
				model.addAttribute("message", "修改密码成功");
			} else {
				model.addAttribute("message", "修改密码失败，旧密码错误");
			}
		}
		model.addAttribute("user", user);
		return "modules/sys/userModifyPwd.jsp";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required = false) String officeId,
			HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> list = systemService.findUserByOfficeId(officeId);
		for (int i = 0; i < list.size(); i++) {
			User e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_" + e.getId());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}

	// @InitBinder
	// public void initBinder(WebDataBinder b) {
	// b.registerCustomEditor(List.class, "roleList", new
	// PropertyEditorSupport(){
	// @Autowired
	// private SystemService systemService;
	// @Override
	// public void setAsText(String text) throws IllegalArgumentException {
	// String[] ids = StringUtils.split(text, ",");
	// List<Role> roles = new ArrayList<Role>();
	// for (String id : ids) {
	// Role role = systemService.getRole(Long.valueOf(id));
	// roles.add(role);
	// }
	// setValue(roles);
	// }
	// @Override
	// public String getAsText() {
	// return Collections3.extractToString((List) getValue(), "id", ",");
	// }
	// });
	// }
}
