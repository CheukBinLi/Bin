package Controller;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/***
 * 
 * @author Administrator 错误代码 errorid: -1 用户名密码错误（登录失败） -2 余额不足
 */
@WebService
@SOAPBinding(style = Style.RPC)
public class SmsServiceImpl extends SpringBeanAutowiringSupport {

	public SmsServiceImpl() {
		super();
	}

	@WebMethod
	@WebResult(name = "result")
	public String sendSMS(@WebParam(name = "userName") String userName, @WebParam(name = "password") String password, @WebParam(name = "constent") String constent, @WebParam(name = "numbers") String numbers) {
		return "errornum=&errorid=-1&";

	}

	@WebMethod
	@WebResult(name = "result")
	public String queryBalance(@WebParam(name = "userName") String userName, @WebParam(name = "password") String password) {
		return null;
	}

	@WebMethod
	@WebResult(name = "result")
	// @SOAPBinding(parameterStyle = ParameterStyle.WRAPPED)
	public ArrayList<String> receiveSms(@WebParam(name = "userName") String userName, @WebParam(name = "password") String password) {
		ArrayList<String> al = new ArrayList<String>();
		al.add("暂不支持");
		return al;
	}

	@WebMethod
	@WebResult(name = "result")
	public String prepaid(@WebParam(name = "userName") String userName, @WebParam(name = "password") String password, Object voucher) {
		// TODO Auto-generated method stub
		return "暂不支持";
	}

}
