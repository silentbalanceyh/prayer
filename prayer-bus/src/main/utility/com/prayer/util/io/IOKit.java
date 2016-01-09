package com.prayer.util.io;

import static com.prayer.util.debug.Log.info;
import static com.prayer.util.debug.Log.jvmError;
import static com.prayer.util.reflection.Instance.singleton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.constant.Constants;
import com.prayer.constant.Resources;
import com.prayer.constant.Symbol;
import com.prayer.constant.log.InfoKey;
import com.prayer.plugin.io.filter.OnlyDirectoryFilter;
import com.prayer.plugin.io.filter.OnlyFileFilter;

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
            jvmError(LOGGER, ex);
        } catch (IOException ex) {
            jvmError(LOGGER, ex);
        } finally {
            try {
                inStream.close();
            } catch (IOException ex) {
                jvmError(LOGGER, ex);
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
        return list(folder, false);
    }

    /**
     * 
     * @param folder
     * @return
     */
    @NotNull
    public static List<String> listDirectories(@NotNull @NotEmpty @NotBlank final String folder) {
        return list(folder, true);
    }

    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Get/Set =============================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================

    private static List<String> list(final String folder, final boolean isDirectory) {
        final URL url = getURL(folder);
        final List<String> retList = new ArrayList<>();
        if (null != url) {
            final File file = new File(url.getFile());
            if (file.isDirectory() && file.exists()) {
                // 设置Filter信息
                FileFilter filter = null;
                if (isDirectory) {
                    filter = singleton(OnlyDirectoryFilter.class);
                } else {
                    filter = singleton(OnlyFileFilter.class);
                }
                // Filter不可为空
                if (null != filter) {
                    final File[] files = file.listFiles(filter);
                    for (final File item : files) {
                        retList.add(item.getName());
                    }
                }
            }
        }
        return retList;
    }

    private static InputStream readStream(final String fileName, final Class<?> clazz) { // NOPMD
        return clazz.getResourceAsStream(fileName);
    }

    private static InputStream readStream(final String fileName) { // NOPMD
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return loader.getResourceAsStream(fileName);
    }

    private static InputStream readStream(final File file) {
        InputStream retStream = null;
        if (null != file && file.exists() && file.isFile()) {
            try {
                retStream = new FileInputStream(file);
                info(LOGGER, InfoKey.INF_PU_READ_FILE, file.getAbsolutePath());
            } catch (FileNotFoundException ex) {
                jvmError(LOGGER, ex);
            }
        }
        return retStream;
    }

    private IOKit() {
    }
    // ~ hashCode,equals,toString ============================
}