// ~ 注销 =============================================
/** 注销账号的JS函数 * */
function exeLogout() {
    BootstrapDialog.show({
        message: "Do you want to Log off ?",
        buttons: [{
            label: "Yes",
            cssClass: "btn btn-primary",
            action: function (self) {
                self.close();
                window.location = "/dynamic/logout";
            }
        }, {
            label: "No",
            action: function (self) {
                self.close();
            }
        }]
    });
}
// ~ Ready Function =============================================
var INIT_FUNS = {
    "/dynamic/admin/options/h2": function () {

        jQuery("#lblHost").html(
            "H2 Database is Running on : <b>" + jQuery("#hdHost").val()
            + "</b>");
    },
    "/dynamic/admin/options/server": function () {
        jQuery("#lblHost").html(
            "Prayer Framework is Running on : <b>"
            + jQuery("#hdHost").val() + "</b>");
    },
    "/dynamic/admin/options/vertx": function () {
        var name = '<font class="text-error">' + jQuery("#hdVertx").val()
            + "</font>";
        jQuery("#lblHost").html(
            "Vert.x Engine ( " + name + " ) is Running on : <b>"
            + jQuery("#hdHost").val() + "</b>");
        jQuery(".vxname").html(name);
        // 设置.cluster和.ha
        BTN.checked("#chkClusterEnabled", ".cluster");
        BTN.checked("#chkHAEnabled", ".ha");
    },
    "/dynamic/admin/options/security": function () {
        var name = '<font class="text-success">' + jQuery("#hdMode").val()
            + "</font>";
        jQuery("#lblHost").html(
            "Security Model ( " + name + " ) is Actived on : <b>"
            + jQuery("#hdHost").val() + "</b>");
        switch (jQuery("#hdMode").val()) {
            case "BASIC":
                BTN.active("#liBasic", ".limode");
                break;
            case "DIGEST":
                BTN.active("#liDigest", ".limode");
                break;
            case "OAUTH":
                BTN.active("#liOAuth", ".limode");
                break;
        }
        // 设置激活模式状态
        BTN.shift("#lblActive", "#lblInactive");
    }
};
function MENU_STATUS(path) {
    switch (path) {
        case "/dynamic/admin/options/h2":
        {
            BTN.active("#mOptions", ".leftNav");
            BTN.active("#mOptions", ".topNav");
            BTN.show("#mH2", ".topSubIcon");
        }
            break;
        case "/dynamic/admin/options/server":
        {
            BTN.active("#mOptions", ".leftNav");
            BTN.active("#mOptions", ".topNav");
            BTN.show("#mServer", ".topSubIcon");
        }
            break;
        case "/dynamic/admin/options/vertx":
        {
            BTN.active("#mOptions", ".leftNav");
            BTN.active("#mOptions", ".topNav");
            BTN.show("#mVertx", ".topSubIcon");
        }
            break;
        case "/dynamic/admin/options/security":
        {
            BTN.active("#mOptions", ".leftNav");
            BTN.active("#mOptions", ".topNav");
            BTN.show("#mSecurity", ".topSubIcon");
        }
            break;
        case "/dynamic/admin/profile":
        {
            BTN.active(null, ".leftNav");
            BTN.active(null, ".topNav");
            BTN.show("#mProfile", ".topSubIcon");
            FORM.init("#fProfile", "/static/resources/rules/validate/users/profile.json");
            FORM.init("#fPassword", "/static/resources/rules/validate/users/password.json");
        }
            break;
        case "/dynamic/admin/users":
        {
            BTN.active(null, ".leftNav");
            BTN.active(null, ".topNav");
            BTN.show("#mUsers", ".topSubIcon");
            TAB.initTable("#fUserList");
        }
            break;
        case "/dynamic/admin/scripts":
        {
            BTN.active("#mScript", ".leftNav");
            BTN.active(null, ".topNav");
            BTN.show(null, ".topSubIcon");
            TAB.initTable("#fScriptList");
            TAB.initEditor("taScript");
        }
            break;
        case "/dynamic/admin/address":
        {
            BTN.active("#mAddress", ".leftNav");
            BTN.active(null, ".topNav");
            BTN.show(null, ".topSubIcon");
            TAB.initTable("#fAddressList");
        }
            break;
        case "/dynamic/admin/verticle":
        {
            BTN.active("#mVerticle", ".leftNav");
            BTN.active(null, ".topNav");
            BTN.show(null, ".topSubIcon");
            TAB.initTable("#fVerticleList");
        }
            break;
    }
}
jQuery(document).ready(function () {
    var FUN = INIT_FUNS[window.location.pathname];
    if ('function' === typeof (FUN)) {
        FUN();
    }
    MENU_STATUS(window.location.pathname);
});