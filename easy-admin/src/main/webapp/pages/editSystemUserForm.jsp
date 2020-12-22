<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath %>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>编辑系统用户</title>
</head>
<body>
<center style="padding-top: 50px">
    <table width="400px" id="editSystemUser">
        <tr v-for="firstMenu in firstMenuList">
            <td>
                <strong>
                    <input type="hidden" name="firstId"
                           v-bind:value="firstMenu.firstId">{{firstMenu.firstText}}&nbsp;&nbsp;
                </strong>
            </td>
            <td v-for="secondMenu in firstMenu.secondMenuList">
                <input type="checkbox" name="secondId" v-bind:value="secondMenu.secondId">{{secondMenu.secondText}}&nbsp;&nbsp;
            </td>
        </tr>
        <tr>
            <td>
                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">确认</a>
            </td>
        </tr>
    </table>
</center>

<h3>${param.userId}</h3>

<script type="text/javascript">
    const vue1 = new Vue({
        el: "#editSystemUser",
        data: {
            firstMenuList: []
        },
        mounted() {
            this.$http.post("<%=basePath %>/userAuthority/getFirstAndSecondAuthorityRelation.do").then(
                function (rs) {
                    console.log(rs.body);
                    this.firstMenuList = rs.body;
                }
            );
        }
    });
</script>
</body>
</html>