<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath %>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>横向菜单</title>
</head>
<body>
<%--数据表格--%>
<table id="dg"></table>

<%--添加前台横向菜单窗口--%>
<div id="frontWin">
    <center style="padding-top: 30px">
        <form id="ff" method="post" action="javascript:void(0)">
            <table cellpadding="5">
                <tr>
                    <td>菜单名:</td>
                    <td><input class="a1" type="text" name="title"></td>
                </tr>
                <tr>
                    <td>跳转链接:</td>
                    <td><input class="a2" type="text" name="url"></td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <a href="#" class="c1">添加</a>
                        <a href="#" class="c2" id="rs">重置</a>
                    </td>
                </tr>
            </table>
        </form>
    </center>
</div>


<%--编辑前台横向菜单窗口--%>
<div id="frontWinEdit">
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

        //数据表格
        $("#dg").datagrid({
            url: '<%=basePath %>/web/getWebMenu.do',
            rownumbers: true,
            columns: [
                [
                    {checkbox: true},
                    {field: 'id', title: '主键', width: 150, align: 'center', sortable: true},
                    {field: 'title', title: '菜单名', width: 150, align: 'center'},
                    {field: 'url', title: '跳转链接', width: 200, align: 'center'},
                    {field: 'last_modify_time', title: '更新时间', width: 200, align: 'center'},
                    {
                        title: '操作', width: 150,
                        formatter: function (value, row, index) {
                            if (row.user) {
                                return row.user.name;
                            } else {
                                return value;
                            }
                        }
                    }
                ]
            ],
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
                text: '添加',
                handler: function () {
                    $(".c1").linkbutton({
                        iconCls: 'icon-ok'
                    });
                    $(".c2").linkbutton({
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
                    $('#frontWin').window('open');
                }
            }, '-', {
                iconCls: 'icon-edit',
                text: '编辑',
                handler: function () {
                    var length = $("#dg").datagrid("getSelections").length;
                    if (length == 0) {
                        $.messager.alert('提示', '请选择要编辑的记录！');
                    } else if (length == 1) {
                        var row = $("#dg").datagrid("getSelected");
                        $("input[name=title]").val(row.title);
                        $("input[name=url]").val(row.url);
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
                        $('#frontWinEdit').window('open');
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

        //添加前台横向菜单窗口
        $('#frontWin').window({
            width: 600,
            height: 400,
            title: '添加横向菜单',
            iconCls: 'icon-add',
            draggable: true,
            resizable: false,
            minimizable: false,
            collapsible: false,
            maximizable: false,
            modal: true,
            closed: true
        });

        /*重置添加内容*/
        $("#rs").click(function () {
            $("#ff input[type=text]").val("");
            $("#a1").focus();
        });

        /*添加触发ajax*/
        $(".c1").click(function () {
            $.ajax({
                url: "<%=basePath %>/web/addWebMenu.do",
                type: "post",
                dataType: "json",
                data: {
                    "title": $("#ff input[name=title]").val(),
                    "url": $("#ff input[name=url]").val()
                },
                success: function (rs) {
                    if (rs) {//添加成功
                        //1、关闭添加前台菜单窗口
                        $("#frontWin").window('close');
                        //2、刷新表格
                        $("#dg").datagrid('reload')
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

        //编辑前台横向菜单窗口
        $('#frontWinEdit').window({
            width: 600,
            height: 400,
            title: '编辑横向菜单',
            iconCls: 'icon-edit',
            draggable: true,
            resizable: false,
            minimizable: false,
            collapsible: false,
            maximizable: false,
            modal: true,
            closed: true
        });

        /*编辑触发ajax*/
        $(".c11").click(function () {
            var id = $("#dg").datagrid("getSelected").id;
            $.ajax({
                url: "<%=basePath %>/web/alterWebMenu.do",
                type: "post",
                dataType: "json",
                data: {
                    "title": $("#ff2 input[name=title]").val(),
                    "url": $("#ff2 input[name=url]").val(),
                    "id": id
                },
                success: function (rs) {
                    if (rs) {//编辑成功
                        //1、关闭编辑前台菜单窗口
                        $("#frontWinEdit").window('close');
                        //2、刷新表格
                        $("#dg").datagrid('reload')
                        //3、提示添加成功
                        $.messager.show({
                            title: "提示",
                            msg: "前台菜单编辑成功！"
                        });
                    } else {//添加失败
                        $.messager.alert('提示', '前台菜单编辑失败，请重试！');
                    }
                }
                ,
                error: function (err) {

                }
            });
        });
    });
</script>
</body>
</html>