/** 自定义JS文件 * */
function btnLogin() {
	var username = value("username");
	var password = value("password");
	var auth = "Basic " + $$U.base64(username + ":" + password);
	// 隐藏错误信息
	message("msgError");
	$.ajax({
		type : "GET",
		url : "/api/sec/login",
		headers : {
			"Authorization" : auth
		},
		dataType : "json",
		async:false,
		success : function(data) {
			if(200 === data.statusCode){
				window.location = "/dynamic/admin/main.jade";
				// element("lnkMain").click();
			}
		},
		error : function(error) {
			var response = JSON.parse(error.responseText);
			if(401 === response.statusCode){
				if(undefined === response.authenticateError){
					message("msgError",true,MSG["AUTH"]["AUTH.MISSING"]);
				}else{
					message("msgError",true,MSG["AUTH"][response.authenticateError]);
				}
			}
		}
	});
}