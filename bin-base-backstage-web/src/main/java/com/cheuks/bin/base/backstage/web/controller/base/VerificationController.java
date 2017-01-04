package com.cheuks.bin.base.backstage.web.controller.base;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cheuks.bin.base.web.util.Message;
import com.cheuks.bin.base.web.util.RandomImage;
import com.cheuks.bin.base.web.util.ResponseCharset;

@Controller
@RequestMapping({ "/*", "/*/**", "/", "" })
@Scope("prototype")
public class VerificationController {

	@Autowired
	private RandomImage randomImage;

	// 验证码
	@RequestMapping(value = "verificationCode")
	public void ramdonImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String verificationCode = null;
		String tempVerificationCode = null;
		if (null == (verificationCode = request.getParameter("verificationCode"))
				|| null == (tempVerificationCode = SecurityUtils.getSubject().getSession()
						.getAttribute("verificationCode").toString()))
			SecurityUtils.getSubject().getSession().setAttribute("verificationCode",
					randomImage.randomImageWriter(response, request));
		else {
			ResponseCharset.responseChangeEncodeUTF8(response).getWriter()
					.write(new Message(verificationCode.toUpperCase().equals(tempVerificationCode)) {
					}.toJson());
		}
		return;
	}

	@RequestMapping(value = "verification")
	public void verification(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Subject token = SecurityUtils.getSubject();
		System.err.println("验证通过" + (token.isAuthenticated() ? "OK" : "FAIL"));
		response.getWriter().write(new Message(token.isAuthenticated()) {
		}.toJson());
	}

}
