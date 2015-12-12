package com.prayer.util;

import static com.prayer.util.Error.debug;
import static com.prayer.util.Error.info;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.util.cv.Constants;
import com.prayer.util.cv.Resources;
import com.prayer.util.cv.Symbol;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 资源文件探索器
 *
 * @author Lang
 * @see
 */
@Guarded
public final class IOKit {
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(IOKit.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 
     * @param fileName
     * @return
     */
    public static InputStream getFile(@NotNull @NotEmpty @NotBlank final String fileName) {
        return getFile(fileName, null);
    }

    /**
     *
     * @param fileName
     * @return
     */
    public static String getContent(@NotNull @NotEmpty @NotBlank final String fileName) {
        final InputStream inStream = getFile(fileName);
        final StringBuilder builder = new StringBuilder(Constants.BUFFER_SIZE);
        info(LOGGER, " File = " + fileName);
        BufferedReader reader;
        String content = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inStream, Resources.SYS_ENCODING));
            String line = null;
            while (null != (line = reader.readLine())) { // NOPMD
                builder.append(line).append(Symbol.NEW_LINE);
            }
            content = builder.toString();
            reader.close();
        } catch (UnsupportedEncodingException ex) {
            debug(LOGGER, "JVM.ENCODING", ex, Resources.SYS_ENCODING);
        } catch (IOException ex) {
            debug(LOGGER, "JVM.IO", ex, fileName);
        } finally {
            try {
                inStream.close();
            } catch (IOException ex) {
                debug(LOGGER, "JVM.IO", ex, fileName);
            }
        }
        return content;
    }

    /**
     * 
     * @param fileName
     * @return
     */
    public static URL getURL(@NotNull @NotEmpty @NotBlank final String fileName) {
        URL retURL = null;
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (null != loader) {
            retURL = loader.getResource(fileName);
        }
        return retURL;
    }

    /**
     * 
     * @param fileName
     * @param clazz
     * @return
     */
    public static InputStream getFile(@NotNull @NotEmpty @NotBlank final String fileName, final Class<?> clazz) {
        InputStream retStream = null;
        if (null == clazz) {
            // 直接从文件File file = new File()中读取InputStream
            retStream = readStream(new File(fileName));
            // 根据当前Thread的ClassLoader中获取InputStream
            retStream = null == retStream ? readStream(fileName) : retStream;
            // 获取不到的时候从传入的Class --> FileExplorer中获取InputStream
            retStream = null == retStream ? readStream(fileName, IOKit.class) : retStream;
        } else {
            // 若有Class传入则先从传入class中获取InputStream
            retStream = readStream(fileName, clazz);
            // 不传Class
            retStream = null == retStream ? getFile(fileName, null) : retStream;
        }
        return retStream;
    }

    /**
     * 
     * @param folder
     * @return
     */
    @NotNull
    public static List<String> listFiles(@NotNull @NotEmpty @NotBlank final String folder) {
        final URL url = getURL(folder);
        final List<String> retList = new ArrayList<>();
        if (null != url) {
            final File file = new File(url.getFile());
            if (file.isDirectory() && file.exists()) {
                retList.addAll(Arrays.asList(file.list()));
            }
        }
        return retList;
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Get/Set =============================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private static InputStream readStream(final String fileName, final Class<?> clazz) { // NOPMD
        final InputStream retStream = clazz.getResourceAsStream(fileName);
        debug(LOGGER, "SYS.KIT.IO.CP", fileName, retStream);
        return retStream;
    }

    private static InputStream readStream(final String fileName) { // NOPMD
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final InputStream retStream = loader.getResourceAsStream(fileName);
        debug(LOGGER, "SYS.KIT.IO.LOADER", fileName, retStream);
        return retStream;
    }

    private static InputStream readStream(final File file) {
        InputStream retStream = null;
        if (null != file && file.exists() && file.isFile()) {
            try {
                retStream = new FileInputStream(file);
                info(LOGGER, "SYS.KIT.IO.FILE", null, file.getAbsoluteFile(), retStream);
            } catch (FileNotFoundException ex) {
                debug(LOGGER, "SYS.KIT.IO.FILE", ex, file.getAbsolutePath(), retStream);
            }
        } else {
            if (null == file) {
                debug(LOGGER, "SYS.KIT.IO.ERR.FILE", file);
            } else {
                debug(LOGGER, "SYS.KIT.IO.ERR.FILE", file.getAbsolutePath());
            }
        }
        return retStream;
    }

    private IOKit() {
    }
    // ~ hashCode,equals,toString ============================
}