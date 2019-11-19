<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<#setting number_format="#">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>图片剪裁上传</title>
    <link rel="stylesheet" type="text/css" id="SiteTheme" href="/assets/theme/css/app.css" />
    <link href="/assets/script/plugin/SwfUpload/H5Upload.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="/assets/script/plugin/JQ1.8.3/jQmin.js"></script>
    <script type="text/javascript" src="/assets/script/plugin/SwfUpload/h5uploadhandler.js"></script>
    <link href="/assets/script/plugin/JQ1.8.3/imgareaselect/css/imgareaselect-animated.css" rel="stylesheet" type="text/css" media="screen" />
    <style>
        #pic {
            margin: 0;
            width: 400px;
            padding: 0;
            border: 1px solid #346F97;
            height: 300px;
            overflow: hidden;
            z-index: 0;
        }

        #progress {
            width: 140px !important;
            float: left;
            margin-top: 5px;
        }

        #uploadspeed, #remaining, #b_transfered {
            float: left;
            width: auto;
        }
    </style>
</head>
<body>
<form id="form1">
    <div style="width: 612px">
        <div style="width: 200px; float: left; padding-left: 10px;">
            <table border="0" cellspacing="0" cellpadding="0" style="width: 200px !important; margin-right: 10px" class="ecs_searchc">
                <tr>
                    <td style="line-height: 30px; font-weight: 700;">上传图片 </td>
                </tr>
                <tr>
                    <td>
                        <input id="txtFile0" style="width: 150px; margin-left: 3px; height: 24px" class="input" readonly="readonly" />
                        <input id="btnOpen" type="button" value="选 择" style="width: 36px; height: 26px; background-color: white" />
                        <input type="file" name="fileToUpload" id="txtFile" onchange="fileSelected();" style="width: 300px; display: none" />
                        <div id="divUploadTip" style="width: 180px; word-break: break-all; word-wrap: break-word;"></div>
                        <div id="divFileInfo" style="margin: 5px 0; display: none">
                            <div id="fileSize" style="height: 24px; line-height: 24px;"></div>
                            <div id="fileType" style="height: 24px; line-height: 24px;"></div>
                        </div>
                        <div id="progress_info" style="width: 180px; background-color: aliceblue; padding: 10px; display: none">
                            <div id="progress"></div>
                            <div id="progress_percent">&nbsp;</div>
                            <div class="clear_both"></div>
                            <div>
                                <div id="uploadspeed">&nbsp;</div>
                                <div id="remaining">&nbsp;</div>
                                <div id="b_transfered">&nbsp;</div>
                                <div class="clear_both"></div>
                            </div>
                            <div id="upload_response"></div>
                        </div>
                        <input id="hidFileName" type="hidden" />

                    </td>
                </tr>
                <tr>
                    <td style="line-height: 30px; font-weight: 700;">显示效果</td>
                </tr>
                <tr>
                    <td>图片翻转：
                        <select id="ddlPicRoll" class="input" style="width: 72px" size="1" onchange="OnPicRollChange();">
                            <option value="0" selected>默认</option>
                            <option value="1">右转90度</option>
                            <option value="2">右转180度</option>
                            <option value="3">右转270度</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div>锁定宽高比例<input id="ch_sd" type="checkbox" /></div>
                        <br />
                        宽：<input id="w" class="sd_input" value="4" style="width: 60px" /><br />
                        高：<input id="h" class="sd_input" value="3" style="width: 60px" /><br />
                    </td>
                </tr>
                <tr class="edit" style="display: none;">
                    <td>显示宽度：
                        <input id="txtWidth" class="input" style="width: 60px" onblur="CalculateShowHeight();" />px
                    </td>
                </tr>
                <tr class="edit" style="display: none;">
                    <td>显示高度：
                        <input id="txtHeight" class="input" style="width: 60px" onblur="CalculateShowWidth();" />px
                    </td>
                </tr>
            </table>
        </div>
        <div id="pic" style="float: left; min-height: 300px; min-width: 400px;">
            <img id="imgSelect" onload="InitLoadImage(this.width, this.height, 0);" src="">
        </div>
    </div>
    <div style="clear: both;"></div>
    <table border="0" cellpadding="0" cellspacing="0" width="100%" class="Popup2">

        <tr>
            <td colspan="2" style="height: 50px" align="center">
                <input id="btnCrop" type="button" value="确定" onclick="CropImage();" class="btn3" />&nbsp;
                <input id="btnClose" type="button" value="取消" onclick="CloseDialog();" class="btn2" />
            </td>
        </tr>
    </table>
</form>
</body>
</html>
<script src="/assets/script/plugin/JQ1.8.3/imgareaselect/jquery.imgareaselect.min.js" type="text/javascript"></script>
<script type="text/javascript" src="/assets/script/common/common.js"></script>
<script type="text/javascript" src="/assets/script/plugin/JQ1.8.3/layer/layer.js"></script>
<script type="text/javascript">
    //图片剪裁对象
    var cropInstance;
    var FileName;
    var IsInit = true;
    var OriWidth;
    var OriHeight;
    var aspectRatio = "0:0";//不受比例限制
    $(document).ready(function () {
        $("#txtFile0").click(function () {
            return $("#txtFile").click();
        });
        $("#btnOpen").click(function () {
            return $("#txtFile").click();
        });
        IsInit = true;
        if ("${from}" == "1") {
            $(".edit").show();
        }
        var oControl;
        if ("${from}" == "1") {
            oControl = parent.document.getElementById("eWebEditor1").contentWindow.FindImageObject("${imgTip}");
        } else {
            oControl = parent.document.getElementById("${imgTip}");
        }
        if ($(oControl).attr("src") != null) {
            var url = $(oControl).attr("src");
            url = url.split('?')[0];
            $("#imgSelect").attr("src", url);
            FileName = url.replace(/.*(\/|\\)/, "");
            $("#txtFile0").val(FileName);
            $("#hidFileName").val(FileName);

        } else {
            $("#imgSelect").attr("src", "/assets/images/editor/icondemo.gif");
        }

        //初始化上传
        if (ifilesize != 0) {
            iMaxFileSize = Math.min(iMaxFileSize, ifilesize);
        }
        var allowSize = bytesToSize(iMaxFileSize);
    });
    $('input').on('change', function () {
        sdWH();
    });
    var issteady ='${isSteadyCropDiv}';
    function sdWH() {

        if ($("#ch_sd").is(':checked')) {
            if ($("#w").val() == "0" || $("#h").val() == "0") {

                alert("锁定宽高不能为0");
                $("#ch_sd").attr("checked", false);
                return;
            }
            aspectRatio = $("#w").val() + ":" + $("#h").val();
            $(".sd_input").attr("readOnly", "true");
            issteady = 1;
        } else {
            aspectRatio = "0:0";
            $(".sd_input").removeAttr("readonly");
            issteady = 0;
        }


        var thum = InitMaxWidthHeight(400, 300, OriWidth, OriHeight);
        var thum1 = InitMaxWidthHeight(400, 300, $("#w").val(), $("#h").val());
        $("#imgSelect").css("height", thum.height).css("width", thum.width);
        var cropthum = thum;//如果不是固定比例剪裁，则初始化的拖动区跟图片一样大小
        if (issteady == "1") {
            var cropDivScale = "${cropDivScale}";
            var s = cropDivScale.split(":");
            cropthum = InitMaxWidthHeight(thum.width, thum.height, s[0], s[1]);
            cropInstance = $("#imgSelect").imgAreaSelect({ instance: true, handles: true, onSelectEnd: onSelectEnd, aspectRatio: aspectRatio, maxWidth: 400, maxHeight: 300, persistent: true, x1: 0, y1: 0, x2: thum1.width, y2: thum1.height });
        }
        cropInstance = $("#imgSelect").imgAreaSelect({ instance: true, handles: true, onSelectEnd: onSelectEnd, aspectRatio: aspectRatio, maxWidth: 400, maxHeight: 300, persistent: true, x1: 0, y1: 0, x2: thum1.width, y2: thum1.height });
        //初始化显示高度和宽度

        var showWidth = OriWidth;
        var showHeight = OriHeight;
        if (showWidth > 600) showWidth = 600;
        if (showHeight > 600) showHeight = 600;
        var tt = InitMaxWidthHeight(showWidth, showHeight, cropthum.width, cropthum.height);
        $("#txtWidth").val(tt.width);
        $("#txtHeight").val(tt.height);


    }
    //自适应加载图片的宽高
    function InitLoadImage(width, height, t) {
        if (!IsInit && t == 0) return;
        OriWidth = width;
        OriHeight = height;
        var thum = InitMaxWidthHeight(400, 300, width, height);
        $("#imgSelect").css("height", thum.height).css("width", thum.width);
        var cropthum = thum;//如果不是固定比例剪裁，则初始化的拖动区跟图片一样大小
        if ("${isSteadyCropDiv}" == "1") {
            var cropDivScale = "${cropDivScale}";
            var s = cropDivScale.split(":");
            cropthum = InitMaxWidthHeight(thum.width, thum.height, s[0], s[1]);
            cropInstance = $("#imgSelect").imgAreaSelect({ instance: true, handles: true, onSelectEnd: onSelectEnd, aspectRatio: aspectRatio, maxWidth: 400, maxHeight: 300, persistent: true, x1: 0, y1: 0, x2: cropthum.width, y2: cropthum.height });
        } else {
            cropInstance = $("#imgSelect").imgAreaSelect({ instance: true, handles: true, onSelectEnd: onSelectEnd, maxWidth: 400, maxHeight: 300, persistent: true, x1: 0, y1: 0, x2: cropthum.width, y2: cropthum.height });
        }
        //初始化显示高度和宽度
        var showWidth = OriWidth;
        var showHeight = OriHeight;
        if (showWidth > 600) showWidth = 600;
        if (showHeight > 600) showHeight = 600;
        var tt = InitMaxWidthHeight(showWidth, showHeight, cropthum.width, cropthum.height);
        $("#txtWidth").val(tt.width);
        $("#txtHeight").val(tt.height);
    }

    function onSelectEnd(img, crop) {
        //初始化显示高度和宽度
        var showWidth = OriWidth;
        var showHeight = OriHeight;
        if (showWidth > 600) showWidth = 600;
        if (showHeight > 600) showHeight = 600;
        var tt = InitMaxWidthHeight(showWidth, showHeight, crop.width, crop.height);
        $("#txtWidth").val(tt.width);
        $("#txtHeight").val(tt.height);
    }

    function CalculateShowHeight() {
        var selection = cropInstance.getSelection();
        var width = $("#txtWidth").val();
        var height = width * selection.height / selection.width;
        $("#txtHeight").val(parseInt(height));
    }
    function CalculateShowWidth() {
        var selection = cropInstance.getSelection();
        var height = $("#txtHeight").val();
        var width = height * selection.width / selection.height;
        $("#txtWidth").val(parseInt(width));
    }

    //选择需要截图的图片
    function InitImage(url, filename) {
        $("#imgSelect").css("height", "auto").css("width", "auto");
        $("#imgSelect").attr("src", url);
        FileName = filename;
    }

    function InitMaxWidthHeight(frameWidth, frameHeight, width, height) {
        var w = frameWidth;
        var h = parseInt(height * w / width);
        if (h > frameHeight) {
            h = frameHeight;
            w = parseInt(width * h / height);
        }
        return { "width": w, "height": h };
    }

    //验证上传图片
    function validate() {
        var validationResult = true;
        validationResult = jQuery.trim((jQuery("#txtFile0").val())) != "";
        if (!validationResult) {
            alert("请先上传图片！");
            return validationResult;
        }
        validationResult = jQuery.trim((jQuery("#hidFileName").val())) != "";
        if (!validationResult) {
            alert("请先上传图片！");
            return validationResult;
        }
        return validationResult;
    }

    //确定截图
    function CropImage() {
        if (!validate()) return;
        var selection = cropInstance.getSelection();
        var image = $("#imgSelect");
        var imageWidth = image.css("width").replace("px", "");
        var imageHeight = image.css("height").replace("px", "");
        $.ajax({
            type: "POST",
            data: { "folder":'${directory}',"fileName":FileName , "imageWidth": imageWidth,"imageHeight":imageHeight ,"cropWidth": selection.width,"cropHeight":selection.height ,"cropX":selection.x1,"cropY": selection.y1 , "fileServerId":'${fid}', "prex":'${prex}',"fileId":'${fieldId}',"isCopy":'${isCopy}',"userType":'${useType}'},
            dataType: "text",
            url: "/manage/upload/setOperateImage",
            success: function (result) {
                var ss = result;
                var width = $("#txtWidth").val();
                var height = $("#txtHeight").val();
                if ("${from}" != "1" && "${isSteadyCropDiv}" == "1") {
                    //不是从编辑器来
                    var cropDivScale = "${cropDivScale}";
                    var s = cropDivScale.split(":");
                    width = s[0];
                    height = s[1];
                }
                var url = "${prex}${directory}/" + ss;
                if ("${reducetype}" != "0") {
                    url = "${prex}${directory}/reduce/" + ss;
                }
                var html = "<img src=\"" + url + "\" width=\"" + width + "\" height=\"" + height + "\" class=\"uploadImage\">";
                if ("${from}" == "1") {
                    parent.SetEditorContent(html);

                } else {
                    parent.SetUploadImage(html);
                }
                CloseDialog();
            }
        });
    }

    //上传文件
    var FileID = "txtFile";
    var FileNameClientID = "hidFileName";
    sfiletypes = "${AllowExt}";
    ifilesize = "${AllowSize}";
    var prex = '${prex}';
    var directory = '${directory}';
    var nametype = "${useType}"=="skin"?"real":"";
    var autoUpload = 1;

    function uploadDone() {
        try {
            var url = "${prex}${directory}/" + $("#" + FileNameClientID).val();
            InitImage(url, $("#" + FileNameClientID).val());
            $("#divFileInfo").hide();
            $("#progress_info").hide();
            jQuery("#txtFile0").val($("#" + FileNameClientID).val());
        } catch (ex) {
            alert("提交数据出错");
        }
    }

    function __flash__removeCallback(instance, name) {
        if (instance != null) { instance[name] = null; }
    }

    //旋转时保存
    function OnPicRollChange() {
        var val = $("#ddlPicRoll").val();
        jQuery.ajax({
            type: "POST",
            data: {"url":'${prex}',"directory":'${directory}',"fileName":$("#" + FileNameClientID).val(),"picRollType": val },
            dataType: "json",
            url: "/manage/upload/picRollImageForServer",
            success: function (result) {
                setTimeout(function () {
                    var src = $("#imgSelect").attr("src");
                    $("#imgSelect").attr("src", "");
                    var i = src.indexOf("?");
                    if(i>0)
                    {
                        src = src.substring(0, i);
                    }
                    $("#imgSelect").attr("src", src + "?" + timestamp());
                    InitLoadImage(result[0], result[1], 1);
                }, 1000)
            }
        });
    }

    function timestamp() {
        var date = new Date(); //日期对象
        var now = "";
        now = date.getFullYear();
        now = now + (date.getMonth() + 1);
        now = now + date.getDate();
        now = now + date.getHours();
        now = now + date.getMinutes();
        now = now + date.getSeconds();
        return now;
    }
</script>