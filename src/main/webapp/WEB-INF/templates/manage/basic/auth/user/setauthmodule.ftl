<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>
<link rel="stylesheet" type="text/css" href="/assets/scripts/dtree/css/ui.css" />

<div class="main-content">
    <div class="main-content-inner">
        <div class="page-content">
            <div class="row">
                <div class="col-xs-12">
                    <div id="tree" style="height: 500px;">

                    </div>
                    <button style="margin-left: 40px; margin-top: 5px" class="btn btn-success btn-sm" type="button">
                        <i class="ace-icon fa fa-check"></i>
                        确 定
                    </button>
                    &nbsp; &nbsp;
                    <button class="btn btn-sm btn-danger" style="margin-top: 5px" type="reset">
                        <i class="ace-icon fa fa-times"></i>
                        关闭
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<#include "/controls/appbottom.ftl"/>
<script type="text/javascript" src="/assets/scripts/dtree/core.js"></script>
<script type="text/javascript" src="/assets/scripts/dtree/ui.js"></script>
<script type="text/javascript" src="/assets/scripts/dtree/cookie.js"></script>
<script type="text/javascript" src="/assets/scripts/dtree/dynatree.js"></script>
<script type="text/javascript" src="/assets/scripts/dtree/dtree.js"></script>

<script type="text/javascript">

    jQuery(function ($) {
        var tree = $('#tree');
        tree.mac('dtree', {
            treeConfig: {
                title: "",
                rootVisible: false,
                clickFolderMode: 3,
                selectMode:3,
                checkbox:true,
                onSelect: function (flag, dtnode) { dtnode.expand(true); },
                activeVisible:false
                //autoCollapse:true
            },
            loader: {
                url: '/manage/basic/userAuth/getAuthModuleList?userId=${userId}&roleId=${roleId}&type=${type}',
                params: { id: '0',level:0 },
                autoLoad: true
            }
        });

        $(".btn-danger").on("click", function () {
            layer_close();
            event.stopPropagation();
        });

        $(".btn-success").on("click", function () {
            var treeSelectedNodes = $('#tree').dynatree("getTree").getSelectedNodes();
            var selectAppList = $.map(treeSelectedNodes, function (node) {
                if (node.data.code == -1) {
                    return node.data.key;
                }
            });
            var selectMenuList = $.map(treeSelectedNodes, function (node) {
                if (node.data.code == 1) {
                    return node.data.key;
                }
            });
            var selectItemList = $.map(treeSelectedNodes, function (node) {
                if (node.data.code == 2) {
                    return node.data.key;
                }
            });
            if("${type}"=="0") {
                jQuery.ajax({
                    type: "POST",
                    data: {
                        "userId":${userId},
                        "authModuleIds": selectAppList.join(","),
                        "authMenuIds": selectMenuList.join(","),
                        "authItemIds": selectItemList.join(",")
                    },
                    dataType: "json",
                    url: "/manage/basic/userAuth/saveUserAuth",
                    success: function (result) {
                        if (result == 1) {
                            parent.location.href = "/manage/basic/userAuth";
                        }
                        else if (result == 0) {
                            layer.msg("授权用户不存在", {icon: 1});
                        }
                    },
                    error: function () {

                    }
                });
            }
            else
            {
                jQuery.ajax({
                    type: "POST",
                    data: {
                        "roleId":${roleId},
                        "authModuleIds": selectAppList.join(","),
                        "authMenuIds": selectMenuList.join(","),
                        "authItemIds": selectItemList.join(",")
                    },
                    dataType: "json",
                    url: "/manage/basic/roleAuth/saveRoleAuth",
                    success: function (result) {
                        layer.msg("设置成功", {icon: 1, time: 1000}, function () {
                            layer_close();
                        });
                    },
                    error: function () {

                    }
                });
            }
            event.stopPropagation();
        });
    });

</script>
