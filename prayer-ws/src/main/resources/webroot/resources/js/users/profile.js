// User Information -> Profile -> Save Profile
function saveProfile(){
    if($("#fProfile").valid()){
        var data = FORM.success("#fProfile");
        // TODO: ����Profile��ִ���߼�
        console.log(data);
    }
}
// User Information -> Profile -> Update Password
function updatePassword(){
    if($("#fPassword").valid()){
        var data = FORM.success("#fPassword");
        // TODO: ����Password��ִ���߼�
        console.log(data);
    }
}
