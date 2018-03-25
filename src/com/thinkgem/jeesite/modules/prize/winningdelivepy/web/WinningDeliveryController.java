/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.prize.winningdelivepy.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

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
import com.thinkgem.jeesite.modules.logistics.logisticsfirm.entity.LogisticsFirm;
import com.thinkgem.jeesite.modules.logistics.logisticsfirm.service.LogisticsFirmService;
import com.thinkgem.jeesite.modules.prize.winningdelivepy.entity.WinningDelivery;
import com.thinkgem.jeesite.modules.prize.winningdelivepy.service.WinningDeliveryService;

/**
 * 奖品派送Controller
 * @author tanghaobo
 * @version 2017-03-31
 */
@Controller
@RequestMapping(value = "${adminPath}/winningdelivepy/winningDelivery")
public class WinningDeliveryController extends BaseController {

	@Autowired
	private WinningDeliveryService winningDeliveryService;
	@Autowired
	private LogisticsFirmService logisticsFirmService;
	
	
	@ModelAttribute
	public WinningDelivery get(@RequestParam(required=false) String id) {
		WinningDelivery entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = winningDeliveryService.get(id);
		}
		if (entity == null){
			entity = new WinningDelivery();
		}
		return entity;
	}
	
	@RequiresPermissions("winningdelivepy:winningDelivery:view")
	@RequestMapping(value = {"list", ""})
	public String list(WinningDelivery winningDelivery, HttpServletRequest request, HttpServletResponse response, Model model) {
		//判断名字是否有表情,应该查询中奖信息表的openid
		if(winningDelivery.getName()!=null && !winningDelivery.getName().equals("")){
			Boolean emoji =  containsEmoji(winningDelivery.getName());
			if(emoji){
				try {
					String name=java.net.URLEncoder.encode(winningDelivery.getName(), "UTF-8");
					winningDelivery.setName(name);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		if(startTime != null && endTime != null){
			try {
				winningDelivery.setStartTime(sdf.parse(startTime));
				winningDelivery.setEndTime(sdf.parse(endTime));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//先将时间剔除出来 修改一下 时间的格式
		System.err.println(startTime+"----"+endTime);
		Page<WinningDelivery> page = winningDeliveryService.findPage(new Page<WinningDelivery>(request, response), winningDelivery);
		List<WinningDelivery> wx = page.getList();
		for(WinningDelivery winning : wx){
			try {
				String urlStr = URLDecoder.decode(winning.getName(), "UTF-8");
				winning.setName(urlStr);
				if(winningDelivery.getName()!=null && !winningDelivery.getName().equals("")){
					winningDelivery.setName(urlStr);
				}else{
					winningDelivery.setName(null);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		model.addAttribute("page", page);
		return "modules/prize/winningdelivepy/winningDeliveryList.jsp";
	}
	
	@RequiresPermissions("signgoods:signGoods:view")
	@RequestMapping(value = "export", method = RequestMethod.GET)
	public String exportNum(WinningDelivery winningDelivery,HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		List<WinningDelivery> info = winningDeliveryService.findList(winningDelivery);
		try {
			String fileName = "奖品派送" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			List<String> headerList = Lists.newArrayList();
			headerList.add("昵称");
			headerList.add("中奖方式");
			headerList.add("奖品名称");
			headerList.add("快递单号");
			headerList.add("快递公司");
			headerList.add("收货地址");
			headerList.add("收货人");
			headerList.add("收货电话");
			headerList.add("发货时间");
			headerList.add("中奖时间");
			headerList.add("配送状态");
			ExportExcel ee = new ExportExcel("发货单查询", headerList);
			for(WinningDelivery si : info){
				Row row = ee.addRow();
				ee.addCell(row, 0, si.getName());
				//中奖方式 1扫码中奖 2转盘中奖 3商城兑换
				if(si.getWinningWay().equals("1")){
					ee.addCell(row, 1, "扫码中奖");
				}else if(si.getWinningWay().equals("2")){
					ee.addCell(row, 1, "转盘中奖");
				}else if(si.getWinningWay().equals("3")){
					ee.addCell(row, 1, "商城兑换");
				}
				ee.addCell(row, 2, si.getPrizeName());
				ee.addCell(row, 3, si.getExpressNumber());
				ee.addCell(row, 4, si.getExpress());
				ee.addCell(row, 5, si.getCollectAddress());
				ee.addCell(row, 6, si.getCollectUser());
				ee.addCell(row, 7, si.getCollectPhone());
				ee.addCell(row, 8, si.getDeliveryTime());
				ee.addCell(row, 9, si.getWinningTime());
				//配送状态  1未配送 2已配送 3已领取 4未使用 5已使用 6已赠送 7赠送中  8已删除  9未领取
				if(si.getDistributionStatus().equals("1")){
					ee.addCell(row, 10, "未配送");
				}else if(si.getDistributionStatus().equals("2")){
					ee.addCell(row, 10, "已配送");
				}else if(si.getDistributionStatus().equals("3")){
					ee.addCell(row, 10, "已领取");
				}else if(si.getDistributionStatus().equals("4")){
					ee.addCell(row, 10, "未使用");
				}else if(si.getDistributionStatus().equals("5")){
					ee.addCell(row, 10, "已使用");
				}else if(si.getDistributionStatus().equals("6")){
					ee.addCell(row, 10, "已赠送");
				}else if(si.getDistributionStatus().equals("7")){
					ee.addCell(row, 10, "赠送中");
				}else if(si.getDistributionStatus().equals("8")){
					ee.addCell(row, 10, "已删除");
				}else if(si.getDistributionStatus().equals("9")){
					ee.addCell(row, 10, "未领取");
				}
			}
			System.out.println("！！！！！导出列表长度为："+info.size()+"！！！！！");
			ee.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出信息失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/winningdelivepy/winningDeliveryList.jsp";
	}

	@RequiresPermissions("winningdelivepy:winningDelivery:view")
	@RequestMapping(value = "form")
	public String form(WinningDelivery winningDelivery, Model model) {
		LogisticsFirm firm = new LogisticsFirm();
		//查询所有的快递公司
		List<LogisticsFirm> findList = logisticsFirmService.findList(firm);
		//查询该奖品派送的快递公司
		LogisticsFirm logistics = logisticsFirmService.get(winningDelivery.getExpress());
		model.addAttribute("winningDelivery", winningDelivery);
		model.addAttribute("findList",findList);
		model.addAttribute("logistics",logistics);
		return "modules/prize/winningdelivepy/winningDeliveryForm.jsp";
	}

	@RequiresPermissions("winningdelivepy:winningDelivery:edit")
	@RequestMapping(value = "save")
	public String save(WinningDelivery winningDelivery, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, winningDelivery)){
			return form(winningDelivery, model);
		}
		winningDeliveryService.save(winningDelivery);
		addMessage(redirectAttributes, "保存奖品派送成功");
		return "redirect:"+Global.getAdminPath()+"/winningdelivepy/winningDelivery/?repage";
	}
	
	@RequiresPermissions("winningdelivepy:winningDelivery:edit")
	@RequestMapping(value = "delete")
	public String delete(WinningDelivery winningDelivery, RedirectAttributes redirectAttributes) {
		winningDeliveryService.delete(winningDelivery);
		addMessage(redirectAttributes, "删除奖品派送成功");
		return "redirect:"+Global.getAdminPath()+"/winningdelivepy/winningDelivery/?repage";
	}
	/** 
     * 判断字符串中是否包含表情符号
     * @return 
     */  
    public static boolean containsEmoji(String source) {
        int len = source.length();
        boolean isEmoji = false;
        for (int i = 0; i < len; i++) {
            char hs = source.charAt(i);
            if (0xd800 <= hs && hs <= 0xdbff) {
                if (source.length() > 1) {
                    char ls = source.charAt(i+1);
                    int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                    if (0x1d000 <= uc && uc <= 0x1f77f) {
                        return true;
                    }
                }
            } else {
                // non surrogate
                if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
                    return true;
                } else if (0x2B05 <= hs && hs <= 0x2b07) {
                    return true;
                } else if (0x2934 <= hs && hs <= 0x2935) {
                    return true;
                } else if (0x3297 <= hs && hs <= 0x3299) {
                    return true;
                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c || hs == 0x2b1b || hs == 0x2b50|| hs == 0x231a ) {
                    return true;
                }
                if (!isEmoji && source.length() > 1 && i < source.length() -1) {
                    char ls = source.charAt(i+1);
                    if (ls == 0x20e3) {
                        return true;
                    }
                }
            }
        }
        return  isEmoji;
    }
}