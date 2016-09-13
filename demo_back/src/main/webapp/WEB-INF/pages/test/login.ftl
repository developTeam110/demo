<!DOCTYPE html>
<html lang="en">

<head>

    <title>登录</title>
<#include "/comm/head_meta.ftl"/>
</head>

<body>

     <div id="example"></div>

<#include "/comm/foot.ftl"/>
    <script type='text/javascript'>
        var options = {
            containerClass:"pagination"
            , currentPage:1
            , numberOfPages: 3
            , totalPages:11
            , pageUrl:function(type,page){
                return null;
            }
            , onPageClicked:null
            , onPageChanged:null
        }

        $('#example').bootstrapPaginator(options);
    </script>
</body>

</html>
