package com.prayer.model.type;

import static org.junit.Assert.assertEquals;

import java.util.Date;

//import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 
 * @author Lang
 * @see
 */
public class DateTestCase {     // NOPMD
    // ~ Static Fields =======================================
    /** **/
    private static final String V_DATE = "20150929";

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /** **/
    @Test
    public void testDat1() {
        final DateType data = new DateType();
        assertEquals("[T] DateType ", new Date(), data.getValue());
    }

    /** **/
    @Test
    public void testDate2() {
        final DateType data = new DateType(20150929L);
        assertEquals("[T] DateType -> 20150929", Date.class, data.getValue()
                .getClass());
    }

    /** **/
    @Test
    public void testDate3() {
        final DateType data = new DateType("20150302");
        assertEquals("[T] DateType -> 2015/09/29", Date.class, data.getValue()
                .getClass());
    }

    /** **/
    @Test
    public void testDate4() {
        final Date dateTime = new Date();
        final DateType data = new DateType(dateTime);
        assertEquals("[T] DateType -> Date", Date.class, data.getValue()
                .getClass());
    }

    /** **/
    @Test
    public void testDate5() {
        final DateType data1 = new DateType();
        final Date data2 = new Date();
        data1.setValue(data2);
        assertEquals("[T] test setValue - > Date", Date.class, data1.getValue()
                .getClass());
    }

    /** **/
    @Test
    public void testDate6() {
        final DateType data = new DateType();
        assertEquals("[T] test getType", Date.class, data.getType());
    }

    /** **/
    @Test
    public void testDate7() {
        final DateType data = new DateType();
        assertEquals("[T] test getDataType", DataType.DATE, data.getDataType());
    }

    /** **/
    @Test
    public void testDate8() {
        final DateType data1 = new DateType();
        final DateType data2 = new DateType(20150929L);
        data1.setValue(20150929L);
        assertEquals("[T] test setValue -> Long", data1.getValue(),
                data2.getValue());
    }

    /** **/
    @Test
    public void testDate9() {
        final DateType data1 = new DateType();
        final DateType data2 = new DateType(V_DATE);
        data1.setValue(V_DATE);
        assertEquals("[T] test setValue -> String", data1.getValue(),
                data2.getValue());
    }

    /** **/
    @Test
    public void testDate10() {
        final DateType data1 = new DateType(V_DATE);
        final DateType data2 = new DateType(V_DATE);
        assertEquals("[T] test toString", data1.toString(), data2.toString());
    }

    /** **/
    @Test
    public void testDate11() {
        final DateType data1 = new DateType(V_DATE);
        final DateType data2 = new DateType(V_DATE);
        assertEquals("[T] test hashCode", data1.hashCode(), data2.hashCode());
    }

    // ~ Private Methods =====================================
    // ~ hashCode,equals,toString ============================

}
