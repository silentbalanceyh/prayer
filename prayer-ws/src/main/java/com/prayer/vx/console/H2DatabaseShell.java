package com.prayer.vx.console;

import static com.prayer.util.Instance.singleton;
import static com.prayer.util.Log.jvmError;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.h2.tools.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.dao.impl.jdbc.H2ConnImpl;
import com.prayer.util.StringKit;
import com.prayer.util.cv.Constants;
import com.prayer.vx.configurator.ServerConfigurator;

/**
 * 开启H2 Database的Shell
 * 
 * @author Lang
 *
 */
public class H2DatabaseShell {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(H2DatabaseShell.class);
    /** **/
    private transient final ServerConfigurator configurator = singleton(ServerConfigurator.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     * @throws SQLException
     */
    public void start() {
        try {
            if (StringKit.isNonNil(this.configurator.getJdbcUrl())) {
                final List<String> params = new ArrayList<>();
                params.add("-url");
                params.add(this.configurator.getJdbcUrl());
                params.add("-user");
                params.add(H2ConnImpl.getH2User());
                params.add("-password");
                params.add(H2ConnImpl.getH2Password());
                // 实例化H2的Shell
                final Shell shell = new Shell();
                shell.runTool(params.toArray(Constants.T_STR_ARR));
            }
        } catch (SQLException ex) {
            jvmError(LOGGER, ex);
        }
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
