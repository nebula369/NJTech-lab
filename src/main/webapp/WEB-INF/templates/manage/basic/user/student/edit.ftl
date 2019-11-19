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
                                    <select class="chosen-select form-control" name="unitId" id="unitId" style="width: 200px;" data-placeholder="--请选择所属学校--">
                                        <option value=""></option>
                                        <#list unitList as item>
                                            <option value="${item.pkid}" <#if unitId==item.pkid>selected</#if>>${item.name}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="loginName">帐号:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control" value="${user.loginname}" id="loginName" name="loginName" type="text">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="name">姓名:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control" value="${user.name}" id="name" name="name" type="text">
                                </div>
                            </div>
                        </div>
                        <div class="form-group" id="stage">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="sex">性别:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <select class="chosen-select form-control" name="sex" id="sex" style="width: 100px;">
                                        <#list sexList as item>
                                            <option value="${item.code}" <#if user.sex?string == item.code>selected</#if>>${item.name}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="grade">年级班级:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <select style="width: 150px;" class="chosen-select form-control tag-input-style" name="grade" id="grade" data-placeholder="选择年级">

                                    </select>
                                    <select style="width: 150px;" class="chosen-select form-control tag-input-style" name="classId" id="classId" data-placeholder="选择班级">

                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="mobile">学号:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control" value="${student.stuid}" id="stuId" name="stuId" type="text">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="mobile">学籍号:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control" value="${student.stunum}" id="stuNum" name="stuNum" type="text">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="mobile">手机号码:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control" value="${user.mobile}" id="mobile" name="mobile" type="text">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="email">电子邮箱:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control" value="${user.email}" id="email" name="email" type="text">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="pic">真实照片:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <img src="/${userPhoto.photo}"  id="headimg" alt="头像" width="80px" height="80px"  onerror="javascript:this.src='/assets/lib/images/avatars/avatar2.png';">
                                    <input type="file" id="file" name="file" value="${userPhoto.photo}" />
                                    <input type="hidden" id="pic" name="pic"  value="${userPhoto.photo}"/>
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
                unitId:{
                    required: true
                },
                name: {
                    required: true
                },
                loginname: {
                    required: true
                },
                password: {
                    required: true
                },
                grade: {
                    required: true
                },
                classId: {
                    required: true
                }
            },

            messages: {
                unitId:{
                    required: "请选择所属单位"
                },
                name: {
                    required: "不能为空"
                },
                loginname: {
                    required: "不能为空"
                },
                password: {
                    required: "不能为空"
                },
                grade: {
                    required: "请选择年级班级"
                },
                classId: {
                    required: "请选择年级班级"
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
            var stageGradeIds = $("#grade").val().split("_");
            var stageId = stageGradeIds[0];
            var gradeId = stageGradeIds[1];
            jQuery.ajax({
                type: "POST",
                data: {"pkid":${user.pkid},"name":$("#name").val(),"loginname":$("#loginName").val(),"password":$("#password").val(),
                "mobile":$("#mobile").val(),"email":$("#email").val(),"sex":$("#sex").val(),"unitId":$("#unitId").val(),"stageId":""+stageId+"","gradeId":""+gradeId+"","classId":""+$("#classId").val()+"",
                "stuId":""+$("#stuId").val()+"","stuNum":""+$("#stuNum").val()+"","photoRealistic":""+$("#pic").val()+""},
                dataType: "text",
                url: "/manage/basic/student/doEdit",
                success: function (result) {
                    if(result == 1) {
                        layer.msg("保存成功", {icon: 1, time: 1000}, function () {
                            parent.reloadData();
                            layer_close();
                        });
                    }
                    else if(result == 0)
                    {
                        layer.msg("当前学生帐号不存在",{icon:2});
                        obj.removeClass("disabled");
                    }
                },
                error:function () {
                    obj.removeClass("disabled");
                }
            });
        });

        $('#unitId').chosen({
            allow_single_deselect:false,
            search_contains:true,
            no_results_text:"没有匹配数据:"
        });

        $('.tag-input-style').chosen({
            search_contains:true,
            no_results_text:"没有匹配数据:"
        });

        $("#unitId").on("change", function () {
            initGradeList();
        });

        $("#grade").on("change", function () {
            initClassList();
        });

        initGradeList();

        function initGradeList()
        {
            var unitId = $("#unitId").val();
            if(unitId=="")unitId=0;
            jQuery.ajax({
                type: "POST",
                data: {"unitId":unitId},
                dataType: "json",
                url: "/manage/basic/teacher/getGradeList",
                success: function (result) {
                    var html = '';
                    for(var i=0;i<result.length;i++){
                        if("${stageId}" == result[i].valcode && "${gradeId}" == result[i].code)
                        {
                            html +='<option value="' + result[i].valcode +"_"+result[i].code+'" selected>'+result[i].name+'</option>';
                        }
                        else
                        {
                            html +='<option value="' + result[i].valcode +"_"+result[i].code+'">'+result[i].name+'</option>';
                        }
                    }
                    $('#grade').html(html);
                    $("#grade").trigger("chosen:updated");
                    initClassList();
                },
                error:function () {

                }
            });
        }

        function initClassList()
        {
            var unitId = $("#unitId").val();
            if(unitId=="")unitId=0;
            var stageGradeIds = $("#grade").val().split("_");
            var stageId = stageGradeIds[0];
            var gradeId = stageGradeIds[1];
            jQuery.ajax({
                type: "POST",
                data: {"schoolId":unitId,"stageId":stageId, "gradeId":gradeId},
                dataType: "json",
                url: "/manage/basic/student/getSchoolClassList",
                success: function (result) {
                    var html = '';
                    for(var i=0;i<result.length;i++){
                        if("${classId}" == result[i].pkid)
                        {
                            html +='<option value="' + result[i].pkid+'" selected>'+result[i].name+'</option>';
                        }
                        else
                        {
                            html +='<option value="' + result[i].pkid+'">'+result[i].name+'</option>';
                        }
                    }
                    $('#classId').html(html);
                    $("#classId").trigger("chosen:updated");
                },
                error:function () {

                }
            });
        }
        /*头像上传*/
        $("#validation-form #file").on("change", function () {
            var formData = new FormData();
            formData.append("file", $("#file")[0].files[0]);
            var filepath="upload/headimg/";
            formData.append("filepath",filepath) ;
            $.ajax({
                type : 'POST',
                url : '/service/upload/uploadsinglepic',
                data : formData,
                cache : false,
                processData : false,
                contentType : false,
            }).success(function(data) {
                var result= JSON.parse(data);
                $("#pic").val(result.url);
                $("#headimg").attr("src","/"+result.url);
                layer.msg("头像上传成功!",{icon:1});
            }).error(function() {
                layer.msg("头像上传失败!",{icon:2});
            });
        })
    });

</script>
