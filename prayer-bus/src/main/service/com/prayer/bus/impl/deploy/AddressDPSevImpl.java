package com.prayer.bus.impl.deploy;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prayer.base.bus.AbstractDPSevImpl;
import com.prayer.base.exception.AbstractSystemException;
import com.prayer.dao.impl.schema.AddressDaoImpl;
import com.prayer.facade.bus.deploy.AddressDPService;
import com.prayer.model.vertx.PEAddress;
import com.prayer.util.io.JsonKit;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class AddressDPSevImpl extends AbstractDPSevImpl<PEAddress, String> implements AddressDPService{    // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressDPSevImpl.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Class<?> getDaoClass() {
        return AddressDaoImpl.class;
    }

    /** 获取Logger **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }
    /** T Array **/
    @Override
    public PEAddress[] getArrayType(){
        return new PEAddress[]{};
    }

    /** **/
    @Override
    public List<PEAddress> readJson(@NotNull @NotBlank @NotEmpty final String jsonPath)
            throws AbstractSystemException {
        final TypeReference<List<PEAddress>> typeRef = new TypeReference<List<PEAddress>>() {
        };
        return JsonKit.fromFile(typeRef, jsonPath);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
