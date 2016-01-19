package com.prayer.facade.dao.schema;

import com.prayer.facade.accessor.MetaAccessor;
import com.prayer.model.meta.vertx.PEAddress;
/**
 * 
 * @author Lang
 *
 */
public interface AddressDao extends MetaAccessor<PEAddress, String> {
    /**
     * 
     * @param workClass
     * @return
     */
    PEAddress getByClass(Class<?> workClass);
}
