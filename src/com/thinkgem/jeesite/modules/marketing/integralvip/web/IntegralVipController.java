/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.integralvip.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import com.thinkgem.jeesite.modules.marketing.integralvip.entity.IntegralVip;
import com.thinkgem.jeesite.modules.marketing.integralvip.service.IntegralVipService;

/**
 * 会员积分Controller
 * @author tanghaobo
 * @version 2017-03-27
 */
@Controller
@RequestMapping(value = "${adminPath}/integralvip/integralVip")
public class IntegralVipController extends BaseController {

	@Autowired
	private IntegralVipService integralVipService;
	
	@ModelAttribute
	public IntegralVip get(@RequestParam(required=false) String id) {
		IntegralVip entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = integralVipService.get(id);
		}
		if (entity == null){
			entity = new IntegralVip();
		}
		return entity;
	}
	
	@RequiresPermissions("integralvip:integralVip:view")
	@RequestMapping(value = {"list", ""})
	public String list(IntegralVip integralVip, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(integralVip.getLuckyDraw() != null && !integralVip.getLuckyDraw().equals("")){
			Boolean emoji =  containsEmoji(integralVip.getLuckyDraw());
			if(emoji){
				try {
					String name=java.net.URLEncoder.encode(integralVip.getLuckyDraw(), "UTF-8");
					integralVip.setLuckyDraw(name);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		/*//获取前端传递的查询的分数
		String smallFens = integralVip.getSmallFen()
		String bigFens = request.getParameter("bigFen");
		if(smallFens!=null&&bigFens!=null){
			int smallFen =Integer.parseInt(smallFens);
			int bigFen = Integer.parseInt(bigFens);
			integralVip.setSmallFen(smallFen);
			integralVip.setBigFen(bigFen);
		}*/
		Page<IntegralVip> page = integralVipService.findPage(new Page<IntegralVip>(request, response), integralVip); 
		List<IntegralVip> wx = page.getList();
		for(IntegralVip winning : wx){
			try {
				String urlStr = URLDecoder.decode(winning.getLuckyDraw(), "UTF-8");
				winning.setLuckyDraw(urlStr);
				if(integralVip.getLuckyDraw() != null && !integralVip.getLuckyDraw().equals("")){
					integralVip.setLuckyDraw(urlStr);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		model.addAttribute("page", page);
		return "modules/marketing/integralvip/integralVipList.jsp";
	}

	@RequiresPermissions("integralvip:integralVip:view")
	@RequestMapping(value = "form")
	public String form(IntegralVip integralVip, Model model) {
		model.addAttribute("integralVip", integralVip);
		return "modules/marketing/integralvip/integralVipForm.jsp";
	}

	@RequiresPermissions("integralvip:integralVip:edit")
	@RequestMapping(value = "save")
	public String save(IntegralVip integralVip, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, integralVip)){
			return form(integralVip, model);
		}
		integralVipService.save(integralVip);
		addMessage(redirectAttributes, "保存会员积分成功");
		return "redirect:"+Global.getAdminPath()+"/integralvip/integralVip/?repage";
	}
	
	@RequiresPermissions("integralvip:integralVip:edit")
	@RequestMapping(value = "delete")
	public String delete(IntegralVip integralVip, RedirectAttributes redirectAttributes) {
		integralVipService.delete(integralVip);
		addMessage(redirectAttributes, "删除会员积分成功");
		return "redirect:"+Global.getAdminPath()+"/integralvip/integralVip/?repage";
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