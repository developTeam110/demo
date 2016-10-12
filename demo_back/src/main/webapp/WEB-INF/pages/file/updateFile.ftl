<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>WebUploader演示</title>
    <#include "/comm/head.ftl"/>
    <link href="${rc.contextPath}/static/css/plugins/webuploader/webuploader.css" rel="stylesheet">
    <link href="${rc.contextPath}/static/css/plugins/webuploader/style.css" rel="stylesheet">
</head>
<body>
    <div id="wrapper">
        <div id="container">
            <!--头部，相册选择和格式选择-->

            <div id="uploader">
                <div class="queueList">
                    <div id="dndArea" class="placeholder">
                        <div id="filePicker"></div>
                        <p>或将照片拖到这里，单次最多可选300张</p>
                    </div>
                </div>
                <div class="statusBar" style="display:none;">
                    <div class="progress">
                        <span class="text">0%</span>
                        <span class="percentage"></span>
                    </div><div class="info"></div>
                    <div class="btns">
                        <div id="filePicker2"></div><div class="uploadBtn">开始上传</div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <#include "/comm/foot.ftl"/>
    <script src="${rc.contextPath}/static/js/plugins/webuploader/webuploader.js"></script>
    <script src="${rc.contextPath}/static/js/plugins/webuploader/upload.js"></script>
</body>
</html>
