<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>
<link rel="stylesheet" href="/assets/lib/css/bootstrap-datepicker3.min.css" />

<div class="main-content">
    <div class="main-content-inner">
        <div class="page-content">
            <div class="row">
                <div class="col-xs-12">
                    <form class="form form-horizontal" id="form-article-add" enctype="multipart/form-data">
                        <div class="form-group" id="stage">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="searchDate">上传文件:</label>
                            <div class="col-sm-9" style="float: left; ">
                                <div class="clearfix" style="float:left;">
                                    <input type="text" id="file_name" readonly="readonly" value=""  />
                                    <a href="javascript:void(0);" class="input" style="color:#FFF;">
                                        浏览
                                        <input type="file" id="file" name="file">
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div class="form-group" id="stage">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="searchDate">注意事项:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <span>
                                        1、考试开始日期时间、考试结束日期为日期类型<br/>格式：yyyy-mm-dd hh:mm:ss<br/>
                                        2、人数为正数<br/>
                                        3、请按照模板导入&nbsp;&nbsp; <a href="/service/fileDownload?fileName=考试计划模板.xlsx">下载模板</a>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-offset-3 col-md-9" style="text-align: center;">
                            <button class="btn btn-info btn-sm" type="button"  onClick="importExamPlan();"> 导入数据</button>
                            <button class="btn btn-sm"  onclick="layer_close();"> 取消 </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<style>
    #file_name{
        height: 30px;
    }
    a.input {
        width:70px;
        height:30px;
        line-height:30px;
        background:#3091d1;
        text-align:center;
        display:inline-block;/*具有行内元素的视觉，块级元素的属性 宽高*/
        overflow:hidden;/*去掉的话，输入框也可以点击*/
        position:relative;/*相对定位，为 #file 的绝对定位准备*/
        top:10px;
    }
    a.input:hover {
        background:#31b0d5;
        color: #ffffff;
    }
    a{
        text-decoration:none;

    }
    #file {
        opacity:0;/*设置此控件透明度为零，即完全透明*/
        filter:alpha(opacity=0);/*设置此控件透明度为零，即完全透明针对IE*/
        font-size:100px;
        position:absolute;/*绝对定位，相对于 .input */
        top:0;
        right:0;
    }
</style>
<#include "/controls/appbottom.ftl"/>
<script src="/assets/lib/js/moment.min.js"></script>
<script type="text/javascript">
    $(function(){
        $("#file").change(function(){  // 当 id 为 file 的对象发生变化时
            var fileSize = this.files[0].size;
            var size = fileSize / 1024 / 1024;
            if (size > 5) {
                alert("附件不能大于5M,请将文件压缩后重新上传！");
                this.value="";
                return false;
            }else{
                $("#file_name").val($("#file").val());  //将 #file 的值赋给 #file_name
            }
        })
    });
    /*导入数据*/
    function importExamPlan(){
        var fileName=$("#file").val();
        if(fileName == '') {
            layer.msg("请选择文件!",{icon:2});
            return false;
        }
        //验证文件格式
        var fileType = (fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length)).toLowerCase();
        if (fileType != 'xlsx') {
            layer.msg("文件格式不正确!",{icon:2});
            return false;
        }
        var index = layer.load(1, {
            shade: [0.5,'#000'] //0.1透明度的背景
        });
        var formData = new FormData();
        formData.append("file", $("#file")[0].files[0]);
        formData.append("examroomId",${examroomId}) ;
        $.ajax({
            type : 'POST',
            url : '/manage/eclassbrand/examplan/importExamPlan',
            data : formData,
            cache : false,
            async : false,
            processData : false,
            contentType : false,
        }).success(function(data) {
            if(data == "error"){
                layer.close(index);
                layer.msg("文件导入失败，请重新上传！", {
                    shade: [0.3, '#393D49'], // 透明度  颜色
                    time:5000
                });
                return false;
            }else{
                layer.close(index);
                layer.msg("文件导入成功！", {
                    shade: [0.3, '#393D49'], // 透明度  颜色
                    time:5000
                });
                layer_close();
                return false;
            }
        }).error(function() {
            layer.close(index);
            layer.msg("文件导入失败!",{icon:2});
        });
    }
</script>
