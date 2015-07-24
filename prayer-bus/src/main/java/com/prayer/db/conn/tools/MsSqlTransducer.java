package com.prayer.db.conn.tools;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.prayer.kernel.Value;
import com.prayer.model.type.BinaryType;
import com.prayer.model.type.BooleanType;
import com.prayer.model.type.DataType;
import com.prayer.model.type.DateType;
import com.prayer.model.type.DecimalType;
import com.prayer.model.type.IntType;
import com.prayer.model.type.JsonType;
import com.prayer.model.type.LongType;
import com.prayer.model.type.ScriptType;
import com.prayer.model.type.StringType;
import com.prayer.model.type.XmlType;

import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * 
 * @author Lang
 *
 */
final class MsSqlTransducer implements Transducer { // NOPMD
	// ~ Static Fields =======================================
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	/** **/
	@Override
	public void injectArgs(@NotNull final PreparedStatement stmt, @Min(0) final int idx, @NotNull final Value<?> value)
			throws SQLException {
		switch (value.getDataType()) {
		case INT: {
			stmt.setInt(idx, ((IntType) value).getValue());
		}
			break;
		case LONG: {
			stmt.setLong(idx, ((LongType) value).getValue());
		}
			break;
		case BOOLEAN: {
			stmt.setBoolean(idx, ((BooleanType) value).getValue());
		}
			break;
		case DECIMAL: {
			stmt.setBigDecimal(idx, ((DecimalType) value).getValue());
		}
			break;
		case DATE: {
			final Date datetime = ((DateType) value).getValue();
			stmt.setTimestamp(idx, new java.sql.Timestamp(datetime.getTime()),
					Calendar.getInstance(Locale.getDefault()));
		}
			break;
		case BINARY: {
			final InputStream stream = new ByteArrayInputStream(((BinaryType) value).getValue());
			stmt.setBinaryStream(idx, stream);
		}
			break;
		default: // Default for XML, STRING, SCRIPT, JSON
		{
			stmt.setString(idx, ((StringType) value).getValue());
		}
			break;
		}
	}

	/** **/
	@Override
	public Value<?> getValue(@NotNull final ResultSet retSet, @NotNull final DataType type, // NOPMD
			@NotNull @NotEmpty @NotBlank final String column) throws SQLException {
		Value<?> ret = null;
		switch (type) {
		case INT: {
			final int value = retSet.getInt(column);
			ret = new IntType(value);
		}
			break;
		case LONG: {
			final long value = retSet.getLong(column);
			ret = new LongType(value);
		}
			break;
		case BOOLEAN: {
			final boolean value = retSet.getBoolean(column);
			ret = new BooleanType(value);
		}
			break;
		case DECIMAL: {
			final BigDecimal value = retSet.getBigDecimal(column);
			ret = new DecimalType(value);
		}
			break;
		case DATE: {
			final java.sql.Timestamp value = retSet.getTimestamp(column, Calendar.getInstance(Locale.getDefault()));
			ret = new DateType(new Date(value.getTime()));
		}
			break;
		case BINARY: {
			final byte[] value = retSet.getBytes(column);
			ret = new BinaryType(value);
		}
			break;
		case XML: {
			final String value = retSet.getString(column);
			ret = new XmlType(value);
		}
			break;
		case JSON: {
			final String value = retSet.getString(column);
			ret = new JsonType(value);
		}
			break;
		case SCRIPT: {
			final String value = retSet.getString(column);
			ret = new ScriptType(value);
		}
			break;
		default: {
			final String value = retSet.getString(column);
			ret = new StringType(value);
		}
			break;
		}
		return ret;
	}
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================
}
