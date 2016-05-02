// ~ Value Assignment =======================================
var record = $env.getRecord();
var params = $env.getValues();
/** 查询条件生成 **/
var column = record.toColumn($data["name"]);
var expr = Cond.EQ(column);
$env.setExpr(expr);
/** 参数值操作 **/
var value = Type.String($data["value"]);
params.add(value);