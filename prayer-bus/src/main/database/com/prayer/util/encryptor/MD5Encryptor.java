package com.prayer.util.encryptor;

import static com.prayer.util.debug.Log.jvmError;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prayer.facade.constant.Constants;
import com.prayer.facade.util.Encryptor;

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
public class MD5Encryptor implements Encryptor{
    
    // ~ Static Fields =======================================
    /** **/
    private static final Logger LOGGER = LoggerFactory.getLogger(MD5Encryptor.class);
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /**
     * 
     */
    @Override
    public String encrypt(@NotNull @NotBlank @NotEmpty final String value) {
        String retValue = null;
        try {
            final MessageDigest digester = MessageDigest.getInstance("MD5");
            final byte[] source = value.getBytes();
            digester.update(source);
            final byte[] middle = digester.digest(); // MD5计算结果是一个128位长整数
            // 16个字节表示
            final char[] middleStr = new char[16 * 2]; // 每个16进制数使用两个字符

            // 16进制数需要32个字符
            int position = 0; // 转换结果中的字符位置
            for (int idx = 0; idx < 16; idx++) {
                // 16进制字符转换
                final byte byte0 = middle[idx]; // 取第idx个字节
                middleStr[position++] = Constants.UTI_HEX_ARR[byte0 >>> 4 & 0xF]; // 取字节中高4位
                middleStr[position++] = Constants.UTI_HEX_ARR[byte0 & 0xF]; // 取字节中高4位
            }
            retValue = new String(middleStr);
        } catch (NoSuchAlgorithmException ex) {
            jvmError(LOGGER,ex);
            retValue = value;
        }
        return retValue;
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
