<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<c:set var="PrimaryDirectoryId" value="${PrimaryDirectory.id}"/>
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

        function getValue(Id) {
            var getValue = $('#' + Id).val();
            return{
                value: getValue
            }
        }

        function setTopic(meg) {
            layui.use('layer', function(){
                var layer = layui.layer;
                layer.msg(meg);
            });
        }

        //清除输入的信息
        function clearInputInfo() {
            $('input:text').val(null);
        }

        function addPrimaryDirectory() {
            var PrimaryDirectoryName = getValue('PrimaryDirectoryName').value;
            if(PrimaryDirectoryName == null || PrimaryDirectoryName == ""){
                alert("一级目录名称不能为空")
            }
            else{
                $.ajax({
                    url: '/directory-manager/editPrimaryDirectory',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        edit: 'add',
                        primaryDirectoryName: PrimaryDirectoryName
                    },
                    success: function (result) {
                        if(result.meg == "ADD SUCCESS"){
                            setTopic('添加一级目录成功');
                            clearInputInfo();
                        }
                        else {
                            setTopic('添加一级目录失败');
                        }
                    },
                    error: function () {
                        alert('调用接口失败');
                    }
                })
            }
        }

        function updatePrimaryDirectory() {
            var PrimaryDirectoryName = getValue('PrimaryDirectoryName').value;
            if(PrimaryDirectoryName == null || PrimaryDirectoryName == ""){
                alert("一级目录名称不能为空")
            }
            else{
                $.ajax({
                    url: '/directory-manager/editPrimaryDirectory',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        edit: 'update',
                        Id: ${PrimaryDirectoryId},
                        primaryDirectoryName: PrimaryDirectoryName
                    },
                    success: function (result) {
                        if(result.meg == "UPDATE SUCCESS"){
                            setTopic('修改一级目录成功');
                            clearInputInfo();
                        }
                        else {
                            setTopic('修改一级目录失败');
                        }
                    },
                    error: function () {
                        alert('调用接口失败');
                    }
                })
            }
        }

    </script>

</head>
<body>

<div class="container-fluid">
    <div class="row">

        <div class="col-md-12">

            <form action="">

                <c:if test="${PrimaryDirectoryId > 0}">
                    <div class="form-group">
                        <label for="PrimaryDirectoryId">一级目录Id</label>
                        <input type="text" class="form-control" id="PrimaryDirectoryId" value="${PrimaryDirectoryId}" disabled="disabled">
                    </div>
                </c:if>

                <div class="form-group">
                    <label for="PrimaryDirectoryName">一级目录名称</label>
                    <input type="text" class="form-control" id="PrimaryDirectoryName" value="${PrimaryDirectory.primaryDirectoryName}" placeholder="一级目录的名称">
                </div>

                <div class="form-group">
                    <c:if test="${type == 'add'}">
                        <input type="button" class="btn btn-primary btn-lg" onclick="addPrimaryDirectory()" value="确认添加">
                    </c:if>

                    <c:if test="${type == 'update'}">
                        <input type="button" class="btn btn-primary btn-lg" onclick="updatePrimaryDirectory()" value="确认修改">
                    </c:if>
                </div>
            </form>

        </div>

    </div>
</div>

</body>
</html>
