/** 功能函数 * */
// 基本功能函数
var $$U = (function() {
    _base64 = function(string) { // NOPMD
        var keyStr = CryptoJS.enc.Utf8.parse(string);
        return CryptoJS.enc.Base64.stringify(keyStr);
    };
    _message = function(id, show, error) {
        if (show) {
            jQuery("#" + id).html(error);
            jQuery("#" + id).removeClass("invisible");
        } else {
            jQuery("#" + id).addClass("invisible");
        }
    };
    _complete = function(show, hidden, message) {
        _reset(show, hidden);
        jQuery("#" + show).children("span").html(message);
        jQuery("#" + show).fadeIn("slow", function() {
            window.setTimeout(function() {
                jQuery("#" + show).fadeOut("slow");
            }, 15000);
        });
    };
    _reset = function(sid, eid) {
        jQuery("#" + sid).children("span").html();
        jQuery("#" + eid).children("span").html();
        jQuery("#" + sid).fadeOut("fast");
        jQuery("#" + eid).fadeOut("fast");
    }
    return function() {
        return {};
    }
})();
// 获取远程API函数
var API = (function() {
    _uri = function(api) {
        var apiUrl = jQuery("#hdApi").val();
        return apiUrl + api;
    };
    return function() {
        return {};
    }
})();
var BTN = (function() {
    _before = function(button, text) {
        jQuery(button).addClass("disabled");
        jQuery(button).html(text);
    };
    _after = function(button, text) {
        jQuery(button).removeClass("disabled");
        jQuery(button).html(text);
    };
    _checked = function(boxId, cls) {
        // 判断CheckBox是否选中
        var value = jQuery(boxId + ":checked").length;
        if (true == value) {
            jQuery(cls).removeAttr("readonly");
        } else {
            jQuery(cls).attr("readonly","readonly");
        }
    };
    _active = function(id,cls){
        if(undefined !== jQuery(cls)){
            jQuery(cls).removeClass("active");
        }
        if(undefined !== jQuery(id)){
            jQuery(id).addClass("active");
        }
    };
    _show = function(id, cls){
        if(undefined !== jQuery(cls)){
            jQuery(cls).addClass("hidden");
        }
        if(undefined !== jQuery(id)){
            jQuery(id).removeClass("hidden");
        }
    }
    _shift = function(show,hidden){
        jQuery(hidden).addClass("hidden");
        jQuery(show).removeClass("hidden");
        
    };
    return function() {
        return {};
    }
})();
// =================下边是接口=======================
(function() {
    $$U = $$U.prototype = { // NOPMD
        base64 : _base64,
        message : _message,
        reset : _reset,
        complete : _complete
    }
})();
(function() {
    BTN = BTN.prototype = { // NOPMD
        before : _before,
        after : _after,
        checked : _checked,
        active : _active,
        shift : _shift,
        show : _show
    }
})();
(function() {
    API = API.prototype = {
        uri : _uri
    }
})();

// ==================进度条色差函数==================
// 进度条功能函数
var $$P = 0;
var $$STOP = null;
var $$FAIL = false;
// Failure
function failureP(obj) {
    $$FAIL = true;
    jQuery(obj).removeClass("progress-info");
    jQuery(obj).removeClass("progress-success");
    jQuery(obj).removeClass("active");
    jQuery(obj).addClass("progress-danger");
}
// Success
function successP(obj) {
    jQuery(obj).removeClass("progress-info");
    jQuery(obj).removeClass("progress-danger");
    jQuery(obj).removeClass("active");
    jQuery(obj).addClass("progress-success");
}
// Reset
function resetP(sec, obj) {
    $$P = 0;
    $$FAIL = false;
    jQuery(".bar").attr("style", "width:0%");
    jQuery(obj).removeClass("progress-success");
    jQuery(obj).removeClass("progress-danger");
    jQuery(obj).addClass("progress-info");
    jQuery(obj).addClass("active");
    $$STOP = window.setInterval(progress, sec);
}
function progress() {
    var $$STOP = null;
    jQuery(".bar").attr("style", "width:" + $$P + "%");
    if ($$P > 90 && !$$FAIL) {
        successP();
    }
    $$P += 5;
    if ($$P > 100) {
        window.clearInterval($$STOP);
    }
}