<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>
<link rel="stylesheet" type="text/css" href="/assets/scripts/dtree/css/ui.css" />

<div class="main-content">
    <div class="main-content-inner">
        <div class="page-content">
            <div class="row">
                <div class="col-xs-12">
                    <div id="tree" style="height: 400px;">

                    </div>
                    <button class="btn btn-sm btn-danger" style="margin-top: 5px; margin-left: 90px" type="reset">
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
                checkbox:false,
                onSelect: function (flag, dtnode) { dtnode.expand(true); },
                activeVisible:false
                //autoCollapse:true
            },
            loader: {
                url: '/manage/basic/userAuth/getAuthModuleListForView?userId=${userId}',
                params: { id: '0',level:0 },
                autoLoad: true
            }
        });

        $(".btn-danger").on("click", function () {
            layer_close();
            event.stopPropagation();
        });
    });

</script>
