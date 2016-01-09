package com.prayer.model.vertx;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.prayer.base.model.AbstractEntity;
import com.prayer.constant.Constants;
import com.prayer.facade.entity.Entity;
import com.prayer.plugin.jackson.ClassDeserializer;
import com.prayer.plugin.jackson.ClassSerializer;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;

/**
 * 对应表EVX_ROUTE
 * 
 * @author Lang
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "uniqueId")
public class PERoute extends AbstractEntity { // NOPMD
    // ~ Static Fields =======================================
    /**
     * 
     */
    private static final long serialVersionUID = -9091453176338568079L;
    // ~ Instance Fields =====================================
    /** K_ID: EVX_ROUTE表的主键 **/
    @JsonProperty(ID)
    private String uniqueId;
    /** S_PARENT **/
    @JsonProperty(PARENT)
    private String parent;
    /** S_PATH **/
    @JsonProperty(PATH)
    private String path;
    /** S_MIME_CONSUMER **/
    @JsonProperty(CONSUMER_MIMES)
    private List<String> consumerMimes;
    /** S_MIME_PRODUCER **/
    @JsonProperty(PRODUCER_MIMES)
    private List<String> producerMimes;
    /** S_METHOD **/
    @JsonProperty(METHOD)
    private HttpMethod method;
    /** S_ORDER **/
    @JsonProperty(ORDER)
    private int order = Constants.ORDER.NOT_SET;

    /** S_SHANDLER **/
    @JsonProperty(REQUEST_HANDLER)
    @JsonSerialize(using = ClassSerializer.class)
    @JsonDeserialize(using = ClassDeserializer.class)
    private Class<?> requestHandler;
    /** S_FHANDLER **/
    @JsonProperty(FAILURE_HANDLER)
    @JsonSerialize(using = ClassSerializer.class)
    @JsonDeserialize(using = ClassDeserializer.class)
    private Class<?> failureHandler;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public PERoute() {
    }

    /** **/
    public PERoute(final JsonObject data) {
        this.fromJson(data);
    }

    /** **/
    public PERoute(final Buffer buffer) {
        this.readFromBuffer(Constants.POS, buffer);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 转成Json **/
    @Override
    public JsonObject toJson() {
        final JsonObject data = new JsonObject();
        writeString(data, ID, this::getUniqueId);
        writeString(data, PARENT, this::getParent);
        writeString(data, PATH, this::getPath);
        writeList(data, CONSUMER_MIMES, this::getConsumerMimes);
        writeList(data, PRODUCER_MIMES, this::getProducerMimes);
        writeEnum(data, METHOD, this::getMethod);
        writeInt(data, ORDER, this::getOrder);
        writeClass(data, REQUEST_HANDLER, this::getRequestHandler);
        writeClass(data, FAILURE_HANDLER, this::getFailureHandler);
        return data;
    }

    /** 从Json反序列化 **/
    @Override
    public Entity fromJson(final JsonObject data) {
        readString(data, ID, this::setUniqueId);
        readString(data, PARENT, this::setParent);
        readString(data, PATH, this::setPath);
        readList(data, CONSUMER_MIMES, this::setConsumerMimes);
        readList(data, PRODUCER_MIMES, this::setProducerMimes);
        readEnum(data, METHOD, this::setMethod, HttpMethod.class);
        readInt(data, ORDER, this::setOrder);
        readClass(data, REQUEST_HANDLER, this::setRequestHandler);
        readClass(data, FAILURE_HANDLER, this::setFailureHandler);
        return null;
    }

    /** 写入Buffer **/
    @Override
    public void writeToBuffer(final Buffer buffer) {
        writeString(buffer, this::getUniqueId);
        writeString(buffer, this::getParent);
        writeString(buffer, this::getPath);
        writeList(buffer, this::getConsumerMimes);
        writeList(buffer, this::getProducerMimes);
        writeEnum(buffer, this::getMethod);
        writeInt(buffer, this::getOrder);
        writeClass(buffer, this::getRequestHandler);
        writeClass(buffer, this::getFailureHandler);
    }

    /** 从Buffer中读取 **/
    @Override
    public int readFromBuffer(int pos, final Buffer buffer) {
        pos = readString(pos, buffer, this::setUniqueId);
        pos = readString(pos, buffer, this::setParent);
        pos = readString(pos, buffer, this::setPath);
        pos = readList(pos, buffer, this::setConsumerMimes);
        pos = readList(pos, buffer, this::setProducerMimes);
        pos = readEnum(pos, buffer, this::setMethod, HttpMethod.class);
        pos = readInt(pos, buffer, this::setOrder);
        pos = readClass(pos, buffer, this::setRequestHandler);
        pos = readClass(pos, buffer, this::setFailureHandler);
        return 0;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================

    /**
     * @return the uniqueId
     */
    public String getUniqueId() {
        return uniqueId;
    }

    /**
     * @param uniqueId
     *            the uniqueId to set
     */
    public void setUniqueId(final String uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     * @return the parent
     */
    public String getParent() {
        return parent;
    }

    /**
     * @param parent
     *            the parent to set
     */
    public void setParent(final String parent) {
        this.parent = parent;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     *            the path to set
     */
    public void setPath(final String path) {
        this.path = path;
    }

    /**
     * @return the method
     */
    public HttpMethod getMethod() {
        return method;
    }

    /**
     * @param method
     *            the method to set
     */
    public void setMethod(final HttpMethod method) {
        this.method = method;
    }

    /**
     * @return the requestHandler
     */
    public Class<?> getRequestHandler() {
        return requestHandler;
    }

    /**
     * @param requestHandler
     *            the requestHandler to set
     */
    public void setRequestHandler(final Class<?> requestHandler) {
        this.requestHandler = requestHandler;
    }

    /**
     * @return the failureHandler
     */
    public Class<?> getFailureHandler() {
        return failureHandler;
    }

    /**
     * @param failureHandler
     *            the failureHandler to set
     */
    public void setFailureHandler(final Class<?> failureHandler) {
        this.failureHandler = failureHandler;
    }

    /**
     * @return the order
     */
    public int getOrder() {
        return order;
    }

    /**
     * @param order
     *            the order to set
     */
    public void setOrder(final int order) {
        this.order = order;
    }

    /**
     * @return the consumerMimes
     */
    public List<String> getConsumerMimes() {
        return consumerMimes;
    }

    /**
     * @param consumerMimes
     *            the consumerMimes to set
     */
    public void setConsumerMimes(final List<String> consumerMimes) {
        this.consumerMimes = consumerMimes;
    }

    /**
     * @return the producerMimes
     */
    public List<String> getProducerMimes() {
        return producerMimes;
    }

    /**
     * @param producerMimes
     *            the producerMimes to set
     */
    public void setProducerMimes(final List<String> producerMimes) {
        this.producerMimes = producerMimes;
    }

    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public String toString() {
        return this.toJson().encode();
    }

    /** **/
    @Override
    public int hashCode() { // NOPMD
        final int prime = Constants.HASH_BASE;
        int result = 1;
        result = prime * result + ((method == null) ? 0 : method.hashCode());
        result = prime * result + ((parent == null) ? 0 : parent.hashCode());
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        result = prime * result + ((uniqueId == null) ? 0 : uniqueId.hashCode());
        return result;
    }

    /** **/
    @Override
    public boolean equals(final Object obj) { // NOPMD
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PERoute other = (PERoute) obj;
        if (method != other.method) {
            return false;
        }
        if (parent == null) {
            if (other.parent != null) {
                return false;
            }
        } else if (!parent.equals(other.parent)) {
            return false;
        }
        if (path == null) {
            if (other.path != null) {
                return false;
            }
        } else if (!path.equals(other.path)) {
            return false;
        }
        if (uniqueId == null) {
            if (other.uniqueId != null) {
                return false;
            }
        } else if (!uniqueId.equals(other.uniqueId)) {
            return false;
        }
        return true;
    }
}
