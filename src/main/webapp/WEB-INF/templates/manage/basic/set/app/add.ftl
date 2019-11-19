<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>

<div class="main-content">
    <div class="main-content-inner">
        <div class="page-content">
            <div class="row">
                <div class="col-xs-12">
                    <form class="form-horizontal" id="validation-form" role="form">
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="name">应用名称:</label>
                            <div class="col-sm-9" style="float: left;">
                                <div class="clearfix" style="float:left;">
                                    <input type="text" name="name" value="" id="name" style="width: 200px;" class="col-xs-12 col-sm-6">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="link">应用URL:</label>
                            <div class="col-sm-9" style="float: left;">
                                <div class="clearfix" style="float:left;">
                                    <input type="text" name="link" value="" id="link" style="width: 300px;" class="col-xs-12 col-sm-6">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="code">应用标识:</label>
                            <div class="col-sm-9" style="float: left;">
                                <div class="clearfix" style="float:left;">
                                    <input type="text" name="code" value="" id="code" class="col-xs-12 col-sm-6">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="icon">应用图标:</label>
                            <div class="col-sm-9" style="float: left;">
                                <div class="clearfix" style="float:left;width: 100px;">
                                    <input type="text" name="icon" id="icon" value="" class="col-xs-12 col-sm-6">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right" style="float:left;width:100px">打开窗口方式:</label>
                            <div class="col-xs-12 col-sm-9" style="float:left;width: 200px;">
                                <div style="float: left;">
                                    <label class="line-height-1">
                                        <input name="linktarget" checked value="0" type="radio" class="ace">
                                        <span class="lbl">当前窗口</span>
                                    </label>
                                </div>
                                <div style="float:left;">
                                    <label class="line-height-1">
                                        <input name="linktarget" value="1" type="radio" class="ace">
                                        <span class="lbl">新窗口</span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right" style="float:left;width:100px;text-align: right">用户类型:</label>
                            <div class="col-xs-12 col-sm-9" style="float:left;width: 300px;">
                                <div style="float: left;">
                                    <label class="line-height-1">
                                        <input name="usetype" checked value="0" type="radio" class="ace">
                                        <span class="lbl">默认</span>
                                    </label>
                                </div>
                                <div style="float:left;">
                                    <label class="line-height-1">
                                        <input name="usetype" value="1" type="radio" class="ace">
                                        <span class="lbl">单位管理员</span>
                                    </label>
                                </div>
                                <div style="float:left;">
                                    <label class="line-height-1">
                                        <input name="usetype" value="2" type="radio" class="ace">
                                        <span class="lbl">普通用户</span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float: left;width:100px" for="status">状态:</label>
                            <div class="col-sm-9" style="float: left;">
                                <label>
                                    <input name="status" id="status" checked class="ace ace-switch ace-switch-3" type="checkbox">
                                    <span class="lbl"></span>
                                </label>
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
<script src="/assets/lib/js/jquery.validate.min.js"></script>

<script type="text/javascript">
    jQuery(function ($) {
        $('#validation-form').validate({
            errorElement: 'div',
            errorClass: 'help-block',
            focusInvalid: true,
            ignore: "",
            rules: {
                name: {
                    required: true
                },
                link: {
                    required: true
                },
                code: {
                    required: true
                },
                icon: {
                    required: true
                }
            },

            messages: {
                name: {
                    required: "不能为空"
                },
                link: {
                    required: "不能为空"
                },
                code: {
                    required: "不能为空"
                },
                icon: {
                    required: "不能为空"
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
            var linktarget = $('input:radio[name=linktarget]:checked').val();
            var usetype = $('input:radio[name=usetype]:checked').val();
            var status = $("#status")[0].checked ? 1:0;
            var obj = $(this);
            jQuery.ajax({
                type: "POST",
                data: {"name":$("#name").val(),"link":$("#link").val(),"linktarget":linktarget,"smallicon":$("#icon").val(),
                "status":status,"code":$("#code").val(),"usetype":usetype},
                dataType: "text",
                url: "/manage/basic/app/doAdd",
                success: function (result) {
                    if(result == 1) {
                        layer.msg("保存成功", {icon: 1, time: 1000}, function () {
                            parent.initAppList();
                            layer_close();
                        });
                    }
                    else if(result == 0)
                    {
                        layer.msg("应用标识不能为空",{icon:2});
                        obj.removeClass("disabled");
                    }
                    else if(result == -1)
                    {
                        layer.msg("应用标识已经存在，不能重复",{icon : 2});
                        obj.removeClass("disabled");
                    }
                },
                error:function () {
                    obj.removeClass("disabled");
                }
            });
        });
    });

</script>
