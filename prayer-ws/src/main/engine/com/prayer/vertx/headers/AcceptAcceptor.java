package com.prayer.vertx.headers;

import static com.prayer.util.Converter.toStr;
import static com.prayer.util.debug.Log.info;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.web._406ClientNotAcceptException;
import com.prayer.facade.engine.cv.MsgVertx;
import com.prayer.facade.vtx.headers.Acceptor;
import com.prayer.fantasm.vtx.header.AbstractAcceptor;
import com.prayer.model.web.StatusCode;
import com.prayer.vertx.util.MimeParser;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
public class AcceptAcceptor extends AbstractAcceptor implements Acceptor {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(AcceptAcceptor.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Envelop accept(final HttpServerRequest request, final String... expectes) {
        /** 1.如果expectList中包含了通配符，则直接Skip掉Required的验证 **/
        final List<String> expectList = Arrays.asList(expectes);
        Envelop envelop = null;
        if (expectList.contains("*/*")) {
            /** 2.服务端不限制偏好信息，不做任何检查 **/
            envelop = Envelop.success(new JsonObject());
        } else {
            /** 2.偏好有要求，先检查Accept头 **/
            envelop = this.acceptRequired(request, HttpHeaders.ACCEPT.toString());
            if (envelop.succeeded()) {
                /** 3.检查Accept和期望值是否匹配 **/
                envelop = this.acceptMimes(request, expectList);
            }
        }
        return envelop;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private Envelop acceptMimes(final HttpServerRequest request, final List<String> expectes) {
        /** 1.从Accept中提取每种Mime以及偏好值 **/
        final ConcurrentMap<String, Double> mimes = MimeParser.accept(request.headers());
        /** 2.Vertx中使用了expectes中的producer匹配，所以必须让Accept头中的信息和expectes匹配 **/
        Envelop envelop = Envelop.success(new JsonObject());
        /** 3.客户端万能偏好 **/
        if (!mimes.containsKey("*/*") || 0.0 >= mimes.get("*/*")) {
            // 移除掉*/*，这个时候*/*的值已经是0.0以下了，不需要检测
            mimes.remove("*/*");
            // 检测其他的Mime
            boolean matched = false;
            outer: for (final String actual : expectes) {
                /** 4.提取Mimes **/
                for (final String expect : mimes.keySet()) {
                    /** 5.需要验证匹配的mimes必须保证q > 0.0，因为q=0.0是禁止 **/
                    final Double qvalue = mimes.get(expect);
                    final boolean match = MimeParser.wideMatch(actual, expect);
                    info(LOGGER, MessageFormat.format(MsgVertx.REQ_ACCEPT_MATCH, getClass().getSimpleName(), expect,
                            String.valueOf(qvalue), actual, match));
                    if (qvalue > 0.0) {
                        // 如果qvalue大于0.0
                        // actual不在expectes清单中，则抛出异常
                        if (match) {
                            matched = true;
                            break outer;
                        }
                    } else {
                        // 如果qvalue小于0.0
                        // actual在expectes清单中，则抛出异常
                        if (!match) {
                            matched = true;
                            break outer;
                        }
                    }
                }
            }
            /** 不匹配 **/
            if (!matched) {
                envelop = Envelop.failure(new _406ClientNotAcceptException(getClass(), toStr(expectes),
                        request.headers().get(HttpHeaders.ACCEPT.toString())), StatusCode.NOT_ACCEPTABLE);
            }
        }
        return envelop;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
