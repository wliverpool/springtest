package com.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

}
