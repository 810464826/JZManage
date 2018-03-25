package com.thinkgem.jeesite.modules.wx.wxTool;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.thinkgem.jeesite.modules.wx.followuser.entity.WxUser;
import com.thinkgem.jeesite.modules.wx.followuser.service.WxUserService;
import com.thinkgem.jeesite.modules.wx.followuser.web.WxUserController;
import com.thinkgem.jeesite.modules.wx.wxTool.WeixinUtil;
import com.thinkgem.jeesite.modules.wx.wx_config.entity.WxConfig;
import com.thinkgem.jeesite.modules.wx.wx_config.service.WxConfigService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.web.BaseController;

@Controller
@RequestMapping(value = "${adminPath}/wx/wxconfig")
public class WXConfig extends BaseController{
	
	@Autowired
	WxUserController wxuser;
	@Autowired
	WxUserService wxuserservice;
	@Autowired
	private WxConfigService wxConfigService;
	
	public static String accessToken = null;
	
    public Map<String, String> WXParameter(String url) {
    	WxConfig accesstoken = getWxconfigByName();
    	String jsapi_ticket =WeixinUtil.getAccess_token("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accesstoken.getValue()+"&type=jsapi");
        String[] jsapi1 = jsapi_ticket.split(":");
    	String[] jsapi2 = jsapi1[3].split(",");
    	char [] stringArray = jsapi2[0].toCharArray();
    	String ticket3 = "" ;
    	for(int i=1;i<stringArray.length-1;i++){
    		String ticket = String.valueOf(stringArray[i]);
    		ticket3 += ticket;
    	} 
        Map<String, String> ret = new HashMap<String, String>();  
        String nonce_str = create_nonce_str();  
        String timestamp = create_timestamp();  
        String string1;  
        String signature = "";
        //注意这里参数名必须全部小写，且必须有序  
        string1 = "jsapi_ticket=" + ticket3 +  
                  "&noncestr=" + nonce_str +  
                  "&timestamp=" + timestamp +  
                  "&url=" + url;  
        System.out.println("string1="+string1);  
        try  
        {  
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");  
            crypt.reset();  
            crypt.update(string1.getBytes("UTF-8"));  
            signature = byteToHex(crypt.digest());  
        }  
        catch (NoSuchAlgorithmException e)  
        {  
            e.printStackTrace();  
        }  
        catch (UnsupportedEncodingException e)  
        {  
            e.printStackTrace();  
        }  
  
        ret.put("url", url);  
        ret.put("jsapi_ticket", ticket3);  
        ret.put("nonceStr", nonce_str);  
        ret.put("timestamp", timestamp);  
        ret.put("signature", signature);  
        ret.put("appId", Global.getConfig("appId"));   
        return ret;  
    }  
    
    
	/** 
     * 数据库中获取两小时时效的accessToken,但是项目MyTask中的定时器是每小时获取一次，一天24小时获取24次
     * @param hash 
     * @return 
     */ 
    public WxConfig getWxconfigByName(){
    	accessToken = "";
    	WxConfig config =  wxConfigService.getWxconfigByName("accessToken");
    	return config;
    }
    
    
    
    /**
     * 只获取AccessToken参数
     * */
    public String getAccessToken(){
    	String appid = "";
    	String appSecret = "";
    	WxConfig wxConfig = new WxConfig();
    	List<WxConfig> list = wxConfigService.findList(wxConfig);
    	for(WxConfig config : list){
    		if (config.getName().equals("appid")) {
    			appid = config.getValue();
    			System.out.println("获取AccessToken参数appid"+appid);
			}
    		if (config.getName().equals("appSecret")) {
    			appSecret = config.getValue();
    			System.out.println("获取AccessToken参数appSecret"+appSecret);
			}
    	}
    	String aToken = WeixinUtil.getAccess_token("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appSecret+"");  
    	String[] tokenOne = aToken.split(":");
    	String[] token = tokenOne[1].split(",");
    	char [] stringArr = token[0].toCharArray();
    	String token3 = "" ;
    	for(int i=1;i<stringArr.length-1;i++){
    		String token2 = String.valueOf(stringArr[i]);
    		token3 += token2;
    	}
    	return token3;
    }
    
	/**
	 * 获取关注用户
	 * */
	public void getUser(String openid){
		WxConfig accesstoken = getWxconfigByName();
		String userList = WeixinUtil.getAccess_token("https://api.weixin.qq.com/cgi-bin/user/get?access_token="+accesstoken.getValue()+"&next_openid="+openid);
		JSONObject dataJson = JSONObject.fromObject(userList);
		JSONObject  response=dataJson.getJSONObject("data");
		JSONArray  open =response.getJSONArray("openid");
		//总用户数
		String total = dataJson.get("total").toString();
		System.out.println("总用户数"+dataJson.get("total"));
		//拉取数
		String count = dataJson.get("count").toString();
		System.out.println("拉取用户数"+dataJson.get("count"));
		//最后一个OPENID
		String next_openid = dataJson.get("next_openid").toString();
		System.out.println("最后用户的Openid"+dataJson.get("next_openid"));
		if(Integer.parseInt(total)<=10000){
			//循环保存关注用户，循环拉取数量
			for(int i=0;i<Integer.parseInt(count);i++){
				//判断当前数据库是否已有该用户,根据openid查询
				//查询数据
				WxUser getuser = wxuserservice.getUserByOpenid(open.get(i).toString());
				if(getuser == null){
					WxUser user = new WxUser();
					user.setId(IdGen.uuid());//JEEsite生产ID
					user.setOpenid(open.get(i).toString());//用户OPENID
					wxuserservice.saveByOpenid(user);
				}else{
					System.out.println("用户已经存在");
				}
			}
		}else{
			//循环保存关注用户，循环拉取数量
			for(int i=0;i<Integer.parseInt(count);i++){
				//判断当前数据库是否已有该用户,根据openid查询
				//查询数据
				WxUser getuser = wxuserservice.getUserByOpenid(open.get(i).toString());
				if(getuser == null){
					WxUser user = new WxUser();
					user.setId(IdGen.uuid());//JEEsite生产ID
					user.setOpenid(open.get(i).toString());//用户OPENID
					wxuserservice.saveByOpenid(user);
				}else{
					System.out.println("用户已经存在");
				}
			}
			backOff(next_openid);
		}
	}
    //大于10000人时的回掉
	public void backOff(String openid){
		getUser(openid);
	}
	
	/**
	 * 根据OPENID修改详细信息
	 * */
	public void upUser(){
		//数据库获取所有用户
		WxUser user = new WxUser();
		List<WxUser> list =  wxuserservice.findList(user);
		for(WxUser wxuser : list){
			//读取用户基本信息
			String token = WeixinUtil.getAccess_token("https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accessToken+"&openid="+wxuser.getOpenid()+"&lang=zh_CN");
			JSONObject obj = JSONObject.fromObject(token);
			user.setId(wxuser.getId());
			user.setOpenid(wxuser.getOpenid());
			try {
			user.setNickname(emojiConvert1(obj.get("nickname").toString()));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//微信名称
			if(obj.get("sex").toString().equals("1")){//性别
				user.setSex("男");
			}else{
				user.setSex("女");
			}
			user.setCountry(obj.get("country").toString());//国籍
			user.setProvince(obj.get("province").toString());//省
			user.setCity(obj.get("city").toString());//市
			user.setHeadimgurl(obj.get("headimgurl").toString());//头像地址
			user.setPhoneNumber(" ");
			//时间戳转换
			long msgCreateTime = Long.parseLong(obj.get("subscribe_time").toString()) * 1000L;
		    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		    String time =  format.format(new Date(msgCreateTime));
		    String dstr=time;  
		    try {
				java.util.Date date=format.parse(dstr);
				user.setFollotime(date);//关注时间
				wxuserservice.upUser(user);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 字符串转换unicode
	 */
	public static String string2Unicode(String string) {
	 
	    StringBuffer unicode = new StringBuffer();
	 
	    for (int i = 0; i < string.length(); i++) {
	 
	        // 取出每一个字符
	        char c = string.charAt(i);
	 
	        // 转换为unicode
	        unicode.append("\\u" + Integer.toHexString(c));
	    }
	 
	    return unicode.toString();
	}
	
	/**
	 * unicode 转字符串
	 */
	public static String unicode2String(String unicode) {
	 
	    StringBuffer string = new StringBuffer();
	 
	    String[] hex = unicode.split("\\\\u");
	 
	    for (int i = 1; i < hex.length; i++) {
	 
	        // 转换出每一个代码点
	        int data = Integer.parseInt(hex[i], 16);
	 
	        // 追加成string
	        string.append((char) data);
	    }
	 
	    return string.toString();
	}
	
	public static String emojiConvert1(String str)
			throws UnsupportedEncodingException {
			String patternString = "([\\x{10000}-\\x{10ffff}\ud800-\udfff])";

			Pattern pattern = Pattern.compile(patternString);
			Matcher matcher = pattern.matcher(str);
			StringBuffer sb = new StringBuffer();
			String aa = "";
			while(matcher.find()) {
			aa = string2Unicode(matcher.group(1));
			matcher.appendReplacement(sb,"");
			}
			matcher.appendTail(sb);
			return sb.toString()+aa;
			}

			/**
			* @Description 还原utf8数据库中保存的含转换后emoji表情的字符串
			* @param str
			* 转换后的字符串
			* @return 转换前的字符串
			* @throws UnsupportedEncodingException
			* exception
			*/
			public static String emojiRecovery2(String str)
			throws UnsupportedEncodingException {
			String patternString = "\\[\\[(.*?)\\]\\]";

			Pattern pattern = Pattern.compile(patternString);
			Matcher matcher = pattern.matcher(str);

			StringBuffer sb = new StringBuffer();
			while(matcher.find()) {
			try {
			matcher.appendReplacement(sb,
			URLDecoder.decode(matcher.group(1), "UTF-8"));
			} catch(UnsupportedEncodingException e) {
			throw e;
			}
			}
			matcher.appendTail(sb);
			return sb.toString();
			}
	
	/**
     * 随机加密 
     * @param hash 
     * @return 
     */  
    private static String byteToHex(final byte[] hash) {  
        Formatter formatter = new Formatter();  
        for (byte b : hash)  
        {  
            formatter.format("%02x", b);  
        }  
        String result = formatter.toString();  
        formatter.close();  
        return result;  
    }  
  
    /** 
     * 产生随机串--由程序自己随机产生 
     * @return 
     */  
    private static String create_nonce_str() {  
        return UUID.randomUUID().toString();  
    }  
  
    /** 
     * 由程序自己获取当前时间 
     * @return 
     */  
    private static String create_timestamp() {  
        return Long.toString(System.currentTimeMillis() / 1000);  
    } 
}
