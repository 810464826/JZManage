package com.thinkgem.jeesite.modules.wx.wxTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.wx.followuser.service.WxUserService;
import com.thinkgem.jeesite.modules.wx.wxTool.WXConfig;

@Component  
public class MyTask {
	@Autowired
	private WXConfig wxconfig;
	@Autowired
	WxUserService wxuserservice;
	
	
	@Scheduled(cron="0 0 */1 * * ?") //隔1小时执行  
    public void taskCycle(){ 
		//执行清空表数据
		wxuserservice.empty();
		//用户超过10000人有用，记录最后一人的OPENID，此处无用
		String openid = "";
		//首先获取到所有关注用户
		wxconfig.getUser(openid);
		//根据每个用户的openid查询详细信息
		wxconfig.upUser();
		
    }
}
	