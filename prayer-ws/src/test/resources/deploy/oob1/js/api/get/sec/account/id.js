// ~ Value Assignment =======================================

// ~ Query Expression =======================================
var column = $$R.toColumn(PK);
var expr = $$Q.eq(column);
$$ENV.setExpr(expr);
// ~ Query Generation =======================================
var id = $$V.string($data["id"]);
$$L.add(id);