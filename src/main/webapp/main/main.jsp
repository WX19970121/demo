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

    <style type="text/css">
        .grid-spacing{
            margin-top: 1.5%;
            margin-bottom: 1.5%;
        }
    </style>

    <script>
        function UserManager(Type) {
            if(Type == 1){
                document.getElementById("MianShow").src = "${path}/user-manager/GoInfoPage";
            }
            if(Type == 2){
                document.getElementById("MianShow").src = "${path}/Echarts/GoUserDistributionPage";
            }
        }

        function DirectoryManager(){
            document.getElementById("MianShow").src = "${path}/directory-manager/GoInfoPage";
        }

        function JournalManager() {
            document.getElementById("MianShow").src = "${path}/Journal-Manager/GoInfoPage";
        }
    </script>

</head>
<body>
    <div class="container-fluid">
        <div class="row">

            <!--导航条-->
            <div class="col-md-12">
                <nav class="navbar navbar-default">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="#">应学视频APP后台管理系统</a>
                    </div>
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul class="nav navbar-nav navbar-right">
                            <li><a href="#">${adminUser.adminName}</a></li>
                            <li><a href="${path}/login/cancellationAdminService">退出<span class="glyphicon glyphicon-log-out" aria-hidden="true"></span></a></li>
                        </ul>
                    </div>
                </nav>
            </div>

            <div class="col-md-2">
                <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">

                    <div class="panel panel-danger">
                        <div class="panel-heading" role="tab" id="headingOne">
                            <h4 class="panel-title">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                    用户管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
                            <div class="panel-body">
                                <div class="row" align="center">
                                    <div class="col-xs-8 col-sm-12 grid-spacing">
                                        <button type="button" class="btn btn-warning" onclick="UserManager(1)">用户展示</button>
                                    </div>
                                    <div class="col-xs-8 col-sm-12 grid-spacing">
                                        <button type="button" class="btn btn-warning">用户统计</button>
                                    </div>
                                    <div class="col-xs-8 col-sm-12 grid-spacing">
                                        <button type="button" class="btn btn-warning" onclick="UserManager(2)">用户分布</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="panel panel-success">
                        <div class="panel-heading" role="tab" id="headingTwo">
                            <h4 class="panel-title">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
                                    分类管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                            <div class="panel-body">
                                <div class="row" align="center">
                                    <div class="col-xs-8 col-sm-12 grid-spacing">
                                        <button type="button" class="btn btn-success" onclick="DirectoryManager()">分类展示</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="panel panel-primary">
                        <div class="panel-heading" role="tab" id="headingThree">
                            <h4 class="panel-title">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="true" aria-controls="collapseTwo">
                                    视频管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                            <div class="panel-body">
                                <div class="row" align="center">
                                    <div class="col-xs-8 col-sm-12 grid-spacing">
                                        <button type="button" class="btn btn-primary">视频展示</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="panel panel-warning">
                        <div class="panel-heading" role="tab" id="headingFouth">
                            <h4 class="panel-title">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFouth" aria-expanded="true" aria-controls="collapseTwo">
                                    日志管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseFouth" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFouth">
                            <div class="panel-body">
                                <div class="row" align="center">
                                    <div class="col-xs-8 col-sm-12 grid-spacing">
                                        <button type="button" class="btn btn-info" onclick="JournalManager()">查看日志</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="panel panel-info">
                        <div class="panel-heading" role="tab" id="headingFive">
                            <h4 class="panel-title">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFive" aria-expanded="true" aria-controls="collapseTwo">
                                    反馈管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseFive" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFive">
                            <div class="panel-body">
                                <div class="row" align="center">
                                    <div class="col-xs-8 col-sm-12 grid-spacing">
                                        <button type="button" class="btn btn-warning">反馈展示</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>

            <div class="col-md-10">
                <div class="panel panel-danger">
                    <div class="panel-heading">Panel heading without title</div>
                    <div class="panel-body">
                        <div class="embed-responsive embed-responsive-16by9">
                            <iframe id="MianShow" class="embed-responsive-item" src=""></iframe>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</body>
</html>
