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
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="loginName">帐号:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control" value="${user.loginname}" disabled id="loginName" name="loginName" type="text">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="name">姓名:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control" value="${user.name}" disabled id="name" name="name" type="text">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="password">新密码:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control" value="" id="password" name="password" type="password">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="password">新密码确认:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control" value="" id="passworda" name="passworda" type="password">
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
                password: {
                    required: true
                },
                passworda:{
                    required: true
                }
            },

            messages: {
                password: {
                    required: "不能为空"
                },
                passworda: {
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
            var pwd = $("#password").val();
            var pwda = $("#passworda").val();
            if(pwd != pwda)
            {
                layer.msg("密码确认不对，请重新输入", {icon:2});
                return;
            }
            if($(this).hasClass("disabled")) return;
            $(this).addClass("disabled");
            var obj = $(this);
            jQuery.ajax({
                type: "POST",
                data: {"id":${user.pkid},"pwd":""+$("#password").val()+""},
                dataType: "text",
                url: "/manage/basic/teacher/doEditPwd",
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

        $('.tag-input-style').chosen({
            search_contains:true,
            no_results_text:"没有匹配数据:"
        });

        $("#unitId").on("change", function () {
            initDeptList();
            initGradeList();
        });

        function initDeptList()
        {
            var unitId = $("#unitId").val();
            if(unitId=="")unitId=0;
            jQuery.ajax({
                type: "POST",
                data: {"unitId":unitId},
                dataType: "json",
                url: "/manage/basic/teacher/getDeptList",
                success: function (result) {
                    var html = '';
                    for(var i=0;i<result.length;i++){
                        html +='<option value="'+result[i].pkid+'">'+result[i].name+'</option>';
                    }
                    $('#dept').html(html);
                    $("#dept").trigger("chosen:updated");
                },
                error:function () {

                }
            });
        }
        function initGradeList()
        {
            var unitId = $("#unitId").val();
            if(unitId=="")unitId=0;
            jQuery.ajax({
                type: "POST",
                data: {"unitId":unitId},
                dataType: "json",
                url: "/manage/basic/teacher/getGradeList",
                success: function (result) {
                    var html = '';
                    for(var i=0;i<result.length;i++){
                        html +='<option value="'+result[i].code+'">'+result[i].name+'</option>';
                    }
                    $('#grade').html(html);
                    $("#grade").trigger("chosen:updated");
                },
                error:function () {

                }
            });
        }
    });

</script>
