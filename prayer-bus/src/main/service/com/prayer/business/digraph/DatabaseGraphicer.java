package com.prayer.business.digraph;

import static com.prayer.util.reflection.Instance.reservoir;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.ArrayList;
import java.util.List;

import com.prayer.constant.Accessors;
import com.prayer.constant.Constants;
import com.prayer.constant.MemoryPool;
import com.prayer.constant.Resources;
import com.prayer.dao.impl.data.DatabaseDalor;
import com.prayer.facade.dao.DatabaseDao;
import com.prayer.facade.schema.verifier.DataValidator;
import com.prayer.facade.util.digraph.NodeData;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.util.digraph.Edges;
import com.prayer.util.digraph.Graphic;
import com.prayer.util.digraph.Node;

import io.vertx.core.json.JsonArray;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
final class DatabaseGraphicer {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 数据库验证器 **/
    private transient DataValidator validator;
    /** 数据库访问使用的Dao **/
    private transient DatabaseDao dao;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    public DatabaseGraphicer() {
        this.validator = reservoir(MemoryPool.POOL_VALIDATOR, Resources.DB_CATEGORY, Accessors.validator());
        this.dao = singleton(DatabaseDalor.class);
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     * @param tables
     * @return
     */
    public Graphic build(@NotNull final JsonArray tables) throws AbstractException {
        /** 1.获取数据Key **/
        final String[] data = this.buildNodes(tables);
        /** 2.读取关系信息 **/
        final Edges fromTo = this.dao.getRelations();
        /** 3.构建图 **/
        final Graphic graphic = this.buildGraphic(data, fromTo);
        return graphic;
    }
    // ~ Private Methods =====================================

    private Graphic buildGraphic(final String[] data, Edges fromTo) {
        final int length = data.length;
        final Node[] nodes = new Node[length];
        for (int idx = 0; idx < length; idx++) {
            final String key = data[idx];
            final NodeData dataItem = new OrderedData(key);
            dataItem.setData(key);
            final Node node = new Node(dataItem);
            nodes[idx] = node;
        }
        return new Graphic(nodes, fromTo);
    }

    private String[] buildNodes(final JsonArray tables) throws AbstractException {
        final List<String> data = new ArrayList<>();
        final int size = tables.size();
        for (int idx = 0; idx < size; idx++ ) {
            final String table = tables.getString(idx);
            if (null == this.validator.verifyTable(table)) {
                data.add(table);
            }
        }
        return data.toArray(Constants.T_STR_ARR);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
