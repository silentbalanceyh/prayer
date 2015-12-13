package com.prayer.bus;

import static com.prayer.util.Converter.toStr;
import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.singleton;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.net.telnet.TelnetClient;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.util.IOKit;
import com.prayer.util.PropertyKit;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.Resources;
import com.prayer.util.cv.Symbol;
import com.prayer.util.cv.SystemEnum.SecurityMode;
import com.prayer.vx.configurator.SecurityConfigurator;
import com.prayer.vx.configurator.ServerConfigurator;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class RestClient {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);
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
            info(LOGGER, "Connecting...");
            telnet.connect(CONFIGURATOR.getApiOptions().getHost(), CONFIGURATOR.getApiOptions().getPort());
            running = true;
            info(LOGGER, "Remote Api is running on : " + CONFIGURATOR.getEndPoint());
        } catch (IOException ex) {
            running = false;
            info(LOGGER, "Remote Api is not running. Error: " + ex.getMessage());
        }
    }

    // ~ Static Methods ======================================
    /** Mock **/
    public static ConcurrentMap<String, String> getHeaders(String username, String password) {
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
            retMap.put("Authorization", "Basic " + token);
        }
        return retMap;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * User Name
     * 
     * @return
     */
    public String getUserName() {
        return LOADER.getString("BASIC.api.username");
    }

    /**
     * Password
     * 
     * @return
     */
    public String getPassword() {
        return LOADER.getString("BASIC.api.password");
    }

    /** **/
    public JsonObject getParameter(final String jsonFile) {
        final String content = IOKit.getContent("/rest/input/" + jsonFile);
        JsonObject ret = new JsonObject();
        if (null != content) {
            ret = new JsonObject(content);
        }
        info(LOGGER, "[INPUT] Request Body = " + ret.encode());
        return ret;
    }

    /** **/
    public String getApi(final String path) {
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
    public JsonObject requestPost(final String api, final ConcurrentMap<String, String> headers,
            final JsonObject data) {
        return this.executeRequest(new HttpPost(api), headers, data);
    }

    /**
     * 
     * @param api
     * @param headers
     * @param data
     * @return
     */
    public JsonObject requestPut(final String api, final ConcurrentMap<String, String> headers, final JsonObject data) {
        return this.executeRequest(new HttpPut(api), headers, data);
    }

    /**
     * 
     * @param api
     * @param headers
     * @return
     */
    public JsonObject requestDelete(final String api, final ConcurrentMap<String, String> headers,
            final JsonObject data) {
        return this.executeRequest(new HttpDelete(api), headers, data);
    }

    /**
     * 
     * @param URI
     * @return
     */
    public JsonObject requestGet(final String api, final ConcurrentMap<String, String> headers) {
        return this.executeRequest(new HttpGet(api), headers);
    }

    // ~ Private Methods =====================================
    private JsonObject executeRequest(final HttpRequestBase request, final ConcurrentMap<String, String> headers) {
        final JsonObject ret = new JsonObject();
        ret.put("status", "SKIP");
        if (SecurityMode.BASIC == SECUTOR.getMode() && running) {
            // 直接注入Header
            this.injectHeaders(request, headers);
            // 请求发送
            this.sendRequest(request, ret);
        }
        return ret;
    }

    private JsonObject executeRequest(final HttpEntityEnclosingRequestBase request,
            final ConcurrentMap<String, String> headers, final JsonObject data) {
        final JsonObject ret = new JsonObject();
        ret.put("status", "SKIP");
        if (SecurityMode.BASIC == SECUTOR.getMode() && running) {
            // 直接注入Header
            this.injectHeaders(request, headers);
            try {
                // 参数设置
                this.injectEntity(request, data);
                // 请求发送
                this.sendRequest(request, ret);
            } catch (UnsupportedEncodingException ex) {
                info(LOGGER, "[ERR] Encoding error: ", ex);
            }
        }
        return ret;
    }

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
            info(LOGGER, "[ERR] Network issue: ", ex);
            injectRef.put("status", "ERROR");
        } finally {
            try {
                client.close();
            } catch (IOException ex) {
                info(LOGGER, "[ERR] Close Http Client issue : ", ex);
                injectRef.put("status", "ERROR");
            }
        }
        info(LOGGER, "[INFO] Finale Result : " + injectRef.encode());
    }

    private void injectEntity(final HttpEntityEnclosingRequestBase request, final JsonObject data)
            throws UnsupportedEncodingException {
        final StringEntity body = new StringEntity(URLEncoder.encode(data.encode(), Resources.SYS_ENCODING.name()));
        body.setContentEncoding(Resources.SYS_ENCODING.name());
        body.setContentType("application/json");
        request.setEntity(body);
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
