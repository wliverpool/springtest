package com.util;

import java.util.Collection;
import java.util.LinkedHashSet;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.google.common.collect.Range;

/**
 * 单分片键数据源分片算法，分库路由函数
 * @author mittermeyer
 *
 */
public class ModuloDatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm<Integer>{  
	  
	/**
	 * 根据分片值和SQL的=运算符计算分片结果名称集合.在WHERE使用=作为条件分片键。算法中使用shardingValue.getValue()获取等=后的值
	 * @param availableTargetNames 所有的可用目标名称集合, 一般是数据源或表名称,此处为所有可用的数据源名称的集合
	 * @param shardingValue 分片值
	 * @return 分片后指向的目标名称, 一般是数据源或表名称
	 */
    @Override  
    public String doEqualSharding(Collection<String> availableTargetNames, ShardingValue<Integer> shardingValue) {  
    	//循环所有可用的数据源集合，按照分片值数据字段除以2取模的值，决定数据存储到哪个数据源
        for (String each : availableTargetNames) {  
            if (each.endsWith(shardingValue.getValue() % 2 + "")) {  
                return each;  
            }  
        }  
        throw new IllegalArgumentException();  
    }  
  
    /**
     * 根据分片值和SQL的IN运算符计算分片结果名称集合. 在WHERE使用IN作为条件分片键。算法中使用shardingValue.getValues()获取IN后的值
     * @param availableTargetNames 所有的可用目标名称集合, 一般是数据源或表名称
     * @param shardingValue 分片值
     * @return 分片后指向的目标名称集合, 一般是数据源或表名称
     */
    @Override  
    public Collection<String> doInSharding(Collection<String> availableTargetNames, ShardingValue<Integer> shardingValue) {  
        Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());  
        for (Integer value : shardingValue.getValues()) {  
            for (String each : availableTargetNames) {  
                if (each.endsWith(value % 2 + "")) {  
                    result.add(each);  
                }  
            }  
        }  
        return result;  
    }  
  
    /**
     * 根据分片值和SQL的BETWEEN运算符计算分片结果名称集合.在WHERE使用BETWEEN作为条件分片键。算法中使用shardingValue.getValueRange()获取BETWEEN后的值
     * @param availableTargetNames 所有的可用目标名称集合, 一般是数据源或表名称
     * @param shardingValue 分片值
     * @return 分片后指向的目标名称集合, 一般是数据源或表名称
     */
    @Override  
    public Collection<String> doBetweenSharding(Collection<String> availableTargetNames,  ShardingValue<Integer> shardingValue) {  
        Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());  
        Range<Integer> range = (Range<Integer>) shardingValue.getValueRange();  
        for (Integer i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {  
            for (String each : availableTargetNames) {  
                if (each.endsWith(i % 2 + "")) {  
                    result.add(each);  
                }  
            }  
        }  
        return result;  
    }  
  
}  