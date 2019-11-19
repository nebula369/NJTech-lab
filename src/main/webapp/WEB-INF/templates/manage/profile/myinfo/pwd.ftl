<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>

<!-- 加载顶部bar -->
<@topBar title="${title}" titleUrl="/manage/profile/index" currentUser=user icon="${menuConfig.icon}"/>

<div class="main-container ace-save-state" id="main-container">

    <!-- 加载左侧菜单 -->
    <@leftMenu indexUrl="/manage/profile/index" menuList=menuConfig.menuList currentUrl="/manage/profile/myInfo"/>

    <div class="main-content">
        <div class="main-content-inner">
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="/manage/profile/index">导航页</a>
                    </li>

                    <li>
                        <a href="#">个人信息</a>
                    </li>
                    <li class="active">修改密码</li>
                </ul>
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12" style="padding:0px;">
                        <form class="form-horizontal" id="validation-form" role="form">
                            <div class="form-group">
                                <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="oriPwd">旧密码:</label>
                                <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                    <div class="clearfix" style="float:left;">
                                        <input class="form-control" value="" id="oriPwd" name="oriPwd" type="password">
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
                                <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="password">确认新密码:</label>
                                <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                    <div class="clearfix" style="float:left;">
                                        <input class="form-control" value="" id="passwordA" name="passwordA" type="password">
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
                    </div><!-- /.col -->
                </div><!-- /.row -->

            </div><!-- /.page-content -->
        </div>
    </div>

    <!-- 加载底部版权 -->
    <#include "/controls/footer.ftl"/>

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
                oriPwd:{
                    required: true
                },
                password: {
                    required: true
                },
                passwordA: {
                    required: true
                }
            },

            messages: {
                oriPwd:{
                    required: "不能为空"
                },
                password: {
                    required: "不能为空"
                },
                passwordA: {
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

            var pwd = $("#password").val();
            var pwdA = $("#passwordA").val();
            if(pwd!=pwdA)
            {
                layer.msg("确认密码不一致", {icon:2});
                $(this).removeClass("disabled");
                return;
            }

            var obj = $(this);
            jQuery.ajax({
                type: "POST",
                data: {"oriPwd":$("#oriPwd").val(),"pwd":$("#password").val()},
                dataType: "text",
                url: "/manage/profile/pwd/doPwdEdit",
                success: function (result) {
                    if(result == 1) {
                        layer.msg("保存成功", {icon: 1, time: 1000}, function () {
                            obj.removeClass("disabled");
                        });
                    }
                    else{
                        layer.msg("旧密码错误", {icon: 2, time: 1000}, function () {
                            obj.removeClass("disabled");
                        });
                    }
                },
                error:function () {
                    obj.removeClass("disabled");
                }
            });
        });
    });

</script>
