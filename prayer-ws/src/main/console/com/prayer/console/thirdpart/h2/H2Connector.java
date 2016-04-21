package com.prayer.console.thirdpart.h2;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.prayer.console.util.OutGoing;
import com.prayer.facade.console.Connector;
import com.prayer.facade.console.message.H2Tidings;
import com.prayer.facade.constant.DBConstants;
import com.prayer.facade.constant.Symbol;
import com.prayer.facade.engine.Options;
import com.prayer.facade.metaserver.h2.H2Messages;
import com.prayer.facade.metaserver.h2.H2Quoter;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.metaserver.h2.util.UriResolver;
import com.prayer.metaserver.model.MetaOptions;
import com.prayer.resource.InceptBus;
import com.prayer.resource.Resources;
import com.prayer.rmi.RemoteInvoker;

import io.vertx.core.json.JsonObject;

/**
 * 
 * @author Lang
 *
 */
class H2Connector implements Connector, H2Tidings {
    // ~ Static Fields =======================================
    /** **/
    private static final Inceptor RMI_INCEPTOR = InceptBus.build(Point.RMI.class);
    // ~ Instance Fields =====================================
    /** **/
    private transient JsonObject options = new JsonObject();

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** **/
    @Override
    public boolean connecting() {
        /** 1.Meta Server类型是否匹配 **/
        if (!Resources.Meta.CATEGORY.equals(DBConstants.CATEGORY_H2)) {
            OutGoing.outLn(Error.CATEGORY, Resources.Meta.CATEGORY);
            return false;
        }
        /** 2.读取远程对象 **/
        if (!this.readRemote()) {
            return false;
        }
        /** 3.读取JDBC **/
        this.readJdbc();
        OutGoing.outLn(SUCCESS);
        return true;
    }

    /** **/
    @Override
    public JsonObject read() {
        return this.options;
    }

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private void readJdbc() {
        final Options opts = new MetaOptions(this.options);
        final String uri = UriResolver.resolveJdbc(opts);
        this.options.getJsonObject("server").put(Data.URL, uri);
        OutGoing.outLn(JDBC, uri);
    }

    private boolean readRemote() {
        boolean remote = false;
        try {
            final String pattern = RMI_INCEPTOR.getString(Point.RMI.META_SERVER);
            final H2Quoter quoter = (H2Quoter) RemoteInvoker.lookupDirect(pattern, H2Messages.RMI.OPTS_H2);
            if (null != quoter) {
                final JsonObject refRvt = new JsonObject(quoter.service(null));
                if (null != refRvt) {
                    this.options.mergeIn(refRvt.copy());
                    OutGoing.outLn(OPTS, refRvt.encode());
                }
                remote = true;
            }
        } catch (RemoteException | NotBoundException ex) {
            OutGoing.outLn(Error.RMI, ex.getClass(), ex.getMessage().replaceAll(Symbol.NEW_LINE, ""));
            remote = false;
        }
        return remote;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
