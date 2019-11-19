<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <title>登录界面 - 基础平台</title>

    <meta name="description" content="User login page" />
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
                    <div class="center">
                        <h1>
                            <i class="ace-icon fa fa-leaf green"></i>
                            基础平台
                        </h1>
                        <h4 class="blue" id="id-company-text">&copy; Company Name</h4>
                    </div>

                    <div class="space-6"></div>

                    <div class="position-relative">
                        <div id="login-box" class="login-box visible widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header blue lighter bigger">
                                        <i class="ace-icon fa fa-coffee green"></i>
                                        请输入登录帐号
                                    </h4>

                                    <div class="space-6"></div>

                                    <form id="validation-form">
                                        <fieldset>
                                            <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" name="userName" id="userName" class="form-control" placeholder="登录名" />
															<i class="ace-icon fa fa-user"></i>
														</span>
                                            </label>

                                            <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" name="password" id="password" class="form-control" placeholder="登录密码" />
															<i class="ace-icon fa fa-lock"></i>
														</span>
                                            </label>

                                            <div class="space"></div>

                                            <div class="clearfix">
                                                <label class="inline">
                                                    <input type="checkbox" class="ace" />
                                                    <span class="lbl">记住密码</span>
                                                </label>

                                                <button type="button" class="width-35 pull-right btn btn-sm btn-primary" id="btnLogin">
                                                    <i class="ace-icon fa fa-key"></i>
                                                    <span class="bigger-110">登录</span>
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
<script type="text/javascript">
    if('ontouchstart' in document.documentElement) document.write("<script src='/assets/lib/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
</script>

<script src="/assets/lib/js/jquery.validate.min.js"></script>

<!-- inline scripts related to this page -->
<script type="text/javascript">

    $('#btnLogin').on('click', function(e) {
        if($(this).hasClass("disabled")) return;
        $(this).addClass("disabled");
        $(this).find("span").text("登录中...");
        var obj = $(this);
        jQuery.ajax({
            type: "POST",
            data: {"loginName": $("#userName").val(), "loginPwd":$("#password").val(),"appKey":"${appKey}","auth":"${auth}"},
            dataType: "json",
            url: "/checkLogin",
            success: function (result) {
                if(result.code == 3000) {
                    if ("${appKey}" != "") {
                        location.href = "/manage/ssoLogin?appKey=${appKey}&auth=${auth}";
                    } else if ("${auth}" == "weixin_info") {
                        location.href="/service/faceattendance/GetWeinUser?token="+getQueryVariable("token");
                    } else if ("${auth}" == "appointment") {
                        location.href="/manage/kaoqin/appointment";
                    } else {
                        location.href = "/manage/home";
                    }
                }
                else
                {
                    $(".alert").html(result.message);
                    $(".alert").show();
                    obj.removeClass("disabled");
                    obj.find("span").text("登录");
                }
            },
            error:function(){
                $(".alert").html("登录出错，请联系管理员");
                $(".alert").show();
                obj.removeClass("disabled");
                obj.find("span").text("登录");
            }
        });
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
</script>
</body>
</html>
