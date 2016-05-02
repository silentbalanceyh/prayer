package com.prayer.script.js;

import static com.prayer.util.reflection.Instance.singleton;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import com.prayer.business.configuration.impl.ConfigBllor;
import com.prayer.dao.ObjectTransferer;
import com.prayer.facade.business.instantor.configuration.ConfigInstantor;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.constant.Symbol;
import com.prayer.facade.model.record.Record;
import com.prayer.facade.script.Region;
import com.prayer.facade.script.Workshop;
import com.prayer.facade.util.Transferer;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.model.business.Eidolon;
import com.prayer.model.business.behavior.ActRequest;
import com.prayer.model.meta.vertx.PEScript;
import com.prayer.util.string.StringKit;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class JSRegion implements Region {
    // ~ Static Fields =======================================
    /** **/
    private static final String DATA = "data";
    /** **/
    private static final String ENV = "env";
    // ~ Instance Fields =====================================
    /** **/
    @NotNull
    private transient final Class<?> recordCls;
    /** **/
    @NotNull
    private transient final Workshop workshop;
    /** **/
    @NotNull
    private transient final Transferer transferer;
    /** **/
    @NotNull
    private transient final ConfigInstantor instantor;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    @PostValidateThis
    public JSRegion(@NotNull final Class<?> recordCls) {
        this.recordCls = recordCls;
        this.workshop = singleton(JSWorkshop.class);
        this.transferer = singleton(ObjectTransferer.class);
        this.instantor = singleton(ConfigBllor.class);
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    @Override
    public Eidolon execute(@NotNull final ActRequest request) throws ScriptException, AbstractException {
        /** 1.初始化环境变量 **/
        final Eidolon eidolon = this.initEnv(request);
        /** 2.读取Script的信息 **/
        final String script = this.getScript(request.getScript());
        /** 3.直接执行 **/
        this.workshop.getEngine().eval(script);
        /** 4.执行过后返回最终结果值 **/
        return eidolon;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private String getScript(final String name) throws AbstractException {
        String content = Constants.EMPTY_STR;
        if (StringKit.isNonNil(name)) {
            final PEScript script = this.instantor.script(name);
            content = script.getContent();
        }
        return content;
    }

    private Eidolon initEnv(final ActRequest request) throws ScriptException, AbstractException {
        /** 1.初始化Record **/
        final Record record = this.transferer.toRecord(request.getIdentifier(), this.recordCls, request.getData());
        final Eidolon env = new Eidolon(record);
        env.setOrder(request.getOrders());
        env.setPager(request.getPager());
        env.setProjection(request.getProjection());
        /** 2.获取Script Engine **/
        final ScriptEngine engine = this.workshop.getEngine();
        final ScriptContext context = engine.getContext();
        final Bindings bindings = context.getBindings(ScriptContext.ENGINE_SCOPE);
        /** 3.将变量信息注入到Bindings中 **/
        bindings.put(Symbol.DOLLER + DATA, engine.eval(Symbol.BRACKET_SL + request.getData().encode() + Symbol.BRACKET_SR));
        bindings.put(Symbol.DOLLER + ENV, env);
        return env;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
