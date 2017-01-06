package com.action;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pojo.User;

@Controller
public class VelocityAction {

	@RequestMapping(value = "/testVelocity")
	public ModelAndView helloVm(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("testVelocity");
		modelAndView.addObject("test", "hello , this is velocity!");
		List<User> list = new ArrayList<>();
		User u = new User();
		u.setId(12123L);
		u.setPassword("afdasfddasf");
		u.setUsername("user1");
		list.add(u);
		u = new User();
		u.setId(22232L);
		u.setPassword("wfmhbbwt");
		u.setUsername("user2");
		list.add(u);
		modelAndView.addObject("list", list);
		Map<String, Object> map = new HashMap<>();
		map.put("aaa", "1111");
		modelAndView.addObject("map", map);
		request.getSession().setAttribute("IsSave","yes");
		return modelAndView;
	}
	
	@RequestMapping(value = "/generateHtml")
	public ModelAndView generateVelocityHtml(HttpServletRequest request)throws Exception{
		VelocityEngine engine = new VelocityEngine();
		engine.setProperty("input.encoding", "UTF-8");
		engine.setProperty("output.encoding", "UTF-8");
		engine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, request.getSession().getServletContext().getRealPath("/WEB-INF/velocity/"));
		engine.init();
		VelocityContext context = new VelocityContext();
		context.put("test", "hello , this is velocity!");
		List<User> list = new ArrayList<>();
		User u = new User();
		u.setId(12123L);
		u.setPassword("afdasfddasf");
		u.setUsername("user1");
		list.add(u);
		u = new User();
		u.setId(22232L);
		u.setPassword("wfmhbbwt");
		u.setUsername("user2");
		list.add(u);
		context.put("list", list);
		Map<String, Object> map = new HashMap<>();
		map.put("aaa", "1111");
		context.put("map", map);
		HttpSession session = request.getSession();
		session.setAttribute("IsSave","yes");
		context.put("request", request);
		context.put("session", session);
		boolean flag = doRender(context, engine, "testVelocity.vm", "test");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("generate");
		modelAndView.addObject("flag", flag);
		return modelAndView;
	}
	
	/**
	 * 根据velocity模版引擎生成对应的html信息
	 * @param context  velocity上下文
	 * @param engine   velocity引擎
	 * @param fileName  要生成的velocity的页面模版
	 * @throws Exception
	 */
	private void renderScreenContent(Context context,VelocityEngine engine,String fileName)throws Exception{
		StringWriter sw = new StringWriter();
		//获取内部真正页面显示内容的velocity模版对象
		Template screenContent = getTemplate(engine, fileName);
		screenContent.merge(context, sw);
		context.put("screen_content", sw.toString());
	}
	
	/**
	 * 根据velocity模版名称获取模版对象
	 * @param engine    velocity引擎
	 * @param name      velocity模版名称
	 * @return
	 * @throws Exception
	 */
	private Template getTemplate(VelocityEngine engine,String name)throws Exception{
		return engine.getTemplate(name,"UTF-8");
	}
	
	/**
	 * 根据velocity模版生成对应的html文件
	 * @param context   velocity上下文
	 * @param engine    velocity引擎
	 * @param fileName  velocity模版名称
	 * @param targetFileName   生成的html文件名称
	 * @return  是否生成成功
	 */
	private boolean doRender(Context context,VelocityEngine engine,String fileName,String targetFileName){
		FileWriter writer = null;
		try {
			renderScreenContent(context,engine,fileName);
			//获取配置velocity布局模版的文件路径
			String layoutUrl = (String)context.get("layout");
			if(StringUtils.isBlank(layoutUrl)){
				layoutUrl = "layout/layout.vm";
			}
			//获取布局的模版对象
			Template template = getTemplate(engine, layoutUrl);
			writer = new FileWriter("/Users/mittermeyer/Documents/git/springtest/" + targetFileName + ".html");
			//加入对macro函数的解析
			List<String> marcoList = new ArrayList<>();
			marcoList.add("macro/macro.vm");
			
			//解析模版对象生成html信息,写入文件
			template.merge(context, writer,marcoList);
			writer.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
		return false;
	}

}
