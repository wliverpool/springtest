package com.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import com.util.HttpUtil;
import com.util.SelfSignHttpsClient;

public class SelfSignHttpsClientTest {
	
	@Test  
	public void doTest() throws ClientProtocolException, URISyntaxException, IOException{  
		SelfSignHttpsClient util = SelfSignHttpsClient.getInstance();  
	    InputStream in = util.doGet("https://mail.bankcomm.com");  
	    String retVal = SelfSignHttpsClient.readStream(in, "GBK");  
	    System.out.println(retVal);  
	}  
	
	@Test
	public void doSSLTest(){
		String content = HttpUtil.getResponseByHttpGet("https://mail.bankcomm.com");
		System.out.println(content); 
	}
	
	@Test
	public void testUseCookieInHttpClient()throws Exception{
		String url = "http://www.datalearner.com/login";
		//String url = "https://passport.csdn.net/account/login";
		//String url = "https://passport.csdn.net/account/verify";
		Map<String,String> params = new HashMap<String,String>();
		params.put("username", "mittermeyer");
		//params.put("username", "liverpool_749@hotmail.com");
		params.put("password", "liverpool2008");
		String needCookieUrl = "http://www.datalearner.com/account";
		//String needCookieUrl = "https://www.csdn.net/";
		String saveCookiePath = "/Users/wufuming/Documents/git/springtest/cookieInfo.txt";
		String result = HttpUtil.useCookieInHttpClient(url, needCookieUrl, params, saveCookiePath);
		System.out.println("result:" + result);
	}

}
