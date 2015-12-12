package com.prayer.bus;

import java.net.URI;

import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

/**
 * 带HttpEntity的Delete
 * 
 * @author Lang
 *
 */
@NotThreadSafe
public class HttpDelete extends HttpEntityEnclosingRequestBase {
    // ~ Static Fields =======================================
    /** **/
    public final static String METHOD_NAME = "DELETE";
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /** **/
    public HttpDelete(){
        super();
    }
    /** **/
    public HttpDelete(final URI uri){
        super();
        setURI(uri);
    }
    /**
     * @throws IllegalArgumentException if the uri is invalid.
     * @param uri
     */
    public HttpDelete(final String uri){
        super();
        setURI(URI.create(uri));
    }
    /** **/
    @Override
    public String getMethod(){
        return METHOD_NAME;
    }
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
