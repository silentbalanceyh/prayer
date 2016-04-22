package com.prayer.metaserver.h2.config;

import static com.prayer.util.debug.Log.info;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.engine.opts.Options;
import com.prayer.facade.metaserver.MetaOptsIntaker;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.fantasm.exception.AbstractLauncherException;
import com.prayer.metaserver.h2.opts.ClusterOptions;
import com.prayer.metaserver.h2.opts.ExtOptions;
import com.prayer.metaserver.h2.opts.JdbcOptions;
import com.prayer.metaserver.h2.opts.SingleOptions;
import com.prayer.model.web.options.JsonOptions;
import com.prayer.resource.InceptBus;

import io.vertx.core.json.JsonObject;
import net.sf.oval.guard.Guarded;

/**
 * H2 Database配置加载器
 * 
 * @author Lang
 *
 */
@Guarded
public class H2OptionsIntaker implements MetaOptsIntaker {
    // ~ Static Fields =======================================

    /** Inceptor **/
    private static final Inceptor INCEPTOR = InceptBus.build(Point.MetaServer.class);
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(H2OptionsIntaker.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public Options ingest() throws AbstractLauncherException {
        /** 1.读取Meta Server配置文件路径 **/
        final String configFile = INCEPTOR.getString(Point.MetaServer.CONFIG);
        /** 1.使用file生成DynamicInceptor **/
        final Inceptor inceptor = InceptBus.build(Point.MetaServer.class, configFile);
        /** 2.获取当前所有的Options **/
        final List<Options> optList = H2OptsHoder.buildOptsList(inceptor);
        for (final Options opt : optList) {
            if (null != opt && null != opt.getError()) {
                throw opt.getError();
            }
        }
        /** 3.构建Meta Options **/
        return this.buildOptions(optList);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private Options buildOptions(final List<Options> optList) {
        final JsonObject data = new JsonObject();
        for (final Options opt : optList) {
            if (opt instanceof JdbcOptions) {
                data.put("server", opt.readOpts());
            }
            if (opt instanceof ExtOptions) {
                data.put("extension", opt.readOpts());
            }
            if (opt instanceof SingleOptions) {
                data.put("nodes", opt.readOpts());
            }
            if (opt instanceof ClusterOptions) {
                data.mergeIn(opt.readOpts());
            }
        }
        info(LOGGER, "( Options ) : " + data.encode());
        return new JsonOptions(data);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
