package com.prayer.business.deployment.acus;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.digraph.OrderedBuilder;
import com.prayer.business.instantor.schema.SchemaBllor;
import com.prayer.facade.business.instantor.deployment.acus.DeployAcus;
import com.prayer.facade.business.instantor.schema.SchemaInstantor;
import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractException;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 2.执行Schema的Deploy用的发布器
 * 
 * @author Lang
 *
 */
@Guarded
public class SchemaAcus implements DeployAcus {
    // ~ Static Fields =======================================
    /** 日志记录器 **/
    private static final Logger LOGGER = LoggerFactory.getLogger(SchemaAcus.class);
    // ~ Instance Fields =====================================
    /** 主要用于Deploy **/
    @NotNull
    private transient final OrderedBuilder builder = singleton(OrderedBuilder.class); // NOPMD
    /** Schema Service **/
    @NotNull
    private transient final SchemaInstantor service = singleton(SchemaBllor.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public boolean deploy(@NotNull @NotEmpty @NotBlank final String folder) throws AbstractException {
        /** 0.文件目录准备 **/
        final String dftFile = folder + "/schema";
        /** 1.构建Schema文件的顺序 **/
        final ConcurrentMap<Integer, String> ordMap = this.builder.buildDeployOrder(dftFile);
        /** 2.创建顺序按照从大到小 **/
        final int size = ordMap.size();
        info(LOGGER, "[DP] 1.<Start>.Schema deployment start... ");
        for (int idx = size; idx > 0; idx--) {
            /** 3.根据文件名读取Schema **/
            final String file = ordMap.get(idx);
            /** 4.同步当前Schema **/
            this.importSchema(file);
            info(LOGGER, "[DP] 1." + idx + ".Schema deployment executed successfully, file = " + file);
        }
        info(LOGGER, "[DP] 1.<End>.Schema deployment executed successfully under : " + dftFile);
        return true;
    }

    /** **/
    @Override
    public boolean purge() throws AbstractException {
        info(LOGGER, "[PG] 1.<Start>.Schema purging start...");
        boolean purged = this.service.purge();
        info(LOGGER, "[PG] 1.<End>.Schema metadata data have been purged successfully.");
        return purged;
    }
    // ~ Methods =============================================

    // ~ Private Methods =====================================
    private void importSchema(final String file) throws AbstractException {
        /** 1.从文件导入Schema **/
        Schema schema = this.service.importSchema(file);
        if (null != schema) {
            /** 3.执行业务数据库同步 **/
            schema = this.service.syncMetadata(schema);
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
