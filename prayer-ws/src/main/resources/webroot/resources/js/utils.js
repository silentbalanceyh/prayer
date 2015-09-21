/** 功能函数 * */
// 进度条功能函数
var $$P = 0;
var $$STOP = null;
var $$FAIL = false;
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
	return function() {
		return {};
	}
})();
// =================下边是接口=======================
(function() {
	$$U = $$U.prototype = { // NOPMD
		base64 : _base64,
		message : _message
	}
})();
(function() {
	BTN = BTN.prototype = { // NOPMD
		before : _before,
		after : _after,
	}
})();
(function() {
	API = API.prototype = {
		uri : _uri
	}
})();

// ==================进度条色差函数==================

function failureP(){
	$$FAIL = true;
	jQuery(".progress-bar").removeClass("progress-bar-info");
	jQuery(".progress-bar").removeClass("progress-bar-success");
	jQuery(".progress-bar").removeClass("active");
	jQuery(".progress-bar").addClass("progress-bar-danger");
}

function successP(){
	jQuery(".progress-bar").removeClass("progress-bar-info");
	jQuery(".progress-bar").removeClass("progress-bar-danger");
	jQuery(".progress-bar").removeClass("active");
	jQuery(".progress-bar").addClass("progress-bar-success");
}

function resetP(sec){
	$$P = 0;
	jQuery(".progress-bar").attr("style", "width:0%");
	$$FAIL = false;
	jQuery(".progress-bar").removeClass("progress-bar-success");
	jQuery(".progress-bar").removeClass("progress-bar-danger");
	jQuery(".progress-bar").addClass("progress-bar-info");
	jQuery(".progress-bar").addClass("active");
	$$STOP = window.setInterval(progress, sec);
}
function progress() {
	var $$STOP = null;
	jQuery(".progress-bar").attr("style", "width:" + $$P + "%");
	if ($$P > 90 && !$$FAIL){
		successP();
	}
	$$P += 10;
	if ($$P > 100) {
		window.clearInterval($$STOP);
	}
}