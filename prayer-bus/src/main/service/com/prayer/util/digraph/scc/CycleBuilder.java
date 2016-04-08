package com.prayer.util.digraph.scc;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ConcurrentMap;

import com.prayer.facade.constant.Constants;
import com.prayer.util.digraph.CycleNode;
import com.prayer.util.digraph.op.DigraphResult;

/**
 * 
 * @author Lang
 *
 */
public final class CycleBuilder {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 根据有向图的遍历结果生成最终的强连通分量
     * 
     * @param result
     * @return
     */
    public static List<CycleNode> buildKosaraju(final DigraphResult result) {
        /** 1.构造一个堆栈进行配对操作 **/
        final Stack<String> stack = new Stack<>();
        final ConcurrentMap<Integer, String> path = result.getPath();
        /** 2.最终返回结果 **/
        final List<CycleNode> nodes = new ArrayList<>();
        /** 3.一个新的栈用于构建链表 **/
        final Stack<String> tree = new Stack<>();
        /** 4.遍历路径 **/
        for (final Integer key : path.keySet()) {
            final String value = path.get(key);
            if (stack.isEmpty()) {
                /** 5.1.空栈，直接压入 **/
                stack.push(value);
                tree.push(value);
            } else {
                /** 5.2.取栈顶元素 **/
                final String top = stack.peek();
                if (value.equals(top)) {
                    stack.pop();
                    /** 6.开始pop过后不为空即可以处理tree了 **/
                    if (stack.isEmpty()) {
                        continue;
                    } else {
                        /** Tree建立的最低条件是尺寸大于1，这个条件一般不会被破坏，因为上层会有相关内容 **/
                        if (!tree.isEmpty() && Constants.ONE < tree.size()) {
                            final CycleNode node = buildNode(tree);
                            nodes.add(node);
                        }
                    }
                } else {
                    /** 7.不相等，则压入 **/
                    stack.push(value);
                    tree.push(value);
                }
            }
        }
        return nodes;
    }

    private static CycleNode buildNode(final Stack<String> tree) {
        CycleNode root = null;
        CycleNode ret = null;
        while (!tree.isEmpty()) {
            final String key = tree.pop();
            if (null == ret) {
                ret = new CycleNode(key);
                root = ret;
            } else {
                final CycleNode next = new CycleNode(key);
                ret.addNext(next);
                ret = ret.getNext();
            }
        }
        return root;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private CycleBuilder() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
