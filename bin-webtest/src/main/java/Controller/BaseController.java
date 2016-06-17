package Controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping({ "/*", "/*/**", "/", "" })
@Scope("prototype")
public class BaseController {

	@RequestMapping("{path}")
	public ModelAndView basePath(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("path") String path) throws IOException {
		String url = request.getParameter("url");
		if ("proxy".equals(path)) {
			return new ModelAndView("proxy", getParams(request)).addObject("data", JspProxy.getProxy(url));
		}
		return new ModelAndView(path, getParams(request));
	}

//	@RequestMapping({ "**/**", "/**" })
//	public ModelAndView getXPage(HttpServletRequest request, HttpServletResponse response) {
//		System.out.println("*******************");
//		System.out.println(request.getServletPath());
//		System.out.println(request.getServerName());
//		System.out.println(request.getContextPath());
//		return new ModelAndView(request.getServletPath());
//	}

	@RequestMapping({ "/login" })
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		Subject user = SecurityUtils.getSubject();
		String username = request.getParameter("name");
		System.err.println(username);
		if (null == username)
			return new ModelAndView(request.getServletPath());
		String password = request.getParameter("password");
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		user.login(token);
		return new ModelAndView("/main");
	}

	protected final Map<String, Object> getParams(HttpServletRequest request) {
		Enumeration<String> en = request.getParameterNames();
		Map<String, Object> map = new HashMap<String, Object>();
		String name;
		while (en.hasMoreElements()) {
			name = en.nextElement();
			map.put(name, request.getParameter(name));
		}
		return map;
	}

}
