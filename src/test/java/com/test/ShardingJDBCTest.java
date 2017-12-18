/*package com.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.util.ModuloDatabaseShardingAlgorithm;
import com.util.ModuloTableShardingAlgorithm;

public class ShardingJDBCTest {

	public static void main(String[] args) throws SQLException {*/

		// 数据源
		/*Map<String, DataSource> dataSourceMap = new HashMap<>(2);
		dataSourceMap.put("sharding_order_0", createDataSource("sharding_order_0"));
		dataSourceMap.put("sharding_order_1", createDataSource("sharding_order_1"));

		DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap);

		// 分表分库的表，第一个参数是逻辑表名，第二个是实际表名，第三个是实际库
		TableRule orderTableRule = TableRule.builder("t_order").actualTables(Arrays.asList("t_order_0", "t_order_1")).dataSourceRule(dataSourceRule).build();
		TableRule orderItemTableRule = TableRule.builder("t_order_item").actualTables(Arrays.asList("t_order_item_0", "t_order_item_1")).dataSourceRule(dataSourceRule).build();
		*/
		/**
		 * DatabaseShardingStrategy 分库策略 参数一：根据哪个字段分库 参数二：分库路由函数
		 * TableShardingStrategy 分表策略 参数一：根据哪个字段分表 参数二：分表路由函数
		 * 
		 *//*
		ShardingRule shardingRule = ShardingRule.builder()
		        .dataSourceRule(dataSourceRule)
		        .tableRules(Arrays.asList(orderTableRule, orderItemTableRule))
		        .databaseShardingStrategy(new DatabaseShardingStrategy("user_id", new ModuloDatabaseShardingAlgorithm()))//分库策略
		        .tableShardingStrategy(new TableShardingStrategy("order_id", new ModuloTableShardingAlgorithm()))//分表策略
		        .build();

		DataSource dataSource = ShardingDataSourceFactory.createDataSource(shardingRule);
		String sql = "SELECT i.* FROM t_order o JOIN t_order_item i ON o.order_id=i.order_id WHERE o.user_id=? AND o.order_id=?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, 10);
			pstmt.setInt(2, 1001);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					System.out.println(rs.getInt(1));
					System.out.println(rs.getInt(2));
					System.out.println(rs.getInt(3));
				}
			}
		}
	}*/

	/**
	 * 创建数据源
	 * 
	 * @param dataSourceName
	 * @return
	 *//*
	private static DataSource createDataSource(String dataSourceName) {
		BasicDataSource result = new BasicDataSource();
		result.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
		result.setUrl(String.format("jdbc:mysql://localhost:3306/%s", dataSourceName));
		result.setUsername("root");
		result.setPassword("wfmhbbwt");
		return result;
	}

}
*/