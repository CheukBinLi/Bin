<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<body>
	<h2>网页代理</h2>
	<form action="proxy.do" method="post">
		<input type="text" placeholder="www.baidu.com" name="url">
		 <input type="button" value="浏览" onclick="xxx();">
	</form>
</body>
</html>
<script>
	function xxx() {
		var form = document.getElementsByTagName("form")[0];
		var input = document.getElementsByTagName("input")[0];
		if (input.value.length > 0)
			form.submit();
	}
</script>


