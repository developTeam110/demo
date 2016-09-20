<!DOCTYPE html>
<html>

<head>

    <title>添加用户</title>
    <#include "/comm/head.ftl"/>

</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="col-sm-2"></div>
        <div class="col-sm-8">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>添加用户</h5>
                </div>
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="addUserForm" action="#">

                        <div class="form-group">
                            <label class="col-sm-3 control-label">登录名：</label>
                            <div class="col-sm-8">
                                <input id="loginString" name="loginString" class="form-control" type="text">
                                <span class="help-block m-b-none"><i class="fa fa-info-circle"></i>4到16个英文字符、数字或下划线</span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">邮箱：</label>
                            <div class="col-sm-8">
                                <input id="email" name="email" class="form-control" type="text" aria-required="true" aria-invalid="false" class="valid">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">电话号码：</label>
                            <div class="col-sm-8">
                                <input id="phone" name="phone" class="form-control" type="text" aria-required="true" aria-invalid="true" class="error">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">用户昵称：</label>
                            <div class="col-sm-8">
                                <input id="nickname" name="nickname" class="form-control" type="text" aria-required="true" aria-invalid="true" class="error">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">头像：</label>
                            <div class="col-sm-8">
                                <input id="nickname" name="nickname" class="form-control" type="text" aria-required="true" aria-invalid="true" class="error">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">密码：</label>
                            <div class="col-sm-8">
                                <input id="password" name="password" class="form-control" type="password">
                                <span class="help-block m-b-none"><i class="fa fa-info-circle"></i>必须是6到16个英文字符、数字</span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">确认密码：</label>
                            <div class="col-sm-8">
                                <input id="confirmPassword" name="confirmPassword" class="form-control" type="password">
                                <span class="help-block m-b-none"><i class="fa fa-info-circle"></i> 请再次输入您的密码</span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">状态：</label>
                            <div class="col-sm-8">
                                <select class="form-control m-b" name="status">
                                    <#list statuses as item>
                                        <option value="${item.code()}">${item.cnName()}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否内部账号：</label>
                            <div class="col-sm-8">
                                <div class="checkbox i-checks">
                                    <label><input name="innerFlag" type="checkbox" value="true"></label>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-3">
                                <button class="btn btn-primary" id="submitBtn">提交</button>
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
    $(document).ready(function(){
        $("#submitBtn").on("click", function(e) {
                e.preventDefault();
                e.stopPropagation();
                $.ajax({
                    url: "${rc.contextPath}/admin/user/addUser.do",
                    data: $("#addUserForm").serialize(),
                    type: "POST",
                    dataType: "json",
                    async: false,
                    success: function(result) {
                        if (result.code == 1) {
                            window.location.href="${rc.contextPath}/admin/index.do";
                        }else {
                            $("#errorTip").html(result.message);
                        }
                    },
                    error: function() {
                        console.log("Call saveWarningFlag method occurs error");
                        $("#errorTip").html("网络连接异常，请稍后重试！");
                    }
            });
        });
    })

    </script>
</body>

</html>
