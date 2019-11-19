
var EASettings = {
    i18n: {
        editText: '<img src="/assets/home/images/edit.png" width="16" height="16" />',
        editTitle: '编辑当前控件属性',
        closeText: '<img src="/assets/home/images/close.png" width="16" height="16" />',
        closeTitle: '移除当前控件',
        collapseText: '<img src="/assets/home/images/collapse.png" width="16" height="16" />',
        collapseTitle: '收缩控件内容',
        extendTitle: '展开控件内容',
        cancelEditText: '<img src="/assets/home/images/edit.png" width="16" height="16" />',
        extendText: '<img src="/assets/home/images/extend.png" width="16" height="16" />',
        confirmMsg: '确定移除当前控件？'
    },
    callbacks: {
        onExtend: function () {  },
        onCollapse: function () { },
        onChangePositions: function () { InitWidgetPlaceHeight(); },
        onEditQuery: function () {
            var result = confirm("确定编辑？");
            if (result) {
                return true;
            }
            return false;
        },
        onCloseQuery: function (link, widget) {
            var result = confirm("确定移除当前控件？");
            if (result) {
                InitWidgetPlaceHeight();
                return true;
            }            
            return false;
        }
    }
};
$(function () {
    InitializeWidgets();
});
function InitializeWidgets() {
    $.fn.EasyWidgets(EASettings);
    InitWidgetPlaceHeight();
}

//初始化拖动区高度，如果拖动项，则高度为100，否则自适应
function InitWidgetPlaceHeight() {
    $(".widget-place").each(function (i, item) {
        $(this).css("border", "1px solid gray");
        $(this).css("width", $(this).width()-2);
        if ($(this).children().length == 0) {
            $(this).css("height", "150px");
        }
        else {
            $(this).css("height", "auto");
        }
    });
}

//添加控件时加载图片显示
function CreateLoading() {
    var a = $('<img id="vdloading" src="/assets/home/images/ajax-loader.gif"/>');
    a.appendTo("body");
    return a
}

