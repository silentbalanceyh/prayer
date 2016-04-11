package com.prayer.business.deployment.impl;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.builder.MetadataBuilder;
import com.prayer.constant.Resources;
import com.prayer.constant.log.InfoKey;
import com.prayer.dao.impl.schema.CommuneImporter;
import com.prayer.dao.impl.schema.SchemaDalor;
import com.prayer.exception.database.SchemaNotFoundException;
import com.prayer.facade.builder.Builder;
import com.prayer.facade.business.deployment.SchemaInstantor;
import com.prayer.facade.dao.schema.Importer;
import com.prayer.facade.dao.schema.SchemaDao;
import com.prayer.facade.schema.Schema;
import com.prayer.facade.schema.verifier.Altimeter;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.schema.common.SchemaAltimeter;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.PreValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class SchemaBllor implements SchemaInstantor {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SchemaBllor.class);
    // ~ Instance Fields =====================================
    /** 访问H2的Schema数据层接口 **/
    @NotNull
    private transient final SchemaDao dao;
    /** 访问传统数据库的Builder **/
    @NotNull
    private transient final Builder builder;
    /** 导入器 **/
    @NotNull
    private transient final Importer importer;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================

    /** **/
    @PostValidateThis
    public SchemaBllor() {
        this.dao = singleton(SchemaDalor.class);
        this.builder = singleton(MetadataBuilder.class);
        this.importer = singleton(CommuneImporter.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    @PreValidateThis
    public Schema importSchema(@NotNull @NotEmpty @NotBlank final String filePath) throws AbstractException {
        /** 1.读取Schema信息，从Json到H2中 **/
        final Schema schema = importer.read(filePath);
        /** 2.【Advanced验证】主要验证变更：Schema的变更是否合法的验证 **/
        final Altimeter altimeter = singleton(SchemaAltimeter.class, this.dao);
        altimeter.verify(schema);
        /** 3.将读取到的schema存如到H2 Database中 **/
        info(LOGGER, InfoKey.INF_DP_STEP1, filePath, Resources.META_CATEGORY);
        this.dao.save(schema);
        return schema;
    }

    /** **/
    @Override
    @PreValidateThis
    public Schema syncMetadata(@NotNull final Schema schema) throws AbstractException {
        this.builder.synchronize(schema);
        return schema;
    }

    /** **/
    @Override
    @PreValidateThis
    public Schema findById(@NotNull @NotEmpty @NotBlank final String identifier) throws AbstractException{
        final Schema schema = this.dao.get(identifier);
        if (null == schema) {
            throw new SchemaNotFoundException(getClass(), identifier);
        }
        return schema;
    }

    /** **/
    @Override
    @PreValidateThis
    public boolean removeById(@NotNull @NotEmpty @NotBlank final String identifier) throws AbstractException{
        final Schema schema = this.dao.get(identifier);
        if (null != schema) {
            this.builder.purge(schema);
        }
        this.dao.delete(identifier);
        return true;
    }

    /** **/
    @Override
    @PreValidateThis
    public boolean purge() throws AbstractException {
        final List<String> purged = this.dao.purge();
        this.builder.purge(new HashSet<>(purged));
        return true;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
