<!DOCTYPE html>
<html>

<head>

    <title>编辑资源</title>
    <#include "/comm/head.ftl"/>

</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">

        <div class="col-sm-12">
            <ol class="breadcrumb">
                <li><a href="#">资源管理</a></li>
                <li class="active">编辑资源</li>
            </ol>
        </div>


        <div class="col-sm-2"></div>
        <div class="col-sm-8">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>编辑资源</h5>
                </div>
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="resourceForm" action="#">

                        <div class="form-group">
                            <label class="col-sm-3 control-label">主键ID：</label>
                            <div class="col-sm-8">
                                <input name="id" class="form-control" type="text" value="${resource.id}" readonly="readonly">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">编码：</label>
                            <div class="col-sm-8">
                                <input name="code" class="form-control" type="text" value="${resource.code}">
                                <span class="help-block m-b-none"><i class="fa fa-info-circle"></i>1到64个英文字符</span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">名称：</label>
                            <div class="col-sm-8">
                                <input name="name" class="form-control" type="text" value="${resource.name}">
                                <span class="help-block m-b-none"><i class="fa fa-info-circle"></i>1到64个英文字符</span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">URL资源：</label>
                            <div class="col-sm-8">
                                <input name="url" class="form-control" type="text" value="${resource.name}">
                                <span class="help-block m-b-none"><i class="fa fa-info-circle"></i>1到64个英文字符</span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">描述：</label>
                            <div class="col-sm-8">
                                <textarea name="desc" class="form-control">${resource.desc}</textarea>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">排序值：</label>
                            <div class="col-sm-8">
                                <input name="sort" class="form-control" type="text" value="${resource.sort}">
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-3">
                                <button class="btn btn-primary" type="submit">提交</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="col-sm-2"></div>
    </div>

    <#include "/comm/foot.ftl"/>

    <script>
    $().ready(function(){//jquery init start

        $("#resourceForm").validate({
            rules: {
              code: {
                  required: true,
                  rangelength:[1,64]
              },
              name: {
                  required: true,
                  rangelength:[1,64]
              },
              url: {
                  required: true,
                  rangelength:[1,500]
              },
              sort: {
                  digits:true
              }
            },
            submitHandler: function(form) {
                var $form = $(form);
                $.ajax({
                    url: "${rc.contextPath}/admin/resource/edit.do",
                    data: $form.serialize(),
                    type: "POST",
                    dataType: "json",
                    async: false,
                    success: function(result) {
                        if (result.code == 1) {
                            window.location.href="${rc.contextPath}/admin/resource/toList.do";
                        }else {
                            toastr.error(result.message)
                        }
                    },
                    error: function() {
                        console.log("Call saveWarningFlag method occurs error");
                        toastr.error("网络连接异常，请稍后重试！")
                    }
                });
            }
        });

    });//jquery init end

    </script>
</body>

</html>
