package com.prayer.util.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.prayer.facade.resource.Inceptor;
import com.prayer.facade.resource.Point;
import com.prayer.resource.InceptBus;

import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

/**
 * 匹配器
 * 
 * @author Lang
 *
 */
@Guarded
public final class StringMater {
    // ~ Static Fields =======================================
    /** 正则表达式配置读取类 **/
    private static final Inceptor INCEPTOR = InceptBus.build(Point.Pattern.class);

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    /**
     * 是否匹配
     * 
     * @param input
     * @param pattern
     * @return
     */
    public static boolean isMatch(@NotNull @NotBlank @NotEmpty final String input,
            @NotNull @NotBlank @NotEmpty final String regex) {
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
    /**
     * 是否合法Email
     * @param input
     * @return
     */
    public static boolean isEmail(@NotNull @NotBlank @NotEmpty final String input) {
        final String regex = INCEPTOR.getString("pattern.email");
        if(StringKit.isNil(regex)){
            return false;
        }
        return isMatch(input, regex);
    }
    /**
     * 是否合法Mobile
     * @param input
     * @return
     */
    public static boolean isMobile(@NotNull @NotBlank @NotEmpty final String input) {
        final String regex = INCEPTOR.getString("pattern.mobile");
        if(StringKit.isNil(regex)){
            return false;
        }
        return isMatch(input, regex);
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private StringMater() {
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
