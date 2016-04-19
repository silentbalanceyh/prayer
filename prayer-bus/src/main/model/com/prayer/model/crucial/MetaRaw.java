package com.prayer.model.crucial;

import static com.prayer.util.debug.Log.peError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.SystemEnum.MetaPolicy;
import com.prayer.exception.database.MetaCounterException;
import com.prayer.exception.database.MetaTypeWrongException;
import com.prayer.exception.database.MetadataDefMissingException;
import com.prayer.facade.constant.Constants;
import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.fantasm.exception.AbstractDatabaseException;
import com.prayer.model.type.DataType;
import com.prayer.resource.InceptBus;
import com.prayer.util.string.StringKit;

import net.sf.oval.constraint.AssertFieldConstraints;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public final class MetaRaw {
	// ~ Static Fields =======================================
	/** 元数据资源文件 **/
	private static final Inceptor LOADER = InceptBus.build(Point.Schema.class);
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(MetaRaw.class);
	// ~ Instance Fields =====================================
	/** **/
	@NotNull
	@NotBlank
	@NotEmpty
	private transient final String identifier;
	/** **/
	private transient final List<String> names = new ArrayList<>();
	/** **/
	private transient final List<DataType> types = new ArrayList<>();
	/** **/
	private transient final List<String> columnTypes = new ArrayList<>();
	/** **/
	private transient final List<String> columns = new ArrayList<>();
	/** **/
	private transient final List<String> ids = new ArrayList<>();

	/** **/
	private transient String table;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param identifier
	 */
	@PostValidateThis
	public MetaRaw(@AssertFieldConstraints("identifier") final String identifier) {
		this.identifier = identifier;
		if (this.names.isEmpty()) {
			try {
				this.names.addAll(readArray(this.identifier + ".field.names"));
			} catch (AbstractDatabaseException ex) {
				peError(LOGGER, ex);
			}
		}
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================

	// ~ Items Reading =======================================

	public List<String> readIds() throws AbstractDatabaseException {
		// if (this.ids.isEmpty()) {
		// this.ids.addAll(Arrays.asList(LOADER.getArray(this.identifier +
		// ".ids")));
		// if (this.ids.isEmpty()) {
		// throw new SchemaNotFoundException(getClass(), this.identifier);
		// }
		// }
		// TODO：暂时固定uniqueId
		this.ids.add(Constants.PID);
		return this.ids;
	}

	/** **/
	public List<String> readNames() throws AbstractDatabaseException {
		if (this.names.isEmpty()) {
			this.names.addAll(readArray(this.identifier + ".field.names"));
		}
		return this.names;
	}

	/** **/
	public List<String> readColumnTypes() throws AbstractDatabaseException {
		if (this.columnTypes.isEmpty()) {
			final List<String> columnTypes = readArray(this.identifier + ".column.types");
			// 保证尺寸
			ensureSize(columnTypes, "Column Type");
			this.columnTypes.addAll(columnTypes);
		}
		return this.columnTypes;
	}

	/** **/
	public List<String> readColumns() throws AbstractDatabaseException {
		if (this.columns.isEmpty()) {
			final List<String> columns = readArray(this.identifier + ".column.names");
			// 保证尺寸
			ensureSize(columns, "Column");
			this.columns.addAll(columns);
		}
		return this.columns;
	}

	/** **/
	public List<DataType> readTypes() throws AbstractDatabaseException {
		if (this.types.isEmpty()) {
			final String key = this.identifier + ".field.types";
			final List<String> literals = readArray(key);
			// 保证尺寸
			ensureSize(literals, "Field Type");
			for (final String literal : literals) {
				if (StringKit.isNonNil(literal)) {
					final DataType type = DataType.fromString(literal);
					if (null == type) {
						throw new MetaTypeWrongException(getClass(), literal, LOADER.getFile(), key);
					}
					this.types.add(type);
				}
			}
		}
		return this.types;
	}

	// ~ Meta Reading ========================================
	/** **/
	public String readTable() throws AbstractDatabaseException {
		if (null == this.table) {
			final String propKey = this.identifier + ".meta.table";
			this.table = LOADER.getString(propKey);
			if (StringKit.isNil(this.table)) {
				throw new MetadataDefMissingException(getClass(), LOADER.getFile(), propKey);
			}
		}
		return this.table;
	}

	/**
	 * 读取Meta的Policy
	 * 
	 * @return
	 */
	public MetaPolicy readPolicy() throws AbstractDatabaseException {
		/*
		 * if (null == this.policy) { final String propKey = this.identifier +
		 * ".meta.policy"; final String policy = LOADER.getString(propKey); if
		 * (StringKit.isNil(policy)) { throw new
		 * MetadataDefMissingException(getClass(), Resources.OOB_SCHEMA_FILE,
		 * propKey); } this.policy = Converter.fromStr(MetaPolicy.class,
		 * policy); }
		 */
		// TODO: 目前仅仅支持GUID
		return MetaPolicy.GUID;
	}

	// ~ Private Methods =====================================
	/**
	 * 
	 * @param key
	 * @return
	 */
	private List<String> readArray(final String key) throws AbstractDatabaseException {
		final List<String> ret = Arrays.asList(LOADER.getArray(key));
		if (ret.isEmpty()) {
			throw new MetadataDefMissingException(getClass(), LOADER.getFile(), key);
		}
		return ret;
	}

	private void ensureSize(final List<String> checkedList, final String flag) throws AbstractDatabaseException {
		final int size = this.names.size();
		if (size != checkedList.size()) {
			throw new MetaCounterException(getClass(), size, flag, checkedList.size());
		}
	}
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
