package com.prayer.facade.engine.cv;

/**
 * 内部使用的Message Queue
 * 
 * @author Lang
 *
 */
public interface WebKeys {
    /** 某个URI的信息表 **/
    String URI_ADDR = "MSG://INTERNAL/QUEUE/URI{0}";
    
    interface UriMeta {
        /** Uri本身信息 **/
        String URI = "uri";
        /** Rule本身信息 **/
        interface UAC {
            /** Validator **/
            String VALIDATOR = "validator";
            /** Convertor **/
            String CONVERTOR = "convertor";
            /** Dependent **/
            String DEPENDENT = "dependent";
        }
    }
    
    interface Params{
        /** 参数类型 **/
        String TYPE = "type";
        /** 参数本身值 **/
        String NAME = "parameters";
    }

    interface Request {
        /** 请求的真实URI地址 **/
        String URI = "REQUEST.URI";
        /** 出现错误的时候的Envelop信息 **/
        String ERR_ENVP = "ERROR.ENVP";
        /** Solicitor传递 **/
        String SOLICITOR = "REQ.SOLICITOR";
        /** Envelop数据传递 **/
        String ENVP = "REQ.ENVP";

        interface Rule {
            /** 请求过程中的Uri信息 **/
            String URI = "RULE.REQ.URI";
            /** 跳过VALIDATOR **/
            String SKIPV = "VALIDATOR.SKIP";
            /** 跳过CONVERTOR **/
            String SKIPC = "CONVERTOR.SKIP";
            /** 跳过DEPENDANT **/
            String SKIPD = "DEPENDANT.SKIP";
        }

        interface Token {
            /** 请求过程中的模式 **/
            String MODE = "AUTH.MODE";
            /** 请求过程中的Token **/
            String TOKEN = "AUTH.TOKEN";
        }
    }

    /** 响应信息格式 **/
    interface Envelop {
        /** Status根节点 **/
        String STATUS = "status";
        /** Error根节点 **/
        String ERROR = "error";
        /** Data数据节点 **/
        String DATA = "data";

        /** Http状态代码 **/
        interface Status {
            /** 状态代码 **/
            String CODE = "code";
            /** 状态Title **/
            String MESSAGE = "message";
        }

        /** Error节点 **/
        interface Error {
            /** 系统的Error Code **/
            String CODE = "code";
            /** AbstractException中对应的Error Message **/
            String MESSAGE = "message";
        }

        /** **/
        interface Data {
            /** Headers信息 **/
            String HEADER = "header";
            /** Body信息 **/
            String BODY = "body";
        }
    }

    interface Shared {
        /** **/
        interface Params {
            /** JsonObject -> map **/
            String MAP = "map";
            /** JsonObject -> key **/
            String KEY = "key";
            /** JsonObject -> value **/
            String VALUE = "value";
        }

        /** SharedData中的Key，用于存储URI和Rule **/
        String URI = "SHARED.URI";
    }
}
