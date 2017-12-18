package com.allinfinance.framework.constant;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetPropertytl {
	private final static Logger logger = LoggerFactory.getLogger(GetPropertytl.class);
	private static String configFileName="tlcommon.properties"; 
	private static String entityId;
	private static String productId;
	private static String aPublicKeyN;
	private static String taglist_o;
	private static String taglist;
	public GetPropertytl(){
		
	}
	public static void getPropertytl(){
		Properties prop = new Properties();     
	    try{
	        InputStream in = GetPropertytl.class.getClassLoader().getResourceAsStream(configFileName);
	        prop.load(in);     ///加载属性列表
	        Iterator<String> it=prop.stringPropertyNames().iterator();
	        while(it.hasNext()){
	            String key=it.next();
	            System.out.println(key+":"+prop.getProperty(key));
	        }
	        in.close();
	    }
	    catch(Exception e){
	        System.out.println(e);
	    }
	    entityId = prop.getProperty("entityId");
	    productId = prop.getProperty("productId");
	    aPublicKeyN = prop.getProperty("aPublicKeyN");
	    taglist_o = prop.getProperty("taglist_o");
	    taglist = prop.getProperty("taglist");
	}
	public static String getEntityId() {
		if(entityId == null){
			getPropertytl();
		}
		return entityId;
	}
	public static void setEntityId(String entityId) {
		GetPropertytl.entityId = entityId;
	}
	public static String getProductId() {
		if(productId == null){
			getPropertytl();
		}
		return productId;
	}
	public static void setProductId(String productId) {
		GetPropertytl.productId = productId;
	}
	public static String getAPublicKeyN() {
		if(aPublicKeyN == null){
			getPropertytl();
		}
		return aPublicKeyN;
	}
	public static void setAPublicKeyN(String aPublicKeyN) {
		GetPropertytl.aPublicKeyN = aPublicKeyN;
	}
	public static String getTaglist() {
		if(taglist == null){
			getPropertytl();
		}
		return taglist;
	}

	public static void setTaglist(String taglist) {
		GetPropertytl.taglist = taglist;
	}
	public static void setTaglist_o(String taglist_o) {
		GetPropertytl.taglist_o = taglist_o;
	}
	
	public static String getTaglist_o() {
		if(taglist_o == null){
			getPropertytl();
		}
		return taglist_o;
	}
	public static Logger getLogger() {
		return logger;
	}
} 
