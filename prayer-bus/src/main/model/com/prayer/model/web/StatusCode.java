package com.prayer.model.web;

import jodd.util.StringUtil;

/**
 * 
 * @author Lang
 *
 */
public enum StatusCode {
    // ~ 1xx =================================================
    /** 100 Continue **/
    CONTINUE(100, "Continue"),

    /** 101 Switching Protocols **/
    SWITCHING_PROTOCOLS(101, "Switching Protocols"),

    /** 102 Processing **/
    PROCESSING(102, "Processing"),
    // ~ 2xx =================================================
    /** 200 OK **/
    OK(200, "OK"),

    /** 201 Created **/
    CREATED(201, "Created"),

    /** 202 Accepted **/
    ACCEPTED(202, "Accepted"),

    /** 203 Non-Authoritative Information **/
    NOT_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information"),

    /** 204 No Content **/
    NO_CONTENT(204, "No Content"),

    /** 205 Reset Content **/
    RESET_CONTENT(205, "Reset Content"),

    /** 206 Partial Content **/
    PARTIAL_CONTENT(206, "Partial Content"),

    /** 207 Multi-Status **/
    MULTI_STATUS(207, "Multi-Status"),
    // ~ 3xx =================================================
    /** 300 Multiple Choices **/
    MULTIPLE_CHOICES(300, "Multiple Choices"),

    /** 301 Moved Permanently **/
    MOVED_PERMANENTLY(301, "Moved Permanently"),

    /** 302 Move Temporarily **/
    MOVE_TEMPORARILY(302, "Move Temporarily"),

    /** 303 See Other **/
    SEE_OTHER(303, "See Other"),

    /** 304 Not Modified **/
    NOT_MODIFIED(304, "Not Modified"),

    /** 305 Use Proxy **/
    USE_PROXY(305, "Use Proxy"),

    /** 306 Switch Proxy **/
    SWITCH_PROXY(306, "Switch Proxy"),

    /** 307 Temporary Redirect **/
    TEMPORARY_REDIRECT(307, "Temporary Redirect"),
    // ~ 4xx =================================================
    /** 400 Bad Request **/
    BAD_REQUEST(400, "Bad Request"),

    /** 401 Unauthorized **/
    UNAUTHORIZED(401, "Unauthorized"),

    /** 402 Payment Required **/
    PAYMENT_REQUIRED(402, "Payment Required"),

    /** 403 Forbidden **/
    FORBIDDEN(403, "Forbidden"),

    /** 404 Not Found **/
    NOT_FOUND(404, "Not Found"),

    /** 405 Method Not Allowed **/
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),

    /** 406 Not Acceptable **/
    NOT_ACCEPTABLE(406, "Not Acceptable"),

    /** 407 Proxy Authentication Required **/
    PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),

    /** 408 Request Timeout **/
    REQUEST_TIMEOUT(408, "Request Timeout"),

    /** 409 Conflict **/
    CONFLICT(409, "Conflict"),

    /** 410 Gone **/
    GONE(410, "Gone"),

    /** 411 Length Required **/
    LENGTH_REQUIRED(411, "Length Required"),

    /** 412 Precondition Failed **/
    PRECONDITION_FAILED(412, "Precondition Failed"),

    /** 413 Request Entity Too Large **/
    REQUEST_ENTITY_TOO_LARGE(413, "Request Entity Too Large"),

    /** 414 Request-URI Too Long **/
    REQUEST_URI_TOO_LONG(414, "Request-URI Too Long"),

    /** 415 Unsupported Media Type **/
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),

    /** 416 Requested Range Not Satisfiable **/
    REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested Range Not Satisfiable"),

    /** 417 Expectation Failed **/
    EXPECTATION_FAILED(417, "Expectation Failed"),

    /** 418 Too Many Connections **/
    TOO_MANY_CONNECTIONS(421, "Too Many Connections"),

    /** 422 Unprocessable Entity **/
    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),

    /** 423 Locked **/
    LOCKED(423, "Locked"),

    /** 424 Failed Dependency **/
    FAILED_DEPENDENCY(424, "Failed Dependency"),

    /** 425 Unordered Collection **/
    UNORDERED_COLLECTION(425, "Unordered Collection"),

    /** 426 Upgrade Required **/
    UPGRADE_REQUIRED(426, "Upgrade Required"),

    /** 449 Retry With **/
    RETRY_WITH(449, "Retry With"),
    // ~ 5xx =================================================

    /** 500 Internal Server Error **/
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    /** 501 Not Implemented **/
    NOT_IMPLEMENTED(501, "Not Implemented"),

    /** 502 Bad Gateway **/
    BAD_GATEWAY(502, "Bad Gateway"),

    /** 503 Service Unavailable **/
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),

    /** 504 Gateway Timeout **/
    GATEWAY_TIMEOUT(504, "Gateway Timeout"),

    /** 505 HTTP Version Not Supported **/
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported"),

    /** 506 Variant Also Negotiates **/
    VARIANT_ALSO_NEGOTIATES(506, "Variant Also Negotiates"),

    /** 507 Insufficient Storage **/
    INSUFFICIENT_STORAGE(507, "Insufficient Storage"),

    /** 508 Badwidth Limit Exceeded **/
    BANDWIDTH_LIMIT_EXCEEDED(508, "Bandwidth Limit Exceeded"),

    /** 510 Not Extended **/
    NOT_EXTENDED(510, "Not Extended"),

    /** 600 Unparseable Response Headers **/
    UNPARSEABLE_RESPONSE_HEADERS(600, "Unparseable Response Headers");
    // ~ Instance Fields =====================================
    /** Http Status **/
    private int statusCode;
    /** Http Message **/
    private String message;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 从int转换成StatusCode
     * 
     * @param statusCode
     * @return
     */
    public static StatusCode fromCode(final int statusCode) {
        final StatusCode[] values = StatusCode.values();
        StatusCode code = null;
        for (final StatusCode value : values) {
            if (statusCode == value.statusCode) {
                code = value;
                break;
            }
        }
        return code;
    }

    /**
     * 从String转换成StatusCode
     * 
     * @param message
     * @return
     */
    public static StatusCode fromString(final String message) {
        final StatusCode[] values = StatusCode.values();
        StatusCode code = null;
        for (final StatusCode value : values) {
            if (StringUtil.equals(message, value.message)) {
                code = value;
                break;
            }
        }
        return code;
    }

    // ~ Constructors ========================================
    /** **/
    StatusCode(final int statusCode, final String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 返回状态代码 **/
    public int status() {
        return this.statusCode;
    }

    /** **/
    @Override
    public String toString() {
        return this.message;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
