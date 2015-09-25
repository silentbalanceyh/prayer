// User Information -> Profile -> Save Profile
function saveProfile(){
    if($("#fProfile").valid()){
        var data = FORM.success("#fProfile");
        // TODO: 更新Profile的执行逻辑
        console.log(data);
    }
}
// User Information -> Profile -> Update Password
function updatePassword(){
    if($("#fPassword").valid()){
        var data = FORM.success("#fPassword");
        // TODO: 更新Password的执行逻辑
        console.log(data);
    }
}
