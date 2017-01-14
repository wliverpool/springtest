package com.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class SqlUtil {

	/**
	 * 根据原始sql生成分页查询sql
	 * @param type        数据库类型
	 * @param sql         原始sql
	 * @param pageStart   分页开始行
	 * @param pageEnd     分页结束行
	 * @return            分页查询sql
	 */
	public static String createPageSql(String type, String sql, int pageStart, int pageEnd) {
		if ("oracle".equals(type)) {
			sql = "select * from (select pTable_.*, rownum rNum_ from (" + sql + ") pTable_ where rownum<=" + pageEnd + ") where rNum_>=" + pageStart;
		} else if ("mysql".equals(type)) {
			sql = sql + " limit " + pageStart + "," + (pageEnd - pageStart);
		} else if ("db2".equals(type)) {
			sql = "select * from (select *, rownumber() ROW_NEXT from (" + sql + ") where ROW_NEXT between " + pageStart + " and " + pageEnd;
		}
		return sql;
	}
	
	/**
	 * 根据原始sql生成对应的统计总数的sql
	 * @param sql  原始sql
	 * @return     统计总数的sql
	 */
	public static String generateCountSql(String sql){
		if(StringUtils.isNotBlank(sql)){
			return "select count(1) as cnt " + removeSelect(removeOrders(sql));
		}
		return "";
	}

	/**
	 * 去除原始sql中的order by语句
	 * @param sql   原始sql
	 * @return
	 */
	private static String removeOrders(String sql) {
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(sql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 去除原始sql中的select列名部分
	 * @param sql   原始sql
	 * @return
	 */
	private static String removeSelect(String sql) {
		final int beginPos = sql.toLowerCase().indexOf("from");
		return sql.substring(beginPos);
	}

}
