<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>

<div class="main-content">
    <div class="main-content-inner">
        <div class="page-content">
            <div class="row">
                <div class="col-xs-12">
                    <form class="form-horizontal" id="validation-form" role="form">

                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="type">单位类型:</label>
                            <div class="col-xs-12 col-sm-9" style="float:left;width: 400px;margin-top: 5px;">
                                <#list unitType as item>
                                    <div style="float: left;margin-right: 5px;">
                                        <label class="line-height-1">
                                            <input name="type" disabled <#if item.code==unit.type?string>checked</#if> value="${item.code}" type="radio" class="ace">
                                            <span class="lbl">${item.name}</span>
                                        </label>
                                    </div>
                                </#list>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="parentid">所属上级单位:</label>
                            <div class="col-sm-9" style="float: left;">
                                <div class="clearfix" style="float:left;">
                                    <select class="chosen-select form-control" name="parentid" id="parentid" style="width: 200px;" data-placeholder="--请选择上级单位--">
                                        <option value=""></option>
                                        <#list unitList as item>
                                            <option value="${item.pkid}" <#if item.pkid==unit.parentid>selected</#if> >${item.name}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="name">名称:</label>
                            <div class="col-sm-9" style="float: left;">
                                <div class="clearfix" style="float:left;">
                                    <input type="text" name="name" value="${unit.name}" id="name" style="width: 200px;" class="col-xs-12 col-sm-6">
                                </div>
                            </div>
                        </div>
                        <div class="form-group" id="stage">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="stage">所属学段:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <#list stageList as item>
                                         <div style="float: left;margin-right: 5px;">
                                             <label class="line-height-1">
                                                 <input name="stageid" <#if (unit.stageids?split(","))?seq_contains(item.code)>checked</#if> value="${item.code}" type="checkbox" class="ace ace-checkbox-2">
                                                 <span class="lbl">${item.name}</span>
                                             </label>
                                         </div>
                                    </#list>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="stage">单位管理员:</label>
                            <div class="col-sm-9" style="float: left; ">
                                <div class="clearfix">
                                    <input type="text" name="manageUserName" value="${(user.name)!""}" id="manageUserName" style="width: 120px;" class="col-xs-12 col-sm-6">
                                    &nbsp;<a onclick="selectUser();return false;" href="#">设置单位管理员</a>
                                    <a onclick="cancelManageUser();return false;" href="#">取消单位管理员</a>
                                    <input type="hidden" name="manageuser" value="${(user.pkid)!0}" id="manageuser"/>
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
                name: {
                    required: true
                },
                parentid:{
                    required: true
                },
                stageid:{
                    required:true
                }
            },

            messages: {
                name: {
                    required: "不能为空"
                },
                parentid: {
                    required: "必须选择上级单位"
                },
                stageid: {
                    required: "必须选择一个"
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
            var type = $('input:radio[name=type]:checked').val();
            var stageIds = getStageIds();
            if(type !=1)
            {
                stageIds = "";
            }
            jQuery.ajax({
                type: "POST",
                data: {"pkid":${unit.pkid},"name":$("#name").val(),"parentid":$("#parentid").val(),"type":type,"stageids":stageIds,"manageuser":$("#manageuser").val()},
                dataType: "text",
                url: "/manage/basic/unit/doEdit",
                success: function (result) {
                    if(result == 1) {
                        layer.msg("保存成功", {icon: 1, time: 1000}, function () {
                            parent.reloadData();
                            layer_close();
                        });
                    }
                    else if(result == 0)
                    {
                        layer.msg("当前设置的单位管理员已经设置为其他单位的单位管理员，不能重复设置",{icon:2});
                        obj.removeClass("disabled");
                    }
                    else
                    {
                        layer.msg("保存失败",{icon:2});
                        obj.removeClass("disabled");
                    }
                },
                error:function () {
                    obj.removeClass("disabled");
                }
            });
        });

        $('#parentid').chosen({
            allow_single_deselect:false,
            search_contains:true,
            no_results_text:"没有匹配数据:"
        });

        function initStage()
        {
            var type = $('input:radio[name=type]:checked').val();
            if(type == 1)
            {
                $("#stage").show();
            }
            else {
                $("#stage").hide();
                $("input:checkbox[name=stageid]").prop("checked", "checked");
            }
        }
        initStage();

        function getStageIds()
        {
            var result = [];
            $("input:checkbox[name=stageid]").each(function () {
                if(this.checked)
                {
                    result.push($(this).val())
                }
            });
            return result.join(",");
        }
    });

    function selectUser()
    {
        parent.layer_show("设置单位管理员", "/manage/basic/common/selectUser?type=0&isSingle=1", 900, 600);
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
