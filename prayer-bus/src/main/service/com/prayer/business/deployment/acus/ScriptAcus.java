package com.prayer.business.deployment.acus;

import static com.prayer.util.debug.Log.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prayer.facade.business.instantor.deployment.acus.DeployAcus;
import com.prayer.facade.constant.Constants.EXTENSION;
import com.prayer.facade.constant.Symbol;
import com.prayer.fantasm.business.deployment.acus.AbstractEntityAcus;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.meta.vertx.PEScript;
import com.prayer.util.io.IOKit;
import com.prayer.util.io.JacksonKit;

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
public class ScriptAcus extends AbstractEntityAcus implements DeployAcus {
    // ~ Static Fields =======================================
    /** 日志记录器 **/
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptAcus.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public boolean deploy(@NotNull @NotBlank @NotEmpty final String folder) throws AbstractException {
        final TypeReference<PEScript> typeRef = new TypeReference<PEScript>() {
        };

        info(LOGGER, "[DP] 5.Engine's ( Scripts ) deployment start...");
        final String scriptData = folder + "/script.json";
        final PEScript script = JacksonKit.fromFile(typeRef, scriptData);
        info(LOGGER, "[DP] 5. - .Engine's ( Scripts ) of " + scriptData + " have been deployed successfully. ");
        /** 计算Script内容 **/
        this.injectContent(script, folder);
        accessor(PEScript.class).insert(script);
        info(LOGGER, "[DP] 5.( ENG_SCRIPT ) Engine's ( Script ) have been deployed successfully. Folder = " + scriptData);
        return true;
    }

    /** **/
    @Override
    public boolean purge() throws AbstractException {
        info(LOGGER, "[PG] 5.<Start>.Engine's ( Scripts ) purging start...");
        final boolean ret = accessor(PEScript.class).purge();
        info(LOGGER, "[PG] 5.<End>.Engine's ( Scripts ) data have been purged successfully.");
        return ret;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void injectContent(final PEScript script, final String folder) {
        if (null == script.getContent()) {
            final String path = folder + "/" + script.getName() + Symbol.DOT + EXTENSION.JS;
            info(LOGGER, "[DP] 5. - .Engine's ( Scripts ) of " + path + " content have been deployed successfully. ");
            script.setContent(IOKit.getContent(path));
        } else {
            script.setContent(IOKit.getContent(script.getContent()));
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
