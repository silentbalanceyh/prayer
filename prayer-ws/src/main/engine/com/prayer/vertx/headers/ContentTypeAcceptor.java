package com.prayer.vertx.headers;

import static com.prayer.util.Converter.toStr;

import java.util.Arrays;
import java.util.List;

import com.prayer.exception.web._415MimeNotMatchException;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.constant.Symbol;
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
            flag = this.wideMatch(value, expected);
            if (flag) {
                // 匹配
                break;
            }
        }
        /** 2.返回Envelop **/
        Envelop envelop = Envelop.success(new JsonObject());
        if (!flag) {
            envelop = Envelop.failure(new _415MimeNotMatchException(getClass(), toStr(expectes), value),
                    StatusCode.UNSUPPORTED_MEDIA_TYPE);
        }
        return envelop;
    }

    private boolean wideMatch(final String actual, final String expected) {
        /** 1.如果二者相等，直接匹配 **/
        if (actual.equals(expected)) {
            return true;
        }
        /** 2.抽取 **/
        final String[] actualArr = actual.split("/");
        final String[] expectArr = expected.split("/");
        if (Constants.TWO != actualArr.length && Constants.TWO != expectArr.length) {
            return false;
        }
        /** 2.1.如果expected父类子类都是* **/
        if (expectArr[Constants.IDX].equals(Symbol.STAR) && expectArr[Constants.ONE].equals(Symbol.STAR)) {
            return true;
        }
        /** 3.父类匹配，子类通配 **/
        // actual: application/json
        // expected: application/*
        if (actualArr[Constants.IDX].equals(expectArr[Constants.IDX])) {
            // 父类相等
            // 子类为* 或其他
            if (expectArr[Constants.ONE].equals(Symbol.STAR)) {
                return true;
            } else if (expectArr[Constants.ONE].equals(actualArr[Constants.IDX])) {
                return true;
            }
        }
        /** 4.子类匹配，父类通配 **/
        // actual: application/json
        // expected: */json
        if (actualArr[Constants.ONE].equals(expectArr[Constants.ONE])) {
            // 子类相等
            // 父类为* 或其他
            if (expectArr[Constants.IDX].equals(Symbol.STAR)) {
                return true;
            } else if (expectArr[Constants.IDX].equals(actualArr[Constants.IDX])) {
                return true;
            }
        }
        return false;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
