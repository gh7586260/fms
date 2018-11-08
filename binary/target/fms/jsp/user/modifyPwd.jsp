<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
%>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>修改密码</title>
    <link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>css/base.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath%>js/html5.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/jquery-1.10.2.min.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/bootstrap.min.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/vue.min.js" type="text/javascript"></script>
</head>
<body>

<div style="width: 100%; padding: 100px 120px;" id="modifyPwd">
    <div style="margin: 30px auto;width: 172px">
        <span style="">当前用户：{{userName}}</span>
        <input v-model="newPassword" placeholder="新密码" style="display: block;margin-top: 30px;height: 30px;"/>
        <span style="color: red;margin: 0 auto;text-align: center;padding-left:50px">{{tipMsg}}</span>
        <input @click="doModify" type="button" value="确定" class="js_login"
               style="display:block;width: 80px;height: 40px;margin: 10px auto;"/>
    </div>
</div>
</body>
<script>
    var vm = new Vue({
        el: "#modifyPwd",
        data: {
            userId: 0,
            userName: '',
            newPassword: '',
            tipMsg: ''
        },
        created: function () {
            var outer = this;
            $.ajax({
                method: "GET",
                url: "<%=basePath%>get/curUser",
                success: function (data) {
                    if (data.success) {
                        outer.userId = data.result.userId;
                        outer.userName = data.result.userName;
                    } else {
                        //当前用户不存在，跳转登录页面
                        window.location.href = "/open/user/login";
                    }
                }
            })
        },
        methods: {
            doModify: function () {
                var outer = this;
                $.ajax({
                    method: "PUT",
                    url: "<%=basePath%>user/modify/password/excute?newPassword=" + this.newPassword,
                    success: function (data) {
                        if (data.success) {
                            outer.tipMsg = "修改成功";
                        } else {
                            outer.tipMsg = data.errorMsg;
                        }
                    }
                })
            }
        }
    });
</script>

</html>
