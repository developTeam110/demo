<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>权限不足</title>
    <#include "/comm/head.ftl"/>
</head>

<body class="gray-bg">
    <div class="middle-box text-center animated fadeInDown">
        <h1 style="font-size: 100px;">Permission denied</h1>
        <h3 class="font-bold">权限不足</h3>

        <div class="error-desc">
            你当前账户没有访问该资源权限...
            <br/>您可以返回主页看看
            <br/><a href="${rc.contextPath}/admin/index.do" class="btn btn-primary m-t">主页</a>
        </div>
    </div>

    <#include "/comm/foot.ftl"/>
</body>

</html>
