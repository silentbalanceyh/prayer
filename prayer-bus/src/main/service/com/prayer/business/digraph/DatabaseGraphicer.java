package com.prayer.business.digraph;

import static com.prayer.util.reflection.Instance.reservoir;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.prayer.dao.data.DatabaseDalor;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.database.dao.DatabaseDao;
import com.prayer.facade.database.dao.schema.DataValidator;
import com.prayer.facade.util.digraph.NodeData;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.resource.Injections;
import com.prayer.resource.Resources;
import com.prayer.util.digraph.Edges;
import com.prayer.util.digraph.Graphic;
import com.prayer.util.digraph.Node;

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
    private transient DataValidator validator = reservoir(Resources.Data.CATEGORY, Injections.Data.VALIDATOR);
    /** 数据库访问使用的Dao **/
    private transient DatabaseDao dao = singleton(DatabaseDalor.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 
     * @param tables
     * @return
     */
    public Graphic build(@NotNull final Set<String> tables) throws AbstractException {
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

    private String[] buildNodes(final Set<String> tables) throws AbstractException {
        final List<String> data = new ArrayList<>();
        for (final String table : tables) {
            if (null == this.validator.verifyTable(table)) {
                data.add(table);
            }
        }
        return data.toArray(Constants.T_STR_ARR);
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
