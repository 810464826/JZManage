/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.exportcompany.entity.Exportcompany;
import com.thinkgem.jeesite.modules.exportcompany.service.ExportcompanyService;
import com.thinkgem.jeesite.modules.exportuser.service.ExportuserService;
import com.thinkgem.jeesite.modules.salesman.entity.Salesman;
import com.thinkgem.jeesite.modules.salesman.service.SalesmanService;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.RoleService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.AddressUtil;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.userrole.entity.SysUserRole;
import com.thinkgem.jeesite.modules.userrole.service.SysUserRoleService;

/**
 * 机构Controller
 * @author ThinkGem
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/office")
public class OfficeController extends BaseController {

	// 这里是保存用户的信息
	@Autowired
	private SystemService systemService;
	@Autowired
	private SalesmanService salesmanService;
	// 这里是保存经销商的信息
	@Autowired
	private OfficeService officeService;

	@Autowired
	private ExportcompanyService exportcompanyService;
	@Autowired
	private ExportuserService exportuserService;


	@Autowired
	private AreaService areaService;
	@Autowired
	private SysUserRoleService userRoleService;

	@Autowired
	private RoleService roleService;
	
	@Autowired
//	private SalesmanService salesService;

	@ModelAttribute("office")
	public Office get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return officeService.get(id);
		}else{
			return new Office();
		}
	}

	//TODO 门店管理的分页  页面需要传值过来
	@RequestMapping(value = {"storeIndex"})
	public String storeIndex(Office office, Model model,HttpServletRequest request) {
        model.addAttribute("list", null);
		return "modules/sys/storeIndex.jsp";
	}
	
	/**
	 * 进入storelist页面
	 */
	@RequestMapping(value = {"storeList"})
	public String storeList(Office office, Model model,HttpServletRequest request) {
        model.addAttribute("list", null);
		return "modules/sys/storeList.jsp";
	}
	
	/**
	 * 
	 * loadStoreInfo  加载门店的信息分页
	 * @author 81046
	 * @date 2018年2月9日下午2:27:14
	 */
	@RequestMapping(value = {"loadStoreInfo"})
	public void loadStoreInfo(HttpServletRequest request, HttpServletResponse response){
		String pageNumbers = request.getParameter("pageNumber");
		String officeName = request.getParameter("officeName");
		String type = request.getParameter("type");
		if(type.equals("请选择")){
			type="";
		}else if(type.equals("餐饮店")){
			type="1";
		}else if(type.equals("烟酒店")){
			type="2";
		}else if(type.equals("商超店")){
			type="3";
		}else if(type.equals("KA专卖店")){
			type="4";
		}else if(type.equals("夜店")){
			type="5";
		}
		String coretype = request.getParameter("coretype");
		if(coretype.equals("请选择")){
			coretype="";
		}else if(coretype.equals("核心店")){
			coretype="1";
		}
		else if(coretype.equals("非核心店")){
			coretype="2";
		}
		int pageNumber = Integer.parseInt(pageNumbers);
		int startLine=0;
		int endLine=0;
		if(pageNumber==1){
			startLine=1;
			//测试的时候 只获取5条数据   后面改成30
			endLine=30;
		}else{
			startLine=(pageNumber-1)*30+1;
			endLine=pageNumber*30;
		}
		List<Office> storeByType = officeService.getStoreByType(officeName,type,coretype,startLine,endLine);
		//查询当前门店所有的数量   这里查询的是门店的数量  还是需要带上条件
		int storeByTypeCount = officeService.getStoreByTypeCount(officeName,type,coretype);
		Map<String,Object> map=new HashMap<>();
		map.put("list", storeByType);
		map.put("pageNumber", pageNumber);
		map.put("storeByTypeCount", storeByTypeCount);
		renderString(response, map);
	}
	
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {"storelistparent"})
	public String storelistparent(Office office, Model model,HttpServletRequest request) {
		List<Office> storeList =new ArrayList<Office>();
		//还需要根据传递过来的officeid查询他的下级的门店
		//这里的id不是officeid而是该公司的父级id
		String officeid = request.getParameter("officeid");
		//这里是该公司的父级的id集合
		String parentIds = request.getParameter("parentIds");
		System.out.println(officeid+parentIds);
		//这是进入渠道商户首页传递过来的标识
		String storeofficeid = request.getParameter("storeofficeid");
        model.addAttribute("list", storeList);
        model.addAttribute("officeid", officeid);
        model.addAttribute("parentIds", parentIds);
        model.addAttribute("storeofficeid", storeofficeid);
		return "modules/sys/storeList_parent.jsp";
	}
	
	/**
	 * 这是有节点的下面的门店的分页
	 */
	@RequestMapping(value = {"loadParentStoreInfo"})
	public void loadParentStoreInfo(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> map=new HashMap<>();
		String officeid = request.getParameter("officeid");
		//这里是该公司的父级的id集合
		String parentIds = request.getParameter("parentIds");
		System.out.println(officeid+parentIds);
		//这是进入渠道商户首页传递过来的标识
		String storeofficeid = request.getParameter("storeofficeid");
		System.out.println("");
		String pageNumbers = request.getParameter("pageNumber");
		int pageNumber = Integer.parseInt(pageNumbers);
		int startLine=0;
		int endLine=0;
		if(pageNumber==1){
			startLine=1;
			//测试的时候 只获取5条数据   后面改成30
			endLine=30;
		}else{
			startLine=(pageNumber-1)*30+1;
			endLine=pageNumber*30;
		}
		String officeName = request.getParameter("officeName");
		String type = request.getParameter("type");
		if(type.equals("请选择")){
			type="";
		}else if(type.equals("餐饮店")){
			type="1";
		}else if(type.equals("烟酒店")){
			type="2";
		}else if(type.equals("商超店")){
			type="3";
		}else if(type.equals("KA专卖店")){
			type="4";
		}else if(type.equals("夜店")){
			type="5";
		}
		String coretype = request.getParameter("coretype");
		if(coretype.equals("请选择")){
			coretype="";
		}else if(coretype.equals("核心店")){
			coretype="1";
		}
		else if(coretype.equals("非核心店")){
			coretype="2";
		}
		List<Office> storeByType=null;
		int storeByTypeCount=0;
		//index代表刚进页面的时候 查询出的数据      或者officeid为空的时候 也是查询所有的门店
		if("index".equals(storeofficeid)||"".equals(officeid)||officeid==null){
			storeByType= officeService.getStoreByType(officeName,type,coretype,startLine,endLine);
			//查询当前门店所有的数量   这里查询的是门店的数量  还是需要带上条件
			storeByTypeCount = officeService.getStoreByTypeCount(officeName,type,coretype);
		}
		//这里查询的是左边树形结构点击的节点对应下面的门店
		else{
			storeByType=officeService.getStoreByOfficeId(officeid,officeName,type,coretype,startLine,endLine);
			storeByTypeCount=officeService.getStoreByParentOfficeIdCount(officeid, officeName, type, coretype);
		}
		map.put("list", storeByType);
		map.put("pageNumber", pageNumber);
		map.put("storeByTypeCount", storeByTypeCount);
		renderString(response, map);
	}
	
	/**
	 * 这是没有节点的门店list   storenoparent
	 */
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {"storenoparent"})
	public String storenoparent(Office office, Model model,HttpServletRequest request) {
		List<Office> storeList =new ArrayList<Office>();
		//还需要根据传递过来的officeid查询他的下级的门店
		//这里的id不是officeid而是该公司的父级id
		String officeid = request.getParameter("officeid");
		//这里是该公司的父级的id集合
		String parentIds = request.getParameter("parentIds");
		System.out.println(officeid+parentIds);
		//这是进入渠道商户首页传递过来的标识
		String storeofficeid = request.getParameter("storeofficeid");
        model.addAttribute("list", storeList);
        model.addAttribute("officeid", officeid);
        model.addAttribute("parentIds", parentIds);
        model.addAttribute("storeofficeid", storeofficeid);
		return "modules/sys/storeList_noparent.jsp";
	}
	
	/**
	 * 加载没有节点的门店信息
	 * @author 81046
	 * @date 2018年2月24日上午9:45:48
	 */
	/**
	 * 这是有节点的下面的门店的分页
	 */
	@RequestMapping(value = {"loadNoParentStoreInfo"})
	public void loadNoParentStoreInfo(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> map=new HashMap<>();
		String officeid = request.getParameter("officeid");
		//这里是该公司的父级的id集合
		String parentIds = request.getParameter("parentIds");
		System.out.println(officeid+parentIds);
		//这是进入渠道商户首页传递过来的标识
		String storeofficeid = request.getParameter("storeofficeid");
		System.out.println("");
		String pageNumbers = request.getParameter("pageNumber");
		int pageNumber = Integer.parseInt(pageNumbers);
		int startLine=0;
		int endLine=0;
		if(pageNumber==1){
			startLine=1;
			//测试的时候 只获取5条数据   后面改成30
			endLine=30;
		}else{
			startLine=(pageNumber-1)*30+1;
			endLine=pageNumber*30;
		}
		String officeName = request.getParameter("officeName");
		String type = request.getParameter("type");
		if(type.equals("请选择")){
			type="";
		}else if(type.equals("餐饮店")){
			type="1";
		}else if(type.equals("烟酒店")){
			type="2";
		}else if(type.equals("商超店")){
			type="3";
		}else if(type.equals("KA专卖店")){
			type="4";
		}else if(type.equals("夜店")){
			type="5";
		}
		String coretype = request.getParameter("coretype");
		if(coretype.equals("请选择")){
			coretype="";
		}else if(coretype.equals("核心店")){
			coretype="1";
		}
		else if(coretype.equals("非核心店")){
			coretype="2";
		}
		List<Office> storeByType=null;
		int storeByTypeCount=0;
		//index代表刚进页面的时候 查询出的数据      或者officeid为空的时候 也是查询所有的门店
		if("index".equals(storeofficeid)||"".equals(officeid)||officeid==null){
			storeByType= officeService.getStoreByType(officeName,type,coretype,startLine,endLine);
			//查询当前门店所有的数量   这里查询的是门店的数量  还是需要带上条件
			storeByTypeCount = officeService.getStoreByTypeCount(officeName,type,coretype);
		}
		//这里查询的是左边树形结构点击的节点对应下面的门店
		else{
			storeByType=officeService.getNoParentStoreByOfficeId(officeid,officeName,type,coretype,startLine,endLine);
			storeByTypeCount=officeService.getStoreByNoParentOfficeIdCount(officeid, officeName, type, coretype);
		}
		map.put("list", storeByType);
		map.put("pageNumber", pageNumber);
		map.put("storeByTypeCount", storeByTypeCount);
		renderString(response, map);
	}
	
	//这里是渠道商户 点击的链接 进入渠道商
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {"index"})
	public String index(Office office, Model model) {
        model.addAttribute("list", null);
		return "modules/sys/officeIndex.jsp";
	}

	//TODO 渠道商户的分页  这里进来给空  加载分页的方法
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {"officeList"})
	public String officeList(Office office, Model model,HttpServletRequest request) {
        model.addAttribute("list", null);
		return "modules/sys/officeList.jsp";
	}
	
	/**
	 * 
	 * loadOfficeInfo  加载渠道商的信息分页
	 * @author 81046
	 * @date 2018年2月9日下午2:27:14
	 */
	@RequestMapping(value = {"loadOfficeInfo"})
	public void loadOfficeInfo(HttpServletRequest request, HttpServletResponse response){
		String pageNumbers = request.getParameter("pageNumber");
		String officeName = request.getParameter("officeName");
		String roletype = request.getParameter("type");
		if(roletype.equals("请选择")){
			roletype="";
		}else if(roletype.equals("平台商")){
			roletype="1";
		}else if(roletype.equals("一级经销商")){
			roletype="2";
		}else if(roletype.equals("二级经销商")){
			roletype="3";
		}
		int pageNumber = Integer.parseInt(pageNumbers);
		int startLine=0;
		int endLine=0;
		if(pageNumber==1){
			startLine=1;
			//测试的时候 只获取5条数据   后面改成30
			endLine=30;
		}else{
			startLine=(pageNumber-1)*30+1;
			endLine=pageNumber*30;
		}
		//查询所有的经销商
		List<Office> storeByType = officeService.getOfficeList(officeName,roletype,startLine,endLine);
		//查询当前经销商所有的数量   这里查询的是经销商的数量  还是需要带上条件
		int storeByTypeCount = officeService.getOfficeListCount(officeName,roletype);
		Map<String,Object> map=new HashMap<>();
		map.put("list", storeByType);
		map.put("pageNumber", pageNumber);
		map.put("storeByTypeCount", storeByTypeCount);
		renderString(response, map);
	}
	
	/**
	 * 有节点的渠道商 分页
	 */
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {"officelistparent"})
	public String officelistparent(Office office, Model model,HttpServletRequest request) {
		List<Office> officeList =new ArrayList<Office>();
		//还需要根据传递过来的officeid查询他的下级的门店
		//这里的id不是officeid而是该公司的父级id
		String officeid = request.getParameter("officeid");
		//这里是该公司的父级的id集合
		String parentIds = request.getParameter("parentIds");
		System.out.println(officeid+parentIds);
		//这是进入渠道商户首页传递过来的标识
		String storeofficeid = request.getParameter("storeofficeid");
        model.addAttribute("list", officeList);
        model.addAttribute("officeid", officeid);
        model.addAttribute("parentIds", parentIds);
        model.addAttribute("storeofficeid", storeofficeid);
		return "modules/sys/officeList_parent.jsp";
	}
	
	/**
	 * 有节点的加载渠道商的信息 分页
	 */
	@RequestMapping(value = {"loadParentOfficeInfo"})
	public void loadParentOfficeInfo(HttpServletRequest request, HttpServletResponse response){
		String officeid = request.getParameter("officeid");
		String name = request.getParameter("officeName");
		String roletype = request.getParameter("roleType");
		String pageNumbers = request.getParameter("pageNumber");
		int pageNumber = Integer.parseInt(pageNumbers);
		int startLine=0;
		int endLine=0;
		if(pageNumber==1){
			startLine=1;
			//测试的时候 只获取5条数据   后面改成30
			endLine=30;
		}else{
			startLine=(pageNumber-1)*30+1;
			endLine=pageNumber*30;
		}
		List<Office> officeList=null;
		int officeCount=0;
		//如果为空则加载全部
		if("index".equals(officeid)||"".equals(officeid)||officeid==null){
			//查询所有的经销商
			officeList = officeService.getOfficeList(name,roletype,startLine,endLine);
			//查询当前经销商所有的数量   这里查询的是经销商的数量  还是需要带上条件
			officeCount = officeService.getOfficeListCount(name,roletype);
		}
		else{
			//这里还是需要这三个参数 去查询
			officeList=officeService.getParentOfficeByOfficeId(officeid, name, roletype, startLine, endLine);
			officeCount=officeService.getOfficeParentOfficeIdCount(officeid, name, roletype);
		}
		Map<String,Object> map=new HashMap<>();
		map.put("list", officeList);
		map.put("pageNumber", pageNumber);
		map.put("storeByTypeCount", officeCount);
		renderString(response, map);
	}
	
	/**
	 * 没有节点的渠道商 分页
	 */
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {"officelistnoparent"})
	public String officelistnoparent(Office office, Model model,HttpServletRequest request) {
		List<Office> officeList =new ArrayList<Office>();
		//还需要根据传递过来的officeid查询他的下级的门店
		//这里的id不是officeid而是该公司的父级id
		String officeid = request.getParameter("officeid");
		//这里是该公司的父级的id集合
		String parentIds = request.getParameter("parentIds");
		System.out.println(officeid+parentIds);
		//这是进入渠道商户首页传递过来的标识
		String storeofficeid = request.getParameter("storeofficeid");
        model.addAttribute("list", officeList);
        model.addAttribute("officeid", officeid);
        model.addAttribute("parentIds", parentIds);
        model.addAttribute("storeofficeid", storeofficeid);
		return "modules/sys/officeList_noparent.jsp";
		
	}
	
	/**
	 * 没有节点的加载渠道商的信息 分页
	 */
	@RequestMapping(value = {"loadNoParentOfficeInfo"})
	public void loadNoParentOfficeInfo(HttpServletRequest request, HttpServletResponse response){
		String officeid = request.getParameter("officeid");
		String name = request.getParameter("officeName");
		String roletype = request.getParameter("roleType");
		String pageNumbers = request.getParameter("pageNumber");
		int pageNumber = Integer.parseInt(pageNumbers);
		int startLine=0;
		int endLine=0;
		if(pageNumber==1){
			startLine=1;
			//测试的时候 只获取5条数据   后面改成30
			endLine=30;
		}else{
			startLine=(pageNumber-1)*30+1;
			endLine=pageNumber*30;
		}
		List<Office> officeList=null;
		int officeCount=0;
		//如果为空则加载全部
		if("index".equals(officeid)||"".equals(officeid)||officeid==null){
			//查询所有的经销商
			officeList = officeService.getOfficeList(name,roletype,startLine,endLine);
			//查询当前经销商所有的数量   这里查询的是经销商的数量  还是需要带上条件
			officeCount = officeService.getOfficeListCount(name,roletype);
		}
		else{
			//这里还是需要这三个参数 去查询
			officeList=officeService.getNoParentOfficeByOfficeId(officeid, name, roletype, startLine, endLine);
			officeCount=officeService.getOfficeNoParentOfficeIdCount(officeid, name, roletype);
		}
		Map<String,Object> map=new HashMap<>();
		map.put("list", officeList);
		map.put("pageNumber", pageNumber);
		map.put("storeByTypeCount", officeCount);
		renderString(response, map);
	}
	
	
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {"list1"})
	public String list1(Office office, Model model,HttpServletRequest request) {
		String officeid = request.getParameter("officeid");
		List<Office> findList = new ArrayList<>();
		if((officeid==null||"".equals(officeid))){
			findList = officeService.findList(office);
		}else{
			findList=officeService.getOfficeByOfficeId(officeid);
		}
		List<Office> findList1 =new ArrayList<>();
		for (Office o : findList) {
			String roleType = o.getRoletype();
			if(roleType.equals("1")){
				o.setRoletype("平台商");
			}else if(roleType.equals("2")){
				o.setRoletype("一级经销商");
			}else if(roleType.equals("3")){
				o.setRoletype("二级经销商");
			}else if("4".equals(roleType)||roleType==null){
				o.setRoletype("门店");
			}
			findList1.add(o);
		}
        model.addAttribute("list", findList1);
		return "modules/sys/officeList.jsp";
	}
	
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {"list2"})
	public String list2(Office office, Model model,HttpServletRequest request) {
		String officeid = request.getParameter("officeid");
		List<Office> findList = new ArrayList<>();
		if((officeid==null||"".equals(officeid))){
			findList = officeService.findList(office);
		}else{
			findList=officeService.getOfficeByOfficeId1(officeid);
		}
		List<Office> findList1 =new ArrayList<>();
		for (Office o : findList) {
			String roleType = o.getRoletype();
			if(roleType.equals("1")){
				o.setRoletype("平台商");
			}else if(roleType.equals("2")){
				o.setRoletype("一级经销商");
			}else if(roleType.equals("3")){
				o.setRoletype("二级经销商");
			}else if("4".equals(roleType)||roleType==null){
				o.setRoletype("门店");
			}
			findList1.add(o);
		}
        model.addAttribute("list", findList1);
		return "modules/sys/officeList.jsp";
	}
	
	//这是else执行的方法
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {"storelist1"})
	public String storelist1(Office office, Model model,HttpServletRequest request) {
		//还需要根据传递过来的officeid查询他的下级的门店
		//这里的id不是officeid而是该公司的父级id
		String officeid = request.getParameter("officeid");
		//这里是该公司的父级的id集合
		String parentIds = request.getParameter("parentIds");
		System.out.println(officeid+parentIds);
		List<Office> storeList =new ArrayList<Office>();
		if("".equals(officeid)||officeid==null){
			//TODO
			storeList=officeService.getStoreByType("","","",1,2);
		}else{
			//查询的是门店了  根据传递过来的officeid查询所有parentsid里包含了该officeid的所有的office
			storeList= officeService.getStoreByOfficeId1(officeid);
		}
//		storeList=officeService.getStoreByType();
		List<Office> storeList1 =new ArrayList<Office>();
		for (Office o : storeList) {
			String type = o.getType();
			if (type.equals("0")) {
				o.setType("经销商");
			}else if (type.equals("1")) {
				o.setType("餐饮店");
			} else if (type.equals("2")) {
				o.setType("烟酒店");
			} else if (type.equals("3")) {
				o.setType("商超店");
			} else if (type.equals("4")) {
				o.setType("KA专卖店");
			} else if (type.equals("5")) {
				o.setType("夜店");
			}
			String coreType = o.getCoreType();
			if("1".equals(coreType)){
				o.setCoreType("核心店");
			}else if("2".equals(coreType)){
				o.setCoreType("非核心店");
			}else{
				o.setCoreType("经销商");
			}
			storeList1.add(o);
		}
        model.addAttribute("list", storeList1);
		return "modules/sys/storeList.jsp";
	}
	
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "form")
	public String form(Office office, Model model) {
		User user = UserUtils.getUser();
		
		if (office.getParent()==null || office.getParent().getId()==null){
			office.setParent(user.getOffice());
		}
		office.setParent(officeService.get(office.getParent().getId()));
		if (office.getArea()==null){
			office.setArea(user.getOffice().getArea());
		}
		// 自动获取排序号
		if (StringUtils.isBlank(office.getId())&&office.getParent()!=null){
			int size = 0;
			List<Office> list = officeService.findAll();
			for (int i=0; i<list.size(); i++){
				Office e = list.get(i);
				if (e.getParent()!=null && e.getParent().getId()!=null
						&& e.getParent().getId().equals(office.getParent().getId())){
					size++;
				}
			}
			office.setCode(office.getParent().getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size+1 : 1), 3, "0"));
		}
		model.addAttribute("office", office);
		return "modules/sys/officeForm.jsp";
	}
	
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "save")
	public String save(Office office, Model model,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/";
		}
		if (!beanValidator(model, office)){
			return form(office, model);
		}
		//这里添加office的时候经销商 
		office.setType("0");
		//修改经销商的时候 传递过来的类型
		String officeType = request.getParameter("officeType");
		System.out.println(officeType);
		office.setRoletype(officeType);
		String name = office.getArea().getName();
		String address = office.getAddress();
		String addr=name+address;
		office.setAddressname(addr);
		Map<String, Double> lngAndLat = AddressUtil.getLngAndLat(addr);
		Double lng = lngAndLat.get("lng");
		Double lat = lngAndLat.get("lat");
		office.setLongitude(lng+"");
		office.setLatitude(lat+"");
		officeService.save(office);
		if(office.getChildDeptList()!=null){
			Office childOffice = null;
			for(String id : office.getChildDeptList()){
				childOffice = new Office();
				childOffice.setName(DictUtils.getDictLabel(id, "sys_office_common", "未知"));
				childOffice.setParent(office);
				childOffice.setArea(office.getArea());
				childOffice.setType("2");
				childOffice.setGrade(String.valueOf(Integer.valueOf(office.getGrade())+1));
				childOffice.setUseable(Global.YES);
				officeService.save(childOffice);
			}
		}
		addMessage(redirectAttributes, "保存机构'" + office.getName() + "'成功");
		String id = "0".equals(office.getParentId()) ? "" : office.getParentId();
		return "redirect:" + adminPath + "/sys/office/list?id="+id+"&parentIds="+office.getParentIds();
	}
	
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "delete")
	public String delete(Office office, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/list";
		}
			//删除经销商的时候   他所属的用户都要删除  所属的业务员也要跟着删除  导出经销商表也要删除  导出终端也要删除
		    String id = office.getId();
		    //先判断删除的经销商是门店还是经销商
		    //如果是门店  只需要删除门店的信息  和门店老板的登录账号   导出终端也要删除
		    //如果是经销商 就要删除他和他的下级的 登录账号 和office的信息   相关的业务员也要删除  业务员相关的登录账号 和导出经销商表也要删除
		    //type 是0就是经销商  其他则为门店
		    //1,2,3,4,5
		    //删除3     1.删除3这个经销商      2.查找parentIds里所有包含3的经销商
	    	//查询所有的公司
	    	List<Office> allOffice = officeService.getAllOffice();
	    	for (int i=0;i<allOffice.size();i++) {
	    		//里边循环一下 看哪一个公司的父级的ids里面有上面删除的公司id
	    		//第一个公司的ids判断一下 
				String parentIds = allOffice.get(i).getParentIds();
				String[] split = parentIds.split(",");
				//这里循环判断一下这里的父级ids里面有没有上面的id
				// 1,2,3,4,5   删除3   循环ids   如果
				//先从ids数组里取出3和它后面的ids   
				//定义一个数组来接收
				String[] deletArr;
				int index=0;
				for(int j=0;j<split.length;j++){
					if(split[j].equals(id)){
						index=j;
					}
				}
				//这里是需要删除的经销商的ids   数组的截取
				if(index!=0){
					//这里的下标要-1
					deletArr=Arrays.copyOfRange(split, index, split.length);
					 //这里还要删除下级的经销商
					//还要删除相关的用户
					List<User> userList1 = systemService.findDeleteUserByOfficeId(allOffice.get(i).getId());
					if(userList1.size()!=0){
				    	//根据公司id删除相关的用户
						systemService.deleteUser(allOffice.get(i).getId());
				    }
					for (User user : userList1) {
						//删除导出用户表
						exportuserService.deleteExportuserByUserId(user.getId());
					}
					officeService.deleteOfficeById(allOffice.get(i).getId());
					for (String deleId : deletArr) {
						//这里是删除的公司
						Office officeById = officeService.getOfficeById(deleId);
						//这是该公司所属的用户
						List<User> userList = systemService.findDeleteUserByOfficeId(deleId);
						for (User user : userList) {
							//删除导出用户表
							exportuserService.deleteExportuserByUserId(user.getId());
						}
						if(userList.size()!=0){
					    	//根据公司id删除相关的用户
							systemService.deleteUser(deleId);
					    }
						//查询该公司所拥有的业务员
						List<Salesman> findSalesManByOfficeId = salesmanService.findSalesManByOfficeId(deleId);
						if(findSalesManByOfficeId.size()!=0){
//							for (Salesman salesman : findSalesManByOfficeId) {
//								//这里删除门店之后   
////								 salesService.delete(salesman);
//							}
						}
						//删除经销商
						if(officeById!=null){
							officeService.deleteOfficeById(officeById.getId());
						}
					}	
				}
				
	    	}
	    	 //这里还要删除下级的经销商
			//还要删除相关的用户
			List<User> userList1 = systemService.findDeleteUserByOfficeId(id);
			if(userList1.size()!=0){
		    	//根据公司id删除相关的用户
				systemService.deleteUser(id);
		    }
			for (User user : userList1) {
				//删除导出用户表
				exportuserService.deleteExportuserByUserId(user.getId());
			}
			officeService.deleteOfficeById(id);
			exportcompanyService.deleteExportCompanyByOfficeId(id);
			addMessage(redirectAttributes, "删除机构成功");
//			return "redirect:" + adminPath + "/sys/office/list?id="+office.getParentId()+"&parentIds="+office.getParentIds();
			return "redirect:" + adminPath + "/sys/office/list";
	}
	
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "deleteStore")
	public String deleteStore(Office office, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/list";
		}
			//删除经销商的时候   他所属的用户都要删除  所属的业务员也要跟着删除  导出经销商表也要删除  导出终端也要删除
		    String id = office.getId();
		    //先判断删除的经销商是门店还是经销商
		    //如果是门店  只需要删除门店的信息  和门店老板的登录账号   导出终端也要删除
		    //如果是经销商 就要删除他和他的下级的 登录账号 和office的信息   相关的业务员也要删除  业务员相关的登录账号 和导出经销商表也要删除
		    //type 是0就是经销商  其他则为门店
		    //1,2,3,4,5
		    //删除3     1.删除3这个经销商      2.查找parentIds里所有包含3的经销商
	    	//查询所有的公司
	    	List<Office> allOffice = officeService.getAllOffice();
	    	for (int i=0;i<allOffice.size();i++) {
	    		//里边循环一下 看哪一个公司的父级的ids里面有上面删除的公司id
	    		//第一个公司的ids判断一下 
				String parentIds = allOffice.get(i).getParentIds();
				String[] split = parentIds.split(",");
				//这里循环判断一下这里的父级ids里面有没有上面的id
				// 1,2,3,4,5   删除3   循环ids   如果
				//先从ids数组里取出3和它后面的ids   
				//定义一个数组来接收
				String[] deletArr;
				int index=0;
				for(int j=0;j<split.length;j++){
					if(split[j].equals(id)){
						index=j;
					}
				}
				//这里是需要删除的经销商的ids   数组的截取
				if(index!=0){
					//这里的下标要-1
					deletArr=Arrays.copyOfRange(split, index, split.length);
					 //这里还要删除下级的经销商
					//还要删除相关的用户
					List<User> userList1 = systemService.findDeleteUserByOfficeId(allOffice.get(i).getId());
					if(userList1.size()!=0){
				    	//根据公司id删除相关的用户
						systemService.deleteUser(allOffice.get(i).getId());
				    }
					for (User user : userList1) {
						//删除导出用户表
						exportuserService.deleteExportuserByUserId(user.getId());
					}
					officeService.deleteOfficeById(allOffice.get(i).getId());
					for (String deleId : deletArr) {
						//这里是删除的公司
						Office officeById = officeService.getOfficeById(deleId);
						//这是该公司所属的用户
						List<User> userList = systemService.findDeleteUserByOfficeId(deleId);
						for (User user : userList) {
							//删除导出用户表
							exportuserService.deleteExportuserByUserId(user.getId());
						}
						if(userList.size()!=0){
					    	//根据公司id删除相关的用户
							systemService.deleteUser(deleId);
					    }
						//查询该公司所拥有的业务员
						List<Salesman> findSalesManByOfficeId = salesmanService.findSalesManByOfficeId(deleId);
						if(findSalesManByOfficeId.size()!=0){
//							for (Salesman salesman : findSalesManByOfficeId) {
//								//这里删除门店之后   
////								 salesService.delete(salesman);
//							}
						}
						//删除经销商
						if(officeById!=null){
							officeService.deleteOfficeById(officeById.getId());
						}
					}	
				}
				
	    	}
	    	 //这里还要删除下级的经销商
			//还要删除相关的用户
			List<User> userList1 = systemService.findDeleteUserByOfficeId(id);
			if(userList1.size()!=0){
		    	//根据公司id删除相关的用户
				systemService.deleteUser(id);
		    }
			for (User user : userList1) {
				//删除导出用户表
				exportuserService.deleteExportuserByUserId(user.getId());
			}
			officeService.deleteOfficeById(id);
			exportcompanyService.deleteExportCompanyByOfficeId(id);
			addMessage(redirectAttributes, "删除机构成功");
//			return "redirect:" + adminPath + "/sys/office/list?id="+office.getParentId()+"&parentIds="+office.getParentIds();
			return "redirect:" + adminPath + "/sys/office/storeList?repage";
	}

	/**
	 * 获取机构JSON数据。
	 * @param extId 排除的ID
	 * @param type	类型（1：公司；2：部门/小组/其它：3：用户）
	 * @param grade 显示级别
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
			@RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = officeService.findList(isAll);
		for (int i=0; i<list.size(); i++){
				Office e = list.get(i);
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				if (type != null && "3".equals(type)){
					map.put("isParent", true);
				}
				mapList.add(map);
			
		}
		return mapList;
	}
	//进入页面的时候将该office的经纬度传递给前端
	@RequestMapping(value = "officePositioning")
	public String officePositioning(Office office, Model model,RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		String officeId = request.getParameter("officeId");
		Office office2 = officeService.get(officeId);
		String address = office2.getAddress();
		String name = office2.getArea().getName();
		office2.setAddressname(name+address);
		model.addAttribute("office",office2);
		return "modules/sys/officePositioning.jsp";
	}
	
	/**
	 * 保存地理位置
	 * @param office
	 * @param model
	 * @param redirectAttributes
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "savePosition")
	public String savePosition(Office office, Model model,RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		String lng=(String)request.getParameter("lng");
		String lat=(String) request.getParameter("lat");
		String province=(String) request.getParameter("province");
		Area area = areaService.findAreaByName(province);
		String address=(String) request.getParameter("address");
		System.out.println(province+address);
		String officeId=(String) request.getParameter("officeId");
		Office office2 = officeService.get(officeId);
		office2.setLatitude(lat);
		office2.setArea(area);
		office2.setAddress(address);
		office2.setLongitude(lng);
		office2.setId(officeId);
		office2.setAddressname(province+address);
		officeService.save(office2);
		return null;
	}
	
	/**
	 * 导出终端门店的数据
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(Exportcompany company, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		List<Exportcompany> companyList = exportcompanyService.findList(company);
		try {
			String fileName = "终端店信息.xlsx";
			List<String> headerList = Lists.newArrayList();
			headerList.add("终端店全称");
			headerList.add("所属区域");
			headerList.add("地址");
			headerList.add("终端店类型");
			headerList.add("核心店类型");
			headerList.add("联系人姓名");
			headerList.add("联系人手机号");
			headerList.add("上级经销商(选填)");
			headerList.add("所属业务员名称");
			headerList.add("业务员联系电话");
			headerList.add("备注(选填)");
			ExportExcel ee = new ExportExcel("终端店信息", headerList);
			for (Exportcompany eu : companyList) {
				Row row = ee.addRow();
				ee.addCell(row, 0, eu.getCompanyallname());
				ee.addCell(row, 1, eu.getCompanyaddress());
				ee.addCell(row, 2, eu.getCaddress());
				ee.addCell(row, 3, eu.getCompanytype());
				ee.addCell(row, 4, eu.getCoretype());
				ee.addCell(row, 5, eu.getName());
				ee.addCell(row, 6, eu.getPhone());
				ee.addCell(row, 7, eu.getParentcompany());
				ee.addCell(row, 8, eu.getSalesmanname());
				ee.addCell(row, 9, eu.getSalesmanphone());
				ee.addCell(row, 10, eu.getRemakes());
			}
			System.err.println("！！！！！导出列表长度为：" + companyList.size() + "！！！！！");
			ee.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	/**
	 * 这里是终端信息导入      
	 * 
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:office:edit")
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
			List<Exportcompany> list = ei.getDataList(Exportcompany.class);
			for (Exportcompany exportCompany : list) {
				try {
					if ("true".equals(checkLoginName("", exportCompany.getPhone()))) {
						Office office = new Office();
						// 导入用户的上级经销商
						String parentcompany = exportCompany.getParentcompany();
						// 根据上级经销商的名称查询经销商Office的信息
						Office officeByName = officeService.getOfficeByName(parentcompany);
						if (officeByName != null) {
							office.setParent(officeByName);
						} else {
							// 这里如果上级经销商为空的时候 说明该经销商的上级经销商就是绵柔尖庄公司了   测试厂商   绵柔尖庄总公司
							String name = "测试厂商";
							office.setParent(officeService.getOfficeByName(name));
						}
						office.setSort(30);
						// 设置公司的名称
						office.setName(exportCompany.getCompanyallname());
						// 区域
						String companyaddress = exportCompany.getCompanyaddress();
						Area area = areaService.findAreaByName(companyaddress);
						office.setArea(area);
						// 地址
						office.setAddress(exportCompany.getCaddress());
						//公司的电话  这里就和经销商绑定了
						office.setPhone(exportCompany.getPhone());
						office.setAddressname(area.getName()+exportCompany.getCaddress());
						Map<String, Double> lngAndLat = AddressUtil.getLngAndLat(area.getName()+exportCompany.getCaddress());
						Double lng = lngAndLat.get("lng");
						Double lat = lngAndLat.get("lat");
						office.setLongitude(lng+"");
						office.setLatitude(lat+"");
						office.setUseable("1");
						// 店铺类型type 1餐饮店 2烟酒店 3商超店 4KA专卖店 5夜场
						// 核心类型coreShop 1核心店 2非核心店
						String type = exportCompany.getCompanytype();
						if (type.equals("餐饮店")) {
							office.setType("1");
						} else if (type.equals("烟酒店")) {
							office.setType("2");
						} else if (type.equals("商超店")) {
							office.setType("3");
						} else if (type.equals("KA专卖店")) {
							office.setType("4");
						} else if (type.equals("夜店")) {
							office.setType("5");
						}
						// 核心类型coreShop 1核心店 2非核心店
						String coreShop = exportCompany.getCoretype();
						if (coreShop.equals("核心店")) {
							office.setCoreType("1");
						} else if (coreShop.equals("非核心店")) {
							office.setCoreType("2");
						}
						office.setRoletype("4");
						BeanValidators.validateWithException(validator, office);
						//TODO 先查询该经销商是否存在 如果存在就不需要保存了 这是测试的时候做的 上线了就直接保存了 因为名称可以相同的
						Office officeByName3 = officeService.getOfficeByName(exportCompany.getCompanyallname());
						if (officeByName3 == null) {
							//这里保存了门店终端
							officeService.save(office);
						} 
						else {
							String address = officeByName3.getAddress();
							//如果有同名，再判断地址是不是一样 地址不一样 才保存该office
							if(!address.equals(exportCompany.getCaddress())){
								//这里保存了门店终端
								officeService.save(office);
							}
							System.err.println(officeByName3+"该经销商已经存在！");
						}
						// 经销商类型userType 0系统管理员 1平台商 2一级分销商 3二级分销商 4门店
						Salesman salesman = new Salesman();
						// 根据导入用户的经销商的名称去获取该office
						Office officeByName2 = officeService.getOfficeByName(exportCompany.getCompanyallname());
						Office office1= officeService.getOfficeByName(office.getName());
//						salesman.setId(UUID.randomUUID().toString().replaceAll("-", ""));
						//这里是所属的经销商 
						salesman.setOfficeid(officeByName.getId());
						salesman.setOfficename(officeByName.getName());
						salesman.setName(exportCompany.getSalesmanname());
						salesman.setPhone(exportCompany.getSalesmanphone());
						salesman.setRemakes(exportCompany.getRemakes());
						//这是业务员管理的终端店
						salesman.setManangeofficeid(office1.getId());
						salesman.setManangeofficename(office.getName());
						BeanValidators.validateWithException(validator, salesman);
						// 保存用户信息
						User userByLoginName = systemService.getUserByLoginName(exportCompany.getPhone());
						//这里先验证一下 当前的业务员的手机和管理的门店是不是一样的来添加   如果手机号码和管理的门店是一样的就不用添加
						//先验证手机 是不是一致    再来验证手机和管理的门店是不是一致
						//这是同一个手机号的业务员
						String phone = exportCompany.getSalesmanphone();
						String manangeofficeid = office1.getId();
						User user = new User();
						// 根据导入用户的经销商的名称去获取该office
						String userid = UUID.randomUUID().toString().replaceAll("-", "");
						//这里的用户id就是业务员ID
						user.setId(userid);
						user.setCompany(officeByName2);
						user.setOffice(officeByName2);
						user.setLoginName(exportCompany.getPhone());
						user.setPassword(SystemService.entryptPassword("123456"));
						user.setName(exportCompany.getName());
						user.setPhone(exportCompany.getPhone());
						user.setMobile(exportCompany.getPhone());
						user.setDelFlag("0");
						// 这里是设置当前登录的用户
						User user2 = UserUtils.getUser();
						user.setUpdateBy(user2);
						user.setUpdateDate(new Date());
						user.setCreateDate(new Date());
						user.setCreateBy(user2);
						BeanValidators.validateWithException(validator, user);
//						List<Salesman> findSalesManByPhone = salesmanService.findSalesManByPhone(exportCompany.getSalesmanphone(),exportCompany.getName());
						List<Salesman> findSalesManByPhone = salesmanService.findSalesManByPhone(exportCompany.getSalesmanphone());
						//通过手机号查找业务员 不存在的时候，才添加业务员登录用户
						if(findSalesManByPhone.size()==0) {
							//上面保存的是门店的登录用户
							//还要保存业务员的登录用户信息
							User userSales=new User();
							//这里的用户id
							userSales.setId(UUID.randomUUID().toString().replaceAll("-", ""));
							userSales.setCompany(officeByName);
							userSales.setOffice(officeByName);
							userSales.setLoginName(exportCompany.getSalesmanphone());
							userSales.setPassword(SystemService.entryptPassword("123456"));
							userSales.setName(exportCompany.getSalesmanname());
							userSales.setPhone(exportCompany.getSalesmanphone());
							userSales.setMobile(exportCompany.getSalesmanphone());
							userSales.setDelFlag("0");
							userSales.setUpdateBy(user2);
							userSales.setUpdateDate(new Date());
							userSales.setCreateDate(new Date());
							userSales.setCreateBy(user2);
							String userSalesType = "业务员";
							userSales.setUserType("5");
							systemService.save(userSales);
							//这里保存业务员的角色
							Role role1 = roleService.getRoleByName(userSalesType);
							SysUserRole sysUserRole1 = new SysUserRole();
							sysUserRole1.setUser(userSales);
							sysUserRole1.setRoleId(role1.getId());
							userRoleService.save(sysUserRole1);
						} else {
							System.err.println("该用户已经存在！");
						}
						//这是同一个手机号和管理的同一个店 没有数据
						List<Salesman> findSalesManByPhoneManageOffice = salesmanService.findSalesManByPhoneManageOffice(phone, manangeofficeid);
						//通过判断这个条件来保存业务员
						if(findSalesManByPhoneManageOffice.size()==0 ){
							List<User> userByPhone = systemService.getUserByPhone(exportCompany.getSalesmanphone());
							if(userByPhone.size()!=0){
								User user3 = userByPhone.get(0);
								salesman.setUserId(user3.getId());
							}
							//保存业务员
							salesmanService.save(salesman);
						}else {
							System.err.println("该业务员已经存在！");
						}
						//这里是判断这个门店的登录用户是否存在
						List<User> userByPhone = systemService.getUserByPhone(exportCompany.getPhone());
						// 根据手机号   保存用户信息   这是同一个手机号的业务员
						if (userByPhone.size() == 0){
							// 经销商类型userType 0系统管理员 1平台商 2一级分销商 3二级分销商 4门店 5业务员
							String userType = "门店";
							user.setUserType("4");
							systemService.save(user);
							Role role = roleService.getRoleByName(userType);
							SysUserRole sysUserRole = new SysUserRole();
							sysUserRole.setUser(user);
							sysUserRole.setRoleId(role.getId());
							userRoleService.save(sysUserRole);
						}
						
						//这里导入的时候，将该用户信息也要存入到导出用户的信息里       *************待商榷
						if (officeByName3 == null || userByLoginName == null) {
							// 保存导入导出终端的信息
							exportCompany.setOfficeid(office.getId());
							exportCompany.setUserid(userid);
							exportcompanyService.save(exportCompany);
							successNum++;
						}
					} else {
						failureMsg.append("<br/>登录名 " + exportCompany.getPhone() + " 已存在; ");
						failureNum++;
					}
				} catch (ConstraintViolationException ex) {
					failureMsg.append("<br/>登录名 " + exportCompany.getPhone() + " 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/>登录名 " + exportCompany.getPhone() + " 导入失败：" + ex.getMessage());
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条终端信息，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条终端信息" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入终端信息失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/office/storeIndex?repage";
	}

	/**
	 * 下载导入经销商信息填写模版
	 * 
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "终端店信息填写模版.xlsx";
			List<Office> list = Lists.newArrayList();
			// list.add(UserUtils.getUser());
			// 第三个参数设置为“2”表示输出为导入模板（1:导出数据；2：导入模板）
			new ExportExcel("终端店信息填写模版", Exportcompany.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/office/list?repage";
	}

	
	/**
	 * 验证登录名是否有效
	 * 
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {

		if (loginName != null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName != null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}
	
	//门店添加/修改
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "storeAdd")
	public String storeAdd(Office office, Model model,RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		return "modules/sys/storeForm.jsp";
	}
	
	//门店添加/修改
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "storeUpdate")
	public String storeUpdate(Office office, Model model,RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("officeType", office.getType());
		request.setAttribute("officeCoreType", office.getCoreType());
		return "modules/sys/shopFormModify.jsp";
	}
	
	//门店添加存储
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "storeSave")
	public String storeSave(Office office, Model model,RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		System.out.println(office.getType());
		//核心店 1核心店 2非核心店
		String core=(String)request.getParameter("core");
		if(core.equals("核心店")){
			office.setCoreType("1");
		}else if(core.equals("非核心店")){
			office.setCoreType("2");
		}
		//门店类型 1餐饮店 2烟酒店 3商超店 4KA专卖店 5夜店
		String shopType=(String)request.getParameter("shopType");
		if(shopType.equals("餐饮店")){
			office.setType("1");
		}else if(shopType.equals("烟酒店")){
			office.setType("2");
		}else if(shopType.equals("商超店")){
			office.setType("3");
		}else if(shopType.equals("KA专卖店")){
			office.setType("4");
		}else if(shopType.equals("夜店")){
			office.setType("5");
		}
		office.setRoletype("4");
		String shopName=(String)request.getParameter("shopName");
		office.setName(shopName);
		//这里设置一个用户
		String name=(String)request.getParameter("name");
		String telphone=(String)request.getParameter("telphone");
		office.setPhone(telphone);
		String address = office.getAddress();
		String name2 = office.getArea().getName();
		office.setAddressname(name2+address);
		Map<String, Double> lngAndLat = AddressUtil.getLngAndLat(name2+address);
		Double lng = lngAndLat.get("lng");
		Double lat = lngAndLat.get("lat");
		office.setLongitude(lng+"");
		office.setLatitude(lat+"");
		officeService.save(office);
		User user =new User();
		user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		user.setCompany(office);
		user.setOffice(office);
		user.setLoginName(telphone);
		user.setPassword(SystemService.entryptPassword("123456"));
		user.setName(name);
		user.setPhone(telphone);
		user.setMobile(telphone);
		user.setDelFlag("0");
		// 这里是设置当前登录的用户
		User user2 = UserUtils.getUser();
		user.setUpdateBy(user2);
		user.setUpdateDate(new Date());
		user.setCreateDate(new Date());
		user.setCreateBy(user2);
		String condtion = "门店";
		//这是门店
		user.setUserType("4");
		systemService.save(user);
		Role role = roleService.getRoleByName(condtion);
		SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setUser(user);
		sysUserRole.setRoleId(role.getId());
		userRoleService.save(sysUserRole);
		model.addAttribute("list", officeService.findList(office));
		return "redirect:" + adminPath + "/sys/office/storeList?repage";
	}
	
	//门店添加存储
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "storeUpdateSave")
	public String storeUpdateSave(Office office, Model model,RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		//核心店 1核心店 2非核心店
		String core=(String)request.getParameter("core");
		/*office.setCoreType(core);*/
		if(core.equals("核心店")){
			office.setCoreType("1");
		}else if(core.equals("非核心店")){
			office.setCoreType("2");
		}
		//门店类型 1餐饮店 2烟酒店 3商超店 4KA专卖店 5夜店
		String shopType=(String)request.getParameter("shopType");
		/*office.setType(shopType);*/
		if(shopType.equals("餐饮店")){
			office.setType("1");
		}else if(shopType.equals("烟酒店")){
			office.setType("2");
		}else if(shopType.equals("商超店")){
			office.setType("3");
		}else if(shopType.equals("KA专卖店")){
			office.setType("4");
		}else if(shopType.equals("夜店")){
			office.setType("5");
		}
		String shopName=(String)request.getParameter("shopName");
		office.setName(shopName);
		String address = office.getAddress();
		String name = office.getArea().getName();
		office.setAddressname(name+address);
		Map<String, Double> lngAndLat = AddressUtil.getLngAndLat(name+address);
		Double lng = lngAndLat.get("lng");
		Double lat = lngAndLat.get("lat");
		office.setLongitude(lng+"");
		office.setLatitude(lat+"");
		office.setId(office.getId());
		officeService.save(office);
		model.addAttribute("list", officeService.findList(office));
		return "redirect:" + adminPath + "/sys/office/storeList?repage";
	}
}
