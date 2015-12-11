package com.prayer.bus;

import static com.prayer.util.Converter.fromStr;
import static com.prayer.util.Converter.toStr;
import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.singleton;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.net.telnet.TelnetClient;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.model.web.StatusCode;
import com.prayer.util.IOKit;
import com.prayer.util.PropertyKit;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.Resources;
import com.prayer.util.cv.Symbol;
import com.prayer.util.cv.SystemEnum.ResponseCode;
import com.prayer.util.cv.SystemEnum.SecurityMode;
import com.prayer.vx.configurator.SecurityConfigurator;
import com.prayer.vx.configurator.ServerConfigurator;

import io.vertx.core.json.JsonObject;
import jodd.util.StringUtil;

/**
 * 
 * @author Lang
 *
 */
public abstract class AbstractRBTestCase {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRBTestCase.class);
    /** **/
    private static final ServerConfigurator CONFIGURATOR = singleton(ServerConfigurator.class);
    /** **/
    private static final SecurityConfigurator SECUTOR = singleton(SecurityConfigurator.class);
    /** **/
    private static final PropertyKit LOADER = new PropertyKit(AbstractRBTestCase.class, "/account.properties");
    /** **/
    private static final ConcurrentMap<String, String> DEFAULT_HEADERS;
    /** **/
    private static boolean running = false;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /** **/
    static {
        // DEFAULT HEADERS
        DEFAULT_HEADERS = getHeaders(LOADER.getString("BASIC.api.username"), LOADER.getString("BASIC.api.password"));
        // Telnet Checking
        final TelnetClient telnet = new TelnetClient("VT220");
        try {
            info(LOGGER, "[INFO] Connecting...");
            telnet.connect(CONFIGURATOR.getApiOptions().getHost(), CONFIGURATOR.getApiOptions().getPort());
            running = true;
            info(LOGGER, "[INFO] Remote Api is running on : " + CONFIGURATOR.getEndPoint());
        } catch (IOException ex) {
            running = false;
            info(LOGGER, "[INFO] Remote Api is not running. Error: " + ex.getMessage());
        }
    }

    // ~ Static Methods ======================================
    /** Mock **/
    protected static ConcurrentMap<String, String> getHeaders(String username, String password) {
        final ConcurrentMap<String, String> retMap = new ConcurrentHashMap<>();
        retMap.put("User-Agent", "Apache-HttpClient/4.5.3 (java 1.7)");
        retMap.put("Connection", "Keep-Alive");
        retMap.put("Host",
                CONFIGURATOR.getApiOptions().getHost() + Symbol.COLON + CONFIGURATOR.getApiOptions().getPort());
        retMap.put("Accept-Encoding", "gzip,deflate");
        if (null != username || null != password) {
            // User Name Missing
            if (null == username) {
                username = Constants.EMPTY_STR;
            }
            // Password Missing
            if (null == password) {
                password = Constants.EMPTY_STR;
            }
            final String token = Base64.getEncoder()
                    .encodeToString((username + Symbol.COLON + password).getBytes(Resources.SYS_ENCODING));
            info(LOGGER, "[INFO] Token : " + token);
            retMap.put("Authorization", "Basic " + token);
        }
        return retMap;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    /** **/
    public abstract Logger getLogger();

    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * User Name
     * 
     * @return
     */
    protected String getUserName() {
        return LOADER.getString("BASIC.api.username");
    }

    /**
     * Password
     * 
     * @return
     */
    protected String getPassword() {
        return LOADER.getString("BASIC.api.password");
    }

    /** **/
    protected JsonObject getParameter(final String jsonFile) {
        final String content = IOKit.getContent("/rest/input/" + jsonFile);
        JsonObject ret = new JsonObject();
        if (null != content) {
            ret = new JsonObject(content);
        }
        info(getLogger(), "[T] Request Body = " + ret.encode());
        return ret;
    }

    /** **/
    protected String getApi(final String path) {
        final StringBuilder api = new StringBuilder();
        api.append(CONFIGURATOR.getEndPoint());
        api.append(path);
        return api.toString();
    }

    /**
     * 
     * @param api
     * @param headers
     * @param data
     * @return
     */
    protected JsonObject requestPost(final String api, final ConcurrentMap<String, String> headers,
            final JsonObject data) {
        final JsonObject ret = new JsonObject();
        ret.put("status", "SKIP");
        if (SecurityMode.BASIC == SECUTOR.getMode() && running) {
            HttpPost request = new HttpPost(api);
            // 直接注入Header
            this.injectHeaders(request, headers);
            try {
                // 参数设置
                final StringEntity body = new StringEntity(data.encode());
                body.setContentEncoding(Resources.SYS_ENCODING.name());
                body.setContentType("application/json");
                request.setEntity(body);
                // 请求发送
                this.sendRequest(request, ret);
            } catch (UnsupportedEncodingException ex) {
                info(getLogger(), "[ERR] Encoding error: ", ex);
            }
        }
        return ret;
    }

    /**
     * 
     * @param URI
     * @return
     */
    protected JsonObject requestGet(final String api, final ConcurrentMap<String, String> headers) {
        final JsonObject ret = new JsonObject();
        ret.put("status", "SKIP");
        if (SecurityMode.BASIC == SECUTOR.getMode() && running) {
            HttpGet request = new HttpGet(api);
            // 直接注入Header
            this.injectHeaders(request, headers);
            // 请求发送
            this.sendRequest(request, ret);
        }
        return ret;
    }

    // ~ Response Check =====================================
    /** **/
    protected boolean checkSuccess(final JsonObject resp) {
        boolean ret = true;
        if (StatusCode.OK.status() != resp.getInteger("code")) {
            ret = false;
        }
        if (!StringUtil.equals(resp.getString("status"), "PASSED")) {
            ret = false;
        }
        JsonObject data = resp.getJsonObject("data");
        final ResponseCode code = fromStr(ResponseCode.class, data.getString("returnCode"));
        if (ResponseCode.SUCCESS != code) {
            ret = false;
        }
        if (StatusCode.OK.status() != data.getInteger("status")) {
            ret = false;
        }
        data = data.getJsonObject("data");
        if (data.isEmpty()) {
            ret = false;
        }
        return ret;
    }

    /** **/
    protected boolean checkErrorCode(final JsonObject error, final int code) {
        final int errorCode = error.getInteger("code");
        return errorCode == code;
    }

    /** **/
    protected boolean checkHttpStatus(final JsonObject resp, final int code) {
        final int statusCode = resp.getInteger("code");
        return statusCode == code;
    }

    /** **/
    protected boolean checkReturnCode(final JsonObject data, final ResponseCode retCode) {
        final ResponseCode code = fromStr(ResponseCode.class, data.getString("returnCode"));
        return code == retCode;
    }

    /** **/
    protected boolean checkStatus(final JsonObject status, final StatusCode statusCode) {
        boolean ret = true;
        if (status.getInteger("code") != statusCode.status()) {
            ret = false;
        }
        if (!StringUtil.equals(status.getString("literal"), statusCode.name().toUpperCase(Locale.getDefault()))) {
            ret = false;
        }
        return ret;
    }

    // ~ Private Methods =====================================

    private void sendRequest(final HttpRequestBase request, final JsonObject injectRef) {
        final CloseableHttpClient client = HttpClients.createDefault();
        try {
            final CloseableHttpResponse resp = client.execute(request);
            injectRef.put("code", resp.getStatusLine().getStatusCode());
            try {
                final HttpEntity entity = resp.getEntity();
                if (entity != null) {
                    final InputStream in = entity.getContent();
                    injectRef.put("status", "PASSED");
                    injectRef.put("data", new JsonObject(toStr(in)));
                }
            } finally {
                resp.close();
            }
        } catch (IOException ex) {
            info(getLogger(), "[ERR] Network issue: ", ex);
            injectRef.put("status", "ERROR");
        } finally {
            try {
                client.close();
            } catch (IOException ex) {
                info(getLogger(), "[ERR] Close Http Client issue : ", ex);
                injectRef.put("status", "ERROR");
            }
        }
        info(getLogger(),"[INFO] Finale Result : " + injectRef.encode());
    }

    private void injectHeaders(final HttpRequestBase request, final ConcurrentMap<String, String> headers) {
        if (null == headers || headers.isEmpty()) {
            for (final String name : DEFAULT_HEADERS.keySet()) {
                request.addHeader(name, DEFAULT_HEADERS.get(name));
            }
        } else {
            for (final String name : headers.keySet()) {
                request.addHeader(name, headers.get(name));
            }
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
