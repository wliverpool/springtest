package com.util;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Base64;

/**
 * RSA签名验签类
 */
public class RSASignature {
	
	private static final String CHARSET_UTF_8 = "utf-8";

	/**
	 * 签名算法
	 */
	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

	/**
	 * RSA签名
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

			KeyFactory keyf = KeyFactory.getInstance("RSA");
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

	public static String sign(String content, String privateKey) {
		try {
			Base64 base64 = new Base64();
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(base64.decode(privateKey));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
			signature.initSign(priKey);
			signature.update(content.getBytes());
			byte[] signed = signature.sign();
			return new String(base64.encode(signed),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * RSA验签名检查
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
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
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

	public static boolean doCheck(String content, String sign, String publicKey) {
		try {
			Base64 base64 = new Base64();
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = base64.decode(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

			signature.initVerify(pubKey);
			signature.update(content.getBytes());

			boolean bverify = signature.verify(base64.decode(sign));
			return bverify;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	
	/**
     * verifySign 方法
     * 
     * @param params 参数
     * @param publicKey 参数
     * @param sign 参数
     * @return
     */
    public static boolean verifySign(Map<String, String> params, String publicKey, String sign) {
        // 过滤空值、sign与sign_type参数
        Map<String, String> sParaNew = paraFilter(params);
        // 获取待签名字符串
        String preSignStr = createLinkString(sParaNew);
        // 获得签名验证结果
        return doCheck(preSignStr, sign, publicKey, CHARSET_UTF_8);
    }
    
    /**
     * paraFilter 方法
     * 
     * @param sArray 参数
     * @return
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new LinkedHashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        Iterator<Entry<String, String>> ite = sArray.entrySet().iterator();
        while (ite.hasNext()) {
            Entry<String, String> entry = ite.next();
            String key = entry.getKey();
            String value = entry.getValue();
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }
    
    /**
     * createLinkString 方法
     * 
     * @param params 参数
     * @return
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        StringBuffer sbPreStr = new StringBuffer();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            // 拼接时，不包括最后一个&字符
            if (i == keys.size() - 1) {
                sbPreStr.append(key);
                sbPreStr.append("=");
                sbPreStr.append(value);
            } else {
                sbPreStr.append(key);
                sbPreStr.append("=");
                sbPreStr.append(value);
                sbPreStr.append("&");
            }
        }

        return String.valueOf(sbPreStr);
    }

}
