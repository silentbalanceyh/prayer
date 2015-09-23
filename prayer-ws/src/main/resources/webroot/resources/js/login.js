// 进度条功能函数
var $$P = 0;
var $$STOP = null;
var $$FAIL = false;
// Failure
function failureP(){
    $$FAIL = true;
    jQuery(".progress-bar").removeClass("progress-bar-info");
    jQuery(".progress-bar").removeClass("progress-bar-success");
    jQuery(".progress-bar").removeClass("active");
    jQuery(".progress-bar").addClass("progress-bar-danger");
}
// Success
function successP(){
    jQuery(".progress-bar").removeClass("progress-bar-info");
    jQuery(".progress-bar").removeClass("progress-bar-danger");
    jQuery(".progress-bar").removeClass("active");
    jQuery(".progress-bar").addClass("progress-bar-success");
}
// Reset
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
    if ($$P > 60 && !$$FAIL){
        successP();
    }
    $$P += 10;
    if ($$P > 100) {
        window.clearInterval($$STOP);
    }
}
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