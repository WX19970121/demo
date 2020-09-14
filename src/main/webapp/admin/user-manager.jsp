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

    <style type="text/css">
        .table-button{
            margin-left: 2%;
            margin-right: 2%;
        }
    </style>

    <script>

        $(function(){
            localStorage.removeItem("UserRowId");
            InitializationTable();
        });

        function setTopic(meg) {
            layui.use('layer', function(){
                var layer = layui.layer;
                layer.msg(meg);
            });
        }

        function openifram(topic, url){
            layui.use('layer', function(){
                var layer = layui.layer;
                layer.open({
                    type: 2,
                    title: topic,
                    area: ['500px', '600px'],
                    content: url
                });
            });
        }


        function InitializationTable(){
            $('#user-info').jqGrid({
                url: '${path}/user-manager/getTableInfo',
                datatype : 'json',
                styleUI : "Bootstrap",
                caption : "用户信息数据表",
                autowidth : true,
                viewrecords : true,
                height : "auto",
                colNames : [ 'Id', '用户名', '手机号', '微信', '头像', '签名', '状态', '注册时间'],
                colModel : [
                    {name : 'id', index : 'id'},
                    {name : 'userName', index : 'userName', align : "center"},
                    {name : 'userPhone', index : 'userPhone', align : "center"},
                    {name : 'userWeiXin', index : 'userWeiXin', align : "center"},
                    {name : 'userHeadImg', index : 'userHeadImg', align : "center",
                        formatter:function(cellvalue, options, rowObject){
                            return '<img src="${path}/img/formalpath/' +cellvalue+ '" width="100px" height="100px">'
                        }
                    },
                    {name : 'userAutograph', index : 'userAutograph', align : "center"},
                    {name : 'userState', index : 'userState', align : "center",
                        formatter:function(cellvalue, options, rowObject){
                            if (cellvalue == '正常'){
                                return '<button type="button" class="btn btn-primary" onclick="ChangeStatus(\''+cellvalue+'\', \''+rowObject.id+'\')">正常</button>';
                            }
                            else{
                                return '<button type="button" class="btn btn-danger" onclick="ChangeStatus(\''+cellvalue+'\', \''+rowObject.id+'\')">冻结</button>';
                            }
                        }
                    },
                    {name : 'userRegisterTime', index : 'userRegisterTime', align : "center", width : '240'},
                ],
                rowNum : 5,
                rowList : [ 10, 20, 30 ],
                pager : '#user-info-assembly',
                onSelectRow : function (rowid) {
                    localStorage.setItem("UserRowId", rowid);
                }
            });

            $("#user-info").jqGrid('navGrid', '#user-info-assembly', {edit : false, add : false, del : false, search : false})
                .navButtonAdd('#user-info-assembly', {
                    caption : '添加用户',
                    buttonicon : 'none',
                    onClickButton : function () {
                        openifram("添加用户" ,"/user-manager/GoAddPage");
                    }
                })
                .navButtonAdd('#user-info-assembly', {
                    caption : '删除用户',
                    buttonicon : 'none',
                    onClickButton : function () {
                        var deleteId = localStorage.getItem("UserRowId");
                        if(deleteId == "" || deleteId == null || deleteId == 0){
                            alert("请选择要删除的账户")
                        }
                        else{
                            layui.use('layer', function(){
                                var layer = layui.layer;
                                layer.confirm('是否删除用户Id为'+deleteId+'的用户？', {
                                    btn: ['确定删除', '取消'],
                                    icon: 2
                                }, function(index, layero){
                                    //关闭提示框
                                    layer.close(index);

                                    DeleteUser(deleteId);
                                    localStorage.removeItem("UserRowId");
                                }, function(index){
                                    layer.close(index);
                                });
                            });
                        }
                    }
                })
        }

        function ChangeStatus(status, id) {
            var changeStatus = '';

            if(status == '正常'){
                changeStatus = '冻结';
            }
            else{
                changeStatus = '正常';
            }

            $.ajax({
                url: '/user-manager/edit',
                type: 'post',
                dataType: 'json',
                data: {
                    edit: 'editstatus',
                    Id: id,
                    userState: changeStatus
                },
                success: function (result) {
                    if(result.meg == "CHANGE STATUS SUCCESS"){
                        setTopic('修改状态成功');
                        $("#user-info").trigger("reloadGrid");
                    }
                    else {
                        alert('修改状态失败!!!');
                    }
                }
            })
        }

        function DeleteUser(id){
            $.ajax({
                url: '/user-manager/edit',
                type: 'post',
                dataType: 'json',
                data: {
                    edit: 'delete',
                    Id: id,
                },
                success: function (result) {
                    if (result.meg == 'DELETE SUCCESS'){
                        setTopic('删除用户成功!!');
                        $("#user-info").trigger("reloadGrid");
                    }
                    else {
                        setTopic('删除用户失败!!');
                    }
                },
                error: function () {
                    setTopic('调用接口失败!!');
                }
            })
        }

        function SendMess() {
            var PhoneNum = $('#SendMessPhone').val();
            if(PhoneNum == null || PhoneNum == ""){
                alert("请输入要发送验证码的手机号");
            }
            else {
                $.ajax({
                    url: '/aliyun/SendMessToPhone',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        PhoneNum: PhoneNum,
                    },
                    success: function (result) {
                        if (result.meg == 'OK'){
                            ChangeSendMessButtonStyle("SendMessButton", 45);
                        }
                        else {
                            alert("发送失败");
                        }
                    },
                    error: function () {
                        setTopic('调用接口失败!!');
                    }
                })
            }
        }

        //点击发送验证码按钮后按钮的样式改变
        function ChangeSendMessButtonStyle(Id, CountdownTime) {
            var Countdown = CountdownTime; //设置倒计时的时间
            var OrginHtml = document.getElementById(Id).innerHTML; //获取原来按钮的值

            document.getElementById(Id).setAttribute("disabled", "disabled");

            var CountdownFunction = setInterval(function () {
                if(Countdown <= 0){
                    document.getElementById(Id).removeAttribute("disabled");
                    document.getElementById(Id).innerHTML = OrginHtml;
                    clearInterval(CountdownFunction); //清除定时循环函数
                }
                else {
                    document.getElementById(Id).innerHTML = "("+Countdown+")" + "重新发送";
                    Countdown--;
                }
            }, 1000);

        }

        function ExcelPutOutUser() {
            window.location.href = "/PutOutExcel/ExcelUserPutOut";
        }


    </script>

</head>
<head>
    <title>Title</title>
</head>
<body>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-1">
            <button type="button" class="btn btn-success" onclick="ExcelPutOutUser()">导出用户数据</button>
        </div>

        <div class="col-md-5" align="right">
            <form class="form-inline">
                <div class="form-group">
                    <label for="SendMessPhone">手机号码</label>
                    <input type="text" class="form-control" id="SendMessPhone" placeholder="请输入需要发送验证码的手机号码">
                </div>
                <button type="button" class="btn btn-primary" id="SendMessButton" onclick="SendMess()">发送验证码</button>
            </form>
        </div>

        <div class="col-md-12" style="margin-top: 15px">


            <table id="user-info">

            </table>

            <div id="user-info-assembly">

            </div>

        </div>
    </div>
</div>

</body>
</html>
