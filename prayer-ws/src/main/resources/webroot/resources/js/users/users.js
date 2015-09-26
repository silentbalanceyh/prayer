// Users -> User Management -> Add New
function formUserA(path) {
    TAB.addTab(path, {
        "title": "User Add",
        "data": null
    }, function () {
        FORM.init("#fUserAdd", "/static/resources/rules/validate/users/useradd.json");
        // ������Confirm Dialog�Ĺر�
        jQuery("#btnBack").attr("onclick","closeTabForm()");
    });
}
// Users -> User Management -> Update
function formUserU(path) {
    var data = $$U.row(".radio", "#fUserListDATA");
    TAB.addTab(path, {
        "title": "User Update",
        "data": data
    }, function () {
        FORM.init("#fUserUpdate", "/static/resources/rules/validate/users/userupdate.json");
        FORM.init("#fUserPwD", "/static/resources/rules/validate/users/userpwd.json");
        // ������Confirm Dialog�Ĺر�
        jQuery("#btnBack").attr("onclick","closeTabForm()");
    });
}
// Users -> User Management -> View
function formUserV(path,callback) {
    var data = $$U.row(".radio", "#fUserListDATA");
    TAB.addTab(path, {
        "title": "User View",
        "data": data
    },function(){
        jQuery("#btnBack").attr("onclick","closeTabView()");
        // Ϊɾ�����������ư�ť
        if(undefined !== callback){
            callback();
        }
    });
}
// Users -> User Management -> Delete
function formUserD(){
    var data = $$U.row(".radio","#fUserListDATA");
    $$D.confirm("Do you want to delete this record directly ? ",function(){
        _deleteUser(data);
    },function(){
        formUserV('/dynamic/admin/forms/users/userview',function(){
            // ����ɾ����ť
            jQuery("#btnDelete").removeClass("hidden");
        });
    });
}
// Users -> User Management -> Delete -> View -> Delete
function deleteUser(){
    $$D.confirm("Do you want to delete this record ? ",function(){
        var data = FORM.success("#fUserView");
        _deleteUser(data);
        closeTabView();
    });
}
// ִ���߼�: exeDelete
function _deleteUser(data){
    // TODO: ɾ���û���Ϣ
    console.log(data);
}
// Users -> User Management -> Add New -> Add New
function addUser() {
    if ($("#fUserAdd").valid()) {
        var data = FORM.success("#fUserAdd");
        // TODO: ����û�ִ���߼�
        console.log(data);
    }
}
// Users -> User Management -> Update -> Update Profile
function updateUser(){
    if ($("#fUserUpdate").valid()) {
        var data = FORM.success("#fUserUpdate");
        // TODO: �����û���Ϣִ���߼�
        console.log(data);
    }
}
// Users -> User Management -> Update -> Update Password
function updatePwD(){
    if ($("#fUserPwD").valid()) {
        var data = FORM.success("#fUserPwD");
        // TODO: �����û�����ִ���߼�
        console.log(data);
    }
}

