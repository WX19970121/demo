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

    <script>

        //获取随机数
        function randomValue() {
            var Value = Math.round((Math.random()*10000)+1);
            return Value;
        }

        function setChartsInfo(){

            var dataList=[
                {name:"南海诸岛",value:0},
                {name: '北京', value: randomValue()},
                {name: '天津', value: randomValue()},
                {name: '上海', value: randomValue()},
                {name: '重庆', value: randomValue()},
                {name: '河北', value: randomValue()},
                {name: '河南', value: randomValue()},
                {name: '云南', value: randomValue()},
                {name: '辽宁', value: randomValue()},
                {name: '黑龙江', value: randomValue()},
                {name: '湖南', value: randomValue()},
                {name: '安徽', value: randomValue()},
                {name: '山东', value: randomValue()},
                {name: '新疆', value: randomValue()},
                {name: '江苏', value: randomValue()},
                {name: '浙江', value: randomValue()},
                {name: '江西', value: randomValue()},
                {name: '湖北', value: randomValue()},
                {name: '广西', value: randomValue()},
                {name: '甘肃', value: randomValue()},
                {name: '山西', value: randomValue()},
                {name: '内蒙古', value: randomValue()},
                {name: '陕西', value: randomValue()},
                {name: '吉林', value: randomValue()},
                {name: '福建', value: randomValue()},
                {name: '贵州', value: randomValue()},
                {name: '广东', value: randomValue()},
                {name: '青海', value: randomValue()},
                {name: '西藏', value: randomValue()},
                {name: '四川', value: randomValue()},
                {name: '宁夏', value: randomValue()},
                {name: '海南', value: randomValue()},
                {name: '台湾', value: randomValue()},
                {name: '香港', value: randomValue()},
                {name: '澳门', value: randomValue()}
            ]

            for(var i = 0; i < dataList.length; i++){
                var e = dataList[i];

                $.ajax({
                    url: '/Test/AddChartsInfo',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        name: e.name,
                        value: e.value,
                        titleId: 2
                    },
                    success: function (result) {
                        console.log(result.A);
                    },
                    error: function () {
                        alert('调用接口失败');
                    }
                });

            }

        }

    </script>

</head>
<body>
<button onclick="setChartsInfo()">Test</button>
</body>
</html>
