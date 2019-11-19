<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>
<link rel="stylesheet" type="text/css" href="/assets/scripts/dtree/css/ui.css" />

<!-- 加载顶部bar -->
<@topBar title="${title}" titleUrl="/manage/eclassbrand/index" currentUser=user icon="${menuConfig.icon}"/>
<style>
    .terminallist{width:100%;}
    .terminalinfo{display:inline-block;margin:5px}
    .terminalinfotop{height:130px;width:280px;border:1px solid #b3b3b3;padding:5px;cursor:pointer}
    .terminalimg{float:left;width:65%;height:100%}
    .terminalmode{float:left;width:35%;height:100%;text-align:center}
    .terminalname{padding:5px;height:30px;width:280px;line-height:20px}
    .modename{font-weight:700;font-size:16px;width:100%;height:40px;line-height:40px;}
    .openname{font-size:14px;width:100%;height:25px;line-height:25px}
    .onselect{box-shadow:#438EB9 2px 2px 10px 2px;border:none}
    input[type='checkbox']{width:20px;height:20px;-webkit-appearance:none;background: url("/assets/home/images/btn_n.png")no-repeat center;vertical-align:sub;background-size: contain;}
    input[type="checkbox"]:checked {background: url("/assets/home/images/btn_s.png")no-repeat center;background-size: contain;}
</style>
<div class="main-container ace-save-state" id="main-container">

    <!-- 加载左侧菜单 -->
    <@leftMenu indexUrl="/manage/eclassbrand/index" menuList=menuConfig.menuList currentUrl="/manage/eclassbrand/mode"/>
    <div class="main-content">
        <div class="main-content-inner">
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="/manage/basic/index">导航页</a>
                    </li>

                    <li>
                        <a href="#">模式设置</a>
                    </li>
                    <li class="active">模式设置</li>
                </ul>
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12" style="padding:0px;">
                        <div>
                            <div class="row search-page">
                                <div class="col-xs-12">
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-3" style="width:230px;">
                                            <div id="tree" class="search-area well well-sm">

                                            </div>
                                        </div>
                                        <div class="col-xs-12 col-sm-9 no-padding-left" style="width:85%">
                                            <div style="margin-bottom: 5px;">
                                                <button id="btnAdd" onclick="setmode();" style="float:left; margin-top: 2px" class="btn btn-success btn-mini radius-4"><i class="ace-icon fa fa-plus"></i>批量设置</button>
                                                <input type="text" id="searchKey" style="width:250px;margin-left:10px" placeholder="请输入终端名/别名" class="input-sm" />
                                                <button style="margin-bottom: 3px; margin-left: 5px;" type="button" id="searchButton" class="btn btn-purple btn-xs">
                                                    <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
                                                    搜索
                                                </button>

                                                <select class="chosen-select form-control" onchange="initDataList();" name="classId" id="classId" style="width: 200px; float:right ;">
                                                    <option value="-1">所有场景模式</option>
                                                </select>
                                                <label style="padding-top: 5px;float:right;"  for="year">筛选：</label>
                                           </div>
                                           <div id="terminallist" class="terminallist">
                                               <#--<div class="terminalinfo">
                                                   <div class="terminalinfotop">
                                                       <div class="terminalimg"></div>
                                                       <div class="terminalmode">
                                                           <div class="modename">班牌模式</div>
                                                           <div class="openname">巡视启用</div>
                                                           <div class="openname">考勤启用</div>
                                                           <div class="openname">今日考场</div>
                                                       </div>
                                                   </div>
                                                   <div class="terminalname">初一(1)班 陈丹</div>
                                               </div>-->
                                            </div>
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
    var categoryid = 0;
    var siteurl='${siteUrl}';
    jQuery(function ($) {
        var tree = $('#tree');
        tree.mac('dtree', {
            treeConfig: {
                title: '${topUnitName}',
                rootVisible: true,
                clickFolderMode: 1
            },
            onActivate: function (node) {
                categoryid = null;
                categoryid = node.data.key;
                initDataList();
            },
            loader: {
                url: '/manage/eclassbrand/mode/getCategoryList',
                params: { id: '0',level:0 },
                autoLoad: true
            }
        });
    });

    jQuery(function ($) {
        $(window).triggerHandler('resize.jqGrid');

        $("#searchButton").on("click", function () {
            initDataList();
        });
        initDataList();
    });

    function setmode()
    {
        var ids="";
        $("input[type='checkbox']").each(function () {
            if ($(this).is(":checked")) {
                if(ids=="")
                    ids=$(this).val();
                else
                    ids=ids+","+$(this).val();
            }
        });
        if(ids=="") {layer.alert("请选择需要设置的终端");return;}

        layer_show("模式设置", "/manage/eclassbrand/mode/setmode?terminalids=" + ids, 950, 520);
        event.stopPropagation();
    }

    function setStatus(id,obj)
    {
        var status = obj.checked?1:0;
        jQuery.ajax({
            type: "POST",
            data: {"id":id,"status":status},
            dataType: "text",
            url: "/manage/basic/teacher/doSetStatus",
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

    function initDataList() {
        var searchKey = $("#searchKey").val();
        //alert(categoryid);
        jQuery.ajax({
            type: "POST",
            data: {"categoryid": categoryid, "key": searchKey, "pageindex": 1},
            dataType: "json",
            url: "/manage/eclassbrand/mode/getTerminalList",
            success: function (result) {
                $("#terminallist").html("");
                for(var p in result.list){
                    var modename="";
                    if(result.list[p].mode==0)
                        modename="班牌模式";
                    else if(result.list[p].mode==1) {
                        modename = "资源模式";
                    }
                    else if(result.list[p].mode==2){
                            modename="网站模式";
                    }
                    $("#terminallist").append("<div class=\"terminalinfo\">\n" +
                        "                                                   <div style=\""+ (result.list[p].online==1?'background-color:#009787':'background-color:#b3b3b3') +"\" onclick='selectTerminal("+ result.list[p].pkid +",this);' class=\"terminalinfotop\">\n" +
                        "                                                       <div class=\"terminalimg\"><img onerror=\"this.onerror=null;this.src='/assets/lib/images/default/terminalerror.jpg';\" style='width:100%;height:100%' src='"+ siteurl +"/upload/ds/screenshot/latest/"+ result.list[p].code +".jpg'></div>\n" +
                        "                                                       <div class=\"terminalmode\">\n" +
                        "                                                           <div class=\"modename\"><span style='color:white;font-size:16px'>"+ modename +"</span></div>\n" +
                        "                                                           <div class=\"openname\"><span style='color:white;font-size:12px'>"+ (result.list[p].ispatrol==0?'':'巡视模式') +"</span></div>\n" +
                        "                                                           <div class=\"openname\"><span style='color:white;font-size:12px'>"+ (result.list[p].isattendance==0?'':'考勤模式') +"</span></div>\n" +
                        "                                                           <div class=\"openname\"><span style='color:white;font-size:12px'>"+ (result.list[p].mode==0?'':'今日考场') +"</span></div>\n" +
                        "                                                       </div>\n" +
                        "                                                   </div>\n" +
                        "                                                   <div class=\"terminalname\"><input class='checkBox' name='terminal' value='"+ result.list[p].pkid +"' type='checkbox' id='chk"+ result.list[p].pkid +"'><span style='margin-left:10px;font-weight:700;font-size:16px;color:#438eb9'>"+ result.list[p].name +"</span><span style='margin:10px;font-size:12px;color:#b3b3b3'>"+ result.list[p].spacename +"</span></div>\n" +
                        "                                               </div>")
                }
            },
            error: function () {
            }
        });
    }

    function selectTerminal(id,obj) {
        document.getElementById("chk"+ id +"").checked=!document.getElementById("chk"+ id +"").checked;
        if(document.getElementById("chk"+ id +"").checked)
            $(obj).attr("class","terminalinfotop onselect");
        else
            $(obj).attr("class","terminalinfotop");
    }

</script>
