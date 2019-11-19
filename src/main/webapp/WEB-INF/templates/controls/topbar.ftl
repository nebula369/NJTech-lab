<#macro topBar title titleUrl currentUser icon="fa-leaf">
    <style>
        .layui-layer{border-radius:10px}
        .layui-layer-iframe iframe{border-radius:10px;}
    </style>
<script type="text/javascript">
    function logout()
    {
        layer.confirm('您确认要退出吗？',function(index){
            layer.close(index);
            location.href = "/logout";
        });

    }
    function platformmessage() {
        layer.open({
            type: 2,
            area: ['700px','500px'],
            fix: false, //不固定
            maxmin: false,
            shadeClose: true,
            shade:0.4,
            title: false,
            content: '/manage/platform',
            success: function (layero, index1) {

            }
        });
    }
</script>
    <div id="navbar" class="navbar navbar-default ace-save-state">
        <div class="navbar-container ace-save-state" id="navbar-container">
            <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
                <span class="sr-only">Toggle sidebar</span>

                <span class="icon-bar"></span>

                <span class="icon-bar"></span>

                <span class="icon-bar"></span>
            </button>
            <div class="navbar-header pull-left">
                <a href="${titleUrl}" class="navbar-brand">
                    <small>
                        <i class="fa ${icon} bigger-120"></i>
                        ${title}
                    </small>
                </a>
            </div>
            <div class="navbar-buttons navbar-header pull-right" role="navigation">
                <ul class="nav ace-nav">
                    <li class="dropdown-modal" title="平台信息">
                        <a onclick="platformmessage();">
                            <i class="fa fa-question-circle bigger-200" style="margin-top: 10px"></i>
                        </a>
                    </li>
                    <li class="purple dropdown-modal" title="回到个人桌面">
                        <a href="/manage/home">
                            <i class="fa fa-home bigger-200" style="margin-top: 10px"></i>
                        </a>
                    </li>
                    <li class="green dropdown-modal">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <i class="ace-icon fa fa-envelope icon-animated-vertical"></i>
                            <span class="badge badge-success" id="spanUnReadMsgCount_0">0</span>
                        </a>

                        <ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
                            <li class="dropdown-header">
                                <i class="ace-icon fa fa-envelope-o"></i>
                                <span id="spanUnReadMsgCount_1">0</span>条未读消息
                            </li>

                            <li class="dropdown-content">
                                <ul class="dropdown-menu dropdown-navbar" id="ulUnReadMsgList">

                                </ul>
                            </li>

                            <li class="dropdown-footer">
                                <a href="/manage/profile/inbox">
                                    查看所有消息
                                    <i class="ace-icon fa fa-arrow-right"></i>
                                </a>
                            </li>
                        </ul>
                    </li>

                    <li class="light-blue dropdown-modal" title="个人邮件">
                        <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                            <img class="nav-user-photo" src="/assets/lib/images/avatars/avatar2.png" />
                            <span class="user-info">
                                <small>欢迎，</small>
                                ${currentUser.name}
                            </span>

                            <i class="ace-icon fa fa-caret-down"></i>
                        </a>

                        <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                            <li style="display: none;">
                                <a href="#">
                                    <i class="ace-icon fa fa-cog"></i>
                                    设置
                                </a>
                            </li>

                            <li>
                                <a href="/manage/profile/index">
                                    <i class="ace-icon fa fa-user"></i>
                                    个人信息
                                </a>
                            </li>

                            <li class="divider"></li>

                            <li>
                                <a href="#" onclick="logout();return false;">
                                    <i class="ace-icon fa fa-power-off"></i>
                                    退出
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </div>

<script>
    isNeedGetCurrentMsgList = true;
</script>

</#macro>