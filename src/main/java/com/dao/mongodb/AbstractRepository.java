package com.dao.mongodb;

import java.util.List;

import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;

import com.mongodb.BasicDBList;
import com.pojo.mongodb.Person;

public interface AbstractRepository {

	public boolean insert(Person person);

	public Person findOne(String templateKey, Object templateValue);

	public Person findById(String Id);

	public List<Person> findAll();

	public List<Person> findByRegex(String regex);

	public boolean removeOne(String id);

	public boolean removeAll();

	public boolean updateInc(String templateKey, Object templateValue, String key, Number inc);

	public boolean updateSet(String templateKey, Object templateValue, String Uppdatekey, Object updateValue);

	public BasicDBList mapReduce(String inputCollectionName, String mapFunction, String reduceFunction,
			MapReduceOptions options);

	public void createCollection();

	public void dropCollection();
}
