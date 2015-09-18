/** 功能函数 * */
var $$U = (function() {
	_base64 = function(string) { // NOPMD
		var keyStr = CryptoJS.enc.Utf8.parse(string);
		return CryptoJS.enc.Base64.stringify(keyStr);
	};
	return function() {
		return {};
	}
})();
(function() {
	$$U = $$U.prototype = { // NOPMD
		base64 : _base64
	}
})();
/** Global全局函数 * */
// 获取元素，先按照id获取，其次按照class获取
function element(idOrCls) {
	var ret = $("#" + idOrCls);
	if (undefined == ret) {
		ret = $("." + idOrCls);
	}
	return ret;
}

function value(id) {
	return element(id).val();
}
// 界面错误信息
function message(id, show, error) {
	if (show) {
		element(id).html(error);
		element(id).removeClass("invisible");
	} else {
		element(id).addClass("invisible");
	}
}
