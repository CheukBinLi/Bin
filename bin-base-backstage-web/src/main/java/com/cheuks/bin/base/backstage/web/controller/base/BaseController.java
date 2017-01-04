package com.cheuks.bin.base.backstage.web.controller.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({ "/*", "/*/**", "/", "" })
@Scope("prototype")
public class BaseController {

	@RequestMapping({ "login" })
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("login");
	}

	@RequestMapping({ "logout" })
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("login");
	}

}
