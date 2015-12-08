<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isELIgnored="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
${data.toString()}
</html>
<script>
	//alert("${url}");
	//var body = document.getElementsByTagName("body");
	//var x = '<div style="position: relative; z-index: 10010; height: 200px; display: block;">内容:</div><div>';
	//body.insertAdjacentHTML('beforebegin', x);
	//alert(123);
	var as = document.getElementsByTagName("a");
	alert(as.length);
	alert(as[0].getAttribute('href'));
	as[0].setAttribute('href', '123');
	alert(as[0].getAttribute('href'));
	for (var a = 0; a < as.length; a++) {
		//if(as[a].getAttribute('href').indexOf("http://")>0) 
		as[a].setAttribute('href','http://localhost:8080/bin-webtest/proxy.do?url='+as[a].getAttribute('href'));
		//else
		//	as[a].setAttribute('href','http://localhost:8080/bin-webtest/proxy.do?url=http://${url}'+as[a].getAttribute('href'));

		//console.log("xxxxxxxxxxxx:"+as[a].getAttribute('href'));
	}
	//alert(as.length);
</script>