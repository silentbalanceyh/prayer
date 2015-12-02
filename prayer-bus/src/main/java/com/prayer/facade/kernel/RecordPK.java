package com.prayer.facade.kernel;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.model.h2.schema.FieldModel;
import com.prayer.util.cv.SystemEnum.MetaPolicy;
/**
 * 
 * @author Lang
 *
 */
interface RecordPK {
    /**
     * 主键的Policy
     * 
     * @return
     */
    MetaPolicy policy();
    /**
     * 生成主键键值对
     * @return
     */
    ConcurrentMap<String,Value<?>> idKV() throws AbstractDatabaseException;
    /**
     * 当前记录的主键Schema
     * @return
     */
    List<FieldModel> idschema();
    /**
     * seq name
     * 
     * @return
     */
    String seqname();
}
