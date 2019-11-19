<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>
<link rel="stylesheet" href="/assets/lib/css/bootstrap-datepicker3.min.css" />

<div class="main-content">
    <div class="main-content-inner">
        <div class="page-content">
            <div class="row">
                <div class="col-xs-12">
                    <form class="form-horizontal" id="validation-form" role="form">
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="classid">所属班级:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <select multiple=""  style="width: 300px;" class="chosen-select form-control tag-input-style" name="classid" id="classid" data-placeholder="选择班级">
                                        <#list classList as item>
                                            <option value="${item.pkid}">${item.name}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="subject">所属学科:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <select class="chosen-select form-control tag-input-style" name="subject" id="subject" style="width: 300px;" data-placeholder="--请选择学科--">
                                        <option value=""></option>
                                        <#list subjectList as item>
                                            <option value="${item.code}">${item.name}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="type">类型:</label>
                            <div class="col-xs-12 col-sm-9" style="float:left;width: 400px;margin-top: 5px;">
                                    <div style="float: left;margin-right: 5px;">
                                        <label class="line-height-1">
                                            <input name="type" id="type" checked  value="1" type="radio" class="ace">
                                            <span class="lbl">家庭作业</span>
                                        </label>
                                    </div>
                                  <div style="float: left;margin-right: 5px;">
                                    <label class="line-height-1">
                                        <input name="type" id="type" checked  value="2" type="radio" class="ace">
                                        <span class="lbl">课后作业</span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group" id="stage">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="content">作业内容:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <textarea  class="form-control" value="" id="content" name="content"   cols="40"   rows="4"   style="overflow: hidden; margin: 0px; width: 300px; height: 95px;resize: none;"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="form-group" id="stage">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="sortnum">排序号:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control" value="1" id="sortnum" name="sortnum" type="text">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-offset-3 col-md-9" style="margin-left: 90px;">
                            <button class="btn btn-success btn-sm" type="button">
                                <i class="ace-icon fa fa-check"></i>
                                确 定
                            </button>
                            &nbsp; &nbsp;
                            <button class="btn btn-sm" type="reset">
                                <i class="ace-icon fa fa-undo"></i>
                                重置
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<#include "/controls/appbottom.ftl"/>
<script src="/assets/lib/js/moment.min.js"></script>
<script src="/assets/lib/js/jquery.validate.min.js"></script>
<script src="/assets/lib/js/chosen.jquery.min.js"></script>
<script type="text/javascript">
    jQuery(function ($) {

        $('#validation-form').validate({
            errorElement: 'div',
            errorClass: 'help-block',
            focusInvalid: true,
            ignore: "",
            rules: {
                classid: {
                    required: true
                },
                subject: {
                    required: true
                },
                content: {
                    required: true
                },
                type: {
                    required: true
                },
                sortnum:{
                    required: true,
                    digits: true
                }
            },
            messages: {
                classid: {
                    required: "不能为空"
                },
                subject: {
                    required: "不能为空"
                },
                content: {
                    required: "不能为空"
                },type: {
                    required: "不能为空"
                },
                sortnum: {
                    required: "不能为空",
                    digits: "请输入正整数"
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
                initErrorPlacement(error, element);
            }
        });

        $("#validation-form .btn-success").on("click", function () {
            if(!$('#validation-form').valid())
            {
                event.preventDefault();
                return;
            }
            if($(this).hasClass("disabled")) return;
            $(this).addClass("disabled");
            var obj = $(this);
            var classid=$("#classid").val().join(",");
            var subject=$("#subject").val();
            jQuery.ajax({
                type: "POST",
                data: {"content":$("#content").val(),"type":$("#type").val(),"sortnum":$("#sortnum").val(),"subject":subject,"classid":classid},
                dataType: "text",
                url: "/manage/eclassbrand/task/doAdd",
                success: function (result) {
                    if(result == 1) {
                        layer.msg("保存成功", {icon: 1, time: 1000}, function () {
                            parent.reloadData();
                            layer_close();
                        });
                    }
                    else
                    {
                        layer.msg("保存错误",{icon:2});
                        obj.removeClass("disabled");
                    }
                },
                error:function () {
                    obj.removeClass("disabled");
                }
            });
        });

        $('.tag-input-style').chosen({
            search_contains:true,
            no_results_text:"没有匹配数据:"
        });
        $('#subject').chosen({
            allow_single_deselect:false,
            search_contains:true,
            no_results_text:"没有匹配数据:"
        });
    });

</script>
