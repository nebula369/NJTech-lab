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
                        <table style="width:100%">
                            <tr>
                                <td style="width:50%">
                                    <div class="form-group">
                                        <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="type">类型:</label>
                                        <div class="col-xs-12 col-sm-9" style="float:left;width: 400px;margin-top: 5px;">
                                            <div style="float: left;margin-right: 5px;">
                                                <label class="line-height-1">
                                                    <input name="type" id="type" checked  value="1" type="radio" class="ace">
                                                    <span class="lbl" onclick="$('#chooseclass').hide();$('#txtType').val('1');">学校活动</span>
                                                </label>
                                            </div>
                                            <div style="float: left;margin-right: 5px;">
                                                <label class="line-height-1">
                                                    <input name="type" id="type" checked  value="2" type="radio" class="ace">
                                                    <span class="lbl" onclick="$('#chooseclass').show();$('#txtType').val('0');">班级活动</span>
                                                </label>
                                            </div>
                                            <input type="text" style="display: none;" id="txtType" value="1">
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="form-group" id="chooseclass">
                                        <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="classid">所属班级:</label>
                                        <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                            <div class="clearfix" style="float:left;">
                                                <select multiple=""  style="width: 200px;" class="chosen-select form-control tag-input-style" name="classid" id="classid" data-placeholder="选择班级">
                                                    <#list classList as item>
                                                        <option value="${item.pkid}">${item.name}</option>
                                                    </#list>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="form-group" id="stage">
                                        <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="stage">标题:</label>
                                        <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                            <div class="clearfix" style="float:left;;width:200px">
                                                <input class="form-control" value="" id="title" name="title" type="text">
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="form-group" id="stage">
                                        <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="stage">创建人:</label>
                                        <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                            <div class="clearfix" style="float:left;;width:200px">
                                                <input class="form-control" value="" id="author" name="author" type="text">
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="form-group" id="stage">
                                        <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="stage">活动介绍:</label>
                                        <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                            <div class="clearfix" style="float:left;;width:200px">
                                                <input class="form-control" value="" id="intro" name="intro" type="text">
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="form-group" id="stage">
                                        <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="stage">起止时间:</label>
                                        <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                            <div class="clearfix" style="float:left;">
                                    <span class="input-group col-xs-2" style="float: left">
                                        <input class="input-sm" style="width: 200px;height:34px;" type="text" name="date-range-picker" id="searchDate">
                                    </span>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="form-group" id="stage">
                                        <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="stage">排序号:</label>
                                        <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                            <div class="clearfix" style="float:left;width:200px">
                                                <input class="form-control" value="1" id="sortnum" name="sortnum" type="text">
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="form-group">
                                        <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="picture">封面:</label>
                                        <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                            <div class="clearfix" style="float:left;">
                                                <img src=""  id="pictureimg" alt="头像" width="80px" height="80px"   onerror="javascript:this.src='/assets/lib/images/avatars/avatar2.png';">
                                                <input type="file" id="file" name="file"  />
                                                <input type="hidden" id="picture" name="picture" />
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </table>
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

        $('input[name=date-range-picker]').daterangepicker({
            'applyClass' : 'btn-sm btn-success',
            'cancelClass' : 'btn-sm btn-default',
            locale: {
                applyLabel: '确定',
                cancelLabel: '取消',
                format: "YYYY-MM-DD",
                separator: "至",
                daysOfWeek: ["日","一","二","三","四","五","六"],
                monthNames: ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"]
            },
            showDropdowns:true,
            startDate:'2018-01-01',
            endDate:moment()
        });

        $('#validation-form').validate({
            errorElement: 'div',
            errorClass: 'help-block',
            focusInvalid: true,
            ignore: "",
            rules: {
                classid:{
                    required:     function () {
                        return $('#txtType').val() == "0";
                    }
                },
                title: {
                    required: true
                },
                author: {
                    required: true
                },
                sortnum:{
                    required: true,
                    digits: true
                }
            },

            messages: {
                classid:{
                    required: "请选择班级"
                },
                title: {
                    required: "不能为空"
                },
                author: {
                    required: "不能为空"
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
            var classid=$("#classid").val().join(",");
            jQuery.ajax({
                type: "POST",
                data: {"activityType":$('#txtType').val(),"placeId":classid,"intro":$("#intro").val(),"title":$("#title").val(),"author":$("#author").val(),"starttime":$("#searchDate").val().split('至')[0],"endtime":$("#searchDate").val().split('至')[1],"sortnum":$("#sortnum").val(),"content":"","photo":$("#picture").val()},
                dataType: "text",
                url: "/manage/eclassbrand/activity/doAdd",
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

        $('.tag-input-style').chosen({
            search_contains:true,
            no_results_text:"没有匹配数据:"
        });

        /*图片上传*/
        $("#validation-form #file").on("change", function () {
            var formData = new FormData();
            formData.append("file", $("#file")[0].files[0]);
            var filepath="upload/eclassbrand/news";
            formData.append("filepath",filepath) ;
            $.ajax({
                type : 'POST',
                url : '/service/upload/uploadsinglepic',
                data : formData,
                // data: {"file":formData,"filepath":filepath},
                cache : false,
                processData : false,
                contentType : false,
            }).success(function(data) {
                var result= JSON.parse(data);
                $("#picture").val(result.url);
                $("#pictureimg").attr("src","/"+result.url);
                layer.msg("图片上传成功!",{icon:1});
            }).error(function() {
                layer.msg("图片上传失败!",{icon:2});
            });
        })
    });


</script>
