package com.prayer.business.impl.ordered;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.system.RecurrenceReferenceException;
import com.prayer.fantasm.exception.AbstractSchemaException;
import com.prayer.util.io.IOKit;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;
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
    private transient List<OrderNode> rawList = new ArrayList<>();
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** 执行分析，得出最终的Model的定义顺序 **/
    public Map<Integer, String> execute(final String folder) throws RecurrenceReferenceException {
        /** 1.读取所有的文件 **/
        final List<String> files = IOKit.listFiles(folder);
        final List<OrderNode> nodes = new ArrayList<>();
        /** 2.初始化所有的基础节点 **/
        for(final String file: files){
            final OrderNode node = this.initNode(folder + '/' + file);
            if(null != node && node.isValid()){
                nodes.add(node);
                System.out.println(node);
            }
        }
        return null;
    }

    // ~ Private Methods =====================================
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
