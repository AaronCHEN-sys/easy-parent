<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath %>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>结算页面</title>
    <script type="text/javascript" src="<%=basePath %>/static/js/jquery-1.8.2.min.js"></script>
</head>
<body>
<h1>正在结算中......</h1>
<input type="hidden" name="orderNo" value="${param.orderNo}">
<script type="text/javascript">
    function aaa() {
        $.ajax({
            url: 'http://localhost:8092/order/confirmOrder.do',
            dataType: 'JSON',
            type: 'POST',
            data: {
                'orderNo': $("input[name=orderNo]").val()
            },
            success: function (rs) {
                if (rs) {
                    window.location.href = "http://localhost:8082/pages/CompleteSeckill.jsp";
                } else {
                    window.location.href = "http://localhost:8082/pages/Cache.jsp?orderNo=${param.orderNo}";
                }
            }
        });
    }

    window.setInterval('aaa()', 2000);
</script>
</body>
</html>