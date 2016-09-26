<!DOCTYPE html>
<html lang="en">

<head>

    <title>用户列表</title>
    <#include "/comm/head_table.ftl"/>
</head>

<body>

    <div class="container-fluid">

        <div class="row">
            <div class="col-sm-12">
                <ol class="breadcrumb">
                    <li><a href="#">用户管理</a></li>
                    <li class="active">列表</li>
                </ol>
        </div>

        <div class="row">
            <div class="col-sm-1"></div>
            <div class="col-sm-10">

            <div id="toolbarSearch">
                <table>
                    <tr>
                        <td>
                            <label>用户名：</label>
                        </td>
                        <td>
                            <input class="form-control" name="username" type="text">
                        </td>

                        <td>
                            <label>登录名：</label>
                        </td>
                        <td>
                            <input class="form-control" name="loginString" type="text">
                        </td>

                        <td>
                            <label>邮箱：</label>
                        </td>
                        <td>
                            <input class="form-control" name="email" type="text">
                        </td>

                        <td>
                            <label>手机号：</label>
                        </td>
                        <td>
                            <input class="form-control" name="phone" type="text">
                        </td>

                        <td>
                            <label>用户状态：</label>
                        </td>
                        <td>
                            <select class="form-control" name="status">
                                <#list statuses as item>
                                    <option value="${item.code()}" >${item.cnName()}</option>
                                </#list>
                            </select>
                        </td>

                        <td>
                            <label>是否内部账号：</label>
                        </td>
                        <td>
                            <select class="form-control" name="innerFlag">
                                <option value >全部</option>
                                <option value="true" >是</option>
                                <option value="false" >否</option>
                            </select>
                        </td>

                        <td>
                            <button type="button" class="btn btn-default JS_search_btn ">
                                <i class="glyphicon glyphicon-search"></i>
                            </button>
                        </td>
                    </tr>
                </table>
            </div>

            <div id="toolbar" class="btn-group">
                <button type="button" class="btn btn-default JS_add_btn">
                    <i class="glyphicon glyphicon-plus"></i>
                </button>
                <button type="button" class="btn btn-default JS_delete_btn">
                    <i class="glyphicon glyphicon-trash"></i>
                </button>
            </div>
    
            <table data-toggle="table" id="table"
                   data-height="600"
                   data-toolbar="#toolbar"
                   data-pagination="true"
                   data-side-pagination="server"
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
                    <th data-field="innerFlag" data-formatter="myTable.booleanFormatter">内部账号</th>
                    <th data-field="status" data-formatter="statusFormatter">状态</th>
                    <th data-field="lastLoginTime" data-formatter="myTable.timestampTimeFormatter">登录时间</th>
                    <th data-field="lastLoginIp">登录IP地址</th>
                    <th data-field="action" data-formatter="actionFormatter" data-events="actionEvents">操作</th>
                </tr>
                </thead>
            </table>
            </div>
    
            <div class="col-sm-1"></div>
        </div>

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

           $('#toolbarSearch .JS_search_btn').on('click', function(){
                $('button[name="refresh"]').click();
            });

        });

        function queryParams(params) {
            var $searchTable = $("#toolbarSearch table");
            params.username = $searchTable.find("input[name='username']").val();
            params.loginString = $searchTable.find("input[name='loginString']").val();
            params.email = $searchTable.find("input[name='email']").val();
            params.phone = $searchTable.find("input[name='phone']").val();
            params.status = $searchTable.find("select[name='status'] option:selected").val();
            params.innerFlag = $searchTable.find("select[name='innerFlag'] option:selected").val();
            return params;
        }

        /* 显示列信息格式化方法 开始 */
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
            var $searchTable = $("#toolbarSearch table");
            var $statusOption = $searchTable.find("select[name='status'] option");
            var text = "";
            $.each($statusOption, function(index, item) {
                var $option = $(item);
                if (value == $option.val()) {
                    text = $option.html();
                    return false;
                }
            });
            return text;
        }
        /* 显示列信息格式化方法 结束 */

        window.actionEvents = {
            'click .edit': function (e, value, row, index) {
                window.location.href="${rc.contextPath}/admin/user/toEdit.do?uniqueId=" + row.username;
            },

            'click .remove': function (e, value, row, index) {
                window.myTable.deleteItems(new Array(row.username));
            }
        };

    </script>

</html>
