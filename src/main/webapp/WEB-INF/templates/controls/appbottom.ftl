    </body>
</html>
            <!-- basic scripts -->
<!--[if !IE]> -->
<script src="/assets/lib/js/jquery-2.1.4.min.js"></script>

<!-- <![endif]-->
<!--[if IE]>
<script src="/assets/lib/js/jquery-1.11.3.min.js"></script>
<![endif]-->
<script type="text/javascript">
    if ('ontouchstart' in document.documentElement) document.write("<script src='/assets/lib/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
</script>

<script src="/assets/lib/js/bootstrap.min.js"></script>
<!-- ace scripts -->
<script src="/assets/lib/js/ace-elements.min.js"></script>
<script src="/assets/lib/js/ace.min.js"></script>
    <script src="/assets/scripts/layer/layer.js"></script>
<script src="/assets/lib/js/jquery.inputlimiter.min.js"></script>
    <script type="text/javascript" src="/assets/scripts/easybasic.js"></script>
    <script>
        $(document).ready(function () {
           if(isNeedGetCurrentMsgList)
           {
               initUnReadMsgList();
               //setTimeout(initUnReadMsgList, 10000);
           }
        });

        function initUnReadMsgList()
        {
            jQuery.ajax({
                type: "POST",
                data: {},
                dataType: "json",
                url: "/manage/profile/getUnReadMsgList",
                success: function (result) {
                    $("#spanUnReadMsgCount_0").html(result.length);
                    $("#spanUnReadMsgCount_1").html(result.length);
                    var html = [];
                    for(var i=0;i<result.length;i++) {
                        var item = result[i];
                        html.push('<li>' +
                                '       <a href="/manage/profile/viewMsg?id='+item.pkid+'" class="clearfix">' +
                                '           <img src="/assets/lib/images/avatars/avatar2.png" class="msg-photo"/>' +
                                '           <span class="msg-body">' +
                                '               <span class="msg-title">' +
                                '                   <span class="blue">'+item.fromUserName+':</span>'+item.title+'</span>' +
                                '                   <span class="msg-time">' +
                                '                       <i class="ace-icon fa fa-clock-o"></i>' +
                                '                       <span>'+item.createtime+'</span>' +
                                '                   </span>' +
                                '              </span>' +
                                '        </a>' +
                                '</li>');
                    }
                    $("#ulUnReadMsgList").html(html.join(""));
                },
                error:function () {

                }
            });
        }
    </script>