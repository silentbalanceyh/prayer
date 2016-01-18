package com.prayer.facade.kernel;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.base.exception.AbstractDatabaseException;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.model.meta.database.PEField;
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
    List<PEField> idschema();
    /**
     * seq name
     * 
     * @return
     */
    String seqname();
}
