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
    }
    
    interface Shared{
        /** **/
        interface Params{
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
