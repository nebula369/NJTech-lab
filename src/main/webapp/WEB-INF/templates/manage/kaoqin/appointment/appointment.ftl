<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <title>实验室预约</title>
    <meta name="description" content="Appointment page" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="/assets/lib/css/bootstrap.min.css" />
    <link rel="stylesheet" href="/assets/lib/font-awesome/4.5.0/css/font-awesome.min.css" />
    <!-- text fonts -->
    <link rel="stylesheet" href="/assets/lib/css/fonts.googleapis.com.css" />
    <!-- ace styles -->
    <link rel="stylesheet" href="/assets/lib/css/ace.min.css" />
    <!--[if lte IE 9]>
    <link rel="stylesheet" href="/assets/lib/css/ace-part2.min.css" />
    <![endif]-->
    <link rel="stylesheet" href="/assets/lib/css/ace-rtl.min.css" />
    <!--[if lte IE 9]>
    <link rel="stylesheet" href="/assets/lib/css/ace-ie.min.css" />
    <![endif]-->
    <!--[if lte IE 8]>
    <script src="/assets/lib/js/html5shiv.min.js"></script>
    <script src="/assets/lib/js/respond.min.js"></script>
    <![endif]-->
    <script language="JavaScript">
        if (window != top)
            top.location.href = location.href;
    </script>
</head>

<body class="login-layout light-login">
<div class="main-container">
    <div class="main-content">
        <div class="row">
            <div class="col-sm-10 col-sm-offset-1">
                <div class="login-container">
                    <div class="space-6"></div>
                    <div class="position-relative">
                        <div id="login-box" class="login-box visible widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header blue lighter bigger">
                                        <i class="ace-icon fa fa-coffee green"></i>
                                        请输入预约信息
                                    </h4>

                                    <div class="space-6"></div>

                                    <form id="validation-form">
                                        <fieldset>
                                            <label class="block clearfix" style="">
												<label class="blue bigger" style="line-height:21px;width:25%;margin-bottom:0;">开始时间:</label>
                                                <span class="block input-icon input-icon-right" style="display:inline-block !important;">
													<input style="padding-right:22px;width:210px;height:35px;" type="datetime-local" name="password" id="begintime" class=""  pattern="2017-02-26 00:00:00"  />
												</span>
                                            </label>
                                            <label class="block clearfix">
                                                <label class="blue bigger" style="line-height:21px;width:25%;margin-bottom:0;">结束时间:</label>
												<span class="block input-icon input-icon-right" style="display:inline-block !important;">
													<input style="padding-right:22px;width:210px;height:35px;" type="datetime-local" name="password" id="endtime" class=""  pattern="2017-02-26 00:00:00" />
												</span>
                                            </label>
                                            <label class="block clearfix">
                                                <label class="blue bigger" style="line-height:21px;width:25%;margin-bottom:0;">任课教师:</label>
                                                <span class="block input-icon input-icon-right" style="display:inline-block !important;">
                                                <select style="padding-right:22px;width:210px;height:35px;">
                                                  <option value="1">陈丹</option>
                                                  <option value="2">隋国光</option>
                                                  <option value="3">林嘉诚</option>
                                                  <option value="4">谭国英</option>
                                                </select>
												</span>
                                            </label>
                                            <div id="list">
                                                <div style="border-bottom:1px solid #d5e3ef">
                                                    <label class="block clearfix">
                                                        <label class="blue bigger" style="line-height:21px;width:25%;margin-bottom:0;">实验器材:</label>
                                                        <span class="block input-icon input-icon-right" style="display:inline-block !important;">
                                                            <select style="padding-right:22px;width:210px;height:35px;">
                                                              <option value="光谱仪">光谱仪</option>
                                                              <option value="热度系数测定仪">热度系数测定仪</option>
                                                              <option value="光偏振试验仪">光偏振试验仪</option>
                                                            </select>
											        	</span>
                                                    </label>
                                                    <label class="block clearfix">
                                                        <label class="blue bigger" style="line-height:21px;width:25%;margin-bottom:0;"></label>
                                                        <span class="block input-icon input-icon-right" style="display:inline-block !important;">
                                                            <select style="padding-right:22px;width:210px;height:35px;">
                                                                <option value="实验室1">实验室1</option>
                                                                <option value="实验室2">实验室2</option>
                                                                <option value="实验室3">实验室3</option>
                                                            </select>
                                                        </span>
                                                        <i onclick="del(this);" style="font-size:20px;color:red;float:right;margin-top:5px" class="ace-icon fa fa-trash-o fa-2x icon-only"></i>
                                                    </label>
                                                </div>
                                            </div>
                                            <div class="space"></div>
                                            <div class="clearfix">
                                                <button type="button" class="width-35 pull-right btn btn-sm btn-primary" id="btnLogin" onclick="save()">
                                                    <i class="ace-icon fa fa-key"></i>
                                                    <span class="bigger-110">确认预约</span>
                                                </button>
                                                <button type="button" class="width-35 pull-right btn btn-sm btn-primary" id="btnAdd" style="margin-right:10px" onclick="add();">
                                                    <i class="ace-icon fa glyphicon-plus"></i>
                                                    <span class="bigger-110">增加器材</span>
                                                </button>
                                            </div>

                                            <div class="space-4"></div>
                                        </fieldset>
                                    </form>
                                    <div class="alert alert-danger" style="display: none;">
                                    </div>
                                </div><!-- /.widget-main -->
                            </div><!-- /.widget-body -->
                        </div><!-- /.login-box -->
                    </div><!-- /.position-relative -->
                </div>
            </div><!-- /.col -->
        </div><!-- /.row -->
    </div><!-- /.main-content -->
</div><!-- /.main-container -->

<!-- basic scripts -->

<!--[if !IE]> -->
<script src="/assets/lib/js/jquery-2.1.4.min.js"></script>

<!-- <![endif]-->

<!--[if IE]>
<script src="/assets/lib/js/jquery-1.11.3.min.js"></script>
<![endif]-->

<script src="/assets/lib/js/jquery.validate.min.js"></script>
<script src="/assets/scripts/layer/layer.js"></script>

<!-- inline scripts related to this page -->
<script type="text/javascript">
    var now = new Date(), year = now.getFullYear(), month = ((now.getMonth() + 1)>9)?(now.getMonth() + 1):("0"+(now.getMonth() + 1)), date = translate(now.getDate()), hour = translate(now.getHours()), minute = translate(now.getMinutes()), second = translate(now.getSeconds());
    function translate(prop) {
        if (prop <= 9) {
            return "0" + prop;
        } else {
            return prop
        }
    }
    var dateString =  year+"-"+month+"-"+date+"T"+hour+":"+minute;
    $('#begintime').val(dateString);
    $('#endtime').val(dateString);

    $('#btnLogin').on('click', function(e) {

        e.preventDefault();
    });

    $(function(){
        document.onkeydown = function(e){
            var ev = document.all ? window.event : e;
            if(ev.keyCode==13) {
                document.getElementById("btnLogin").click();
            }
        }
    });

    function add(){
        $("#list").append("<div style=\"border-bottom:1px solid #d5e3ef;margin-top:5px\">\n" +
            "                                   <label class=\"block clearfix\">\n" +
            "                                       <label class=\"blue bigger\" style=\"line-height:21px;width:25%;margin-bottom:0;\">实验器材:</label>\n" +
            "                                       <span class=\"block input-icon input-icon-right\" style=\"display:inline-block !important;\">\n" +
            "                                           <select style=\"padding-right:22px;width:210px;height:35px;\">\n" +
            "                                             <option value=\"光谱仪\">光谱仪</option>\n" +
            "                                             <option value=\"热度系数测定仪\">热度系数测定仪</option>\n" +
            "                                             <option value=\"光偏振试验仪\">光偏振试验仪</option>\n" +
            "                                           </select>\n" +
            "                                        </span>\n" +
            "                                        </label>\n" +
            "                                        <label class=\"block clearfix\">\n" +
            "                                            <label class=\"blue bigger\" style=\"line-height:21px;width:25%;margin-bottom:0;\"></label>\n" +
            "                                            <span class=\"block input-icon input-icon-right\" style=\"display:inline-block !important;\">\n" +
            "                                                <select style=\"padding-right:22px;width:210px;height:35px;\">\n" +
            "                                                    <option value=\"实验室1\">实验室1</option>\n" +
            "                                                    <option value=\"实验室2\">实验室2</option>\n" +
            "                                                    <option value=\"实验室3\">实验室3</option>\n" +
            "                                                </select>\n" +
            "                                            </span>\n" +
            "                                            <i onclick=\"del(this);\" style=\"font-size:20px;color:red;float:right;margin-top:5px\" class=\"ace-icon fa fa-trash-o fa-2x icon-only\"></i>\n" +
            "                                        </label>\n" +
            "                                     </div>")
    }
    function del(item) {
        $(item).parent().parent().remove();
    }

    function getQueryVariable(variable)
    {
        var query = window.location.search.substring(1);
        var vars = query.split("&");
        for (var i=0;i<vars.length;i++) {
            var pair = vars[i].split("=");
            if(pair[0] == variable){return pair[1];}
        }
        return(false);
    }
    
    function save() {
        var list = document.getElementsByTagName("select");
        var userid = 0;
        var username = "";
        for (i = 0; i < list.length; i++) {
            if (i == 0) {
                userid = $(list[i]).val();
                username = $(list[i]).find("option:selected").text();
            } else if (i % 2 == 1) {
                jQuery.ajax({
                    type: "POST",
                    data: {
                        "subject": $(list[i]).val(),
                        "place": $(list[i + 1]).val(),
                        "teacherid": userid,
                        "teacher": username,
                        "begintime": $("#begintime").val(),
                        "endtime": $("#endtime").val()
                    },
                    dataType: "text",
                    url: "/manage/kaoqin/appointment/add",
                    success: function (result) {
                        /*                       if(result == 1) {
                                                   layer.msg("保存成功", {icon: 1, time: 1000}, function () {
                                                       //layer_close();
                                                   });
                                               }
                                               else
                                               {
                                                   layer.msg("保存错误",{icon:2});
                                               }*/
                    },
                    error: function () {
                    }
                });
            }
        }
        layer.msg("您已预约成功！", {icon: 1, time: 1000}, function () {
            //layer_close();
        });
    }
</script>
</body>
</html>
