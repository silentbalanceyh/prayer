package com.prayer.model.h2.vx;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.prayer.constant.Constants;

import io.vertx.core.http.HttpMethod;

/**
 * 对应表EVX_ROUTE
 * 
 * @author Lang
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "uniqueId")
public class RouteModel implements Serializable { // NOPMD
	// ~ Static Fields =======================================
	/**
	 * 
	 */
	private static final long serialVersionUID = -9091453176338568079L;
	// ~ Instance Fields =====================================
	/** K_ID: EVX_ROUTE表的主键 **/
	@JsonIgnore
	private String uniqueId;
	/** S_PARENT **/
	@JsonProperty("parent")
	private String parent;
	/** S_PATH **/
	@JsonProperty("path")
	private String path;
	/** S_MIME_CONSUMER **/
	@JsonProperty("consumerMimes")
	private List<String> consumerMimes;
	/** S_MIME_PRODUCER **/
	@JsonProperty("producerMimes")
	private List<String> producerMimes;
	/** S_METHOD **/
	@JsonProperty("method")
	private HttpMethod method;
	/** S_ORDER **/
	@JsonProperty("order")
	private int order = Constants.ORDER.NOT_SET;
	
	/** S_SHANDLER **/
	@JsonProperty("requestHandler")
	private String requestHandler;
	/** S_FHANDLER **/
	@JsonProperty("failureHandler")
	private String failureHandler;

	/** IS_SYNC **/
	@JsonProperty("sync")
	private boolean sync = true;
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
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
	public String getRequestHandler() {
		return requestHandler;
	}

	/**
	 * @param requestHandler
	 *            the requestHandler to set
	 */
	public void setRequestHandler(final String requestHandler) {
		this.requestHandler = requestHandler;
	}

	/**
	 * @return the failureHandler
	 */
	public String getFailureHandler() {
		return failureHandler;
	}

	/**
	 * @param failureHandler
	 *            the failureHandler to set
	 */
	public void setFailureHandler(final String failureHandler) {
		this.failureHandler = failureHandler;
	}

	/**
	 * @return the sync
	 */
	public boolean isSync() {
		return sync;
	}

	/**
	 * @param sync
	 *            the sync to set
	 */
	public void setSync(final boolean sync) {
		this.sync = sync;
	}

	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
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
	 * @param consumerMimes the consumerMimes to set
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
	 * @param producerMimes the producerMimes to set
	 */
	public void setProducerMimes(final List<String> producerMimes) {
		this.producerMimes = producerMimes;
	}

	// ~ hashCode,equals,toString ============================
	/** **/
	@Override
	public String toString() {
		return "RouteModel [uniqueId=" + uniqueId + ", parent=" + parent + ", path=" + path + ", method=" + method
				+ ", requestHandler=" + requestHandler + ", failureHandler=" + failureHandler + ", sync=" + sync + "]";
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
		final RouteModel other = (RouteModel) obj;
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
