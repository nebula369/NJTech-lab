<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<style>
    .item{
        float: left;
        margin: 10px;
        cursor: pointer;
        padding: 0 10px;
        height: 30px;
        line-height: 30px;
    }
    .itemcurr, .item:hover{
        color: white;
        background: #2E8965;
        border-radius: 5px;
    }
</style>
<div class="main-content">
    <div class="main-content-inner">
        <div class="page-content">
            <div class="row" style="height: 390px;">
                <div class="col-xs-12">
                    <#list list as item>
                        <div class="item <#if item.teacherId == teacherId && item.subjectId=subjectId >itemcurr</#if>" onclick="setSubjectTeacher(this);"  data-teacherId="${item.teacherId}" data-subjectId="${item.subjectId}">${item.teacherName}(${item.subjectName})</div>
                    </#list>
                </div>
            </div>
        </div>
    </div>
</div>
<#include "/controls/appbottom.ftl"/>
<script type="text/javascript">
    var oldteacherId=${teacherId};
    var oldsubjectId=${subjectId};
    function setSubjectTeacher(obj) {
        var teacherId = $(obj).attr("data-teacherId");
        var subjectId = $(obj).attr("data-subjectId");
        if((teacherId==oldteacherId)&&(subjectId==oldsubjectId))
        {
            layer.confirm("确定删除？", function (index) {
                layer.close(index);
                setClassSubjectTeacher(teacherId,subjectId,2);//删除
            })
        }
        else{
            setClassSubjectTeacher(teacherId,subjectId,1);//删除
        }
    }

    function setClassSubjectTeacher(teacherId,subjectId,type) {
        jQuery.ajax({
            type: "POST",
            data: {"schoolId":${schoolId},"classId":${classId},"week":${week},"session":${session},"teacherId":teacherId,"subjectId":subjectId,"type":type},
            dataType: "text",
            url: "/manage/edu/curriculum/setClassSubjectTeacher",
            success: function (result) {
                if(result == 1) {
                    var msg="保存成功";
                    if(type==2)
                    { msg="删除成功";
                    }
                    layer.msg(msg, {icon: 1, time: 1000}, function () {
                        parent.initClassCurriclumData();
                        layer_close();
                    });
                }
            },
            error:function () {
                obj.removeClass("disabled");
            }
        });
    }
</script>
