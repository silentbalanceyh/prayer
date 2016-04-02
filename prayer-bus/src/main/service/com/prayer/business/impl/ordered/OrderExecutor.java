package com.prayer.business.impl.ordered;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.system.RecurrenceReferenceException;
import com.prayer.fantasm.exception.AbstractSchemaException;
import com.prayer.util.io.IOKit;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 分析Schema文件，获取Schema的创建顺序
 * 
 * @author Lang
 *
 */
@Guarded
public class OrderExecutor {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderExecutor.class);

    // ~ Instance Fields =====================================
    /** 获取原始的列表 **/
    private transient ConcurrentMap<String, OrderNode> rawMap = new ConcurrentHashMap<>();

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** 执行分析，得出最终的Model的定义顺序 **/
    public Map<Integer, String> execute(@NotNull @NotEmpty @NotBlank final String folder)
            throws RecurrenceReferenceException {
        /** 1.读取所有的文件，初始化基础节点 **/
        final List<OrderNode> nodeList = this.buildNodeList(folder);
        /** 2.初始化所有的基础节点 **/

        return null;
    }

    // ~ Private Methods =====================================
    
    private void buildRefs(final List<OrderNode> nodeList){
        
    }

    private List<OrderNode> buildNodeList(final String folder) {
        final List<String> files = IOKit.listFiles(folder);
        final List<OrderNode> nodes = new ArrayList<>();
        for (final String file : files) {
            final OrderNode node = this.initNode(folder + '/' + file);
            if (null != node && node.isValid()) {
                nodes.add(node);
                rawMap.put(node.getTable(), node);
                System.out.println(node);
            }
        }
        return nodes;
    }

    /**
     * 只执行创建验证，不执行更新验证，并且过滤需要验证数据库表存在的情况
     * 
     * @param file
     * @return
     * @throws AbstractSchemaException
     */
    private OrderNode initNode(final String file) {
        OrderNode node = null;
        try {
            final JsonObject data = new JsonObject(IOKit.getContent(file));
            node = new OrderNode(data);
        } catch (DecodeException ex) {
            node = null;
        }
        return node;
    }

    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
