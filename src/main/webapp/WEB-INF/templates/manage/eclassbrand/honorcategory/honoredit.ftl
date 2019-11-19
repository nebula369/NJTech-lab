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
                        <div class="form-group" id="stage">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="name">名称:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control" style="width: 300px;"  id="name" name="name" value="${honor.name}" type="text">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="picture">图片:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <img src="/${honor.picture}"  id="pictureimg" alt="图片"  height="60px"  onerror="javascript:this.src='/assets/lib/images/default/defaultpicture.png';"/>
                                    <input type="file" id="file" name="file"  />
                                    <input type="hidden" id="picture" name="picture"  value="${honor.picture}"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group" id="stage">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="sortnum">排序:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control" value="1" style="width: 300px;" id="sortnum" name="sortnum" value="${honor.sortnum}" type="text">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-offset-3 col-md-9" style="text-align: center">
                            <button class="btn btn-success btn-sm" type="button">
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
                sortnum:{
                    required: true,
                    digits: true
                }
            },
            messages: {
                name: {
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
             jQuery.ajax({
                type: "POST",
                data: {"pkid":${honor.pkid},"name":$("#name").val(),"sortnum":$("#sortnum").val(),"picture":$("#picture").val()},
                dataType: "text",
                url: "/manage/eclassbrand/honorcategory/honordoEdit",
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
        /*图片上传*/
        $("#validation-form #file").on("change", function () {
            var formData = new FormData();
            formData.append("file", $("#file")[0].files[0]);
            var filepath="upload/eclassbrand/honor";
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
