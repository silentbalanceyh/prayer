package com.prayer.schema.json.internal;

import static com.prayer.util.Error.info;
import static com.prayer.util.Instance.instance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.prayer.exception.AbstractSchemaException;
import com.prayer.exception.AbstractSystemException;
import com.prayer.exception.system.SerializationException;
import com.prayer.exception.system.TypeInitException;
import com.prayer.schema.Ensurer;
import com.prayer.schema.Importer;
import com.prayer.util.JsonKit;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;
import net.sf.oval.guard.PostValidateThis;
import net.sf.oval.guard.PreValidateThis;

/**
 * Json中的导入器
 * 
 * @author Lang
 * @see
 */
@Guarded
public class CommunionImporter implements Importer {
	// ~ Static Fields =======================================
	/** **/
	private static final Logger LOGGER = LoggerFactory.getLogger(CommunionImporter.class);
	// ~ Instance Fields =====================================
	/** **/
	@NotNull
	private transient final String filePath;
	/** **/
	@NotNull
	private transient final Ensurer ensurer;
	/** **/
	private transient JsonNode rawData;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/**
	 * 
	 * @param filePath
	 */
	@PostValidateThis
	public CommunionImporter(@NotNull @NotEmpty @NotBlank final String filePath) {
		this.filePath = filePath;
		this.ensurer = instance(GenericEnsurer.class.getName());
		if (null == this.filePath) {
			info(LOGGER, "[E] File path initializing met error!",
					new TypeInitException(getClass(), "Constructor: GenericImporter(String)", this.filePath));
		}
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/**
	 * 初始化文件读取的过程
	 */
	@Override
	@PreValidateThis
	public void importFile() throws AbstractSystemException {
		final JsonNode schemaData = JsonKit.readJson(this.filePath);
		/**
		 * JsonKit.readJson本身会抛出AbstractSystemException
		 * <code>-20002: ResourceIOException</code>
		 * <code>-20003：JsonParserException</code>
		 * 如果异常会中断直接抛出异常，否则继续，如果读取的数据为null则也表示读取异常
		 */
		if (null != schemaData) {
			this.ensurer.refreshData(schemaData);
		}
	}

	/**
	 * 验证Schema文件信息
	 */
	@Override
	public void ensureSchema() throws AbstractSchemaException {
		if (this.ensurer.validate()) {
			this.rawData = this.ensurer.getRaw();
		} else {
			if (null != this.ensurer.getError()) {
				throw this.ensurer.getError();
			}
		}
	}

	/**
	 * 
	 */
	@Override
	public void transformModel() throws SerializationException {
		if (null != this.rawData) {

		}
	}

	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
