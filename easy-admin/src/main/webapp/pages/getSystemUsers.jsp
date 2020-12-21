<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath %>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户信息</title>
</head>
<body>
<%--数据表格--%>
<table id="getSystemUser"></table>

<%--添加系统用户菜单窗口--%>
<div id="addSystemUser">

</div>

<%--编辑系统用户菜单窗口--%>
<div id="editSystemUser">
    <center style="padding-top: 30px">
        <form id="ff2" method="post" action="javascript:void(0)">
            <table cellpadding="5">
                <tr>
                    <td>菜单名:</td>
                    <td><input class="a11" type="text" name="title"></td>
                </tr>
                <tr>
                    <td>跳转链接:</td>
                    <td><input class="a22" type="text" name="url"></td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <a href="#" class="c11">编辑</a>
                        <a href="#" class="c22" id="rs2">重置</a>
                    </td>
                </tr>
            </table>
        </form>
    </center>
</div>

<script type="text/javascript">
    $(function () {
        $('#getSystemUser').datagrid({
            url: '<%=basePath %>/userAuthority/getSystemUser.do',
            rownumbers: true,
            columns: [
                [
                    {field: 'id', title: '主键', width: 150, align: 'center'},
                    {field: 'username', title: '账号', width: 150, align: 'center'},
                    {field: 'root', title: '超级管理员', width: 150, align: 'center'},
                    {field: 'create_time', title: '创建时间', width: 150, align: 'center'},
                    {field: 'last_modify_time', title: '更新时间', width: 150, align: 'center'},
                    {
                        field: 'operation',
                        title: '操作',
                        width: 200,
                        align: 'center',
                        formatter: function (value, row, index) {
                            if (row.root == '1') {
                                return '无操作';
                            } else {
                                return '<a class="c1"></a>';
                            }
                        }
                    }
                ]
            ],
            onLoadSuccess: function () {
                jQuery(".c1").linkbutton({
                    iconCls: 'icon-edit',
                    text: '设置权限',
                    plain: true
                });
            },
            sortName: 'id',
            remoteSort: false,
            fit: true,
            striped: true,
            pagination: true,
            pageList: [5, 10, 20, 30],
            rowStyler: function (index, row) {
                if (index % 2 == 0) {
                    return 'background-color:yellow;';
                }
            },
            toolbar: [{
                iconCls: 'icon-add',
                text: '添加系统用户',
                handler: function () {
                    $("#addUser input[name=username]").val("");
                    $("#addUser input[name=password]").val("");
                    $('#addSystemUser').window('open');
                }
            }, '-', {
                iconCls: 'icon-edit',
                text: '编辑系统用户',
                handler: function () {
                    var length = $("#dg").datagrid("getSelections").length;
                    if (length == 0) {
                        $.messager.alert('提示', '请选择要编辑的记录！');
                    } else if (length == 1) {
                        var row = $("#dg").datagrid("getSelected");
                        $("#ff2 input[name=title]").val(row.title);
                        $("#ff2 input[name=url]").val(row.url);
                        $(".c11").linkbutton({
                            iconCls: 'icon-ok'
                        });
                        $(".c22").linkbutton({
                            iconCls: 'icon-cancel'
                        });
                        $(".a11").validatebox({
                            required: true,
                            validType: 'length[1,10]'
                        });
                        $(".a22").validatebox({
                            required: true,
                            validType: 'url'
                        });
                        $('#editSystemUser').window('open');
                    } else {
                        $.messager.alert('警告', '只能选择一条记录进行编辑！');
                    }
                }
            }, '-', {
                iconCls: 'icon-remove',
                text: '删除',
                handler: function () {
                    var length = $("#dg").datagrid("getSelections").length;
                    if (length == 0) {
                        $.messager.alert('提示', '请选择要删除的记录！');
                    } else {
                        $.messager.confirm('警告', '确认要删除选中的记录吗？', function (rs) {
                            if (rs) {
                                var idStr = "";
                                var ckAttr = $("#dg").datagrid("getSelections");
                                for (var i = 0; i < ckAttr.length; i++) {
                                    idStr += ckAttr[i].id + ",";
                                }
                                $.ajax({
                                    url: "<%=basePath %>/web/abandonWebMenu.do",
                                    type: "post",
                                    dataType: "json",
                                    data: {
                                        "idStr": idStr
                                    },
                                    success: function (rs) {
                                        if (rs) {//删除成功
                                            //2、刷新表格
                                            $("#dg").datagrid('reload')
                                            //3、提示添加成功
                                            $.messager.show({
                                                title: "提示",
                                                msg: "前台菜单删除成功！"
                                            });
                                        } else {//添加失败
                                            $.messager.alert('提示', '前台菜单删除失败，请重试！');
                                        }
                                    }
                                    ,
                                    error: function (err) {

                                    }
                                });
                            }
                        });
                    }
                }
            }]
        });

        //添加系统用户菜单窗口
        $('#addSystemUser').window({
            width: 600,
            height: 400,
            title: '添加系统用户',
            iconCls: 'icon-add',
            draggable: true,
            resizable: false,
            minimizable: false,
            collapsible: false,
            maximizable: false,
            modal: true,
            closed: true,
            href: '<%=basePath %>/pages/addSystemUserForm.jsp'
        });
    });
</script>
</body>
</html>