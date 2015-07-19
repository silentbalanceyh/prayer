package com.prayer.kernel.query;

/**
 * LIKE语句的匹配模式
 * @author Lang
 *
 */
public enum MatchMode {
	EXACT {
		/**
		 * 精确匹配LIKE语句 
		 * **/
		@Override
		public String toMatchString(final String pattern) {
			return pattern;
		}
	},
	START {
		/**
		 * 匹配开始的LIKE语句：pattern %
		 *  **/
		@Override
		public String toMatchString(final String pattern) {
			return pattern + '%';
		}
	},
	END {
		/**
		 * 匹配结束的LIKE语句：% pattern 
		 * **/
		@Override
		public String toMatchString(final String pattern) {
			return '%' + pattern;
		}
	},
	ANYWHERE {
		/**
		 * 匹配前后的LIKE语句：% pattern % 
		 * **/
		@Override
		public String toMatchString(final String pattern) {
			return '%' + pattern + '%';
		}
	};
	/**
	 * 返回最终匹配模式的语句
	 * @param pattern
	 * @return
	 */
	public abstract String toMatchString(String pattern);
}
