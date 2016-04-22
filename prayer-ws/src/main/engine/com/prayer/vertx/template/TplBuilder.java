package com.prayer.vertx.template;

import static com.prayer.util.Converter.fromStr;

import com.prayer.constant.SystemEnum.TemplateEngine;

import io.vertx.ext.web.handler.TemplateHandler;
import io.vertx.ext.web.templ.JadeTemplateEngine;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class TplBuilder {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param tplMode
     * @return
     */
    public static TemplateHandler build(@NotNull @NotBlank @NotEmpty final String tplMode) {
        final TemplateEngine template = fromStr(TemplateEngine.class, tplMode);
        TemplateHandler handler = null;
        switch (template) {
        case JADE: {
            handler = buildJADE();
        }
        default: {
            handler = null;
        }
            break;
        }
        return handler;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private static TemplateHandler buildJADE() {
        final TemplateHandler handler = TemplateHandler.create(JadeTemplateEngine.create());
        // TODO: Vertx-Handler : Jade扩展
        return handler;
    }

    private TplBuilder() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
