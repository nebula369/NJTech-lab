<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>
<link rel="stylesheet" href="/assets/lib/css/bootstrap-datepicker3.min.css" />
<link rel="stylesheet" href="/assets/lib/js/layui/css/layui.css" />
<div class="main-content">
    <div class="main-content-inner">
        <div class="page-content" style="padding:0">
            <div class="row">
                <div class="col-xs-12">
                    <form class="form-horizontal" id="validation-form" role="form"  enctype="multipart/form-data">
                      <table style="width: 100%">
                          <tr >
                              <td style="width: 20%; height: 100%;background-color: #EEEEEE;vertical-align: top">
                                   <ul  id="ulSelect" style="list-style: none;text-align: center; margin-left:20px;margin-top: 40px;">
                                       <li  class="SelectedNavItem" id="1"  style="height: 35px;line-height: 35px; cursor: pointer" >包文件导入</li>
                                       <li  id="2" style="height: 35px;line-height: 35px; cursor: pointer">从照片导入</li>
                                   </ul>
                              </td>
                              <td>
                                  <div style="margin-left: 30px;margin-top: 20px;height: 430px;">
                                  <div class="packupload"  >
                                     <div>
                                          <p style="color: #FF6600;">温馨提醒：</p>
                                          <p>包文件中应当包含场次信息，因此通过此种方式导入数据无需选择场次。</p>
                                          <p>文件来源：</p>
                                          <p>1、通过专用工具从考场安排软件数据中自动生成；</p>
                                          <p>2、按照模板制作手动导入包， <a href="/service/fileDownload?fileName=考场学生照片模板.zip" style="color: #0066FF">点这里获取模板文件</a>。</p>
                                      </div>
                                      <div>
                                          <a href="javascript:void(0);" class="input" style="color:#FFF;">
                                              选择包文件
                                              <input type="file" id="file" name="file" class="file">
                                          </a>
                                          <span>仅支持zip压缩文件</span>
                                      </div>
                                      <div class="packuploadinfo" id="packuploadinfo"  style="margin-top: 30px;">
                                        <div class="container" id="packuploadprogress" style="display: none;">
                                               <div  id="filename"></div>
                                              <div class="progress"  style='height: 6px;'>
                                                     <div class="progress-bar" id="prog" style="width:10%"></div>
                                                   </div>
                                             </div>
                                      </div>
                                  </div>
                                  <div class="photoupload" style="display: none;" >
                                      <div>
                                          <p style="color: #FF6600;">温馨提醒：</p>
                                          <p>1、请使用姓名对照片进行命名，如有同名请用数字作为后缀区分，如：张三1.jpg</p>
                                          <p>2、包本次操作会将上传的考生添加至所选考试场次，当前已选<i></i>${examroom.name}考试。</p>
                                      </div>
                                      <div class="x-body" style="margin-top: 20px">
                                          <div class="layui-upload">
                                          <button type="button" class="layui-btn layui-btn-sm layui-btn-normal" style="float:left;" id="testList">选择要上传的图片</button>
                                          <button type="button" class="layui-btn layui-btn-sm layui-btn-normal" id="startUpload" style="margin-left: 30px;">开始上传</button>
                                          <div  id="uploadLoadingDiv" style="display: none;  width: 300px;float:left; margin-top: 10px; margin-left: 10px">
                                              <div class="layui-progress layui-progress-big" lay-showpercent="true" lay-filter="demo" >
                                                      <div class="layui-progress-bar" lay-percent="0%"></div>
                                              </div>
                                          </div>
                                          <div style="clear: both;"></div>
                                          <div class="layui-upload-list">
                                              <table class="layui-table">
                                                  <thead>
                                                  <tr><th>文件名</th>
                                                      <th>大小</th>
                                                      <th>状态</th>
                                                      <th>操作</th>
                                                  </tr></thead>
                                                  <tbody id="demoList"></tbody>
                                              </table>
                                          </div>
                                      </div>
                                      </div>
                                  </div>
                                  </div>
                              </td>
                          </tr>
                      </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<style>
    .SelectedNavItem{
        color: #1E9FFF !important;
        background-color: #fff;
    }
    a.input {
        width:110px;
        height:30px;
        line-height:30px;
        background:#1E9FFF;
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
    p{
        padding: 5px;
    }
    .file {
        opacity:0;/*设置此控件透明度为零，即完全透明*/
        filter:alpha(opacity=0);/*设置此控件透明度为零，即完全透明针对IE*/
        font-size:100px;
        position:absolute;/*绝对定位，相对于 .input */
        top:0;
        right:0;
        cursor: pointer;
    }
    .packuploadinfo .container{
        width: 350px;
        margin: 0;
    }
   .container .progress{
    height: 6px;
    border-radius: 3px 3px 3px 3px;
   }
    .uploader-list{
        display: inline-block;
    }

    .uploader-list  .file-iteme{
      float: left;
    }
    .uploader-list .handle {
        position: relative;
        background-color: black;
        color: white;
        filter: alpha(Opacity=80);
        -moz-opacity: 0.5;
        opacity: 0.5;
        width: 100px;
        text-align: right;
        height: 18px;
        margin-bottom: -18px;
        display: none;
    }
    .uploader-list .handle i {
        margin-right: 5px;
    }
    .uploader-list .handle i:hover {
        cursor: pointer;
    }
    .uploader-list .file-iteme {
        margin: 12px 0 0 15px;
        padding: 1px;
        float: left;
    }
    .uploader-list .nfo{
    font-size: 13px;
    text-align: center;
     }
</style>
<#include "/controls/appbottom.ftl"/>
<script src="/assets/lib/js/moment.min.js"></script>
<script src="/assets/lib/js/jquery.validate.min.js"></script>
<script src="/assets/lib/js/layui/layui.js"></script>

<script>
    var directory="upload/exam/userphoto/"+${examplanids};
    layui.use(['upload','element'], function(){
        var $ = layui.jquery
            ,upload = layui.upload;
        var demoListView = $('#demoList')
            ,uploadListIns = upload.render({
            elem: '#testList'
            ,url: '/manage/upload/fileupload'
            ,data:{"directory":directory,"type":"image"}
            ,accept:'images'
            ,exts:'png|jpg|jpeg'
            ,multiple: true
            //,number:2//最多2个同时上传
            ,auto: false
            ,size:102400
            ,bindAction: '#startUpload'
            ,before:function(obj){
                $("#uploadLoadingDiv").show();
                var trs = demoListView.find('tr');
                $.each(trs, function (item) {
                    var tds = $(this).children()
                    tds.eq(2).html('<span style="color: gray;">上传中...</span>');
                })
            }
            ,choose: function(obj){
                var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
                //读取本地文件
                obj.preview(function(index, file, result){
                    var tr = $(['<tr id="upload-'+ index +'">'
                        ,'<td><img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img">'+ file.name +'</td>'
                        ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
                        ,'<td>等待上传</td>'
                        ,'<td>'
                        ,'<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
                        ,'<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
                        ,'</td>'
                        ,'</tr>'].join(''));

                    //单个重传
                    tr.find('.demo-reload').on('click', function(){
                        obj.upload(index, file);
                    });

                    //删除
                    tr.find('.demo-delete').on('click', function(){
                        delete files[index]; //删除对应的文件
                        tr.remove();
                        uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                    });

                    demoListView.append(tr);
                });
            }
            ,xhr:xhrOnProgress
            ,progress:function(value){//上传进度回调 value进度值
                element.progress('demo', value+'%')
            }
            ,done: function(res, index, upload){
                if(res.code != 0){ //上传成功
                    var tr = demoListView.find('tr#upload-'+ index)
                        ,tds = tr.children();
                    tds.eq(3).html(''); //清空操作
                    tds.eq(2).html('<span style="color: #5FB878;">正在保存数据</span>');
                     var userphoto=res.path+"/"+res.data;
                     var currfilename= res.filename.substring(0,res.filename.indexOf("."));
                     console.log(currfilename); console.log(${examplanids});console.log(userphoto);
                    jQuery.ajax({
                        type: "POST",
                        data: {"filename":currfilename,"examplanid":${examplanids}, "userphoto":userphoto},
                        dataType: "json",
                        url: "/manage/eclassbrand/examplan/addExamUserPhoto",
                        success: function (result) {
                            if(result=="1")
                            {
                                tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
                            }
                            else
                            {
                                tds.eq(2).html('<span style="color: #FF5722;">保存失败</span>');
                            }
                        }
                    });
                    return delete this.files[index]; //删除文件队列已经上传成功的文件
                }
                this.error(index, upload);
            }
            ,allDone: function(obj){ //当文件全部被提交后，才触发
                if(obj.total == obj.successful)
                {
                    layer.msg("全部上传完成",{time:2000}, function () {
                        //说明全部上传成功
                        var index = parent.layer.getFrameIndex(window.name);
                        //关闭当前frame
                        parent.layer.close(index);
                        parent.reloadData();
                    });
                }
            }
            ,error: function(index, upload){
                var tr = demoListView.find('tr#upload-'+ index)
                    ,tds = tr.children();
                tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
                tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
            }
        });
    });

</script>
<script type="text/javascript">
   jQuery(function ($) {
    $("#ulSelect li").on("click", function () {
        $("#ulSelect li").removeClass();
        $("#ulSelect li").addClass("subNavItem");
        $(this).removeClass().addClass("SelectedNavItem");
        var id = $(this).attr("id");
        $("#packuploadprogress").hide();
        if(id=="1")
        {
           $(".packupload").show();
            $(".photoupload").hide()
        }else{
            $(".packupload").hide();
            $(".photoupload").show()
        }
      });
    //上传包
    $("#file").change(function(){  // 当 id 为 file 的对象发生变化时
            var file =$("#file")[0].files;
            var fileSize = file[0].size;
            var fileName=file[0].name;
            //   var fileExtension = fileName.split('.').pop();
            var fileType=(fileName.substring(fileName.lastIndexOf(".")+1,fileName.length)).toLowerCase();//后缀名
            var size = fileSize / 1024 / 1024;
            if (size > 5)
            {
                layer.msg("附件不能大于5M,请将文件压缩后重新上传！",{icon:2});
                this.value="";
                return false;
            }
            if (fileType !=  "zip") {
                layer.msg("文件格式不正确!",{icon:2});
                this.value="";
                return false;
            }
            $("#packuploadprogress").show();
            $("#packuploadprogress #filename").html(fileName);
            uploadpack();
            timer=setInterval(function()   //开启循环：每秒出现一次提示框
            {
               var width=  $("#prog").attr("width");
               if(!width=="100")
                {
                   $("#prog").css("width",i+"%");
                   i++
               }
           },1000);
        });
    //上传包
    function uploadpack()
    {
        var formData = new FormData();
            formData.append("file", $("#file")[0].files[0]);
            formData.append("examplanids",${examplanids}) ;
            $.ajax({
                type : 'POST',
                url: "/manage/eclassbrand/examplan/importExamUserPhotoPack",
                data : formData,
                // data: {"file":formData,"filepath":filepath},
                cache : false,
                processData : false,
                contentType : false,
            }).success(function(data) {
                var obj =eval('(' + data+ ')');
                clearInterval(timer); //关闭循环
                if(obj.code==0)
                {
                    $("#prog").css("width","100%");
                    layer.msg("上传成功!",{icon:1});
                   // parent.reloadData();
                   //layer_close();
                }
                else{
                    layer.msg("上传失败!"+obj.msg,{icon:2});
                }
            }).error(function() {
                   clearInterval(timer); //关闭循环
                   layer.msg("上传失败!",{icon:2});
            });
     }
   });
</script>
