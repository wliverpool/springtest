package com.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * DSA算法帮助类 一般用于数字签名和认证。和RSA不同之处在于它不能用作加密和解密，也不能进行密钥交换，只用于签名,生成签名它比RSA要快很多.
 * @author wufuming
 *
 */

public class DSAEncrypt {
	
	/**
	 * 签名算法
	 */
	public static final String SIGN_ALGORITHMS = "SHA1WithDSA";
	
	/** 
     * 随机生成密钥对 
     */  
    public static void genKeyPair(String filePath) {  
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象  
        KeyPairGenerator keyPairGen = null;  
        try {  
            keyPairGen = KeyPairGenerator.getInstance("DSA");  
        } catch (NoSuchAlgorithmException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }   
        keyPairGen.initialize(512);  
        // 生成一个密钥对，保存在keyPair中  
        KeyPair keyPair = keyPairGen.generateKeyPair();  
        // 得到私钥  
        DSAPrivateKey privateKey = (DSAPrivateKey) keyPair.getPrivate();  
        // 得到公钥  
        DSAPublicKey publicKey = (DSAPublicKey) keyPair.getPublic();  
        try {  
        	Base64 base64 = new Base64();
            // 得到公钥字符串  
            String publicKeyString = new String (base64.encode(publicKey.getEncoded()),"UTF-8");  
            // 得到私钥字符串  
            String privateKeyString = new String(base64.encode(privateKey.getEncoded()),"UTF-8");  
            // 将密钥对写入到文件  
            FileWriter pubfw = new FileWriter(filePath + "/dsa_publicKey.keystore");  
            FileWriter prifw = new FileWriter(filePath + "/dsa_privateKey.keystore");  
            BufferedWriter pubbw = new BufferedWriter(pubfw);  
            BufferedWriter pribw = new BufferedWriter(prifw);  
            pubbw.write(publicKeyString);
            System.out.println("dsa publicKeyString:"+publicKeyString);
            pribw.write(privateKeyString);  
            System.out.println("dsa privateKeyString:"+privateKeyString);
            pubbw.flush();  
            pubbw.close();  
            pubfw.close();  
            pribw.flush();  
            pribw.close();  
            prifw.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
	
    /** 
     * 从文件中输入流中加载公钥 
     *  
     * @param in 
     *            公钥输入流 
     * @throws Exception 
     *             加载公钥时产生的异常 
     */  
    public static String loadPublicKeyByFile(String path) throws Exception {  
        try {  
            BufferedReader br = new BufferedReader(new FileReader(path  
                    + "/dsa_publicKey.keystore"));  
            String readLine = null;  
            StringBuilder sb = new StringBuilder();  
            while ((readLine = br.readLine()) != null) {  
                sb.append(readLine);  
            }  
            br.close();  
            return sb.toString();  
        } catch (IOException e) {  
            throw new Exception("dsa公钥数据流读取错误");  
        } catch (NullPointerException e) {  
            throw new Exception("dsa公钥输入流为空");  
        }  
    }  
    
    /** 
     * 从字符串中加载dsa公钥 
     *  
     * @param publicKeyStr 
     *            公钥数据字符串 
     * @throws Exception 
     *             加载公钥时产生的异常 
     */  
    public static DSAPublicKey loadDSAPublicKeyByStr(String dsaPublicKeyStr)  
            throws Exception {  
        try {  
        	Base64 base64 = new Base64();
            byte[] buffer = base64.decode(dsaPublicKeyStr);  
            KeyFactory keyFactory = KeyFactory.getInstance("DSA");  
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);  
            return (DSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("dsa公钥非法");  
        } catch (NullPointerException e) {  
            throw new Exception("dsa公钥数据为空");  
        }  
    }  
    
    /** 
     * 从文件中加载dsa私钥 
     *  
     * @param keyFileName 
     *            私钥文件名 
     * @return 是否成功 
     * @throws Exception 
     */  
    public static String loadDSAPrivateKeyByFile(String path) throws Exception {  
        try {  
            BufferedReader br = new BufferedReader(new FileReader(path  
                    + "/dsa_privateKey.keystore"));  
            String readLine = null;  
            StringBuilder sb = new StringBuilder();  
            while ((readLine = br.readLine()) != null) {  
                sb.append(readLine);  
            }  
            br.close();  
            return sb.toString();  
        } catch (IOException e) {  
            throw new Exception("dsa私钥数据读取错误");  
        } catch (NullPointerException e) {  
            throw new Exception("dsa私钥输入流为空");  
        }  
    }  
  
    /** 
     * 从字符串中加载dsa私钥 
     *  
     * @param keyFileName 
     *            私钥文件名 
     * @return 是否成功 
     * @throws Exception 
     */  
    public static DSAPrivateKey loadDSAPrivateKeyByStr(String dsaPrivateKeyStr)  
            throws Exception {  
        try {  
        	Base64 base64 = new Base64();
            byte[] buffer = base64.decode(dsaPrivateKeyStr);  
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);  
            KeyFactory keyFactory = KeyFactory.getInstance("DSA");  
            return (DSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("dsa私钥非法");  
        } catch (NullPointerException e) {  
            throw new Exception("dsa私钥数据为空");  
        }  
    } 
    
    /**
	 * DSA签名
	 * 
	 * @param content
	 *            待签名数据
	 * @param privateKey
	 *            商户私钥
	 * @param encode
	 *            字符集编码
	 * @return 签名值
	 */
	public static String sign(String content, String privateKey, String encode) {
		try {
			Base64 base64 = new Base64();
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(base64.decode(privateKey));

			KeyFactory keyf = KeyFactory.getInstance("DSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);

			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

			signature.initSign(priKey);
			signature.update(content.getBytes(encode));

			byte[] signed = signature.sign();

			return new String(base64.encode(signed),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * DSA验签名检查
	 * 
	 * @param content
	 *            待签名数据
	 * @param sign
	 *            签名值
	 * @param publicKey
	 *            分配给开发商公钥
	 * @param encode
	 *            字符集编码
	 * @return 布尔值
	 */
	public static boolean doCheck(String content, String sign, String publicKey, String encode) {
		try {
			Base64 base64 = new Base64();
			KeyFactory keyFactory = KeyFactory.getInstance("DSA");
			byte[] encodedKey = base64.decode(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

			signature.initVerify(pubKey);
			signature.update(content.getBytes(encode));

			boolean bverify = signature.verify(base64.decode(sign));
			return bverify;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
