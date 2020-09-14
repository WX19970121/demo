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
            InitializationJournalTable();
        });

        function setTopic(meg) {
            layui.use('layer', function(){
                var layer = layui.layer;
                layer.msg(meg);
            });
        }

        //初始化日志信息表格
        function InitializationJournalTable(){
            $('#journal-info').jqGrid({
                url: '${path}/Journal-Manager/GetAllJournalInfo',
                datatype : 'json',
                styleUI : "Bootstrap",
                caption : "日志记录表",
                autowidth : true,
                viewrecords : true,
                height : "auto",
                colNames : [ 'Id', '日志记录', '日志记录时间'],
                colModel : [
                    {name : 'journalId', index : 'journalId'},
                    {name : 'journalMessage', index : 'journalMessage', align : "center", width : 850},
                    {name : 'journalTime', index : 'journalTime', align : "center"},
                ],
                rowNum : 5,
                rowList : [ 10, 20, 30 ],
                pager : '#journal-info-assembly'
            });

            $("#journal-info").jqGrid('navGrid', '#journal-info-assembly', {edit : false, add : false, del : false, search : false})
        }

        //清空全部日志记录
        function clearAllJournal() {
            $.ajax({
                url: '/Journal-Manager/ClearAllJournal',
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if(result.meg == "CLEAR SUCCESS"){
                        setTopic('清除日志成功');
                        $("#journal-info").trigger("reloadGrid");
                    }
                },
                error: function () {
                    alert('调用接口失败');
                }
            })
        }

        function ClearJournal(){
            layui.use('layer', function(){
                var layer = layui.layer;
                layer.confirm('是否清除全部的日志记录？', {
                    btn: ['确定删除', '取消'],
                    icon: 2
                }, function(index, layero){
                    //关闭提示框
                    layer.close(index);

                    clearAllJournal();
                }, function(index){
                    layer.close(index);
                });
            });
        }

        //导出日志记录
        function ExcelPutOutJournal() {
            window.location.href = "/PutOutExcel/ExcelJournalPutOut";
        }

    </script>


</head>
<body>

<div class="container-fluid">
    <div class="row">

        <div class="col-md-2" align="left">
            <button type="button" class="btn btn-success" onclick="ExcelPutOutJournal()">导出日志记录</button>
        </div>

        <div class="col-md-2" align="left">
            <button type="button" class="btn btn-warning" onclick="ClearJournal()">清空全部日志</button>
        </div>

        <div class="col-md-12" style="margin-top: 15px">

            <table id="journal-info">

            </table>

            <div id="journal-info-assembly">

            </div>

        </div>

    </div>
</div>

</body>
</html>
