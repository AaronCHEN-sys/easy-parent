<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath %>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查看轮播图</title>
</head>
<body>

<!--表格数据展示-->
<table id="getBanner"></table>

<%--添加系统用户菜单窗口--%>
<div id="addBanner">
    <center style="padding-top: 30px">
        <form action="javascript:void(0)" method="post">
            <table style="width: 500px;">
                <tr>
                    <td>商品图片</td>
                    <td>
                        <input type="button" id="onPicUpload" value="图片上传"/>
                        <input type="hidden" name="imgUrl" value=""/>
                    </td>
                </tr>
                <tr>
                    <td>跳转链接</td>
                    <td>
                        <input type="text" name="href"/>
                    </td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td>
                        <textarea name="remark"></textarea>
                    </td>
                </tr>
                <tr>
                    <td>排序号</td>
                    <td>
                        <select name="sort">
                            <option value="1">第1张</option>
                            <option value="2">第2张</option>
                            <option value="3">第3张</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><input type="submit" value="添加"></td>
                    <td>
                        <input type="reset" value="取消">
                    </td>
                </tr>
            </table>
        </form>
    </center>
</div>

<%--&lt;%&ndash;编辑系统用户菜单窗口&ndash;%&gt;
<div id="editBanner">

</div>--%>
<script type="text/javascript">


    $(function () {

        //添加轮播单击事件
        $("#addBanner input[type=submit]").click(function () {
            $.ajax({
                url: "<%=basePath %>/web/addWebBanner.do",
                type: "POST",
                dataType: "json",
                data: {
                    "imgUrl": $("#addBanner input[name=imgUrl]").val(),
                    "href": $("#addBanner input[name=href]").val(),
                    "remark": $("#addBanner textarea[name=remark]").val(),
                    "sort": $("#addBanner select[name=sort]").val()
                },
                success: function (rs) {
                    console.log(rs);
                }
            });
        });

        var editor;
        window.editor = KindEditor.create('textarea[name="description"]', {
            allowFileManager: true,
            height: "200px" //编辑器的高度为100px
        });
        /*图片上传*/
        $("#onPicUpload").click(function () {
            var _self = $(this);
            KindEditor.editor({
                //指定上传文件参数名称
                filePostName: "uploadFile",
                //指定上传文件请求的url。
                uploadJson: '<%=basePath %>/uploadFile/uploadFileByFastDFS.do',
                //上传类型，分别为image、flash、media、file
                dir: "image"
            }).loadPlugin('image', function () {
                this.plugin.imageDialog({
                    showRemote: false,
                    clickFn: function (url, title, width, height, border, align) {
                        console.log("url=" + url);
                        //url=http://192.168.25.133/group1/M00/00/04/wKgZhVxozjWACYeZAACmK0sntbI152.jpg
                        $("#addBanner input[name=imgUrl]").val(url);
                        $("#onPicUpload").after("<a href='" + url + "' target='_blank'><img src='" + url + "' width='80' height='50'/></a>");
                        $("#onPicUpload").remove();
                        this.hideDialog();
                    }
                });
            });
        });

        <%--/*图片上传*/--%>
        <%--$("#onPicUpload").click(function () {--%>
        <%--var _self = $(this);--%>
        <%--KindEditor.editor({--%>
        <%--//指定上传文件参数名称--%>
        <%--filePostName: "uploadFile",--%>
        <%--//指定上传文件请求的url。--%>
        <%--uploadJson: '<%=basePath %>/uploadFile/uploadFileByFastDFS.do',--%>
        <%--//上传类型，分别为image、flash、media、file--%>
        <%--dir: "image"--%>
        <%--}).loadPlugin('image', function () {--%>
        <%--this.plugin.imageDialog({--%>
        <%--showRemote: false,--%>
        <%--clickFn: function (url, title, width, height, border, align) {--%>
        <%--$("#addBanner input[name=imgUrl]").val(url);--%>
        <%--$("#onPicUpload").after("<a href='" + url + "' target='_blank'><img src='" + url + "' width='80' height='50'/></a>");--%>
        <%--$("#onPicUpload").remove();--%>
        <%--this.hideDialog();--%>
        <%--}--%>
        <%--});--%>
        <%--});--%>
        <%--});--%>


        $('#getBanner').datagrid({
            url: '<%=basePath %>/web/getWebBanner.do',
            rownumbers: true,
            columns: [
                [
                    {field: 'id', title: '主键', width: 150, align: 'center'},
                    {field: 'image_url', title: '图片地址', width: 550, align: 'center'},
                    {field: 'href', title: '跳转链接', width: 200, align: 'center'},
                    {field: 'remark', title: '备注', width: 150, align: 'center'},
                    {field: 'sort', title: '排序', width: 150, align: 'center'},
                    {field: 'update_time', title: '更新时间', width: 150, align: 'center'}
                ]
            ],
            sortName: 'id',
            remoteSort: false,
            fit: true,
            striped: true,
            pagination: true,
            pageList: [5, 10, 20, 30],
            rowStyler:

                function (index, row) {
                    if (index % 2 == 0) {
                        return 'background-color:yellow;';
                    }
                }
            ,
            toolbar: [{
                iconCls: 'icon-add',
                text: '添加轮播图',
                handler: function () {
                    $("#addUser input[name=username]").val("");
                    $("#addUser input[name=password]").val("");
                    $('#addBanner').window('open');
                }
            }, '-', {
                iconCls: 'icon-edit',
                text: '编辑轮播图',
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
                text: '删除轮播图',
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

        $("#addBanner").window({
            width: 600,
            height: 400,
            title: "添加轮播图",
            iconCls: 'icon-add',
            draggable: true, /*能拖动*/
            resizable: false, /*不能改变尺寸*/
            minimizable: false,
            collapsible: false,
            maximizable: false,
            modal: true,
            closed: true
        });
    });
</script>
</body>
</html>