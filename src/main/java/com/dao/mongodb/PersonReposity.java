package com.dao.mongodb;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.data.mongodb.core.MongoTemplate;
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

}
