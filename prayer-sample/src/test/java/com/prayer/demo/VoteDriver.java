package com.prayer.demo;

import static com.prayer.util.Converter.toStr;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

/**
 * 
 * @author Lang
 *
 */
public class VoteDriver {
	// ~ Static Fields =======================================
	/** **/
	private static final String URI = "http://toupiao.diywap.cn/Home/Index/toupiao";
	// ~ Instance Fields =====================================
	// ~ Static Block ========================================
	// ~ Static Methods ======================================
	/** **/
	@Test
	public void testVote() throws Exception{
		/** 1.构造HTTP POST请求 **/
		final HttpPost post = new HttpPost(URI);
		final CloseableHttpClient client = HttpClients.createDefault();
		/** 2.构造Header **/
		{
			post.setHeader("Accept", "*/*");
			post.setHeader("Host","toupiao.diywap.cn");
			post.setHeader("Proxy-Connection","keep-alive");
			post.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
			post.setHeader("Origin","http://toupiao.diywap.cn");
			post.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36 MicroMessenger/6.5.2.501 NetType/WIFI WindowsWechat");
			post.setHeader("X-Requested-With","XMLHttpRequest");
			post.setHeader("Referer","http://toupiao.diywap.cn/Show/index/id/2153.html");
			post.setHeader("Accept-Encoding","gzip, deflate");
			post.setHeader("Accept-Language","en-us,en;q=0.8");
		}
		/** 得到相应Entity **/
		HttpResponse response = client.execute(post);
		{
			final HttpEntity entity = response.getEntity();
			if(entity != null){
				final InputStream in = entity.getContent();
				System.out.println(toStr(in));
			}
		}
	}
	// ~ Constructors ========================================
	// ~ Abstract Methods ====================================
	// ~ Override Methods ====================================
	// ~ Methods =============================================
	// ~ Private Methods =====================================
	// ~ Get/Set =============================================
	// ~ hashCode,equals,toString ============================

}
