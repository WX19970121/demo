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


    <script src="${path}/echarts/echarts.min.js"></script>
    <script src="${path}/echarts/shine.js"></script>

    <script>

        $(function(){
            GetUserDisInfo();
        });

        var Charts = null;

        function setCharts(datatitle, datalist) {

            $.getJSON('${path}/echarts/china.json', function (data) {
                echarts.registerMap('china', data);

                Charts = echarts.init(document.getElementById("UserDisCharts"));

                Charts.showLoading();

                var option = {
                    title: {
                        text: '注册人数分布图',
                        subtext: '',
                        sublink: ''
                    },
                    legend: {
                        show: true,
                        data: datatitle
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: function (params){
                            return params.seriesName + '<br/>' + params.name + ': ' + params.value;
                        }
                    },
                    toolbox: {
                        show: false,
                        orient: 'vertical',
                        left: 'right',
                        top: 'center',
                        feature: {
                            dataView: {readOnly: false},
                            restore: {},
                            saveAsImage: {}
                        }
                    },
                    visualMap: {
                        left: 'left',
                        min: 0,
                        max: 20000,
                        inRange: {
                            color: ['lightskyblue', 'yellow', 'orangered']
                        },
                        text: ['High', 'Low'],           // 文本，默认为数值文本
                        calculable: true
                    },
                    series: datalist
                }

                Charts.setOption(option);

                Charts.hideLoading();

            })
        }

        function GetUserDisInfo() {
            $.ajax({
                url: '${path}/Echarts/UserDistributionCharts',
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    if(result.code == "OK"){

                        var ChartsTitle = []; //定义标题
                        var ChartsData = []; //定义数据

                        for(var i = 0; i < result.data.length; i++){
                            var e = result.data[i]; //存放数据

                            ChartsTitle.push(e.titleName);

                            var SeriesData = {
                                name: e.titleName,
                                type: 'map',
                                mapType: 'china', // 自定义扩展图表类型
                                label: {
                                    show: true
                                },
                                data: e.chartsValueList
                            }
                            ChartsData.push(SeriesData);
                        }

                        setCharts(ChartsTitle, ChartsData);
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

        <div class="col-md-12" id="UserDisCharts" style="width: 100%;height:800px;">

        </div>

    </div>
</div>

</body>
</html>
