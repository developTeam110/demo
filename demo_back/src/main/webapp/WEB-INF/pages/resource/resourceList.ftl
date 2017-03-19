<!DOCTYPE html>
<html lang="en">

<head>

    <title>资源列表</title>
    <#include "/comm/head_table.ftl"/>
</head>

<body>

    <div class="container-fluid">

        <div class="row">
            <div class="col-sm-12">
                <ol class="breadcrumb">
                    <li><a href="#">资源管理</a></li>
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
                            <label>编码：</label>
                        </td>
                        <td>
                            <input class="form-control" name="name" type="text">
                        </td>

                        <td>
                            <label>资源名称：</label>
                        </td>
                        <td>
                            <input class="form-control" name="name" type="text">
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
                   data-page-list = "[10, 20, 50, 100]"
                   data-url="${rc.contextPath}/admin/resource/list.do"
                   data-delete-url="${rc.contextPath}/admin/resource/delete.do"
                   data-unique-id="id"
                   data-id-field="id"
                   >
                <thead>
    
                <tr>
                    <th data-field="state" data-checkbox="true"></th>
                    <th data-field="id">主键ID</th>
                    <th data-field="code">资源编码</th>
                    <th data-field="name">资源名称</th>
                    <th data-field="desc">描述</th>
                    <th data-field="phone">电话号码</th>
                    <th data-field="nickname">资源昵称</th>
                    <th data-field="createTime" data-formatter="myTable.timestampTimeFormatter">创建时间</th>
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
                window.location.href="${rc.contextPath}/admin/resource/toAdd.do";
            });

           $('#toolbarSearch .JS_search_btn').on('click', function(){
                $('button[name="refresh"]').click();
            });

        });

        function queryParams(params) {
            var $searchTable = $("#toolbarSearch table");
            params.code = $searchTable.find("input[name='code']").val();
            params.name = $searchTable.find("input[name='name']").val();
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
        /* 显示列信息格式化方法 结束 */

        window.actionEvents = {
            'click .edit': function (e, value, row, index) {
                window.location.href="${rc.contextPath}/admin/resource/toEdit.do?uniqueId=" + row.id;
            },

            'click .remove': function (e, value, row, index) {
                window.myTable.deleteItems([row.id]);
            }
        };

    </script>

</html>
