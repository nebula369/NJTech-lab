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
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="parentid">所属学校:</label>
                            <div class="col-sm-9" style="float: left;">
                                <div class="clearfix" style="float:left;">
                                    <select class="chosen-select form-control" name="schoolid" id="schoolid" style="width: 200px;" data-placeholder="--请选择所属学校--">
                                        <option value=""></option>
                                        <#list unitList as item>
                                            <option value="${item.pkid}" <#if item.pkid==schoolId>selected</#if>>${item.name}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="name">当前年段:</label>
                            <div class="col-sm-9" style="float: left;">
                                <div class="clearfix" style="float:left;">
                                    <select class="chosen-select form-control" name="name" id="name" style="width: 200px;">
                                        <#list yearList as item>
                                            <option value="${item}">${item}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="name">学期名称:</label>
                            <div class="col-sm-9" style="float: left;">
                                <div class="clearfix" style="float:left;">
                                    <select class="chosen-select form-control" name="schoolterm" id="schoolterm" style="width: 200px;">
                                        <#list termList as item>
                                            <option value="${item.code}">${item.name}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group" id="stage">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="workdate">工作日期:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix input-group col-xs-2" style="float:left;width:300px">
                                        <span class="input-group-addon">
                                            <i class="fa fa-calendar bigger-110"></i>
                                        </span>
                                        <input class="input-sm" style="width: 200px;" type="text" name="date" id="workdate">
                                </div>
                            </div>
                        </div>
                        <div class="form-group" id="stage">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="stage">学习日期:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix input-group col-xs-2" style="float:left;width:300px">
                                    <span class="input-group-addon">
                                    <i class="fa fa-calendar bigger-110"></i>
                                    </span>
                                    <input class="input-sm" style="width: 200px;" type="text" name="date" id="learndate">
                                </div>
                            </div>
                        </div>
                        <div class="form-group" id="stage">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="stage">上午课节数:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control" value="4" id="morningsessions" name="sessions" type="text">
                                </div>
                            </div>
                        </div>
                        <div class="form-group" id="stage">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="stage">下午课节数:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control" value="4" id="aftersessions" name="sessions" type="text">
                                </div>
                            </div>
                        </div>
                        <div class="form-group" id="stage">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="stage">晚上课节数:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control" value="0" id="eveningsessions" name="sessions" type="text">
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
<script src="/assets/lib/js/daterangepicker.min.js"></script>
<script type="text/javascript">
    jQuery(function ($) {

        $('input[name=date]').daterangepicker({
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
            showDropdowns:true
        })


        $('#validation-form').validate({
            errorElement: 'div',
            errorClass: 'help-block',
            focusInvalid: true,
            ignore: "",
            rules: {
                schoolid:{
                    required: true
                },
                name: {
                    required: true
                },
                sessions:{
                    required: true,
                    digits: true
                },
                date:{
                    required:true
                }
            },

            messages: {
                schoolid:{
                    required: "请选择所属学校"
                },
                name: {
                    required: "不能为空"
                },
                sessions: {
                    required: "不能为空",
                    digits: "请输入正整数"
                },
                date: {
                    required: "不能为空"
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
            var schoolName = $("#schoolid").find("option:selected").text();
            if(schoolName.indexOf("[学]")<0)
            {
                layer.msg("只有学校才可以添加学期数据",{icon:2});
                obj.removeClass("disabled");
                return;
            }
            var workDates = $("#workdate").val().split("--");
            var workStartDate = workDates[0];
            var workEndDate = workDates[1];
            var learningDates = $("#learndate").val().split("--");
            var learningStartDate = learningDates[0];
            var learningEndDate = learningDates[1];
            jQuery.ajax({
                type: "POST",
                data: {"schoolid":$("#schoolid").val(),"name":$("#name").val(),"schoolterm":$("#schoolterm").val(),"workstartdate": workStartDate,"workenddate": workEndDate,"learnstartdate":learningStartDate, "learnenddate":learningEndDate, "morningsessions":$("#morningsessions").val(),"afternoonsessions":$("#aftersessions").val(),"eveningsessions":$("#eveningsessions").val()},
                dataType: "text",
                url: "/manage/basic/yearterm/doAdd",
                success: function (result) {
                    if(result == 1) {
                        layer.msg("保存成功", {icon: 1, time: 1000}, function () {
                            parent.reloadData();
                            layer_close();
                        });
                    }
                    else if(result == 0)
                    {
                        layer.msg("当前学校的学年学期已经存在,不能重复添加",{icon:2});
                        obj.removeClass("disabled");
                    }
                },
                error:function () {
                    obj.removeClass("disabled");
                }
            });
        });

        $('#schoolid').chosen({
            allow_single_deselect:false,
            search_contains:true,
            no_results_text:"没有匹配数据:"
        });

        $("#schoolid").on("change", function () {
            var schoolName = $("#schoolid").find("option:selected").text();
            if(schoolName.indexOf("[学]")<0)
            {
                layer.msg("只有学校才可以添加学期数据",{icon:2});
                $("#schoolid").val("${schoolId}");
                $("#schoolid").trigger("chosen:updated");
            }
        });
    });

</script>
