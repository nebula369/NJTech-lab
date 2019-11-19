<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>

<!-- 加载顶部bar -->
<@topBar title="${title}" titleUrl="/manage/eclassbrand/index" currentUser=user icon="${menuConfig.icon}"/>

<div class="main-container ace-save-state" id="main-container">

    <!-- 加载左侧菜单 -->
    <@leftMenu indexUrl="/manage/eclassbrand/index" menuList=menuConfig.menuList currentUrl="/manage/eclassbrand/examplan"/>

    <div class="main-content">
        <div class="main-content-inner">
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="/manage/eclassbrand/index">导航页</a>
                    </li>

                    <li>
                        <a href="#">网站模式</a>
                    </li>
                    <li class="active">考试计划</li>
                </ul>
            </div>
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12" style="padding:0px;">
                        <div>
                            <div class="row search-page">
                                <div class="col-xs-12">
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-2">
                                            <div id="tree" class="search-area well well-sm">
                                                <table  style="width: 100%;font-size: 8px">
                                                    <td style="width: 30%;vertical-align: top;">
                                                        <div>
                                                            <i class="ace-icon  fa fa-building"></i>
                                                            <ul class="spacetypeList" id="spaceTypeList" style="list-style: none;margin: 0;text-align: center;">
                                                                <#list spaceTypeList as item>
                                                                    <li  class="subNavItem  <#if item.pkid == spacetypeid >SelectedNavItem</#if> " id="${item.pkid}"  style="padding: 5px; cursor: pointer; "  onclick="initSpaceList(this)"  title="${item.name}">${item.name} </li>
                                                                </#list>
                                                            </ul>
                                                        </div>
                                                    </td>
                                                    <td style="width: 0.5%; background-color: #858585;vertical-align: top;"></td>
                                                    <td style="width: 65%;vertical-align: top;">
                                                        <div>
                                                            <ul class="spaceList" id="spaceList"  style="list-style: none;margin: 0;text-align: center;">
                                                                <#list spaceList as item>
                                                                    <li  onclick="initRoomList(this)" class="subNavItem    <#if item.pkid == examroomid >SelectedNavItem</#if> " id="${item.pkid}"  style="padding: 5px; cursor: pointer; "  title="${item.name}">${item.name} </li>
                                                                </#list>
                                                            </ul>
                                                            <input type="hidden" value="${examroomid}" id="examroomid">
                                                        </div>
                                                    </td>
                                                </table>
                                            </div>
                                        </div>
                                        <div class="col-xs-12 col-sm-10 no-padding-left">
                                            <div style="margin-bottom: 5px;">
                                                <button id="btnAdd" style="float:left;margin-top: 3px; margin-left: 10px;" class="btn btn-success btn-mini radius-4" value=""><i class="ace-icon fa fa-plus"></i>新增考试计划</button>
                                                <button id="btnExamImport" style="float:left;margin-top: 3px; margin-left: 10px;" class="btn btn-info btn-mini radius-4" value=""><i class="ace-icon fa fa-plus"></i>考试导入</button>
                                                <button id="btnExamineeImport" style="float:left;margin-top: 3px; margin-left: 10px;" class="btn btn-info btn-mini radius-4" value=""><i class="ace-icon fa fa-plus"></i>考生导入</button>
                                                <span>
                                                     <label style="padding-top: 5px;margin-left: 10px;" class="no-padding-right" for="year">科目名称:</label>
                                                    <input type="text" id="searchKey" style="width: 250px;" placeholder="请输入科目名称" class="input-sm" />
                                                    <button style="margin-bottom: 3px; margin-left: 5px;" type="button" id="searchButton" class="btn btn-purple btn-xs">
                                                    <span class="ace-icon fa fa-search icon-on-right bigger-110"></span> 搜索
                                                    </button>
                                                </span>
                                                <button id="btnDel" style="float:left; margin-top: 5px; margin-left: 5px" class="btn btn-danger btn-mini radius-4"><i class="ace-icon fa fa-trash-o"></i>删除考试</button>
                                            </div>
                                            <table id="grid-table"></table>
                                            <div id="grid-pager"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div><!-- /.col -->
                </div><!-- /.row -->

            </div><!-- /.page-content -->
        </div>
    </div>

    <!-- 加载底部版权 -->
    <#include "/controls/footer.ftl"/>
<style>
    .SelectedNavItem{
        color: #1E9FFF !important;
        background-color: #EEEEEE;
    }
</style>
</div>

<#include "/controls/appbottom.ftl"/>
<script src="/assets/lib/js/jquery.jqGrid.min.js"></script>
<script src="/assets/lib/js/grid.locale-cn.js"></script>
<script src="/assets/lib/js/chosen.jquery.min.js"></script>
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
            url: "/manage/eclassbrand/examplan/getExamPlanListForPage",
            postData: {examRoomId: $("#examroomid").val(),searchKey:$("#searchKey").val()},
            datatype: "json", // 从服务器端返回的数据类型，默认xml。可选类型：xml，local，json，jsonnp，script，xmlstring，jsonstring，clientside
            mtype: "POST", // 提交方式，默认为GET
            height: "auto", // 高度，表格高度。可为数值、百分比或'auto'
            colNames: ['编号','考试日期','考试时间', '考试科目', '年级专业', '监考人员','人数','准考证起止号码','数据来源','操作'], // 列显示名称，是一个数组对象
            colModel: [
                // name 表示列显示的名称；
                // index 表示传到服务器端用来排序用的列名称；
                // width 为列宽度；
                // align 为对齐方式；
                // sortable 表示是否可以排序
                {name: 'pkid', index: 'pkid', align: 'left',sortable:false, resize:false, hidden:true},
                {name: 'examDate', index: 'examDate', align: 'left',sortable:false, resize:false},
                {name: 'examTime', index: 'examTime', align: 'left',sortable:false, resize:false},
                {name: 'subject', index: 'subject', align: 'left',sortable:false, resize:false},
                {name: 'grade', index: 'grade', align: 'left',sortable:false, resize:false},
                {name: 'examiner', index: 'examiner', align: 'left',sortable:false, resize:false},
                {name: 'usercount', index: 'usercount', align: 'left',sortable:false, resize:false},
                {name: 'ticket', index: 'ticket', align: 'left',sortable:false, resize:false},
                {name: 'isimport', index: 'isimport', align: 'left',fixed:false, width:100,sortable:false, resize:false,
                    formatter:function(value, grid, rows, state){
                        if(rows.isimport==1)
                        {return "<span>基础平台</span>"; }
                        else{  return "<span>第三方导入</span>";}
                    }
                },
                {
                    name: 'action', index: '', sortable: false, resize: false,
                    formatter: function (value, grid, rows, state) {
                        var html = "";
                        html = "<button class='btn btn-minier btn-success' onclick='modify("+rows.pkid+");'><i class=\"ace-icon fa fa-pencil\"></i></button>&nbsp;&nbsp;" +
                              "<button class='btn btn-minier btn-info' onclick='setUser("+rows.pkid+",this);'><i class=\"ace-icon fa fa-users\"></i></button>&nbsp;&nbsp;" +
                                "<button class='btn btn-minier btn-danger' onclick='del("+rows.pkid+");'><i class=\"ace-icon fa fa-trash-o\"></i></button>";
                        return html;
                    }
                }
            ],
            //rownumbers: true,// 显示左侧的序号
            altRows:true,// 设置为交替行表格,默认为false
            sortname:'starttime', // 排序列的名称，此参数会被传到后台
            sortorder:'desc', // 排序顺序，升序或者降序（ASC或DESC）
            viewrecords: true, // 是否在翻页导航栏显示记录总数
            rowNum: getRowNum(), // 每页显示记录数
            rowList: rowList, // 用于改变显示行数的下拉列表框的元素数组
            pager: $('#grid-pager'), // 定义翻页用的导航栏
            multiselect: true,
            //multikey: "ctrlKey",
            //multiboxonly: true,
            loadComplete : function() {
                var table = this;
                setTimeout(function(){
                    //styleCheckbox(table);
                    //updateActionIcons(table);
                    updatePagerIcons(table);
                    //enableTooltips(table);
                }, 0);
            },
        });
        $(window).triggerHandler('resize.jqGrid');

        jQuery("#grid-table").jqGrid('navGrid',"#grid-pager",
                {
                    edit: false,
                    editicon : 'ace-icon fa fa-pencil blue',
                    add: false,
                    addicon : 'ace-icon fa fa-plus-circle purple',
                    del: false,
                    delicon : 'ace-icon fa fa-trash-o red',
                    search: false,
                    searchicon : 'ace-icon fa fa-search orange',
                    refresh: true,
                    refreshicon : 'ace-icon fa fa-refresh green',
                    view: false,
                    viewicon : 'ace-icon fa fa-search-plus grey',
                }
        )

        //replace icons with FontAwesome icons like above
        function updatePagerIcons(table) {
            var replacement =
                    {
                        'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
                        'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
                        'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
                        'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
                    };
            $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
                var icon = $(this);
                var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
                if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
            })
        }

        $("#btnAdd").on("click", function () {
            var examroomid=$("#examroomid").val();
            if(examroomid=="0")
            {
                layer.msg("请选择考场！", {icon:2});
                return false;
            }
            layer_show("新建考试计划", "/manage/eclassbrand/examplan/add?examroomid="+ examroomid, 1000, 430);
            event.stopPropagation();
        });
        $("#searchButton").on("click", function () {
            initDataList();
        });
        $("#btnDel").on("click", function () {
            layer.confirm("确定删除选中的考试?", function (index) {
                layer.close(index);
                var ids=$('#grid-table').jqGrid('getGridParam','selarrrow');
                var examPlanIds = [];
                for(var i=0; i<ids.length; i++)
                {
                    var rowData = $("#grid-table").jqGrid('getRowData',ids[i]);
                    examPlanIds.push(rowData.pkid);
                }
                if(examPlanIds.length==0)
                {
                    layer.msg("请选择要删除的考试", {icon:2});
                    return;
                }
                jQuery.ajax({
                    type: "POST",
                    data: {"ids":examPlanIds.join(",")},
                    dataType: "text",
                    url: "/manage/eclassbrand/examplan/doDel",
                    success: function (result) {
                        if(result == 1) {
                            layer.msg("删除成功", {icon: 1, time: 1000}, function () {
                                reloadData();
                            });
                        }
                        else
                        {
                            layer.msg("删除失败",{icon:2});
                        }
                    },
                    error:function () {
                        layer.msg("删除失败",{icon:2});
                    }
                });
            })
        });
        $("#btnExamImport").on("click", function () {
            var examroomid=$("#examroomid").val();
            if(examroomid=="0")
            {
                layer.msg("请选择考场！", {icon:2});
                return false;
            }
            layer_show("导入考试计划", "/manage/eclassbrand/examplan/examimport?examroomid="+ examroomid, 520, 260);
            event.stopPropagation();
        });
        $("#btnExamineeImport").on("click", function () {
            var examroomid=$("#examroomid").val();
            if(examroomid=="0")
            {
                layer.msg("请选择考场！", {icon:2});
                return false;
            }
            var ids=$('#grid-table').jqGrid('getGridParam','selarrrow');
            var examPlanIds = [];
            for(var i=0; i<ids.length; i++)
            {
                var rowData = $("#grid-table").jqGrid('getRowData',ids[i]);
                examPlanIds.push(rowData.pkid);
            }
            if(examPlanIds.length==0)
            {
                layer.msg("请选择考试场次", {icon:2});
                return;
            }
            layer_show("导入考生", "/manage/eclassbrand/examplan/examineeimport?examroomid="+examroomid+"&ids="+ examPlanIds, 750, 500);
            event.stopPropagation();
        });
    });


    function reloadData()
    {
        $("#grid-table").trigger("reloadGrid");
    }
    function modify(id)
    {
        layer_show("编辑考试计划", "/manage/eclassbrand/examplan/edit?id=" + id, 1000, 430);
        event.stopPropagation();
    }
    function setUser(id) {

        layer_show("设置考生", "/manage/eclassbrand/examplan/relationuser?id="+id, 800, 700,setCurrentLayerObj);
        event.stopPropagation();
    }

    function initDataList()
    {
        var examRoomId = $("#examroomid").val();
        if(examRoomId==null||examRoomId=="")
        {
            return ;
        }
        var searchKey = $("#searchKey").val();
        var postJson = {examRoomId: examRoomId,searchKey:searchKey};
        //传入查询条件参数
        $("#grid-table").jqGrid("setGridParam",{postData:postJson, page:1}).trigger("reloadGrid");
    }

    function initSpaceList(obj) {
        $("#spaceTypeList li").removeClass();
        $("#spaceTypeList li").addClass("subNavItem");
        $(obj).removeClass().addClass("SelectedNavItem");
        typeId = $(obj).attr("id");
        initExamRoom(typeId);
    }

    function initExamRoom(typeId)
    {
        $('#spaceList').html("");
        $("#examroomid").val(0);
        jQuery.ajax({
            type: "POST",
            data: {"spaceTypeId":typeId},
            dataType: "json",
            url: "/manage/eclassbrand/examroom/getExamRoomListForSpaceId",
            success: function (result) {
                var html = '';
                for(var i=0;i<result.length;i++){
                    if(i==0)
                    {
                        html +=  " <li  class=\"SelectedNavItem\" id=\""+result[i].pkid+"\"  onclick=\"initRoomList(this)\" style=\"padding: 5px; cursor: pointer;\" title=\""+result[i].name+"\" >"+result[i].name+"</li>";
                        $("#examroomid").val(result[i].pkid);
                    }
                    else{
                        html +=  "  <li  class=\"subNavItem\" id=\""+result[i].pkid+"\"  onclick=\"initRoomList(this)\"  style=\"padding: 5px; cursor: pointer;\" title=\""+result[i].name+"\" >"+result[i].name+"</li>";
                    }
                }
                $('#spaceList').html(html);
                initDataList();
            },
            error:function () {
            }
        });
    }
    function initRoomList(obj) {
        $("#spaceList li").removeClass();
        $("#spaceList li").addClass("subNavItem");
        $(obj).removeClass().addClass("SelectedNavItem");
        $("#examroomid").val(obj.id);
        initDataList();
    }
    function del(id)
    {
        layer.confirm("确定删除考试计划？", function (index) {
            layer.close(index);
            jQuery.ajax({
                type: "POST",
                data: {"id":id},
                dataType: "text",
                url: "/manage/eclassbrand/examplan/doDel",
                success: function (result) {
                    if(result == 1) {
                        layer.msg("删除成功", {icon: 1, time: 1000}, function () {
                            reloadData();
                        });
                    }
                },
                error:function () {
                    layer.msg("删除失败",{icon:2});
                }
            });
        })
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
</script>
