<!DOCTYPE html>
<html lang="en">

<head>

    <title>用户列表</title>
    <#include "/comm/head_table.ftl"/>
</head>

<body>

	<div class="container">

		<div id="toolbar" class="btn-group">
		    <button type="button" class="btn btn-default" id="addBtn">
		        <i class="glyphicon glyphicon-plus"></i>
		    </button>
		    <button type="button" class="btn btn-default" id="deleteBtn">
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
		        <th data-field="status" data-formatter="statusFormatter">状态</th>
		        <th data-field="lastLoginTime">登录时间</th>
		        <th data-field="lastLoginIp">登录IP地址</th>
		        <th data-field="action" data-formatter="actionFormatter" data-events="actionEvents">Action</th>
		    </tr>
		    </thead>
		</table>

	</div>

</body>
<#include "/comm/foot_table.ftl"/>

    <script>
        $(function(){
           $('#addBtn').on('click', function(){
                window.location.href="${rc.contextPath}/admin/user/toAdd.do";
            });

	        $("#deleteBtn").click(function() {
	            //toastr.success("Without any options", "Simple notification!")
	            //toastr.error("Hi, welcome to Inspinia. This is example of Toastr notification box.")

	            swal({
	                title: "您确定要删除这条信息吗",
	                type: "warning",
	                showCancelButton: true,
	                confirmButtonColor: "#DD6B55",
	                confirmButtonText: "删除",
	                closeOnConfirm: false
	            }, function() {
	                swal("删除成功！", "您已经永久删除了这条信息。", "success")
	            })

	        });
        });

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

        function statusFormatter(value, row, index) {
            console.log(value, row, index);
            return value;
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

</html>
