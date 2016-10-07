<!DOCTYPE html>
<html>


<!-- Mirrored from www.zi-han.net/theme/hplus/lockscreen.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 20 Jan 2016 14:19:52 GMT -->
<head>
    <title>登录超时</title>
    <#include "/comm/head.ftl"/>
    <script>if(window.top !== window.self){ window.top.location = window.location;}</script>

</head>

<body class="gray-bg">

    <div class="lock-word animated fadeInDown">
    </div>
    <div class="middle-box text-center lockscreen animated fadeInDown">
        <div>
            <div class="m-b-md">
                <img alt="image" class="img-circle circle-border" src="${user.headImage}">
            </div>
            <h3>${user.nickname}</h3>
            <p>您的登录已过期，需重新登录</p>

            <input type="hidden" name="backUrl" placeholder="返回跳转URL" value="${backUrl}" />
            <form class="m-t" role="form" action="#" id="reloginForm">
                <p class="m-t-md" id="errorTip" style="color:red;"></p>
                <div class="form-group">
                    <input type="hidden" name="loginName" class="form-control" value="${user.username}"/>
                    <input type="password" name="password" class="form-control" placeholder="请输入密码" required="">
                </div>
                <button class="btn btn-primary block full-width" id="reloginBtn">重新登录</button>
            </form>
        </div>
    </div>

    <#include "/comm/foot.ftl"/>
</body>

<script>
$(function(){
    $("#reloginBtn").on("click", function() {
        $("#errorTip").html("");

        $.ajax({
            url: "${rc.contextPath}/admin/login.do",
            data: $("#reloginForm").serialize(),
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
