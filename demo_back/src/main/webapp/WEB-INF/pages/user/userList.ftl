<!DOCTYPE html>
<html lang="en">

<head>

    <title>用户列表</title>
    <#include "/comm/head_table.ftl"/>
</head>

<body>

    <div class="container-fluid">

        <div class="col-sm-12">
            <ol class="breadcrumb">
                <li><a href="#">用户管理</a></li>
                <li class="active">列表</li>
            </ol>
        </div>

        <div class="col-sm-1"></div>
        <div class="col-sm-10">

        <div id="toolbar" class="btn-group">
            <button type="button" class="btn btn-default JS_add_btn">
                <i class="glyphicon glyphicon-plus"></i>
            </button>
            <button type="button" class="btn btn-default JS_delete_btn">
                <i class="glyphicon glyphicon-trash"></i>
            </button>
        </div>

        <table data-toggle="table"
               data-height="600"
               data-toolbar="#toolbar"
               data-pagination="true"
               data-side-pagination="server"
               data-search="true"
               data-query-params="queryParams"
               data-show-refresh = true
               data-show-toggle = true
               data-show-columns = true
               data-striped = true
               data-page-list = [10, 25, 50, 100, All]
               data-url="${rc.contextPath}/admin/user/list.do"
               data-delete-url="${rc.contextPath}/admin/user/delete.do"
               data-unique-id="username"
               data-id-field="username"
               >
            <thead>

            <tr>
                <th data-field="state" data-checkbox="true"></th>
                <th data-field="username">用户名</th>
                <th data-field="loginString">登录名</th>
                <th data-field="email">邮件</th>
                <th data-field="phone">电话号码</th>
                <th data-field="nickname">用户昵称</th>
                <th data-field="headImage">头像</th>
                <th data-field="innerFlag">内部账号</th>
                <th data-field="status" data-formatter="statusFormatter">状态</th>
                <th data-field="lastLoginTime">登录时间</th>
                <th data-field="lastLoginIp">登录IP地址</th>
                <th data-field="action" data-formatter="actionFormatter" data-events="actionEvents">操作</th>
            </tr>
            </thead>
        </table>
        </div>

        <div class="col-sm-1"></div>
    </div>

</body>
<#include "/comm/foot_table.ftl"/>

    <script>
        $(function(){
           var $table = $("table");
           window.myTable.init();

           $('#toolbar .JS_add_btn').on('click', function(){
                window.location.href="${rc.contextPath}/admin/user/toAdd.do";
            });

        });

        function queryParams(params) {
            return params;
        }

        function actionFormatter(value, row, index) {
            return [
                '<a class="edit ml10" href="javascript:void(0)" title="Edit">',
                '<i class="glyphicon glyphicon-edit"></i>',
                '</a>',
                '<a class="remove ml10" href="javascript:void(0)" title="Remove">',
                '<i class="glyphicon glyphicon-remove"></i>',
                '</a>'
            ].join('');
        }

        function statusFormatter(value, row, index) {
            return value;
        }

        window.actionEvents = {
            'click .edit': function (e, value, row, index) {
                alert('You click edit icon, row: ' + JSON.stringify(row));
                console.log(value, row, index);
                window.location.href="${rc.contextPath}/admin/user/toAdd.do?username=" + row.username;
            },

            'click .remove': function (e, value, row, index) {
                window.myTable.deleteItems(new Array(row.username));
            }
        };

    </script>

</html>
