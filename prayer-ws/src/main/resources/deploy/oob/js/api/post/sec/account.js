// ~ Value Assignment =======================================
$$R.set("username",$data["username"]);
$$R.set("password",$data["password"]);
$$R.set("email",$$E.acquire($data["email"]));
$$R.set("mobile",$$E.acquire($data["mobile"]));