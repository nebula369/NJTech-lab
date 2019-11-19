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
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="usergroup">考勤组:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <select multiple="" style="width: 300px;" class="chosen-select form-control tag-input-style" name="usergroup" id="usergroup" data-placeholder="选择考勤组">

                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group" id="stage">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="subject">学科:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control"  id="subject" name="subject" type="text" style="width: 300px;">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="stage">教师:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input type="text" name="teachername"  id="teachername" style="width: 120px;" class="col-xs-12 col-sm-6" >
                                    &nbsp;<a   onclick="selectUser();return false;"  href="#" style="vertical-align:-webkit-baseline-middle;">选择教师</a>
                                    <a onclick="cancelManageUser();return false;"   href="#" style="vertical-align:-webkit-baseline-middle;">取消教师</a>
                                    <input type="hidden" name="teacherid"  id="teacherid"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-offset-3 col-md-9" style="text-align: center;margin-top:50px;">
                            <button class="btn btn-success btn-sm" type="button" style="margin-left: 20px">
                                <i class="ace-icon fa fa-check"></i>
                                确 定
                            </button>
                            <button class="btn btn-sm" type="reset" style="margin-left: 20px">
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
        $('.tag-input-style').chosen({
            search_contains:true,
            no_results_text:"没有匹配数据:"
        });
        initUserGroupList();
        function initUserGroupList()
        {
            jQuery.ajax({
                type: "POST",
                data: {},
                dataType: "json",
                url: "/manage/kaoqin/usergroup/getUserGroupList",
                success: function (result) {
                    var html = '';
                    for(var i=0;i<result.length;i++){
                        html += '<option value="' + result[i].pkid + '">' + result[i].groupname + '</option>';
                    }
                    $('#usergroup').html(html);
                    $("#usergroup").trigger("chosen:updated");
                },
                error:function () {
                }
            });
        }
        $('#validation-form').validate({
            errorElement: 'div',
            errorClass: 'help-block',
            focusInvalid: true,
            ignore: "",
            rules: {
                subject:{
                    required: true
                },
                usergroup: {
                    required: true
                },
                teachername: {
                    required: true
                }

            },
            messages: {
                subject:{
                    required: "不能为空"
                },
                usergroup: {
                    required: "不能为空"
                },
                teachername: {
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
            var usergroup = $("#usergroup").val().toString();
            var spaceId=parent.$("#spaceId").val();
            var weekorder=parent.$("#weekTypeId").val();
            /*var begintime=  parent.currbegintime;
            var endtime= parent.currendtime;
            var weekno=parent.currweek;*/
           //alert(begintime+"|"+endtime+"|"+weekno+"|"+spaceId+"|"+weekorder);
            jQuery.ajax({
                type: "POST",
                data: {
                     "usergroupidstr":usergroup,"subject":$("#subject").val(),
                     "begintime":parent.currbegintime,"endtime":parent.currendtime,
                     "weekorder":weekorder, "weekno":parent.currweek,
                     "schoolid":0,"spaceid":spaceId, "teacherid":$("#teacherid").val()
                   },
                dataType: "text",
                url: "/manage/kaoqin/planweek/doAdd",
                success: function (result) {
                    if(result == 1) {
                        layer.msg("保存成功", {icon: 1, time: 1000}, function () {
                            parent.reloadData();
                            layer_close();
                        });
                    }
                    else
                    {
                        layer.msg("保存错误",{icon:2});
                        obj.removeClass("disabled");
                    }
                },
                error:function () {
                    obj.removeClass("disabled");
                }
            });
        });

    });
    function selectUser()
    {
        parent.layer_show("选择教师", "/manage/basic/common/selectUser?type=0&isSingle=1", 900, 600);
        event.stopPropagation();
    }
    function setSelectedUser(selectUsers)
    {
        var users = selectUsers.split("@");
        $("#teachername").val(users[1]);
        $("#teacherid").val(users[0]);
    }
    function cancelManageUser()
    {
       // $('#teachername').removeAttr("disabled");
        $("#teachername").val("");
        $("#teacherid").val("0");
      //  $("#teachername").attr("disabled","disabled");
    }

</script>
