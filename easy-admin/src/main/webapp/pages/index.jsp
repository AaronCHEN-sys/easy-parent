<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath %>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>欢迎来到易卖网后台</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/static/js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/static/js/easyui/themes/icon.css">
    <script type="text/javascript" src="<%=basePath %>/static/js/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>/static/js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>/static/js/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'north',title:'北丐',split:true" style="height:100px;"></div>
<div data-options="region:'south',split:true" style="height: 60px;">
    <center>&copy;版权所有|维权必究</center>
</div>
<div data-options="region:'east',title:'东邪',split:true" style="width:100px;"></div>
<div data-options="region:'west',title:'菜单栏',split:true" style="width:200px;">
    <ul id="tt"></ul>
</div>
<div data-options="region:'center',title:'中神通'">
    <div id="tb" class="easyui-tabs" data-options="fit: true">
        <div data-options="title:'首页',iconCls:'icon-house'">
            <center style="padding-top: 150px;font-size: 36px;color: blue;font-weight: bolder;">欢迎使用易卖网后台管理系统</center>
        </div>
    </div>
</div>
<script type="text/javascript">
    $('#tt').tree({
        url: '<%=basePath %>/user/getAuthorityByUsername.do',
        lines: true,
        onClick: function () {
            $('#tt').tree({
                lines: true,
                onClick: function (node) {
                    console.log(node);
                    var flag = $("#tb").tabs('exists', node.text);
                    if (!flag && node.url != null) {//选项卡不存在
                        $("#tb").tabs('add', {
                            title: node.text,
                            iconCls: node.iconCls,
                            closable: true,
                            href: "<%=basePath %>/pages/" + node.url
                        });
                    } else {//选项卡存在，则选中
                        $("#tb").tabs('select', node.text);
                    }
                }
            });
        }
    });
</script>
</body>
</html>