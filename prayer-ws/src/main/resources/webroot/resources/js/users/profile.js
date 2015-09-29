// User Information -> Profile -> Save Profile
function saveProfile(button){
    if($("#fProfile").valid()){
        var data = FORM.success("#fProfile");
        var config = {
            uri:"/sec/account",
            method:"PUT",
            data:data
        };
        var ui = {
            button:{
                ref:jQuery(button),
                before:"&nbsp;&nbsp;Updating...",
                after:"&nbsp;&nbsp;Update Profile"
            },
            bar:{
                selector:"#barProfile"
            }
        };
        var callback = {
            dialog:{
               message:"Your profile has been updated successfully !",
               yes:function(){
                   FORM.reset("#fProfile");
               }
            }
        };
        API.submit(config,ui,callback);
    }
}
// User Information -> Profile -> Update Password
function updatePassword(button){
    if($("#fPassword").valid()){
        var data = FORM.success("#fPassword");
        data["password"] = data["newpwd"];
        delete data["oldpwd"];
        delete data["newpwd"];
        delete data["cfmpwd"];
        var config = {
            uri:"/sec/account",
            method:"PUT",
            data:data
        };
        var ui = {
            button:{
                ref:jQuery(button),
                before:"&nbsp;&nbsp;Updating...",
                after:"&nbsp;&nbsp;Update Password"
            },
            bar:{
                selector:"#barPassword"
            }
        };
        var callback = {
            dialog:{
               message:"Your password has been updated successfully, please use new password to re-login into the system.",
               yes:function(){
                   FORM.reset("#fPassword");
                   window.location = "/dynamic/logout";
               }
            }
        };
        API.submit(config,ui,callback);
    }
}
