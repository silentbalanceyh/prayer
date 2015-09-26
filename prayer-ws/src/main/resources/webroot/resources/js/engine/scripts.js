// Left -> Script Management -> Add New
function formScriptA(path) {
    TAB.addTab(path, {
        "title": "Script Add",
        "data": null
    }, function () {
        TAB.initEditor("in_content");
        $("#in_content").css("width","100%");

        FORM.init("#fScriptAdd", "/static/resources/rules/validate/scripts/scripts.json");
        // 带不带Confirm Dialog的关闭
        jQuery("#btnBack").attr("onclick","closeTabForm()");
    });
}
// Left -> Script Management -> Update
function formScriptU(path){
    var data = $$U.row(".radio","#fScriptListDATA");
    TAB.addTab(path, {
        "title": "Script Update",
        "data": data
    }, function () {
        TAB.initEditor("in_content");
        $("#in_content").css("width","100%");

        FORM.init("#fScriptUpdate", "/static/resources/rules/validate/scripts/scripts.json");
        // 带不带Confirm Dialog的关闭
        jQuery("#btnBack").attr("onclick","closeTabForm()");
    });
}
// Left -> Script Management -> Delete
function formScriptD(){
    var data = $$U.row(".radio","#fScriptListDATA");
    $$D.confirm("Do you want to delete this script ? ",function(){
        _deleteScript(data);
    });
}
// Left -> Script Management -> Update -> Delete
function deleteScript(){
    $$D.confirm("Do you want to delete this script record ? ",function(){
        var data = FORM.success("#fScriptUpdate");
        _deleteScript(data);
        closeTabView();
    });
}
// Left -> Script Management -> Update -> Update
function updateScript(){
    var content = editAreaLoader.getValue("in_content");
    if(false !== content && content.length > 0) {
        jQuery("#content").attr("value", content);
        if($("#fScriptUpdate").valid()){
            var data = FORM.success("#fScriptUpdate");
            data["in_content"] = content;
            // TODO: 更新脚本信息
            console.log(data);
        }
    }else{
        FORM.failure("#fScriptUpdate");
        $$D.message("Script content is required !",null,BootstrapDialog.TYPE_WARNING);
    }
}
// Left -> Script Management -> Add New -> Add New
function addScript(){
    var content = editAreaLoader.getValue("in_content");
    if(false !== content && content.length > 0) {
        jQuery("#content").attr("value", content);
        if($("#fScriptAdd").valid()){
            var data = FORM.success("#fScriptAdd");
            data["in_content"] = content;
            // TODO: 添加脚本信息
            console.log(data);
        }
    }else{
        FORM.failure("#fScriptAdd");
        $$D.message("Script content is required !",null,BootstrapDialog.TYPE_WARNING);
    }
}

// 执行逻辑: exeDelete
function _deleteScript(data){
    // TODO: 删除脚本信息
    console.log(data);
}