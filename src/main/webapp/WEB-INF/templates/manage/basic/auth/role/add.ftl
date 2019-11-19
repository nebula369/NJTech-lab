<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>
<link rel="stylesheet" type="text/css" href="/assets/scripts/dtree/css/ui.css" />

<div class="main-content">
    <div class="main-content-inner">
        <div class="page-content">
            <div class="row">
                <div class="col-xs-12">
                    <form class="form-horizontal" id="validation-form" role="form">
                        <div class="row">
                        <div class="col-xs-12"  style="width: 490px;">
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="name">角色名称:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control" value="" id="name" name="name" style="width: 300px;" type="text">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="roleuser">角色用户:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <textarea class="form-control" id="roleuser" onclick="selectUser();" style="width: 350px;height: 130px;" placeholder="点击选择角色用户" readonly></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float: left;width:100px" for="status">状态:</label>
                            <div class="col-sm-9" style="float: left;">
                                <label>
                                    <input name="status" id="status" checked class="ace ace-switch ace-switch-3" type="checkbox">
                                    <span class="lbl"></span>
                                </label>
                            </div>
                        </div>
                        <div class="form-group" id="stage">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 10px;width: 100px;" for="stage">排序号:</label>
                            <div class="col-sm-9" style="float: left; margin-top: 5px;">
                                <div class="clearfix" style="float:left;">
                                    <input class="form-control" style="width: 100px" value="1" id="sortnum" name="sortnum" type="text">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 no-padding-right text-right" style="float:left;padding-top: 5px;width: 100px;" for="remark">角色说明:</label>
                            <div class="col-sm-9" style="float: left;">
                                <div class="clearfix" style="float:left;">
                                    <textarea class="form-control limited" style="width: 350px;height: 80px;" placeholder="最多输入50个字符" id="remark" maxlength="50"></textarea>
                                </div>
                            </div>
                        </div>
                        </div>
                        <div class="col-xs-12" style="width: 220px;">
                            <div id="tree" style="height: 400px; width:220px; overflow: auto">

                            </div>
                        </div>
                        </div>
                        <div class="col-md-offset-3 col-md-9" style="margin-left: 90px;">
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
<script src="/assets/lib/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="/assets/scripts/dtree/core.js"></script>
<script type="text/javascript" src="/assets/scripts/dtree/ui.js"></script>
<script type="text/javascript" src="/assets/scripts/dtree/cookie.js"></script>
<script type="text/javascript" src="/assets/scripts/dtree/dynatree.js"></script>
<script type="text/javascript" src="/assets/scripts/dtree/dtree.js"></script>

<script type="text/javascript">
    jQuery(function ($) {

        var tree = $('#tree');
        tree.mac('dtree', {
            treeConfig: {
                title: "",
                rootVisible: false,
                clickFolderMode: 3,
                selectMode:3,
                checkbox:true,
                onSelect: function (flag, dtnode) { dtnode.expand(true); },
                activeVisible:false
                //autoCollapse:true
            },
            loader: {
                url: '/manage/basic/userAuth/getAuthModuleList?userId=0&roleId=0&type=1',
                params: { id: '0',level:0 },
                autoLoad: true
            }
        });

        $('#validation-form').validate({
            errorElement: 'div',
            errorClass: 'help-block',
            focusInvalid: true,
            ignore: "",
            rules: {
                name: {
                    required: true
                },
                sortnum:{
                    required: true,
                    digits: true
                }
            },

            messages: {
                name: {
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
                //在easybasic.js中定义
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

            var treeSelectedNodes = $('#tree').dynatree("getTree").getSelectedNodes();
            var selectAppList = $.map(treeSelectedNodes, function (node) {
                if (node.data.code == -1) {
                    return node.data.key;
                }
            });
            var selectMenuList = $.map(treeSelectedNodes, function (node) {
                if (node.data.code == 1) {
                    return node.data.key;
                }
            });
            var selectItemList = $.map(treeSelectedNodes, function (node) {
                if (node.data.code == 2) {
                    return node.data.key;
                }
            });
            var status = $("#status")[0].checked ? 1:0;
            jQuery.ajax({
                type: "POST",
                data: {"name":$("#name").val(),"remark":$("#remark").val(),"modulepurviews":selectAppList.join(","),
                "menupurviews":selectMenuList.join(","),"itempurviews":selectItemList.join(","),"sortnum":$("#sortnum").val(),"status":status,"roleUserIds":selectRoleUserIds.join(",")},
                dataType: "text",
                url: "/manage/basic/roleAuth/doAdd",
                success: function (result) {
                    layer.msg("保存成功", {icon: 1, time: 1000}, function () {
                        parent.reloadData();
                        layer_close();
                    });
                },
                error:function () {
                    obj.removeClass("disabled");
                }
            });
        });
    });

    function selectUser()
    {
        parent.layer_show("选择用户", "/manage/basic/common/selectUser?type=0", 900, 600);
        event.stopPropagation();
    }

    var selectRoleUserIds = [];
    var selectRoleUserNames = [];
    function setSelectedUser(selectUsers)
    {
        var userList = selectUsers.split(",");
        for(var i=0;i<userList.length; i++)
        {
            var users = userList[i].split("@");
            if(selectRoleUserIds.indexOf(users[0])==-1) {
                selectRoleUserIds.push(users[0]);
                selectRoleUserNames.push(users[1]);
            }
        }
        $("#roleuser").val(selectRoleUserNames.join("，"));
    }
</script>
