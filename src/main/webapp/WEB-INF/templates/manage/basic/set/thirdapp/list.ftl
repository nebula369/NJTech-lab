<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>

<!-- 加载顶部bar -->
<@topBar title="${title}" titleUrl="/manage/basic/index" currentUser=user icon="${menuConfig.icon}"/>

<div class="main-container ace-save-state" id="main-container">

    <!-- 加载左侧菜单 -->
    <@leftMenu indexUrl="/manage/basic/index" menuList=menuConfig.menuList currentUrl="/manage/basic/thirdApp"/>

    <div class="main-content">
        <div class="main-content-inner">
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="/manage/basic/index">导航页</a>
                    </li>

                    <li>
                        <a href="#">配置信息</a>
                    </li>
                    <li class="active">第三方应用配置</li>
                </ul>
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12" style="padding:0px;">
                        <div style="margin-bottom: 5px;">
                            <button id="btnAdd" style="float:left; margin-top: 2px" class="btn btn-success btn-mini radius-4"><i class="ace-icon fa fa-plus"></i>新增第三方应用</button>
                            <label style="padding-top: 3px;" class="col-sm-1 no-padding-right" for="txtUserName">应用名称：</label>
                            <input type="text" id="txtUserName" style="width: 300px;" placeholder="请输入应用名称" class="input-sm" />
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
            url: "/manage/basic/thirdApp/getThirdAppList",
            datatype: "json", // 从服务器端返回的数据类型，默认xml。可选类型：xml，local，json，jsonnp，script，xmlstring，jsonstring，clientside
            mtype: "POST", // 提交方式，默认为GET
            height: "auto", // 高度，表格高度。可为数值、百分比或'auto'
            colNames: ['应用名称', '应用标识', '应用URL','应用密钥', '状态' ,'操作'], // 列显示名称，是一个数组对象
            colModel: [
                // name 表示列显示的名称；
                // index 表示传到服务器端用来排序用的列名称；
                // width 为列宽度；
                // align 为对齐方式；
                // sortable 表示是否可以排序
                {name: 'name', index: 'name',  align: 'left',sortable:true, resize:false},
                {name: 'appkey', index: 'appkey', width: 100,fixed:true, align: 'left',sortable:true, resize:false},
                {name: 'appurl', index: 'appurl', align: 'left',sortable:false, resize:false},
                {name: 'appsecret', index: 'appsecret', align: 'left',sortable:false, resize:false},
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
                {
                    name: 'action', index: '', sortable: false,fixed:true, width:150, resize: false,
                    formatter: function (value, grid, rows, state) {
                        var html = "";
                        html = "<button class='btn btn-minier btn-success' title='编辑' onclick='modify("+rows.pkid+");'><i class=\"ace-icon fa fa-pencil\"></i>编辑</button>&nbsp;" +
                                "<button class='btn btn-minier btn-danger' title='删除' onclick='del("+rows.pkid+",\""+rows.name+"\");'><i class=\"ace-icon fa fa-trash-o\"></i>删除</button>&nbsp;";
                        return html;
                    }
                }
            ],
            //rownumbers: true,// 显示左侧的序号
            altRows:true,// 设置为交替行表格,默认为false
            sortname:'pkid', // 排序列的名称，此参数会被传到后台
            sortorder:'asc', // 排序顺序，升序或者降序（ASC或DESC）
            viewrecords: true, // 是否在翻页导航栏显示记录总数
        });
        $(window).triggerHandler('resize.jqGrid');

        $("#searchButton").click(function(){
            var searchName = $("#txtUserName").val();
            var postJson = {searchName:searchName};
            //传入查询条件参数
            $("#grid-table").jqGrid("setGridParam",{postData:postJson, page:1}).trigger("reloadGrid");
        });

        $("#btnAdd").on("click", function () {
            layer_show("新增第三方应用", "/manage/basic/thirdApp/add", 600, 400);
            event.stopPropagation();
        });
    });

    function modify(id)
    {
        layer_show("编辑第三方应用数据", "/manage/basic/thirdApp/edit?id=" + id, 600, 400);
        event.stopPropagation();
    }

    function reloadData()
    {
        $("#grid-table").trigger("reloadGrid");
    }

    function setStatus(id,obj)
    {
        var status = obj.checked?1:0;
        jQuery.ajax({
            type: "POST",
            data: {"id":id,"status":status},
            dataType: "text",
            url: "/manage/basic/thirdApp/doSetStatus",
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

    function initDataList()
    {
        var searchName = $("#txtUserName").val();
        var postJson = {searchName:searchName};
        //传入查询条件参数
        $("#grid-table").jqGrid("setGridParam",{postData:postJson, page:1}).trigger("reloadGrid");
    }

    function del(id, name)
    {
        layer.confirm("确定删除第三方应用“"+name+"”？", function (index) {
            layer.close(index);
            jQuery.ajax({
                type: "POST",
                data: {"id":id},
                dataType: "text",
                url: "/manage/basic/thirdApp/doDel",
                success: function (result) {
                    if(result == 1) {
                        layer.msg("删除成功", {icon: 1, time: 1000}, function () {
                            reloadData();
                        });
                    }
                    else if(result == 0)
                    {
                        layer.msg("要删除的第三方应用不存在",{icon:2});
                    }
                },
                error:function () {
                    layer.msg("删除失败",{icon:2});
                }
            });
        })
        event.stopPropagation();
    }
</script>
