<#include "/manage/apptop.ftl"/>

 <div class="x-body">
     <form class="layui-form">
         <div class="layui-form-item">
             <label for="L_username" class="layui-form-label">
                 姓名
             </label>
             <div class="layui-input-inline">
                 <input type="text" id="L_username" name="username" disabled="" value="${user.name}" class="layui-input">
             </div>
         </div>
         <div class="layui-form-item">
             <label for="L_repass" class="layui-form-label">
                 <span class="x-red">*</span>旧密码
             </label>
             <div class="layui-input-inline">
                 <input type="password" id="L_repass" name="oldpass" required="" lay-verify="required"
                        autocomplete="off" class="layui-input">
             </div>
         </div>
         <div class="layui-form-item">
             <label for="L_pass" class="layui-form-label">
                 <span class="x-red">*</span>新密码
             </label>
             <div class="layui-input-inline">
                 <input type="password" id="L_pass" name="newpass" required="" lay-verify="pass"
                        autocomplete="off" class="layui-input">
             </div>
             <div class="layui-form-mid layui-word-aux">
                 6到16个字符
             </div>
         </div>
         <div class="layui-form-item">
             <label for="L_repass" class="layui-form-label">
                 <span class="x-red">*</span>确认密码
             </label>
             <div class="layui-input-inline">
                 <input type="password" id="L_repass" name="repass" required="" lay-verify="repass"
                        autocomplete="off" class="layui-input">
             </div>
         </div>
         <div class="layui-form-item">
             <label for="L_repass" class="layui-form-label">
             </label>
             <button  class="layui-btn" lay-filter="save" lay-submit="">
                 修改
             </button>
         </div>
     </form>
 </div>
    <script>
        layui.use(['form','layer'], function(){
            $ = layui.jquery;
            var form = layui.form
                    ,layer = layui.layer;

            form.verify({
                pass: [/(.+){6,12}$/, '密码必须6到12位']
                ,repass: function(value){
                    if($('#L_pass').val()!=$('#L_repass').val()){
                        return '两次密码不一致';
                    }
                }
            });

            //监听提交
            form.on('submit(save)', function(data){
                if($(this).hasClass("layui-btn-disabled")) return;
                $(this).addClass("layui-btn-disabled");
                $(this).text("修改中...");
                var obj = $(this);
                jQuery.ajax({
                    type: "POST",
                    data: {"oldPwd":data.field.oldpass,"newPwd":data.field.newpass},
                    dataType: "json",
                    url: "/manage/doeditmypwd",
                    success: function (result) {
                        layer.alert("修改成功", {icon: 6},function () {
                            // 获得frame索引
                            var index = parent.layer.getFrameIndex(window.name);
                            //关闭当前frame
                            parent.layer.close(index);
                        });
                    }
                });
                return false;
            });


        });
    </script>

<#include "/manage/appbottom.ftl"/>