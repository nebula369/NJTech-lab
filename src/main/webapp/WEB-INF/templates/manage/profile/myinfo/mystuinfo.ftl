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
                    <li class="active">我的信息</li>
                </ul>
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12" style="padding:0px;">
                        <form class="form-horizontal" id="validation-form" role="form">
                            <div class="form-group">
                                <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;width: 100px;" for="parentid">所属学校:</label>
                                <div class="col-sm-9" style="float: left;margin-top: 9px;">
                                    <div class="clearfix" style="float:left;">
                                        ${(unit.name)!""}
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;width: 100px;" for="loginName">帐号:</label>
                                <div class="col-sm-9" style="float: left; margin-top: 9px;">
                                    <div class="clearfix" style="float:left;">
                                        ${user.loginname}
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;width: 100px;" for="name">姓名:</label>
                                <div class="col-sm-9" style="float: left; margin-top: 9px;">
                                    <div class="clearfix" style="float:left;">
                                        ${user.name}
                                    </div>
                                </div>
                            </div>
                            <div class="form-group" id="stage">
                                <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;width: 100px;" for="sex">性别:</label>
                                <div class="col-sm-9" style="float: left; margin-top: 9px;">
                                    <div class="clearfix" style="float:left;">
                                        <#if user.sex==0>男<#else>女</#if>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;width: 100px;" for="dept">年级班级:</label>
                                <div class="col-sm-9" style="float: left; margin-top: 9px;">
                                    <div class="clearfix" style="float:left;">
                                        ${className!""}
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;width: 100px;" for="grade">学号:</label>
                                <div class="col-sm-9" style="float: left; margin-top: 9px;">
                                    <div class="clearfix" style="float:left;">
                                        ${student.stuid}
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;width: 100px;" for="subject">学籍号:</label>
                                <div class="col-sm-9" style="float: left; margin-top: 9px;">
                                    <div class="clearfix" style="float:left;">
                                        ${student.stunum}
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="mobile">手机号码:</label>
                                <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                    <div class="clearfix" style="float:left;">
                                        <input class="form-control" value="${user.mobile}" id="mobile" name="mobile" type="text">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="email">电子邮箱:</label>
                                <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                    <div class="clearfix" style="float:left;">
                                        <input class="form-control" value="${user.email}" id="email" name="email" type="text">
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

<script type="text/javascript">
    jQuery(function ($) {

        $("#validation-form .btn-success").on("click", function () {
            if($(this).hasClass("disabled")) return;
            $(this).addClass("disabled");
            var obj = $(this);
            jQuery.ajax({
                type: "POST",
                data: {"mobile":$("#mobile").val(),"email":$("#email").val()},
                dataType: "text",
                url: "/manage/profile/myInfo/doEdit",
                success: function (result) {
                    layer.msg("保存成功", {icon: 1, time: 1000}, function () {
                        obj.removeClass("disabled");
                    });
                },
                error:function () {
                    obj.removeClass("disabled");
                }
            });
        });
    });

</script>
