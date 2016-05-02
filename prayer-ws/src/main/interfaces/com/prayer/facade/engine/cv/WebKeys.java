package com.prayer.facade.engine.cv;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 内部使用的Message Queue
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.CONSTANT)
public interface WebKeys {
    /** 某个URI的信息表 **/
    String URI_ADDR = "MSG://INTERNAL/QUEUE/URI{0}";

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface UriMeta {
        /** Uri本身信息 **/
        String URI = "uri";

        /** Rule本身信息 **/
        @VertexPoint(Interface.CONSTANT)
        interface UAC {
            /** Skewer **/
            String VALIDATOR = "validator";
            /** Convertor **/
            String CONVERTOR = "convertor";
            /** Dependent **/
            String DEPENDENT = "dependent";
        }
    }

    @VertexPoint(Interface.CONSTANT)
    interface Params {
        /** 参数本身值 **/
        String NAME = "parameters";
    }

    @VertexPoint(Interface.CONSTANT)
    interface Request {
        /** 请求的真实URI地址 **/
        String URI = "REQUEST.URI";
        /** 出现错误的时候的Envelop信息 **/
        String ERR_ENVP = "ERROR.ENVP";

        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Data {
            /** 参数信息 **/
            String PARAMS = "DATA.REQ.PARAMS";

            /** **/
            @VertexPoint(Interface.CONSTANT)
            interface Meta {
                /** PEUri **/
                String PEURI = "DATA.URI";
                /** List<PERule> **/
                String PEV = "DATA.VALIDATOR";
                /** List<PERule> **/
                String PEC = "DATA.CONVERTOR";
                /** List<PERule> **/
                String PED = "DATA.DEPENDENT";
            }
        }

        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Token {
            /** 请求过程中的模式 **/
            String MODE = "AUTH.MODE";
            /** 请求过程中的Token **/
            String TOKEN = "AUTH.TOKEN";
        }
    }

    /** 响应信息格式 **/
    @VertexPoint(Interface.CONSTANT)
    interface Envelop {
        /** Status根节点 **/
        String STATUS = "status";
        /** Error根节点 **/
        String ERROR = "error";
        /** Data数据节点 **/
        String DATA = "data";
        /** User用户节点 **/
        String INFO = "info";

        /** Http状态代码 **/
        @VertexPoint(Interface.CONSTANT)
        interface Status {
            /** 状态代码 **/
            String CODE = "code";
            /** 状态Message **/
            String MESSAGE = "message";
        }

        /** Error节点 **/
        @VertexPoint(Interface.CONSTANT)
        interface Error {
            /** 系统的Error Code **/
            String CODE = "code";
            /** AbstractException中对应的Error Message **/
            String MESSAGE = "message";
        }

        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Data {
            /** Body信息 **/
            String BODY = "body";
        }
    }

    @VertexPoint(Interface.CONSTANT)
    interface Shared {
        /** **/
        @VertexPoint(Interface.CONSTANT)
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
