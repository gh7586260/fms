<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>添加账单</title>
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
        <div v-if="curBillId!=0" style="margin-top: 60px">
            <div style="width: 50%;float: left">
                <div @click="editBill" type="button"
                     style="margin:0 auto;background-color: #5cb85c;width: 100px;height:50px;line-height:50px;  border-radius:15px;
             color: #000000;text-align: center;">
                    更新
                </div>
            </div>
            <div style="width: 50%;float: left">
                <div @click="deleteBill" type="button"
                     style="margin:0 auto;background-color: #761c19;width: 100px;height:50px;line-height:50px; border-radius:15px;
             color: #9d9d9d;text-align: center;">
                    删除
                </div>
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
        },
        watch: {
            detail: function () {
                if (vm.detail.length > 11) {
                    vm.detail = vm.detail.substring(0, 11);
                }
            }
        },
        created: function () {
            //初始化当前用户信息
            this.initCurUser();
            this.curBillId =${curBillId == null ?0:curBillId};
            //初始化当前账单信息
            if (this.curBillId != null && this.curBillId != 0) {
                this.initCurBill();
            } else {
                //初始化当前日期
                this.initCurTime();
            }
        },
        methods: {
            addBill: function () {
                if (this.detail.trim() == '' || this.payPrice == null || this.payDate.trim() == '') {
                    alert("参数错误");
                    return;
                }
                window.location.href = "<%=basePath%>add/bill?detail=" + this.detail + "&payPrice=" + this.payPrice + "&payTime=" + this.payDate;
            },
            editBill: function () {
                window.location.href = "<%=basePath%>edit/bill?billId=" + this.curBillId + "&detail=" + this.detail + "&payPrice=" + this.payPrice + "&payTime=" + this.payDate;
            },
            deleteBill: function () {
                window.location.href = "<%=basePath%>delete/bill?billId=" + this.curBillId;
            },
            toUserInfo: function () {
                window.location.href = "<%=basePath%>to/user/info";
            },
            initCurBill: function () {
                var outer = this;
                $.ajax({
                    method: "GET",
                    url: "<%=basePath%>get/bill/info?billId=" + this.curBillId,
                    success: function (data) {
                        if (data.success) {
                            outer.detail = data.result.detail;
                            outer.payPrice = data.result.payPrice;
                            outer.payDate = data.result.payTime;
                        }
                    }
                })
            },
            initCurTime: function () {
                var date = new Date;
                var year = date.getFullYear();
                var month = date.getMonth() + 1;
                var day = date.getDate()
                month = (month < 10 ? "0" + month : month);
                day = (day < 10 ? "0" + day : day);
                this.payDate = year + '-' + month + '-' + day;
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
