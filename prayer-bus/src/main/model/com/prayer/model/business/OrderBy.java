package com.prayer.model.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.constant.Symbol;
import com.prayer.facade.record.Record;
import com.prayer.fantasm.exception.AbstractException;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jodd.util.StringUtil;
import net.sf.oval.constraint.InstanceOf;
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
    private transient final List<String> orderByList = new ArrayList<>();

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param orderArr
     * @param record
     * @return
     */
    public static OrderBy create(@NotNull final JsonArray orderArr,
            @NotNull @InstanceOf(Record.class) final Record record) {
        return new OrderBy(orderArr, record);
    }

    /**
     * 
     * @param orderArr
     * @return
     */
    public static OrderBy create(@NotNull final JsonArray orderArr) {
        return new OrderBy(orderArr);
    }

    /**
     * 
     */
    public OrderBy() {
        if (!orderByList.isEmpty()) {
            orderByList.clear();
        }
    }

    // ~ Constructors ========================================
    /**
     * 
     * @param orderArr
     */
    private OrderBy(final JsonArray orderArr) {
        for (int idx = 0; idx < orderArr.size(); idx++) {
            final JsonObject item = orderArr.getJsonObject(idx);
            final Iterator<Map.Entry<String, Object>> itor = item.iterator();
            // Orders只包含一个元素
            if (itor.hasNext()) {
                final Map.Entry<String, Object> entry = itor.next();
                final String flag = entry.getValue().toString();
                this.add(entry.getKey(), flag);
            }
        }
    }

    /**
     * 
     * @param jsonArr
     */
    private OrderBy(final JsonArray orderArr, final Record record) {
        for (int idx = 0; idx < orderArr.size(); idx++) {
            final JsonObject item = orderArr.getJsonObject(idx);
            final Iterator<Map.Entry<String, Object>> itor = item.iterator();
            // Orders只包含一个元素
            if (itor.hasNext()) {
                final Map.Entry<String, Object> entry = itor.next();
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
        this.orderByList.add(statement.toString());
    }

    /** 清除掉当前的OrderBy Queue **/
    public void clear() {
        if (!orderByList.isEmpty()) {
            orderByList.clear();
        }
    }

    /** **/
    public String toSql() {
        String orderBySql = "";
        if (!orderByList.isEmpty()) {
            orderBySql = StringUtil.join(this.orderByList.toArray(Constants.T_STR_ARR), Symbol.COMMA);
        }
        return orderBySql;
    }

    /** **/
    public boolean valid() {
        return !this.orderByList.isEmpty();
    }
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
