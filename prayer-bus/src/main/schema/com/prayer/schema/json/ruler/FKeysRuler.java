package com.prayer.schema.json.ruler;

import java.util.List;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.facade.schema.rule.ArrayHabitus;
import com.prayer.facade.schema.rule.ArrayRuler;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Ruler;
import com.prayer.facade.schema.verifier.Attributes;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.AssertFieldConstraints;
import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class FKeysRuler implements ArrayRuler {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 对外键单键验证的Ruler **/
    @NotNull
    private transient final Ruler ruler;
    /** 当前表的信息，用于外键是否需要判断当前引用表的信息 **/
    @NotNull
    @NotEmpty
    @NotBlank
    private transient final String table;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param ruler
     * @param table
     */
    @PostValidateThis
    public FKeysRuler(@NotNull @InstanceOfAny(FKeyRuler.class) final Ruler ruler,
            @AssertFieldConstraints("table") final String table) {
        this.ruler = ruler;
        this.table = table;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public void apply(@NotNull final ArrayHabitus habitus) throws AbstractSchemaException {
        /** 6.0.优先处理ArrayHabitus中的容器异常：10002/10006 **/
        habitus.ensure();
        /** 6.1.检查Array中foreignkey = true 的单键异常 **/
        verifyFKSpecification(habitus);
        /** 6.2.检查外键是否出现了重复引用，两键引用同一张表 **/
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    /** 外键单键检查 **/
    private void verifyFKSpecification(final ArrayHabitus habitus) throws AbstractSchemaException {
        final JsonObject filter = new JsonObject();
        filter.put(Attributes.F_FK, true);
        final List<ObjectHabitus> data = habitus.get(filter);
        for (final ObjectHabitus item : data) {
            this.ruler.apply(item);
        }
    }

    private JsonObject getAddtional(final ObjectHabitus habitus) {
        final JsonObject addtional = new JsonObject();
        addtional.put(Attributes.M_TABLE, this.table);
        return addtional;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
