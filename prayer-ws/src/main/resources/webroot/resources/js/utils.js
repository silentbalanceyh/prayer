/** 功能函数 * */
// 基本功能函数
var $$U = (function () {
    _base64 = function (string) { // NOPMD
        var keyStr = CryptoJS.enc.Utf8.parse(string);
        return CryptoJS.enc.Base64.stringify(keyStr);
    };
    _message = function (id, show, error) {
        if (show) {
            jQuery("#" + id).html(error);
            jQuery("#" + id).removeClass("invisible");
        } else {
            jQuery("#" + id).addClass("invisible");
        }
    };
    _complete = function (show, hidden, message) {
        _reset(show, hidden);
        jQuery("#" + show).children("span").html(message);
        jQuery("#" + show).fadeIn("slow", function () {
            window.setTimeout(function () {
                jQuery("#" + show).fadeOut("slow");
            }, 15000);
        });
    };
    _reset = function (sid, eid) {
        jQuery("#" + sid).children("span").html();
        jQuery("#" + eid).children("span").html();
        jQuery("#" + sid).fadeOut("fast");
        jQuery("#" + eid).fadeOut("fast");
    };
    _row = function (cls, id) {
        var selectId = jQuery(cls + ":checked").val();
        var data = {};
        if (undefined === selectId) {
            $$D.message("Please select one record that you want to process !", null, BootstrapDialog.TYPE_WARNING);
        } else {
            console.log(id);
            var row = jQuery(id).val();
            var fields = row.toString().split(',');
            for (var idx = 0; idx < fields.length; idx++) {
                data[fields[idx]] = jQuery("#" + fields[idx] + selectId).val();
            }
        }
        return data;
    };
    return function () {
        return {};
    }
})();
var TAB = (function () {
    var _injectField = function (data) {
        for (var item in data) {
            var type = jQuery("#" + item).attr("type");
            if (undefined == type) {
                jQuery("#" + item).html(data[item]);
            } else if ("text" == type) {
                jQuery("#" + item).attr("value", data[item]);
            } else if ("hidden" == type) {
                jQuery("#" + item).attr("value", data[item]);
            }
        }
    };
    _addTab = function (path, config, callback) {
        $.ajax({
            url: path,
            success: function (data, status, response) {
                if (response.status == 200) {
                    {
                        jQuery("#tpForm").html(data);
                        jQuery("#liForm").removeClass("hidden");
                        // Content
                        jQuery("#ulTab a[href=#tpForm]").html(config["title"]);
                        jQuery("#ulTab a[href=#tpForm]").tab("show");
                    }
                    if (undefined !== config["data"] && null !== config["data"]) {
                        var data = config["data"];
                        _injectField(data);
                    }
                    if (undefined !== callback && null !== callback) {
                        callback();
                    }
                }
            }
        });
    };
    _closeTab = function (callback) {
        $$D.confirm("Do you want to exit and all information will be dismissed ?", function () {
            TAB.dismiss(callback);
        });
    };
    _dismiss = function (callback) {
        jQuery("#tpForm").html("");
        // Header
        jQuery("#liForm").addClass("hidden");
        // Content
        jQuery("#ulTab a[href=#tpList]").tab("show");
        //
        if (undefined !== callback) {
            callback();
        }
    };
    _initTable = function (id) {
        $(id).dataTable({
            "sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
            "sPaginationType": "bootstrap",
            "oLanguage": {
                "sLengthMenu": "_MENU_ records per page"
            }
        });
    };
    _initEditor = function (id) {
        editAreaLoader.init({
            id: id,
            language: "en",
            syntax: "js",
            start_highlight: true,
            min_height: 450,
            toolbar: "save,|,search,go_to_line,|,undo,redo,word_wrap",
            replace_tab_by_spaces: true,
            allow_toggle: false,
            allow_resize: true
        });
    };
    return function () {
        return {};
    }
})();
// 获取远程API函数
var API = (function () {
    _uri = function (api) {
        var apiUrl = jQuery("#hdApi").val();
        return apiUrl + api;
    };
    return function () {
        return {};
    }
})();
var BTN = (function () {
    _before = function (button, text) {
        jQuery(button).addClass("disabled");
        jQuery(button).html(text);
    };
    _after = function (button, text) {
        jQuery(button).removeClass("disabled");
        jQuery(button).html(text);
    };
    _checked = function (boxId, cls) {
        // 判断CheckBox是否选中
        var value = jQuery(boxId + ":checked").length;
        if (true == value) {
            jQuery(cls).removeAttr("readonly");
        } else {
            jQuery(cls).attr("readonly", "readonly");
        }
    };
    _active = function (id, cls) {
        if (undefined !== jQuery(cls) && null !== cls) {
            jQuery(cls).removeClass("active");
        }
        if (undefined !== jQuery(id) && null !== id) {
            jQuery(id).addClass("active");
        }
    };
    _show = function (id, cls) {
        if (undefined !== jQuery(cls) && null !== cls) {
            jQuery(cls).addClass("hidden");
        }
        if (undefined !== jQuery(id) && null !== id) {
            jQuery(id).removeClass("hidden");
        }
    }
    _shift = function (show, hidden) {
        jQuery(hidden).addClass("hidden");
        jQuery(show).removeClass("hidden");
    };
    return function () {
        return {};
    }
})();
// =================下边是接口=======================
(function () {
    $$U = $$U.prototype = { // NOPMD
        base64: _base64,
        message: _message,
        reset: _reset,
        complete: _complete,
        row: _row
    }
})();
(function () {
    BTN = BTN.prototype = { // NOPMD
        before: _before,
        after: _after,
        checked: _checked,
        active: _active,
        shift: _shift,
        show: _show
    }
})();
(function () {
    TAB = TAB.prototype = {
        addTab: _addTab,
        closeTab: _closeTab,
        dismiss: _dismiss,
        initTable: _initTable,
        initEditor: _initEditor
    }
})();
(function () {
    API = API.prototype = {
        uri: _uri
    }
})();
// Confirm Dialog Back
function closeTabForm() {
    TAB.closeTab(function () {
        // 带不带Confirm Dialog的关闭
        jQuery(".radio").attr("checked", false);
    });
}
// No Confirm Dialog Back
function closeTabView() {
    TAB.dismiss();
}
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