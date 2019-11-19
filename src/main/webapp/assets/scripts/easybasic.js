
var rowNum = 15;
var rowList = [15, 30, 50];

function getRowNum()
{
    //这里可以处理cookie
    return rowNum;
}

/**
 * 修改url中的参数值
 * @param url
 * @param arg
 * @param val
 * @returns {any}
 */
function changeUrlArg(url, arg, val){
    var pattern = arg+'=([^&]*)';
    var replaceText = arg+'='+val;
    return url.match(pattern) ? url.replace(eval('/('+ arg+'=)([^&]*)/gi'), replaceText) : (url.match('[\?]') ? url+'&'+replaceText : url+'?'+replaceText);
}

/**
 * 获取url指定参数的值
 * @param name
 * @returns {*}
 */
function getQueryString(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}

var StringUtil = new function () {

    this.Base64Encode = function (str) {
        if (!str || str == '') {
            return "";
        }
        return base64encode(utf16to8(str));
    };

    this.Base64Decode = function (str) {
        if (!str || str == '') {
            return "";
        }
        return utf8to16(base64decode(str));
    };

    var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    var base64DecodeChars = new Array(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);
    var base64encode = function (str) {
        var out, i, len;
        var c1, c2, c3;
        len = str.length;
        i = 0;
        out = "";
        while (i < len) {
            c1 = str.charCodeAt(i++) & 0xff;
            if (i == len) {
                out += base64EncodeChars.charAt(c1 >> 2);
                out += base64EncodeChars.charAt((c1 & 0x3) << 4);
                out += "==";
                break;
            }
            c2 = str.charCodeAt(i++);
            if (i == len) {
                out += base64EncodeChars.charAt(c1 >> 2);
                out += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
                out += base64EncodeChars.charAt((c2 & 0xF) << 2);
                out += "=";
                break;
            }
            c3 = str.charCodeAt(i++);
            out += base64EncodeChars.charAt(c1 >> 2);
            out += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
            out += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >> 6));
            out += base64EncodeChars.charAt(c3 & 0x3F);
        }
        return out;
    };

    var base64decode = function (str) {
        var c1, c2, c3, c4;
        var i, len, out;
        len = str.length;
        i = 0;
        out = "";
        while (i < len) {
            do {
                c1 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
            } while (i < len && c1 == -1);
            if (c1 == -1)
                break;
            do {
                c2 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
            } while (i < len && c2 == -1);
            if (c2 == -1)
                break;
            out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));
            do {
                c3 = str.charCodeAt(i++) & 0xff;
                if (c3 == 61)
                    return out;
                c3 = base64DecodeChars[c3];
            } while (i < len && c3 == -1);
            if (c3 == -1)
                break;
            out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));
            do {
                c4 = str.charCodeAt(i++) & 0xff;
                if (c4 == 61)
                    return out;
                c4 = base64DecodeChars[c4];
            } while (i < len && c4 == -1);
            if (c4 == -1)
                break;
            out += String.fromCharCode(((c3 & 0x03) << 6) | c4);
        }
        return out;
    };
    var utf16to8 = function (str) {
        var out, i, len, c;
        out = "";
        len = str.length;
        for (i = 0; i < len; i++) {
            c = str.charCodeAt(i);
            if ((c >= 0x0001) && (c <= 0x007F)) {
                out += str.charAt(i);
            } else if (c > 0x07FF) {
                out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
                out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
            } else {
                out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
            }
        }
        return out;
    };
    var utf8to16 = function (str) {
        var out, i, len, c;
        var char2, char3;
        out = "";
        len = str.length;
        i = 0;
        while (i < len) {
            c = str.charCodeAt(i++);
            switch (c >> 4) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    out += str.charAt(i - 1);
                    break;
                case 12:
                case 13:
                    char2 = str.charCodeAt(i++);
                    out += String.fromCharCode(((c & 0x1F) << 6) | (char2 & 0x3F));
                    break;
                case 14:
                    char2 = str.charCodeAt(i++);
                    char3 = str.charCodeAt(i++);
                    out += String.fromCharCode(((c & 0x0F) << 12) | ((char2 & 0x3F) << 6) | ((char3 & 0x3F) << 0));
                    break;
            }
        }
        return out;
    };
};


//Json对象转换为字符串
function jsonToString(obj) {
    var THIS = this;
    switch (typeof (obj)) {
        case 'string':
            return '"' + obj.replace(/(["\\])/g, '\\$1') + '"';
        case 'array':
            return '[' + obj.map(THIS.jsonToString).join(',') + ']';
        case 'object':
            if (obj instanceof Array) {
                var strArr = [];
                var len = obj.length;
                for (var i = 0; i < len; i++) {
                    strArr.push(THIS.jsonToString(obj[i]));
                }
                return '[' + strArr.join(',') + ']';
            } else if (obj == null) {
                return 'null';

            } else {
                var string = [];
                for (var property in obj) string.push(THIS.jsonToString(property) + ':' + THIS.jsonToString(obj[property]));
                return '{' + string.join(',') + '}';
            }
        case 'number':
            return obj;
        case 'boolean':
            return obj;
        case false:
            return obj;
    }
}



//JSON字符串转换为json对象
function stringToJSON(obj) {
    try {
        return eval('(' + obj + ')');
    }
    catch (e) {
        return "";
    }
}


function inArray(str, stringarray) {
    var result = false;
    if (!stringarray) return false;
    var list = stringarray.split(",");
    for (var i = 0; i < list.length; i++) {
        if (str == list[i]) {
            result = true;
            break;
        }
    }
    return result;
}


/****************************************************************
 * 常用属性重写
 ****************************************************************/
Date.prototype.formatt = function (format) //author: meizz
{

    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(),    //day
        "h+": this.getHours(),   //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
        "S": this.getMilliseconds() //millisecond
    };
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}


/******************************************************************/
//cookie操作
/******************************************************************/
function GetCookie(sName) {
    var aCookie = document.cookie.split(";");
    for (var i = 0; i < aCookie.length; i++) {
        var aCrumb = aCookie[i].split("=");

        if (sName.Trim() == aCrumb[0].Trim())
            return unescape(aCrumb[1].Trim());
    }
    return null;
}

function SetCookie(name, value) {
    var argv = arguments;
    var argc = arguments.length;
    var expires = (argc > 2) ? argv[2] : null;
    var path = (argc > 3) ? argv[3] : '/';
    var domain = (argc > 4) ? argv[4] : null;
    var secure = (argc > 5) ? argv[5] : false;
    document.cookie = name + "=" + escape(value) +
        ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) +
        ((path == null) ? "" : ("; path=" + path)) +
        ((domain == null) ? "" : ("; domain=" + domain)) +
        ((secure == true) ? "; secure" : "");
}


String.prototype.Trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
};
String.prototype.LTrim = function () {
    return this.replace(/(^\s*)/g, "");
};
String.prototype.RTrim = function () {
    return this.replace(/(\s*$)/g, "");
};
String.prototype.IsNullOrEmpty = function () {
    if (this.LTrim().RTrim().length == 0) {
        return true;
    } else {
        return false;
    }
};


//比较二个时间大小：>返回1；=返回0；<返回-1
function CompareTime(startTime, endTime) {
    var sts = startTime.split(":");
    var ets = endTime.split(":");
    if(parseInt(sts[0]) > parseInt(ets[0])) {
        return 1;
    }
    if (parseInt(sts[0]) < parseInt(ets[0])) {
        return -1;
    }
    if (parseInt(sts[1]) > parseInt(ets[1])) {
        return 1;
    }
    if (parseInt(sts[1]) < parseInt(ets[1])) {
        return -1;
    }
    return 0;
}


//比较二个时间范围是否有重叠
function CheckTimeRangeOver(time1, time2) {
    var t1 = time1.split("-");
    var t2 = time2.split("-");
    if (t1.length != 2 || t2.length != 2) return true;
    if(CompareTime(t1[0], t2[0])>0 && CompareTime(t1[0], t2[1])<0) {
        return true;
    }
    if (CompareTime(t1[1], t2[0]) > 0 && CompareTime(t1[1], t2[1]) < 0) {
        return true;
    }
    if (CompareTime(t2[0], t1[0]) > 0 && CompareTime(t2[0], t1[1]) < 0) {
        return true;
    }
    if (CompareTime(t2[1], t1[0]) > 0 && CompareTime(t2[1], t1[1]) < 0) {
        return true;
    }
    return false;
}

//比较时间是否有重叠
function CheckTimeRangeOverForList(times, setime) {
    var t = times.split(",");
    var result = false;
    for(var i=0; i<t.length; i++) {
        if(CheckTimeRangeOver(t[i], setime)) {
            result = true;
            break;
        }
    }
    return result;
}

function getFileName(path){
    var pos1 = path.lastIndexOf('/');
    var pos2 = path.lastIndexOf('\\');
    var pos  = Math.max(pos1, pos2)
    if( pos<0 )
        return path;
    else
        return path.substring(pos+1);
}

function getFileNameWithoutExt(text) {
    var pattern = /\.{1}[a-z]{1,}$/;
    if (pattern.exec(text) !== null) {
        return (text.slice(0, pattern.exec(text).index));
    } else {
        return text;
    }
}

function dateCompare(date1,date2){
    var oDate1 = new Date(date1);
    var oDate2 = new Date(date2);
    if(oDate1.getTime() > oDate2.getTime()){
        return 1;
    } else {
        return -1;
    }
    return 0;
}


/*弹出层*/
/*
    参数解释：
    title   标题
    url     请求的url
    id      需要操作的数据id
    w       弹出层宽度（缺省调默认值）
    h       弹出层高度（缺省调默认值）
*/
function layer_show(title,url,w,h,setCurrentLayerObj){
    if (title == null || title == '') {
        title=false;
    };
    if (url == null || url == '') {
        url="404.html";
    };
    if (w == null || w == '') {
        w=($(window).width()*0.9);
    };
    if (h == null || h == '') {
        h=($(window).height() - 50);
    };
    layer.open({
        type: 2,
        area: [w+'px', h +'px'],
        fix: false, //不固定
        maxmin: true,
        shadeClose: true,
        shade:0.4,
        title: title,
        content: url,
        success: function (layero, index1) {
            if (setCurrentLayerObj) setCurrentLayerObj(layero);
        }
    });
}

/*关闭弹出框口*/
function layer_close(){
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}

function getIframeWin(currentLayObj) {
    var iframeWin = window[currentLayObj.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：
    return iframeWin;
}

/**
 * 删除文件和文件名称
 * @param dirpath:文件路径：删除整个目录文件夹
 * @param filename：文件名称：删除文件
 */
function delFile(dirpath,filename)
{
    jQuery.ajax({
        type: "POST",
        data: {"dirpath":dirpath,"filename":filename},
        dataType: "text",
        url: "/manage/upload/delfile",
        success: function (result) {
            if(result == 1) {
                layer.msg("删除成功！",{icon:1});
            }
            else
            {
                layer.msg("删除失败！",{icon:2});
            }
        },
        error:function () {
        }
    });
}
/**
 * 上传时出现进度条的插件定义，在upload.js中调用
 * @param fun
 * @returns {Function}
 */
var xhrOnProgress=function(fun) {
    xhrOnProgress.onprogress = fun; //绑定监听
    //使用闭包实现监听绑
    return function() {
        //通过$.ajaxSettings.xhr();获得XMLHttpRequest对象
        var xhr = $.ajaxSettings.xhr();
        //判断监听函数是否为函数
        if (typeof xhrOnProgress.onprogress !== 'function')
            return xhr;
        //如果有监听函数并且xhr对象支持绑定时就把监听函数绑定上去
        if (xhrOnProgress.onprogress && xhr.upload) {
            xhr.upload.onprogress = xhrOnProgress.onprogress;
        }
        return xhr;
    }
}
/**
 * 初始化表单验证错误信息格式
 * @param error
 * @param element
 */
function initErrorPlacement(error, element)
{
    error.addClass("fa fa-exclamation-circle");
    if(element.is('input[type=checkbox]') || element.is('input[type=radio]')) {
        error.attr("style","float:left;margin-left:5px;");
        error.insertAfter(element.parent().parent().parent());
    }
    //else if(element.is('.select2')) {
    //    error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
    //}
    //else if(element.is('.chosen-select')) {
    //    error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
    //}
    else {
        error.attr("style","float:left;margin-left:5px;padding-top:5px;");
        error.insertAfter(element.parent());
    }
}

jQuery(document).ready(function () {
    $('textarea.limited').inputlimiter({
        remText: '%n 字符 剩余...',
        limitText: '最多允许字符 : %n.'
    });
    window.setInterval(function () {
        jQuery.ajax({
            type: "POST",
            data: {},
            dataType: "json",
            url: "/manage/validateOnlineUser"
        });
    },5000);
});
