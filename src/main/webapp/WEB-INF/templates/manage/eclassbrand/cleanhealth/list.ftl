<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<#include "/controls/leftmenu.ftl"/>

<!-- 加载顶部bar -->
<@topBar title="${title}" titleUrl="/manage/eclassbrand/index" currentUser=user icon="${menuConfig.icon}"/>

<div class="main-container ace-save-state" id="main-container">

    <!-- 加载左侧菜单 -->
    <@leftMenu indexUrl="/manage/eclassbrand/index" menuList=menuConfig.menuList currentUrl="/manage/eclassbrand/cleanhealth"/>

    <div class="main-content">
        <div class="main-content-inner">
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="/manage/eclassbrand/index">导航页</a>
                    </li>

                    <li>
                        <a href="#">班牌模式</a>
                    </li>
                    <li class="active">值日</li>
                </ul>
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12" style="padding:0px;">
                        <div style="margin-bottom: 5px;">
                            <label style="padding-top: 5px;float:left;"  for="year">当前班级：</label>
                            <select class="chosen-select form-control" onchange="initDataList();" name="classId" id="classId" style="width: 200px; float:left;">
                                <option value="0">请选择班级</option>
                                <#list classList as item>
                                    <option value="${item.pkid}">${item.name}</option>
                                </#list>
                            </select>
                            <div style="clear:both;"></div>
                        </div>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th scope="col" align="center" style="width: 20%">星期</th>
                                <th scope="col" style="width: 80%">值日内容</th>
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
    function initDataList()
    {
        var classId = $("#classId").val();
        $('#tbList').html("");
        if($.trim(classId) == ""||classId=="0") return;
        jQuery.ajax({
            type: "POST",
            data: {"classid": classId},
            dataType: "json",
            url: "/manage/eclassbrand/cleanhealth/getCleanHealthList",
            success: function (result) {
                var htmlArray = [];
                for(var i=1;i<=7;i++){
                    if(result.length==0)
                    {
                        htmlArray.push(getAddContent(i.toString()));
                    }
                    else
                    {
                        if(getContent(i,result)!="")
                        {
                            htmlArray.push(getContent(i.toString(),result));
                        }
                        else {
                            htmlArray.push(getAddContent(i.toString()));
                        }
                    }
                }
                $('#tbList').html(htmlArray.join(""));
            },
            error:function () {

            }
        });
    }
    function getContent(day, cleanHealthList)
    {
        var result = "";  var week=GetDay(day);
        for(var i=0; i<cleanHealthList.length; i++)
        {
            var item = cleanHealthList[i];
            if(item.day == day)
            {
                 result = "<tr >" +
                    "           <th class=\"indexrow\" scope=\"row\" style=\"width: 20%\"><span >"+week+"</span><br />" +
                    "           </th>" +
                    "           <td style=\"width: 80%\">" +
                    "               <div class=\"js-text\">"+item.content+"</div>" +
                    "                    <div class=\"js-action\" >" +
                    "                        <a href=\"#\" onclick=\"setCurrDay('"+item.pkid+"');\">修改</a>" +
                   "                  </div>" +
                    "           </td>" +
                    "</tr>";
            }
        }
        return result;
    }
    function getAddContent(day)
    {
        var week=GetDay(day);
        var result = "<tr >" +
            "           <th class=\"indexrow\" scope=\"row\" style=\"width: 20%\"><span >"+week+"</span><br />" +
            "           </th>" +
            "           <td style=\"width: 80%\">" +
            "               <div class=\"js-text\"></div>" +
            "                    <div class=\"js-action\"  >" +
            "                        <a href=\"#\"  onclick=\"addCurrDay('"+day+"')\">添加</a>" +
            "                  </div>" +
            "           </td>" +
            "</tr>";
        return result;
    }

    function GetDay(week)
    { var result="";
     switch (week) {
    case "1":result="星期一";break;
    case "2":result="星期二";break;
    case "3":result="星期三";break;
    case "4":result="星期四";break;
    case "5":result="星期五";break;
    case "6":result="星期六";break;
    case "7":result="星期天";break;
       }
          return result;
    }
    function setCurrDay(id) {
        layer_show("修改值日内容", "/manage/eclassbrand/cleanhealth/edit?id=" + id , 550, 270);
        event.stopPropagation();
    }
    function addCurrDay(day)
    {
        var classId = $("#classId").val();
        layer_show("添加值日内容", "/manage/eclassbrand/cleanhealth/add?classid=" + classId +"&day="+day, 550, 270);
        event.stopPropagation();
    }
    function reloadData() {
        initDataList();
    }
</script>
