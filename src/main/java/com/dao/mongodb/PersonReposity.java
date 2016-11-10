package com.dao.mongodb;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.BasicDBList;
import com.mongodb.CommandResult;
import com.mongodb.WriteResult;
import com.pojo.mongodb.Book;
import com.pojo.mongodb.Person;
import com.pojo.mongodb.PersonAggregationResult;

public class PersonReposity implements AbstractRepository {

	private MongoTemplate template;

	public void setTemplate(MongoTemplate template) {
		this.template = template;
	}

	public MongoTemplate getTemplate() {
		return template;
	}

	@Override
	public boolean insert(Person person) {
		try {
			getTemplate().insert(person);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Person findOne(String templateKey, Object templateValue) {
		return getTemplate().findOne(new Query(Criteria.where(templateKey).is(templateValue)), Person.class);
	}

	@Override
	public List<Person> findAll() {
		return getTemplate().find(new Query(), Person.class);
	}

	@Override
	public List<Person> findByRegex(String regex) {
		return getTemplate().find(new Query(new Criteria("name").regex(Pattern.compile(regex))), Person.class);
	}

	@Override
	public boolean removeOne(String id) {
		Criteria criteria = Criteria.where("_id").in(id);
		if (criteria != null) {
			Query query = new Query(criteria);
			Person person = getTemplate().findOne(query, Person.class);
			if (person != null) {
				WriteResult result = getTemplate().remove(person);
				// System.out.println(result.getN());
				return result.getN() > 0;
			}
		}
		return false;
	}

	@Override
	public boolean removeAll() {
		return false;
	}

	@Override
	public boolean updateInc(String templateKey, Object templateValue, String key, Number inc) {
		WriteResult result = getTemplate().updateFirst(new Query(Criteria.where(templateKey).is(templateValue)),
				new Update().inc(templateKey, inc), Person.class);
		return result.isUpdateOfExisting();
	}

	@Override
	public boolean updateSet(String templateKey, Object templateValue, String Uppdatekey, Object updateValue) {
		WriteResult result = getTemplate().updateFirst(new Query(Criteria.where(templateKey).is(templateValue)),
				new Update().set(Uppdatekey, updateValue), Person.class);
		return result.isUpdateOfExisting();
	}

	@Override
	public BasicDBList mapReduce(String inputCollectionName, String mapFunction, String reduceFunction,
			MapReduceOptions options) {
		MapReduceResults<Person> mapReduce = getTemplate().mapReduce(inputCollectionName, mapFunction, reduceFunction,
				Person.class);
		BasicDBList list = (BasicDBList) mapReduce.getRawResults().get("result");
		return list;
	}

	@Override
	public void createCollection() {
		if (!getTemplate().collectionExists(Book.class)) {
			getTemplate().createCollection(Book.class);
		}
	}

	@Override
	public void dropCollection() {
		if (getTemplate().collectionExists(Book.class)) {
			getTemplate().dropCollection(Book.class);
		}
	}

	@Override
	public Person findById(String Id) {
		return getTemplate().findById(Id, Person.class);
	}

	public CommandResult executeCommand(String jsonCommand) {
		return getTemplate().executeCommand(jsonCommand);
	}
	
	public void aggregate(){
		/*
		 * 基本的操作包括：
		 * $project - 可以从子文档中提取字段，可以重命名字段。
	     * $match - 可以实现查找的功能。
		 * $limit - 接受一个数字n，返回结果集中的前n个文档。
		 * $skip - 接受一个数字n，丢弃结果集中的前n个文档。
		 * $group - 统计操作， 还提供了一系列子命令。
		 * $avg, $sum 等等函数…。
		 * $sort - 排序。	
		 * 相当于sql中 select a.loc, count(*) as count from person
         * as a group by a.loc having count > 2
		 * mongodb中语法
		 * $group:根据loc分组，然后统计次数，用$sum函数，显示第一个名称 $project:定义要显示的key,1为显示，0为不显示 $match:过滤掉次数小于2的
		 *  db.person.aggregate([{"$group":{"_id":"$loc","count":{"$sum":1},"name":{"$first":"$loc"}}},
		 *  {"$project":{"name":1,"count":1,"_id":0}},{"$match":{"count":{"$gt":2}}}])
		 */
		Aggregation agg = newAggregation(
			group("loc").count().as("count").first("loc").as("name"),
			project("name","count"),sort(Direction.DESC,"count"),
			match(Criteria.where("count").gt(2))
		);
		
		AggregationResults<PersonAggregationResult> results = getTemplate().aggregate(agg, "person", PersonAggregationResult.class);
		List<PersonAggregationResult> tagCount = results.getMappedResults();
		for(PersonAggregationResult result : tagCount){
			System.out.println(result.getName()+"\t"+result.getCount());
		}
	}

}
