package com.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * http请求的工具类
 * @author 吴福明
 *
 */
public class HttpUtil {
	
	/**
	 * 通过http的post方法请求http地址
	 * 
	 * @param url
	 * @return 获取请求地址的返回字符串
	 */
	public static String getResponseByHttpPost(String url, Map<String, String> params) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String result = null;
		try {
			HttpPost post = new HttpPost(url);
			// 创建参数列表
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			for(String param : params.keySet()){
				list.add(new BasicNameValuePair(param, params.get(param)));
			}
			//url格式编码
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list, "UTF-8");
			post.setEntity(uefEntity);
			System.out.println("POST 请求...." + post.getURI());
			// 执行请求
			CloseableHttpResponse httpResponse = httpClient.execute(post);
			try {
				HttpEntity entity = httpResponse.getEntity();
				if (null != entity) {
					result = EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				httpResponse.close();
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	/**
	 * 通过http的get方法请求http地址
	 * 
	 * @param url
	 * @return 获取请求地址的返回字符串
	 */
	public static String getResponseByHttpGet(String url) {
		// 创建默认的httpClient实例
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String result = null;
		try {
			// 用get方法发送http请求
			HttpGet get = new HttpGet(url);
			System.out.println("执行get请求:...." + get.getURI());
			CloseableHttpResponse httpResponse = null;
			// 发送get请求
			httpResponse = httpClient.execute(get);
			try {
				// response实体
				HttpEntity entity = httpResponse.getEntity();
				if (null != entity) {
					result = EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				httpResponse.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * 用http的方式上传文件
	 * 
	 * @param filePath
	 *            素材文件路径
	 * @param url
	 *            上传地址
	 * @return 媒体文件上传后，获取的唯一标识
	 * @throws FileNotFoundException
	 */
	public static String uploadFileByHttp(String filePath, String url) throws FileNotFoundException {

		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new FileNotFoundException("文件不存在");
		}
		// 设置http链接
		URL urlObj = null;
		OutputStream out = null;
		DataInputStream in = null;
		BufferedReader reader = null;
		String result = null;
		try {
			urlObj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			String boundary = "---------7d4a6d158c9";
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

			StringBuffer sb = new StringBuffer();
			sb.append("--");
			sb.append(boundary);
			sb.append("\r\n");
			sb.append("Content-Disposition:form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
			sb.append("Content-Type:application/octet-stream\r\n\r\n");

			byte[] head = sb.toString().getBytes("UTF-8");
			// 获得上传输出流
			out = new DataOutputStream(conn.getOutputStream());
			// 输出http头信息
			out.write(head);
			// 文件上传
			in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				// 输出读取到的文件流数据
				out.write(bufferOut, 0, bytes);
			}
			// 输出尾部信息
			byte[] foot = ("\r\n").getBytes("UTF-8");
			out.write(foot);

			byte[] end_data = ("\r\n--" + boundary + "--\r\n").getBytes();
			out.write(end_data);
			out.flush();

			StringBuffer buffer = new StringBuffer();
			// 读取上传响应结果
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			result = buffer.toString();
		} catch (IOException e) {
			// TODO: handle exception
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return result;
	}

}
