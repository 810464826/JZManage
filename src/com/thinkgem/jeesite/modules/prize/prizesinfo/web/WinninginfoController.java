/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.prize.prizesinfo.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.thinkgem.jeesite.modules.prize.prizesinfo.entity.Winninginfo;
import com.thinkgem.jeesite.modules.prize.prizesinfo.service.WinninginfoService;

/**
 * 中奖记录Controller
 * @author tanghaobo
 * @version 2017-06-15
 */
@Controller
@RequestMapping(value = "${adminPath}/prizesinfo/winninginfo")
public class WinninginfoController extends BaseController {

	@Autowired
	private WinninginfoService winninginfoService;
	
	@ModelAttribute
	public Winninginfo get(@RequestParam(required=false) String id) {
		Winninginfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = winninginfoService.get(id);
		}
		if (entity == null){
			entity = new Winninginfo();
		}
		return entity;
	}
	
	@RequiresPermissions("prizesinfo:winninginfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(Winninginfo winninginfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(winninginfo.getName() != null && !winninginfo.getName().equals("")){
			Boolean emoji =  containsEmoji(winninginfo.getName());
			if(emoji){
				try {
					String name=java.net.URLEncoder.encode(winninginfo.getName(), "UTF-8");
					winninginfo.setName(name);
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
				winninginfo.setStartTime(sdf.parse(startTime));
				winninginfo.setEndTime(sdf.parse(endTime));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//先将时间剔除出来 修改一下 时间的格式
		System.err.println(startTime+"----"+endTime);
		
		Page<Winninginfo> page = winninginfoService.findPage(new Page<Winninginfo>(request, response), winninginfo); 
		List<Winninginfo> wx = page.getList();
		for(Winninginfo winning : wx){
			try {
				String urlStr = URLDecoder.decode(winning.getName(), "UTF-8");
				winning.setName(urlStr);
				if(winninginfo.getName() != null && !winninginfo.getName().equals("")){
					winninginfo.setName(urlStr);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		model.addAttribute("page", page);
		return "modules/prize/prizesinfo/winninginfoList.jsp";
	}

	@RequiresPermissions("prizesinfo:winninginfo:view")
	@RequestMapping(value = "form")
	public String form(Winninginfo winninginfo, Model model) {
		model.addAttribute("winninginfo", winninginfo);
		return "modules/prize/prizesinfo/winninginfoForm.jsp";
	}

	@RequiresPermissions("prizesinfo:winninginfo:edit")
	@RequestMapping(value = "save")
	public String save(Winninginfo winninginfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, winninginfo)){
			return form(winninginfo, model);
		}
		winninginfoService.save(winninginfo);
		addMessage(redirectAttributes, "保存中奖记录成功");
		return "redirect:"+Global.getAdminPath()+"/prizesinfo/winninginfo/?repage";
	}
	
	@RequiresPermissions("prizesinfo:winninginfo:edit")
	@RequestMapping(value = "delete")
	public String delete(Winninginfo winninginfo, RedirectAttributes redirectAttributes) {
		winninginfoService.delete(winninginfo);
		addMessage(redirectAttributes, "删除中奖记录成功");
		return "redirect:"+Global.getAdminPath()+"/prizesinfo/winninginfo/?repage";
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