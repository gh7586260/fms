<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>月资金统计</title>
    <script src="<%=basePath%>js/html5.js" type="text/javascript"></script>
    <link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>css/base.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath%>js/jquery-1.10.2.min.js"></script>
    <script src="<%=basePath%>js/bootstrap.min.js"></script>
    <script src="<%=basePath%>js/vue.min.js"></script>
    <style type="text/css">
        .table > thead > tr > th {
            text-align: center;
            font-weight: bold;
            font-size: 16px;
            color: #CC6633;
        }

        .table > tbody > tr > td {
            text-align: center;
            font-weight: bold;
            font-size: 12px;
            border: none;
        }
    </style>
</head>
<body>
<div id="monthStatic">
    <div @click="toUserInfo" style="width: 150px;margin: 10px 10px">
        <img style="width: 50px;height: 50px;float: left;" :src="userPhoto">
        <span style="line-height: 50px;padding-left: 10px">{{userName}}</span>
    </div>
    <div style="padding: 40px 10px">
        <div style="width:75px;margin: 0 auto">{{monthStaticModel.curMonth}}</div>
        <table class="table table-striped" style="margin-top:5px;width:100%;font-size: 14px;">
            <thead>
            <tr>
                <th style="border-right: 2px solid #ddd;">姓名</th>
                <th>支出</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="userPay in monthStaticModel.staticUserPays" key="userPay.userId">
                <td style="border-right: 2px solid #ddd;">
                    {{userPay.name}}
                </td>
                <td>
                    ￥{{userPay.totalPay}}
                </td>
            </tr>
            </tbody>
        </table>
        <div style="width:127px;margin: 50px auto">
            <div style="">总支出:
                <span style="padding-left: 2em">￥{{monthStaticModel.totalPayPrice}}</span>
            </div>
            <div style="margin: 10px auto">平均值:
                <span style="padding-left: 2em">￥{{monthStaticModel.avgPrice}}</span>
            </div>
        </div>
        <ul style="width:230px;margin: 0px auto">
            <li v-for="(calResult,index) in monthStaticModel.calResults" key="index">
                <span>{{calResult.spendUserName}}</span>
                <span style="padding-left: 2em;color: #990000">应给</span>
                <span style="padding-left: 2em">{{calResult.incomeUserName}}</span>
                <span style="padding-left: 2em">￥{{calResult.price}}</span>
            </li>
        </ul>
    </div>
</div>
</body>
<script>
    var vm = new Vue({
        el: "#monthStatic",
        data: {
            userName: '',
            userPhoto: '',
            monthStaticModel: ''
        },
        created: function () {
            //初始化当前用户信息
            this.initCurUser();
            //初始化月统计信息
            this.initMonthStatic();
        },
        methods: {
            toUserInfo: function () {
                window.location.href = "<%=basePath%>to/user/info";
            },
            initMonthStatic: function () {
                var outer = this;
                $.ajax({
                    method: "GET",
                    url: "<%=basePath%>month/bill/static/info?month=${curMonth}",
                    success: function (data) {
                        if (data.success) {
                            outer.monthStaticModel = data.result;
                        }
                    }
                })
            },
            initCurUser: function () {
                var outer = this;
                $.ajax({
                    method: "GET",
                    url: "<%=basePath%>get/curUser",
                    success: function (data) {
                        if (data.success) {
                            outer.userPhoto = data.result.photo;
                            outer.userName = data.result.userName;
                        } else {
                            //当前用户不存在，跳转登录页面
                            window.location.href = "/open/user/login";
                        }
                    }
                })
            }
        }
    });
</script>
</html>
