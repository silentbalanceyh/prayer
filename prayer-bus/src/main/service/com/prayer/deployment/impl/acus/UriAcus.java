package com.prayer.deployment.impl.acus;

import static com.prayer.util.debug.Log.info;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.prayer.constant.Constants;
import com.prayer.database.accessor.impl.MetaAccessorImpl;
import com.prayer.facade.accessor.MetaAccessor;
import com.prayer.facade.deployment.acus.DeployAcus;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.meta.vertx.PERule;
import com.prayer.model.meta.vertx.PEUri;
import com.prayer.util.io.IOKit;
import com.prayer.util.io.JsonKit;

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
public class UriAcus implements DeployAcus {
    // ~ Static Fields =======================================

    /** 日志记录器 **/
    private static final Logger LOGGER = LoggerFactory.getLogger(UriAcus.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public boolean deploy(@NotNull @NotBlank @NotEmpty final String folder) throws AbstractException {
        final TypeReference<List<PEUri>> typeRef = new TypeReference<List<PEUri>>() {
        };
        info(LOGGER, "[DP] 6.<Start>.Vertx's ( Uris ) deployment start...");
        final String targetFolder = folder + "/vertx/uri/";
        final List<String> files = IOKit.listFiles(targetFolder);
        for (final String file : files) {
            final String target = targetFolder + file;
            final List<PEUri> uris = JsonKit.fromFile(typeRef, target);
            injectScript(uris);
            if (!uris.isEmpty()) {
                accessor(PEUri.class).insert(uris.toArray(new PEUri[] {}));
                this.deployRules(uris, folder);
            }
            info(LOGGER, "[DP] 6. - Vertx's ( Uris ) of " + target + " have been deployed successfully. Size = "
                    + uris.size());
        }
        info(LOGGER,
                "[DP] 6.<End>.( EVX_URI ) Vertx's ( Uris ) have been deployed successfully. Folder = " + targetFolder);
        return true;
    }

    /** **/
    @Override
    public boolean purge() throws AbstractException {
        info(LOGGER, "[PG] - 6.<Start>.Vertx's ( Uri -> Rule ) purging start...");
        accessor(PERule.class).purge();
        info(LOGGER, "[PG] - 6.<End>.Vertx's ( Uri -> Rule ) data have been purged successfully.");
        info(LOGGER, "[PG] 6.<Start>.Vertx's ( Uris ) purging start...");
        accessor(PEUri.class).purge();
        info(LOGGER, "[PG] 6.<End>.Vertx's ( Uris ) data have been purged successfully.");
        return true;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void deployRules(final List<PEUri> uris, final String folder) throws AbstractException {
        final TypeReference<List<PERule>> typeRef = new TypeReference<List<PERule>>() {
        };
        for (final PEUri uri : uris) {
            info(LOGGER, "[DP] - 6.<Start>.Vertx's ( Uri -> Rule ) deployment start...");
            final String ruleFolder = folder + "/vertx/rule/";
            final String paramFolder = ruleFolder + uri.getMethod().toString().toLowerCase(Locale.getDefault()) + "/"
                    + uri.getUri().substring(1, uri.getUri().length()).replaceAll("/", ".").replaceAll(":", "\\$");
            final List<String> files = IOKit.listFiles(paramFolder);
            for (final String file : files) {
                final String ruleFile = paramFolder + "/" + file;
                final List<PERule> rules = JsonKit.fromFile(typeRef, ruleFile);
                this.injectRefId(rules, uri);
                if (!rules.isEmpty()) {
                    accessor(PERule.class).insert(rules.toArray(new PERule[] {}));
                }
                info(LOGGER, "[DP] - 6. - Vertx's ( Uris -> Rule ) of " + ruleFile
                        + " have been deployed successfully. Size = " + rules.size());
            }
            info(LOGGER,
                    "[DP] - 6.<End>.( EVX_RULE ) Vertx's ( Uri -> Rule ) have been deployed successfully. Folder = "
                            + ruleFolder);
        }
    }

    private void injectRefId(final List<PERule> rules, final PEUri uri) {
        for (final PERule rule : rules) {
            rule.setRefUriId(uri.getUniqueId());
        }
    }

    private void injectScript(final List<PEUri> uris) {
        for (final PEUri uri : uris) {
            if (null == uri.getScript()) {
                final StringBuilder script = new StringBuilder(Constants.BUFFER_SIZE); // NOPMD
                script.append("js.api.").append(uri.getMethod().toString().toLowerCase(Locale.getDefault()))
                        .append('.');
                final String exeJS = uri.getUri().replaceAll("/api/", "").replaceAll("/", "\\.");
                script.append(exeJS);
                uri.setScript(script.toString());
            }
        }
    }

    /** 获取对应实体的Accessor **/
    private MetaAccessor accessor(final Class<?> genericT) {
        return new MetaAccessorImpl(genericT);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
