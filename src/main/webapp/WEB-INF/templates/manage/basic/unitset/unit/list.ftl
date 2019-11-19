<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>

<!-- 加载顶部bar -->
<@topBar title="${title}" titleUrl="/manage/basic/index" currentUser=user icon="${menuConfig.icon}"/>

<div class="main-container ace-save-state" id="main-container">

    <!-- 加载左侧菜单 -->
    <@leftMenu indexUrl="/manage/basic/index" menuList=menuConfig.menuList currentUrl="/manage/basic/unit"/>

    <div class="main-content">
        <div class="main-content-inner">
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="/manage/basic/index">导航页</a>
                    </li>

                    <li>
                        <a href="#">基础设置</a>
                    </li>
                    <li class="active">单位信息</li>
                </ul>
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12" style="padding:0px;">
                        <div style="margin-bottom: 5px;">
                            <button id="btnAdd" class="btn btn-success btn-mini radius-4"><i class="ace-icon fa fa-plus"></i>新增单位</button>
                        </div>
                        <table id="grid-table"></table>
                        <div id="grid-pager"></div>
                    </div><!-- /.col -->
                </div><!-- /.row -->

            </div><!-- /.page-content -->
        </div>
    </div>

    <!-- 加载底部版权 -->
    <#include "/controls/footer.ftl"/>

</div>

<#include "/controls/appbottom.ftl"/>
<script src="/assets/lib/js/jquery.jqGrid.min.js"></script>
<script src="/assets/lib/js/grid.locale-cn.js"></script>
<script type="text/javascript">
    jQuery(function ($) {

        var parent_column = $("#grid-table").closest('[class*="col-"]');
        //当页面大小变化时，让表格宽度自适应
        $(window).on('resize.jqGrid', function () {
            $("#grid-table").jqGrid("setGridWidth", parent_column.width() );
        })

        //当左侧菜单收缩时，让表格宽度自适应
        $(document).on('settings.ace.jqGrid' , function(ev, event_name, collapsed) {
            if( event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed' ) {
                setTimeout(function() {
                    $("#grid-table").jqGrid('setGridWidth', parent_column.width() );
                }, 20);
            }
        })
        $("#grid-table").jqGrid({
            url: "/manage/basic/unit/getUnitList",
            datatype: "json", // 从服务器端返回的数据类型，默认xml。可选类型：xml，local，json，jsonnp，script，xmlstring，jsonstring，clientside
            mtype: "POST", // 提交方式，默认为GET
            height: "auto", // 高度，表格高度。可为数值、百分比或'auto'
            colNames: ['编号','单位名称', '单位类型', '所属学段','单位管理员', '操作'], // 列显示名称，是一个数组对象
            colModel: [
                // name 表示列显示的名称；
                // index 表示传到服务器端用来排序用的列名称；
                // width 为列宽度；
                // align 为对齐方式；
                // sortable 表示是否可以排序
                {name: 'pkid', index: 'pkid', width: 70,fixed:true, align: 'left',sortable:false, resize:false},
                {name: 'name', index: 'name', align: 'left',sortable:false, resize:false},
                {name: 'unitTypeStr', index: 'unitTypeStr', align: 'left',sortable:false, resize:false},
                {name: 'stageIdsStr', index: 'stageIdsStr', align: 'left',sortable:false, resize:false},
                {name: 'manageUserName', index: 'manageUserName', align: 'left',sortable:false, resize:false},
                {
                    name: 'action', index: '', sortable: false, resize: false,
                    formatter: function (value, grid, rows, state) {
                        var html = "";
                        if(rows.parentid == 0)
                        {
                            html = "<button class='btn btn-minier btn-success' disabled onclick='modify("+rows.pkid+");'><i class=\"ace-icon fa fa-pencil\"></i>编辑</button>&nbsp;&nbsp;" +
                                    "<button class='btn btn-minier btn-danger' disabled onclick='del("+rows.pkid+");'><i class=\"ace-icon fa fa-trash-o\"></i>删除</button>";
                        }
                        else{
                            html = "<button class='btn btn-minier btn-success' onclick='modify("+rows.pkid+");'><i class=\"ace-icon fa fa-pencil\"></i>编辑</button>&nbsp;&nbsp;" +
                                    "<button class='btn btn-minier btn-danger' onclick='del("+rows.pkid+",\""+rows.name+"\");'><i class=\"ace-icon fa fa-trash-o\"></i>删除</button>";
                        }
                        return html;
                    }
                }
            ],
            //rownumbers: true,// 显示左侧的序号
            altRows:true,// 设置为交替行表格,默认为false

        });
        $(window).triggerHandler('resize.jqGrid');

        $("#btnAdd").on("click", function () {
            layer_show("新建单位", "/manage/basic/unit/add", 600, 350,setCurrentLayerObj);
            event.stopPropagation();
        });
    });

    var currentLayObj;
    function setCurrentLayerObj(obj) {
        currentLayObj = obj;
    }

    function setSelectedUser(selectUsers)
    {
        var iframeWin = getIframeWin(currentLayObj);
        iframeWin.setSelectedUser(selectUsers);
    }

    function reloadData()
    {
        $("#grid-table").trigger("reloadGrid");
    }

    function modify(id)
    {
        layer_show("编辑单位", "/manage/basic/unit/edit?id=" + id, 600, 350, setCurrentLayerObj);
        event.stopPropagation();
    }

    function del(id, name)
    {
        layer.confirm("确定删除单位“"+name+"”？", function (index) {
            layer.close(index);
            jQuery.ajax({
                type: "POST",
                data: {"id":id},
                dataType: "text",
                url: "/manage/basic/unit/doDel",
                success: function (result) {
                    if(result == 1) {
                        layer.msg("删除成功", {icon: 1, time: 1000}, function () {
                            reloadData();
                        });
                    }
                    else if(result == 0)
                    {
                        layer.msg("该单位有下级单位关联，请先删除下级单位",{icon:2});
                    }
                },
                error:function () {
                    layer.msg("删除失败",{icon:2});
                }
            });
        })
    }
</script>
