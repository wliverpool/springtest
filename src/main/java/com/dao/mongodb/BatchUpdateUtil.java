package com.dao.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * 急适合单独直接连接mongodb,又适合spring mongodb批量更新工具类
 * @author 吴福明
 *
 */
public class BatchUpdateUtil {
	
	private MongoTemplate template;
	
	public MongoTemplate getTemplate() {
		return template;
	}
	
	public void setTemplate(MongoTemplate template) {
		this.template = template;
	}
	
	/**
	 * 批量更新集合数据
	 * @param template   spring mongodb template对象
	 * @param collName   要更新的集合名称
	 * @param options    要更新的操作
	 * @param ordered    执行时遇到更新异常是否不再继续,为true不继续执行,为false继续执行
	 * @return  更新的数量
	 */
	public int batchUpdate(MongoTemplate template,String collName,List<BatchUpdateOptions> options,boolean ordered){
		return doBatchUpdate(template.getCollection(collName), collName, options, ordered);
	}
	
	/**
	 * 批量更新集合数据
	 * @param dbCollection   要更新的集合对象
	 * @param collName   要更新的集合名称
	 * @param options    要更新的操作
	 * @param ordered    执行时遇到更新异常是否不再继续,为true不继续执行,为false继续执行
	 * @return  更新的数量
	 */
	public int batchUpdate(DBCollection dbCollection,String collName,List<BatchUpdateOptions> options,boolean ordered){
		return doBatchUpdate(dbCollection, collName, options, ordered);
	}
	
	/**
	 * 批量更新集合数据
	 * @param template   spring mongodb template对象
	 * @param entityClass   要更新的集合的java类型
	 * @param options    要更新的操作
	 * @param ordered    执行时遇到更新异常是否不再继续,为true不继续执行,为false继续执行
	 * @return  更新的数量
	 */
	public int batchUpdate(MongoTemplate template,Class<?> entityClass,List<BatchUpdateOptions> options,boolean ordered){
		String collName = determineCollectionName(entityClass);
		return doBatchUpdate(template.getCollection(collName), collName, options, ordered);
	}
	
	/**
	 * 按照执行时遇到更新异常不再继续更新的方式,批量更新集合数据
	 * @param template   spring mongodb template对象
	 * @param collName   要更新的集合名称
	 * @param options    要更新的操作
	 * @return  更新的数量
	 */
	public int batchUpdate(MongoTemplate template,String collName,List<BatchUpdateOptions> options){
		return doBatchUpdate(template.getCollection(collName), collName, options, true);
	}
	
	/**
	 * 按照执行时遇到更新异常不再继续更新的方式,批量更新集合数据
	 * @param dbCollection   要更新的集合对象
	 * @param collName   要更新的集合名称
	 * @param options    要更新的操作
	 * @return  更新的数量
	 */
	public int batchUpdate(DBCollection dbCollection,String collName,List<BatchUpdateOptions> options){
		return doBatchUpdate(dbCollection, collName, options, true);
	}
	
	/**
	 * 按照执行时遇到更新异常不再继续更新的方式,批量更新集合数据
	 * @param template   spring mongodb template对象
	 * @param entityClass   要更新的集合的java类型
	 * @param options    要更新的操作
	 * @return  更新的数量
	 */
	public int batchUpdate(MongoTemplate template,Class<?> entityClass,List<BatchUpdateOptions> options){
		String collName = determineCollectionName(entityClass);
		return doBatchUpdate(template.getCollection(collName), collName, options, true);
	}
	
	/**
	 * 批量更新集合数据
	 * @param dbCollection   要更新的集合
	 * @param collName       要更新的集合名称
	 * @param options        批量更新的操作
	 * @param ordered        执行时遇到更新异常是否不再继续,为true不继续执行,为false继续执行
	 * @return   更新的数量
	 */
	private int doBatchUpdate(DBCollection dbCollection,String collName,List<BatchUpdateOptions> options,boolean ordered){
		
		DBObject command = new BasicDBObject();
		command.put("update", collName);
		List<BasicDBObject> updateList = new ArrayList<>();
		for(BatchUpdateOptions option : options){
			BasicDBObject update = new BasicDBObject();
			update.put("q", option.getQuery().getQueryObject());
			update.put("u", option.getUpdate().getUpdateObject());
			update.put("upsert", option.isUpsert());
			update.put("multi", option.isMulti());
			updateList.add(update);
		}
		command.put("updates", updateList);
		command.put("ordered", ordered);
		CommandResult result = dbCollection.getDB().command(command);
		return Integer.parseInt(result.get("n").toString());
	}
	
	/**
	 * 根据类获取对应的mongodb的集合名
	 * @param entityClass   java类型类
	 * @return  mongodb的集合名
	 */
	private String determineCollectionName(Class<?> entityClass){
		if(null==entityClass){
			throw new InvalidDataAccessApiUsageException("No Class parameter provided,entityCollection can't be determined!");
		}
		String collName = entityClass.getSimpleName();
		if(entityClass.isAnnotationPresent(Document.class)){
			//如果类有Document注解,去Document注解中的collection值的属性
			Document document = entityClass.getAnnotation(Document.class);
			collName = document.collection();
		}else{
			//其他情况就把类名转换成小写 当做集合名
			collName = collName.replaceFirst(collName.substring(0, 1), collName.substring(0, 1).toLowerCase());
		}
		return collName;
	}

}
