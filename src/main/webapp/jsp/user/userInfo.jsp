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

<div style="width: 100%; padding: 100px 120px;" id="userInfo">
    <div style="width: 150px;margin: 0 auto;">
        <img :src="userPhoto" style="max-width: 100%;height: 150px;"/>
    </div>
    <span style="display:block;margin: 30px auto;text-align: center;">{{userName}}</span>
    <a @click="toPayBill" style="text-align: center;display: block;">查看流水</a>
    <a @click="toModifyPwd" style="text-align: center;display: block;margin-top: 50px">修改密码</a>
    <input @click="doLoginOut" type="button" value="登出"
           style="display:block;width: 100px;height: 40px;margin: 10px auto;"/>
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
