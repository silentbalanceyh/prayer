package com.prayer.util.coder;

import java.util.Base64;

import com.prayer.facade.util.EDCoder;
import com.prayer.resource.Resources;

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
public class Base64Coder implements EDCoder {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    /** 编码 **/
    @Override
    public String encode(@NotNull @NotEmpty @NotBlank final String value) {
        final Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(value.getBytes(Resources.ENCODING));
    }
    /** 解码 **/
    @Override
    public String decode(@NotNull @NotEmpty @NotBlank final String value) {
        final Base64.Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(value.getBytes(Resources.ENCODING)), Resources.ENCODING);
    }
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
