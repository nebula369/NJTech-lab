<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>
<link rel="stylesheet" href="/assets/lib/js/ueditor/themes/default/css/ueditor.css">
<link rel="stylesheet" href="/assets/lib/js/layui/css/layui.css" />
<link rel="stylesheet" href="/assets/lib/css/cropper.css" />
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
                                        <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="classid">所属班级:</label>
                                        <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                            <div class="clearfix" style="float:left;">
                                                <select multiple=""  style="width: 300px;" class="chosen-select form-control tag-input-style" name="classid" id="classid" data-placeholder="选择班级">
                                                    <#list classList as item>
                                                        <option value="${item.pkid}">${item.name}</option>
                                                    </#list>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="form-group" id="stage">
                                        <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="name">名称:</label>
                                        <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                            <div class="clearfix" style="float:left;">
                                                <input class="form-control" style="width: 300px;"  id="name" name="name" type="text">
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="form-group" id="stage">
                                        <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="buyurl">购买网址:</label>
                                        <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                            <div class="clearfix" style="float:left;">
                                                <input class="form-control" style="width: 300px;"  id="buyurl" name="buyurl" type="text">
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="form-group" id="stage">
                                        <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="sortnum">排序:</label>
                                        <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                            <div class="clearfix" style="float:left;">
                                                <input class="form-control" value="1" style="width: 300px;" id="sortnum" name="sortnum" type="text">
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="form-group" id="stage">
                                        <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="reason">推荐理由:</label>
                                        <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                            <div class="clearfix" style="float:left;">
                                                <textarea  class="form-control" value="" id="reason" name="reason"   cols="40"   rows="4"   style="overflow: hidden; margin: 0px; width: 300px; height: 95px;resize: none;"> </textarea>
                                            </div>
                                        </div>
                                    </div>

                                </td>
                                <td>
                                    <div class="form-group" >
                                        <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="picture">图片:</label>
                                        <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                            <div class="clearfix" style="float:left;">
                                                <img src=""  id="pictureimg" alt="图片"  style=" width:75px; height:60px"   onerror="javascript:this.src='/assets/lib/images/default/defaultpicture.png';" />
                                                <input type="file" id="file" name="file"  />
                                                <input type="hidden" id="picture" name="picture" />
                                            </div>
                                        </div>
                                    </div>
                                  <#--  <div class="layui-form-item form-group">
                                        <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="picture">图片:</label>
                                        <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                            <div class="clearfix" style="float:left;">
                                                <div class="layui-input-inline" style="width: 100px">
                                                    <div class="layui-upload-list" style="margin:0">
                                                        <img src="123.jpg"  id="srcimgurl"  style=" width: 100%; height: 100%;" class="layui-upload-img" onerror="javascript:this.src='/assets/lib/images/default/defaultpicture.png';" />
                                                    </div>
                                                </div>
                                                <div class="layui-input-inline layui-btn-container" style="width: auto;">
                                                    <button class="layui-btn layui-btn-sm" id="editimg" style="margin-top: 20px">上传图片</button >
                                                </div>
                                                <div class="layui-input-inline" style="display: none">
                                                    <input type="text" name="head" lay-verify="required" id="picture" placeholder="图片地址" class="layui-input">
                                                </div>
                                            </div>
                                        </div>
                                    </div>-->
                                </td>
                            </tr>
                        </table>
                        <div class="form-group" id="stage">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="detail">推荐详情:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <textarea id="detail" style="min-height:350px" name="detail"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-offset-3 col-md-9" style="margin-left: 90px; text-align: center">
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
<script type="text/javascript" src="/assets/lib/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="/assets/lib/js/ueditor/ueditor.all.js"></script>
<script src="/assets/lib/js/layui/layui.js"></script>
<script src="/assets/lib/js/cropper/cropper.js"></script>
<script src="/assets/lib/js/cropper/croppers.js"></script>


<script type="text/javascript">
    jQuery(function ($) {
/*
        layui.config({
            base: '/assets/lib/js/cropper/' //layui自定义layui组件目录
        }).use(['form','croppers'], function () {
            var $ = layui.jquery
                ,form = layui.form
                ,croppers = layui.croppers
                ,layer= layui.layer;

            //创建一个头像上传组件
            croppers.render({
                elem: '#editimg'
                ,saveW:150     //保存宽度
                ,saveH:150
                ,mark:16/9    //选取比例
                ,title:"上传图片" //页面标题
                ,area:['900px', '550px']  //弹出层页面比例
                ,url:'/service/upload/newuploadsinglepic'  //图片上传接口返回和（layui 的upload 模块）返回的JOSN一样
                ,filepath:"upload/eclassbrand/recommend/"
                ,info:"头像的尺寸限定150x150px,大小在50kb以内"
                ,done: function(url){ //上传完毕回调

                }
            });

        });
*/

        $('#validation-form').validate({
            errorElement: 'div',
            errorClass: 'help-block',
            focusInvalid: true,
            ignore: "",
            rules: {
                classid: {
                    required: true
                },
                name: {
                    required: true
                },
                detail: {
                    required: true
                },
                sortnum:{
                    required: true,
                    digits: true
                }
            },
            messages: {
                classid: {
                    required: "不能为空"
                },
                name: {
                    required: "不能为空"
                },detail: {
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
                data: {"name":$("#name").val(),"picture":$("#picture").val(),"detail":ue.getContent(),"reason":$("#reason").val(),"buyurl":$("#buyurl").val(),"sortnum":$("#sortnum").val(),"classidstr":""+classid+""},
                dataType: "text",
                url: "/manage/eclassbrand/recommend/doAdd",
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
             var filepath="upload/eclassbrand/recommend/";
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
    var ue= UE.getEditor("detail");
   //图片显示
   /* function InitPic(url)
    {
        $("#picture").val(url);
        $("#srcimgurl").attr('src',"/"+url);
    }*/
</script>
