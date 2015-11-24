package com.prayer.kernel.model;

import static com.prayer.util.Error.debug;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.exception.AbstractDatabaseException;
import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.metadata.ColumnInvalidException;
import com.prayer.exception.metadata.FieldInvalidException;
import com.prayer.kernel.Record;
import com.prayer.kernel.Value;
import com.prayer.model.h2.FieldModel;
import com.prayer.model.type.DataType;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.Pre;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class MetaRecord implements Record {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MetaRecord.class);
    /** 前置条件 **/
    private static final String PRE_CONNECTOR_CON = "_this._connector != null && _this.data != null";

    // ~ Instance Fields =====================================
    /** 全局标识符 **/
    @NotNull
    private transient final String _identifier;
    /** Metadata 连接器 **/
    @NotNull
    private transient MetaConnector _connector;
    /** 当前Record中的数据 **/
    private transient final ConcurrentMap<String, Value<?>> data;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 
     * @param identifier
     */
    @PostValidateThis
    public MetaRecord(@NotNull @NotBlank @NotEmpty final String identifier) {
        // 1.连接操作
        try {
            this._connector = MetaConnector.connect(identifier);
        } catch (AbstractSystemException ex) {
            this._connector = null;
            debug(LOGGER, getClass(), "D20006", ex, identifier);
        }
        this._identifier = this._connector.identifier();
        this.data = new ConcurrentHashMap<>();
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    @NotNull
    @Pre(expr = PRE_CONNECTOR_CON, lang = Constants.LANG_GROOVY)
    public MetaPolicy policy() {
        return this._connector.policy();
    }

    @Override
    public ConcurrentMap<String, Value<?>> idKV() throws AbstractDatabaseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<FieldModel> idschema() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Value<?> column(String column) throws AbstractDatabaseException {
        // TODO Auto-generated method stub
        return null;
    }
    /** **/
    @Override
    @NotNull
    @Pre(expr = PRE_CONNECTOR_CON, lang = Constants.LANG_GROOVY)
    public String table() {
        return this._connector.table();
    }
    /** **/
    @Override
    @NotNull
    @Pre(expr = PRE_CONNECTOR_CON, lang = Constants.LANG_GROOVY)
    public Set<String> columns() {
        return this._connector.columns();
    }

    @Override
    public ConcurrentMap<String, DataType> columnTypes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void set(String name, Value<?> value) throws AbstractDatabaseException {
        // TODO Auto-generated method stub

    }

    @Override
    public void set(String name, String value) throws AbstractDatabaseException {
        // TODO Auto-generated method stub

    }

    @Override
    public Value<?> get(String name) throws AbstractDatabaseException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 
     */
    @Override
    @NotNull
    @Pre(expr = PRE_CONNECTOR_CON, lang = Constants.LANG_GROOVY)
    public String identifier() {
        return this._connector.identifier();
    }

    @Override
    public String toField(String column) throws AbstractDatabaseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toColumn(String field) throws AbstractDatabaseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ConcurrentMap<String, DataType> fields() {
        // TODO Auto-generated method stub
        return null;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private void verifyField(final String name) throws AbstractDatabaseException {
        if (!this._connector.fields().contains(name)) {
            throw new FieldInvalidException(getClass(), name, this._connector.identifier());
        }
    }
    
    private void verifyColumn(final String column) throws AbstractDatabaseException{
        if(!this._connector.columns().contains(column)){
            throw new ColumnInvalidException(getClass(), column, this.table());
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
