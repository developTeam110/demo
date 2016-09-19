<!DOCTYPE html>
<html lang="en">

<head>

    <title>用户列表</title>
    <#include "/comm/head_table.ftl"/>
</head>

<body>

	<div class="container">

		<div id="toolbar" class="btn-group">
		    <button type="button" class="btn btn-default">
		        <i class="glyphicon glyphicon-plus"></i>
		    </button>
		    <button type="button" class="btn btn-default">
		        <i class="glyphicon glyphicon-trash"></i>
		    </button>
		</div>

		<table data-toggle="table"
		       data-height="600"
		       data-toolbar="#toolbar"
		       data-pagination="true"
		       data-side-pagination="server"
		       data-search="true"
			   data-page-number: 1
    		   data-page-size: 4
    		   data-page-list: [5, 10, 20]
    		   data-query-params="queryParams"
    		   data-url="${rc.contextPath}/admin/user/list.do"
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
		        <th data-field="status">状态</th>
		        <th data-field="lastLoginTime">登录时间</th>
		        <th data-field="lastLoginIp">登录IP地址</th>
		        <th data-field="action" data-formatter="actionFormatter" data-events="actionEvents">Action</th>
		    </tr>
		    </thead>
		</table>

	</div>

	<script>
	function queryParams(params) {
	console.log(params);
	    return params;
	}

		function actionFormatter(value, row, index) {
		    return [
		        '<a class="like" href="javascript:void(0)" title="Like">',
		        '<i class="glyphicon glyphicon-heart"></i>',
		        '</a>',
		        '<a class="edit ml10" href="javascript:void(0)" title="Edit">',
		        '<i class="glyphicon glyphicon-edit"></i>',
		        '</a>',
		        '<a class="remove ml10" href="javascript:void(0)" title="Remove">',
		        '<i class="glyphicon glyphicon-remove"></i>',
		        '</a>'
		    ].join('');
		}
		
		window.actionEvents = {
		    'click .like': function (e, value, row, index) {
		        alert('You click like icon, row: ' + JSON.stringify(row));
		        console.log(value, row, index);
		    },
		    'click .edit': function (e, value, row, index) {
		        alert('You click edit icon, row: ' + JSON.stringify(row));
		        console.log(value, row, index);
		    },
		    'click .remove': function (e, value, row, index) {
		        alert('You click remove icon, row: ' + JSON.stringify(row));
		        console.log(value, row, index);
		    }
		};
	</script>
</body>
<#include "/comm/foot_table.ftl"/>
</html>
