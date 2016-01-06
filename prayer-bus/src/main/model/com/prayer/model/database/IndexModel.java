package com.prayer.model.database;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import io.vertx.core.json.JsonObject;

/**
 * 对应表SYS_INDEX
 * @author Lang
 *
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property="uniqueId")
public class IndexModel implements Serializable{
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** K_ID: Index表的主键 **/
    @JsonProperty("id")
    private String uniqueId;
    /** D_NAME：Index的名称，数据库的索引名 **/
    @JsonProperty("name")
    private String name;
    /** D_COLUMNS：Index中的列数据 **/
    @JsonProperty("columns")
    private List<JsonObject> columns;
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
