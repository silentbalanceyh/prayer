package com.prayer.facade.dao.builder.line;

import java.util.concurrent.ConcurrentMap;

import com.prayer.model.crucial.schema.FKReferencer;
import com.prayer.model.meta.database.PEField;
import com.prayer.model.meta.database.PEKey;

/**
 * 
 * @author Lang
 *
 */
public interface KeySaber {
    /**
     * PK, UK
     * @param key
     * @return
     */
    String buildLine(PEKey key);
    /**
     * PK, UK, FK
     * @param key
     * @param field
     * @return
     */
    String buildLine(PEKey key, ConcurrentMap<String,PEField> fieldMap);
    /**
     * FK另外一种模式
     * @param name
     * @param columns
     * @param toTable
     * @param toColumn
     * @return
     */
    String buildLine(FKReferencer ref);
}
