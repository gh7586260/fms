<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
%>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>错误页面</title>
    <link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>css/base.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath%>js/html5.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/jquery-1.10.2.min.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/bootstrap.min.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/vue.min.js" type="text/javascript"></script>
</head>
<body>

<div style="width: 100%; padding: 100px 120px;">
    <span class="js_userName" placeholder="用户名" style="display: block;margin: 30px auto;height: 30px;">
        ${errorMsg}
    </span>
</div>
</body>
</html>
