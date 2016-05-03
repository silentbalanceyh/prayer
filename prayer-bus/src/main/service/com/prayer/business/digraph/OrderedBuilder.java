package com.prayer.business.digraph;

import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.reflection.Instance.singleton;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.system.RecurrenceReferenceException;
import com.prayer.facade.util.digraph.Algorithm;
import com.prayer.facade.util.digraph.StrongConnect;
import com.prayer.fantasm.exception.AbstractException;
import com.prayer.util.digraph.CycleNode;
import com.prayer.util.digraph.Graphic;
import com.prayer.util.digraph.Node;
import com.prayer.util.digraph.algorithm.DigraphAlgorithm;
import com.prayer.util.digraph.op.DigraphResult;
import com.prayer.util.digraph.scc.KosarajuSCC;

import io.vertx.core.json.DecodeException;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 构建Purge/Create用
 * 
 * @author Lang
 *
 */
@Guarded
public class OrderedBuilder {
    // ~ Static Fields =======================================

    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderedBuilder.class);
    // ~ Instance Fields =====================================
    /** 连通性查找SCC，检查表循环 **/
    private transient final StrongConnect kosaraju = singleton(KosarajuSCC.class);
    /** 图的基础算法 **/
    private transient final Algorithm algorithm = singleton(DigraphAlgorithm.class);

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 构建Purge顺序
     * 
     * @param tables
     * @return
     * @throws AbstractException
     */
    public ConcurrentMap<Integer, String> buildPurgeOrder(@NotNull final Set<String> tables) throws AbstractException {
        final DatabaseGraphicer executor = singleton(DatabaseGraphicer.class);
        /** JsonArray **/
        ConcurrentMap<Integer, String> ret = new ConcurrentHashMap<>();
        try {
            /** 1.构建图信息 **/
            final Graphic graphic = executor.build(tables);
            ret = this.buildIdOrd(graphic);
        } catch (DecodeException ex) {
            jvmError(LOGGER, ex);
            ret.clear();
        }
        return ret;
    }

    /**
     * 构建Deploy顺序
     * 
     * @param folder
     * @return
     * @throws AbstractException
     */
    public ConcurrentMap<Integer, String> buildDeployOrder(@NotNull @NotEmpty @NotBlank final String folder)
            throws AbstractException {
        final SchemaGraphicer executor = singleton(SchemaGraphicer.class);
        /** 1.使用Schema文件构建图 **/
        final Graphic graphic = executor.build(folder);
        /** 2.检查连通性 **/
        this.checkSCC(graphic);
        /** 3.打印图信息 **/
        return this.buildIdOrd(graphic);
    }

    /** **/
    private ConcurrentMap<Integer, String> buildIdOrd(final Graphic graphic) throws RecurrenceReferenceException {
        final ConcurrentMap<String, Node> nodes = graphic.getVertex();
        /** 1.按照原图的入度进行排序，序号最大的最先创建 **/
        final DigraphResult ret = this.algorithm.topSort(graphic);
        /** 2.构建返回内容 **/
        final ConcurrentMap<Integer, String> retMap = new ConcurrentHashMap<>();
        final ConcurrentMap<Integer, String> orderMap = ret.getOrder();
        /** 3.遍历最终顺序 **/
        for (final Integer order : orderMap.keySet()) {
            final String table = orderMap.get(order);
            final Node node = nodes.get(table);
            final String file = node.getData().getData();
            retMap.put(order, file);
        }
        return retMap;
    }

    /** **/
    protected void checkSCC(final Graphic graphic) throws RecurrenceReferenceException {
        final List<CycleNode> sccNodes = kosaraju.findSCC(graphic);
        if (!sccNodes.isEmpty()) {
            final StringBuilder patterns = new StringBuilder();
            for (final CycleNode node : sccNodes) {
                patterns.append("\n").append(node);
            }
            throw new RecurrenceReferenceException(getClass(), patterns.toString());
        }
    }

}
