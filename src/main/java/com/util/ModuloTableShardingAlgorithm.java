package com.util;

import java.util.Collection;
import java.util.LinkedHashSet;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;

/**
 * 表分配策略
 * @author mittermeyer
 *
 */
public final class ModuloTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Integer> {

	/**
	 * 根据分片值和SQL的=运算符计算分片结果名称集合.在WHERE使用=作为条件分片键。算法中使用shardingValue.getValue()获取等=后的值
	 * @param availableTargetNames 所有的可用目标名称集合, 一般是数据源或表名称,此处为所有可用的数据源中表的集合
	 * @param shardingValue 分片值
	 * @return 分片后指向的目标名称, 一般是数据源或表名称
	 * 例如：
	 * 程序中的sql：select * from t_order from t_order where order_id = 11 
	 * 由于=后面的值跟2取模之后为1，根据规则都是匹配到t_order_表中以1结尾的表
	 * 变成实际的sql为： SELECT * FROM t_order_1 WHERE order_id = 11 
	 * 
	 * 而：select * from t_order from t_order where order_id = 44 
	 * 由于=后面的值跟2取模之后为0，根据规则都是匹配到t_order_表中以0结尾的表
	 * 实际的sql则为：SELECT * FROM t_order_0 WHERE order_id = 44
	 */
	public String doEqualSharding(final Collection<String> tableNames, final ShardingValue<Integer> shardingValue) {
		for (String each : tableNames) {
			if (each.endsWith(shardingValue.getValue() % 2 + "")) {
				return each;
			}
		}
		throw new IllegalArgumentException();
	}

	/**
	 * 根据分片值和SQL的IN运算符计算分片结果名称集合. 在WHERE使用IN作为条件分片键。算法中使用shardingValue.getValues()获取IN后的值
     * @param availableTargetNames 所有的可用目标名称集合, 一般是数据源或表名称,此处为所有可用的数据源中表的集合
     * @param shardingValue 分片值
     * @return 分片后指向的目标名称集合, 一般是数据源或表名称
     * 例如：
	 * 程序中的sql：select * from t_order from t_order where order_id in (11,44)
	 * 由于in中的值跟2取模之后即有1的值也有0的值，根据规则t_order_表中以0和1结尾的表都会匹配到，所以会变成实际的sql时只会出现2条sql，对两张表都会进行查询
	 * 会变成实际的sql为： SELECT * FROM t_order_0 WHERE order_id IN (11,44)  
	 * 					SELECT * FROM t_order_1 WHERE order_id IN (11,44) 
	 * 
	 * select * from t_order from t_order where order_id in (11,13,15) 
	 * 由于in中的值跟2取模之后都是1，根据规则都是匹配到t_order_表中以1结尾的表，所以会变成实际的sql时只会出现一条sql查询t_order_1表
	 * 实际sql为： SELECT * FROM t_order_1 WHERE order_id IN (11,13,15) 
	 * 		
	 * select * from t_order from t_order where order_id in (22,24,26) 
	 * 由于in中的值跟2取模之后都是0，根据规则都是匹配到t_order_表中以0结尾的表，所以会变成实际的sql时也只会出现一条sql查询t_order_0表
	 * 实际sql为： SELECT * FROM t_order_0 WHERE order_id IN (22,24,26)
	 */
	public Collection<String> doInSharding(final Collection<String> tableNames,
			final ShardingValue<Integer> shardingValue) {
		Collection<String> result = new LinkedHashSet<>(tableNames.size());
		for (Integer value : shardingValue.getValues()) {
			for (String tableName : tableNames) {
				if (tableName.endsWith(value % 2 + "")) {
					result.add(tableName);
				}
			}
		}
		return result;
	}

	/**
	 * 根据分片值和SQL的BETWEEN运算符计算分片结果名称集合.在WHERE使用BETWEEN作为条件分片键。算法中使用shardingValue.getValueRange()获取BETWEEN后的值
     * @param availableTargetNames 所有的可用目标名称集合, 一般是数据源或表名称，此处为所有可用的数据源中表的集合
     * @param shardingValue 分片值
     * @return 分片后指向的目标名称集合, 一般是数据源或表名称
     * 
	 * select * from t_order from t_order where order_id between 10 and 20 
	 * 由于从between中的上界限值到下界限的值跟2取模之后即有1的值也有0的值，根据规则t_order_表中以0和1结尾的表都会匹配到，所以会变成实际的sql时只会出现2条sql，对两张表都会进行查询
	 * 实际sql为： SELECT * FROM t_order_0 WHERE order_id BETWEEN 10 AND 20 
	 * 			  SELECT * FROM t_order_1 WHERE order_id BETWEEN 10 AND 20
	 */
	public Collection<String> doBetweenSharding(final Collection<String> tableNames,final ShardingValue<Integer> shardingValue) {
		Collection<String> result = new LinkedHashSet<>(tableNames.size());
		Range<Integer> range = (Range<Integer>) shardingValue.getValueRange();
		for (Integer i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
			for (String each : tableNames) {
				if (each.endsWith(i % 2 + "")) {
					result.add(each);
				}
			}
		}
		return result;
	}
}