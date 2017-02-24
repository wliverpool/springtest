package com.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.util.Token;

@Controller
@RequestMapping("/multi")
public class MultiSubmitController {

	@RequestMapping("/toForm")
	@Token(save = true)
	public String form(Model model) {
		return "multi/form";
	}

	@RequestMapping("/saveForm")
	@Token(remove = true)
	public String save(Model model, HttpServletRequest request){
		String loginName = request.getParameter("login_name");
		String password = request.getParameter("login_psw");
		System.out.println("====================" + loginName + ":" + password + "=====================");
		return "redirect:/index.jsp";
	}

}
