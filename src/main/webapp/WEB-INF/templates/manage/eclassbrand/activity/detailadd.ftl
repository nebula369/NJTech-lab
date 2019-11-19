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
                                <td>
                                    <div class="form-group" id="stage">
                                        <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="stage">描述:</label>
                                        <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                            <div class="clearfix" style="float:left;;width:200px">
                                                <input class="form-control" value="" id="title" name="title" type="text">
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="form-group">
                                        <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="picture">封面:</label>
                                        <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                            <div class="clearfix" style="float:left;">
                                                <img src=""  id="pictureimg" alt="头像"  height="60px"   onerror="javascript:this.src='/assets/lib/images/default/defaultpicture.png';">
                                                <input type="file" id="file" name="file"  />
                                                <input type="hidden" id="picture" name="picture" />
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td></td>
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
<script type="text/javascript">
    jQuery(function ($) {


        $('#validation-form').validate({
            errorElement: 'div',
            errorClass: 'help-block',
            focusInvalid: true,
            ignore: "",
            rules: {
                title: {
                    required: true
                }
            },

            messages: {
                title: {
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
            jQuery.ajax({
                type: "POST",
                data: {"activityid":${activityid},"describe":$("#title").val(),"photo":$("#picture").val()},
                dataType: "text",
                url: "/manage/eclassbrand/activity/doDetailAdd",
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
            var filepath="upload/eclassbrand/activity";
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
    var ue= UE.getEditor("container");


</script>
