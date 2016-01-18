package com.prayer.schema.json;

import static com.prayer.util.Converter.fromStr;
import static com.prayer.util.debug.Log.peError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.exception.AbstractSchemaException;
import com.prayer.constant.SystemEnum.Mapping;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.facade.schema.rule.ArrayHabitus;
import com.prayer.facade.schema.rule.ArrayRuler;
import com.prayer.facade.schema.rule.ObjectHabitus;
import com.prayer.facade.schema.rule.Ruler;
import com.prayer.facade.schema.verifier.Attributes;
import com.prayer.facade.schema.verifier.Verifier;
import com.prayer.schema.json.ruler.FieldsRuler;
import com.prayer.schema.json.ruler.ForeignsRuler;
import com.prayer.schema.json.ruler.KeysRuler;
import com.prayer.schema.json.ruler.MetaRuler;
import com.prayer.schema.json.ruler.PrimarysRuler;
import com.prayer.schema.json.ruler.RootRuler;
import com.prayer.schema.json.ruler.SubsRuler;
import com.prayer.schema.json.ruler.TypesRuler;

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
            // 注意调用copy防止过程中的变更
            /**
             * 1.验证Root节点
             */
            verifyRoot(data);
            /**
             * 2.验证Meta节点
             */
            verifyMeta(data);
            /**
             * 3.验证Fields节点
             */
            verifyFields(data);
            /**
             * 4.验证主键
             */
            verifyPrimaryKeys(data);
            /**
             * 5.验证SubKey
             */
            verifySubs(data);
            /**
             * 6.验证ForeignKey
             */
            verifyForeignKeys(data);
            /**
             * 7.验证Type
             */
            verifyType(data);
            /**
             * 8.验证Keys
             */
            verifyKeys(data);
        } catch (AbstractSchemaException ex) {
            peError(LOGGER, ex);
            error = ex;
        }
        return error;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void verifyKeys(final JsonObject data) throws AbstractSchemaException {
        /**
         * 8.验证Keys的规范
         */
        final ArrayHabitus habitus = this.getKeys(data);
        final ArrayRuler container = new KeysRuler();
        container.apply(habitus);
    }

    private void verifyType(final JsonObject data) throws AbstractSchemaException {
        /**
         * 7.验证字段类型
         */
        final ArrayHabitus habitus = this.getFields(data);
        final ArrayRuler container = new TypesRuler();
        container.apply(habitus);
    }

    private void verifyForeignKeys(final JsonObject data) throws AbstractSchemaException {
        /**
         * 6.验证外键的特殊情况，如果有外键才会验证
         */
        final ArrayHabitus habitus = this.getFields(data);
        final ArrayRuler container = new ForeignsRuler(habitus, this.getMeta(data, Attributes.M_TABLE));
        container.apply(habitus);
    }

    private void verifySubs(final JsonObject data) throws AbstractSchemaException {
        /**
         * 5.针对Mapping的特殊情况，必须处理subtable和subkey
         */
        final ArrayHabitus habitus = this.getFields(data);
        final ArrayRuler container = new SubsRuler(fromStr(Mapping.class, this.getMeta(data, Attributes.M_MAPPING)));
        container.apply(habitus);
    }

    private void verifyPrimaryKeys(final JsonObject data) throws AbstractSchemaException {
        /**
         * 4.验证主键
         */
        final ArrayHabitus habitus = this.getFields(data);
        final ArrayRuler container = new PrimarysRuler(this.getMeta(data, Attributes.M_TABLE),
                fromStr(MetaPolicy.class, this.getMeta(data, Attributes.M_POLICY)));
        container.apply(habitus);
    }

    private void verifyFields(final JsonObject data) throws AbstractSchemaException {
        /**
         * 3.Field节点的基本验证
         */
        final ArrayHabitus habitus = this.getFields(data);
        final ArrayRuler container = new FieldsRuler();
        container.apply(habitus);
    }

    private void verifyMeta(final JsonObject data) throws AbstractSchemaException {
        /**
         * 2.Meta节点的基本验证
         */
        final ObjectHabitus habitus = new JObjectHabitus(data.getJsonObject(Attributes.R_META));
        final Ruler ruler = new MetaRuler();
        ruler.apply(habitus);
    }

    private void verifyRoot(final JsonObject data) throws AbstractSchemaException {
        /**
         * 1.Root节点的验证
         */
        final ObjectHabitus habitus = new JObjectHabitus(data);
        final Ruler ruler = new RootRuler();
        ruler.apply(habitus);
    }

    // ~ Basic Info ==========================================
    /** 获取Fields对应的数据 **/
    private ArrayHabitus getFields(final JsonObject data) {
        return new JArrayHabitus(data.getJsonArray(Attributes.R_FIELDS), Attributes.R_FIELDS);
    }

    /** 获取Keys对应的数据 **/
    private ArrayHabitus getKeys(final JsonObject data) {
        return new JArrayHabitus(data.getJsonArray(Attributes.R_KEYS), Attributes.R_KEYS);
    }

    /** 获取Meta中某个键值下的String值 **/
    private String getMeta(final JsonObject data, final String key) {
        final JsonObject meta = data.getJsonObject(Attributes.R_META);
        return meta.getString(key);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
