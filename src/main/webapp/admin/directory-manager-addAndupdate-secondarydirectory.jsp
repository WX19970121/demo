<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<c:set var="SecondaryDirectoryId" value="${SecondaryDirectory.id}"/>
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
            //初始化下拉菜单框
            var PrimaryDirectoryId = ${SecondaryDirectory.primaryDirectory.id};
            console.log(PrimaryDirectoryId);
            if(PrimaryDirectoryId != 0 && PrimaryDirectoryId != null && PrimaryDirectoryId != ""){
                SetSelectInfo(PrimaryDirectoryId);
            }
        });

        //输出提示信息
        function setTopic(meg) {
            layui.use('layer', function(){
                var layer = layui.layer;
                layer.msg(meg);
            });
        }

        //获取文本框的值
        function getValue(Id){
            var GetValue = $('#' + Id).val();
            return{
                value: GetValue
            }
        }

        //清空输入的信息
        function clearInput(){
            $('input:text').val(null);
        }

        //初始化下拉框
        function SetSelectInfo(PrimaryDirectoryId){
            var SelectPrimaryDirectory = document.getElementById("BelongPrimaryDirectory");

            for(var i = 0; i < SelectPrimaryDirectory.options.length; i++){
                if(SelectPrimaryDirectory.options[i].value == PrimaryDirectoryId){
                    SelectPrimaryDirectory.options[i].selected = true;
                    break;
                }
            }
        }

        //添加二级目录
        function addSecondaryDirectory(){
            var SecondaryDirectoryName = getValue('SecondaryDirectoryName').value;
            var BelongId = getValue('BelongPrimaryDirectory').value;

            if(SecondaryDirectoryName == null || SecondaryDirectoryName == ""){
                setTopic('二级目录的名称不能为空');
            }
            else if(BelongId == 0){
                setTopic('请选择对应的一级目录')
            }
            else {
                $.ajax({
                    url: '/directory-manager/editSecondaryDirectory',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        edit: 'add',
                        secondaryDirectoryName: SecondaryDirectoryName,
                        belongId: BelongId
                    },
                    success: function (result) {
                        if(result.meg == "ADD SUCCESS"){
                            setTopic('添加二级目录成功');
                            clearInput();
                        }
                        else {
                            setTopic('添加二级目录失败');
                        }
                    },
                    error: function () {
                        alert('调用接口失败');
                    }
                })
            }
        }

        //修改二级目录
        function updateSecondaryDirectory() {
            var SecondaryDirectoryId = ${SecondaryDirectoryId};
            var SecondaryDirectoryName = getValue('SecondaryDirectoryName').value;
            var BelongId = getValue('BelongPrimaryDirectory').value;

            if(SecondaryDirectoryName == null || SecondaryDirectoryName == ""){
                setTopic('二级目录的名称不能为空');
            }
            else if(BelongId == 0){
                setTopic('请选择对应的一级目录')
            }
            else {
                $.ajax({
                    url: '/directory-manager/editSecondaryDirectory',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        edit: 'update',
                        Id: SecondaryDirectoryId,
                        secondaryDirectoryName: SecondaryDirectoryName,
                        belongId: BelongId
                    },
                    success: function (result) {
                        if(result.meg == "UPDATE SUCCESS"){
                            setTopic('修改二级目录成功');
                            clearInput();
                        }
                        else {
                            setTopic('修改二级目录失败');
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

                <c:if test="${SecondaryDirectoryId > 0}">
                    <div class="form-group">
                        <label for="SecondaryDirectoryId">二级目录Id</label>
                        <input type="text" class="form-control" id="SecondaryDirectoryId" value="${SecondaryDirectoryId}" disabled="disabled">
                    </div>
                </c:if>

                <div class="form-group">
                    <label for="SecondaryDirectoryName">二级目录名称</label>
                    <input type="text" class="form-control" id="SecondaryDirectoryName" value="${SecondaryDirectory.secondaryDirectoryName}">
                </div>

                <div class="form-group">
                    <label for="BelongPrimaryDirectory">对应的一级目录</label>
                    <select class="form-control" id="BelongPrimaryDirectory">
                        <option value="0">请选择对应的一级目录</option>
                        <c:forEach items="${PrimaryDirectory}" var="x">
                            <option value="${x.id}">${x.primaryDirectoryName}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <c:if test="${type == 'add'}">
                        <input type="button" class="btn btn-primary btn-lg" onclick="addSecondaryDirectory()" value="确认添加">
                    </c:if>

                    <c:if test="${type == 'update'}">
                        <input type="button" class="btn btn-primary btn-lg" onclick="updateSecondaryDirectory()" value="确认修改">
                    </c:if>
                </div>

            </form>

        </div>

    </div>
</div>
</body>
</html>
