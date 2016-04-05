package com.prayer.business.impl.ordered;

import java.util.ArrayList;
import java.util.List;

import com.prayer.exception.system.RecurrenceReferenceException;

import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public class DependCalculator {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	/** **/
	@NotNull
	@MinSize(1)
	private transient List<OrderNode> list;
	/** 用于检查循环引用的容器 **/
	@NotNull
	private transient List<String> innerList;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	@PostValidateThis
	public DependCalculator(@NotNull @MinSize(1) List<OrderNode> list) {
		this.list = list;
		this.innerList = new ArrayList<>();
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	/**
	 * 查找当前节点中所有的依赖关系
	 * 
	 * @param node
	 * @param list
	 * @return
	 */
	public List<OrderNode> findReferences(final OrderNode node) throws RecurrenceReferenceException {	
		/** 1.为空则没有任何依赖关系信息 **/
		this.innerList.add(node.getTable());
		final List<OrderNode> retList = new ArrayList<>();
		if (!node.isEmpty()) {
			/** 2.遍历所有的节点 **/
			for (final String key : node.refTables()) {
				final OrderNode item = findByTable(list, key);
				/** 4.先添加当前节点，当前节点铁定不会重复 **/
				retList.add(item);
				this.interrupt(item);
				/** 5.递归查找当前节点中的References **/
				retList.addAll(this.findReferences(item));
			}
		}
		return retList;
	}

	/**
	 * 检查循环引用
	 * 
	 * @param item
	 * @throws RecurrenceReferenceException
	 */
	private void interrupt(final OrderNode item) throws RecurrenceReferenceException {
		/** 3.循环引用中断 **/
		if (this.innerList.contains(item.getTable())) {
			final StringBuilder pattern = new StringBuilder();
			for (final String table : this.innerList) {
				pattern.append(table);
				pattern.append("->");
			}
			if (pattern.toString().endsWith("->")) {
				pattern.delete(pattern.length() - 2, pattern.length());
			}
			System.out.println(this.innerList);
			System.out.println(item);
			System.out.println(pattern);
			throw new RecurrenceReferenceException(getClass(), pattern.toString());
		}
	}

	/**
	 * 根据表名从List中查找当前节点引用
	 * 
	 * @param list
	 * @param table
	 * @return
	 */
	private OrderNode findByTable(final List<OrderNode> list, final String table) {
		OrderNode node = null;
		for (final OrderNode item : list) {
			if (null != item && item.getTable().equals(table)) {
				node = item;
				break;
			}
		}
		return node;
	}
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
