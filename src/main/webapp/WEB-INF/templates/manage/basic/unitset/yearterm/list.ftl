<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>

<!-- 加载顶部bar -->
<@topBar title="${title}" titleUrl="/manage/basic/index" currentUser=user icon="${menuConfig.icon}"/>

<div class="main-container ace-save-state" id="main-container">

    <!-- 加载左侧菜单 -->
    <@leftMenu indexUrl="/manage/basic/index" menuList=menuConfig.menuList currentUrl="/manage/basic/yearterm"/>

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
                    <li class="active">年度学期</li>
                </ul>
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12" style="padding:0px;">
                        <div style="margin-bottom: 5px;">
                            <button id="btnAdd" style="float:left; margin-top: 5px" class="btn btn-success btn-mini radius-4"><i class="ace-icon fa fa-plus"></i>新增学期</button>
                            <span style="float: left">
                            <label style="width: 90px;padding-top: 5px" class="col-sm-1 no-padding-right" for="schoolId">当前学校：</label>
                            <select class="chosen-select form-control" name="schoolId" onchange="initYear();" id="schoolId" style="width: 200px;">
                                        <#list unitList as item>
                                            <option value="${item.pkid}" <#if item.pkid==schoolId>selected</#if>>${item.name}</option>
                                        </#list>
                            </select>
                           </span>
                            <label style="padding-top: 5px;" class="col-sm-1 no-padding-right" for="year">当前年度：</label>
                            <select class="chosen-select form-control" onchange="initDataList();" name="year" id="year" style="width: 100px; float:left;">

                            </select>
                            <div style="clear:both;"></div>
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
            url: "/manage/basic/yearterm/getYearTermList",
            postData: {schoolId: $("#schoolId").val(),year:$("#year").val()},
            datatype: "json", // 从服务器端返回的数据类型，默认xml。可选类型：xml，local，json，jsonnp，script，xmlstring，jsonstring，clientside
            mtype: "POST", // 提交方式，默认为GET
            height: "auto", // 高度，表格高度。可为数值、百分比或'auto'
            colNames: ['编号','学期名称', '工作开始日期', '学习开始日期','学习结束日期','工作结束日期', '操作'], // 列显示名称，是一个数组对象
            colModel: [
                // name 表示列显示的名称；
                // index 表示传到服务器端用来排序用的列名称；
                // width 为列宽度；
                // align 为对齐方式；
                // sortable 表示是否可以排序
                {name: 'pkid', index: 'pkid', width: 70,fixed:true, align: 'left',sortable:false, resize:false},
                {name: 'schoolTermName', index: 'schoolTermName', align: 'left',sortable:false, resize:false},
                {name: 'workstartdate', index: 'workstartdate', align: 'left',sortable:false, resize:false},
                {name: 'learnstartdate', index: 'learnstartdate', align: 'left',sortable:false, resize:false},
                {name: 'learnenddate', index: 'learnenddate', align: 'left',sortable:false, resize:false},
                {name: 'workenddate', index: 'workenddate', align: 'left',sortable:false, resize:false},
                {
                    name: 'action', index: '', sortable: false, resize: false,
                    formatter: function (value, grid, rows, state) {
                        var html = "";
                        html = "<button class='btn btn-minier btn-success' onclick='modify("+rows.pkid+");'><i class=\"ace-icon fa fa-pencil\"></i>编辑</button>&nbsp;&nbsp;";
                        return html;
                    }
                }
            ],
            //rownumbers: true,// 显示左侧的序号
            altRows:true,// 设置为交替行表格,默认为false

        });
        $(window).triggerHandler('resize.jqGrid');

        $("#btnAdd").on("click", function () {
            layer_show("新建学校学期数据", "/manage/basic/yearterm/add?schoolId="+ $("#schoolId").val(), 800, 520);
            event.stopPropagation();
        });

        initYear();

        $('#schoolId').chosen({
            allow_single_deselect:false,
            search_contains:true,
            no_results_text:"没有匹配数据:"
        });
    });

    function reloadData()
    {
        $("#grid-table").trigger("reloadGrid");
    }

    function modify(id)
    {
        layer_show("编辑单位", "/manage/basic/yearterm/edit?id=" + id, 800, 520);
        event.stopPropagation();
    }

    function initYear()
    {
        var schoolName = $("#schoolId").find("option:selected").text();
        if(schoolName.indexOf("[学]")<0)
        {
            layer.msg("仅学校下面有学期数据",{icon:2});
            $("#schoolId").val("${schoolId}");
            $("#schoolId").trigger("chosen:updated");
            return;
        }
        var schoolId = $("#schoolId").val();
        jQuery.ajax({
            type: "POST",
            data: {"schoolId":schoolId},
            dataType: "json",
            url: "/manage/basic/yearterm/getYearList",
            success: function (result) {
                var html = '';
                for(var i=0;i<result.length;i++){
                    html +='<option value="'+result[i]+'">'+result[i]+'</option>';
                }
                if(html == "") html= '<option value="">请选择</option>';
                $('#year').html(html);
                initDataList();
            },
            error:function () {

            }
        });
    }

    function initDataList()
    {
        var schoolId = $("#schoolId").val();
        var year = $("#year").val();
        var postJson = {schoolId: schoolId,year:year};
        //传入查询条件参数
        $("#grid-table").jqGrid("setGridParam",{postData:postJson, page:1}).trigger("reloadGrid");
    }

</script>
