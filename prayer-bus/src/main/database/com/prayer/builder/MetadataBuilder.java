package com.prayer.builder;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.Set;

import com.prayer.facade.builder.Builder;
import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.resource.Injections;

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
public class MetadataBuilder implements Builder {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** Builder引用 **/
    @NotNull
    private transient final Builder builder;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** 实现Builder的操作 **/
    public MetadataBuilder() {
        this.builder = singleton(Injections.Data.BUILDER);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public boolean synchronize(@NotNull final Schema schema) throws AbstractDatabaseException {
        return this.builder.synchronize(schema);
    }

    /** **/
    @Override
    public boolean purge(@NotNull final Schema schema) throws AbstractDatabaseException {
        return this.builder.purge(schema);
    }
    /** **/
    @Override
    public boolean purge(@NotNull @NotBlank @NotEmpty final String table) throws AbstractDatabaseException{
        return this.builder.purge(table);
    }
    /** **/
    @Override
    public boolean purge(@NotNull final Set<String> tables) throws AbstractException{
        return this.builder.purge(tables);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
