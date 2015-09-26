// ~ Value Assignment =======================================

// ~ Query Expression =======================================
var column = $$R.toColumn("username");
var expr = $$Q.eq(column);
$$ENV.setExpr(expr);
// ~ Query Generation =======================================
var username = $$V.string($data["username"]);
$$L.add(username);