/** 验证H2数据库连接逻辑 * */
function exeDbValidate(button) {
    resetP(150,$("#barDBP"));
    $("#barDBP").removeClass("hidden");
    BTN.before(button, "Validating...");
    $$U.reset("lblError","lblSuccess");
    var url = "http://" + jQuery("#hdHost").val() + ":"
            + jQuery("#txtTcpPort").val();
    $.ajax({
        url : url,
        timeout : 3000,
        type : 'POST',
        dataType : 'jsonp',
        complete : function(response, status) {
            if (200 === response.status) {
                successP($("#barDBP"));
                $$U.complete("lblSuccess","lblError","&nbsp;&nbsp;&nbsp;&nbsp;H2 Database connection validated successfully !");
            } else {
                failureP($("#barDBP"));
                $$U.complete("lblError","lblSuccess","&nbsp;&nbsp;&nbsp;&nbsp;H2 Database connection validated failure, please check database TCP port !");
            }
            $("#barDBP").addClass("hidden");
            BTN.after(button, "Validate");
        }
    });
}
/** 打开新页面 **/
function exeOpenUrl(){
    var url = "http://" + jQuery("#hdHost").val() + ":"
    + jQuery("#txtWebPort").val();
    window.open(url);
}