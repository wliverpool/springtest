package com.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

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

}
