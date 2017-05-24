<%--
  Created by IntelliJ IDEA.
  User: gent
  Date: 2017/5/23
  Time: 14:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>四川</title>
    <script src="${ctxStatic}/js/map/echarts-all.js"></script>
    <script src="${ctxStatic}/js/map/map.js"></script>

    <style type="text/css">
        body {
            background-image: url('${ctxStatic}/images/homePage/tu-1.png');
            height: auto;
            margin: 0 auto;
        }

        #box {
            margin-left: 15px;
        }

        #right {
            width: 450px;
        }

        #one, #two, #three {
            height: 300px;
            border: solid white 2px;
            margin-bottom: 5px;

        }
        #cun tr td{
            border:1px solid #ffffff;
        }
        #logo {
            padding-left: 30px;
            padding-top: 10px;
        }

        a{
            text-decoration: none;
            color: white;
        }
        a:hover{
            color: blue;
        }
    </style>
</head>
<body>
<div id="box">
    <table width="1080px">
        <tr>
            <td>
                <div id="logo"><a href="${ctx}/chart/sysIndex"><img src="${ctxStatic}/images/03.png"/></a></div>
            </td>
        </tr>
        <tr>
            <td>
                <div id="main" style="width: 1400px; height: 900px;"></div>
            </td>
            <td>
                <div id="right">
                    <div id="one">
                        <div style="float: right; margin-right: 5px; margin-right: 5px; color: white">(万亩)</div>
                        <iframe width="100%" height="100%" id="map-1" src="${ctx}/grow/getOne?parms=成都市"
                                style="border: none"
                                scrolling="no"></iframe>
                    </div>
                    <div id="two">
                        <div style="float: right; margin-right: 5px; margin-right: 5px; color: white">(晒统个 元/kg)</div>
                        <iframe width="100%" height="100%" id="map-2" src="${ctx}/grow/getTwo?parms=成都市"
                                style="border: none" scrolling="no"></iframe>
                    </div>
                    <div id="three">
                        <table id="cun" width="100%" style="color: white;font-size: 16px; font-weight: bold; ">
                            <tr>
                                <td colspan="6" style="text-align: center">敖平镇川穹种植分布</td>
                            </tr>
                            <tr>
                                <td>平安村</td>
                                <td>凤泉村</td>
                                <td><a href="${ctx}/chart/grow">鹤泉村</a></td>
                                <td>曙光村</td>
                                <td>石泉村</td>
                                <td>方桥村</td>
                            </tr>
                            <tr>
                                <td>双凤村</td>
                                <td>下泉村</td>
                                <td>中泉村</td>
                                <td>枷担村</td>
                                <td>定光村</td>
                                <td>高河村</td>
                            </tr>
                            <tr>
                                <td>漆树村</td>
                                <td>济泉村</td>
                                <td>石音村</td>
                                <td>民主村</td>
                                <td>罗林村</td>
                                <td>河北村</td>
                            </tr>
                            <tr>
                                <td colspan="6" style="text-align: center">隐峰镇川穹种植分布</td>
                            </tr>
                            <tr>
                                <td>农科村</td>
                                <td>杨寨村</td>
                                <td>红桥村</td>
                                <td>寿增村</td>
                                <td>石桥村</td>
                            </tr>
                        </table>
                    </div>
                </div>
            </td>
        </tr>
    </table>
</div>

<script>
    var myChart = echarts.init(document.getElementById('main'));
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: function (a) {
                return a[1];
            }
        },
        series: [{
            name: '四川省',
            type: 'map',
            mapType: '四川',
            selectedMode: 'single',
            data: [{
                name: '甘孜藏族自治州',
                value: 1
            }, {
                name: '阿坝藏族羌族自治州',
                value: 2
            }, {
                name: '凉山彝族自治州',
                value: 3
            }, {
                name: '绵阳市',
                value: 4
            }, {
                name: '达州市',
                value: 5
            }, {
                name: '广元市',
                value: 6
            }, {
                name: '雅安市',
                value: 7
            }, {
                name: '宜宾市',
                value: 8
            }, {
                name: '乐山市',
                value: 9
            }, {
                name: '南充市',
                value: 10
            }, {
                name: '巴中市',
                value: 11
            }, {
                name: '泸州市',
                value: 12
            }, {
                name: '成都市',
                value: 13
            }, {
                name: '资阳市',
                value: 14
            }, {
                name: '攀枝花市',
                value: 15
            }, {
                name: '眉山市',
                value: 16
            }, {
                name: '内江市',
                value: 17
            }, {
                name: '自贡市',
                value: 18
            }, {
                name: '广安市',
                value: 19
            }, {
                name: '遂宁市',
                value: 20
            }, {
                name: '德阳市',
                value: 21
            }],
            itemStyle: {
                normal: {
                    borderColor: 'green',
                    areaColor: 'white',
                },
                emphasis: {
                    areaColor: 'white',
                    borderWidth: 0
                }
            }
        }]
    };
    myChart.on('click', function (params) {
        var parm = params.name;
        document.getElementById("map-1").src = "${ctx}/grow/getOne";
        document.getElementById("map-2").src = "${ctx}/grow/getTwo";
        document.getElementById("map-1").src = document.getElementById("map-1").src + "?parms=" + parm;
        document.getElementById("map-2").src = document.getElementById("map-2").src + "?parms=" + parm;
    });
    myChart.setOption(option,true);
</script>
</body>
</html>
