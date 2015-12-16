package com.prayer.uca.validator;

import static com.prayer.util.Instance.singleton;

import java.text.MessageFormat;

import com.prayer.assistant.Extractor;
import com.prayer.assistant.Interruptor;
import com.prayer.base.exception.AbstractWebException;
import com.prayer.dao.impl.jdbc.JdbcConnImpl;
import com.prayer.facade.dao.jdbc.JdbcContext;
import com.prayer.facade.kernel.Value;
import com.prayer.model.type.DataType;
import com.prayer.uca.WebValidator;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.SqlSegment;
import com.prayer.util.cv.Symbol;

import io.vertx.core.json.JsonObject;
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
public class UPExistingValidator implements WebValidator { // NOPMD

    // ~ Static Fields =======================================
    /** 数据库表名 **/
    private final static String TABLE = "table";
    /** 数据库列名 **/
    private final static String COLUMN = "column";
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final JdbcContext context;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public UPExistingValidator() {
        this.context = singleton(JdbcConnImpl.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public boolean validate(@NotNull @NotBlank @NotEmpty final String name, @NotNull final Value<?> value,
            @NotNull final JsonObject config) throws AbstractWebException {
        // 1.检查Validator提供的配置信息
        Interruptor.interruptRequired(getClass(), name, config, TABLE);
        Interruptor.interruptRequired(getClass(), name, config, COLUMN);
        Interruptor.interruptStringConfig(getClass(), name, config, TABLE);
        Interruptor.interruptStringConfig(getClass(), name, config, COLUMN);
        // 2.检查值
        final String table = Extractor.getString(config, TABLE);
        final String column = Extractor.getString(config, COLUMN);
        // 3.数据库访问查询
        return this.verifyUpUnique(table, column, value);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private boolean verifyUpUnique(final String table, final String column, final Value<?> value) {
        final StringBuilder sql = new StringBuilder(Constants.BUFFER_SIZE);
        // 获取value部分
        String whereVal = null;
        if (value.getDataType() == DataType.STRING || value.getDataType() == DataType.DATE
                || value.getDataType() == DataType.XML || value.getDataType() == DataType.JSON
                || value.getDataType() == DataType.SCRIPT) {
            whereVal = Symbol.S_QUOTES + value.literal() + Symbol.S_QUOTES;
        } else if (value.getDataType() == DataType.INT || value.getDataType() == DataType.LONG
                || value.getDataType() == DataType.BOOLEAN || value.getDataType() == DataType.DECIMAL) {
            whereVal = value.literal();
        }
        // Sql语句设置
        sql.append(MessageFormat.format(SqlSegment.TB_COUNT, table)).append(Symbol.SPACE).append(SqlSegment.WHERE)
                .append(Symbol.SPACE).append(column).append(Symbol.SPACE).append(Symbol.EQUAL).append(Symbol.SPACE)
                .append(whereVal).append(Symbol.SPACE).append(SqlSegment.AND).append(Symbol.SPACE).append(column)
                .append(Symbol.SPACE).append(SqlSegment.NOT_EQUAL).append(Symbol.SPACE).append(whereVal);

        final Long ret = this.context.count(sql.toString());
        return ret == Constants.ZERO;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
