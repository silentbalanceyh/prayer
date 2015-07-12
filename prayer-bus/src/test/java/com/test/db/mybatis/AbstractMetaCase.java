package com.test.db.mybatis;

import static com.prayer.util.Generator.bool;
import static com.prayer.util.Generator.index;
import static com.prayer.util.Generator.number;
import static com.prayer.util.Generator.uuid;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.prayer.db.mybatis.FieldMapper;
import com.prayer.db.mybatis.KeyMapper;
import com.prayer.db.mybatis.MetaMapper;
import com.prayer.db.mybatis.SessionManager;
import com.prayer.meta.DataType;
import com.prayer.mod.meta.FieldModel;
import com.prayer.mod.meta.KeyModel;
import com.prayer.mod.meta.MetaModel;
import com.prayer.mod.meta.SystemEnum.FieldDatetime;
import com.prayer.mod.meta.SystemEnum.KeyCategory;
import com.prayer.mod.meta.SystemEnum.MetaCategory;
import com.prayer.mod.meta.SystemEnum.MetaMapping;
import com.prayer.mod.meta.SystemEnum.MetaPolicy;

/**
 * 
 *
 * @author Lang
 * @see
 */
public class AbstractMetaCase {
	// ~ Static Fields =======================================
	/** **/
	public static final KeyCategory[] KEY_CATEGORIES = new KeyCategory[] {
			KeyCategory.ForeignKey, KeyCategory.PrimaryKey,
			KeyCategory.UniqueKey };
	/** **/
	public static final DataType[] DATA_TYPES = new DataType[] {
			DataType.BOOLEAN, DataType.INT, DataType.LONG, DataType.DECIMAL,
			DataType.DATE, DataType.STRING, DataType.JSON, DataType.XML,
			DataType.SCRIPT, DataType.BINARY };
	/** **/
	public static final FieldDatetime[] FIELD_DATETIME = new FieldDatetime[]{
			FieldDatetime.STRING,
			FieldDatetime.TIMER
	};
	/** **/
	public static final MetaCategory[] META_CATEGORIES = new MetaCategory[]{
			MetaCategory.ENTITY,
			MetaCategory.RELATION
	};
	/** **/
	public static final MetaMapping[] META_MAPPINGS = new MetaMapping[]{
			MetaMapping.COMBINATED,
			MetaMapping.DIRECT,
			MetaMapping.PARTIAL
	};
	/** **/
	public static final MetaPolicy[] META_POLICIES = new MetaPolicy[]{
			MetaPolicy.ASSIGNED,
			MetaPolicy.INCREMENT,
			MetaPolicy.GUID,
			MetaPolicy.COLLECTION
	};
	/** **/
	private static final int BATCH_SIZE = 24;
	// ~ Instance Fields =====================================
	/** **/
	private transient final SqlSession _session;
	/** **/
	private transient final KeyMapper keyMapper;
	/** **/
	private transient final MetaMapper metaMapper;
	/** **/
	private transient final FieldMapper fieldMapper;

	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	/** **/
	public AbstractMetaCase() {
		_session = SessionManager.getSession();
		if (null == this._session) {
			keyMapper = null;
			metaMapper = null;
			fieldMapper = null;
		} else {
			keyMapper = _session.getMapper(KeyMapper.class);
			metaMapper = _session.getMapper(MetaMapper.class);
			fieldMapper = _session.getMapper(FieldMapper.class);

		}
	}

	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Get/Set =============================================
	// ~ Methods =============================================
	/** **/
	protected SqlSession session() {
		return this._session;
	}

	/** **/
	protected KeyMapper getKeyMapper() {
		return this.keyMapper;
	}

	/** **/
	protected MetaMapper getMetaMapper() {
		return this.metaMapper;
	}

	/** **/
	protected FieldMapper getFieldMapper() {
		return this.fieldMapper;
	}

	/**
	 * 获取一个Key Model
	 * 
	 * @param uniqueId
	 * @return
	 */
	protected KeyModel getKey(final String uniqueId) {
		KeyModel key;
		if (null == uniqueId) {
			key = new KeyModel();
			key.setUniqueId(uuid());
		} else {
			key = this.keyMapper.selectById(uniqueId);
			this._session.commit();
		}
		key.setName("NAME-" + uuid());
		final List<String> columns = new ArrayList<>();
		for( int i = 0; i < 5; i++ ){
			columns.add("COLUMNS-" + uuid());
		}
		key.setColumns(columns);
		key.setMulti(bool());
		key.setCategory(KEY_CATEGORIES[index(3)]);
		return key;
	}
	/**
	 * 
	 * @param uniqueIds
	 * @return
	 */
	protected List<KeyModel> getKeys(final List<String> uniqueIds){
		final List<KeyModel> list = new ArrayList<>();
		if(null == uniqueIds){
			for(int i = 0; i < BATCH_SIZE; i++){
				list.add(getKey(null));
			}
		}else{
			for(final String uniqueId: uniqueIds){
				list.add(getKey(uniqueId));
			}
		}
		return list;
	}
	/**
	 * 
	 * @param uniqueId
	 * @return
	 */
	protected FieldModel getField(final String uniqueId) {
		FieldModel field;
		if (null == uniqueId) {
			field = new FieldModel();
			field.setUniqueId(uuid());
		} else {
			field = this.fieldMapper.selectById(uniqueId);
			this._session.commit();
		}
		field.setName("NAME-" + uuid());
		field.setType(DATA_TYPES[index(10)]);
		
		field.setPattern("PATTERN-" + uuid());
		field.setValidator("VALIDATOR-" + uuid());
		field.setLength(number(256));
		field.setDatetime(FIELD_DATETIME[index(2)]);
		field.setDateFormat("DATEFORMAT");
		field.setPrecision(number(16));
		field.setUnit("MB");
		field.setMaxLength(number(256));
		field.setMinLength(number(256));
		field.setMin(number(1000));
		field.setMax(number(2000));
		
		field.setPrimaryKey(bool());
		field.setUnique(bool());
		field.setSubTable(bool());
		field.setForeignKey(bool());
		field.setNullable(bool());
		
		field.setColumnName("COLUMNNAME-" + uuid());
		field.setColumnType("COLUMNTYPE-" + uuid());
		field.setRefTable("TABLE-" + uuid());
		field.setRefId("ID-" + uuid());
		return field;
	}
	/**
	 * 
	 * @param uniqueIds
	 * @return
	 */
	protected List<FieldModel> getFields(final List<String> uniqueIds){
		final List<FieldModel> list = new ArrayList<>();
		if(null == uniqueIds){
			for(int i = 0; i < BATCH_SIZE; i++){
				list.add(getField(null));
			}
		}else{
			for(final String uniqueId: uniqueIds){
				list.add(getField(uniqueId));
			}
		}
		return list;
	}
	/**
	 * 
	 * @param uniqueId
	 * @return
	 */
	protected MetaModel getMeta(final String uniqueId){
		MetaModel meta;
		if(null == uniqueId){
			meta = new MetaModel();
			meta.setUniqueId(uuid());
		}else{
			meta = this.metaMapper.selectById(uniqueId);
			this._session.commit();
		}
		meta.setOobFile("OOBFILE-" + uuid());
		meta.setUsing(bool());
		meta.setInitOrder(number(50));
		meta.setInitSubOrder(number(50));
		
		meta.setName("NAME-" + uuid());
		meta.setNamespace("NAMESPACE-" + uuid());
		meta.setCategory(META_CATEGORIES[index(2)]);
		meta.setGlobalId(uuid());
		meta.setMapping(META_MAPPINGS[index(3)]);
		meta.setPolicy(META_POLICIES[index(4)]);
		
		meta.setTable("TABLE-" + uuid());
		meta.setSubTable("SUBTABLE-" + uuid());
		meta.setSubKey("SUBKEY-" + uuid());
		meta.setSeqName("SEQNAME-" + uuid());
		meta.setSeqStep(number(10));
		meta.setSeqInit(number(10));
		return meta;
	}
	/**
	 * 
	 * @param uniqueIds
	 * @return
	 */
	protected List<MetaModel> getMetata(final List<String> uniqueIds){
		final List<MetaModel> list = new ArrayList<>();
		if(null == uniqueIds){
			for(int i = 0; i < BATCH_SIZE; i++){
				list.add(getMeta(null));
			}
		}else{
			for(final String uniqueId: uniqueIds){
				list.add(getMeta(uniqueId));
			}
		}
		return list;
	}
	// ~ Private Methods =====================================
	// ~ hashCode,equals,toString ============================
}
