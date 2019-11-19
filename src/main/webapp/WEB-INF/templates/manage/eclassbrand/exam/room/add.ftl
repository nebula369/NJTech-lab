<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>
<link rel="stylesheet" href="/assets/lib/css/bootstrap-datepicker3.min.css" />

<div class="main-content">
    <div class="main-content-inner">
        <div class="page-content">
            <div class="row">
                <div class="col-xs-12">
                    <form class="form-horizontal" id="validation-form" role="form">
                        <table style="width: 100%;height: 620px" id="page-table">
                            <tr>
                                <td style="width: 40%;vertical-align: top;">
                                    <div class="form-group"  id="form-spacetypeList">
                                        <ul class="spacetypeList" id="spaceTypeList" style="">
                                        <#list spaceTypeList as item>
                                            <li  class="subNavItem  <#if item.pkid == spacetypeid >SelectedNavItem</#if> " id="${item.pkid}" onclick="initSpaceList(this)" >
                                                <i class="ace-icon  fa fa-building"></i>
                                                <div>${item.name}</div>
                                            </li>
                                        </#list>
                                        </ul>
                                    </div>
                                </td>
                                <td  style="width: 0.5%; background-color: #858585;vertical-align: top;">

                                </td>
                                <td style="width: 50%;vertical-align: top;>
                                    <div class="form-group" id="form-spaceList">
                                   <ul class="spaceList" id="spaceList">

                                    </ul>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <style>
        #page-table ul {
            list-style: none;
        }
        #page-table ul li{
            float: left;
            cursor: pointer;
            text-align: center;
            padding: 5px;
            margin: 5px;
        }
        .subNavItem{
            border: 1px #858585 solid;
        }
        .SelectedNavItem{
            border: 1px  #1E9FFF solid !important;
            color: #1E9FFF;
        }
        .space-list{  padding: 5px;
            border: 1px #858585 solid
        }
        .curr{  padding: 5px;
            background-color:  #1e9fff;
            color: white;
        }
    </style>
</div>

<#include "/controls/appbottom.ftl"/>
<script src="/assets/lib/js/moment.min.js"></script>
<script src="/assets/lib/js/jquery.validate.min.js"></script>
<script src="/assets/lib/js/chosen.jquery.min.js"></script>
<script type="text/javascript">
    var currExamRoomList="";
    var currExamRoom={};
    var spaceid;
    var typeId;
    initExamRoom(${spacetypeid});
    function initSpaceList(obj) {
         $("#spaceTypeList li").removeClass();
         $("#spaceTypeList li").addClass("subNavItem");
         $(obj).removeClass().addClass("SelectedNavItem");
             typeId = $(obj).attr("id");
             initExamRoom(typeId);
        }

    function initEdit(obj)
    {
        $("#spaceList li").removeClass();
        $("#spaceList li").addClass("subNavItem");
        $(obj).removeClass().addClass("SelectedNavItem");
         spaceid= $(obj).attr("id");
        currExamRoom=currExamRoomList.find(item => { return item.spaceid == spaceid });
        layer_show("编辑考场", "/manage/eclassbrand/examroom/edit?id=" + currExamRoom.pkid+"&spaceid="+spaceid, 550, 380);
        event.stopPropagation();
    }

    function initExamRoom(typeId)
    {
        if(typeId=="")typeId=0;
        jQuery.ajax({
            type: "POST",
            data: {"typeid":typeId},
            dataType: "json",
            url: "/manage/eclassbrand/examroom/getExamRoomListForType",
            success: function (result) {
                var html = '';
                currExamRoomList=result;
                for(var i=0;i<result.length;i++){
                    html +="<li class=\"space-list\"  id=\""+result[i].spaceid+"\"   onclick=\"initEdit(this)\" > <div   >"+result[i].spacename+"</div>" +
                        "<div id=\""+result[i].pkid+"\">"+result[i].name+"</div></li>";
                }
                $('#spaceList').html(html);
            },
            error:function () {

            }
        });
    }


</script>
