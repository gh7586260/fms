<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String filePath = (String) session.getAttribute("filePath");
%>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>错误页面</title>
    <link href="<%=filePath%>css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="<%=filePath%>css/base.css" rel="stylesheet" type="text/css">
    <script src="<%=filePath%>js/html5.js" type="text/javascript"></script>
    <script src="<%=filePath%>js/jquery-1.10.2.min.js" type="text/javascript"></script>
    <script src="<%=filePath%>js/bootstrap.min.js" type="text/javascript"></script>
    <script src="<%=filePath%>js/vue.min.js" type="text/javascript"></script>
</head>
<body>

<div style="width: 100%;margin-top: 100px ">
    <span class="js_userName" placeholder="用户名" style="display: block;padding:0 10px;text-align: center;color:crimson;font-size: large">
        ${errorMsg}
    </span>
</div>
</body>
</html>
