package com.prayer.uca.dependant;

import static com.prayer.util.Converter.fromStr;

import java.text.MessageFormat;

import com.prayer.constant.SqlSegment;
import com.prayer.constant.SystemEnum.DependRule;
import com.prayer.exception.web.DependRuleConflictException;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.constant.Symbol;
import com.prayer.facade.model.crucial.Value;
import com.prayer.fantasm.exception.AbstractWebException;
import com.prayer.model.type.DataType;
import com.prayer.uca.WebDependant;
import com.prayer.uca.jdbc.JdbcSwitcher;
import com.prayer.util.web.Extractor;
import com.prayer.util.web.Interruptor;

import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author Lang
 *
 */
public class UPEValidateDependant extends JdbcSwitcher implements WebDependant {    // NOPMD
    // ~ Static Fields =======================================
    /** 数据库表名 **/
    private final static String TABLE = "table";
    /** 数据库列名 **/
    private final static String COLUMN = "column";

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * Depend的处理流程，sqlQuery作为子查询
     */
    @Override
    public JsonObject process(@NotNull @NotBlank @NotEmpty final String name, @NotNull final Value<?> value,
            @NotNull final JsonObject config, @NotNull @NotBlank @NotEmpty final String sqlQuery)
                    throws AbstractWebException {
        // 1.检查Dependant的扩展配置信息
        Interruptor.interruptRequired(getClass(), name, config, TABLE);
        Interruptor.interruptRequired(getClass(), name, config, COLUMN);
        Interruptor.interruptStringConfig(getClass(), name, config, TABLE);
        Interruptor.interruptStringConfig(getClass(), name, config, COLUMN);

        // 2.结果设置
        final JsonObject retJson = new JsonObject();
        final DependRule rule = fromStr(DependRule.class, config.getString("rule"));
        if (DependRule.CONVERT == rule) {
            throw new DependRuleConflictException(getClass(), DependRule.VALIDATE, rule);
        } else {
            // 3.设置结果
            final Boolean ret = this.verifyUpExisting(config, value, sqlQuery);
            retJson.put(VAL_RET, ret);
        }
        return retJson;
    }

    private boolean verifyUpExisting(final JsonObject config, final Value<?> value, final String sqlQuery)  // NOPMD
            throws AbstractWebException {
        final String table = Extractor.getString(config, TABLE);
        final String column = Extractor.getString(config, COLUMN);
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
        // 构造Sql
        sql.append(MessageFormat.format(SqlSegment.TB_COUNT, table)).append(Symbol.SPACE).append(SqlSegment.WHERE)
                .append(Symbol.SPACE).append(column).append(Symbol.SPACE).append(Symbol.EQUAL).append(Symbol.SPACE)
                .append(whereVal).append(Symbol.SPACE).append(SqlSegment.AND).append(Symbol.SPACE).append(sqlQuery);
        // 设置结果
        final Long ret = this.getContext(config).count(sql.toString());
        return ret == Constants.ZERO;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
