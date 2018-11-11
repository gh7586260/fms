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
</head>
<body>
<div id="oneBill">
    <div @click="toUserInfo" style="width: 150px;margin: 10px 10px">
        <img style="width: 50px;height: 50px;float: left;" :src="userPhoto">
        <span style="line-height: 50px;padding-left: 10px">{{userName}}</span>
    </div>
    <div style="padding: 50px 30px">
        <div style="margin-top: 30px">
            <div style="width: 100px;float: left;line-height: 30px">使用事项</div>
            <input type="text" v-model="detail" style="display: block;width: 200px;height: 30px;"/>
        </div>
        <div style="margin-top: 30px">
            <div style="width: 100px;float: left;line-height: 30px">金额</div>
            <input type="number" v-model="payPrice" style="display: block;width: 50px;height: 30px;"/>
        </div>
        <div style="margin-top: 30px">
            <div style="width: 100px;float: left;line-height: 30px">日期</div>
            <input type="date" v-model="payDate" style="height: 25px"/>
        </div>
        <div v-if="curBillId==0" @click="addBill" type="button"
             style="background-color: #5cb85c;width: 150px;height:50px;line-height:50px;  border-radius:15px;
             color: #000000;text-align: center;margin: 80px auto">
            添加
        </div>
        <div v-if="curBillId!=0" style="margin: 80px 60px">
            <div @click="editBill" type="button"
                 style="background-color: #5cb85c;width: 100px;height:50px;line-height:50px;  border-radius:15px;
             color: #000000;text-align: center;float: left">
                更新
            </div>
            <div @click="deleteBill" type="button"
                 style="background-color: #761c19;width: 100px;height:50px;line-height:50px;margin-left: 20px;  border-radius:15px;
             color: #9d9d9d;text-align: center;float: left">
                删除
            </div>
        </div>
    </div>
</div>
</body>
<script>
    var vm = new Vue({
        el: "#oneBill",
        data: {
            userName: '',
            userPhoto: '',
            curBillId: 0,  //当前账单ID
            detail: '',     //消费详情
            payPrice: null,
            payDate: ''
        }
    });
</script>
</html>
