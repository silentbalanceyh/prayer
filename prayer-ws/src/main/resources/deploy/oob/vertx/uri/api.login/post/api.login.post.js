// ~ Value Assignment =======================================
var record = $env.getRecord();
var params = $env.getValues();
/** 查询条件生成 * */

var colUser = record.toColumn("username");
var colPwd = record.toColumn("password");

var exprUser = Cond.EQ(colUser);
var exprPwd = Cond.EQ(colPwd);
var expr = Cond.AND(exprUser, exprPwd);
$env.setExpr(expr);
/** 参数设置 * */
var username = Type.String($data["username"]);
var password = Type.String($data["password"]);
params.add(username);
params.add(password);