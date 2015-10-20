package com.prayer.kernel.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.prayer.constant.Constants;
import com.prayer.constant.Symbol;
import com.prayer.exception.AbstractException;
import com.prayer.kernel.Record;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jodd.util.StringUtil;
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
public class OrderBy implements Serializable {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 1512151704404777705L;
    // ~ Instance Fields =====================================
    /**
     * 
     */
    private transient final List<String> orderBy = new ArrayList<>();

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param orderArr
     * @param record
     * @return
     */
    public static OrderBy create(@NotNull final JsonArray orderArr, @NotNull final Record record) {
        return new OrderBy(orderArr, record);
    }

    // ~ Constructors ========================================
    /**
     * 
     */
    public OrderBy() {
        this.clear();
    }

    /**
     * 
     * @param jsonArr
     */
    private OrderBy(final JsonArray orderArr, final Record record) {
        for (int idx = 0; idx < orderArr.size(); idx++) {
            final JsonObject item = orderArr.getJsonObject(idx);
            final Iterator<Map.Entry<String, Object>> it = item.iterator();
            // Orders只包含一个元素
            if (it.hasNext()) {
                Map.Entry<String, Object> entry = it.next();
                try {
                    final String column = record.toColumn(entry.getKey());
                    final String flag = entry.getValue().toString();
                    this.add(column, flag);
                } catch (AbstractException ex) {
                    continue;
                }
            }
        }
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    public void add(@NotNull @NotBlank @NotEmpty final String column, @NotNull @NotBlank @NotEmpty final String flag) {
        final StringBuilder statement = new StringBuilder();
        statement.append(column).append(Symbol.SPACE);
        if (StringUtil.equals("ASC", flag.toUpperCase(Locale.getDefault()))) {
            statement.append("ASC");
        } else {
            statement.append("DESC");
        }
        this.orderBy.add(statement.toString());
    }

    /** 清除掉当前的OrderBy Queue **/
    public void clear() {
        if (!orderBy.isEmpty()) {
            orderBy.clear();
        }
    }

    /** **/
    public String toSql() {
        String orderBySql = "";
        if (!orderBy.isEmpty()) {
            orderBySql = StringUtil.join(this.orderBy.toArray(Constants.T_STR_ARR), Symbol.COMMA);
        }
        return orderBySql;
    }

    /** **/
    public boolean containOrderBy() {
        return !this.orderBy.isEmpty();
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
