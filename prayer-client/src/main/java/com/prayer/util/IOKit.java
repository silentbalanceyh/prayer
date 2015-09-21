package com.prayer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import com.prayer.constant.Constants;
import com.prayer.constant.Symbol;

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
			reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
			String line = null;
			while (null != (line = reader.readLine())) {	// NOPMD
				builder.append(line).append(Symbol.NEW_LINE);
			}
			content = builder.toString();
			reader.close();
		} catch (UnsupportedEncodingException ex) {
		} catch (IOException ex) {
		} finally {
			try {
				inStream.close();
			} catch (IOException ex) {
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

	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Get/Set =============================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================

	private static InputStream readStream(final String fileName, final Class<?> clazz) { // NOPMD
		final InputStream retStream = clazz.getResourceAsStream(fileName);
		return retStream;
	}

	private static InputStream readStream(final String fileName) { // NOPMD
		final ClassLoader loader = Thread.currentThread().getContextClassLoader();
		final InputStream retStream = loader.getResourceAsStream(fileName);
		return retStream;
	}

	private static InputStream readStream(final File file) {
		InputStream retStream = null;
		if (null != file && file.exists() && file.isFile()) {
			try {
				retStream = new FileInputStream(file);
			} catch (FileNotFoundException ex) {
			}
		} else {
			if (null == file) {
			} else {
			}
		}
		return retStream;
	}

	private IOKit() {
	}
	// ~ hashCode,equals,toString ============================
}