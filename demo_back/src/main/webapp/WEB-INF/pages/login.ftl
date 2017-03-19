<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>后台登录</title>
    <#include "/comm/head.ftl"/>

    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
    <script>if(window.top !== window.self){ window.top.location = window.location;}</script>
</head>

<body class="gray-bg">

    <div class="middle-box text-center loginscreen  animated fadeInDown">
        <div>
            <div>

                <h1 class="logo-name" style="font-size: 90px;">BACK</h1>

            </div>
            <h3>欢迎使用</h3>

            <input type="hidden" name="backUrl" placeholder="返回跳转URL" value="${backUrl}" />

            <form class="m-t" role="form" action="#" id="loginForm">
                <p class="m-t-md" id="errorTip" style="color:red;"></p>

                <div class="form-group">
                    <input type="text" name="loginName" class="form-control" placeholder="用户名">
                </div>
                <div class="form-group">
                    <input type="password" name="password" class="form-control" placeholder="密码">
                </div>
                <button id="loginBtn" class="btn btn-primary block full-width m-b">登 录</button>

                <p class="text-muted text-center"> <a href="login.html#"><small>忘记密码了？</small></a> | <a href="register.html">注册一个新账号</a>
                </p>

            </form>
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
                    if (my.isEmpty(backUrl)) {
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
