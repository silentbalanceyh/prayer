package com.prayer.util;

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
	private static final Logger LOGGER = LoggerFactory
			.getLogger(IOKit.class);

	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static InputStream getFile(
			@NotNull @NotEmpty @NotBlank final String fileName) {
		return getFile(fileName, null);
	}

	/**
	 * 
	 * @param fileName
	 * @param clazz
	 * @return
	 */
	public static InputStream getFile(
			@NotNull @NotEmpty @NotBlank final String fileName,
			final Class<?> clazz) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("[D] ==> ( fileName = " + fileName + ", clazz = " + clazz
					+ " ) Input parameter reading...");
		}
		InputStream retStream = null;
		if (null == clazz) {
			// 直接从文件File file = new File()中读取InputStream
			retStream = readStream(new File(fileName));
			// 根据当前Thread的ClassLoader中获取InputStream
			retStream = null == retStream ? readStream(fileName) : retStream;
			// 获取不到的时候从传入的Class --> FileExplorer中获取InputStream
			retStream = null == retStream ? readStream(fileName,
					IOKit.class) : retStream;
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

	private static InputStream readStream(final String fileName,
			final Class<?> clazz) { // NOPMD
		final InputStream retStream = clazz.getResourceAsStream(fileName);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("[D] <== ( Value -> " + retStream
					+ " ) Read from clazz.getResourceAsStream(filename).");
		}
		return retStream;
	}

	private static InputStream readStream(final String fileName) { // NOPMD
		final ClassLoader loader = Thread.currentThread()
				.getContextClassLoader();
		final InputStream retStream = loader.getResourceAsStream(fileName);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("[D] <== ( Value -> "
					+ retStream
					+ " ) Read from Thread.currentThread().getContextClassLoader().getResourceAsStream(filename).");
		}
		return retStream;
	}

	private static InputStream readStream(final File file) {
		InputStream retStream = null;
		if (null != file && file.exists() && file.isFile()) {
			try {
				retStream = new FileInputStream(file);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(" <== [D] ( Value -> " + retStream
							+ " ) Read from FileInputStream(file).");
				}
			} catch (FileNotFoundException ex) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(
							"[E] ~!!~ File does not exist: "
									+ file.getAbsolutePath(), ex);
				}
			}
		} else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("[E] ~!!~ ( file -> " + file
						+ " ) File does not exist or IO error!");
			}
		}
		return retStream;
	}

	private IOKit() {
	}
	// ~ hashCode,equals,toString ============================
}
