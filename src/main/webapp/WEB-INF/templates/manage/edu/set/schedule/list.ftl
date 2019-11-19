<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>

<!-- 加载顶部bar -->
<@topBar title="${title}" titleUrl="/manage/edu/index" currentUser=user icon="${menuConfig.icon}"/>

<div class="main-container ace-save-state" id="main-container">

    <!-- 加载左侧菜单 -->
    <@leftMenu indexUrl="/manage/edu/index" menuList=menuConfig.menuList currentUrl="/manage/edu/schedule"/>

    <div class="main-content">
        <div class="main-content-inner">
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="/manage/edu/index">导航页</a>
                    </li>

                    <li>
                        <a href="#">基础设置</a>
                    </li>
                    <li class="active">上课时间段</li>
                </ul>
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12" style="padding:0px;">
                        <div style="margin-bottom: 5px;">
                            <button id="btnAdd" style="float:left; margin-top: 5px" class="btn btn-success btn-mini radius-4"><i class="ace-icon fa fa-plus"></i>新增上课时间段</button>
                            <button id="btnInport" style="float:left; margin-top: 5px; margin-left: 5px;" class="btn btn-success btn-mini radius-4"><i class="ace-icon fa fa-plus"></i>Excel导入</button>
                            <span style="float: left">
                            <label style="width: 90px;padding-top: 5px" class="col-sm-1 no-padding-right" for="schoolId">当前学校：</label>
                            <select class="chosen-select form-control" name="schoolId" onchange="initStage();" id="schoolId" style="width: 200px;">
                                        <#list unitList as item>
                                            <option value="${item.pkid}" <#if item.pkid==schoolId>selected</#if>>${item.name}</option>
                                        </#list>
                            </select>
                           </span>
                            <label style="padding-top: 5px;" class="col-sm-1 no-padding-right" for="year">当前学段：</label>
                            <select class="chosen-select form-control" onchange="initDataList();" name="stageId" id="stageId" style="width: 100px; float:left;">
                            </select>
                            <div style="clear:both;"></div>
                        </div>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th scope="col" align="center" style="width: 12.5%">节次\星期</th>
                                <th scope="col" style="width: 12.5%">星期一</th>
                                <th scope="col" style="width: 12.5%">星期二</th>
                                <th scope="col" style="width: 12.5%">星期三</th>
                                <th scope="col" style="width: 12.5%">星期四</th>
                                <th scope="col" style="width: 12.5%">星期五</th>
                                <th scope="col" style="width: 12.5%">星期六</th>
                                <th scope="col" style="width: 12.5%">星期日</th>
                            </tr>
                            </thead>
                            <tbody id="tbList">

                            </tbody>
                        </table>
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

        $("#btnAdd").on("click", function () {
            layer_show("新建上课时间段", "/manage/edu/schedule/add?schoolId="+ $("#schoolId").val()+"&stageId=" + $("#stageId").val(), 600, 470);
            event.stopPropagation();
        });

        $("#btnInport").on("click", function () {
            layer_show("作息时间导入", "/manage/edu/schedule/restTimeInport?schoolId="+ $("#schoolId").val()+"&stageId=" + $("#stageId").val(), 500, 250);
            event.stopPropagation();
        });

        initStage();
    });

    function initStage()
    {
        var schoolName = $("#schoolId").find("option:selected").text();
        if(schoolName.indexOf("[学]")<0)
        {
            layer.msg("仅学校可以维护上课时间段",{icon:2});
            $("#schoolId").val("${schoolId}");
            $("#schoolId").trigger("chosen:updated");
            return;
        }
        var schoolId = $("#schoolId").val();
        jQuery.ajax({
            type: "POST",
            data: {"schoolId":schoolId},
            dataType: "json",
            url: "/manage/basic/class/getSchoolStageList",
            success: function (result) {
                var html = '';
                for(var i=0;i<result.length;i++){
                    html +='<option value="'+result[i].code+'">'+result[i].name+'</option>';
                }
                if(html == "") html= '<option value="">请选择</option>';
                $('#stageId').html(html);
                initDataList();
            },
            error:function () {

            }
        });
    }

    function initDataList()
    {
        jQuery.ajax({
            type: "POST",
            data: {"schoolId": $("#schoolId").val(),"stageId":$("#stageId").val()},
            dataType: "json",
            url: "/manage/edu/schedule/getSchoolScheduleList",
            success: function (result) {
                var htmlArray = [];
                for(var i=0;i<result.length;i++){
                    var item = result[i];
                    htmlArray.push('<tr>' +
                            '           <th class="indexrow" scope="row" style="width: 12.5%">'+item.name+'<br />' +
                            '               <span class="sessiontime"><a href="javaScript:setCurrWeek('+item.pkid+','+item.session+')">设置</a>&nbsp;<a href="javaScript:delRestTime('+item.pkid+',\''+item.name+'\')">删除</a></span>' +
                            '           </th>' +
                            '           <td style="width: 12.5%">' +
                            '               <div class="js-text" id="div_1_'+(i+1)+'">'+getCurrTime(1, item.weekTimeList)+'</div>' +
                            '                   <div class="js-action" id="divaction_1_'+(i+1)+'" onclick="setCurrDay(1, '+item.session+','+(i+1)+','+item.pkid+');">' +
                            '                       <a href="javascript:void()">设置</a>' +
                            '                   </div>' +
                            '           </td>' +
                            '           <td style="width: 12.5%">' +
                            '               <div class="js-text" id="div_2_'+(i+1)+'">'+getCurrTime(2, item.weekTimeList)+'</div>' +
                            '                   <div class="js-action" id="divaction_2_'+(i+1)+'" onclick="setCurrDay(2, '+item.session+', '+(i+1)+','+item.pkid+');">' +
                            '                       <a href="javascript:void()">设置</a>' +
                            '                   </div>' +
                            '           </td>' +
                            '           <td style="width: 12.5%">' +
                            '               <div class="js-text" id="div_3_'+(i+1)+'">'+getCurrTime(3, item.weekTimeList)+'</div>' +
                            '                   <div class="js-action" id="divaction_3_'+(i+1)+'" onclick="setCurrDay(3, '+item.session+','+(i+1)+','+item.pkid+');">' +
                            '                       <a href="javascript:void()">设置</a>' +
                            '                   </div>' +
                            '           </td>' +
                            '           <td style="width: 12.5%">' +
                            '               <div class="js-text" id="div_4_'+(i+1)+'">'+getCurrTime(4, item.weekTimeList)+'</div>' +
                            '                   <div class="js-action" id="divaction_4_'+(i+1)+'" onclick="setCurrDay(4, '+item.session+','+(i+1)+','+item.pkid+');">' +
                            '                       <a href="javascript:void()">设置</a>' +
                            '                   </div>' +
                            '           </td>' +
                            '           <td style="width: 12.5%">' +
                            '               <div class="js-text" id="div_5_'+(i+1)+'">'+getCurrTime(5, item.weekTimeList)+'</div>' +
                            '                   <div class="js-action" id="divaction_5_'+(i+1)+'" onclick="setCurrDay(5, '+item.session+','+(i+1)+','+item.pkid+');">' +
                            '                       <a href="javascript:void()">设置</a>\n' +
                            '                   </div>' +
                            '           </td>' +
                            '           <td style="width: 12.5%">' +
                            '               <div class="js-text" id="div_6_'+(i+1)+'">'+getCurrTime(6, item.weekTimeList)+'</div>' +
                            '                   <div class="js-action" id="divaction_6_'+(i+1)+'" onclick="setCurrDay(6, '+item.session+','+(i+1)+','+item.pkid+');">' +
                            '                       <a href="javascript:void()">设置</a>' +
                            '                   </div>' +
                            '           </td>' +
                            '           <td style="width: 12.5%">' +
                            '               <div class="js-text" id="div_0_'+(i+1)+'">'+getCurrTime(0, item.weekTimeList)+'</div>' +
                            '                   <div class="js-action" id="divaction_0_'+(i+1)+'" onclick="setCurrDay(0, '+item.session+','+(i+1)+','+item.pkid+');">' +
                            '                       <a href="javascript:void()">设置</a>' +
                            '                   </div>' +
                            '           </td>' +
                            '</tr>');
                }
                $('#tbList').html(htmlArray.join(""));
            },
            error:function () {

            }
        });
    }

    function getCurrTime(week, weekTimeList)
    {
        var result = "";
        for(var i=0; i<weekTimeList.length; i++)
        {
            var item = weekTimeList[i];
            if(item.week == week)
            {
                result = item.starttime + "-" + item.endtime;
            }
        }
        return result;
    }

    function setCurrWeek(id, session) {
        layer_show("设置上课时间段", "/manage/edu/schedule/edit?id=" + id +"&weeks=1,2,3,4,5", 600, 470);
        event.stopPropagation();
    }

    function setCurrDay(week, session, itemid, id) {
        layer_show("设置上课时间段", "/manage/edu/schedule/edit?id=" + id +"&weeks=" + week, 600, 470);
        event.stopPropagation();
    }

    function delRestTime(id, name)
    {
        layer.confirm("确定删除作息“"+name+"”？", function (index) {
            layer.close(index);
            jQuery.ajax({
                type: "POST",
                data: {"id":id},
                dataType: "text",
                url: "/manage/edu/schedule/doDel",
                success: function (result) {
                    if(result == 1) {
                        layer.msg("删除成功", {icon: 1, time: 1000}, function () {
                            initDataList();
                        });
                    }
                    else if(result == 0)
                    {
                        layer.msg("删除失败",{icon:2});
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
