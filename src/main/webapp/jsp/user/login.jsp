<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String filePath = (String) session.getAttribute("filePath");
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>登录页面</title>
    <link href="<%=filePath%>css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="<%=filePath%>css/base.css" rel="stylesheet" type="text/css">
    <script src="<%=filePath%>js/html5.js" type="text/javascript"></script>
    <script src="<%=filePath%>js/jquery-1.10.2.min.js" type="text/javascript"></script>
    <script src="<%=filePath%>js/bootstrap.min.js" type="text/javascript"></script>
    <script src="<%=filePath%>js/vue.min.js" type="text/javascript"></script>
</head>
<body>

<div id="userLogin">
    <img src="<%=filePath%>file/fms_back.jpg"
         style="width:100%;height:100%;position: absolute;left: 0;top: 0;z-index: -1;">
    <div style="width: 200px; margin: 100px auto">
        <input v-model="userName" placeholder="用户名"
               style="display: block;margin: 30px auto;height: 30px;padding-left: 5px"/>
        <input v-model="password" placeholder="密码" style="display: block;margin: 30px auto;height: 30px;padding-left: 5px"/>
        <span style="color: red;margin: 0 auto;text-align: center;padding-left:50px">{{errorMsg}}</span>
        <input @click="doLogin" type="button" value="登录"
               style="display:block;width: 80px;height: 40px;margin: 10px auto;"></input>
    </div>
</div>
</body>

<script>
    var vm = new Vue({
        el: "#userLogin",
        data: {
            userName: '',
            password: '',
            errorMsg: ''
        },
        methods: {
            doLogin: function () {
                var outer = this;
                $.ajax({
                    method: 'GET',
                    url: "<%=basePath%>user/check?userName=" + this.userName + "&password=" + this.password,
                    success: function (data) {
                        if (data.success) {
                            window.location.href = "/user/login/excute?userName=" + outer.userName + "&password=" + outer.password;
                        } else {
                            outer.errorMsg = data.errorMsg;
                        }
                    }
                })
            }
        }
    })
</script>
</html>
