package com.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 测试浏览器缓存
 * 
 * @author 吴福明
 *
 */

@Controller
@RequestMapping("/browserCache")
public class BrowserCacheAction {

	@RequestMapping("/cache")
	public ResponseEntity<String> cache(HttpServletRequest request, 
			//为了方便测试，此处传入文档最后修改时间，实际应用时可以使用如商品的最后修改时间等替代。
			@RequestParam("millis") long lastModifiedMillis, 
			//浏览器验证文档内容是否修改时传入的Last-Modified
			@RequestHeader(value = "If-Modified-Since", required = false) Date ifModifiedSince) { 
		//当前系统时间
		long now = System.currentTimeMillis(); 
		//文档可以在浏览器端/proxy上缓存多久
		long maxAge = 20; 
		//判断内容是否修改了，此处使用等值判断
		if (ifModifiedSince != null && ifModifiedSince.getTime() == lastModifiedMillis) {
			return new ResponseEntity<String>(HttpStatus.NOT_MODIFIED);
		}
		
		DateFormat gmtDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
		String body = "<a href=''>点击访问当前链接</a>";
		MultiValueMap<String, String> headers = new HttpHeaders(); 
		headers.add("Content-Type", "text/html; charset=utf-8");
		//文档修改时间
		headers.add("Last-Modified", gmtDateFormat.format(new Date(lastModifiedMillis))); 
		//当前系统时间
		headers.add("Date", gmtDateFormat.format(new Date(now))); 
		// 过期时间 http 1.0支持
		headers.add("Expires", gmtDateFormat.format(new Date(now + maxAge))); 
		// 文档生存时间 http 1.1支持
		headers.add("Cache-Control", "max-age=" + maxAge);
		return new ResponseEntity<String>(body, headers, HttpStatus.OK);
		
	}
	
	@RequestMapping("/cacheEtag")
	public ResponseEntity<String> cacheEtag(
			HttpServletRequest request,
			HttpServletResponse response, 
			//浏览器验证文档内容的实体 If-None-Match
			@RequestHeader (value = "If-None-Match", required = false) String ifNoneMatch) { 
		//当前系统时间
		long now = System.currentTimeMillis(); 
		//文档可以在浏览器端/proxy上缓存多久
		long maxAge = 10; 
		String body = "<a href=''>点击访问当前链接</a>"; 
		//弱实体
		String etag = "W/\"" + DigestUtils.md5Hex(body) + "\""; 
		//生成文档的内容与浏览器验证文档内容的实体没有区别时
		if(StringUtils.equals(ifNoneMatch, etag)) { 
			return new ResponseEntity<String>(HttpStatus.NOT_MODIFIED);
		}
		DateFormat gmtDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
		MultiValueMap<String, String> headers = new HttpHeaders(); 
		headers.add("Content-Type", "text/html; charset=utf-8");
		//ETag http 1.1支持 用于发送到服务端进行内容变更验证的，第一次请求时会发送
		headers.add("ETag", etag);
		//当前系统时间
		headers.add("Date", gmtDateFormat.format(new Date(now))); 
		//文档生存时间 http 1.1支持
		headers.add("Cache-Control", "max-age=" + maxAge); 
		return new ResponseEntity<String>(body, headers, HttpStatus.OK);
	}

}
