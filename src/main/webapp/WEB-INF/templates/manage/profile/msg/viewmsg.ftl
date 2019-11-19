<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>
<link rel="stylesheet" type="text/css" href="/assets/scripts/dtree/css/ui.css" />

<!-- 加载顶部bar -->
<@topBar title="${title}" titleUrl="/manage/profile/index" currentUser=user icon="${menuConfig.icon}"/>

<div class="main-container ace-save-state" id="main-container">

    <!-- 加载左侧菜单 -->
    <@leftMenu indexUrl="/manage/profile/index" menuList=menuConfig.menuList currentUrl="/manage/profile/inbox"/>

    <div class="main-content">
        <div class="main-content-inner">
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="/manage/profile/index">导航页</a>
                    </li>

                    <li>
                        <a href="#">我的消息</a>
                    </li>
                    <li>
                        <a href="/manage/profile/inbox">收件箱</a>
                    </li>
                    <li class="active">消息内容查看</li>
                </ul>
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12" style="padding:0px;">
                        <form class="form-horizontal" id="validation-form" role="form">
                            <div class="form-group">
                                <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;width: 100px;" for="parentid">主题:</label>
                                <div class="col-sm-9" style="float: left;margin-top: 9px">
                                    <div class="clearfix" style="float:left;">
                                    ${inbox.title}
                                    </div>
                                </div>
                            </div>
                            <div class="hr hr-18 dotted"></div>
                            <div class="form-group">
                                <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;width: 100px;" for="loginName">内容:</label>
                                <div class="col-sm-9" style="float: left; margin-top: 9px;">
                                    <div class="clearfix" style="float:left;">
                                    ${inbox.content}
                                    </div>
                                </div>
                            </div>
                            <div class="hr hr-18 dotted"></div>
                            <div class="form-group">
                                <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;width: 100px;" for="name">发件人:</label>
                                <div class="col-sm-9" style="float: left; margin-top: 9px;">
                                    <div class="clearfix" style="float:left;">
                                    ${fromUserName}
                                    </div>
                                </div>
                            </div>
                            <div class="hr hr-18 dotted"></div>
                            <div class="form-group">
                                <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;width: 100px;" for="password">收件时间:</label>
                                <div class="col-sm-9" style="float: left; margin-top: 9px;">
                                    <div class="clearfix" style="float:left;">
                                    ${inbox.createtime?string("yyyy-MM-dd HH:mm:ss")}
                                    </div>
                                </div>
                            </div>
                            <div class="hr hr-18 dotted"></div>
                            <div class="form-group">
                                <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;width: 100px;" for="password">状态:</label>
                                <div class="col-sm-9" style="float: left; margin-top: 9px;">
                                    <div class="clearfix" style="float:left;">
                                        <#if inbox.status==0>未读<#else>已读（${inbox.readtime?string("yyyy-MM-dd HH:mm:ss")}）</#if>
                                    </div>
                                </div>
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
