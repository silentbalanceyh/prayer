package com.prayer.assistant;

import java.text.MessageFormat;

import org.slf4j.Logger;

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
public final class WebLogger {
    // ~ Static Fields =======================================
    /** **/
    public static final String I_VERTICLE_COUNT = "({0}) Verticle count number = {1}";
    /** **/
    public static final String I_MSGH_REQUEST = "[{0}] {1} Order={2} -> Request Handler {3}.";
    /** **/
    public static final String I_MSGH_FAILURE = "Failure Handler {0} has been registered to {1}. Order={2}";
    /** **/
    public static final String I_SERVER_INFO = "Server will be running -> http://{0}:{1}";
    /** **/
    public static final String I_VERTICLE_INFO = "Async deploying verticles successfully ! Result = {0}";
    /** **/
    public static final String I_RECORD_HANDLER = "[VX-CUSTOM] Handler : {0} , RecordHandler processing...";
    /** **/
    public static final String I_SEC_HANDLER = "[VX-CUSTOM] Handler : {0}, BasicAuthenticateHandler processing...";
    /** **/
    public static final String I_STD_HANDLER = " Order = {1}, Path = {2} , Handler = {0}";
    /** **/
    public static final String I_CFG_HANDLER = " Path = {1}, Handler = {0}";
    /** **/
    public static final String I_REST_RESULT = " RestfulResult = {0}";
    /** **/
    public static final String I_VERTICLE_WORK = "Verticle Worker -> {0}";
    /** **/
    public static final String I_H2_DB_BEFORE = "{0} H2 {1} on {2}...";
    /** **/
    public static final String I_H2_DB_AFTER_ST = "H2 {0} started: RUNNING on {1} !";
    /** **/
    public static final String I_H2_DB_AFTER_SP = "H2 {0} stopped.";
    /** **/
    public static final String I_H2_DB_CLS_INIT_S = "H2 Database Cluster initialized successfully! Name List : {0}";
    /** **/
    public static final String I_H2_DB_CLS_STD = "H2 Database Cluster has been started successfully with parameters: {0}";
    /** **/
    public static final String I_H2_DB_CLS_DIS = "H2 Database Cluster has been disabled!";
    /** **/
    public static final String I_COMMON_INFO = "Information -> {0}";

    /** **/
    public static final String E_VERTICLE_ERROR = "Async deploying verticles failure ! Result = {0}";
    /** **/
    public static final String E_HANDLER_ERROR = "Route Configurator met error. Error = {0}";
    /** **/
    public static final String E_VERTICLE_COUNT = "({0}) Vertx reference = {1}, Queue Size = {2}";
    /** **/
    public static final String E_CLUSTER_ERROR = "Cluster vertx met error ! Error = {0}";
    /** **/
    public static final String E_COMMON_EXP = "Error occurs and Exception ex = {0}";
    /** **/
    public static final String E_ERROR_HTTP = "Error Code : {0} -> {1}, Error = {2}.";
    /** **/
    public static final String E_UCA_CFG_ERROR = "UCA Config Error : {0}";
    /** **/
    public static final String E_H2_DB_ERROR = "Starting H2 Database met error. Error = {0}";

    /** **/
    public static final String AUE_USERNAME = "Passed parameter 'authInfo' must contain username in 'username' field";
    /** **/
    public static final String AUE_PASSWORD = "Passed parameter 'authInfo' must contain password in 'password' field";
    /** **/
    public static final String AUE_USER_INVALID = "User does not exist in database, please provide valid user.";
    /** **/
    public static final String AUE_AUTH_FAILURE = "Authentication failure, the password does not match.";

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * Web Information
     * 
     * @param logger
     * @param patternAndMsg
     * @param params
     */
    public static void info(@NotNull final Logger logger, @NotNull @NotBlank @NotEmpty final String patternAndMsg,
            final Object... params) {
        if (params.length == 0) {
            com.prayer.util.Error.info(logger, "[I-WEB] " + patternAndMsg);
        } else {
            com.prayer.util.Error.info(logger, MessageFormat.format("[I-WEB] " + patternAndMsg, params));
        }
    }

    /**
     * Web Error
     * 
     * @param logger
     * @param patternAndMsg
     * @param params
     */
    public static void error(@NotNull final Logger logger, @NotNull @NotBlank @NotEmpty final String patternAndMsg,
            final Object... params) {
        if (params.length == 0) {
            logger.error("[E-WEB] " + patternAndMsg);
        } else {
            logger.error(MessageFormat.format("[E-WEB] " + patternAndMsg, params));
        }
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private WebLogger() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
}
