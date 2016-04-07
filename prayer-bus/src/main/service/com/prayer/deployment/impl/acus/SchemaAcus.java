package com.prayer.deployment.impl.acus;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.business.digraph.OrderedBuilder;
import com.prayer.deployment.impl.SchemaBllor;
import com.prayer.facade.deployment.SchemaService;
import com.prayer.facade.deployment.acus.DeployAcus;
import com.prayer.facade.fun.deploy.AcusPoster;
import com.prayer.facade.schema.Schema;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.business.ServiceResult;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

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
    private transient final OrderedBuilder builder; // NOPMD
    /** Schema Service **/
    @NotNull
    private transient final SchemaService service;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public SchemaAcus() {
        this.builder = singleton(OrderedBuilder.class);
        this.service = singleton(SchemaBllor.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    @Override
    public boolean deploy(@NotNull @NotEmpty @NotBlank final String folder, final AcusPoster fun) throws AbstractException {
        /** 0.文件目录准备 **/
        final String dftFile = folder + "/schema";
        /** 1.构建Schema文件的顺序 **/
        final ConcurrentMap<Integer, String> ordMap = this.builder.buildDeployOrder(dftFile);
        /** 2.创建顺序按照从大到小 **/
        final int size = ordMap.size();
        for (int idx = size; idx > 0; idx--) {
            /** 3.根据文件名读取Schema **/
            final String file = ordMap.get(idx);
            /** 4.同步当前Schema **/
            this.importSchema(file);
        }
        info(LOGGER, "[I] Schema deployment executed successfully under : " + dftFile);
        return true;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private void importSchema(final String file) throws AbstractException {
        /** 1.从文件导入Schema **/
        ServiceResult<Schema> schema = this.service.importSchema(file);
        if (schema.success()) {
            /** 3.执行业务数据库同步 **/
            schema = this.service.syncMetadata(schema.getResult());
        }
        /** 4.执行完成过后有Error **/
        if (!schema.success()) {
            throw schema.getServiceError();
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
