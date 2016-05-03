package com.prayer.business.instantor.schema;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.peError;
import static com.prayer.util.reflection.Instance.reservoir;
import static com.prayer.util.reflection.Instance.singleton;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.dao.schema.SchemaDalor;
import com.prayer.database.accessor.MetaAccessorImpl;
import com.prayer.facade.business.instantor.schema.EnvInstantor;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.database.accessor.MetaAccessor;
import com.prayer.facade.database.dao.schema.SchemaDao;
import com.prayer.facade.model.cache.Cache;
import com.prayer.facade.model.entity.Attributes;
import com.prayer.facade.model.entity.Entity;
import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.cache.CacheBuilder;
import com.prayer.model.crucial.MetaRaw;
import com.prayer.model.meta.database.PEMeta;

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
public class MilieuBllor implements EnvInstantor {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MilieuBllor.class);
    /** Data Schema 缓存 **/
    private static final Cache DATA_CACHE = CacheBuilder.build(Schema.class);
    /** Schema环境的初始化 **/
    private static final String ENV_START = "( {0} ) Schema Environment start initializing... ";
    /** Schema环境的初始化过程 **/
    private static final String ENV_SUCCESS = "( {0} ) Schema ( identifier = {1} ) has been initialized successfully. {2}";
    /** Schema环境的初始化过程 **/
    private static final String ENV_FAILURE = "( {0} ) ***ERROR*** Schema ( identifier = {1} ) initializing met errors.";
    /** Schema数据的统计 **/
    private static final String ENV_SIZE = "( {0} ) Schema ( type = {1}, number = {2} ) has been found.";
    /** Schema环境初始化完成 **/
    private static final String ENV_END = "( {0} ) Schema Environment has been finished initializing.";
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient SchemaDao dao = singleton(SchemaDalor.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public boolean buildMilieu() throws AbstractException {
        info(LOGGER, MessageFormat.format(ENV_START, getClass().getSimpleName()));
        /** 初始化Meta Schema **/
        this.buildMetaEnv();
        /** 初始化Data Schema **/
        this.buildDataEnv();
        info(LOGGER, MessageFormat.format(ENV_END, getClass().getSimpleName()));
        return true;
    }

    /** **/
    @Override
    public Schema get(@NotNull @NotBlank @NotEmpty final String identifier) throws AbstractDatabaseException {
        Schema schema = DATA_CACHE.get(identifier);
        if (null == schema) {
            schema = this.dao.get(identifier);
            if (null != schema) {
                DATA_CACHE.put(identifier, schema);
            }
        }
        return schema;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private String[] buildDataIds() {
        final List<Entity> list = accessor(PEMeta.class).getAll();
        final List<String> identifiers = new ArrayList<>();
        for (final Entity entity : list) {
            if (null != entity) {
                final String identifier = entity.toJson().getString(Attributes.IDENTIFIER);
                identifiers.add(identifier);
            }
        }
        return identifiers.toArray(Constants.T_STR_ARR);
    }

    private String[] buildMetaIds() {
        // TODO: 暂时先固定
        return new String[] { "meta-script", "meta-address", "meta-route", "meta-rule", "meta-uri", "meta-verticle",
                "meta-field", "meta-index", "meta-key", "meta-meta", "meta-trigger", "meta-vcolumn", "meta-view" };
    }

    /** 构建Data Schema的缓存环境 **/
    private boolean buildDataEnv() {
        boolean ret = true;
        final String[] identifiers = this.buildDataIds();
        try {
            info(LOGGER, MessageFormat.format(ENV_SIZE, getClass().getSimpleName(), "Data", identifiers.length));
            for (final String identifier : identifiers) {
                final Schema schema = this.get(identifier);
                if (null == schema) {
                    info(LOGGER, MessageFormat.format(ENV_FAILURE, getClass().getSimpleName(), identifier));
                } else {
                    info(LOGGER, MessageFormat.format(ENV_SUCCESS, getClass().getSimpleName(), identifier,
                            String.valueOf(schema.hashCode())));
                }
            }
            ret = true;
        } catch (AbstractDatabaseException ex) {
            peError(LOGGER, ex);
            ret = false;
        }
        return ret;
    }

    /** 构建Meta Schema的缓存环境 **/
    private boolean buildMetaEnv() {
        final String[] identifiers = this.buildMetaIds();
        /** 1.因为Meta Schema本身从reservior环境中读取，所以仅仅需要各自初始化一次 **/
        info(LOGGER, MessageFormat.format(ENV_SIZE, getClass().getSimpleName(), "Meta", identifiers.length));
        for (final String identifier : identifiers) {
            final MetaRaw raw = reservoir(identifier, MetaRaw.class, identifier);
            if (null == raw) {
                info(LOGGER, MessageFormat.format(ENV_FAILURE, getClass().getSimpleName(), identifier));
            } else {
                info(LOGGER, MessageFormat.format(ENV_SUCCESS, getClass().getSimpleName(), identifier,
                        String.valueOf(raw.hashCode())));
            }
        }
        return true;
    }

    private MetaAccessor accessor(final Class<?> genericT) {
        return new MetaAccessorImpl(genericT);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
