<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>

<!-- 加载顶部bar -->
<@topBar title="${title}" titleUrl="/manage/basic/index" currentUser=user icon="${menuConfig.icon}"/>

<div class="main-container ace-save-state" id="main-container">

    <!-- 加载左侧菜单 -->
    <@leftMenu indexUrl="/manage/basic/index" menuList=menuConfig.menuList currentUrl="/manage/basic/opLog"/>

    <div class="main-content">
        <div class="main-content-inner">
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="/manage/basic/index">导航页</a>
                    </li>

                    <li>
                        <a href="#">日志查询</a>
                    </li>
                    <li class="active">操作日志</li>
                </ul>
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12" style="padding:0px;">
                        <div style="margin-bottom: 5px;">
                            <label style="padding-top: 3px" class="col-sm-1 no-padding-right" for="select-date">时间查询</label>
                            <span class="input-group col-xs-2" style="float: left">
                                <span class="input-group-addon">
                                    <i class="fa fa-calendar bigger-110"></i>
                                </span>
                                <input class="input-sm" style="width: 200px;" type="text" name="date-range-picker" id="searchDate">
                            </span>
                            <label style="padding-top: 3px;" class="col-sm-1 no-padding-right" for="txtUserName">操作用户</label>
                            <input type="text" id="txtUserName" placeholder="请输入操作用户名" class="input-sm" />
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
<script src="/assets/lib/js/moment.min.js"></script>
<script src="/assets/lib/js/daterangepicker.min.js"></script>
<script src="/assets/lib/js/jquery.jqGrid.min.js"></script>
<script src="/assets/lib/js/grid.locale-cn.js"></script>
<script type="text/javascript">
    jQuery(function ($) {

        $('input[name=date-range-picker]').daterangepicker({
            'applyClass' : 'btn-sm btn-success',
            'cancelClass' : 'btn-sm btn-default',
            locale: {
                applyLabel: '确定',
                cancelLabel: '取消',
                format: "YYYY-MM-DD",
                separator: "--",
                daysOfWeek: ["日","一","二","三","四","五","六"],
                monthNames: ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"]
            },
            showDropdowns:true,
            startDate:'2018-01-01',
            endDate:moment()
        })

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
            url: "/manage/basic/getOpLogPageList",
            datatype: "json", // 从服务器端返回的数据类型，默认xml。可选类型：xml，local，json，jsonnp，script，xmlstring，jsonstring，clientside
            mtype: "POST", // 提交方式，默认为GET
            height: "auto", // 高度，表格高度。可为数值、百分比或'auto'
            colNames: ['操作用户', '操作IP','操作应用', '操作模块', '详细描述', '操作时间'], // 列显示名称，是一个数组对象
            colModel: [
                // name 表示列显示的名称；
                // index 表示传到服务器端用来排序用的列名称；
                // width 为列宽度；
                // align 为对齐方式；
                // sortable 表示是否可以排序
                {name: 'username', index: 'username', width: 150,fixed:true, align: 'left',sortable:true, resize:false},
                {name: 'opip', index: 'opip', width: 120, fixed:true, align: 'left',sortable:true, resize:false},
                {name: 'opapp', index: 'opapp', width: 120, fixed:true, align: 'left',sortable:true, resize:false},
                {name: 'opmodule', index: 'opmodule', width: 120, fixed:true, align: 'left',sortable:true, resize:false},
                {name: 'opremark', index: 'opremark',  align: 'left',sortable:false, resize:false},
                {name: 'createtime', index: 'createtime', width: 180,fixed:true, align: 'left',sortable:true, resize:false}
            ],
            //rownumbers: true,// 显示左侧的序号
            altRows:true,// 设置为交替行表格,默认为false
            sortname:'createtime', // 排序列的名称，此参数会被传到后台
            sortorder:'desc', // 排序顺序，升序或者降序（ASC或DESC）
            viewrecords: true, // 是否在翻页导航栏显示记录总数
            rowNum: getRowNum(), // 每页显示记录数
            rowList: rowList, // 用于改变显示行数的下拉列表框的元素数组
            pager: $('#grid-pager'), // 定义翻页用的导航栏
            //multiselect: true,
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

        $("#searchButton").click(function(){
            var searchDate = $("#searchDate").val();
            var searchName = $("#txtUserName").val();
            var postJson = {searchDate:searchDate,searchName:searchName};
            //传入查询条件参数
            $("#grid-table").jqGrid("setGridParam",{postData:postJson, page:1}).trigger("reloadGrid");
        });

    });

</script>
