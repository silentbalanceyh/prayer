package com.prayer.facade.dao.builder.line;

import com.prayer.model.meta.database.PEField;

/**
 * 
 * @author Lang
 *
 */
public interface FieldSaber {
    /**
     * 行构造器
     * 
     * @param field
     * @return
     */
    String buildLine(PEField field);
}
