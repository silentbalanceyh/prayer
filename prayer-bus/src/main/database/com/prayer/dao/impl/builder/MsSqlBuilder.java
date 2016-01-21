package com.prayer.dao.impl.builder;

import static com.prayer.util.reflection.Instance.singleton;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.base.builder.AbstractBuilder;
import com.prayer.constant.Constants;
import com.prayer.constant.Symbol;
import com.prayer.dao.impl.builder.line.MsSqlFieldSaber;
import com.prayer.dao.impl.builder.line.MsSqlKeySaber;
import com.prayer.facade.dao.builder.line.FieldSaber;
import com.prayer.facade.dao.builder.line.KeySaber;
import com.prayer.facade.dao.builder.special.MsSqlStatement;
import com.prayer.facade.dao.builder.special.MsSqlWord;
import com.prayer.facade.kernel.Referencer;
import com.prayer.facade.schema.Schema;
import com.prayer.facade.schema.verifier.DataValidator;
import com.prayer.model.meta.database.PEField;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class MsSqlBuilder extends AbstractBuilder implements MsSqlStatement, MsSqlWord {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MsSqlBuilder.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Referencer getReferencer() {
        return singleton(MsSqlReferencer.class, this.getConnection());
    }

    /** **/
    @Override
    public DataValidator getValidator() {
        return singleton(MsSqlValidator.class);
    }

    /** **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    /** **/
    @Override
    public FieldSaber getFieldSaber() {
        return singleton(MsSqlFieldSaber.class);
    }

    /** **/
    @Override
    public KeySaber getKeySaber() {
        return singleton(MsSqlKeySaber.class);
    }

    /** **/
    @Override
    public String buildIncrement(@NotNull final Schema schema) {
        final StringBuilder line = new StringBuilder();
        assert (Constants.ONE == schema.getPrimaryKeys().size());
        // 1.字段本身的信息
        final PEField field = schema.getPrimaryKeys().get(Constants.IDX);
        line.append(this.getFieldSaber().buildLine(field));
        // 2.主键自增长信息
        line.append(Symbol.SPACE).append(
                MessageFormat.format(Pattern.P_IDENTIFY, schema.meta().getSeqInit(), schema.meta().getSeqStep()));
        return line.toString();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
