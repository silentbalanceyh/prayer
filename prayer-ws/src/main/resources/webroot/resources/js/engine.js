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
        var host = jQuery("#hdHost").val();
        jQuery("#lblHost").html(
                "H2 Database is Running on : <b>" + host + "</b>");
    },
    "/dynamic/admin/options/server": function(){
        var host = jQuery("#hdHost").val();
        jQuery("#lblHost").html(
                "Prayer Framework is Running on : <b>" + host + "</b>");
    }
};

jQuery(document).ready(function() {
    INIT_FUNS[window.location.pathname]();
});