package com.prayer.facade.record;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.facade.kernel.Value;
import com.prayer.model.meta.database.PEField;

/**
 * 
 * @author Lang
 *
 */
interface RecordPrimaryKey {
    /**
     * 主键的Policy
     * 
     * @return
     */
    MetaPolicy policy();

    /**
     * 生成主键键值对
     * 
     * @return
     */
    ConcurrentMap<String, Value<?>> idKV() throws AbstractDatabaseException;

    /**
     * 当前记录的主键Schema
     * 
     * @return
     */
    List<PEField> idschema();

    /**
     * seq name
     * 
     * @return
     */
    String seqname();
}
