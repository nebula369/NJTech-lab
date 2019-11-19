<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>

<!-- 加载顶部bar -->
<@topBar title="${title}" titleUrl="/manage/basic/index" currentUser=user icon="${menuConfig.icon}"/>

<div class="main-container ace-save-state" id="main-container">

    <!-- 加载左侧菜单 -->
    <@leftMenu indexUrl="/manage/basic/index" menuList=menuConfig.menuList currentUrl="/manage/basic/app"/>

    <div class="main-content">
        <div class="main-content-inner">
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="/manage/basic/index">导航页</a>
                    </li>

                    <li>
                        <a href="#">配置信息</a>
                    </li>
                    <li class="active">应用配置</li>
                </ul>
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12" style="padding: 0px;">
                        <!-- PAGE CONTENT BEGINS -->
                        <div>
                            <div class="row search-page">
                                <div class="col-xs-12">
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-4">
                                            <div class="search-area well well-sm">
                                                <div class="search-filter-header bg-primary">
                                                    <h5 class="smaller no-margin-bottom" style="float: left;">
                                                        <i class="ace-icon fa fa-sliders light-green bigger-130"></i>&nbsp; 应用列表
                                                    </h5>
                                                    <button id="btnAdd" class="btn btn-minier btn-success" style="float:right;">
                                                        <i class="ace-icon fa fa-pencil-square-o"></i>
                                                        新建
                                                    </button>
                                                    <div style="clear: both;"></div>
                                                </div>
                                                <div class="hr hr-dotted"></div>
                                                <div class="dd" id="nestable">
                                                    <ol class="dd-list" id="oldrag">

                                                    </ol>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-xs-12 col-sm-4 no-padding-left">
                                            <div class="search-area well well-sm">
                                                <div class="search-filter-header bg-primary">
                                                    <h5 class="smaller no-margin-bottom" style="float: left;">
                                                        <i class="ace-icon fa fa-sliders light-green bigger-130"></i>&nbsp; <span id="spanApp">应用菜单列表</span>
                                                    </h5>
                                                    <button id="btnAddMenu" class="btn btn-minier btn-success" style="float:right;">
                                                        <i class="ace-icon fa fa-pencil-square-o"></i>
                                                        新建
                                                    </button>
                                                    <div style="clear: both;"></div>
                                                </div>
                                                <div class="hr hr-dotted"></div>
                                                <div class="dd" id="nestable">
                                                    <ol class="dd-list" id="olAppMenu">

                                                    </ol>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-xs-12 col-sm-4 no-padding-left">
                                            <div class="search-area well well-sm">
                                                <div class="search-filter-header bg-primary">
                                                    <h5 class="smaller no-margin-bottom" style="float: left;">
                                                        <i class="ace-icon fa fa-sliders light-green bigger-130"></i>&nbsp; <span id="spanAppMenu">应用子菜单列表</span>
                                                    </h5>
                                                    <button id="btnAddSubMenu" class="btn btn-minier btn-success" style="float:right;">
                                                        <i class="ace-icon fa fa-pencil-square-o"></i>
                                                        新建
                                                    </button>
                                                    <div style="clear: both;"></div>
                                                </div>
                                                <div class="hr hr-dotted"></div>
                                                <div class="dd" id="nestable">
                                                    <ol class="dd-list" id="olAppSubMenu">

                                                    </ol>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- PAGE CONTENT ENDS -->
                    </div>
                </div><!-- /.row -->

            </div><!-- /.page-content -->
        </div>
    </div>

    <!-- 加载底部版权 -->
    <#include "/controls/footer.ftl"/>

</div>

<#include "/controls/appbottom.ftl"/>
<script type="text/javascript" src="/assets/scripts/Sortable.js"></script>
<script type="text/javascript">
    jQuery(function ($){
        initAppList();
        $("#btnAdd").on("click",function () {
            layer_show("新建应用", "/manage/basic/app/add", 600, 440);
            event.stopPropagation();
        });

        $("#btnAddSubMenu").on("click",function () {
            layer_show("新建应用子菜单", "/manage/basic/app/subMenuAdd?id=" + currentAppMenuId, 600, 420);
            event.stopPropagation();
        });

        $("#btnAddMenu").on("click",function () {
            layer_show("新建应用菜单", "/manage/basic/app/menuAdd?id=" + currentAppId, 600, 340);
            event.stopPropagation();
        });
    });

    var currentAppId = 0;
    var currentAppName = "";
    var currentAppMenuId = 0;
    var currentAppMenuName = "";

    function initDragSort()
    {
        var foo = document.getElementById("oldrag");
        Sortable.create(foo,{
            animation: 150, // ms, animation speed moving items when sorting, `0` — without animation
            onUpdate: function (evt){
                var orderParam = {};
                $("#oldrag .dd-item").each(function (i, item) {
                    var key = $(this).attr("data-id");
                    orderParam[key] =i;
                });
                var orderStr = StringUtil.Base64Encode(jsonToString(orderParam));
                jQuery.ajax({
                    type: "POST",
                    data: {"orderParam":orderStr},
                    dataType: "text",
                    url: "/manage/basic/app/saveAppOrder",
                    success: function (result) {
                        layer.msg("已保存当前排序", {icon:1});
                    }
                });
            }

        });
    }

    function initAppList()
    {
        jQuery.ajax({
            type: "POST",
            url: "/manage/basic/app/getAppList" ,
            data: {},
            dataType: "json",
            success: function(data)  {
                var isInit = 0;
                var htmlArray = [];
                if(data.length > 0 ){
                    for (var i=0;i<data.length;i++) {
                        var item = data[i];
                        if(i==0 && currentAppId ==0) {
                            currentAppName = item.name;
                            currentAppId = item.pkid;
                            isInit = 1;
                        }
                        var checked = "";
                        if(item.status == 1)
                        {
                            checked = "checked";
                        }
                        var active = "";
                        if(item.pkid == currentAppId) active = "btn-yellow";
                        htmlArray.push('<li class="dd-item" data-id="'+item.pkid+'" data-name="'+item.name+'">' +
                                '<div class="dd-handle '+active+'"><i class="menu-icon fa '+item.smallicon+'"></i>&nbsp;&nbsp;' + item.name +
                                '<div class="pull-right action-buttons">' +
                                '<div style="float: left; padding-right: 15px;">' +
                                '<input data-id="'+item.pkid+'" name="switch-field-1" '+checked+' class="ace ace-switch ace-switch-3" type="checkbox">' +
                                '<span class="lbl"></span>' +
                                '</div>' +
                                '<div style="float: left;">' +
                                '<a data-id="'+item.pkid+'" class="blue" href="#">' +
                                '<i class="ace-icon fa fa-pencil bigger-130"></i>' +
                                '</a>' +
                                '<a data-id="'+item.pkid+'" class="red" href="#">' +
                                '<i class="ace-icon fa fa-trash-o bigger-130"></i>' +
                                '</a>' +
                                '</div>' +
                                '</div>' +
                                '</div>' +
                                '</li>');
                    }
                }
                $("#oldrag").html(htmlArray.join(""));
                initDragSort();
                initEvent();
                if(isInit==1)
                {
                    initAppMenuList();
                }
            }
        });
    }

    function initEvent()
    {
        $("#oldrag").find("input:checkbox").off().on('click', function(){
            var id = $(this).attr("data-id");
            var status = this.checked? 1:0;
            jQuery.ajax({
                type: "POST",
                data: {"id":id,"status":status},
                dataType: "text",
                url: "/manage/basic/app/setAppStatus",
                success: function (result) {
                    layer.msg("已保存当前状态", {icon:1});
                }
            });
            event.stopPropagation();
        });

        //编辑应用事件
        $("#oldrag .blue").off().on("click", function () {
            var id = $(this).attr("data-id");
            layer_show("编辑应用", "/manage/basic/app/edit?id=" + id, 600, 440);
            event.stopPropagation();
        });

        //删除应用事件
        $("#oldrag .red").off().on("click", function () {
            var id = $(this).attr("data-id");
            layer.confirm("确定删除该应用模块？", function (index) {
                layer.close(index);
                jQuery.ajax({
                    type: "POST",
                    data: {"id":id},
                    dataType: "text",
                    url: "/manage/basic/app/doDel",
                    success: function (result) {
                        if(result == 1) {
                            layer.msg("已删除应用模块", {icon: 1, time: 1000}, function () {
                                initAppList();
                            });
                        }
                        else if(result == 0)
                        {
                            layer.msg("当前应用已经维护了菜单，请先删除菜单再删除具体应用", {icon:2});
                        }
                    }
                });
            });
            event.stopPropagation();
        });

        $("#oldrag .dd-handle").off().on("click", function () {
            var id = $(this).parent().attr("data-id");
            $("#oldrag .dd-handle").removeClass("btn-yellow");
            $(this).addClass("btn-yellow");
            currentAppId = id;
            currentAppName = $(this).parent().attr("data-name");
            initAppMenuList();
            event.stopPropagation();
        });
    }

    function initAppMenuList()
    {
        $("#spanApp").html("应用“<font color=yellow>"+currentAppName+"</font>”的菜单列表");
        currentAppMenuId = 0;
        currentAppMenuName = "";
        jQuery.ajax({
            type: "POST",
            url: "/manage/basic/app/getAppMenuList" ,
            data: {"appId":currentAppId},
            dataType: "json",
            success: function(data)  {
                var isInit = 0;
                var htmlArray = [];
                if(data.length > 0 ){
                    for (var i=0;i<data.length;i++) {
                        var item = data[i];
                        if(i==0 && currentAppMenuId ==0) {
                            currentAppMenuName = item.name;
                            currentAppMenuId = item.pkid;
                            isInit = 1;
                        }
                        var checked = "";
                        if(item.status == 1)
                        {
                            checked = "checked";
                        }
                        var active = "";
                        if(item.pkid == currentAppMenuId) active = "btn-yellow";
                        htmlArray.push('<li class="dd-item" data-id="'+item.pkid+'" data-name="'+item.name+'">' +
                                '<div class="dd-handle '+active+'"><i class="menu-icon fa '+item.icon+'"></i>&nbsp;&nbsp;' + item.name +
                                '<div class="pull-right action-buttons">' +
                                '<div style="float: left; padding-right: 15px;">' +
                                '<input data-id="'+item.pkid+'" name="switch-field-1" '+checked+' class="ace ace-switch ace-switch-3" type="checkbox">' +
                                '<span class="lbl"></span>' +
                                '</div>' +
                                '<div style="float: left;">' +
                                '<a data-id="'+item.pkid+'" class="blue" href="#">' +
                                '<i class="ace-icon fa fa-pencil bigger-130"></i>' +
                                '</a>' +
                                '<a data-id="'+item.pkid+'" class="red" href="#">' +
                                '<i class="ace-icon fa fa-trash-o bigger-130"></i>' +
                                '</a>' +
                                '</div>' +
                                '</div>' +
                                '</div>' +
                                '</li>');
                    }
                }
                $("#olAppMenu").html(htmlArray.join(""));
                initAppMenuDragSort();
                initAppMenuEvent();
                if(isInit==1)
                {
                    initAppSubMenuList();
                }
            }
        });
    }

    function initAppMenuDragSort()
    {
        var foo = document.getElementById("olAppMenu");
        Sortable.create(foo,{
            animation: 150, // ms, animation speed moving items when sorting, `0` — without animation
            onUpdate: function (evt){
                var orderParam = {};
                $("#olAppMenu .dd-item").each(function (i, item) {
                    var key = $(this).attr("data-id");
                    orderParam[key] =i;
                });
                var orderStr = StringUtil.Base64Encode(jsonToString(orderParam));
                jQuery.ajax({
                    type: "POST",
                    data: {"appId":currentAppId,"orderParam":orderStr},
                    dataType: "text",
                    url: "/manage/basic/app/saveAppMenuOrder",
                    success: function (result) {
                        layer.msg("已保存当前菜单排序", {icon:1});
                    }
                });
            }
        });
    }

    function initAppMenuEvent()
    {
        $("#olAppMenu").find("input:checkbox").off().on('click', function(){
            var id = $(this).attr("data-id");
            var status = this.checked? 1:0;
            jQuery.ajax({
                type: "POST",
                data: {"id":id,"status":status},
                dataType: "text",
                url: "/manage/basic/app/setAppMenuStatus",
                success: function (result) {
                    layer.msg("已保存当前菜单状态", {icon:1});
                }
            });
            event.stopPropagation();
        });

        //编辑应用事件
        $("#olAppMenu .blue").off().on("click", function () {
            var id = $(this).attr("data-id");
            layer_show("编辑应用菜单", "/manage/basic/app/menuEdit?id=" + id, 600, 340);
            event.stopPropagation();
        });

        //删除应用事件
        $("#olAppMenu .red").off().on("click", function () {
            var id = $(this).attr("data-id");
            layer.confirm("确定删除该应用菜单？", function (index) {
                layer.close(index);
                jQuery.ajax({
                    type: "POST",
                    data: {"id":id},
                    dataType: "text",
                    url: "/manage/basic/app/doMenuDel",
                    success: function (result) {
                        if(result == 1) {
                            layer.msg("已删除应用菜单", {icon: 1, time: 1000}, function () {
                                initAppMenuList();
                            });
                        }
                        else if(result == 0)
                        {
                            layer.msg("当前菜单下已经维护了子菜单，请先删除子菜单再删除", {icon:2});
                        }
                    }
                });
            });
            event.stopPropagation();
        });

        $("#olAppMenu .dd-handle").off().on("click", function () {
            var id = $(this).parent().attr("data-id");
            $("#olAppMenu .dd-handle").removeClass("btn-yellow");
            $(this).addClass("btn-yellow");
            currentAppMenuId = id;
            currentAppMenuName = $(this).parent().attr("data-name");
            initAppSubMenuList();
            event.stopPropagation();
        });
    }

    function initAppSubMenuList()
    {
        $("#spanAppMenu").html("菜单“<font color=yellow>"+currentAppMenuName+"</font>”的子菜单列表");
        jQuery.ajax({
            type: "POST",
            url: "/manage/basic/app/getAppSubMenuList" ,
            data: {"menuId":currentAppMenuId},
            dataType: "json",
            success: function(data)  {
                var htmlArray = [];
                if(data.length > 0 ){
                    for (var i=0;i<data.length;i++) {
                        var item = data[i];
                        var checked = "";
                        if(item.status == 1)
                        {
                            checked = "checked";
                        }
                        htmlArray.push('<li class="dd-item" data-id="'+item.pkid+'" data-name="'+item.name+'">' +
                                '<div class="dd-handle"><i class="menu-icon fa '+item.icon+'"></i>&nbsp;&nbsp;' + item.name +
                                '<div class="pull-right action-buttons">' +
                                '<div style="float: left; padding-right: 15px;">' +
                                '<input data-id="'+item.pkid+'" name="switch-field-1" '+checked+' class="ace ace-switch ace-switch-3" type="checkbox">' +
                                '<span class="lbl"></span>' +
                                '</div>' +
                                '<div style="float: left;">' +
                                '<a data-id="'+item.pkid+'" class="blue" href="#">' +
                                '<i class="ace-icon fa fa-pencil bigger-130"></i>' +
                                '</a>' +
                                '<a data-id="'+item.pkid+'" class="red" href="#">' +
                                '<i class="ace-icon fa fa-trash-o bigger-130"></i>' +
                                '</a>' +
                                '</div>' +
                                '</div>' +
                                '</div>' +
                                '</li>');
                    }
                }
                $("#olAppSubMenu").html(htmlArray.join(""));
                initAppSubMenuDragSort();
                initAppSubMenuEvent();
            }
        });
    }

    function initAppSubMenuDragSort()
    {
        var foo = document.getElementById("olAppSubMenu");
        Sortable.create(foo,{
            animation: 150, // ms, animation speed moving items when sorting, `0` — without animation
            onUpdate: function (evt){
                var orderParam = {};
                $("#olAppSubMenu .dd-item").each(function (i, item) {
                    var key = $(this).attr("data-id");
                    orderParam[key] =i;
                });
                var orderStr = StringUtil.Base64Encode(jsonToString(orderParam));
                jQuery.ajax({
                    type: "POST",
                    data: {"menuId":currentAppMenuId,"orderParam":orderStr},
                    dataType: "text",
                    url: "/manage/basic/app/saveAppSubMenuOrder",
                    success: function (result) {
                        layer.msg("已保存当前子菜单排序", {icon:1});
                    }
                });
            }
        });
    }

    function initAppSubMenuEvent()
    {
        $("#olAppSubMenu").find("input:checkbox").off().on('click', function(){
            var id = $(this).attr("data-id");
            var status = this.checked? 1:0;
            jQuery.ajax({
                type: "POST",
                data: {"id":id,"status":status},
                dataType: "text",
                url: "/manage/basic/app/setAppSubMenuStatus",
                success: function (result) {
                    layer.msg("已保存当前子菜单状态", {icon:1});
                }
            });
            event.stopPropagation();
        });

        //编辑应用事件
        $("#olAppSubMenu .blue").off().on("click", function () {
            var id = $(this).attr("data-id");
            layer_show("编辑子菜单", "/manage/basic/app/subMenuEdit?id=" + id, 600, 440);
            event.stopPropagation();
        });

        //删除应用事件
        $("#olAppSubMenu .red").off().on("click", function () {
            var id = $(this).attr("data-id");
            layer.confirm("确定删除该应用菜单？", function (index) {
                layer.close(index);
                jQuery.ajax({
                    type: "POST",
                    data: {"id":id},
                    dataType: "text",
                    url: "/manage/basic/app/doSubMenuDel",
                    success: function (result) {
                        layer.msg("已删除应用菜单", {icon: 1, time: 1000}, function () {
                            initAppSubMenuList();
                        });
                    }
                });
            });
            event.stopPropagation();
        });
    }
</script>
