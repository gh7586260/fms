<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String filePath = (String) session.getAttribute("filePath");
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>用户信息</title>
    <link href="<%=filePath%>css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="<%=filePath%>css/base.css" rel="stylesheet" type="text/css">
    <script src="<%=filePath%>js/html5.js" type="text/javascript"></script>
    <script src="<%=filePath%>js/jquery-1.10.2.min.js" type="text/javascript"></script>
    <script src="<%=filePath%>js/bootstrap.min.js" type="text/javascript"></script>
    <script src="<%=filePath%>js/vue.min.js" type="text/javascript"></script>

    <style>
        .photoInput {
            width: 100%;
            height: 150px;
            line-height: 150px;
            opacity: 0;
            position: absolute;
            top: 100px;
            left: 0;
        }
    </style>
</head>
<body>
<div id="userInfo">
    <img src="<%=filePath%>file/fms_back.jpg"
         style="width:100%;height:100%;position: absolute;left: 0;top: 0;z-index: -1;">
    <div style="width: 200px; margin: 100px auto">
        <form id="uploadForm" action="/do/user/modify/photo" method="POST" enctype="multipart/form-data">
            <div style="width: 150px;margin: 0 auto;">
                <input name="photoFile" class="photoInput" @change="modifyPhoto" type="file" accept="image/*">
                <img :src="userPhoto" style="max-width: 100%;margin:0 auto;height: 150px;display: block"/>
            </div>
        </form>
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
            //用户修改头像
            modifyPhoto: function () {
                $('#uploadForm').submit();
            },
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
