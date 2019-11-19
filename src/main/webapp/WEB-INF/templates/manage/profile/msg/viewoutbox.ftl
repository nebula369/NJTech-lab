<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>
<link rel="stylesheet" href="/assets/lib/css/bootstrap-datepicker3.min.css" />

<div class="main-content">
    <div class="main-content-inner">
        <div class="page-content">
            <div class="row" style="height: 420px;">
                <div class="col-xs-12">
                    <form class="form-horizontal" id="validation-form" role="form">
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;width: 100px;" for="parentid">主题:</label>
                            <div class="col-sm-9" style="float: left;margin-top: 2px">
                                <div class="clearfix" style="float:left;">
                                    ${outbox.title}
                                </div>
                            </div>
                        </div>
                        <div class="hr hr-18 dotted"></div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;width: 100px;" for="loginName">内容:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 2px;">
                                <div class="clearfix" style="float:left;">
                                    ${outbox.content}
                                </div>
                            </div>
                        </div>
                        <div class="hr hr-18 dotted"></div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;width: 100px;" for="name">发送用户:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 2px;">
                                <div class="clearfix" style="float:left;">
                                    ${toUserNames}
                                </div>
                            </div>
                        </div>
                        <div class="hr hr-18 dotted"></div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;width: 100px;" for="password">发送时间:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 2px;">
                                <div class="clearfix" style="float:left;">
                                    ${outbox.createtime?string("yyyy-MM-dd HH:mm:ss")}
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<#include "/controls/appbottom.ftl"/>
<script type="text/javascript">


</script>
