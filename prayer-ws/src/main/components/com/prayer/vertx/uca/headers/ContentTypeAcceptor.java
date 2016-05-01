package com.prayer.vertx.uca.headers;

import static com.prayer.util.Converter.toStr;

import java.util.Arrays;
import java.util.List;

import com.prayer.exception.web._415MimeNotMatchException;
import com.prayer.facade.vtx.uca.headers.Acceptor;
import com.prayer.fantasm.vtx.uca.AbstractAcceptor;
import com.prayer.model.web.StatusCode;
import com.prayer.util.vertx.MimeParser;
import com.prayer.vertx.web.model.Envelop;

import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerRequest;

/**
 * 
 * @author Lang
 *
 */
public class ContentTypeAcceptor extends AbstractAcceptor implements Acceptor {

    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 如果最终Accept了，返回为空数据的Envelop
     */
    @Override
    public Envelop accept(final HttpServerRequest request, final String... expectes) {
        /** 1.检查Content-Type是否存在 **/
        Envelop envelop = this.acceptRequired(request, HttpHeaders.CONTENT_TYPE.toString());
        if (envelop.succeeded()) {
            /** 2.检查Content-Type和期望值是否匹配 **/
            envelop = this.acceptMimes(request, Arrays.asList(expectes));
        }
        return envelop;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================

    /** 检查Content Type，返回415错误 **/
    private Envelop acceptMimes(final HttpServerRequest request, final List<String> expectes) {
        final String value = MimeParser.content(request.headers());
        /** 1.Expected的值 **/
        boolean flag = false;
        for (final String expected : expectes) {
            flag = MimeParser.wideMatch(value, expected);
            if (flag) {
                // 匹配
                break;
            }
        }
        /** 2.返回Envelop **/
        Envelop envelop = Envelop.success();
        if (!flag) {
            envelop = Envelop.failure(new _415MimeNotMatchException(getClass(), toStr(expectes), value),
                    StatusCode.UNSUPPORTED_MEDIA_TYPE);
        }
        return envelop;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
