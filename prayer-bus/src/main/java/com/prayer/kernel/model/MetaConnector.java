package com.prayer.kernel.model;

import static com.prayer.util.Error.info;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.system.SchemaNotFoundException;
import com.prayer.util.PropertyKit;

import jodd.util.StringUtil;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.Pre;

/**
 * 从MetaRecord到Resource中资源文件定义的Metadata的连接过程
 * 
 * @author Lang
 *
 */
@Guarded
public class MetaConnector {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MetaConnector.class);
    /** 元数据资源文件 **/
    private static final PropertyKit LOADER = new PropertyKit(MetaConnector.class, Resources.META_CFG_FILE);
    /** 前置条件 **/
    private static final String PRE_ID_CON = "_this._identifier != null";
    /** 元数据Global ID集合 **/
    private static final Set<String> idSet = new HashSet<>();
    // ~ Instance Fields =====================================
    /** 全局标识符 **/
    private transient final String _identifier;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param identifier
     * @return
     */
    public static MetaConnector connect(@NotNull @NotBlank @NotEmpty final String identifier)
            throws AbstractSystemException {
        return new MetaConnector(identifier);
    }

    // ~ Constructors ========================================
    /** **/
    private MetaConnector(final String identifier) throws AbstractSystemException {
        // 1.检查Global ID是否配置
        info(LOGGER, "[I] Checking virtual schema from Property files : identifier = " + identifier);
        if (null == idSet || idSet.isEmpty()) {
            final String[] ids = StringUtil.split(LOADER.getString("h2.meta.ids"), ",");
            idSet.addAll(Arrays.asList(ids));
        }
        if (!idSet.contains(identifier)) {
            throw new SchemaNotFoundException(MetaConnector.class, identifier);
        }
        // 2.检查成功针对identifer中的数据进行赋值
        this._identifier = identifier;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 获取当前Global ID
     * 
     * @return
     */
    @NotNull
    @Pre(expr = PRE_ID_CON, lang = Constants.LANG_GROOVY)
    public String identifier() {
        return this._identifier;
    }

    /** **/
    @NotNull
    @MinSize(1)
    @Pre(expr = PRE_ID_CON, lang = Constants.LANG_GROOVY)
    public Set<String> columns() {
        return this.fromStr(LOADER.getString(this._identifier + ".column.names"), ",");
    }

    /**
     * 
     * @return
     */
    @NotNull
    @Pre(expr = PRE_ID_CON, lang = Constants.LANG_GROOVY)
    public String table() {
        return LOADER.getString(this._identifier + ".meta.table");
    }

    /**
     * 
     * @return
     */
    @NotNull
    @MinSize(1)
    @Pre(expr = PRE_ID_CON, lang = Constants.LANG_GROOVY)
    public Set<String> fields() {
        return this.fromStr(LOADER.getString(this._identifier + ".field.names"), ",");
    }
    // ~ Private Methods =====================================
    private Set<String> fromStr(final String content, final String delimiter) {
        final Set<String> dataSet = new HashSet<>();
        final String[] dataArr = StringUtil.split(content, delimiter);
        dataSet.addAll(Arrays.asList(dataArr));
        return dataSet;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
