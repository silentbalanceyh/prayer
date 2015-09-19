/** 自定义JS文件 * */
function btnLogin() {
	var username = value("username");
	var password = value("password");
	var auth = "Basic " + $$U.base64(username + ":" + password);
	// 隐藏错误信息
	message("msgError");
	$.ajax({
		type : "GET",
		url : api("/sec/login"),
		headers :headers(auth),
		dataType : "json",
		async:false,
		success : function(response) {
			if(200 === response.statusCode){
				var username = response.data["uniqueId"];
				window.location = "/dynamic/admin/main?UID=" + username;
			}
		},
		error : function(error) {
			var response = JSON.parse(error.responseText);	// NOPMD
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