package Controller;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.jws.WebParam;
import java.io.Serializable;

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
