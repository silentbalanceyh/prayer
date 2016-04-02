// ~ Value Assignment =======================================
// ~ Query Expression =======================================
var column = $$R.toColumn($data["name"]);
var expr = $$Q.eq(column);
$$ENV.setExpr(expr);
// ~ Query Generation =======================================
var value = $$V.string($data["value"]);
$$L.add(value);