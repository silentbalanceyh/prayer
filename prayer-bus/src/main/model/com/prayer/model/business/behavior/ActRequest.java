package com.prayer.model.business.behavior;

import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.singleton;

import java.io.Serializable;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class ActRequest implements Serializable {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ActRequest.class);
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
    public ActRequest(@NotNull final JsonObject params) {
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

    /** 2.3.执行可选参数Filters，Filters不可以为null **/
    private void ensureFilters(final JsonObject params) {
        if (success()) {
            /** Filters数据 **/
            try {
                final JsonObject data = params.getJsonObject(Constants.PARAM.QUERY);
                JsonArray filterData = new JsonArray();
                /** 1.是否包含query节点 **/
                if (null != data) {
                    filterData = this.arrEnsurer.ensureOptional(data, Constants.PARAM.ADMINICLE.FILTERS);
                    /** 2.是否可读取filters **/
                    if (null == filterData) {
                        filterData = new JsonArray();
                    }
                }
                this.filters = Projection.create(filterData);
            } catch (AbstractException ex) {
                peError(LOGGER, ex);
                this.error = ex;
                this.filters = Projection.create(new JsonArray());
            }
        }
    }

    /** 2.2.执行可选参数OrderBy，OrderBy可以为null **/
    private void ensureOrderBy(final JsonObject params) {
        if (success()) {
            try {
                final JsonObject data = params.getJsonObject(Constants.PARAM.QUERY);
                JsonArray orderData = new JsonArray();
                /** 1.是否可包含query节点 **/
                if (null != data) {
                    orderData = this.arrEnsurer.ensureOptional(data, Constants.PARAM.ADMINICLE.ORDERS);
                    /** 2.是否可读取orders **/
                    if (null == orderData) {
                        orderData = new JsonArray();
                    }
                    /** 3.这个时候创建orders **/
                    this.orders = OrderBy.create(orderData);
                }
            } catch (AbstractException ex) {
                peError(LOGGER, ex);
                this.error = ex;
                this.orders = null;
            }
        }
    }

    /** 2.1.执行可选参数Pager，Pager可以为null **/
    private void ensurePager(final JsonObject params) {
        if (success()) {
            try {
                final JsonObject data = params.getJsonObject(Constants.PARAM.QUERY);
                JsonObject pageJson = new JsonObject();
                /** 1.是否可包含query节点 **/
                if (null != data) {
                    pageJson = this.jsonEnsurer.ensureOptional(data, Constants.PARAM.ADMINICLE.PAGER);
                    /** 2.是否可读取pager **/
                    if (null == pageJson) {
                        pageJson = new JsonObject();
                    }
                    /** 3.这个时候创建Pager **/
                    this.pager = Pager.create(pageJson);
                }
            } catch (AbstractException ex) {
                peError(LOGGER, ex);
                this.error = ex;
                this.pager = null;
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
                this.script = this.strEnsurer.ensureOptional(params, Constants.PARAM.SCRIPT);
                // method必须
                final String method = this.strEnsurer.ensureRequired(params, Constants.PARAM.METHOD);
                this.method = Converter.fromStr(HttpMethod.class, method.toUpperCase(Locale.getDefault()));
                if (null != this.method) {
                    // data必须
                    this.data = this.jsonEnsurer.ensureRequired(params, Constants.PARAM.DATA);
                    // adminicle必须，可以为null，但是必须属性
                    this.jsonEnsurer.ensureOptional(params, Constants.PARAM.QUERY);
                } else {
                    // method必须合法
                    throw new ServiceParamInvalidException(getClass(), "method = " + method);
                }
            } catch (AbstractException ex) {
                peError(LOGGER, ex);
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
                jvmError(LOGGER, ex);
                this.error = new ServiceParamInvalidException(getClass(), ex.getMessage());
            }
        }
    }

    // ~ Data Modify =========================================
    /**
     * 将数据放到data中
     * 
     * @param key
     * @param value
     */
    public void putData(final String key, final Object value) {
        this.data.put(key, value);
    }

    /**
     * 
     * @param method
     */
    public void putData(final HttpMethod method) {
        this.method = method;
    }

    /**
     * 清除Data中的数据
     */
    public void clearData() {
        this.data.clear();
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
