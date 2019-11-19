<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>

<div class="main-content">
    <div class="main-content-inner">
        <div class="page-content" style="height: 200px">
            <div class="row">
                <div class="col-xs-12">
                    <form class="form-horizontal" id="validation-form" role="form">
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="parentid">所属学校:</label>
                            <div class="col-sm-9" style="float: left;">
                                <div class="clearfix" style="float:left;">
                                    <select class="chosen-select form-control" name="unitId" id="unitId" data-placeholder="--请选择所属学校--">
                                        <option value=""></option>
                                                    <#list unitList as item>
                                                        <option value="${item.pkid}">${item.name}</option>
                                                    </#list>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 0px;width: 100px;" for="name">选择文件:</label>
                            <div class="col-sm-9" style="float: left;">
                                <input type="file" name="test1" id="test1">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="name"></label>
                            <div class="col-sm-9" style="float: left;">
                               <a href="/assets/static/Template_Student.xls">学生模板下载</a>
                            </div>
                        </div>
                        <div class="col-md-offset-3 col-md-9" style="margin-left: 90px;">
                            <button id="btnDoInport" type="button" style="display: none;"></button>
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
<script src="/assets/lib/js/chosen.jquery.min.js"></script>
<script type="text/javascript">

    jQuery(function ($) {
        $('#unitId').chosen({
            allow_single_deselect:false,
            search_contains:true,
            no_results_text:"没有匹配数据:"
        });
    });

    $('#validation-form').validate({
        errorElement: 'div',
        errorClass: 'help-block',
        focusInvalid: true,
        ignore: "",
        rules: {
            test1: {
                required: true
            },
            unitId:{
                required: true
            }
        },

        messages: {
            test1: {
                required: "请选择作息数据Excel文件"
            },
            unitId:{
                required:"请选择所属学校"
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

    $("#btnInport").on("click", function (){
        if(!$('#validation-form').valid())
        {
            event.preventDefault();
            return;
        }
        var schoolName = $("#unitId").find("option:selected").text();
        if(schoolName.indexOf("[学]")<0)
        {
            layer.msg("学生只能导入到学校",{icon:2});
            return;
        }
        if($("#btnInport").hasClass("disabled"))return;
        $("#btnDoInport")[0].click();
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
            ,bindAction:"#btnDoInport"
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
            data: {"fileName":fileName, "unitId": $("#unitId").val()},
            dataType: "text",
            url: "/manage/basic/student/doInport",
            success: function (result) {
                if(result == "1")
                {
                    layer.alert("导入学生数据成功", {icon: 6},function () {
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
