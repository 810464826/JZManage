/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.product.productinfo.web;

import java.util.List;
import java.util.UUID;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.product.productinfo.entity.ProductInfo;
import com.thinkgem.jeesite.modules.product.productinfo.service.ProductInfoService;
import com.thinkgem.jeesite.modules.wx.wxcard.entity.WxCard;
import com.thinkgem.jeesite.modules.wx.wxcard.service.WxCardService;

/**
 * 产品管理Controller
 * 
 * @author tanghaobo
 * @version 2017-03-17
 */
@Controller
@RequestMapping(value = "${adminPath}/productinfo/productInfo")
public class ProductInfoController extends BaseController {

	@Autowired
	private ProductInfoService productInfoService;
	@Autowired
	private WxCardService wxCardService;

	@ModelAttribute
	public ProductInfo get(@RequestParam(required = false) String id) {
		ProductInfo entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = productInfoService.get(id);
		}
		if (entity == null) {
			entity = new ProductInfo();
		}
		return entity;
	}

	@RequiresPermissions("productinfo:productInfo:view")
	@RequestMapping(value = { "list", "" })
	public String list(ProductInfo productInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductInfo> page = productInfoService.findPage(new Page<ProductInfo>(request, response), productInfo);
		model.addAttribute("page", page);
		return "modules/product/productinfo/productInfoList.jsp";
	}

	@RequiresPermissions("productinfo:productInfo:view")
	@RequestMapping(value = "form")
	public String form(ProductInfo productInfo, Model model) {
		WxCard card = new WxCard();
		List<WxCard> wxcard = wxCardService.findList(card);
		card = wxCardService.get(productInfo.getCardId());
		model.addAttribute("card", card);
		model.addAttribute("wxcard", wxcard);
		model.addAttribute("productInfo", productInfo);
		return "modules/product/productinfo/productInfoForm.jsp";
	}

	@RequiresPermissions("productinfo:productInfo:edit")
	@RequestMapping(value = "save")
	public String save(ProductInfo productInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, productInfo)) {
			return form(productInfo, model);
		}
		productInfoService.save(productInfo);
		addMessage(redirectAttributes, "保存产品管理成功");
		return "redirect:" + Global.getAdminPath() + "/productinfo/productInfo/?repage";
	}

	@RequiresPermissions("productinfo:productInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(ProductInfo productInfo, RedirectAttributes redirectAttributes) {
		productInfoService.delete(productInfo);
		addMessage(redirectAttributes, "删除产品管理成功");
		return "redirect:" + Global.getAdminPath() + "/productinfo/productInfo/?repage";
	}

	@RequestMapping(value = "getAll")
	public void getAll(ProductInfo productInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		String selsect = request.getParameter("selsect");
		System.out.println(selsect);
		List<ProductInfo> product = productInfoService.getProductBytype(selsect);
//		model.addAttribute("product", product);
		for (ProductInfo p : product) {
			System.out.println(p.getName()+ " "+p.getId());
		}
		renderString(response, product);
	}

	/**
	 * 导出产品数据
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("productinfo:productInfo:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(ProductInfo productInfo, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		List<ProductInfo> productList = productInfoService.findList(productInfo);
		try {
			String fileName = "产品信息.xlsx";
			List<String> headerList = Lists.newArrayList();
			headerList.add("产品名称");
			headerList.add("产品编号");
			headerList.add("产品类型");
			headerList.add("产品价格");
			headerList.add("产品单位(选填)");
			headerList.add("酒精度数(选填)");
			headerList.add("规格(选填)");
			headerList.add("净含量(选填)");
			headerList.add("产品描述(选填)");
			headerList.add("状态(必填)");
			headerList.add("备注(选填)");
			ExportExcel ee = new ExportExcel("产品信息", headerList);
			for (ProductInfo po : productList) {
				Row row = ee.addRow();
				ee.addCell(row, 0, po.getName());
				ee.addCell(row, 1, po.getNumber());
				// 产品类型1卡券商品 2：积分标识 3：物流商品
				String type = po.getType();
				if (type.equals("1")) {
					ee.addCell(row, 2, "卡券商品");
				} else if (type.equals("2")) {
					ee.addCell(row, 2, "积分标识");
				} else if (type.equals("3")) {
					ee.addCell(row, 2, "物流商品");
				}
				ee.addCell(row, 3, po.getPrice());
				ee.addCell(row, 4, po.getUnit());
				ee.addCell(row, 5, po.getAlcoholic());
				ee.addCell(row, 6, po.getProductsize());
				ee.addCell(row, 7, po.getCapacity());
				ee.addCell(row, 8, po.getDescription());
				String status = po.getStatus();
				// 状态 1可用 2不可用
				if (status.equals("1")) {
					ee.addCell(row, 9, "可用");
				} else if (status.equals("2")) {
					ee.addCell(row, 9, "不可用");
				}
				ee.addCell(row, 10, po.getRemark());
			}
			System.err.println("！！！！！导出列表长度为：" + productList.size() + "！！！！！");
			ee.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/productinfo/productInfo/list?repage";
	}

	/**
	 * 这里是产品导入
	 * 
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("productinfo:productInfo:edit")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/productinfo:productInfo/list?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProductInfo> list = ei.getDataList(ProductInfo.class);
			for (ProductInfo p : list) {
				try {
					// 根据产品编号去重
					String number = p.getNumber();
					List<ProductInfo> productByNo = productInfoService.getProductByNumber(number);
					if (productByNo.size()==0) {
						ProductInfo product = new ProductInfo();
						product.setId(UUID.randomUUID().toString().replaceAll("-", ""));
						product.setName(p.getName());
						product.setNumber(p.getNumber());
						// 产品类型1：卡券商品 2：积分标识 3:物流商品
						String type = p.getType();
						if (type.equals("卡券商品")) {
							product.setType("1");
						} else if (type.equals("积分标识")) {
							product.setType("2");
						} else if (type.equals("物流商品")) {
							product.setType("3");
						}
						product.setPrice(p.getPrice());
						product.setUnit(p.getUnit());
						product.setAlcoholic(p.getAlcoholic());
						product.setProductsize(p.getProductsize());
						product.setCapacity(p.getCapacity());
						product.setDescription(p.getDescription());
						String status = p.getStatus();
						if(status.equals("可用")){
							product.setStatus("1");
						}else if(status.equals("不可用")){
							product.setStatus("2");
						}
						product.setRemark(p.getRemark());
						BeanValidators.validateWithException(validator, product);
						productInfoService.savePro(product);
						successNum++;
					}else{
						failureMsg.append("<br/>该编号产品 " + number + " 已存在; ");
						failureNum++;
					}
				} catch (Exception e) {
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条产品，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条产品" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入产品失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/productinfo/productInfo/list?repage";
	}

	/**
	 * 下载产品信息填写模版
	 * 
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("productinfo:productInfo:view")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "产品信息填写模版.xlsx";
			List<ProductInfo> list = Lists.newArrayList();
			// list.add(UserUtils.getUser());
			// 第三个参数设置为“2”表示输出为导入模板（1:导出数据；2：导入模板）
			new ExportExcel("产品信息填写模版", ProductInfo.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/productinfo/productInfo/list?repage";
	}
}