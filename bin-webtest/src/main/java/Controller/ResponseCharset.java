package Controller;

import javax.servlet.http.HttpServletResponse;

public class ResponseCharset {
	// 转码
	public static HttpServletResponse responseChangeEncodeUTF8(HttpServletResponse response) {
		// 编码
		response.setContentType("text/html;charset=UTF-8");
		// 以下两句是缓存
		// HTTP1.1
		response.setHeader("Cache-Control", "no-store");
		// HTTP1.0
		response.setHeader("Pragma", "no-cache");
		// prevents catching at proxy server
		response.setDateHeader("Expires", 0);
		return response;
	}
}
