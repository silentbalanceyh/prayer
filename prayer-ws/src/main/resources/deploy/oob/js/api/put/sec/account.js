// ~ Value Assignment =======================================
$$R.set(PK,$data["id"]);
$$R.set("password",$data["password"]);
$$R.set("email",$$E.updated($data["email"]));
$$R.set("mobile",$$E.updated($data["mobile"]));