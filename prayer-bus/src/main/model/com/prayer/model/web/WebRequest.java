package com.prayer.model.web;

import static com.prayer.util.reflection.Instance.singleton;

import java.io.Serializable;

import com.prayer.business.ensurer.JArrayEnsurer;
import com.prayer.business.ensurer.JObjectEnsurer;
import com.prayer.business.ensurer.StringEnsurer;
import com.prayer.exception.web.ServiceParamInvalidException;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.model.entity.Ensurer;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.business.OrderBy;
import com.prayer.model.business.Pager;
import com.prayer.model.business.Projection;
import com.prayer.util.Converter;
import com.prayer.util.string.StringKit;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class WebRequest implements Serializable {
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = 821436569725674257L;
    // ~ Instance Fields =====================================
    /** **/
    private transient String identifier;
    /** 执行请求的脚本链 **/
    private transient String script;
    /** Json数据 **/
    private transient JsonObject data;
    /** Pager对象 **/
    private transient Pager pager;
    /** Order By排序对象 **/
    private transient OrderBy orders;
    /** Filters对象 **/
    private transient Projection filters;
    /** Method对象提取 **/
    private transient HttpMethod method;
    /** 构造Request时的Error **/
    private transient AbstractException error;

    /** String验证器 **/
    private transient final Ensurer<JsonObject, String> strEnsurer = singleton(StringEnsurer.class);
    /** Json验证器 **/
    private transient final Ensurer<JsonObject, JsonObject> jsonEnsurer = singleton(JObjectEnsurer.class);
    /** Json Array验证器 **/
    private transient final Ensurer<JsonObject, JsonArray> arrEnsurer = singleton(JArrayEnsurer.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param params
     */
    public WebRequest(@NotNull final JsonObject params) {
        /** 1.参数准备阶段 **/
        this.prepareIdentifier(params);
        /** 2.执行Required验证 **/
        this.ensureRequired(params);
        /** 3.执行Optional验证 **/
        this.ensureOptional(params);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** ServiceRequest是否非法 **/
    public boolean success() {
        return null == this.error;
    }

    // ~ Private Methods =====================================
    /** 2.执行可选步骤 **/
    private void ensureOptional(final JsonObject params) {
        // 1.Pager
        this.ensurePager(params);
        // 2.OrderBy
        this.ensureOrderBy(params);
        // 3.Filters
        this.ensureFilters(params);
    }

    /** 2.3.执行可选参数Filters **/
    private void ensureFilters(final JsonObject params) {
        if (success()) {
            try {
                this.filters = Projection.create(this.arrEnsurer.ensureOptional(params, Constants.PARAM.FILTERS));
            } catch (AbstractException ex) {
                this.error = ex;
            }
        }
    }

    /** 2.2.执行可选参数Order **/
    private void ensureOrderBy(final JsonObject params) {
        if (success()) {
            try {
                final JsonObject data = params.getJsonObject(Constants.PARAM.DATA);
                final JsonArray orderData = this.arrEnsurer.ensureOptional(data, Constants.PARAM.ORDERS);
                this.orders = OrderBy.create(orderData);
            } catch (AbstractException ex) {
                this.error = ex;
            }
        }
    }

    /** 2.1.执行可选参数Pager **/
    private void ensurePager(final JsonObject params) {
        if (success()) {
            try {
                final JsonObject pageJson = this.jsonEnsurer.ensureOptional(params, Constants.PARAM.PAGE.NAME);
                this.pager = Pager.create(pageJson);
            } catch (AbstractException ex) {
                this.error = ex;
            }
        }
    }

    /** 1.执行必须参数 **/
    private void ensureRequired(final JsonObject params) {
        if (success()) {
            try {
                // identifier必须
                this.identifier = this.strEnsurer.ensureRequired(params, Constants.PARAM.ID);
                // TODO：script参数暂定义为可选
                this.script = this.strEnsurer.ensureRequired(params, Constants.PARAM.SCRIPT);
                // method必须
                final String method = this.strEnsurer.ensureRequired(params, Constants.PARAM.METHOD);
                this.method = Converter.fromStr(HttpMethod.class, method);
                // data必须
                this.data = this.jsonEnsurer.ensureRequired(params, Constants.PARAM.DATA);
            } catch (AbstractException ex) {
                this.error = ex;
            }
        }
    }

    /** 0.ID值的过滤 **/
    private void prepareIdentifier(final JsonObject params) {
        if (success()) {
            try {
                final String identifier = params.getString(Constants.PARAM.ID);
                if (StringKit.isNonNil(identifier) && identifier.startsWith("RP")) {
                    final String[] replacedId = identifier.split("\\$");
                    if (Constants.ONE < replacedId.length) {
                        final String idAttr = replacedId[Constants.ONE];
                        final String idValue = params.getJsonObject(Constants.PARAM.ID).getString(idAttr);
                        params.put(Constants.PARAM.ID, idValue);
                    }
                }
            } catch (ClassCastException ex) {
                this.error = new ServiceParamInvalidException(getClass(), ex.getMessage());
            }
        }
    }
    // ~ Get/Set =============================================

    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @return the script
     */
    public String getScript() {
        return script;
    }

    /**
     * @return the data
     */
    public JsonObject getData() {
        return data;
    }

    /**
     * @return the pager
     */
    public Pager getPager() {
        return pager;
    }

    /**
     * @return the orders
     */
    public OrderBy getOrders() {
        return orders;
    }

    /**
     * 
     * @return the filters
     */
    public Projection getProjection() {
        return filters;
    }

    /**
     * @return the method
     */
    public HttpMethod getMethod() {
        return method;
    }

    /**
     * @return the error
     */
    public AbstractException getError() {
        return error;
    }

    // ~ hashCode,equals,toString ============================

}
