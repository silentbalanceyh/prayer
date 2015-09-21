// ~ 登录/注销 =============================================
/** 自定义JS文件 * */
function exeLogin(button) {
	resetP(150);
	$(".progress").removeClass("hidden");
	// 获取值
	var username = $("#username").val();
	var password = $("#password").val();
	var auth = "Basic " + $$U.base64(username + ":" + password);
	// 隐藏错误信息
	$$U.message("msgError");
	BTN.before(button, "Login...");
	// 发送请求
	$.ajax({
		type : "GET",
		url : API.uri("/sec/login"),
		headers : {
			"Authorization" : auth
		},
		dataType : "json",
		success : function(response) {
			if (200 === response.statusCode) {
				var username = response.data["uniqueId"];
				window.location = "/dynamic/admin/main?UID=" + username;
			}
		},
		error : function(error) {
			failureP();
			var response = JSON.parse(error.responseText); // NOPMD
			if (401 === response.statusCode) {
				if (undefined === response.authenticateError) {
					$$U.message("msgError", true, MSG["AUTH"]["AUTH.MISSING"]);
				} else {
					$$U.message("msgError", true,
							MSG["AUTH"][response.authenticateError]);
				}
			}
			jQuery("#btnLogin").removeClass("disabled");
			$(".progress").addClass("hidden");
			BTN.after(button, "Login");
		}
	});
}
/** 注销的JS文件 * */
function exeLogout() {
	BootstrapDialog.show({
		message : "Do you want to Log off ?",
		buttons : [ {
			label : "Yes",
			cssClass : "btn btn-primary",
			action : function(self) {
				self.close();
				window.location = "/dynamic/logout";
			}
		}, {
			label : "No",
			action : function(self) {
				self.close();
			}
		} ]
	});
}
// ~ H2 Database Options ========================================
/** 验证逻辑 * */
function exeValidate(button) {
	BTN.before(button, "Validating...");
	var url = "tcp://" + value("hdHost") + ":" + value("txtTcpPort");
	$.ajax({
		url : url,
		timeout : 3000,
		type : 'GET',
		dataType : 'jsonp',
		success : function(data) {
			console.log(data);
			BTN.after(button, "Validate");
		},
		complete : function(request, status) {
			console.log(request);
			if ('timeout' == status) {
				console.log("Timeout");
			}
			BTN.after(button, "Validate");
		}
	})
}

// ~ Ready Function =============================================

jQuery(document).ready(
		function() {
			switch (window.location.pathname) {
			case "/dynamic/admin/options/h2": {
				var host = jQuery("#hdHost").val();
				jQuery("#lblHost").html(
						"H2 Database is Running on : <b>" + host + "</b>");
			}
				break;
			}
		});