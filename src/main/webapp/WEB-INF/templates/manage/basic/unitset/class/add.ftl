<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>
<link rel="stylesheet" href="/assets/lib/css/bootstrap-datepicker3.min.css" />

<div class="main-content">
    <div class="main-content-inner">
        <div class="page-content">
            <div class="row">
                <div class="col-xs-12">
                    <form class="form-horizontal" id="validation-form" role="form">
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="schoolId">学校:</label>
                            <div class="col-sm-9" style="float: left;">
                                <div class="clearfix" style="float:left;">
                                    <select class="chosen-select form-control" name="schoolId" id="schoolId" style="width: 200px;" data-placeholder="--请选择学校--">
                                        <option value=""></option>
                                        <#list unitList as item>
                                            <option value="${item.pkid}" <#if item.pkid == schoolId>selected</#if>>${item.name}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="stage">学段:</label>
                            <div class="col-sm-9" style="float: left;">
                                <div class="clearfix" style="float:left;">
                                    <select class="chosen-select form-control" name="stage" style="width: 150px" id="stage" data-placeholder="--请选择学段--">
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="grade">年级:</label>
                            <div class="col-sm-9" style="float: left;">
                                <div class="clearfix" style="float:left;">
                                    <select class="chosen-select form-control" style="width: 150px;" name="grade" id="grade" data-placeholder="--请选择年级--">
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group" id="stage">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="classCode">班级代码:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control" value="" id="classCode" name="classCode" type="text">
                                </div>
                            </div>
                        </div>
                        <div class="form-group" id="stage">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="name">班级名称:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <span style="float:left;margin-top: 7px;padding-right: 5px;" id="gradeName"></span>
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control" value="" id="name" name="name" type="text">
                                </div>
                            </div>
                        </div>
                        <div class="form-group" id="stage">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="schoolYear">学制:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control" value="3" style="width:100px;" id="schoolYear" name="schoolYear" type="text">
                                </div>
                            </div>
                        </div>
                        <div class="form-group" id="stage">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="space">班级地点:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <select class="chosen-select form-control" name="spaceType" id="spaceType" style="float:left;" data-placeholder="--请选择场地--">
                                        <option value=""></option>
                                        <#list spaceTypes as item>
                                            <option value="${item.pkid}">${item.name}</option>
                                        </#list>
                                    </select>
                                    <select class="chosen-select form-control" name="space" id="space" style="float:left;" data-placeholder="--请选择场地--">
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="stage">单位管理员:</label>
                            <div class="col-sm-9" style="float: left; ">
                                <div class="clearfix">
                                    <input type="text" name="manageUserName" value="" id="manageUserName" style="width: 120px;" class="col-xs-12 col-sm-6">
                                    &nbsp;<a onclick="selectUser();return false;" href="#">设置单位管理员</a>
                                    <a onclick="cancelManageUser();return false;" href="#">取消单位管理员</a>
                                    <input type="hidden" name="manageuser" value="0" id="manageuser"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group" id="stage">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="stage">排序号:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control" style="width: 100px" value="1" id="sortnum" name="sortnum" type="text">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-offset-3 col-md-9" style="margin-left: 90px;">
                            <button class="btn btn-success btn-sm" type="button">
                                <i class="ace-icon fa fa-check"></i>
                                确 定
                            </button>
                            &nbsp; &nbsp;
                            <button class="btn btn-sm" type="reset">
                                <i class="ace-icon fa fa-undo"></i>
                                重置
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<#include "/controls/appbottom.ftl"/>
<script src="/assets/lib/js/moment.min.js"></script>
<script src="/assets/lib/js/jquery.validate.min.js"></script>
<script src="/assets/lib/js/chosen.jquery.min.js"></script>
<script type="text/javascript">
    jQuery(function ($) {

        $('#validation-form').validate({
            errorElement: 'div',
            errorClass: 'help-block',
            focusInvalid: true,
            ignore: "",
            rules: {
                schoolId:{
                    required: true
                },
                stage:{
                    required: true
                },
                grade:{
                    required: true
                },
                classCode:{
                    required: true
                },
                name: {
                    required: true
                },
                schoolYear:{
                    required: true,
                    digits: true
                },
                sortnum:{
                    required: true,
                    digits: true
                }
            },

            messages: {
                schoolId:{
                    required: "请选择所属学校"
                },
                stage:{
                    required: "请选择学段"
                },
                grade:{
                    required: "请选择年级"
                },
                classCode:{
                    required: "不能为空"
                },
                name: {
                    required: "不能为空"
                },
                schoolYear: {
                    required: "不能为空",
                    digits: "请输入正整数"
                },
                sortnum: {
                    required: "不能为空",
                    digits: "请输入正整数"
                }
            },
            highlight: function (e) {
                $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
            },
            success: function (e) {
                $(e).closest('.form-group').removeClass('has-error');
                $(e).remove();
            },
            errorPlacement: function (error, element) {
                //在easybasic.js中定义
                initErrorPlacement(error, element);
            }
        });

        $("#validation-form .btn-success").on("click", function () {
            if(!$('#validation-form').valid())
            {
                event.preventDefault();
                return;
            }
            if($(this).hasClass("disabled")) return;
            $(this).addClass("disabled");
            var obj = $(this);
            var spaceId = $("#space").val();
            if(spaceId == "") spaceId=0;
            jQuery.ajax({
                type: "POST",
                data: {"unitid":$("#schoolId").val(),"name":$("#name").val(),"sortnum":$("#sortnum").val(),
                    "classcode":$("#classCode").val(),"stageid":$("#stage").val(),"gradeid":$("#grade").val(),"schoolsystem":$("#schoolYear").val(),
                    "schoolyear":year,"spaceId":spaceId,"manageuser":$("#manageuser").val()},
                dataType: "text",
                url: "/manage/basic/class/doAdd",
                success: function (result) {
                    if(result == 1) {
                        layer.msg("保存成功", {icon: 1, time: 1000}, function () {
                            parent.reloadData();
                            layer_close();
                        });
                    }
                    else if(result == 0)
                    {
                        layer.msg("班级代码已经存在",{icon:2});
                        obj.removeClass("disabled");
                    }
                    else if(result == -1)
                    {
                        layer.msg("班级名称已经存在",{icon:2});
                        obj.removeClass("disabled");
                    }
                },
                error:function () {
                    obj.removeClass("disabled");
                }
            });
        });

        $('#schoolId').chosen({
            allow_single_deselect:false,
            search_contains:true,
            no_results_text:"没有匹配数据:"
        });

        $('#spaceType').chosen({
            allow_single_deselect:true,
            search_contains:true,
            no_results_text:"没有匹配数据:"
        });

        $('#space').chosen({
            allow_single_deselect:true,
            search_contains:true,
            no_results_text:"没有匹配数据:"
        });

        initStage();

        $("#schoolId").on("change",function () {
            var schoolName = $("#schoolId").find("option:selected").text();
            if(schoolName.indexOf("[学]")<0)
            {
                layer.msg("请选择学校",{icon:2});
                $("#schoolId").val("${schoolId}");
                $("#schoolId").trigger("chosen:updated");
                return;
            }
            initStage();
        });
        $("#stage").on("change", function () {
            initGrade();
        });
        $("#grade").on("change", function () {
            initClassCode();
        });
        $("#spaceType").on("change", function () {
            initSpace();
        });

        function initStage()
        {
            var schoolId = $("#schoolId").val();
            if(schoolId=="")schoolId=0;
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
                    if(html == "") html= '<option value="">--请选择学段--</option>';
                    $('#stage').html(html);
                    initGrade();
                },
                error:function () {

                }
            });
        }

        function initGrade()
        {
            var stageId = $("#stage").val();
            if(stageId=="")stageId=0;
            jQuery.ajax({
                type: "POST",
                data: {"stageId":stageId},
                dataType: "json",
                url: "/manage/basic/class/getStageGradeList",
                success: function (result) {
                    var html = '';
                    for(var i=0;i<result.length;i++){
                        html +='<option value="'+result[i].code+'">'+result[i].name+'</option>';
                    }
                    if(html == "") html= '<option value="">--请选择年级--</option>';
                    $('#grade').html(html);
                    initClassCode();
                },
                error:function () {

                }
            });
        }

        function initSpace()
        {
            var typeId = $("#spaceType").val();
            if(typeId=="")typeId=0;
            jQuery.ajax({
                type: "POST",
                data: {"typeId":typeId},
                dataType: "json",
                url: "/manage/basic/class/getSpaceList",
                success: function (result) {
                    var html = '';
                    for(var i=0;i<result.length;i++){
                        html +='<option value="'+result[i].pkid+'">'+result[i].name+'</option>';
                    }
                    if(html == "") html= '<option value="">--请选择场地--</option>';
                    $('#space').html(html);
                    $("#space").trigger("chosen:updated");
                },
                error:function () {

                }
            });
        }

        var year = "";
        function initClassCode() {
            var schoolId = $("#schoolId").val();
            var stageId = $("#stage").val();
            var gradeId = $("#grade").val();
            var gradeName = jQuery("#grade option:selected").text();
            if (gradeId != "") {
                $("#gradeName").html(gradeName);
            }
            jQuery.ajax({
                type: "POST",
                data: {"schoolId":schoolId , "stageId":stageId,"gradeId": gradeId},
                dataType: "json",
                url: "/manage/basic/class/initClassCode",
                success: function (result) {
                    $("#classCode").val(result[1]);
                    $("#name").val(result[2]);
                    year = result[0];
                    $("#schoolYear").val(result[3]);
                }
            });
        }

    });

    function selectUser()
    {
        parent.layer_show("设置班主任", "/manage/basic/common/selectUser?type=0&isSingle=1", 900, 700);
        event.stopPropagation();
    }

    function setSelectedUser(selectUsers)
    {
        var users = selectUsers.split("@");
        $("#manageUserName").val(users[1]);
        $("#manageuser").val(users[0]);
    }

    function cancelManageUser()
    {
        $("#manageUserName").val("");
        $("#manageuser").val("0");
    }
</script>
