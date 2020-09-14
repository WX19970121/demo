<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>应学APP后台管理系统</title>
    <link rel="icon" href="${path}/bootstrap/img/arrow-up.png" type="image/x-icon">
    <link rel="stylesheet" href="${path}/bootstrap/css/bootstrap.css">

    <%--引入jqgrid中主题css--%>
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/css/css/hot-sneaks/jquery-ui-1.8.16.custom.css">
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/boot/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入js文件--%>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/bootstrap/js/bootstrap.js"></script>
    <script src="${path}/bootstrap/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="${path}/bootstrap/jqgrid/boot/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${path}/bootstrap/js/ajaxfileupload.js"></script>

    <%--引入--%>
    <link rel="stylesheet" href="${path}/layui/css/layui.css">
    <script src="${path}/layui/layui.js"></script>

    <script>

        $(function(){
            localStorage.removeItem("PrimaryDirectoryRowId");
            InitializationPrimaryDirectoryTable();
        });

        function openifram(topic, url){
            layui.use('layer', function(){
                var layer = layui.layer;
                layer.open({
                    type: 2,
                    title: topic,
                    area: ['500px', '350px'],
                    content: url
                });
            });
        }

        function setTopic(meg) {
            layui.use('layer', function(){
                var layer = layui.layer;
                layer.msg(meg);
            });
        }

        //初始化一级目录信息
        function InitializationPrimaryDirectoryTable() {
            $('#directory-info').jqGrid({
                url: '${path}/directory-manager/getPrimaryDirectoryInfo',
                datatype : 'json',
                styleUI : "Bootstrap",
                caption : "一级目录信息数据表",
                autowidth : true,
                viewrecords : true,
                height : "auto",
                colNames : [ 'Id', '一级目录名称', '所拥有的二级目录数量'],
                colModel : [
                    {name : 'id', index : 'id'},
                    {name : 'primaryDirectoryName', index : 'primaryDirectoryName', align : "center"},
                    {name : 'primaryDirectoryNum', index : 'primaryDirectoryNum', align : "center"},
                ],
                rowNum : 5,
                rowList : [ 10, 20, 30 ],
                pager : '#directory-info-assembly',
                onSelectRow : function (rowid) {
                    localStorage.setItem("PrimaryDirectoryRowId", rowid);
                },
                subGrid : true,
                subGridRowExpanded : function (subgrid_id, row_id) {
                    InitializationSecondaryDirectoryTable(subgrid_id, row_id);
                }
            });

            $("#directory-info").jqGrid('navGrid', '#directory-info-assembly', {edit : false, add : false, del : false, search : false})
                .navButtonAdd('#directory-info-assembly', {
                    caption : '添加一级目录',
                    buttonicon : 'none',
                    onClickButton : function () {
                        openifram('添加一级目录', '${path}/directory-manager/GoAddAndUpdatePrimaryDirectoryPage?PrimaryDirectoryId=0');
                    }
                })
                .navButtonAdd('#directory-info-assembly', {
                    caption : '修改一级目录',
                    buttonicon : 'none',
                    onClickButton : function () {
                        var updateId = localStorage.getItem("PrimaryDirectoryRowId");
                        openifram('修改一级目录', '${path}/directory-manager/GoAddAndUpdatePrimaryDirectoryPage?PrimaryDirectoryId=' + updateId);
                    }
                })
                .navButtonAdd('#directory-info-assembly', {
                    caption : '删除一级目录',
                    buttonicon : 'none',
                    onClickButton : function () {
                        var deleteId = localStorage.getItem("PrimaryDirectoryRowId");
                        if(deleteId == null || deleteId == "" || deleteId == 0){
                            alert('请选择要删除的目录');
                        }
                        else {
                            layui.use('layer', function(){
                                var layer = layui.layer;
                                layer.confirm('是否删除目录Id为'+deleteId+'的目录？', {
                                    btn: ['确定删除', '取消'],
                                    icon: 2
                                }, function(index, layero){
                                    //关闭提示框
                                    layer.close(index);

                                    deletePrimaryDirectory(deleteId);
                                    localStorage.removeItem("PrimaryDirectoryRowId");
                                }, function(index){
                                    layer.close(index);
                                });
                            });
                        }
                    }
                })
        }

        //二级目录表格初始化
        function InitializationSecondaryDirectoryTable(subgrid_id, row_id) {
            var subgrid_table_id, pager_id;

            subgrid_table_id = subgrid_id + "_t";
            pager_id = "p_" + subgrid_table_id;

            $("#" + subgrid_id).html(
                '<table id="'+subgrid_table_id+'"></table>' +
                '<div id="'+pager_id+'"></div>'
            );

            //初始化二级目录表格
            $("#" + subgrid_table_id).jqGrid({
                url: '${path}/directory-manager/getSecondaryDirectoryInfo?BelongPrimaryDirectoryId=' + row_id,
                datatype : 'json',
                styleUI : "Bootstrap",
                caption : "二级目录信息数据表",
                autowidth : true,
                viewrecords : true,
                height : "auto",
                colNames : [ 'Id', '二级目录名称'],
                colModel : [
                    {name : 'id', index : 'id'},
                    {name : 'secondaryDirectoryName', index : 'secondaryDirectoryName', align : "center"}
                ],
                rowNum : 5,
                rowList : [ 10, 20, 30 ],
                pager : '#' + pager_id,
                onSelectRow : function (rowid) {
                    localStorage.setItem("SecondaryDirectoryRowId", rowid);
                },
            });

            $("#" + subgrid_table_id).jqGrid('navGrid', '#' + pager_id, {edit : false, add : false, del : false, search : false})
                .navButtonAdd('#' + pager_id, {
                    caption : '添加二级目录',
                    buttonicon : 'none',
                    onClickButton : function () {
                        openifram('修改一级目录', '${path}/directory-manager/GoAddAndUpdateSecondaryDirectoryPage?SecondaryDirectoryId=0');
                    }
                })
                .navButtonAdd('#' + pager_id, {
                    caption : '修改二级目录',
                    buttonicon : 'none',
                    onClickButton : function () {
                        var updateId = localStorage.getItem("SecondaryDirectoryRowId");
                        openifram('修改一级目录', '${path}/directory-manager/GoAddAndUpdateSecondaryDirectoryPage?SecondaryDirectoryId=' + updateId);
                    }
                })
                .navButtonAdd('#' + pager_id, {
                    caption : '删除二级目录',
                    buttonicon : 'none',
                    onClickButton : function () {
                        var deleteId = localStorage.getItem("SecondaryDirectoryRowId");
                        if(deleteId == null || deleteId == "" || deleteId == 0){
                            alert('请选择要删除的目录');
                        }
                        else {
                            layui.use('layer', function(){
                                var layer = layui.layer;
                                layer.confirm('是否删除目录Id为'+deleteId+'的目录？', {
                                    btn: ['确定删除', '取消'],
                                    icon: 2
                                }, function(index, layero){
                                    //关闭提示框
                                    layer.close(index);

                                    deleteSecondaryDirectory(deleteId, subgrid_table_id);
                                    localStorage.removeItem("SecondaryDirectoryRowId");
                                }, function(index){
                                    layer.close(index);
                                });
                            });
                        }
                    }
                });
        }

        //删除一级目录
        function deletePrimaryDirectory(Id) {
            $.ajax({
                url: '/directory-manager/editPrimaryDirectory',
                type: 'post',
                dataType: 'json',
                data: {
                    edit: 'delete',
                    Id: Id
                },
                success: function (result) {
                    if(result.meg == "DELETE SUCCESS"){
                        setTopic('删除一级目录成功');
                        $("#directory-info").trigger("reloadGrid");
                    }
                    else if(result.meg == "SECONDARY DIRECTORY EXISTS"){
                        setTopic('该目录下存在二级目录，无法删除');
                    }
                    else {
                        setTopic('删除一级目录失败');
                    }
                },
                error: function () {
                    alert('调用接口失败');
                }
            });
        }

        //删除二级目录
        function deleteSecondaryDirectory(Id, subgrid_table_id){
            $.ajax({
                url: '/directory-manager/editSecondaryDirectory',
                type: 'post',
                dataType: 'json',
                data: {
                    edit: 'delete',
                    Id: Id
                },
                success: function (result) {
                    if(result.meg == "DELETE SUCCESS"){
                        setTopic('删除二级目录成功');

                        //刷新表格
                        $("#" + subgrid_table_id).trigger("reloadGrid");
                    }
                    else {
                        setTopic('删除二级目录失败');
                    }
                },
                error: function () {
                    alert('调用接口失败');
                }
            });
        }

    </script>

</head>
<body>

<div class="container-fluid">
    <div class="row">

        <div class="col-md-12" style="margin-top: 15px">

            <table id="directory-info">

            </table>

            <div id="directory-info-assembly">

            </div>

        </div>

    </div>
</div>

</body>
</html>
