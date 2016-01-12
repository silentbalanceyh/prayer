package com.prayer.schema.json;

import static com.prayer.util.debug.Log.peError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.facade.schema.rule.Ruler;
import com.prayer.facade.schema.verifier.Verifier;
import com.prayer.schema.json.ruler.RootRuler;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 验证器的入口
 * 
 * @author Lang
 *
 */
@Guarded
public class SchemaVerifier implements Verifier {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SchemaVerifier.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    @Override
    public AbstractSchemaException verify(@NotNull final JsonObject data) {
        /**
         * 获取Root的ObjectHabitus和Ruler
         */
        AbstractSchemaException error = null;
        try {
            /**
             * 1.验证Root节点
             */
            this.verifyRoot(data);

        } catch (AbstractSchemaException ex) {
            peError(LOGGER, ex);
            error = ex;
        }
        return error;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void verifyRoot(final JsonObject data) throws AbstractSchemaException {
        /**
         * 1.Root节点的验证，初始化规则并且用Ruler去匹配该规则
         */
        final JObjectHabitus habitus = new JObjectHabitus(data);
        final Ruler ruler = new RootRuler();
        ruler.apply(habitus);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
