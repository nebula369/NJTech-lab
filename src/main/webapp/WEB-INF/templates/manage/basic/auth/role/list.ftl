<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>

<!-- 加载顶部bar -->
<@topBar title="${title}" titleUrl="/manage/basic/index" currentUser=user icon="${menuConfig.icon}"/>

<div class="main-container ace-save-state" id="main-container">

    <!-- 加载左侧菜单 -->
    <@leftMenu indexUrl="/manage/basic/index" menuList=menuConfig.menuList currentUrl="/manage/basic/roleAuth"/>

    <div class="main-content">
        <div class="main-content-inner">
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="/manage/basic/index">导航页</a>
                    </li>

                    <li>
                        <a href="#">功能权限</a>
                    </li>
                    <li class="active">角色授权</li>
                </ul>
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12" style="padding:0px;">
                        <div style="margin-bottom: 5px;">
                            <button id="btnAdd" style="float:left; margin-top: 2px" class="btn btn-success btn-mini radius-4" value=""><i class="ace-icon fa fa-plus"></i>新建角色</button>
                            <label style="padding-top: 5px;" class="col-sm-1 no-padding-right" for="year">角色名称</label>
                            <input type="text" id="searchKey" style="width: 250px;" placeholder="请输入角色名称" class="input-sm" />
                            <button style="margin-bottom: 3px; margin-left: 5px;" type="button" id="searchButton" class="btn btn-purple btn-xs">
                                <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
                                搜索
                            </button>
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
            url: "/manage/basic/roleAuth/getRoleAuthListForPage",
            postData: {searchKey:$("#searchKey").val()},
            datatype: "json", // 从服务器端返回的数据类型，默认xml。可选类型：xml，local，json，jsonnp，script，xmlstring，jsonstring，clientside
            mtype: "POST", // 提交方式，默认为GET
            height: "auto", // 高度，表格高度。可为数值、百分比或'auto'
            colNames: ['编号','角色名称', '状态', '角色描述', '操作'], // 列显示名称，是一个数组对象
            colModel: [
                // name 表示列显示的名称；
                // index 表示传到服务器端用来排序用的列名称；
                // width 为列宽度；
                // align 为对齐方式；
                // sortable 表示是否可以排序
                {name: 'pkid', index: 'pkid', width: 70,fixed:true, align: 'left',sortable:true, resize:false},
                {name: 'name', index: 'name', align: 'left',sortable:true, resize:false},
                {name: 'status', index: 'status', align: 'center', fixed:true, width:100,sortable:true, resize:false,
                    formatter:function(value, grid, rows, state) {
                        if(rows.status==1)
                        {
                            return '<input data-id="'+rows.pkid+'" onclick="setStatus('+rows.pkid+', this);" name="switch-field-1" checked class="ace ace-switch ace-switch-3" type="checkbox">' +
                                    '<span class="lbl"></span>';
                        }
                        else {
                            return '<input data-id="'+rows.pkid+'" onclick="setStatus('+rows.pkid+', this);" name="switch-field-1" class="ace ace-switch ace-switch-3" type="checkbox">' +
                                    '<span class="lbl"></span>';
                        }
                    }
                },
                {name: 'remark', index: 'remark', align: 'left',sortable:false, resize:false},
                {
                    name: 'action', index: '', sortable: false, resize: false,
                    formatter: function (value, grid, rows, state) {
                        var html = "";
                        html = "<button class='btn btn-minier btn-success' onclick='modify("+rows.pkid+");'><i class=\"ace-icon fa fa-pencil\"></i>修改</button>&nbsp;&nbsp;" +
                                "<button class='btn btn-minier btn-info' onclick='setRoleUser("+rows.pkid+");'><i class=\"ace-icon fa fa-users\"></i>设置用户</button>&nbsp;&nbsp;" +
                                "<button class='btn btn-minier btn-Primary' onclick='setAuthModule("+rows.pkid+");'><i class=\"ace-icon fa fa-lock\"></i>设置权限</button>&nbsp;&nbsp;" +
                                "<button class='btn btn-minier btn-danger' onclick='del("+rows.pkid+",\""+rows.name+"\");'><i class=\"ace-icon fa fa-trash-o\"></i>删除</button>";
                        return html;
                    }
                }
            ],
            //rownumbers: true,// 显示左侧的序号
            altRows:true,// 设置为交替行表格,默认为false
            sortname:'pkid', // 排序列的名称，此参数会被传到后台
            sortorder:'asc', // 排序顺序，升序或者降序（ASC或DESC）
        });
        $(window).triggerHandler('resize.jqGrid');

        $("#btnAdd").on("click", function () {
            layer_show("新建角色", "/manage/basic/roleAuth/add", 750, 515,setCurrentLayerObj);
            event.stopPropagation();
        });
        $("#searchButton").on("click", function () {
            initDataList();
        });
    });

    function reloadData()
    {
        $("#grid-table").trigger("reloadGrid");
    }

    var currentLayObj;
    function setCurrentLayerObj(obj) {
        currentLayObj = obj;
    }

    function setSelectedUser(selectUsers)
    {
        var iframeWin = getIframeWin(currentLayObj);
        iframeWin.setSelectedUser(selectUsers);
    }

    function initDataList()
    {
        var searchKey = $("#searchKey").val();
        var postJson = {searchKey:searchKey};
        //传入查询条件参数
        $("#grid-table").jqGrid("setGridParam",{postData:postJson, page:1}).trigger("reloadGrid");
    }

    function modify(id)
    {
        layer_show("修改角色", "/manage/basic/roleAuth/edit?id="+ id, 600, 360);
        event.stopPropagation();
    }

    function setRoleUser(id)
    {
        layer_show("设置角色用户", "/manage/basic/roleAuth/setRoleUser?id="+id, 800, 600,setCurrentLayerObj);
        event.stopPropagation();
    }

    function setAuthModule(id)
    {
        layer_show("设置权限", "/manage/basic/userAuth/setAuthModule?userId=0&roleId="+id+"&type=1", 300, 600);
        event.stopPropagation();
    }

    function setStatus(id,obj)
    {
        var status = obj.checked?1:0;
        jQuery.ajax({
            type: "POST",
            data: {"id":id,"status":status},
            dataType: "text",
            url: "/manage/basic/roleAuth/doSetStatus",
            success: function (result) {
                layer.msg("设置成功", {icon: 1, time: 1000}, function () {
                    reloadData();
                });
            },
            error:function () {
                layer.msg("设置失败",{icon:2});
            }
        });
        event.stopPropagation();
    }

    function del(id, name)
    {
        layer.confirm("确定删除角色“"+name+"”？", function (index) {
            layer.close(index);
            jQuery.ajax({
                type: "POST",
                data: {"id":id},
                dataType: "text",
                url: "/manage/basic/roleAuth/doDel",
                success: function (result) {
                    layer.msg("删除成功", {icon: 1, time: 1000}, function () {
                        reloadData();
                    });
                },
                error:function () {
                    layer.msg("删除失败",{icon:2});
                }
            });
        })
    }

</script>
