<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath %>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>秒杀页面</title>
    <script type="text/javascript" src="<%=basePath %>/static/js/jquery-1.8.2.min.js"></script>
</head>
<body>
<button id="seckill">立即抢购</button>
<script type="text/javascript">
    $(function () {
        $("#seckill").click(function () {
            $.ajax({
                url: 'http://localhost:8092/webSeckillConsumer/getSeckillByConsumer.do',
                dataType: 'JSON',
                type: 'POST',
                data: {
                    'seckillId': 1,
                    'userId': 4
                },
                success: function (rs) {
                    var status = rs.status;
                    if (status == '0') {
                        window.alert("抢购成功, 请尽快付款!");
                        var orderNo = rs.orderNo;
                        window.location.href = "http://localhost:8082/pages/Cache.jsp?orderNo=" + orderNo;
                    } else {
                        window.alert("抢购失败," + rs.errorMsg);
                    }
                }
            });
        });
    });
</script>
</body>
</html>