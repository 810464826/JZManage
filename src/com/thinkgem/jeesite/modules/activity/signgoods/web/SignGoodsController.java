/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.activity.signgoods.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.activity.signgoods.entity.SellerCenterObj;
import com.thinkgem.jeesite.modules.activity.signgoods.entity.SellerOutInfo;
import com.thinkgem.jeesite.modules.activity.signgoods.entity.SignGoods;
import com.thinkgem.jeesite.modules.activity.signgoods.service.SellerOutInfoService;
import com.thinkgem.jeesite.modules.activity.signgoods.service.SignGoodsService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 扫描发货Controller
 * @author tanghaobo
 * @version 2017-04-27
 */
@Controller
@RequestMapping(value = "${adminPath}/signgoods/signGoods")
public class SignGoodsController extends BaseController {

	@Autowired
	private SignGoodsService signGoodsService;
	@Autowired
	private SellerOutInfoService sellerOutInfoService;
	
	
	@ModelAttribute
	public SignGoods get(@RequestParam(required=false) String id) {
		SignGoods entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = signGoodsService.get(id);
		}
		if (entity == null){
			entity = new SignGoods();
		}
		return entity;
	}
	//这里查询大数据所有的运单
	@RequiresPermissions("signgoods:signGoods:view")
	@RequestMapping(value = {"list", ""})
	public String list(SignGoods signGoods, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<SellerOutInfo> outList=new ArrayList<>();
		model.addAttribute("outList", outList);
		return "modules/activity/signgoods/signGoodsList.jsp";
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
		String outNo = request.getParameter("outNo");
		String sellerName = request.getParameter("sellerName");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
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
			outList =sellerOutInfoService.getSellerOutInfoByCondition(outNo, sellerName, startTime, endTime,startLine,endLine);
			sellInfoCount = sellerOutInfoService.getAllSellInfoCountSearch(outNo,sellerName,startTime,endTime);
		}
		//普通用户
		else{
			//大数据平台的运单
			outList = sellerOutInfoService.getSellerOutInfoByConditionByPhone(outNo, sellerName, startTime, endTime,phone,startLine,endLine);
			sellInfoCount = sellerOutInfoService.getSellInfoCountByPhoneSearch(outNo,phone,sellerName,startTime,endTime);
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
			sellerList.add(sc);
		}
		return sellerList;
	}
	
	
	@RequiresPermissions("signgoods:signGoods:view")
	@RequestMapping(value = "export", method = RequestMethod.GET)
	public String exportNum(SignGoods signGoods,HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		List<SignGoods> info = signGoodsService.findList(signGoods);
		try {
			String fileName = "发货单查询" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			List<String> headerList = Lists.newArrayList();
			headerList.add("发货单号");
			headerList.add("品种名称");
			headerList.add("渠道商名称");
			headerList.add("收货地址");
			headerList.add("总箱数");
			headerList.add("发货时间");
			ExportExcel ee = new ExportExcel("发货单查询", headerList);
			for(SignGoods si : info){
				Row row = ee.addRow();
				ee.addCell(row, 0, si.getGoodsNumber());
				ee.addCell(row, 1, si.getVarieties());
				ee.addCell(row, 2, si.getDealerName());
				ee.addCell(row, 3, si.getGoodsAddress());
				ee.addCell(row, 4, si.getTotalBox());
				ee.addCell(row, 5, si.getCreateTime());
			}
			System.out.println("！！！！！导出列表长度为："+info.size()+"！！！！！");
			ee.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出信息失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/signgoods/signGoodsList.jsp";
	}
	
	@RequestMapping(value = {"getlist", ""})
	public Page<SignGoods> getlist(SignGoods signGoods, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SignGoods> page = signGoodsService.findPage(new Page<SignGoods>(request, response), signGoods); 
		return page;
	}
	
	@RequiresPermissions("signgoods:signGoods:view")
	@RequestMapping(value = "form")
	public String form(SignGoods signGoods, Model model) {
		model.addAttribute("signGoods", signGoods);
		return "modules/activity/signgoods/signGoodsForm.jsp";
	}

	@RequiresPermissions("signgoods:signGoods:edit")
	@RequestMapping(value = "save")
	public String save(SignGoods signGoods, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, signGoods)){
			return form(signGoods, model);
		}
		signGoodsService.save(signGoods);
		addMessage(redirectAttributes, "保存扫描发货成功");
		return "redirect:"+Global.getAdminPath()+"/signgoods/signGoods/?repage";
	}
	
	@RequiresPermissions("signgoods:signGoods:edit")
	@RequestMapping(value = "delete")
	public String delete(SignGoods signGoods, RedirectAttributes redirectAttributes) {
		signGoodsService.delete(signGoods);
		addMessage(redirectAttributes, "删除扫描发货成功");
		return "redirect:"+Global.getAdminPath()+"/signgoods/signGoods/?repage";
	}

}