<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath %>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>添加系统用户时授权</title>
</head>
<body>
<center style="padding-top: 30px">
    <form id="addUser" method="post" action="javascript:void(0)">
        <table cellpadding="5">
            <tr>
                <td>账号:</td>
                <td><input class="b1" type="text" name="username"></td>
            </tr>
            <tr>
                <td>密码:</td>
                <td><input class="b2" type="text" name="password" value="12345678cl"></td>
            </tr>
            <tr>
                <td>权限设置</td>
                <td>
                    <ul id="getAuthorityTree"></ul>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <a href="#" class="d1" id="addSystemUserOk">添加</a>
                    <a href="#" class="d2" id="resetSystemUser">重置</a>
                </td>
            </tr>
        </table>
    </form>
</center>
<script type="text/javascript">
    $(function () {

        //加载权限树
        $('#getAuthorityTree').tree({
            url: '<%=basePath %>/userAuthority/getFirstAuthority.do',
            lines: true,
            checkbox: true
        });

        $(".d1").linkbutton({
            iconCls: 'icon-ok'
        });
        $(".d2").linkbutton({
            iconCls: 'icon-cancel'
        });

        $(".b1").validatebox({
            required: true,
            validType: 'length[1,10]'
        });
        $(".b2").validatebox({
            required: true,
            validType: 'url'
        });

        /*确认添加系统用户*/
        $("#addSystemUserOk").click(function () {
            var cks = $('#getAuthorityTree').tree('getChecked', ['indeterminate', 'checked']);
            var menuIds = "";
            for (var i in cks) {
                menuIds += cks[i].id + ",";
            }
            $.ajax({
                url: "<%=basePath %>/userAuthority/addSystemUser.do",
                type: "post",
                dataType: "json",
                data: {
                    "username": $("#addUser input[name=username]").val(),
                    "password": $("#addUser input[name=password]").val(),
                    "menuIds": menuIds
                },
                success: function (rs) {
                    if (rs) {//添加成功
                        //1、关闭添加前台菜单窗口
                        $("#addSystemUser").window('close');
                        //2、刷新表格
                        $("#getSystemUser").datagrid('reload')
                        //3、提示添加成功
                        $.messager.show({
                            title: "提示",
                            msg: "前台菜单添加成功！"
                        });
                    } else {//添加失败
                        $.messager.alert('提示', '前台菜单添加失败，请重试！');
                    }
                },
                error: function (err) {

                }
            });
        });
    });

    /*添加系统用户的重置按钮*/
    $("#resetSystemUser").click(function () {
        $("#addUser input[type=text]").val("");
        $(".b1").focus();
    });

</script>
</body>
</html>