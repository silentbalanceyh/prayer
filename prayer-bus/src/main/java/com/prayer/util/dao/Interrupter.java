package com.prayer.util.dao;

import static com.prayer.util.Error.debug;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.exception.AbstractDatabaseException;
import com.prayer.exception.database.ExecuteFailureException;
import com.prayer.exception.database.InvalidPKParameterException;
import com.prayer.exception.database.MoreThanOneException;
import com.prayer.exception.database.OperationNotSupportException;
import com.prayer.exception.database.PKDefinitionMissingException;
import com.prayer.exception.database.PKValueMissingException;
import com.prayer.exception.database.PolicyConflictCallException;
import com.prayer.exception.database.PolicyNotSupportException;
import com.prayer.facade.kernel.Record;
import com.prayer.facade.kernel.Value;
import com.prayer.model.h2.schema.FieldModel;
import com.prayer.util.cv.Constants;
import com.prayer.util.cv.SystemEnum.MetaPolicy;

/**
 * 
 * @author Lang
 *
 */
public final class Interrupter {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    /**
     * Response相关的Interrupter
     * 
     * @author Lang
     *
     */
    public static class Response {
        /**
         * 数据库执行结果是否成功，数据库执行失败抛出该异常
         * 
         * @param clazz
         * @param ret
         * @throws AbstractDatabaseException
         */
        public static void interrupt(final Class<?> clazz, final boolean ret) throws AbstractDatabaseException {
            if (!ret) {
                throw new ExecuteFailureException(clazz);
            }
        }

        /**
         * 唯一记录返回结果检查，返回值只能有一个的检查
         * 
         * @param clazz
         * @param size
         * @throws AbstractDatabaseException
         */
        public static Record interrupt(final Class<?> clazz, final List<Record> retList, final String table)
                throws AbstractDatabaseException {
            if (Constants.ONE < retList.size()) {
                throw new MoreThanOneException(clazz, table);
            }
            return Constants.ZERO == retList.size() ? null : retList.get(Constants.ZERO);
        }
    }

    /**
     * Primary Key主键相关的Interrupter
     * 
     * @author Lang
     *
     */
    public static class PrimaryKey {
        /**
         * 主键定义的Exception，如果主键元数据为0抛出该异常
         * 
         * @param clazz
         * @param size
         * @throws AbstractDatabaseException
         */
        public static void interrupt(final Class<?> clazz, final String identifier, final int size)
                throws AbstractDatabaseException {
            if (size < Constants.ONE) {
                throw new PKDefinitionMissingException(clazz, identifier);
            }
        }

        /**
         * 验证主键中是否有值，针对非INSERT方法必须检查
         * 
         * @param clazz
         * @param record
         * @throws AbstractDatabaseException
         */
        public static void interrupt(final Class<?> clazz, final Record record) throws AbstractDatabaseException {
            for (final FieldModel field : record.idschema()) {
                final Value<?> value = record.get(field.getName());
                if (null == value) {
                    throw new PKValueMissingException(clazz, field.getColumnName(), record.table());
                }
            }
        }

        /**
         * 验证pkeys中的列是否主键列
         * 
         * @param clazz
         * @param record
         * @param pkeys
         * @throws AbstractDatabaseException
         */
        public static void interrupt(final Class<?> clazz, final Record record, final Collection<String> pkeys)
                throws AbstractDatabaseException {
            for (final FieldModel pkSchema : record.idschema()) {
                if (!pkeys.contains(pkSchema.getColumnName())) {
                    throw new InvalidPKParameterException(clazz, pkSchema.getColumnName(), record.table());
                }
            }
        }
    }

    public static class Api {
        /**
         * Api本身相关异常
         * @param clazz
         * @param method
         * @param interfaceCls
         * @throws AbstractDatabaseException
         */
        public static void interrupt(final Class<?> clazz, final String method)
                throws AbstractDatabaseException {
            throw new OperationNotSupportException(clazz, method);
        }
    }

    /**
     * Primary Key的Policy冲突异常
     * 
     * @author Lang
     *
     */
    public static class Policy {
        /** **/
        private static final Logger LOGGER = LoggerFactory.getLogger(Policy.class);

        /**
         * 主键Policy和更新冲突异常，COLLECTION的isMulti必须是true，
         * 而非COLLECTION的isMulti必须是false
         * 
         * @param clazz
         * @param policy
         * @param isMulti
         * @throws AbstractDatabaseException
         */
        public static void interrupt(final Class<?> clazz, final MetaPolicy policy, final boolean isMulti)
                throws AbstractDatabaseException {
            if (isMulti && MetaPolicy.COLLECTION != policy) {
                debug(LOGGER, "Multi = true, policy must be COLLECTION");
                throw new PolicyConflictCallException(clazz, policy.toString());
            }
            if (!isMulti && MetaPolicy.COLLECTION == policy) {
                debug(LOGGER, "Multi = false, policy must not be COLLECTION");
                throw new PolicyConflictCallException(clazz, policy.toString());
            }
        }

        /**
         * 
         * @param clazz
         * @param expected
         * @param actual
         * @throws AbstractDatabaseException
         */
        public static void interrupt(final Class<?> clazz, final MetaPolicy expected, final MetaPolicy actual)
                throws AbstractDatabaseException {
            if (expected != actual) {
                throw new PolicyNotSupportException(clazz, actual);
            }
        }
    }

    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private Interrupter() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
