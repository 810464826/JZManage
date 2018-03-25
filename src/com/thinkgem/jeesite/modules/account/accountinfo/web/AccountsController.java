/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.account.accountinfo.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.thinkgem.jeesite.modules.account.accountinfo.entity.Accounts;
import com.thinkgem.jeesite.modules.account.accountinfo.service.AccountsService;
import com.thinkgem.jeesite.modules.cancel.localcancel.entity.LocalCancel;
import com.thinkgem.jeesite.modules.cancel.localcancel.service.LocalCancelService;
import com.thinkgem.jeesite.modules.salesman.entity.Salesman;
import com.thinkgem.jeesite.modules.salesman.service.SalesmanService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.wx.wx_config.entity.WxConfig;
import com.thinkgem.jeesite.modules.wx.wx_config.service.WxConfigService;
/**
 * 对账管理Controller
 * @author tanghaobo
 * @version 2017-06-27
 */
@Controller
@RequestMapping(value = "${adminPath}/accountinfo/accounts")
public class AccountsController extends BaseController {
	private final Logger log = LoggerFactory.getLogger(AccountsController.class);
	@Autowired
	private AccountsService accountsService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private LocalCancelService localCancelService;
	@Autowired
	private WxConfigService wxConfigService;
	@Autowired
	private SalesmanService salesmanService;
	@ModelAttribute
	public Accounts get(@RequestParam(required=false) String id) {
		Accounts entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = accountsService.get(id);
		}
		if (entity == null){
			entity = new Accounts();
		}
		return entity;
	}
	
	@RequiresPermissions("accountinfo:accounts:view")
	@RequestMapping(value = {"list", ""})
	public String list(Accounts accounts, HttpServletRequest request, HttpServletResponse response, Model model) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		if(startTime != null && endTime != null){
			try {
				accounts.setStartTime(sdf.parse(startTime));
				accounts.setEndTime(sdf.parse(endTime));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		User user = UserUtils.getUser();
		//说明当前登录人员是超级管理员
		if(!user.getId().equals("1")){
			accounts.setAccountsUserId(user.getId());
		}
		Page<Accounts> page = accountsService.findPage(new Page<Accounts>(request, response), accounts); 
		model.addAttribute("page", page);
		log.info("**********>accounts:"+accounts);
		log.info("**********>page:"+page);
		return "modules/account/accountinfo/accountsList.jsp";
	}

	@RequiresPermissions("accountinfo:accounts:view")
	@RequestMapping(value = "form")
	public String form(Accounts accounts, Model model) {
		//获取当前登陆用户的下级，判断是否本地核销中当前人下级
		User user = UserUtils.getUser();
		log.info("**********>user:"+user);
		//Office currentOffice = officeService.get(user.getOffice());
		//当前人员的下级经销商
		List<Office> subordinateOffice = officeService.officeByParent(user.getOffice().getId());
		log.info("**********>subordinateOffice:"+subordinateOffice);
		List<LocalCancel> localList = new ArrayList<LocalCancel>();
		log.info("**********>localList:"+localList);
		for(Office ice : subordinateOffice){
			//查看当前下级经销商是否有核销历史
			List<LocalCancel> local =  localCancelService.QueryLocalCancelUser(ice.getId());
			if(local != null){
				for(LocalCancel can : local){
					localList.add(can);
				}
			}
			
		}
		//查询当前登录人的所有业务员
		Office office = user.getOffice();
		List<Salesman> findSalesManByOfficeId = salesmanService.findSalesManByOfficeId(office.getId());
		model.addAttribute("salesList", findSalesManByOfficeId);
		model.addAttribute("localList", localList);
		model.addAttribute("accounts", accounts);
		return "modules/account/accountinfo/accountsForm.jsp";
	}

	@RequiresPermissions("accountinfo:accounts:edit")
	@RequestMapping(value = "save")
	public String save(Accounts accounts, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, accounts)){
			return form(accounts, model);
		}
		/*SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		accounts.setAccountsTime(sdf.format(new Date()));
		accountsService.save(accounts);*/
		addMessage(redirectAttributes, "保存对账管理成功");
		return "redirect:"+Global.getAdminPath()+"/accountinfo/accounts/?repage";
	}
	
	@RequiresPermissions("accountinfo:accounts:edit")
	@RequestMapping(value = "search")
	public String search(Accounts accounts, Model model,HttpServletRequest request, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		if (!beanValidator(model, accounts)){
			return form(accounts, model);
		}
		User user =  UserUtils.getUser();
		String companyName = request.getParameter("name");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		LocalCancel localCancel=new LocalCancel();
		//这里查询本地核销记录
		if((companyName!=null)&&(!"".equals(companyName))){
			// 根据经销商的名称查询经销商Office的信息
			Office officeByName = officeService.getOfficeByName(companyName);
			//当前登录用户的公司id
			String id = user.getOffice().getId();
			//查询的经销商的officeid
			String id1 = officeByName.getId();
			//查询的经销商的office父级id,
			String parentIds = officeByName.getParentIds();
			String[] ids = parentIds.split(",");
			boolean canLook = isCanLook(ids, id);
			boolean same = isSame(id, id1);
			if(canLook||same){
				localCancel.setCancelOffice(companyName);
			}else{
				//不是同一级 或者不是上级  查看不了的
				localCancel.setCancelOffice("不是同一级 或者不是上级  查看不了的");
			}
		}else{
			//没有输入查询的核销经销商
		}
		if((startTime != null &&!"".equals(startTime)) && (endTime != null&&!"".equals(endTime))){
			localCancel.setStartTime(startTime);
			localCancel.setEndTime(endTime);
		}
		List<LocalCancel> list=new ArrayList<>();
		list=localCancelService.findListByState(localCancel); 
		//这里需要过滤一下的如果是超级管理员就不需要过滤了
		if(!user.getId().equals("1")){
			//当前登录人的userid
			String userid = user.getId();
			List<LocalCancel> list1=new ArrayList<>();
			for (LocalCancel lc : list) {
				//如果核销记录中的核销用户的id和当前登录人的officeid相同的话就加进去
				if(lc.getCancelUser().equals(userid)){
					list1.add(lc);
				}
			}
			//重新给page设置新的list
			list=list1;
		}
		//这里就是所有的未核销的记录
		model.addAttribute("localList", list);
		model.addAttribute("accounts", accounts);
		return "modules/account/accountinfo/accountsForm.jsp";
	}
	
	@RequiresPermissions("accountinfo:accounts:edit")
	@RequestMapping(value = "lookInfo")
	public void lookInfo(Accounts accounts, Model model,HttpServletRequest request, RedirectAttributes redirectAttributes, HttpServletResponse response) {
		String localId = request.getParameter("localId");
		LocalCancel localCancel = localCancelService.get(localId);
		renderString(response,localCancel);
	}
	
	@RequiresPermissions("accountinfo:accounts:edit")
	@RequestMapping(value = "saveAcc")
	public String saveAcc(Accounts accounts, Model model,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		//获取当前登陆的下级，判断是否本地核销中当前人下级
		User user = UserUtils.getUser();
		//核销ID
		String localid = request.getParameter("loaclid");
		//备注   对账的详情
		String textarea = request.getParameter("textarea");
		//添加对账时选择的时间
		String selectTime = request.getParameter("selectTime");
		//对账的业务员id
		String valueId = request.getParameter("valueId");
		//对账业务员名称
		String selectedName = request.getParameter("selectedName");
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
		LocalCancel local = localCancelService.get(localid);
		accounts.setAccountsUserId(user.getId());
		accounts.setAccountsDistributorId(user.getOffice().getId());
		accounts.setAccountsTime(new Date());
		accounts.setState("0");
		accounts.setActivityId(local.getActivityId());
		accounts.setPrizeId(local.getPrizeId());
		accounts.setPrizeName(local.getPrizeName());
		accounts.setCancelNumber(local.getCancelNumber());
		accounts.setCancelId(localid);
		accounts.setSelecttime(selectTime);
		accounts.setSalesman(valueId);
		accounts.setSalesname(selectedName);
		accounts.setRemarks(textarea);
		accountsService.save(accounts);
		local.setCheckState("2");
		localCancelService.save(local);
		//发送对账通知
		try {
			WxConfig config = wxConfigService.get("d667c90d127443238e19a4acebeqweq");
			UrlController.getUrl("http://"+config.getValue()+"/JZWX/cancel/accountsInformation?localid="+localid+"");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addMessage(redirectAttributes, "保存对账管理成功");
		return "redirect:"+Global.getAdminPath()+"/accountinfo/accounts/?repage";
	}
	
	@RequiresPermissions("accountinfo:accounts:edit")
	@RequestMapping(value = "delete")
	public String delete(Accounts accounts, RedirectAttributes redirectAttributes) {
		accountsService.delete(accounts);
		addMessage(redirectAttributes, "删除对账管理成功");
		return "redirect:"+Global.getAdminPath()+"/accountinfo/accounts/?repage";
	}
	
	/**
	 * 
	 * @param ids  查询经销商的office父级ID
	 * @param id   当前登录用的的officeID
	 * @return   true 代表当前登录用户是查询的上级
	 */
	public boolean isCanLook(String[] ids,String id){
		for (int i = 0; i < ids.length; i++) {
			if(id.equals(ids[i])){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param id 当前登录用户的officeID
	 * @param id1 查询经销商的ID
	 * @return true 两个经销商是同一级的
	 */
	public boolean isSame(String id,String id1){
		if(id.equals(id1)){
			return true;
		}
		return false;
	}

}