<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>
<link rel="stylesheet" href="/assets/lib/css/bootstrap-datepicker3.min.css" />

<div class="main-content">
    <div class="main-content-inner">
        <div class="page-content" style="padding:0;">
            <div class="row" style="margin:0;">
                <div class="col-xs-12" style="padding:0;">
                    <form class="form-horizontal" id="validation-form" role="form">
                        <table style="width:100%;height:100%;padding:10px;">
                            <tr>
                                <td style="width:75%;border-right:1px solid #b3b3b3;">
                                    <div style="padding-left:30px">
                                        <div style="height:50px;line-height:50px;font-weight:700;font-size:20px">默认场景模式设置</div>
                                        <div style="padding-left:20px">
                                            <input type="radio" id="cmode1" name="cmode" value="0"><span style="display:inline-block;height:40px;line-height:40px;" onclick="selectcmode('cmode1');">班牌模式</span>
                                            <div style="font-fize:12px;color:#b3b3b3">教室门口的电子班牌通常使用此项</div>
                                            <input type="radio" id="cmode2" name="cmode" value="1"><span style="display:inline-block;height:40px;line-height:40px;" onclick="selectcmode('cmode2');">资源模式</span>
                                            <div style="font-fize:12px;color:#b3b3b3">教室内的电脑、一体机可选用此项</div>
                                            <input type="radio" id="cmode3" name="cmode" value="2"><span style="display:inline-block;height:40px;line-height:40px;" onclick="selectcmode('cmode3');">网站模式</span>
                                            <div style="font-fize:12px;color:#b3b3b3">公共区域的触摸一体机可选用此项</div>
                                        </div>
                                        <div style="height:50px;line-height:50px;font-weight:700;font-size:20px">自启辅助模式设置</div>
                                        <div style="padding-left:20px;padding-bottom:20px">
                                            <input type="checkbox" id="ispatrol" name="zmode"><span style="display:inline-block;height:40px;line-height:40px;" onclick='document.getElementById("ispatrol").checked=!document.getElementById("ispatrol").checked;'>启用巡视模式</span>
                                            <div style="font-fize:12px;color:#b3b3b3">开启后，上课或考试时将自动切换至巡视模式</div>
                                            <input type="checkbox" id="isattendence" name="zmode"><span style="display:inline-block;height:40px;line-height:40px;" onclick='document.getElementById("isattendence").checked=!document.getElementById("isattendence").checked;'>启用考勤模式</span>
                                            <div style="font-fize:12px;color:#b3b3b3">开启后，课前10分钟将自动切换至考勤模式</div>
                                        </div>
                                    </div>
                                </td>
                                <td style="width:25%;text-align:center;">
                                    <div style="height:100%;width:100%;overflow-y:auto">
                                        <div style="font-size:16px;height:35px;line-height:35px">已选终端<span style="color:red">${terminalcount}</span>个</div>
                                        <#list terminallist as item>
                                            <div class="HidTxt" style="height:25px;line-height:25px">${item.name}</div>
                                        </#list>
                                    </div>
                                </td>
                            </tr>
                        </table>
                        <div class="col-md-offset-3 col-md-9" style="text-align:center;padding:10px;border-top:1px solid #b3b3b3">
                            <button class="btn btn-success btn-sm" type="button">
                                <i class="ace-icon fa fa-check"></i>
                                确 定
                            </button>
                            &nbsp; &nbsp;
                            <button class="btn btn-sm" type="reset" onclick="layer_close();">
                                <i class="ace-icon fa fa-undo"></i>
                                取消
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
            },

            messages: {
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
            if($("input[name='cmode']:checked").val()==undefined){
                layer.alert("请选择场景模式");
                obj.removeClass("disabled");
                return;;
            }
            jQuery.ajax({
                type: "POST",
                data: {"ids":"${terminalids}","mode":$("input[name='cmode']:checked").val(),"isAttendence":document.getElementById("isattendence").checked,"isPatrol":document.getElementById("ispatrol").checked},
                dataType: "text",
                url: "/manage/eclassbrand/mode/doEdit",
                success: function (result) {
                    if(result == 1) {
                        layer.msg("保存成功", {icon: 1, time: 1000}, function () {
                            parent.initDataList();
                            layer_close();
                        });
                    } else {
                        layer.msg("保存失败", {icon: 1, time: 1000}, function () {
                            layer_close();
                        });
                    }
                },
                error:function () {
                    obj.removeClass("disabled");
                }
            });
        });
    });

    function selectcmode(id) {
        $('#cmode1').removeAttr("checked");
        $('#cmode2').removeAttr("checked");
        $('#cmode3').removeAttr("checked");
        $("#"+ id +"").attr("checked", "checked");
        $("input[id='"+ id +"']").eq(0).click();
    }

    function selectTerminal(id) {
        document.getElementById("chk"+ id +"").checked=!document.getElementById("chk"+ id +"").checked;
    }
</script>
