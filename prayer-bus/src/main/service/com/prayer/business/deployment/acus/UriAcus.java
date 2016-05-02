package com.prayer.business.deployment.acus;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prayer.facade.business.instantor.deployment.acus.DeployAcus;
import com.prayer.facade.constant.Constants;
import com.prayer.fantasm.business.deployment.acus.AbstractEntityAcus;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.meta.vertx.PERule;
import com.prayer.model.meta.vertx.PEUri;
import com.prayer.util.io.IOKit;
import com.prayer.util.io.JacksonKit;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * Uri发布器
 * 
 * @author Lang
 *
 */
@Guarded
public class UriAcus extends AbstractEntityAcus implements DeployAcus {
    // ~ Static Fields =======================================

    /** 日志记录器 **/
    private static final Logger LOGGER = LoggerFactory.getLogger(UriAcus.class);

    // ~ Instance Fields =====================================
    /** **/
    private transient final DeployAcus scriptAcus = singleton(ScriptAcus.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public boolean deploy(@NotNull @NotBlank @NotEmpty final String folder) throws AbstractException {
        final TypeReference<PEUri> typeRef = new TypeReference<PEUri>() {
        };
        info(LOGGER, "[DP] 5.<Start>.Vertx's ( Uris ) deployment start...");
        final String rootFolder = folder + "/vertx/uri";
        /** URI Folder **/
        final List<String> folders = IOKit.listDirectories(rootFolder);
        for (final String methods : folders) {
            /** Method Folder **/
            final String uriFolder = rootFolder + "/" + methods;
            final List<String> files = IOKit.listDirectories(uriFolder);
            for (final String file : files) {
                final String targetFolder = uriFolder + "/" + file;
                final String target = targetFolder + "/data.json";
                final PEUri uri = JacksonKit.fromFile(typeRef, target);
                this.injectScript(uri);
                accessor(PEUri.class).insert(uri);
                info(LOGGER, "[DP] 5. - Vertx's ( Uris ) of " + target + " have been deployed successfully.");
                // 1.发布Rules
                this.deployRules(uri, targetFolder);
                // 2.发布对应的Scripts
                this.scriptAcus.deploy(targetFolder);
            }
        }
        info(LOGGER,
                "[DP] 5.<End>.( EVX_URI ) Vertx's ( Uris ) have been deployed successfully. Folder = " + rootFolder);
        return true;
    }

    /** **/
    @Override
    public boolean purge() throws AbstractException {
        info(LOGGER, "[PG] - 5.<Start>.Vertx's ( Uri -> Rule ) purging start...");
        accessor(PERule.class).purge();
        info(LOGGER, "[PG] - 5.<End>.Vertx's ( Uri -> Rule ) data have been purged successfully.");
        info(LOGGER, "[PG] 5.<Start>.Vertx's ( Uris ) purging start...");
        accessor(PEUri.class).purge();
        info(LOGGER, "[PG] 5.<End>.Vertx's ( Uris ) data have been purged successfully.");
        return true;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void deployRules(PEUri uri, final String folder) throws AbstractException {
        final TypeReference<List<PERule>> typeRef = new TypeReference<List<PERule>>() {
        };
        info(LOGGER, "[DP] - 5.Vertx's ( Uri -> Rule ) deployment start...");
        final String ruleFolder = folder + "/rules/";
        final List<String> files = IOKit.listFiles(ruleFolder);
        for (final String file : files) {
            final String ruleFile = ruleFolder + file;
            final List<PERule> rules = JacksonKit.fromFile(typeRef, ruleFile);
            this.injectRefId(rules, uri);
            if (!rules.isEmpty()) {
                accessor(PERule.class).insert(rules.toArray(new PERule[] {}));
            }
            info(LOGGER, "[DP] - 5. - Vertx's ( Uris -> Rule ) of " + ruleFile
                    + " have been deployed successfully. Size = " + rules.size());
        }
        info(LOGGER, "[DP] - 5.( EVX_RULE ) Vertx's ( Uri -> Rule ) have been deployed successfully. Folder = "
                + ruleFolder);
    }

    private void injectRefId(final List<PERule> rules, final PEUri uri) {
        for (final PERule rule : rules) {
            rule.setRefUID(uri.getUniqueId());
        }
    }

    private void injectScript(final PEUri uri) {
        if (null == uri.getScript()) {
            final StringBuilder script = new StringBuilder(Constants.BUFFER_SIZE); // NOPMD
            script.append("api.");
            final String exeJS = uri.getUri().replaceAll("/api/", "").replaceAll("/", "\\.");
            script.append(exeJS);
            script.append('.').append(uri.getMethod().toString().toLowerCase(Locale.getDefault()));
            uri.setScript(script.toString());
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
