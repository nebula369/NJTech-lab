//通用js
//班级信息
var classInfo ={schoolId:undefined,schoolName:undefined,schoolLogo:undefined,classId:undefined,className:undefined,studentcount:undefined,stageId:undefined,spaceId:undefined,spaceName:undefined};
//获取终端名称，关联场地关联班级
function  InitClassInfo() {
    $(".classinfo .schoolimg").html("");
    $(".classinfo .schoolname").html("");
    $(".classinfo .classname").html("");
    var  name=getQueryVariable("name");
    var  code=getQueryVariable("code");
    if(name!=false)
    {
        jQuery.ajax({
            type: "POST",
            data: {"terminalname":decodeURI(name),"code":code},
            dataType: "text",
            url: "/service/eclassbrand/getclassorspaceinfo",
            async:false,//同步
            cache:false,
            success: function (result) {
                classInfo=eval("(" + result + ")");
                $(".classinfo .schoolimg").html(classInfo.schoolLogo);
                $(".classinfo .schoolname").html(classInfo.schoolName);
                $(".classinfo .classname").html(classInfo.className);
                $(".peoplecount").html(classInfo.studentcount+"人");
                //console.log(result);
            },
            error:function () {
            }
        });
    }
}


//日期
function clock(){
    $(".clockWeek").html("星期" + "日一二三四五六".charAt(new Date().getDay()));
    $(".clockDate").html(new Date().Format("yyyy/MM/dd"));//时间
    $(".clockTime").html(new Date().Format("HH:mm"));//时间
    setTimeout("clock()", 1000);
}
// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
function DateDifference(faultDate,completeTime){
    // let d1 = new Date(faultDate);
    // let d2 = new Date(completeTime);
    var stime =new Date(faultDate).getTime();
    var etime = new Date(completeTime).getTime();
    var usedTime = etime - stime;  //两个时间戳相差的毫秒数
    var days=Math.floor(usedTime/(24*3600*1000));
    //计算出小时数
    var leave1=usedTime%(24*3600*1000);    //计算天数后剩余的毫秒数
    var hours=Math.floor(leave1/(3600*1000));
    //计算相差分钟数
    var leave2=leave1%(3600*1000);        //计算小时数后剩余的毫秒数
    var minutes=Math.floor(leave2/(60*1000));
     var time = hours+":"+minutes;
    //var time = days;
    return time;
}
//底部页面跳转
function changeurl(url,para) {
    location.href = url+"?name="+ decodeURI(getQueryVariable("name"))+"&code="+ getQueryVariable("code")+"&"+para;
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
