<!DOCTYPE html>
<#setting number_format="#">
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <title>${title!""} - 个人桌面</title>

    <meta name="description" content="Mailbox with some customizations as described in docs" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="/assets/lib/css/bootstrap.min.css" />
    <link rel="stylesheet" href="/assets/lib/font-awesome/4.5.0/css/font-awesome.min.css" />

    <!-- jqgrid plugin styles -->
    <link rel="stylesheet" href="/assets/lib/css/jquery-ui.min.css" />
    <link rel="stylesheet" href="/assets/lib/css/ui.jqgrid.min.css" />
    <link rel="stylesheet" href="/assets/lib/css/daterangepicker.min.css" />

    <link rel="stylesheet" href="/assets/lib/css/chosen.min.css" />
    <!-- text fonts -->
    <link rel="stylesheet" href="/assets/lib/css/fonts.googleapis.com.css" />

    <!-- ace styles -->
    <link rel="stylesheet" href="/assets/lib/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="/assets/lib/css/ace-part2.min.css" class="ace-main-stylesheet"/>
    <![endif]-->
    <link rel="stylesheet" href="/assets/lib/css/ace-skins.min.css" />
    <link rel="stylesheet" href="/assets/lib/css/ace-rtl.min.css" />

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="/assets/lib/css/ace-ie.min.css"/>
    <![endif]-->
    <!-- inline styles related to this page -->
    <!-- ace settings handler -->
    <script src="/assets/lib/js/ace-extra.min.js"></script>

    <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->
    <!--[if lte IE 8]>
    <script src="/assets/lib/js/html5shiv.min.js"></script>
    <script src="/assets/lib/js/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
        .ui-jqgrid tr.jqgrow td { white-space: normal !important; height:auto; } /**内容自动换行 */
    </style>
    <script>
        var isNeedGetCurrentMsgList = false;
    </script>
</head>
<body class="no-skin" style="overflow-x: hidden">