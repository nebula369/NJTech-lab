<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>

<!-- 加载顶部bar -->
<@topBar title="${title}" titleUrl="/manage/edu/index" currentUser=user icon="${menuConfig.icon}"/>

<div class="main-container ace-save-state" id="main-container">

    <!-- 加载左侧菜单 -->
    <@leftMenu indexUrl="/manage/edu/index" menuList=menuConfig.menuList currentUrl="/manage/edu/curriculum"/>

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
                    <li class="active">班级课程表</li>
                </ul>
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12" style="padding:0px;">
                        <div style="margin-bottom: 5px;">
                            <span style="float: left">
                            <label style="width: 90px;padding-top: 5px" class="col-sm-1 no-padding-right" for="schoolId">当前学校：</label>
                            <select class="chosen-select form-control" name="schoolId" onchange="initStage();" id="schoolId" style="width: 200px;">
                                        <#list unitList as item>
                                            <option value="${item.pkid}" <#if item.pkid==schoolId>selected</#if>>${item.name}</option>
                                        </#list>
                            </select>
                           </span>
                            <label style="padding-top: 5px;" class="col-sm-1 no-padding-right" for="year">当前学段：</label>
                            <select class="chosen-select form-control" onchange="initGrade();" name="stageId" id="stageId" style="width: 100px; float:left;">
                            </select>
                            <label style="padding-top: 5px;" class="col-sm-1 no-padding-right" for="year">当前年级：</label>
                            <select class="chosen-select form-control" onchange="initClass();" name="gradeId" id="gradeId" style="width: 100px; float:left;">
                            </select>
                            <label style="padding-top: 5px;" class="col-sm-1 no-padding-right" for="year">当前班级：</label>
                            <select class="chosen-select form-control" onchange="initClassCurriclumData();" name="classId" id="classId" style="width: 100px; float:left;">
                            </select>
                            <button id="btnInport" style="float:left; margin-top: 3px; margin-left: 10px;" class="btn btn-success btn-mini radius-4"><i class="ace-icon fa fa-plus"></i>课表导入</button>
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

        initStage();

        $("#btnInport").on("click", function () {
            var schoolId = $("#schoolId").val();
            var classId = $("#classId").val();
            var stageId = $("#stageId").val();
            var gradeId = $("#gradeId").val();
            layer_show("课程表导入", "/manage/edu/curriculum/currInport?schoolId="+ schoolId+"&classId=" + classId, 500, 250);
            event.stopPropagation();
        });
    });

    function initStage()
    {
        $('#tbList').html("");
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
                initGrade();
            },
            error:function () {

            }
        });
    }

    function initGrade()
    {
        $('#tbList').html("");
        var stageId = $("#stageId").val();
        $('#gradeId').html("");
        jQuery.ajax({
            type: "POST",
            data: {"stageId":stageId},
            dataType: "json",
            url: "/manage/edu/curriculum/getStageGradeList",
            success: function (result) {
                var html = '';
                for(var i=0;i<result.length;i++){
                    html +='<option value="'+result[i].code+'">'+result[i].name+'</option>';
                }
                if(html == "") html= '<option value="">请选择</option>';
                $('#gradeId').html(html);
                initClass();
            },
            error:function () {

            }
        });
    }

    function initClass()
    {
        $('#tbList').html("");
        var stageId = $("#stageId").val();
        var schoolId = $("#schoolId").val();
        var gradeId = $("#gradeId").val();
        $('#classId').html("");
        jQuery.ajax({
            type: "POST",
            data: {"schoolId":schoolId,"stageId":stageId, "gradeId":gradeId},
            dataType: "json",
            url: "/manage/edu/curriculum/getSchoolClassList",
            success: function (result) {
                var html = '';
                for(var i=0;i<result.length;i++){
                    html +='<option value="'+result[i].pkid+'">'+result[i].name+'</option>';
                }
                if(html == "") html= '<option value="">请选择</option>';
                $('#classId').html(html);
                initClassCurriclumData();
            },
            error:function () {

            }
        });
    }

    function initDataList()
    {
        var classId = $("#classId").val();
        $('#tbList').html("");
        if($.trim(classId) == "") return;
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
                            '           </th>' +
                            '           <td style="width: 12.5%">' +
                            '               <div class="js-text" id="div_1_'+(i+1)+'">'+getCurrTime(1, item.weekTimeList, item.session)+'</div>' +
                            '                   <div class="js-action" id="divaction_1_'+(i+1)+'" onclick="setCurrDay(1, '+item.session+','+(i+1)+','+item.pkid+');">' +
                            '                       <a href="javascript:void()">设置</a>' +
                            '                   </div>' +
                            '           </td>' +
                            '           <td style="width: 12.5%">' +
                            '               <div class="js-text" id="div_2_'+(i+1)+'">'+getCurrTime(2, item.weekTimeList, item.session)+'</div>' +
                            '                   <div class="js-action" id="divaction_2_'+(i+1)+'" onclick="setCurrDay(2, '+item.session+', '+(i+1)+','+item.pkid+');">' +
                            '                       <a href="javascript:void()">设置</a>' +
                            '                   </div>' +
                            '           </td>' +
                            '           <td style="width: 12.5%">' +
                            '               <div class="js-text" id="div_3_'+(i+1)+'">'+getCurrTime(3, item.weekTimeList, item.session)+'</div>' +
                            '                   <div class="js-action" id="divaction_3_'+(i+1)+'" onclick="setCurrDay(3, '+item.session+','+(i+1)+','+item.pkid+');">' +
                            '                       <a href="javascript:void()">设置</a>' +
                            '                   </div>' +
                            '           </td>' +
                            '           <td style="width: 12.5%">' +
                            '               <div class="js-text" id="div_4_'+(i+1)+'">'+getCurrTime(4, item.weekTimeList, item.session)+'</div>' +
                            '                   <div class="js-action" id="divaction_4_'+(i+1)+'" onclick="setCurrDay(4, '+item.session+','+(i+1)+','+item.pkid+');">' +
                            '                       <a href="javascript:void()">设置</a>' +
                            '                   </div>' +
                            '           </td>' +
                            '           <td style="width: 12.5%">' +
                            '               <div class="js-text" id="div_5_'+(i+1)+'">'+getCurrTime(5, item.weekTimeList, item.session)+'</div>' +
                            '                   <div class="js-action" id="divaction_5_'+(i+1)+'" onclick="setCurrDay(5, '+item.session+','+(i+1)+','+item.pkid+');">' +
                            '                       <a href="javascript:void()">设置</a>\n' +
                            '                   </div>' +
                            '           </td>' +
                            '           <td style="width: 12.5%">' +
                            '               <div class="js-text" id="div_6_'+(i+1)+'">'+getCurrTime(6, item.weekTimeList, item.session)+'</div>' +
                            '                   <div class="js-action" id="divaction_6_'+(i+1)+'" onclick="setCurrDay(6, '+item.session+','+(i+1)+','+item.pkid+');">' +
                            '                       <a href="javascript:void()">设置</a>' +
                            '                   </div>' +
                            '           </td>' +
                            '           <td style="width: 12.5%">' +
                            '               <div class="js-text" id="div_0_'+(i+1)+'">'+getCurrTime(0, item.weekTimeList, item.session)+'</div>' +
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

    var curriculumData = [];
    function initClassCurriclumData()
    {
        curriculumData = [];
        var schoolId = $("#schoolId").val();
        var classId = $("#classId").val();
        if($.trim(classId) == "") return;
        jQuery.ajax({
            type: "POST",
            data: {"schoolId":schoolId,"classId":classId},
            dataType: "json",
            url: "/manage/edu/curriculum/getSchoolClassCurriculumList",
            success: function (result) {
                curriculumData = result;
                initDataList();
            },
            error:function () {

            }
        });
    }

    function getCurrTime(week, weekTimeList, session)
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
        for(var i=0; i<curriculumData.length; i++)
        {
            var item = curriculumData[i];
            if(item.week == week && item.session == session)
            {
                result = item.teacherName+"("+item.subjectName+")" + "<br/>" + result;
            }
        }
        return result;
    }

    function setCurrDay(week, session, itemid, id) {
        var schoolId = $("#schoolId").val();
        var classId = $("#classId").val();
        var stageId = $("#stageId").val();
        var gradeId = $("#gradeId").val();
        layer_show("设置任课教师和课程", "/manage/edu/curriculum/selectSubjectTeacher?schoolId=" + schoolId +"&stageId=" + stageId +"&gradeId="+gradeId+"&classId="+classId+"&week="+week+"&session=" + session, 600, 470);
        event.stopPropagation();
    }
</script>
