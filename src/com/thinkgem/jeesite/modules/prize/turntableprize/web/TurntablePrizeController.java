/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.prize.turntableprize.web;

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
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.prize.turntableprize.entity.TurntablePrize;
import com.thinkgem.jeesite.modules.prize.turntableprize.service.TurntablePrizeService;

/**
 * 转盘中奖记录Controller
 * @author tanghaobo
 * @version 2017-07-05
 */
@Controller
@RequestMapping(value = "${adminPath}/turntableprize/turntablePrize")
public class TurntablePrizeController extends BaseController {

	@Autowired
	private TurntablePrizeService turntablePrizeService;
	
	@ModelAttribute
	public TurntablePrize get(@RequestParam(required=false) String id) {
		TurntablePrize entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = turntablePrizeService.get(id);
		}
		if (entity == null){
			entity = new TurntablePrize();
		}
		return entity;
	}
	
	@RequiresPermissions("turntableprize:turntablePrize:view")
	@RequestMapping(value = {"list", ""})
	public String list(TurntablePrize turntablePrize, HttpServletRequest request, HttpServletResponse response, Model model) {
		//Page<TurntablePrize> page = turntablePrizeService.findPage(new Page<TurntablePrize>(request, response), turntablePrize);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		if(startTime != null && endTime != null){
			try {
				turntablePrize.setStartTime(sdf.parse(startTime));
				turntablePrize.setEndTime(sdf.parse(endTime));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//先将时间剔除出来 修改一下 时间的格式
		System.err.println(startTime+"----"+endTime);
		List<TurntablePrize> turnp = turntablePrizeService.findList(turntablePrize);
		List<TurntablePrize> prize =turnp;
		//获得数量
		for(TurntablePrize turn : prize ){
			for(TurntablePrize tp : prize){
				//判断是否属于相同奖品
				if(turn.getPrizeName().equals(tp.getPrizeName())){
					//判断是否相同类型
					if(turn.getPrizeType().equals(tp.getPrizeType())){
						if(turn.getNumber()==null){
							turn.setNumber("1");
						}else{
							int number = Integer.parseInt(turn.getNumber())+1;
							turn.setNumber(number+"");
						}
					}
				}
			}
		}
		//去重复
        for (int i = 0; i < prize.size(); i++){  //外循环是循环的次数
            for (int j = prize.size() - 1 ; j > i; j--){	//内循环是 外循环一次比较的次数
                if (prize.get(i).getPrizeName().equals(prize.get(j).getPrizeName())){
                	if(prize.get(i).getPrizeType().equals(prize.get(j).getPrizeType())){
                		prize.remove(j);
                	}
                }
            }
        }
        //page.setCount(prize.size());
		model.addAttribute("page", turnp);
		return "modules/prize/turntableprize/turntablePrizeList.jsp";
	}

	@RequiresPermissions("turntableprize:turntablePrize:view")
	@RequestMapping(value = "export", method = RequestMethod.GET)
	public String exportNum(TurntablePrize turntablePrize,HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		List<TurntablePrize> turnp = turntablePrizeService.findList(turntablePrize);
		List<TurntablePrize> prize =turnp;
		//获得数量
		for(TurntablePrize turn : prize ){
			for(TurntablePrize tp : prize){
				//判断是否属于相同奖品
				if(turn.getPrizeName().equals(tp.getPrizeName())){
					//判断是否相同类型
					if(turn.getPrizeType().equals(tp.getPrizeType())){
						if(turn.getNumber()==null){
							turn.setNumber("1");
						}else{
							int number = Integer.parseInt(turn.getNumber())+1;
							turn.setNumber(number+"");
						}
					}
				}
			}
		}
		//去重复
        for (int i = 0; i < prize.size(); i++){  //外循环是循环的次数
            for (int j = prize.size() - 1 ; j > i; j--){	//内循环是 外循环一次比较的次数
                if (prize.get(i).getPrizeName().equals(prize.get(j).getPrizeName())){
                	if(prize.get(i).getPrizeType().equals(prize.get(j).getPrizeType())){
                		prize.remove(j);
                	}
                }
            }
        }
		try {
			String fileName = "转盘中奖记录" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			List<String> headerList = Lists.newArrayList();
			headerList.add("奖品名称");
			headerList.add("奖品类型");
			headerList.add("中奖数量");
			ExportExcel ee = new ExportExcel("转盘中奖记录", headerList);
			for(TurntablePrize si : prize){
				Row row = ee.addRow();
				ee.addCell(row, 0, si.getName());
				if(si.getPrizeType().equals("1")){
					ee.addCell(row, 1, "积分商品");
				}else if(si.getPrizeType().equals("2")){
					ee.addCell(row, 1, "卡券商品");
				}else if(si.getPrizeType().equals("3")){
					ee.addCell(row, 1, "物流商品");
				}
				ee.addCell(row, 2, si.getNumber());
			}
			System.out.println("！！！！！导出列表长度为："+prize.size()+"！！！！！");
			ee.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出信息失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/turntableprize/turntablePrizeList.jsp";
	}
	
	@RequiresPermissions("turntableprize:turntablePrize:view")
	@RequestMapping(value = "form")
	public String form(TurntablePrize turntablePrize, Model model) {
		model.addAttribute("turntablePrize", turntablePrize);
		return "modules/prize/turntableprize/turntablePrizeForm.jsp";
	}

	@RequiresPermissions("turntableprize:turntablePrize:edit")
	@RequestMapping(value = "save")
	public String save(TurntablePrize turntablePrize, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, turntablePrize)){
			return form(turntablePrize, model);
		}
		turntablePrizeService.save(turntablePrize);
		addMessage(redirectAttributes, "保存转盘中奖记录成功");
		return "redirect:"+Global.getAdminPath()+"/turntableprize/turntablePrize/?repage";
	}
	
	@RequiresPermissions("turntableprize:turntablePrize:edit")
	@RequestMapping(value = "delete")
	public String delete(TurntablePrize turntablePrize, RedirectAttributes redirectAttributes) {
		turntablePrizeService.delete(turntablePrize);
		addMessage(redirectAttributes, "删除转盘中奖记录成功");
		return "redirect:"+Global.getAdminPath()+"/turntableprize/turntablePrize/?repage";
	}

}