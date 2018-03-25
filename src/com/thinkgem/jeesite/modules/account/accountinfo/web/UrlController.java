package com.thinkgem.jeesite.modules.account.accountinfo.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.web.BaseController;
@Controller
@RequestMapping(value = "${adminPath}/url/url")
public class UrlController extends BaseController implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**  
    * post方式请求http服务  
    * @param urlStr  
    * @param params   name=yxd&age=25  
    * @return  
    * @throws Exception  
    */  
   public static String getURLByPost(String urlStr,String params)throws Exception{  
       URL url=new URL(urlStr);  
       HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
       conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)"); 
       conn.setRequestMethod("GET");  
       conn.setDoOutput(true);  
       conn.setDoInput(true);  
       PrintWriter printWriter = new PrintWriter(conn.getOutputStream());  
       printWriter.write(params);  
       printWriter.flush();          
       BufferedReader in = null;   
       StringBuilder sb = new StringBuilder();   
       try{     
           in = new BufferedReader( new InputStreamReader(conn.getInputStream(),"UTF-8") );   
           String str = null;    
           while((str = in.readLine()) != null) {    
               sb.append( str );     
           }     
        } catch (Exception ex) {   
           throw ex;   
        } finally{    
         try{   
             conn.disconnect();  
             if(in!=null){  
                 in.close();  
             }  
             if(printWriter!=null){  
                 printWriter.close();  
             }  
         }catch(IOException ex) {     
             throw ex;   
         }     
        }     
        return sb.toString();  
   }  
   
   public static  String getUrl(String url) {
	   String accessToken = null;
       try {
           URL urlGet = new URL(url);
           HttpURLConnection http = (HttpURLConnection) urlGet
                   .openConnection();
           http.setRequestMethod("GET"); // 必须是get方式请求
           http.setRequestProperty("Content-Type",
                   "application/x-www-form-urlencoded");
           http.setDoOutput(true);
           http.setDoInput(true);
           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
           http.connect();
           InputStream is = http.getInputStream();
           int size = is.available();
           byte[] jsonBytes = new byte[size];
           is.read(jsonBytes);
           accessToken = new String(jsonBytes, "UTF-8");
           System.out.println(accessToken);
           is.close();
       } catch (Exception e) {

           e.printStackTrace();

       }
       return accessToken;

   }
   
}
