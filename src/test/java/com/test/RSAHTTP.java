package com.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.util.RSAEncrypt;
import com.util.RSASignUtils;
import com.util.RSASignature;

public class RSAHTTP {
	
	public static void main(String[] args) throws Exception {  
		boolean isSuccess = false;
	    
	    HttpPost post = null;
	    try {
	        HttpClient httpClient = new DefaultHttpClient();

	        // 设置超时时间
	        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
	        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);
	            
	        post = new HttpPost("http://localhost:8080/online/recharge");
	        // 构造消息头
	        post.setHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
	                    
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date now = new Date();
	        // 构建消息实体
	        Map<String,String> signMap = new HashMap<String,String>();
	        signMap.put("charset", "utf-8"); 
	        signMap.put("timestamp", sdf.format(now)); 
	        signMap.put("txnAmount", "23.5"); 
	        signMap.put("cardNo", "908888950201232321"); 
	        signMap.put("extTxnNo", "1232112"); 
	        signMap.put("notifyUrl", "http://127.0.0.1/test"); 
	        //signMap.put("paymentInterface", ""); 
	        signMap.put("bankCardNo", "32421231312432"); 
	        signMap.put("serviceFee", "0.0"); 
	        /*signMap.put("txnRemark", ""); 
	        signMap.put("accountNo", "");
	        signMap.put("appId", "");
	        signMap.put("terminalSn", "");
	        signMap.put("terminalType", "");
	        signMap.put("channel", "");
	        signMap.put("ipAddress", "");
	        signMap.put("tranCode", "");*/
	        
	        String encrypt = RSASignUtils.buildRequestSign(signMap, RSAEncrypt.loadPrivateKeyByFile("/Users/mittermeyer/Documents/git/springtest"));
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
	        nvps.add(new BasicNameValuePair("signType", "RSA"));  
	        nvps.add(new BasicNameValuePair("charset", "utf-8")); 
	        nvps.add(new BasicNameValuePair("sign", encrypt)); 
	        nvps.add(new BasicNameValuePair("timestamp", sdf.format(now))); 
	        nvps.add(new BasicNameValuePair("cardNo", "908888950201232321")); 
	        nvps.add(new BasicNameValuePair("txnAmount", "23.5")); 
	        nvps.add(new BasicNameValuePair("extTxnNo", "1232112")); 
	        nvps.add(new BasicNameValuePair("notifyUrl", "http://127.0.0.1/test")); 
	        //nvps.add(new BasicNameValuePair("paymentInterface", "")); 
	        nvps.add(new BasicNameValuePair("bankCardNo", "32421231312432")); 
	        nvps.add(new BasicNameValuePair("serviceFee", "0.0")); 
	        /*nvps.add(new BasicNameValuePair("txnRemark", "")); 
	        nvps.add(new BasicNameValuePair("accountNo", ""));  
	        nvps.add(new BasicNameValuePair("appId", ""));  
	        nvps.add(new BasicNameValuePair("terminalSn", ""));  
	        nvps.add(new BasicNameValuePair("terminalType", ""));  
	        nvps.add(new BasicNameValuePair("channel", ""));  
	        nvps.add(new BasicNameValuePair("ipAddress", ""));  
	        nvps.add(new BasicNameValuePair("tranCode", ""));  */
	         
	        //设置参数到请求对象中  
	        post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8")); 
	            
	        HttpResponse response = httpClient.execute(post);
	            
	        // 检验返回码
	        int statusCode = response.getStatusLine().getStatusCode();
	        HttpEntity responseEntity = response.getEntity();  
	        String body = "";  
	        if (responseEntity != null) {  
	            //按指定编码转换结果实体为String类型  
	            body = EntityUtils.toString(responseEntity, "UTF-8");  
	        }  
	        EntityUtils.consume(responseEntity);  
	        //释放链接  
	        System.out.println(body);
	    } catch (Exception e) {
	        e.printStackTrace();
	        isSuccess = false;
	    }finally{
	        if(post != null){
	            try {
	                post.releaseConnection();
	                Thread.sleep(500);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}

}
