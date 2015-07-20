package com.prayer.kernel.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.prayer.constant.SystemEnum.KeyCategory;
import com.prayer.model.meta.FieldModel;
import com.prayer.model.meta.KeyModel;
import com.prayer.util.StringKit;

import jodd.util.StringUtil;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class SchemaExpander {
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	/**
	 * 功能函数
	 * 
	 * @param keys
	 * @return
	 */
	public static ConcurrentMap<String, KeyModel> toKeysMap(@MinSize(1) final List<KeyModel> keys) {
		final ConcurrentMap<String, KeyModel> retMap = new ConcurrentHashMap<>();
		for (final KeyModel key : keys) {
			if (StringKit.isNonNil(key.getName())) {
				retMap.put(key.getName(), key);
			}
		}
		return retMap;
	}

	/**
	 * 功能函数
	 * 
	 * @param fields
	 * @return
	 */
	public static ConcurrentMap<String, FieldModel> toFieldsMap(@MinSize(1) final List<FieldModel> fields) {
		final ConcurrentMap<String, FieldModel> retMap = new ConcurrentHashMap<>();
		for (final FieldModel field : fields) {
			if (StringKit.isNonNil(field.getName())) {
				retMap.put(field.getName(), field);
			}
		}
		return retMap;
	}

	/**
	 * 
	 * @param fields
	 * @param colName
	 * @return
	 */
	public static FieldModel getColumn(@NotNull @MinSize(1) final ConcurrentMap<String, FieldModel> fields,
			@NotNull @NotBlank @NotEmpty final String colName) {
		FieldModel colField = null;
		for (final FieldModel field : fields.values()) {
			if (StringKit.isNonNil(field.getColumnName()) && StringUtil.equals(field.getColumnName(), colName)) {
				colField = field;
				break;
			}
		}
		return colField;
	}

	/**
	 * 注意返回值，获取TreeSet的列名集合
	 * 
	 * @param fields
	 * @return
	 */
	public static Set<String> getColumns(@NotNull @MinSize(1) final ConcurrentMap<String, FieldModel> fields) {
		final Set<String> columns = new TreeSet<>();
		for (final FieldModel field : fields.values()) {
			if (StringKit.isNonNil(field.getColumnName())) {
				columns.add(field.getColumnName());
			}
		}
		return columns;
	}

	/**
	 * 获取主键的Schema
	 * 
	 * @param fields
	 * @return
	 */
	public static List<FieldModel> getPrimaryKeys(@NotNull @MinSize(1) final ConcurrentMap<String, FieldModel> fields) {
		final List<FieldModel> retList = new ArrayList<>();
		for (final FieldModel field : fields.values()) {
			if (field.isPrimaryKey()) {
				retList.add(field);
			}
		}
		return retList;
	}

	/**
	 * 获取外键的原始定义
	 * 
	 * @param keys
	 * @return
	 */
	public static KeyModel getForeignKey(@NotNull @MinSize(1) final ConcurrentMap<String, KeyModel> keys) {
		KeyModel foreignKey = null;
		for (final KeyModel key : keys.values()) {
			if (KeyCategory.ForeignKey == key.getCategory()) {
				foreignKey = key;
				break;
			}
		}
		return foreignKey;
	}

	/**
	 * 获取外键的字段定义
	 * 
	 * @param fields
	 * @return
	 */
	public static FieldModel getForeignField(@NotNull @MinSize(1) final ConcurrentMap<String, FieldModel> fields) {
		FieldModel foreignField = null;
		for (final FieldModel field : fields.values()) {
			if (field.isForeignKey()) {
				foreignField = field;
				break;
			}
		}
		return foreignField;
	}

	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	private SchemaExpander() {
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
