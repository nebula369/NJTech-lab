/*!
 * Cropper v3.0.0
 */

layui.config({
    base: '/assets/lib/js/cropper/' //layui自定义layui组件目录
}).define(['jquery','layer','cropper'],function (exports) {
    var $ = layui.jquery
        ,layer = layui.layer;
    var html = "<link rel=\"stylesheet\" href=\"/assets/lib/css/cropper.css\">\n" +
        "<div class=\"layui-fluid showImgEdit\" style=\"display: none\">\n" +
        "    <div class=\"layui-form-item\" style=\"margin-top: 15px;\">\n" +
        "        <div class=\"layui-input-inline layui-btn-container\" style=\"width: auto;\">\n" +
        "            <label for=\"cropper_avatarImgUpload\" class=\"layui-btn layui-btn-primary\">\n" +
        "                <i class=\"layui-icon\">&#xe67c;</i>选择图片\n" +
        "            </label>\n" +
        "            <input class=\"layui-upload-file\" id=\"cropper_avatarImgUpload\" type=\"file\" value=\"选择图片\" name=\"file\">\n" +
        "        </div>\n" +
        "        <div class=\"layui-form-mid layui-word-aux\">头像的尺寸限定150x150px,大小在50kb以内</div>\n" +
        "    </div>\n" +
        "    <div class=\"layui-row layui-col-space15\">\n" +
        "        <div class=\"layui-col-xs9\">\n" +
        "            <div class=\"readyimg\" style=\"height:350px;background-color: rgb(247, 247, 247);\">\n" +
        "                <img src=\"\" >\n" +
        "            </div>\n" +
        "        </div>\n" +
        "        <div class=\"layui-col-xs3\">\n" +
        "            <div class=\"img-preview\" style=\"width:200px;height:200px;overflow:hidden\">\n" +
        "            </div>\n" +
        "        </div>\n" +
        "    </div>\n" +
        "    <div class=\"layui-row layui-col-space15\">\n" +
        "        <div class=\"layui-col-xs9\">\n" +
        "            <div class=\"layui-row\">\n" +
        "                <div class=\"layui-col-xs6\">\n" +
        "                    <button type=\"button\" class=\"layui-btn layui-icon layui-icon-left\" cropper-event=\"rotate\" data-option=\"-15\" title=\"Rotate -90 degrees\"> 向左旋转</button>\n" +
        "                    <button type=\"button\" class=\"layui-btn layui-icon layui-icon-right\" cropper-event=\"rotate\" data-option=\"15\" title=\"Rotate 90 degrees\"> 向右旋转</button>\n" +
        "                </div>\n" +
        "                <div class=\"layui-col-xs5\" style=\"text-align: right;\">\n" +
/*        "                    <button type=\"button\" class=\"layui-btn\" title=\"移动\"></button>\n" +
        "                    <button type=\"button\" class=\"layui-btn\" title=\"放大图片\"></button>\n" +
        "                    <button type=\"button\" class=\"layui-btn\" title=\"缩小图片\"></button>\n" +*/
        "                    <button type=\"button\" class=\"layui-btn layui-icon layui-icon-refresh\" cropper-event=\"reset\" title=\"重置图片\"></button>\n" +
        "                </div>\n" +
        "            </div>\n" +
        "        </div>\n" +
        "        <div class=\"layui-col-xs3\">\n" +
        "            <button class=\"layui-btn layui-btn-fluid\" cropper-event=\"confirmSave\" type=\"button\"> 保存修改</button>\n" +
        "        </div>\n" +
        "    </div>\n" +
        "\n" +
        "</div>";
    var obj = {
        render: function(e){
            $('body').append(html);
            var self = this,
                elem = e.elem,
                saveW = e.saveW,
                saveH = e.saveH,
                mark = e.mark,
                area = e.area,
                url = e.url,
                filepath=e.filepath,
                done = e.done;

            var content = $('.showImgEdit')
                ,image = $(".showImgEdit .readyimg img")
                ,preview = '.showImgEdit .img-preview'
                ,file = $(".showImgEdit input[name='file']")
                , options = {aspectRatio: mark,preview: preview,viewMode:1};

            $(elem).on('click',function () {
                layer.open({
                    type: 1
                    , content: content
                    , area: area
                    ,title:"上传图片" //页面标题
                    , success: function () {
                        image.cropper(options);
                    }
                    , cancel: function (index) {
                        layer.close(index);
                        image.cropper('destroy');
                    }
                });
            });
            $(".layui-btn").on('click',function () {
                var event = $(this).attr("cropper-event");
                //监听确认保存图像
                if(event === 'confirmSave'){
                    image.cropper("getCroppedCanvas",{
                        width: saveW,
                        height: saveH
                    }).toBlob(function(blob){
                        var fileName=$("#cropper_avatarImgUpload")[0].files[0].name;
                        var formData = new FormData();
                        formData.append("croppedImage", blob);
                        formData.append('fileName', fileName);
                        formData.append("filePath", filepath);
                        $.ajax({
                            type : 'POST',
                            url: url, //用于文件上传的服务器端请求地址
                            data: formData,
                            cache : false,
                            processData: false,
                            contentType: false,
                            success:function(result){
                                if(result.result == 'success'){
                                    InitPic(result.url);
                                    layer.msg("上传成功",{icon: 1});
                                    layer.closeAll('page');

                                }
                            },
                            error:function(result){
                                layer.msg("上传失败!",{icon:2});
                            }
                        });
                    });
               //监听旋转
                }
                else if(event === 'rotate'){
                    var option = $(this).attr('data-option');
                    image.cropper('rotate', option);
                //重设图片
                }else if(event === 'reset'){
                    image.cropper('reset');
                }
                //文件选择
                file.change(function () {
                    var r= new FileReader();
                    var f=this.files[0];
                    r.readAsDataURL(f);
                    r.onload=function (e) {
                        image.cropper('destroy').attr('src', this.result).cropper(options);
                    };
                });
            });
        }

    };
    exports('croppers', obj);
});

//添加转化为二进制方法
function processData(dataUrl) {
    var binaryString = window.atob(dataUrl.split(',')[1]);
    var arrayBuffer = new ArrayBuffer(binaryString.length);
    var intArray = new Uint8Array(arrayBuffer);
    for (var i = 0, j = binaryString.length; i < j; i++) {
        intArray[i] = binaryString.charCodeAt(i);
    }
    var data = [intArray],
        blob;
    try {
        blob = new Blob(data);
    } catch (e) {
        window.BlobBuilder = window.BlobBuilder ||
            window.WebKitBlobBuilder ||
            window.MozBlobBuilder ||
            window.MSBlobBuilder;
        if (e.name === 'TypeError' && window.BlobBuilder) {
            var builder = new BlobBuilder();
            builder.append(arrayBuffer);
            blob = builder.getBlob(imgType); // imgType为上传文件类型，即 file.type
        } else {
            console.log('版本过低，不支持上传图片');
        }
    }
    return blob;
}
