/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.prize.integralprize.web;

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
import com.thinkgem.jeesite.modules.prize.integralprize.entity.IntegralPrize;
import com.thinkgem.jeesite.modules.prize.integralprize.service.IntegralPrizeService;

/**
 * 积分兑换Controller
 * @author tanghaobo
 * @version 2017-07-05
 */
@Controller
@RequestMapping(value = "${adminPath}/integralprize/integralPrize")
public class IntegralPrizeController extends BaseController {

	@Autowired
	private IntegralPrizeService integralPrizeService;
	
	@ModelAttribute
	public IntegralPrize get(@RequestParam(required=false) String id) {
		IntegralPrize entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = integralPrizeService.get(id);
		}
		if (entity == null){
			entity = new IntegralPrize();
		}
		return entity;
	}
	
	@RequiresPermissions("integralprize:integralPrize:view")
	@RequestMapping(value = {"list", ""})
	public String list(IntegralPrize integralPrize, HttpServletRequest request, HttpServletResponse response, Model model) {
		//Page<IntegralPrize> page = integralPrizeService.findPage(new Page<IntegralPrize>(request, response), integralPrize);
		List<IntegralPrize> prize = integralPrizeService.findList(integralPrize);
		//获得数量
		for(IntegralPrize turn : prize ){
			for(IntegralPrize tp : prize){
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
		model.addAttribute("page", prize);
		return "modules/prize/integralprize/integralPrizeList.jsp";
	}

	@RequiresPermissions("integralprize:integralPrize:view")
	@RequestMapping(value = "export", method = RequestMethod.GET)
	public String exportNum(IntegralPrize integralPrize,HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		List<IntegralPrize> prize = integralPrizeService.findList(integralPrize);
		//获得数量
		for(IntegralPrize turn : prize ){
			for(IntegralPrize tp : prize){
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
			String fileName = "积分兑换统计" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			List<String> headerList = Lists.newArrayList();
			headerList.add("奖品名称");
			headerList.add("奖品类型");
			headerList.add("数量");
			ExportExcel ee = new ExportExcel("积分兑换统计记录", headerList);
			for(IntegralPrize si : prize){
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
		return "redirect:" + adminPath + "/integralprize/integralPrizeList.jsp";
	}
	
	@RequiresPermissions("integralprize:integralPrize:view")
	@RequestMapping(value = "form")
	public String form(IntegralPrize integralPrize, Model model) {
		model.addAttribute("integralPrize", integralPrize);
		return "modules/prize/integralprize/integralPrizeForm.jsp";
	}

	@RequiresPermissions("integralprize:integralPrize:edit")
	@RequestMapping(value = "save")
	public String save(IntegralPrize integralPrize, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, integralPrize)){
			return form(integralPrize, model);
		}
		integralPrizeService.save(integralPrize);
		addMessage(redirectAttributes, "保存积分兑换成功");
		return "redirect:"+Global.getAdminPath()+"/integralprize/integralPrize/?repage";
	}
	
	@RequiresPermissions("integralprize:integralPrize:edit")
	@RequestMapping(value = "delete")
	public String delete(IntegralPrize integralPrize, RedirectAttributes redirectAttributes) {
		integralPrizeService.delete(integralPrize);
		addMessage(redirectAttributes, "删除积分兑换成功");
		return "redirect:"+Global.getAdminPath()+"/integralprize/integralPrize/?repage";
	}

}