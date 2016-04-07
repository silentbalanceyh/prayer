package com.prayer.business.impl.deployment;

import static com.prayer.util.reflection.Instance.singleton;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.business.impl.ordered.OrderedGraphicer;
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

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 负责Deploy的类
 * 
 * @author Lang
 *
 */
@Guarded
final class SchemaDeployer {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** Order的图结构 **/
    private transient final OrderedGraphicer executor = singleton(OrderedGraphicer.class);
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
     * 构造OrdMap
     * 
     * @param folder
     *            Schema文件的目录
     * @return
     * @throws RecurrenceReferenceException
     */
    public ConcurrentMap<Integer, String> buildOrdMap(@NotNull @NotEmpty @NotBlank final String folder)
            throws AbstractException {
        /** 1.使用Schema文件构建图 **/
        final Graphic graphic = executor.build(folder);
        /** 2.检查连通性 **/
        this.checkSCC(graphic);
        return this.buildIdOrd(graphic);
    }
    // ~ Private Methods =====================================

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

    private void checkSCC(final Graphic graphic) throws RecurrenceReferenceException {
        final List<CycleNode> sccNodes = kosaraju.findSCC(graphic);
        if (!sccNodes.isEmpty()) {
            final StringBuilder patterns = new StringBuilder();
            for (final CycleNode node : sccNodes) {
                patterns.append("\n").append(node);
            }
            throw new RecurrenceReferenceException(getClass(), patterns.toString());
        }
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
