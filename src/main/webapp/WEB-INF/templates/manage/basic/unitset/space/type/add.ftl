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
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="parentid">所属单位:</label>
                            <div class="col-sm-9" style="float: left;">
                                <div class="clearfix" style="float:left;">
                                    <select class="chosen-select form-control" name="unitId" id="unitId" style="width: 200px;" data-placeholder="--请选择所属单位--">
                                        <option value=""></option>
                                        <#list unitList as item>
                                            <option value="${item.pkid}">${item.name}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group" id="stage">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="stage">场地类型:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control" value="" style="width: 250px;" id="name" name="name" type="text">
                                </div>
                            </div>
                        </div>
                        <div class="form-group" id="stage">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="stage">排序号:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control" value="1" style="width: 100px;" id="sortnum" name="sortnum" type="text">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="link">场地说明:</label>
                            <div class="col-sm-9" style="float: left;">
                                <div class="clearfix" style="float:left;">
                                    <textarea class="form-control limited" style="width: 350px;height: 80px;" placeholder="最多输入50个字符" id="remark" maxlength="50"></textarea>
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
                unitId:{
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
                unitId:{
                    required: "请选择所属单位"
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
                data: {"name":$("#name").val(),"sortnum":$("#sortnum").val(),"remark":$("#remark").val(),"unitid":$("#unitId").val()},
                dataType: "text",
                url: "/manage/basic/space/doAdd",
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

        $('#unitId').chosen({
            allow_single_deselect:false,
            search_contains:true,
            no_results_text:"没有匹配数据:"
        });
    });

</script>
