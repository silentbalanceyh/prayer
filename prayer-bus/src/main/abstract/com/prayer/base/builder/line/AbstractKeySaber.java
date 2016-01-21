package com.prayer.base.builder.line;
/**
 * 
 */

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.Constants;
import com.prayer.constant.Symbol;
import com.prayer.constant.SystemEnum.KeyCategory;
import com.prayer.facade.builder.SQLStatement;
import com.prayer.facade.builder.SQLWord;
import com.prayer.facade.builder.line.KeySaber;
import com.prayer.model.crucial.schema.FKReferencer;
import com.prayer.model.meta.database.PEField;
import com.prayer.model.meta.database.PEKey;
import com.prayer.util.string.StringKit;

import net.sf.oval.constraint.InstanceOfAny;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public abstract class AbstractKeySaber implements KeySaber, SQLWord, SQLStatement {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 生成列定义语句，主键和不重复键处理
     */
    // PrimaryKey: CONSTRAINT PK_NAME PRIMARY KEY (COL1, COL2)
    // UniqueKey: CONSTRAINT UK_NAME UNIQUE (COL1,COL2)
    public String buildLine(@NotNull @InstanceOfAny(PEKey.class) final PEKey key) {
        // 1.初始化缓冲区
        final StringBuilder sql = new StringBuilder();
        // 2.生成列相关信息
        final String columns = StringKit.join(key.getColumns(), Symbol.COMMA);
        // 3.按照类型区分UniqueKey和PrimaryKey
        if (KeyCategory.PrimaryKey == key.getCategory()) {
            sql.append(MessageFormat.format(CONSTRAINT_PK, key.getName(), columns));
        } else if (KeyCategory.UniqueKey == key.getCategory()) {
            sql.append(MessageFormat.format(CONSTRAINT_UK, key.getName(), columns));
        }
        return sql.toString();
    }

    /**
     * 生成键定义行语句，外键处理，虽然目前不支持同表引用，但这个语句必须要设置
     */
    // ForeignKey
    // CONSTRAINT FK_NAME FOREIGN KEY (COLUMN) REFERENCES REF_TABLE(REF_ID)
    public String buildLine(@NotNull @InstanceOfAny(PEKey.class) final PEKey key,
            ConcurrentMap<String, PEField> fieldMap) {
        // 1.初始化缓冲区
        final StringBuilder sql = new StringBuilder();
        if (KeyCategory.ForeignKey == key.getCategory()) {
            // 2.获取Column列表
            final List<String> columns = key.getColumns();
            // 3.获取每一个外键列
            if (Constants.ONE != columns.size()) {
                final String column = columns.get(Constants.IDX);
                if (StringKit.isNonNil(column) && fieldMap.containsKey(column)) {
                    final PEField field = fieldMap.get(column);
                    sql.append(MessageFormat.format(CONSTRAING_FK, key.getName(), StringKit.join(columns, Symbol.COMMA),
                            field.getRefTable(), field.getRefId()));
                }
            }
        }
        return sql.toString();
    }

    /**
     * 生成外键定义语句
     */
    public String buildLine(@NotNull @InstanceOfAny(FKReferencer.class) final FKReferencer ref) {
        // 1.初始化缓冲区
        final StringBuilder sql = new StringBuilder();
        // 2.生成列相关信息
        sql.append(MessageFormat.format(CONSTRAING_FK, ref.getName(), ref.getFromColumn(), ref.getToTable(),
                ref.getToColumn()));
        return sql.toString();
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
