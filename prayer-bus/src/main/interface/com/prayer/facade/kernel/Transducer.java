package com.prayer.facade.kernel;

import static com.prayer.util.reflection.Instance.singleton;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.type.DataType;

import io.vertx.core.json.JsonObject;

/**
 * 内部转换器
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.ENG_PUBLIC)
public interface Transducer {
    /**
     * 
     * @param type
     * @param value
     * @return
     */

    @VertexApi(Api.TOOL)
    Value<?> getValue(DataType type, String value) throws AbstractDatabaseException;
    /**
     * 
     * @param data
     * @param type
     * @param field
     * @return
     */

    @VertexApi(Api.TOOL)
    Value<?> getValue(JsonObject data, DataType type, String field) throws AbstractDatabaseException;
    /**
     * 内部类
     * 
     * @author Lang
     *
     */
    public class V { // NOPMD
        /**
         * 
         * @return
         */
        public static Transducer get(){
            return singleton("com.prayer.model.crucial.ValueTransducer");
        }
    }
}
