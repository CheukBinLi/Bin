package Controller;

import java.io.Serializable;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.servlet.annotation.WebServlet;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Servlet implementation class A
 */
//@WebServlet
//@SOAPBinding(style = Style.RPC)
public class A extends SpringBeanAutowiringSupport implements Serializable {
	private static final long serialVersionUID = 1L;

//	@WebMethod
//	@WebResult(name = "result")
	public String sendSMS(@WebParam(name = "userName") String userName, @WebParam(name = "password") String password, @WebParam(name = "constent") String constent, @WebParam(name = "numbers") String numbers) {

		return "aaa";
	}
}
