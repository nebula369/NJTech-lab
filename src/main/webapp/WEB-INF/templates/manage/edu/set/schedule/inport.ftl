<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>

<div class="main-content">
    <div class="main-content-inner">
        <div class="page-content" style="height: 200px">
            <div class="row">
                <div class="col-xs-12">
                    <form class="form-horizontal" id="validation-form" role="form">
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 0px;width: 100px;" for="name">选择文件:</label>
                            <div class="col-sm-9" style="float: left;">
                                <input type="file" name="test1" id="test1">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="name"></label>
                            <div class="col-sm-9" style="float: left;">
                               <a href="/assets/static/SchoolRestTime.xls">作息模板下载</a>
                            </div>
                        </div>
                        <div class="col-md-offset-3 col-md-9" style="margin-left: 90px;">
                            <button id="btnInport" class="btn btn-success btn-sm" type="button">
                                <i class="ace-icon fa fa-check"></i>
                                确定导入
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<#include "/controls/appbottom.ftl"/>
<script src="/assets/lib/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="/assets/lib/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript">

    $('#validation-form').validate({
        errorElement: 'div',
        errorClass: 'help-block',
        focusInvalid: true,
        ignore: "",
        rules: {
            test1: {
                required: true
            }
        },

        messages: {
            test1: {
                required: "请选择作息数据Excel文件"
            }
        },
        highlight: function (e) {
            $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
        },
        success: function (e) {
            $(e).closest('.form-group').removeClass('has-error');
            $(e).remove();
        },
        errorPlacement: function (error, element) {
            //在easybasic.js中定义
            initErrorPlacement(error, element);
        }
    });

    layui.use('upload', function(){
        var $ = layui.jquery
                ,upload = layui.upload;

        //普通图片上传
        var uploadInst = upload.render({
            elem: '#test1'
            ,url: '/manage/upload/fileupload'
            ,data:{"directory":"upload/temp"}
            ,exts:"xls"
            ,acceptMime:"file/xls"
            ,auto: false
            ,bindAction:"#btnInport"
            ,xhr:xhrOnProgress
            ,progress:function(value){//上传进度回调 value进度值

            }
            ,done: function(res){
                //如果上传失败
                if(res.code == 0){
                    return layer.msg('上传失败');
                }
                doInport(res.data);
                //上传成功
            }
            ,error: function(){
                layer.msg("上传失败");
            }
        });
    });

    function doInport(fileName)
    {
        if($("#btnInport").hasClass("disabled"))return;
        $("#btnInport").addClass("disabled");
        jQuery.ajax({
            type: "POST",
            data: {"fileName":fileName, "schoolId": ${schoolId},"stageId":${stageId}},
            dataType: "text",
            url: "/manage/edu/schedule/doInportTemplate",
            success: function (result) {
                if(result == "1")
                {
                    layer.alert("导入学校作息时间成功", {icon: 6},function () {
                        // 获得frame索引
                        var index = parent.layer.getFrameIndex(window.name);
                        //关闭当前frame
                        parent.layer.close(index);
                        parent.initDataList();
                    });
                }
                else
                {
                    layer.msg("上传导入数据文件错误,请检查文件的正确性");
                    $("#btnInport").removeClass("disabled");
                }
            }
        });
    }

</script>
