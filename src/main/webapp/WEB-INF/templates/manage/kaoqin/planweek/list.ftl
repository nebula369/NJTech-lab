<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>

<!-- 加载顶部bar -->
<@topBar title="${title}" titleUrl="/manage/kaoqin/index" currentUser=user icon="${menuConfig.icon}"/>

<div class="main-container ace-save-state" id="main-container">

    <!-- 加载左侧菜单 -->
    <@leftMenu indexUrl="/manage/kaoqin/index" menuList=menuConfig.menuList currentUrl="/manage/kaoqin/planweek"/>

    <div class="main-content">
        <div class="main-content-inner">
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="/manage/kaoqin/index">导航页</a>
                    </li>
                    <li> <a href="#">人脸考勤</a></li>
                    <li class="active">周期计划</li>
                </ul>
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12" style="padding:0px;">
                        <div style="margin-bottom: 5px;">
                            <span style="float: left">
                                     <label style="width: 90px;padding-top: 5px" class="col-sm-1 no-padding-right" for="spaceId">当前场地：</label>
                                        <select class="chosen-select form-control" name="spaceId" onchange="initDataList();" id="spaceId" style="width: 200px;">
                                            <option value="0">请选择场地</option>
                                                    <#list spacelist as item>
                                                        <option value="${item.pkid}">${item.name}</option>
                                                    </#list>
                                        </select>
                               </span>
                            <span style="float: left">
                                  <label style="width: 90px;padding-top: 5px" class="col-sm-1 no-padding-right" for="weekTypeId">单双周：</label>
                                      <select class="chosen-select form-control" name="weekTypeId" onchange="initDataList();" id="weekTypeId" style="width: 200px;">
                                                      <option value="1" selected="selected">单周</option>
                                                      <option value="2"  >双周</option>
                                       </select>
                             </span>
                            <button id="btnAdd" style="float:left; margin-top: 5px; margin-left: 10px;" class="btn btn-success btn-mini radius-4"><i class="ace-icon fa fa-plus"></i>新增上课时间段</button>
                            <div style="clear:both;"></div>
                        </div>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th scope="col" align="center" style="width: 12.5%">时间\星期</th>
                                <th scope="col" style="width: 12.5%">星期一</th>
                                <th scope="col" style="width: 12.5%">星期二</th>
                                <th scope="col" style="width: 12.5%">星期三</th>
                                <th scope="col" style="width: 12.5%">星期四</th>
                                <th scope="col" style="width: 12.5%">星期五</th>
                                <th scope="col" style="width: 12.5%">星期六</th>
                                <th scope="col" style="width: 12.5%">星期日</th>
                            </tr>
                            </thead>
                            <tbody id="tbList">

                            </tbody>
                        </table>
                    </div><!-- /.col -->
                </div><!-- /.row -->

            </div><!-- /.page-content -->
        </div>
    </div>

    <!-- 加载底部版权 -->
    <#include "/controls/footer.ftl"/>

</div>

<#include "/controls/appbottom.ftl"/>
<script src="/assets/lib/js/jquery.jqGrid.min.js"></script>
<script src="/assets/lib/js/grid.locale-cn.js"></script>

<script type="text/javascript">
    var  currweek;
    var  currbegintime;
    var  currendtime;
    function initDataList()
    {
        var spaceId= $("#spaceId").val();
        if(spaceId=="0")
        {
            return ;
        }
        var weekTypeId= $("#weekTypeId").val();
        $('#tbList').html("");
        jQuery.ajax({
            type: "POST",
            data: {"spaceId":spaceId,"weekTypeId":+weekTypeId},
            dataType: "text",
            url: "/manage/kaoqin/planweek/getPlanWeekScheduleList",
            success: function (result) {
                var obj=JSON.parse(result);
                if(obj.success==false){   layer.msg(obj.msg,{icon:2}); return ;}
                var list=obj.data;  var htmlArray = [];
                for(var i=0;i<list.length;i++){
                    var item = list[i];
                   // var time="'"+ StringUtil.Base64Encode(item.time)+"'" ;
                    var time="'"+ item.time+"'" ;
                    htmlArray.push("<tr id=\"tr_"+(i+1)+"\">" +
                        "           <th class=\"indexrow\" scope=\"row\" style=\"width: 12.5%\"><span id=\"divtime_"+(i+1)+"\">"+item.time+"</span><br />" +
                       "             <span class=\"sessiontime\"><a href=\"javaScript:setCurrWeek('"+item.time+"')\">设置</a></span>" +
                    "           </th>" +
                    "           <td style=\"width: 12.5%\">" +
                    "               <div class=\"js-text\" id=\"div_1_"+(i+1)+"\">"+getCurrTime(1,time, item.weekTimeList)+"</div>" +
                    "           </td>" +
                    "           <td style=\"width: 12.5%\">" +
                    "              <div class=\"js-text\" id=\"div_2_"+(i+1)+"\">"+getCurrTime(2,time, item.weekTimeList)+"</div>" +
                    "           </td>" +
                   "           <td style=\"width: 12.5%\">" +
                    "               <div class=\"js-text\" id=\"div_3_"+(i+1)+"\">"+getCurrTime(3,time, item.weekTimeList)+"</div>" +
                    "                   </div>" +
                    "          </td>" +
                    "           <td style=\"width: 12.5%\">" +
                        "              <div class=\"js-tex\" id=\"div_4_"+(i+1)+"\">"+getCurrTime(4,time, item.weekTimeList)+"</div>" +
                    "                 </div>" +
                    "           </td>" +
                    "           <td style=\"width: 12.5%\">" +
                        "               <div class=\"js-text\" id=\"div_5_"+(i+1)+"\">"+getCurrTime(5,time, item.weekTimeList)+"</div>" +
                    "              </div>" +
                    "          </td>" +
                   "           <td style=\"width: 12.5%\">" +
                    "               <div class=\"js-text\" id=\"div_6_"+(i+1)+"\">"+getCurrTime(6,time, item.weekTimeList)+"</div>" +
                    "           </td>" +
                   "           <td style=\"width: 12.5%\">" +
                    "               <div class=\"js-text\" id=\"div_0_"+(i+1)+"\" >"+getCurrTime(7,time, item.weekTimeList)+"</div>" +
                    "           </td>" +
                    "</tr>");
                }
                $('#tbList').html(htmlArray.join(""));
            }
        })
    }
    function getCurrTime(week,time, weekTimeList)
    {
        var result = "";
        for(var i=0; i<weekTimeList.length; i++)
        {
            var item = weekTimeList[i];
            if(item.week == week)
            {
                if(item.teachername!=""||item.subject!=""||item.groupnames!="")
                {
                    var info=item.teachername+"("+item.subject+")";
                    result = "<div>"+info+"</div><div>"+item.groupnames+"</div><div><a href=\"javaScript:editPlanWeek("+item.pkid+")\">设置</a></div>";
                } else{
                    result = "<div style=\"text-align: center\" onclick=\"javaScript:editPlanWeek("+item.pkid+")\"><i class=\"ace-icon fa fa-plus\"></i><div>";
                }
            }
        }
        if(result == ""&&time!="")
        {
            result="<div style=\"text-align: center\" onclick=\"javaScript:addPlanWeek("+week+","+time+")\"><i class=\"ace-icon fa fa-plus\"></i><div>"
        }
        return result;
    }

    function editPlanWeek(id) {
        layer_show("修改计划内容", "/manage/kaoqin/planweek/edit?id="+id, 550, 350,setCurrentLayerObj);
        //event.stopPropagation();
    }
    function addPlanWeek(week,time) {
        currweek=week;
        if(time!=null)
        {
            var currtime=time.split("-");
            currbegintime= "1901-01-01 "+currtime[0]+":00";
            currendtime=  "1901-01-01 "+currtime[1]+":00";
        }
         layer_show("新增计划内容", "/manage/kaoqin/planweek/add", 550, 350,setCurrentLayerObj);
    }


    function GetValue(types)
    {
        if(types=="spaceId")
        {
            return $("#spaceId").val();
        }
        if(types=="weekorder")
        {
            return $("#weekTypeId").val();
        }
        if(types=="weekno")
        {
            return currweek
        }
        if(currtime!="")
        {

        }
        return "";
    }
    function reloadData()
    {
        initDataList();
    }
    $(function () {
        $("#btnAdd").on("click", function () {
            var spaceId= $("#spaceId").val();
            if(spaceId=="0")
            {
                layer.msg("请选择场地",{icon:2}); return ;
            }
            layer_show("新增节次", "/manage/kaoqin/planweek/addtime?spaceId="+spaceId+"&weekTypeId="+$("#weekTypeId").val(), 450, 300);
            event.stopPropagation();
        });
    })


    //修改时间
    function setCurrWeek(time) {
        layer_show("修改节次", "/manage/kaoqin/planweek/edittime?spaceId="+$("#spaceId").val()+"&weekTypeId="+$("#weekTypeId").val()+"&timeStr="+time, 450, 300,setCurrentLayerObj);
       // event.stopPropagation();
    }
    var currentLayObj;
    function setCurrentLayerObj(obj) {
        currentLayObj = obj;
    }
    function setSelectedUser(selectUsers)
    {
        var iframeWin = getIframeWin(currentLayObj);
        iframeWin.setSelectedUser(selectUsers);
    }
</script>
