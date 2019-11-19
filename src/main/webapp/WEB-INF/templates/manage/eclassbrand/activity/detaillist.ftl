<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>

<div class="main-container ace-save-state" id="main-container">

    <div class="main-content">
        <div class="main-content-inner">
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12" style="padding:0px;">
                        <div>
                            <div class="row search-page">
                                <div class="col-xs-12">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <div style="margin-bottom: 5px;">
                                                <button id="btnAdd" style="float:left; margin-top: 2px" class="btn btn-success btn-mini radius-4"><i class="ace-icon fa fa-plus"></i>添加</button>
                                                <button id="btnDel" style="float:left; margin-top: 2px; margin-left: 5px" class="btn btn-danger btn-mini radius-4"><i class="ace-icon fa fa-trash-o"></i>移除</button>
                                              <#--  <label style="padding-top: 5px;" class="col-sm-1 no-padding-right" for="searchKey"></label>-->
                                                <input type="text" id="searchKey" style="width: 250px; margin-left: 10px" placeholder="请输入活动名称" class="input-sm" />
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
            url: "/manage/eclassbrand/activity/getActivityDetailListForPage",
            postData: {activityid: ${activityid},searchKey:$("#searchKey").val()},
            datatype: "json", // 从服务器端返回的数据类型，默认xml。可选类型：xml，local，json，jsonnp，script，xmlstring，jsonstring，clientside
            mtype: "POST", // 提交方式，默认为GET
            height: "auto", // 高度，表格高度。可为数值、百分比或'auto'
            colNames: ['编号','图片','名称','创建时间', '操作'], // 列显示名称，是一个数组对象
            colModel: [
                // name 表示列显示的名称；
                // index 表示传到服务器端用来排序用的列名称；
                // width 为列宽度；
                // align 为对齐方式；
                // sortable 表示是否可以排序
                {name: 'pkid', index: 'pkid', align: 'left',sortable:true,width:20, resize:false, hidden:true},
                {name: 'photo', index: 'photo', align: 'left', sortable:false,width:100, resize:false,
                    formatter:function(value, grid, rows, state){
                        return "<span>  <img src=\"/"+rows.photo+"\"  id=\"headimg\" alt=\"图片\" height=\"60px\"   onerror=\"javascript:this.src='/assets/lib/images/default/defaultpicture.png';\"></span>";
                    }
                },
                {name: 'describe', index: 'describe', align: 'left',sortable:false, resize:false},
                {name: 'createtime', index: 'createtime', align: 'left',width:100,sortable:false, resize:false},
                {
                    name: 'action', index: '', sortable: false,fixed:true, width:100, resize: false,
                    formatter: function (value, grid, rows, state) {
                        var html =
                            "<button class='btn btn-minier btn-success'  title='编辑' onclick='modify("+rows.pkid+");'><i class=\"ace-icon fa fa-pencil\"></i></button>&nbsp;&nbsp;" +
                            "<button class='btn btn-minier btn-danger' title='删除' onclick='del("+rows.pkid+",\""+rows.describe+"\");'><i class=\"ace-icon fa fa-trash-o\"></i></button>";
                        return html;
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
            layer_show("添加活动详情", "/manage/eclassbrand/activity/detailadd?activityid="+${activityid}, 450, 290);
            event.stopPropagation();
        });

        $("#searchButton").on("click", function () {
            initDataList();
        });

        $("#btnDel").on("click", function () {
            layer.confirm("确定删除选中的活动详情?", function (index) {
                layer.close(index);
                var ids=$('#grid-table').jqGrid('getGridParam','selarrrow');
                var Ids = [];
                for(var i=0; i<ids.length; i++)
                {
                    var rowData = $("#grid-table").jqGrid('getRowData',ids[i]);
                    Ids.push(rowData.pkid);
                }
                if(Ids.length==0)
                {
                    layer.msg("请选择要删除的活动详情", {icon:2});
                    return;
                }
                jQuery.ajax({
                    type: "POST",
                    data: {"ids":Ids.join(",")},
                    dataType: "text",
                    url:  "/manage/eclassbrand/activity/DetaildoDel",
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
        })
    });

    function reloadData()
    {
        $("#grid-table").trigger("reloadGrid");
    }
   /* function honorListReloadData()
    {
        $("#grid-table").trigger("reloadGrid");
    }*/
    function initDataList()
    {
        var searchKey = $("#searchKey").val();
        var postJson = {activityid: ${activityid}, searchKey:searchKey};
        //传入查询条件参数
        $("#grid-table").jqGrid("setGridParam",{postData:postJson, page:1}).trigger("reloadGrid");
    }
    function modify(id) {
        layer_show("编辑活动详情", "/manage/eclassbrand/activity/detailedit?id=" + id + "&activityid="+${activityid},  450, 290);
        event.stopPropagation();
    }

    function del(id, name)
    {
        layer.confirm("确定删除活动详情“"+name+"”？", function (index) {
            layer.close(index);
            jQuery.ajax({
                type: "POST",
                data: {"ids":id.toString()},
                dataType: "text",
                url:  "/manage/eclassbrand/activity/DetaildoDel",
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



</script>
