package com.example.llz.commons.utils;

import java.util.List;

/**
 * SQL语句处理类。
 */
public final class SqlUtil {

	/**
	 * 获取where in语句，主要处理oracle的sql语句in子句中（where id in (1, 2, ..., 1000, 1001)），
	 * 如果子句中超过1000项就会报错。 这主要是oracle考虑性能问题做的限制。 如果要解决次问题，可以用 where id (1, 2, ...,
	 * 1000) or id (1001, ...)
	 * @param column 字段名
	 * @param values 值集合
	 * @param num 数量，指明 每组几个元素
	 * @return where in 语句，即返回( column in (...) or column in (...) )字符串
	 */
	public static String getWhereInValuesSql(String column, List<String> values, int num)
	{
		// sql语句
		String sql = "(";
		// 值的个数
		int valueSize = values.size();
		if(valueSize==0){
			sql += column + " in ( ";
		}else {
			// 批次数
			int batchSize = valueSize / num + (valueSize % num == 0 ? 0 : 1);
			for (int i = 0; i < batchSize; i++) {
				if (i > 0) {
					sql += ") or ";
				}
				sql += column + " in (";
				for (int j = i * num; (j < (i + 1) * num) && j < valueSize; j++) {
					if (j > i * num) {
						sql += ",";
					}
					sql += "'" + values.get(j) + "'";
				}
			}
		}
		sql += "))";
		return sql;
	}

}
