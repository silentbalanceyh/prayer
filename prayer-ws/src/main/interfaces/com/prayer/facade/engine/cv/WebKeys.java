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

    interface Request {
        /** 请求的真实URI地址 **/
        String URI = "REQUEST.URI";
        /** 出现错误的时候的Envelop信息 **/
        String ENVP = "ERROR.ENVP";
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
