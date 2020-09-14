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
            uploadImg();
        });

        function getValue(Id){
            var GetValue = $('#' + Id).val();
            return {
                value : GetValue
            }
        }

        function uploadImg(){
            layui.use('upload', function() {
                var upload = layui.upload;
                var uploadInst = upload.render({
                    elem: '#userheadimg',
                    url: '/user-manager/uploadheadimg',
                    accept: 'images',
                    done: function(res){
                        localStorage.setItem("imgName", res.imgName);
                        document.getElementById("headimgshow").src = "${path}/img/templepath/" + res.imgName;
                    },
                    error: function(){
                        alert("上传失败")
                    }
                });
            });
        }

        function addUser() {
            var userName = getValue('username').value;
            var userPhone = getValue('userphone').value;
            var userWeiXin = getValue('userweixin').value;
            var userHeadImg = localStorage.getItem("imgName");
            var userAutograph = getValue('userautograph').value;

            $.ajax({
                url: '/user-manager/edit',
                type: 'post',
                dataType: 'json',
                data: {
                    edit: 'add',
                    userName: userName,
                    userPhone: userPhone,
                    userWeiXin: userWeiXin,
                    userHeadImg: userHeadImg,
                    userAutograph: userAutograph,
                },
                success: function (result) {
                    if(result.meg == "ADD SUCCESS"){
                        alert('添加用户成功!!!');

                        clearInputInfo(); //清空添加信息
                        localStorage.removeItem("imgName"); //删除上传图片信息
                    }
                    else if(result.meg == "USER EXISTENCE"){
                        alert('用户已存在!!!');
                    }
                },
                error: function () {
                    alert('调用接口失败');

                    clearInputInfo(); //清空添加信息
                    localStorage.removeItem("imgName"); //删除上传图片信息
                }
            })
        }

        //清除各种添加信息
        function clearInputInfo() {
            $('input:text').val(null);
            document.getElementById("headimgshow").src = "";
        }

    </script>

</head>
<body>

<div class="container-fluid">
    <div class="row">

        <div class="col-md-12">

            <form action="">
                <div class="form-group">
                    <label for="username">用户名</label>
                    <input type="text" class="form-control" id="username" placeholder="用户名">
                </div>
                <div class="form-group">
                    <label for="userphone">用户手机号</label>
                    <input type="text" class="form-control" id="userphone" placeholder="用户手机号">
                </div>
                <div class="form-group">
                    <label for="userweixin">用户微信</label>
                    <input type="text" class="form-control" id="userweixin" placeholder="用户微信">
                </div>
                <div class="form-group">
                    <label for="userheadimg">用户头像</label>
                </div>
                <div class="form-group">
                    <img id="headimgshow" src="" width="200px" height="200px" />
                </div>
                <div class="form-group">
                    <input type="button" class="btn btn-primary" id="userheadimg" value="上传用户头像">
                </div>
                <div class="form-group">
                    <label for="userautograph">用户签名</label>
                    <textarea id="userautograph" class="form-control" rows="3"></textarea>
                </div>
                <div class="form-group">
                    <input type="button" class="btn btn-primary btn-lg" onclick="addUser()" value="确认添加">
                </div>
            </form>

        </div>

    </div>
</div>

</body>
</html>
