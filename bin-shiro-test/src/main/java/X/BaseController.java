package X;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class BaseController {

	@RequestMapping({ "**/**", "/**", "**" })
	public ModelAndView getXPage(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("*******************");
		System.out.println(request.getServletPath());
		System.out.println(request.getServerName());
		System.out.println(request.getContextPath());
		return new ModelAndView(request.getServletPath());
	}

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

	public BaseController() {
		super();
		System.err.println("初始化");
	}

}
