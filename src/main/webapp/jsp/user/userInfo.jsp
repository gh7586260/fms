<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>用户信息</title>
    <link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>css/base.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath%>js/html5.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/jquery-1.10.2.min.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/bootstrap.min.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/vue.min.js" type="text/javascript"></script>
</head>
<body>

<div id="userInfo">
    <img src="http://oss.pg.yibaotong.top/system/pg_oms_back.jpg"
         style="width:100%;height:100%;position: absolute;left: 0;top: 0;z-index: -1;">
    <div style="width: 200px; margin: 100px auto">
        <div style="width: 150px;margin: 0 auto;">
            <img :src="userPhoto" style="max-width: 100%;height: 150px;"/>
        </div>
        <span style="display:block;margin: 20px auto;text-align: center;">{{userName}}</span>
        <a @click="toPayBill" style="text-align: center;display: block;color: green;">查看流水</a>
        <div style="display:block;width: 70%;margin: 50px auto">
            <input @click="doLoginOut" type="button" value="登出"
                   style="display:block;height: 40px;width:100%;margin: 0 auto"/>
            <a @click="toModifyPwd" style="display: block;margin-top:20px;float:right;color: coral;">修改密码</a>
        </div>
    </div>
</div>
</body>

<script>
    var vm = new Vue({
        el: "#userInfo",
        data: {
            userId: 0,
            userPhoto: '',
            userName: ''
        },
        created: function () {
            var outer = this;
            $.ajax({
                method: "GET",
                url: "<%=basePath%>get/curUser",
                success: function (data) {
                    if (data.success) {
                        outer.userId = data.result.userId;
                        outer.userPhoto = data.result.photo;
                        outer.userName = data.result.userName;
                    } else {
                        //当前用户不存在，跳转登录页面
                        window.location.href = "/open/user/login";
                    }
                }
            })
        },
        methods: {
            //用户登出
            doLoginOut: function () {
                window.location.href = "/user/login/out";
            },
            toModifyPwd: function () {
                window.location.href = "/open/user/modify/pwd";
            },
            toPayBill: function () {
                window.location.href = "/bill/open";
            }
        }
    });
</script>
</html>
