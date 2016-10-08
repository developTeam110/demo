<!DOCTYPE html>
<html>

<head>

    <title>编辑用户</title>
    <#include "/comm/head.ftl"/>

</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">

        <div class="col-sm-12">
            <ol class="breadcrumb">
                <li><a href="#">用户管理</a></li>
                <li class="active">编辑用户</li>
            </ol>
        </div>

        <div class="col-sm-2"></div>
        <div class="col-sm-8">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>编辑用户</h5>
                </div>
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="userForm" action="#">

                        <div class="form-group">
                            <label class="col-sm-3 control-label">用户名：</label>
                            <div class="col-sm-8">
                                <input name="username" class="form-control" type="text" value="${user.username}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">登录名：</label>
                            <div class="col-sm-8">
                                <input name="loginString" class="form-control" type="text" value="${user.loginString}">
                                <span class="help-block m-b-none"><i class="fa fa-info-circle"></i>4到16个英文字符、数字或下划线（不能为手机号、邮箱）</span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">邮箱：</label>
                            <div class="col-sm-8">
                                <input name="email" class="form-control" type="text" value="${user.email}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">电话号码：</label>
                            <div class="col-sm-8">
                                <input name="phone" class="form-control" type="text" value="${user.phone}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">用户昵称：</label>
                            <div class="col-sm-8">
                                <input name="nickname" class="form-control" type="text" value="${user.nickname}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">头像：</label>
                            <div class="col-sm-8">
                                <input name="headImage" class="form-control" type="text" value="${user.headImage}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">密码：</label>
                            <div class="col-sm-8">
                                <input name="password" class="form-control" type="password" value="${user.password}">
                                <span class="help-block m-b-none"><i class="fa fa-info-circle"></i>必须是6到16个英文字符、数字</span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">确认密码：</label>
                            <div class="col-sm-8">
                                <input name="confirmPassword" class="form-control" type="password" value="${user.password}">
                                <span class="help-block m-b-none"><i class="fa fa-info-circle"></i> 请再次输入您的密码</span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">状态：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b" name="status">
                                    <#list statuses as item>
                                        <option value="${item.code()}" <#if item.code() == user.status>selected="selected"</#if> >${item.cnName()}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否内部账号：</label>
                            <div class="col-sm-8">
                                <div class="checkbox i-checks">
                                    <label><input name="innerFlag" type="checkbox" <#if user.innerFlag>checked="checked" value="true"</#if>></label>
                                </div>
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

        $("#userForm").validate({
            rules: {
              loginString: {
                  required: true,
                  loginName: true
              },
              email: {
                  email: true
              },
              phone: {
                  mobile: true
              },
              nickname: {
                required: true
              },
              password: {
                  required: true,
                  password: true
              },
              confirmPassword: {
                  required: true,
                  equalTo: "input[name='password']"
              },
            },
            submitHandler: function(form) {
                var $form = $(form);
                $.ajax({
                    url: "${rc.contextPath}/admin/user/edit.do",
                    data: $form.serialize(),
                    type: "POST",
                    dataType: "json",
                    async: false,
                    success: function(result) {
                        if (result.code == 1) {
                            window.location.href="${rc.contextPath}/admin/user/toList.do";
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
