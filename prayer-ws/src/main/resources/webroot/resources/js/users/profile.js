// User Information -> Profile -> Save Profile
function saveProfile(button){
    if($("#fProfile").valid()){
        var data = FORM.success("#fProfile");
        console.log(data);
        var config = {
            uri:"/sec/account",
            method:"PUT",
            data: data
        };
        var ui = {
            button:{
                ref:jQuery(button).children("span"),
                before:"&nbsp;&nbsp;Updating...",
                after:"&nbsp;&nbsp;Update Profile"
            },
            bar:{
                selector:"#barProfile"
            }
        };
        var callback = {
            successcall:function(data){
                console.log(data);
            }
        };
        API.submit(config,ui,callback);
    }
}
// User Information -> Profile -> Update Password
function updatePassword(){
    if($("#fPassword").valid()){
        var data = FORM.success("#fPassword");
        // TODO:
        console.log(data);
    }
}
