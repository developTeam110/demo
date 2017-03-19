<!DOCTYPE html>
<html lang="en">

<head>

    <title>登录</title>
    <#include "/comm/head.ftl"/>
    <link href="${rc.contextPath}/static/css/login.min.css" rel="stylesheet">
    <script>
        if(window.top!==window.self){window.top.location=window.location};
    </script>

</head>

<body class="signin">
    <div class="signinpanel">
        <div class="row">
            <div class="col-sm-7">
                <div class="signin-info">
                    <div class="logopanel m-b">
                        <h1>[ H+ ]</h1>
                    </div>
                    <div class="m-b"></div>
                    <h4>欢迎使用 <strong>H+ 后台主题UI框架</strong></h4>
                    <ul class="m-b">
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势一</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势二</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势三</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势四</li>
                        <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势五</li>
                    </ul>
                    <strong>还没有账号？ <a href="#">立即注册&raquo;</a></strong>
                </div>
            </div>
            <div class="col-sm-5">
                <input type="hidden" name="backUrl" placeholder="返回跳转URL" value="${backUrl}" />

                <form id="loginForm">
                    <h4 class="no-margins">登录：</h4>
                    <p class="m-t-md" id="errorTip" style="color:red;"></p>
                    <input type="text" name="loginName" class="form-control" placeholder="用户名" />
                    <input type="password" name="password" class="form-control" placeholder="密码" />
                    <a href="#">忘记密码了？</a>
                    <button class="btn btn-success btn-block" id="loginBtn">登录</button>
                </form>
            </div>
        </div>
        <div class="signup-footer">
            <div class="pull-left">
                &copy; 2016 All Rights Reserved.
            </div>
        </div>
    </div>
</body>

<#include "/comm/foot.ftl"/>

<script>
$(function(){
    $("#loginBtn").on("click", function() {
        $("#errorTip").html("");

        $.ajax({
            url: "${rc.contextPath}/admin/login.do",
            data: $("#loginForm").serialize(),
            type: "POST",
            dataType: "json",
            async: false,
            success: function(result) {
                if (result.code == 1) {
                    var backUrl = $("input[name='backUrl']").val();
                    if (isEmpty(backUrl)) {
                        backUrl = "${rc.contextPath}/admin/index.do"
                    }
                    window.location.href = backUrl;
                }else {
                    $("#errorTip").html(result.message);
                }
            },
            error: function() {
                console.log("Call saveWarningFlag method occurs error");
                $("#errorTip").html("网络连接异常，请稍后重试！");
            }
        });
        return false;
    });

});

</script>
</html>
