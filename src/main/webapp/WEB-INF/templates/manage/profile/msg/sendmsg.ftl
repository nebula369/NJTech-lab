<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>

<!-- 加载顶部bar -->
<@topBar title="${title}" titleUrl="/manage/profile/index" currentUser=user icon="${menuConfig.icon}"/>

<div class="main-container ace-save-state" id="main-container">

    <!-- 加载左侧菜单 -->
    <@leftMenu indexUrl="/manage/profile/index" menuList=menuConfig.menuList currentUrl="/manage/profile/sendMsg"/>

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
                    <li class="active">发送消息</li>
                </ul>
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12" style="padding:0px;">
                        <form id="validation-form" class="form-horizontal message-form col-xs-12">
                            <div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label no-padding-right" for="form-field-recipient">收件人:</label>
                                    <div class="col-sm-6 col-xs-12">
                                        <div class="input-icon block col-xs-12 no-padding">
                                            <input maxlength="100" type="text" class="col-xs-12" style="cursor: pointer" readonly name="touser" id="touser" placeholder="点击选择收件人" />
                                            <i class="ace-icon fa fa-user"></i>
                                        </div>
                                    </div>
                                    <a href="#" onclick="clearToUser();return false;">清空</a>
                                </div>

                                <div class="hr hr-18 dotted"></div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label no-padding-right" for="form-field-subject">主题:</label>

                                    <div class="col-sm-6 col-xs-12">
                                        <div class="input-icon block col-xs-12 no-padding">
                                            <input maxlength="100" type="text" class="col-xs-12" name="subject" id="subject" placeholder="消息主题" />
                                            <i class="ace-icon fa fa-comment-o"></i>
                                        </div>
                                    </div>
                                </div>

                                <div class="hr hr-18 dotted"></div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label no-padding-right">
                                        <span class="inline space-24 hidden-480"></span>
                                        内容:
                                    </label>
                                    <div class="col-sm-9">
                                        <div class="wysiwyg-editor"></div>
                                    </div>
                                </div>
                                <div class="col-xs-12" style="text-align: center; margin-top: 20px; margin-bottom: 30px;">
                                    <button class="btn btn-success btn-sm" type="button">
                                        <i class="ace-icon fa fa-arrow-right icon-on-right"></i>
                                        发送
                                    </button>
                                    &nbsp; &nbsp;
                                    <button class="btn btn-sm" type="reset">
                                        <i class="ace-icon fa fa-undo"></i>
                                        重置
                                    </button>
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
<script src="/assets/lib/js/jquery.validate.min.js"></script>
<script src="/assets/lib/js/bootstrap-tag.min.js"></script>
<script src="/assets/lib/js/jquery.hotkeys.index.min.js"></script>
<script src="/assets/lib/js/bootstrap-wysiwyg.min.js"></script>

<script type="text/javascript">
    jQuery(function ($) {

        $('.message-form .wysiwyg-editor').ace_wysiwyg({
            toolbar:
                    [
                        'bold',
                        'italic',
                        'strikethrough',
                        'underline',
                        null,
                        'justifyleft',
                        'justifycenter',
                        'justifyright',
                        null,
                        'createLink',
                        'unlink',
                        null,
                        'undo',
                        'redo'
                    ]
        }).prev().addClass('wysiwyg-style1');

        $('.message-form .wysiwyg-editor').empty();
        $('.message-form').get(0).reset();

        $("#validation-form .btn-success").on("click", function () {

            if(!$('#validation-form').valid())
            {
                event.preventDefault();
                return;
            }

            if($(this).hasClass("disabled")) return;
            $(this).addClass("disabled");
            var subject = $("#subject").val();
            var content = $('.message-form .wysiwyg-editor').html();
            if(selectUserIds.length == 0)
            {
                layer.msg("请选择收件人", {icon:2});
                $(this).removeClass("disabled");
                return;
            }
            if($.trim(subject) == "")
            {
                layer.msg("请填写消息主题", {icon:2});
                $(this).removeClass("disabled");
                return;
            }
            if($.trim(content) == "")
            {
                layer.msg("请填写消息内容", {icon:2});
                $(this).removeClass("disabled");
                return;
            }
            var obj = $(this);
            jQuery.ajax({
                type: "POST",
                data: {"toUserIds":selectUserIds.join(","),"subject":subject,"content":content},
                dataType: "text",
                url: "/manage/profile/doSendMsg",
                success: function (result) {
                    layer.msg("发送成功", {icon: 1, time: 1000}, function () {
                        location.href = "/manage/profile/outbox";
                    });
                },
                error:function () {
                    obj.removeClass("disabled");
                }
            });
        });

        $("#touser").on("click", function () {
            layer_show("选择用户", "/manage/basic/common/selectUser?type=0", 900, 600);
            event.stopPropagation();
        })

    });

    var selectUserIds = [];
    var selectUserNames = [];
    function setSelectedUser(selectUsers)
    {
        var userList = selectUsers.split(",");
        for(var i=0;i<userList.length; i++)
        {
            var users = userList[i].split("@");
            if(selectUserIds.indexOf(users[0])==-1) {
                selectUserIds.push(users[0]);
                selectUserNames.push(users[1]);
            }
        }
        initSelectUserNames();
    }

    function initSelectUserNames() {
        $("#touser").val(selectUserNames.join("，"));
    }

    function clearToUser() {
        layer.confirm("确定清空收件人？", function (index) {
            layer.close(index);
            selectUserIds = [];
            selectUserNames = [];
            initSelectUserNames();
        });
    }

</script>
