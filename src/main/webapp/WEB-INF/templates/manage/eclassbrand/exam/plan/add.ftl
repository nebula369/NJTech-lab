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
                      <table style="width: 100%">
                          <tr>
                              <td>
                                  <div class="form-group" id="stage">
                                      <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="searchDate">考试时间:</label>
                                      <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                          <div class="clearfix" style="float:left;">
                                              <input class="input-sm" style="width: 255px;height:34px;" type="text" name="date-range-picker" id="searchDate"  >
                                          </div>
                                      </div>
                                  </div>
                              </td>
                              <td>
                                  <div class="form-group" id="stage">
                                      <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="subject">学    科:</label>
                                      <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                          <div class="clearfix" style="float:left;">
                                              <input class="form-control" value="" id="subject" name="subject" type="text"  >
                                          </div>
                                      </div>
                                  </div>
                              </td>
                          </tr>
                          <tr>
                              <td>
                                  <div class="form-group" id="stage">
                                      <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="grade">专业年级:</label>
                                      <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                          <div class="clearfix" style="float:left;">
                                              <input class="form-control" value="" id="grade" name="grade" type="text" style="width: 255px;height:34px;">
                                          </div>
                                      </div>
                                  </div></td>
                              <td>   <div class="form-group" id="stage">
                                      <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="grade">监考老师:</label>
                                      <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                          <div class="clearfix" style="float:left;">
                                              <input class="form-control" value="" id="examiner" name="examiner" type="text">
                                          </div>
                                      </div>
                                  </div></td>
                          </tr>
                          <tr>
                              <td>  <div class="form-group" id="stage">
                                      <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="ticket">准考证<br/>起止号码:</label>
                                      <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                          <div class="clearfix" style="float:left;">
                                              <input class="form-control" value="" id="ticket" name="ticket" type="text" style="width: 255px;height:34px;">
                                          </div>
                                      </div>
                                  </div></td>
                              <td>       <div class="form-group" id="stage">
                                      <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="stage">排序号:</label>
                                      <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                          <div class="clearfix" style="float:left;">
                                              <input class="form-control" value="1" id="sortnum" name="sortnum" type="text">
                                          </div>
                                      </div>
                                  </div></td>
                          </tr>
                      </table>
                        <div class="col-md-offset-3 col-md-9" style="text-align: center;margin: 60px;">
                            <button class="btn btn-success btn-sm" type="button"  id="btnOk"  style="margin-right: 50px;">
                                <i class="ace-icon fa fa-check"></i>
                                确 定
                            </button>
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
        $('#validation-form').validate({
            errorElement: 'div',
            errorClass: 'help-block',
            focusInvalid: true,
            ignore: "",
            rules: {
                searchDate:{
                    required: true
                },
                subject: {
                    required: true
                },
                examiner: {
                    required: true
                },
                grade:{
                    required: true
                }
            },

            messages: {
                searchDate:{
                    required: "请选择考试时间"
                },
                subject: {
                    required: "不能为空"
                },
                examiner: {
                    required: "不能为空"
                },
                grade: {
                    required: "不能为空",
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
        $("#validation-form #btnOk").on("click", function () {
            if(!$('#validation-form').valid())
            {
                event.preventDefault();
                return;
            }
            if($(this).hasClass("disabled")) return;
            $(this).addClass("disabled");
            var obj = $(this);
            jQuery.ajax({
                type: "POST",
                data: {"examroomid":${examroomid},"subject":$("#subject").val(),"grade":$("#grade").val(),"examiner":$("#examiner").val(),"ticket":$("#ticket").val(),"sortnum":$("#sortnum").val(),"startDate":$("#searchDate").val().split('至')[0]+":00","endDate":$("#searchDate").val().split('至')[1]+":00"},
                dataType: "text",
                url: "/manage/eclassbrand/examplan/doAdd",
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

        $('input[name=date-range-picker]').daterangepicker({
            timePicker: true, //显示时间
            timePicker24Hour: true, //时间制
            //timePickerSeconds: true, //时间显示到秒
            startDate: new Date(),
            endDate: new Date(),
            minDate:1999-12-12,
            maxDate:2050-12-30,
            timePickerIncrement: 1,
            locale:{
                applyLabel: "确认",
                cancelLabel: "取消",
                separator: "至",
                format: 'YYYY-MM-DD HH:mm',
                daysOfWeek: ["日","一","二","三","四","五","六"],
                monthNames: ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"]
            }
        }, function (start, end, label) {
            //console.log('选择日期范围: ' + start.format('YYYY-MM-DD HH:mm:ss') + ' 到 ' + end.format('YYYY-MM-DD HH:mm:ss'));
        });
    });

</script>
