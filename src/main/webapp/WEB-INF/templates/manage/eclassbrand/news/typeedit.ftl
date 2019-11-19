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
                                    <select class="chosen-select form-control" disabled="disabled" name="classid" id="classid" style="width: 300px;" data-placeholder="--请选择班级--">
                                        <option value=""></option>
                                        <#list classList as item>
                                            <option value="${item.pkid}" <#if item.pkid == newstype.classid>selected</#if>>${item.name}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="type">是否全校可见:</label>
                            <div class="col-xs-12 col-sm-9" style="float:left;width: 300px;margin-top: 5px;">
                                <div style="float: left;margin-right: 5px;">
                                    <label class="line-height-1">
                                        <input name="type" id="type" <#if newstype.isschool==0>checked</#if>  value="0" type="radio" class="ace">
                                        <span class="lbl" onclick="$('#txtType').val('0');">否</span>
                                    </label>
                                </div>
                                <div style="float: left;margin-right: 5px;">
                                    <label class="line-height-1">
                                        <input name="type" id="type" <#if newstype.isschool==1>checked</#if> value="1" type="radio" class="ace">
                                        <span class="lbl" onclick="$('#txtType').val('1');">是</span>
                                    </label>
                                </div>
                                <input type="text" style="display: none;" id="txtType" value="${newstype.isschool}">
                            </div>
                        </div>
                        <div class="form-group" id="stage">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="name">名称:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control" style="width: 300px;"  id="name" name="name" value="${newstype.name}" type="text">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-offset-3 col-md-9" style="text-align: center">
                            <button class="btn btn-success btn-sm" type="button">
                                <i class="ace-icon fa fa-check"></i>
                                确 定
                            </button>
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
                name: {
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
                name: {
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
                //在easybasic.js中定义
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
             jQuery.ajax({
                type: "POST",
                data: {"pkid":${newstype.pkid},"classid":$("#classid").val(),"isSchool":$("#txtType").val(),"name":$("#name").val()},
                dataType: "text",
                url: "/manage/eclassbrand/news/doTypeEdit",
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

        $('#classid').chosen({
            allow_single_deselect:false,
            search_contains:true,
            no_results_text:"没有匹配数据:"
        });
    });
</script>
