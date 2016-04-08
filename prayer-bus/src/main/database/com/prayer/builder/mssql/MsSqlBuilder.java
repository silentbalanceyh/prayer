package com.prayer.builder.mssql;

import static com.prayer.util.reflection.Instance.singleton;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.builder.mssql.part.MsSqlFieldSaber;
import com.prayer.builder.mssql.part.MsSqlKeySaber;
import com.prayer.builder.mssql.part.MsSqlValidator;
import com.prayer.facade.builder.Refresher;
import com.prayer.facade.builder.line.FieldSaber;
import com.prayer.facade.builder.line.KeySaber;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.constant.Symbol;
import com.prayer.facade.schema.Schema;
import com.prayer.facade.schema.verifier.DataValidator;
import com.prayer.facade.sql.special.MsSqlStatement;
import com.prayer.facade.sql.special.MsSqlWord;
import com.prayer.fantasm.builder.AbstractBuilder;
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
    // ~ 需要访问数据库组件 ==================================

    /** **/
    @Override
    public Refresher getRefresher() {
        return singleton(MsSqlRefresher.class, this.getConnection());
    }

    // ~ 不需要访问数据库的组件 ==============================
    /** **/
    @Override
    public DataValidator getValidator() {
        return singleton(MsSqlValidator.class);
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

    // ~ 日志组件 =============================================
    /** **/
    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    // ~ 自增长SQL语句 ========================================
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
