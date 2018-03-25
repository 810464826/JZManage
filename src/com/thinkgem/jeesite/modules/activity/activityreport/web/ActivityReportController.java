/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.activity.activityreport.web;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.util.json.JSONArray;
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
import com.thinkgem.jeesite.modules.activity.activityreport.entity.ActivityReport;
import com.thinkgem.jeesite.modules.activity.activityreport.service.ActivityReportService;
import com.thinkgem.jeesite.modules.activity.signgoods.entity.SellerCenterObj;
import com.thinkgem.jeesite.modules.activity.signgoods.entity.SellerOutInfo;
import com.thinkgem.jeesite.modules.activity.signgoods.entity.SignGoods;
import com.thinkgem.jeesite.modules.activity.signgoods.service.SellerOutInfoService;
import com.thinkgem.jeesite.modules.activity.signgoods.service.SignGoodsService;
import com.thinkgem.jeesite.modules.activityorder.entity.Activityorder;
import com.thinkgem.jeesite.modules.activityorder.service.ActivityorderService;
import com.thinkgem.jeesite.modules.prize.turntableraffle.entity.TurntableRaffle;
import com.thinkgem.jeesite.modules.prize.turntableraffle.service.TurntableRaffleService;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 活动报备Controller
 * @author tanghaobo
 * @version 2017-03-16
 */
@Controller
@RequestMapping(value = "${adminPath}/activityreport/activityReport")
public class ActivityReportController extends BaseController {

	@Autowired
	private ActivityReportService activityReportService;
	@Autowired
	private SignGoodsService signGoodsService;
	@Autowired
	private TurntableRaffleService turntableRaffleService;
	@Autowired
	private SellerOutInfoService sellerOutInfoService;
	@Autowired
	private ActivityorderService activityorderService;
	
	@ModelAttribute
	public ActivityReport get(@RequestParam(required=false) String id) {
		ActivityReport entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = activityReportService.get(id);
		}
		if (entity == null){
			entity = new ActivityReport();
		}
		return entity;
	}
	
	@ModelAttribute
	public SignGoods getsign(@RequestParam(required=false) String id) {
		SignGoods entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = signGoodsService.get(id);
		}
		if (entity == null){
			entity = new SignGoods();
		}
		return entity;
	}
	
	@RequiresPermissions("activityreport:activityReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(ActivityReport activityReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		String userId = UserUtils.getUser().getId();
		activityReport.setApplyUser(userId);
		Page<ActivityReport> page = activityReportService.findPage(new Page<ActivityReport>(request, response), activityReport); 
		model.addAttribute("page", page);
		return "modules/activity/activityreport/activityReportList.jsp";
	}

	@RequiresPermissions("activityreport:activityReport:view")
	@RequestMapping(value = "form")
	public String form(ActivityReport activityReport, Model model , HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("page", null);
		model.addAttribute("activityReport", activityReport);
		System.out.println("************>活动报备："+activityReport);
		return "modules/activity/activityreport/activityReportFormStart.jsp";
	}
	
	/**
	 * 
	 * loadSellerInfo  加载运单信息  一页显示30条
	 * @author 81046
	 * @date 2018年2月9日下午2:27:14
	 */
	@RequestMapping(value = {"loadSellerInfo"})
	public void loadSellerInfo(HttpServletRequest request, HttpServletResponse response){
		User user = UserUtils.getUser();
		List<SellerOutInfo> outList=null;
		String productname = request.getParameter("productName");
		String pageNumbers = request.getParameter("pageNumber");
		int pageNumber = Integer.parseInt(pageNumbers);
		String phone=user.getMobile();
		int startLine=0;
		int endLine=0;
		if(pageNumber==1){
			startLine=1;
			endLine=30;
		}else{
			startLine=(pageNumber-1)*30+1;
			endLine=pageNumber*30;
		}
		int sellInfoCount=0;
		//管理员
		if(user.getId().equals("1")){
			outList =sellerOutInfoService.getSellerOutInfoByProductName(productname,startLine,endLine);
			sellInfoCount = sellerOutInfoService.getSellerCountByProductName(productname);
		}
		//普通用户
		else{
			//大数据平台的运单
			outList = sellerOutInfoService.getSellerOutInfoByProductNamePhone(phone,productname,startLine,endLine);
			sellInfoCount = sellerOutInfoService.getSellerCountByProductNamePhone(productname,phone);
		}
		List<SellerCenterObj> sellerOutInfo = getSellerOutInfo(outList);
		Map<String,Object> map=new HashMap<>();
		map.put("outList", sellerOutInfo);
		map.put("pageNumber", pageNumber);
		map.put("sellInfoCount", sellInfoCount);
		renderString(response, map);
	}
	
	/**
	 * 这里传入集合处理一下  时间
	 */
	public List<SellerCenterObj> getSellerOutInfo(List<SellerOutInfo> list){
		List<SellerCenterObj> sellerList=new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (SellerOutInfo s : list) {
			SellerCenterObj sc=new SellerCenterObj();
			sc.setRecordId(s.getRecordId());
			sc.setOutNo(s.getOutNo());;
			sc.setProductName(s.getProductName());
			sc.setRecsellerName(s.getRecsellerName());
			Date outDate = s.getOutDate();
			String format = sdf.format(outDate);
			sc.setOutTime(format);
			String spec = s.getSpec();
			sc.setSpec(spec);
			String boxCount = s.getBoxCount();
			sc.setBoxCount(boxCount);
			sellerList.add(sc);
		}
		return sellerList;
	}

	@RequiresPermissions("activityreport:activityReport:edit")
	@RequestMapping(value = "save")
	public String save(ActivityReport activityReport, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, activityReport)){
			return form(activityReport, model,null,null);
		}
		activityReport.setApplyUser(UserUtils.getUser().getId());
		activityReport.setApplyofficeid(UserUtils.getUser().getOffice().getId());
		//状态  1待审批   2已通过  3未通过  4已撤销
		activityReport.setState("1");
		activityReportService.save(activityReport);
		addMessage(redirectAttributes, "保存活动报备成功");
		return "redirect:"+Global.getAdminPath()+"/activityreport/activityReport/?repage";
	}
	
	@RequestMapping(value = "saveActivity")
	public void saveActivity(ActivityReport activityReport, Model model,RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		//提交还是保存   btnSave=保存   btnSubmit=提交
//		String currentClickObj = request.getParameter("currentClickObj");
		//所选运单
		String waybillInfos = request.getParameter("waybillInfos");
		//所填写奖品
		String awardInfos = request.getParameter("awardInfos");
		//输入信息
//		String headerInfos = request.getParameter("headerInfos");
		String activityName = request.getParameter("activityName");
		String createTime = request.getParameter("createTime");
		String activityAddress = request.getParameter("activityAddress");
		String updateTime = request.getParameter("updateTime");
		ActivityReport activity  = new ActivityReport();
		activity.setActivityName(activityName);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
		if(createTime != null && updateTime != null){
			try {
				activity.setCreateTime(sdf.parse(createTime));
				activity.setUpdateTime(sdf.parse(updateTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		activity.setActivityAddress(activityAddress);
		activity.setState("1");
		activity.setApplyUser(UserUtils.getUser().getId());
		activity.setApplyofficeid(UserUtils.getUser().getOffice().getId());
		//保存活动成功后       返回活动报备ID
		String id = activityReportService.saveBackId(activity);
		User user =  UserUtils.getUser();
		Office office = user.getOffice();
		String address = office.getAddress();
		System.out.println("当前用户的地址***>"+address);
		/**
		 * 前端传递过来的是运单号的数组  循环数组 保存到数据库中
		 */
		String billNumbers = request.getParameter("recordIds");
		System.out.println("***运单号的数组为："+billNumbers);
		System.out.println("所选的运单是：*****>"+waybillInfos);
		String[] split = billNumbers.split(",");
		System.out.println("***运单号的数组为长度："+split.length);
		//记录运单JSON数组
		JSONArray waybillInfosjson;
		JSONArray awardInfosjson;
		try{
			waybillInfosjson = new JSONArray(waybillInfos);
			System.out.println("所选的运单长度是："+waybillInfosjson.length());
			for(int i=0 ; i < waybillInfosjson.length() ;i++){
				org.activiti.engine.impl.util.json.JSONObject signobjList = waybillInfosjson.getJSONObject(i);
				JSONObject signobject =JSONObject.fromObject(signobjList.toString());
				String time = signobject.get("createTime").toString();
				SignGoods sign = (SignGoods) JSONObject.toBean(signobject,SignGoods.class);
				Activityorder ao=new Activityorder();
				ao.setActivityid(id);
				//运单号
				ao.setOrderid(sign.getGoodsNumber());
				ao.setRecordid(sign.getRecordId());
				ao.setState("1");
				sign.setGoodsAddress(address);
				sign.setDisreibutorId(user.getId());
				sign.setCreateTime(sdf.format(new Date()));
				sign.setOutTime(time);
				System.out.println(sign.getCreateTime());
				sign.setState("3");
				sign.setActivityReportId(id);
				signGoodsService.save(sign);
				//保存活动和订单的关联
				activityorderService.save(ao);
				System.out.println("signGoods是***>"+sign);
				//保存成功了  将该运单的状态修改为1   activsign  0 可以用   1 不可以用
				sellerOutInfoService.updateSellerInfoOn(sign.getRecordId());
				System.out.println("保存成功了  将该运单的状态修改为1   activsign  0 可以用   1 不可以用");
			}
			awardInfosjson = new JSONArray(awardInfos);
			for(int i=0 ; i < awardInfosjson.length() ;i++){
				org.activiti.engine.impl.util.json.JSONObject prizeobjList = awardInfosjson.getJSONObject(i);
				JSONObject prizeobject =JSONObject.fromObject(prizeobjList.toString());
				TurntableRaffle turntable = (TurntableRaffle) JSONObject.toBean(prizeobject,TurntableRaffle.class);
				turntable.setActivityId(id);
				turntable.setCreateDate(new Date());
				//保存奖品设置
				turntableRaffleService.save(turntable);
			}
		}catch(JSONException e){
		}
		renderString(response,"保存成功");
	}
	
	
	//查看
	@RequiresPermissions("activityreport:activityReport:edit")
	@RequestMapping(value = "activityReportLook")
	public String activityReportLook(ActivityReport activityReport, Model model , HttpServletRequest request, HttpServletResponse response) {
		String id = activityReport.getId();
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
		activityReport = activityReportService.get(id);
		model.addAttribute(activityReport);
		request.setAttribute("sign", sign);
		request.setAttribute("turn", turn);
		return "modules/activity/activityreport/activityReportLook.jsp";
	}
	
	
	//访问修改页面
	@RequestMapping(value = "activityReportModify")
	public String activityReportModify(ActivityReport activityReport, Model model , HttpServletRequest request, HttpServletResponse response) {
		String id = activityReport.getId();
		String[] split = id.split(",");
		for (int i = 0; i < split.length; i++) {
			if(split[i]!=""){
				id=split[i];
			}
		}
		System.out.println("活动的ID**********>"+id);
		//查询活动相关联的运单
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
		activityReport = activityReportService.get(id);
		model.addAttribute(activityReport);
		request.setAttribute("sign", sign);
		request.setAttribute("turn", turn);
		return "modules/activity/activityreport/activityReportModify.jsp";
	}
	
	
	@RequiresPermissions("activityreport:activityReport:edit")
	@RequestMapping(value = "delete")
	public String delete(ActivityReport activityReport, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String id = request.getParameter("id");
		String[] split = id.split(",");
		for (int i = 0; i < split.length; i++) {
			if(split[i]!=""){
				id=split[i];
			}
		}
		System.out.println("活动的ID**********>"+id);
		activityReport.setId(id);
		activityReportService.delete(activityReport);
		List<Activityorder> findActivityOrder = activityorderService.findActivityOrder(id);
		//如果将驳回的活动 将相关联的活动删除
		for (Activityorder activityorder : findActivityOrder) {
			//0是活动取消  1活动进行 3活动删除了
			activityorder.setId(activityorder.getId());
			activityorder.setState("3");
			activityorderService.save(activityorder);
			//保存成功了  将该运单的状态修改为1   activsign  0 可以用   1 不可以用
			sellerOutInfoService.updateSellerInfoOut(activityorder.getRecordid());
			System.out.println("保存成功了  将该运单的状态修改为1   activsign  0 可以用   1 不可以用");
		}
		addMessage(redirectAttributes, "删除活动报备成功");
		return "redirect:"+Global.getAdminPath()+"/activityreport/activityReport/?repage";
	}
	
	/**
	 * 这是修改活动报备之后 保存的方法
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("activityreport:activityReport:edit")
	@RequestMapping(value = "editSave")
	public String editSave(RedirectAttributes redirectAttributes,HttpServletRequest request,  Model model, HttpServletResponse response) {
		String activityId = request.getParameter("activityId");
		String awardArr = request.getParameter("awardArr");
		JSONArray awardInfosjson;
		awardInfosjson = new JSONArray(awardArr);
		for(int i=0 ; i < awardInfosjson.length() ;i++){
			org.activiti.engine.impl.util.json.JSONObject prizeobjList = awardInfosjson.getJSONObject(i);
			JSONObject prizeobject =JSONObject.fromObject(prizeobjList.toString());
			TurntableRaffle turntable = (TurntableRaffle) JSONObject.toBean(prizeobject,TurntableRaffle.class);
			turntable.setActivityId(activityId);
			turntable.setUpdateDate(new Date());
			//保存奖品设置
			turntableRaffleService.save(turntable);
		}
		return null;
	}

}