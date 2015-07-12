package com.prayer.util;

import static com.prayer.util.Error.debug;
import static com.prayer.util.Error.info;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	 * @param clazz
	 * @return
	 */
	public static InputStream getFile(@NotNull @NotEmpty @NotBlank final String fileName, final Class<?> clazz) {
		debug(LOGGER, "SYS.KIT.IO.PARAM", fileName, clazz);
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
				info(LOGGER, "[I] <== ( Value -> " + retStream + " ) Read from FileInputStream(file).");
			} catch (FileNotFoundException ex) {
				debug(LOGGER, "SYS.KIT.IO.FILE", ex, file.getAbsolutePath(), retStream);
			}
		} else {
			debug(LOGGER, "SYS.KIT.IO.ERR.FILE", file.getAbsolutePath());
		}
		return retStream;
	}

	private IOKit() {
	}
	// ~ hashCode,equals,toString ============================
}