package com.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * http请求的工具类
 * 
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
			for (String param : params.keySet()) {
				list.add(new BasicNameValuePair(param, params.get(param)));
			}
			// url格式编码
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

	/*
	 * 读取Cookie的序列化文件，读取后可以直接使用
	 */
	private static CookieStore readCookieStore(String savePath) throws IOException, ClassNotFoundException {
		FileInputStream fs = new FileInputStream(savePath);
		ObjectInputStream ois = new ObjectInputStream(fs);
		CookieStore cookieStore = (CookieStore) ois.readObject();
		ois.close();
		return cookieStore;
	}

	/**
	 * 使用序列化的方式保存CookieStore到本地文件
	 */
	private static void saveCookieStore(CookieStore cookieStore, String savePath) throws IOException {
		FileOutputStream fs = new FileOutputStream(savePath);
		ObjectOutputStream os = new ObjectOutputStream(fs);
		os.writeObject(cookieStore);
		os.close();
	}

	/**
	 * 构建一个无需验证https证书的ssl factory
	 * 
	 * @return
	 * @throws Exception
	 */
	private static SSLConnectionSocketFactory generateHttpsFactory() throws Exception {
		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager xtm = new X509TrustManager() {
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(new TrustSelfSignedStrategy()).build();
		sslcontext.init(new KeyManager[0], new TrustManager[] { xtm }, new SecureRandom());
		sslcontext.init(null, new X509TrustManager[] { xtm }, new SecureRandom());
		SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslcontext, (s, sslSession) -> true);
		return factory;
	}

	private static List<NameValuePair> getParam(Map parameterMap) {
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		Iterator it = parameterMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry parmEntry = (Entry) it.next();
			param.add(new BasicNameValuePair((String) parmEntry.getKey(), (String) parmEntry.getValue()));
		}
		return param;
	}

	/**
	 * 
	 * @param url
	 *            请求url
	 * @param params
	 *            请求参数
	 * @param cookieSavePath
	 *            cookie保存的地址 在httpclient中使用cookie信息访问远程地址
	 * 
	 */
	public static String useCookieInHttpClient(String url, String needCookieUri, Map<String, String> params,
			String cookieSavePath) throws Exception {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		String result = null;
		CloseableHttpClient client = null;
		if(url.indexOf("https")>-1){
			SSLConnectionSocketFactory factory = generateHttpsFactory();
			Registry r = RegistryBuilder.create().register("https", factory).build();
			PoolingHttpClientConnectionManager connPool = new PoolingHttpClientConnectionManager(r);
			connPool.setMaxTotal(200);
			connPool.setDefaultMaxPerRoute(20);
			//HttpHost proxy = new HttpHost("192.168.8.101", 8888,"http");
			//把代理设置到请求配置
	        //RequestConfig defaultRequestConfig = RequestConfig.custom().setProxy(proxy).build();
			client = HttpClients.custom().setConnectionManagerShared(true).setConnectionManager(connPool).setSSLSocketFactory(factory).build();
		}else{
			client = HttpClients.createDefault();
		}
		HttpPost httpPost = new HttpPost(url);
		// 构造自定义Header信息
		List<Header> headerList = new ArrayList<Header>();
		if(url.indexOf("https")>-1){
			headerList.add(new BasicHeader(HttpHeaders.ACCEPT,"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"));
			headerList.add(new BasicHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate, br"));
			headerList.add(new BasicHeader(HttpHeaders.ACCEPT_LANGUAGE,"zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4,ja;q=0.2,de;q=0.2"));
			headerList.add(new BasicHeader(HttpHeaders.CACHE_CONTROL, "max-age=0"));
			headerList.add(new BasicHeader(HttpHeaders.CONNECTION, "keep-alive"));
			headerList.add(new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded"));
			headerList.add(new BasicHeader("DNT", "1"));
			headerList.add(new BasicHeader(HttpHeaders.HOST, "passport.csdn.net"));
			headerList.add(new BasicHeader("Origin", "https://passport.csdn.net"));
			headerList.add(new BasicHeader(HttpHeaders.REFERER,"https://passport.csdn.net/account/login?from=http://my.csdn.net/my/mycsdn"));
			headerList.add(new BasicHeader("Upgrade-Insecure-Requests", "1"));
			headerList.add(new BasicHeader(HttpHeaders.USER_AGENT,
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36"));
		}else{
			headerList.add(new BasicHeader(HttpHeaders.ACCEPT,
					"text/html,application/xhtml+xml,application/xml;q=0.9," + "image/webp,image/apng,*/*;q=0.8"));
			headerList.add(new BasicHeader(HttpHeaders.USER_AGENT,
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36"));
			headerList.add(new BasicHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate"));
			headerList.add(new BasicHeader(HttpHeaders.CACHE_CONTROL, "max-age=0"));
			headerList.add(new BasicHeader(HttpHeaders.CONNECTION, "keep-alive"));
			headerList.add(new BasicHeader(HttpHeaders.ACCEPT_LANGUAGE,
					"zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4,ja;q=0.2," + "de;q=0.2"));
		}
		String loginRequestUrl = null;
		String lt = null;
		String execution = null;
		String eventId = null;
		if(url.indexOf("https")>-1){
			HttpGet httpGet = new HttpGet(url);
			// 执行post请求
			HttpResponse httpResponse = client.execute(httpGet);
			// response实体
			HttpEntity entity = httpResponse.getEntity();
			if (null != entity) {
				result = EntityUtils.toString(entity, "UTF-8");
				Document document = Jsoup.parse(result);
				loginRequestUrl = document.select("form#fm1").attr("action");
				//String[] loginUrl = loginRequestUrl.split(";");
				//System.out.println("loginUrl:"+loginUrl[0]);
				httpPost = new HttpPost(loginRequestUrl);
				//获取三个参数
				lt = document.select("input[name=lt]").attr("value");
				execution = document.select("input[name=execution]").attr("value");
				eventId = document.select("input[name=_eventId]").attr("value");
				params.put("lt", lt);
				params.put("execution", execution);
				params.put("_eventId", eventId);
			}
		}
		for( Header header : headerList ){
		    httpPost.addHeader(header);
		}
		// 获取请求参数
		UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(getParam(params), "UTF-8");
		httpPost.setEntity(postEntity);
		
		try {
			HttpClientContext httpClientContext = HttpClientContext.create();
			// 执行post请求
			HttpResponse httpResponse = client.execute(httpPost,httpClientContext);
			// response实体
			HttpEntity entity = httpResponse.getEntity();
			if (null != entity) {
				result = EntityUtils.toString(entity, "UTF-8");
				System.out.println("result:"+result);
			}
			CookieStore cookieStore = httpClientContext.getCookieStore();
			//这个CookieStore保存了我们的登录信息，我们可以先将它保存到某个本地文件，后面直接读取使用
		    saveCookieStore(cookieStore,cookieSavePath);
		    
		    //下面我们将演示如何使用Cookie来请求，首先我们将之前的Cookie读出来
		    CookieStore cookieStore1 = readCookieStore(cookieSavePath);
			
			
			HttpGet httpGet = new HttpGet(needCookieUri);
			//HttpClient httpClient2 = HttpClientBuilder.create().setDefaultCookieStore(cookieStore1).build();
			HttpResponse httpResponse1 = client.execute(httpGet,httpClientContext);
			// response实体
			entity = httpResponse1.getEntity();
			if (null != entity) {
				result = EntityUtils.toString(entity, "UTF-8");
				result = result.trim();
				if(url.indexOf("https")>-1){
					Document document = Jsoup.parse(result);
					result = document.select("li.gitChat").html();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭流并释放资源
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
