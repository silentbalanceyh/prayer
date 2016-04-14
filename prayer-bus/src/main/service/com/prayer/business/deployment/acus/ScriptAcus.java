package com.prayer.business.deployment.acus;

import static com.prayer.util.debug.Log.info;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prayer.facade.business.deployment.acus.DeployAcus;
import com.prayer.facade.constant.Constants.EXTENSION;
import com.prayer.facade.constant.Symbol;
import com.prayer.fantasm.business.deployment.acus.AbstractEntityAcus;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.meta.vertx.PEScript;
import com.prayer.util.io.IOKit;
import com.prayer.util.io.JsonKit;

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
        /** 4.发布Script **/
        this.deployScript(folder);
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

    private void deployScript(final String folder) throws AbstractException {
        final TypeReference<List<PEScript>> typeRef = new TypeReference<List<PEScript>>() {
        };

        info(LOGGER, "[DP] 5.<Start>.Engine's ( Scripts ) deployment start...");
        final String targetFolder = folder + "/vertx/script/";
        final List<String> files = IOKit.listFiles(targetFolder);
        for (final String file : files) {
            final String target = targetFolder + file;
            final List<PEScript> scripts = JsonKit.fromFile(typeRef, target);
            /** 计算Script内容 **/
            injectContent(scripts, folder);
            if (!scripts.isEmpty()) {
                accessor(PEScript.class).insert(scripts.toArray(new PEScript[] {}));
            }
            info(LOGGER, "[DP] 5. - .Engine's ( Scripts ) of " + target + " have been deployed successfully. Size = "
                    + scripts.size());
        }
        info(LOGGER, "[DP] 5.<End>.( ENG_SCRIPT ) Engine's ( Scripts ) have been deployed successfully. Folder = "
                + targetFolder);
    }

    private void injectContent(final List<PEScript> scripts, final String folder) {
        for (final PEScript script : scripts) {
            if (null == script.getContent()) {
                final String path = folder + "/" + script.getName().replaceAll("\\.", "/") + Symbol.DOT + EXTENSION.JS;
                script.setContent(IOKit.getContent(path));
            } else {
                script.setContent(IOKit.getContent(script.getContent()));
            }
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
