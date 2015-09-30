package com.prayer.model.web;

import io.vertx.core.http.HttpHeaders;

/**
 * 
 * @author Lang
 *
 */
public interface JsonKey {
    /** **/
    interface TOKEN {
        /** **/
        String NAME = "token";
        /** **/
        String SCHEMA = "schema";
        /** **/
        String USERNAME = "username";
        /** **/
        String PASSWORD = "password";
        /** **/
        String ID = "id";
        /** **/
        String ROLE = "role";
    }

    /** **/
    interface REQUEST {
        /** **/
        String NAME = "request";
        /** **/
        String AUTHORIZATION = HttpHeaders.AUTHORIZATION.toString();
        /** **/
        String LOGIN_URL = "login.url";
        /** **/
        String PARAMS = "params";
    }

    /** **/
    interface PARAMS {
        /** **/
        String NAME = "params";
        /** **/
        String METHOD = "method";
        /** **/
        String FILTERS = "filters";
        /** **/
        String IDENTIFIER = "identifier";
        /** **/
        String SCRIPT = "script";
        /** **/
        String DATA = "data";
    }

    /** **/
    interface RESPONSE {
        /** **/
        String NAME = "response";
        /** **/
        String RETURNCODE = "returnCode";
        /** **/
        String STATUS = "status";
        /** **/
        String DATA = PARAMS.DATA;
        /** **/
        String KEY = "key";
    }

    /** **/
    interface RESPONSOR {
        /** **/
        String RETURNCODE = RESPONSE.RETURNCODE;
        /** **/
        String DATA = PARAMS.DATA;

        /** **/
        interface STATUS {
            /** **/
            String NAME = RESPONSE.STATUS;
            /** **/
            String CODE = "code";
            /** **/
            String LITERAL = "literal";
        }

        /** **/
        interface ERROR {
            /** **/
            String NAME = "error";
            /** **/
            String CODE = STATUS.CODE;
            /** **/
            String MESSAGE = "message";
            /** **/
            String DISPLAY = "display";
        }
    }
}
