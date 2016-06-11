<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
${data}
<script type="text/javascript" src="jquery-1.9.1.min.js"></script>
<script>
    var src = $("a");
    for (var a = 0; a < src.length; a++) {
        var x = $(src[a]);
        var h = x.attr("href") + "";
        var aSrc = x.attr("href");
        if (h.indexOf("http") >= 0)
            x.attr("href", 'http://192.168.168.219:8080/bin-webtest/proxy.do?url=' + aSrc);
        //alert(x.attr("href"));
        //alert(aSrc);
        //break;
        else {
            x.attr("href", 'http://192.168.168.219:8080/bin-webtest/proxy.do?url=http://${url}/' + aSrc);
        }

    }
</script>
