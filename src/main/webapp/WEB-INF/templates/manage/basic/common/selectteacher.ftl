<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<link rel="stylesheet" type="text/css" href="/assets/scripts/dtree/css/ui.css" />

<div class="main-container ace-save-state" id="main-container">

    <div class="main-content">
        <div class="main-content-inner">
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <span style="float:left;margin-left: 20px">选择对象： </span><select class="chosen-select form-control" onchange="changeSelect();" name="type" id="type" style="width: 100px;float:left;margin-top: 3px;">
                <option value="0" selected>教师</option>
                <option value="1">学生</option>
            </select>
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12" style="padding:0px;">
                        <div>
                            <div class="row search-page">
                                <div class="col-xs-12">
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-3 no-padding">
                                            <div id="tree" class="search-area well well-sm" style="height: 460px; overflow-y: auto">

                                            </div>
                                        </div>
                                        <div class="col-xs-12 col-sm-9 no-padding">
                                            <div style="margin-bottom: 5px;">
                                                <button id="btnAdd" style="float:left; margin-top: 5px" class="btn btn-success btn-mini radius-4"><i class="ace-icon fa fa-plus"></i>确定选择</button>
                                                <label style="padding-top: 5px;" class="col-sm-1 no-padding-right" for="searchKey">搜索：</label>
                                                <input type="text" id="searchKey" style="width: 250px;" placeholder="请输入姓名、帐号" class="input-sm" />
                                                <button style="margin-bottom: 3px; margin-left: 5px;" type="button" id="searchButton" class="btn btn-purple btn-xs">
                                                    <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
                                                    搜索
                                                </button>
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

</div>

<#include "/controls/appbottom.ftl"/>
<script type="text/javascript" src="/assets/scripts/dtree/core.js"></script>
<script type="text/javascript" src="/assets/scripts/dtree/ui.js"></script>
<script type="text/javascript" src="/assets/scripts/dtree/cookie.js"></script>
<script type="text/javascript" src="/assets/scripts/dtree/dynatree.js"></script>
<script type="text/javascript" src="/assets/scripts/dtree/dtree.js"></script>

<script src="/assets/lib/js/jquery.jqGrid.min.js"></script>
<script src="/assets/lib/js/grid.locale-cn.js"></script>

<script type="text/javascript">
    jQuery(function ($) {
        var tree = $('#tree');
        tree.mac('dtree', {
            treeConfig: {
                title: '${topUnitName}',
                rootVisible: true,
                clickFolderMode: 1
            },
            onActivate: function (node) {
                unitId = null;
                deptId = null;
                if(node.data.code == 0)
                {
                    unitId = node.data.key;
                }
                else if(node.data.code == 1)
                {
                    deptId = node.data.key;
                }
                initDataList();
            },
            loader: {
                url: '/manage/basic/teacher/getUnitDeptList',
                params: { id: '0',level:0 },
                autoLoad: true
            }
        });
    });

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
            url: "/manage/basic/teacher/getTeacherListForPage",
            datatype: "json", // 从服务器端返回的数据类型，默认xml。可选类型：xml，local，json，jsonnp，script，xmlstring，jsonstring，clientside
            mtype: "POST", // 提交方式，默认为GET
            height: "auto", // 高度，表格高度。可为数值、百分比或'auto'
            colNames: ['编号','用户姓名','帐号', '性别'], // 列显示名称，是一个数组对象
            colModel: [
                // name 表示列显示的名称；
                // index 表示传到服务器端用来排序用的列名称；
                // width 为列宽度；
                // align 为对齐方式；
                // sortable 表示是否可以排序
                {name: 'pkid', index: 'pkid', align: 'left',sortable:true, resize:false, hidden:true},
                {name: 'name', index: 'name', align: 'left',sortable:true, resize:false},
                {name: 'loginname', index: 'loginname', align: 'left',sortable:true, resize:false},
                {name: 'sex', index: 'sex', align: 'center',fixed:true, width:60,sortable:true, resize:false,
                    formatter:function(value, grid, rows, state){
                        if(rows.sex==0)
                        {
                            return "<i class=\"ace-icon fa fa-male bigger-150 orange\"></i>";
                        }
                        else
                        {
                            return "<i class=\"ace-icon fa fa-female bigger-150 orange\"></i>";
                        }
                    }
                }
            ],
            //rownumbers: true,// 显示左侧的序号
            altRows:true,// 设置为交替行表格,默认为false
            sortname:'pkid', // 排序列的名称，此参数会被传到后台
            sortorder:'desc', // 排序顺序，升序或者降序（ASC或DESC）
            viewrecords: true, // 是否在翻页导航栏显示记录总数
            rowNum: getRowNum(), // 每页显示记录数
            rowList: rowList, // 用于改变显示行数的下拉列表框的元素数组
            pager: $('#grid-pager'), // 定义翻页用的导航栏
            multiselect: true,
            //multikey: "ctrlKey",
            multiboxonly: <#if isSingle==1>true<#else>false</#if>,
            loadComplete : function() {
                var table = this;
                setTimeout(function(){
                    //styleCheckbox(table);
                    //updateActionIcons(table);
                    updatePagerIcons(table);
                    //enableTooltips(table);
                }, 0);
            }
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
            var ids=$('#grid-table').jqGrid('getGridParam','selarrrow');

            var teacherIds = [];
            for(var i=0; i<ids.length; i++)
            {
                var rowData = $("#grid-table").jqGrid('getRowData',ids[i]);
                teacherIds.push(rowData.pkid+"@"+rowData.name);
            }
            if(teacherIds.length==0)
            {
                layer.msg("请选择教师数据", {icon:2});
                return;
            }
            parent.setSelectedUser(teacherIds.join(","));
            layer_close();
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

    var unitId = null;
    var deptId = null;
    function initDataList()
    {
        var searchKey = $("#searchKey").val();
        var postJson = {unitId: unitId, deptId:deptId,searchKey:searchKey};
        //传入查询条件参数
        $("#grid-table").jqGrid("setGridParam",{postData:postJson, page:1}).trigger("reloadGrid");
    }

    function changeSelect()
    {
        var v = $("#type").val();
        if(v == "1")
        {
            location.href = "/manage/basic/common/selectUser?type=1&isSingle=${isSingle}";
        }
    }
</script>
