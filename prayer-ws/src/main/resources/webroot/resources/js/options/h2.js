/** 验证H2数据库连接逻辑 * */
function exeDbValidate(button) {
    // Data
    var uri = "http://" + jQuery("#hdHost").val() + ":"
    + jQuery("#txtTcpPort").val();
    var config = {
        uri:uri,
        method:"POST",
        token:"NONE",
        type:"jsonp"
    };
    // UI
    var ui = {
        button:{
            ref:button,
            before:"Validating...",
            after:"Validate"
        },
        // 隐藏Error
        msg:{
           eid:"lblError",
           sid:"lblSuccess",
           success:"&nbsp;&nbsp;&nbsp;&nbsp;H2 Database connection validated successfully !",
           error:"&nbsp;&nbsp;&nbsp;&nbsp;H2 Database connection validated failure, please check database TCP port !",
           fade:true
        },
        bar:{
            selector:"#barDBP"
        }
    };
    API.submit(config,ui,{});
}
/** 打开新页面 * */
function exeOpenUrl(){
    var url = "http://" + jQuery("#hdHost").val() + ":"
    + jQuery("#txtWebPort").val();
    window.open(url);
}