<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String filePath = (String) session.getAttribute("filePath");
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>消费账单</title>
    <script src="<%=filePath%>js/html5.js" type="text/javascript"></script>
    <link href="<%=filePath%>css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="<%=filePath%>css/base.css" rel="stylesheet" type="text/css">
    <script src="<%=filePath%>js/jquery-1.10.2.min.js"></script>
    <script src="<%=filePath%>js/bootstrap.min.js"></script>
    <script src="<%=filePath%>js/vue.min.js"></script>
    <style type="text/css">
        .table > thead > th {
            text-align: center;
            font-weight: bold;
            font-size: 12px;
        }
    </style>
</head>
<body>
<div id="userTable">
    <div @click="toUserInfo" style="width: 150px;margin: 10px 10px">
        <img style="width: 50px;height: 50px;float: left;" :src="userPhoto">
        <span style="line-height: 50px;padding-left: 10px">{{userName}}</span>
    </div>
    <div style="padding: 40px 10px">
        <input type="month" v-model="curMonth" style="height: 25px;width: 120px"/>
        <select style="width: 80px;height: 25px" @change="queryBills" id="userSelect">
            <option value="0">全部</option>
            <option v-for="selectUser in allUsers" :value="selectUser.userId">{{selectUser.userName}}</option>
        </select>
        <a @click="toMonthStatic" style="padding-left: 15px">统计</a>
        <div @click="toOneBill(0)" type="button"
             style="background-color: #5cb85c;width: 70px;height:25px;line-height:25px;  border-radius:15px; color: #000000;float: right;text-align: center;margin-right: 10px">
            添加
        </div>
        <table class="table table-striped" style="margin-top:5px;width:100%;border: 1px solid #ddd;font-size: 14px;">
            <thead>
            <th>姓名</th>
            <th>事项</th>
            <th>金额</th>
            <th>日期</th>
            <th>操作</th>
            </thead>
            <tbody>
            <template v-for="payBill in payBills" key="payBill.billId">
                <tr>
                    <td>{{payBill.userName}}</td>
                    <td>{{payBill.detail}}</td>
                    <td>{{payBill.payPrice}}</td>
                    <td>{{payBill.payTime}}</td>
                    <td v-if="canEdit(payBill.userId)" style="color: #337ab7;" @click="toOneBill(payBill.billId)">编辑</td>
                    <td v-if="!canEdit(payBill.userId)" style="color: #cccccc;">编辑</td>
                </tr>
            </template>
            </tbody>
        </table>
    </div>
</div>
</body>
<script>
    var vm = new Vue({
        el: "#userTable",
        data: {
            curUserId: 0,
            userName: '',
            userPhoto: '',
            payBills: null,
            allUsers: null,
            curMonth: '', //当前查看月份
            nowMonth: ''  //当前时间月份
        },
        watch: {
            curMonth: function () {
                //查询账单列表
                this.queryBills();
            }
        },
        created: function () {
            //初始化当前日期
            this.initCurTime();
            //初始化当前用户信息
            this.initCurUser();
            //初始化所有用户
            this.initAllUsers();
            //查询账单列表
            this.queryBills();
        },
        methods: {
            canEdit:function (billUserId) {
                //是当前用户的账单且现在就是所查看的月份
                return this.curUserId == billUserId && this.curMonth == this.nowMonth;
            },
            toUserInfo: function () {
                window.location.href = "<%=basePath%>to/user/info";
            },
            //编辑或添加
            toOneBill: function (curBillId) {
                window.location.href = "<%=basePath%>open/one/bill?curBillId=" + curBillId;
            },
            toMonthStatic: function () {
                window.location.href = "<%=basePath%>open/month/static?curMonth=" + this.curMonth;
            },
            initCurTime: function () {
                var date = new Date;
                var year = date.getFullYear();
                var month = date.getMonth() + 1;
                month = (month < 10 ? "0" + month : month);
                this.nowMonth = year + '-' + month;
                this.curMonth = this.nowMonth;
            },
            queryBills: function () {
                if (this.curMonth == null || this.curMonth.trim() == '') {
                    return;
                }
                var outer = this;
                var selectedUserId = $('#userSelect option:selected').val();//选中的值
                $.ajax({
                    method: "GET",
                    url: "<%=basePath%>query/bills?curMonth=" + this.curMonth + "&userId=" + selectedUserId,
                    success: function (data) {
                        if (data.success) {
                            outer.payBills = data.list;
                        }
                    }
                });
            },
            initCurUser: function () {
                var outer = this;
                $.ajax({
                    method: "GET",
                    url: "<%=basePath%>get/curUser",
                    success: function (data) {
                        if (data.success) {
                            outer.curUserId = data.result.userId;
                            outer.userPhoto = data.result.photo;
                            outer.userName = data.result.userName;
                        } else {
                            //当前用户不存在，跳转登录页面
                            window.location.href = "/open/user/login";
                        }
                    }
                })
            },
            initAllUsers: function () {
                var outer = this;
                $.ajax({
                    method: "GET",
                    url: "<%=basePath%>query/all/users",
                    success: function (data) {
                        if (data.success) {
                            outer.allUsers = data.list;
                        }
                    }
                })
            }
        }
    });
</script>
</html>
