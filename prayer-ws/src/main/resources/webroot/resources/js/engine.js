// ~ 注销 =============================================
/** 注销账号的JS函数 * */
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
// ~ Ready Function =============================================
var INIT_FUNS = {
    "/dynamic/admin/options/h2" : function() {

        jQuery("#lblHost").html(
                "H2 Database is Running on : <b>" + jQuery("#hdHost").val()
                        + "</b>");
    },
    "/dynamic/admin/options/server" : function() {
        jQuery("#lblHost").html(
                "Prayer Framework is Running on : <b>"
                        + jQuery("#hdHost").val() + "</b>");
    },
    "/dynamic/admin/options/vertx" : function() {
        var name = '<font class="text-info">' + jQuery("#hdVertx").val()
                + "</font>";
        jQuery("#lblHost").html(
                "Vert.x Engine ( " + name + " ) is Running on : <b>"
                        + jQuery("#hdHost").val() + "</b>");
        jQuery(".vxname").html(name);
        // 设置.cluster和.ha
        BTN.checked("#chkClusterEnabled",".cluster");
        BTN.checked("#chkHAEnabled",".ha");
    }
};
jQuery(document).ready(function() {
    var FUN = INIT_FUNS[window.location.pathname];
    if('function' === typeof(FUN)){
        FUN();
    }
});