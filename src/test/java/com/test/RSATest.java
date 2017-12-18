package com.test;

import org.apache.commons.codec.binary.Base64;

import com.util.RSAEncrypt;
import com.util.RSASignature;

public class RSATest {
	
	public static void main(String[] args) throws Exception {  
        String filepath="/Users/mittermeyer/Documents/git/springtest";  
  
        //RSAEncrypt.genKeyPair(filepath);  
          
        Base64 base64 = new Base64();
        System.out.println("--------------公钥加密私钥解密过程-------------------");  
        String plainText="ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密"
        		+ "ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密"
        		+ "ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密"
        		+ "ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密ihep_公钥加密私钥解密";  
        //公钥加密过程  
        byte[] cipherData=RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(RSAEncrypt.loadPublicKeyByFile(filepath)),plainText.getBytes());  
        String cipher=new String(base64.encode(cipherData),"UTF-8");  
        //私钥解密过程  
        byte[] res=RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile(filepath)), base64.decode(cipher));  
        String restr=new String(res);  
        System.out.println("原文："+plainText);  
        System.out.println("加密："+cipher);  
        System.out.println("解密："+restr);  
        System.out.println();  
          
        System.out.println("--------------私钥加密公钥解密过程-------------------");  
        //plainText="ihep_私钥加密公钥解密";  
        //私钥加密过程  
        cipherData=RSAEncrypt.encrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile(filepath)),plainText.getBytes());  
        cipher=new String(base64.encode(cipherData),"UTF-8");  
        //公钥解密过程  
        res=RSAEncrypt.decrypt(RSAEncrypt.loadPublicKeyByStr(RSAEncrypt.loadPublicKeyByFile(filepath)), base64.decode(cipher));  
        restr=new String(res);  
        System.out.println("原文："+plainText);  
        System.out.println("加密："+cipher);  
        System.out.println("解密："+restr);  
        System.out.println();  
          
        System.out.println("---------------私钥签名过程------------------");  
        String content="ihep_这是用于签名的原始数据";  
        String signstr=RSASignature.sign(content,RSAEncrypt.loadPrivateKeyByFile(filepath));  
        System.out.println("签名原串："+content);  
        System.out.println("签名串："+signstr);  
        System.out.println();  
          
        System.out.println("---------------公钥校验签名------------------");  
        System.out.println("签名原串："+content);  
        System.out.println("签名串："+signstr);  
          
        System.out.println("验签结果："+RSASignature.doCheck(content, signstr, RSAEncrypt.loadPublicKeyByFile(filepath)));  
        System.out.println();  
          
    }  

}
