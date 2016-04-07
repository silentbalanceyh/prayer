// ~ Value Assignment =======================================
$$R.set("name",$data["name"]);
$$R.set("code",$data["code"]);
$$R.set("comment",$$E.acquire($data["comment"]));
$$R.set("super",$$E.acquire($data["super"]));