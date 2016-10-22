package com.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.dao.mongodb.AbstractRepository;
import com.dao.mongodb.BatchUpdateOptions;
import com.dao.mongodb.BatchUpdateUtil;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.pojo.mongodb.Person;

public class SpringMongoTest {

	private AbstractRepository repository;
	private ClassPathXmlApplicationContext context;
	private BatchUpdateUtil batchUpdate;
	
	@Before
	public void before() {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		repository = (AbstractRepository) context.getBean("personRepository");
		batchUpdate = (BatchUpdateUtil) context.getBean("batchUpdate");
	}

	@After
	public void after() {
		context.close();
	}

	/*
	 * @Test public void testInsert() { Person person = new Person("no13", 13,
	 * "traore", "dc"); boolean flag = repository.insert(person);
	 * assertTrue(flag); }
	 * 
	 * @Test public void testUpdateSet() { boolean flag =
	 * repository.updateSet("name", "gerrard", "loc", "mc,amc");
	 * assertTrue(flag); }
	 * 
	 * @Test public void testUpdateInc() { boolean flag =
	 * repository.updateSet("name", "owen", "no", 5); assertTrue(flag); }
	 * 
	 * @Test public void testFindAll() { List<Person> persons =
	 * repository.findAll(); for (int i = 0; i < persons.size(); i++) {
	 * System.out.println(persons.get(i)); } assertTrue(persons.size() > 0); }
	 */
	
	@Test
	public void testAggregation(){
		repository.aggregate();
	}
	
	@Test
	public void testBatchUpdate(){
		List<BatchUpdateOptions> list = new ArrayList<>();
		list.add(new BatchUpdateOptions(Query.query(Criteria.where("no").is(3)),Update.update("no", 23),true,true));
		list.add(new BatchUpdateOptions(Query.query(Criteria.where("_id").is("no16")),Update.update("name", "hammann"),true,true));
		int n = batchUpdate.batchUpdate(batchUpdate.getTemplate(), Person.class, list);
		assertTrue(n>0);
		System.out.println("受影响行数:"+n);
	}

	/*
	 * @Test public void testFindOne(){ Person person = repository.findOne("no",
	 * 1); assertEquals("gerrard", person.getName()); }
	 * 
	 * @Test public void testFindId(){ Person person =
	 * repository.findById("no1"); assertEquals("gerrard", person.getName()); }
	 * 
	 * @Test public void testFindByRegex(){ List<Person> persons =
	 * repository.findByRegex("owen"); for(int i=0;i<persons.size();i++){
	 * System.out.println(persons.get(i)); } assertTrue(persons.size()>0); }
	 * 
	 * @Test public void testRemoveOne(){ boolean flag =
	 * repository.removeOne("no2"); assertTrue(flag); }
	 * 
	 * @Test public void testCreateCollection(){ repository.createCollection();
	 * }
	 * 
	 * @Test public void testDropCollection(){ repository.dropCollection(); }
	 * 
	 * @Test public void testMapReduce(){ String inputCollectionName = "person";
	 * String mapFunction =
	 * "function(){emit({no:this.no},{count:1,name:this.name})}"; String
	 * reduceFunction =
	 * "function(key,values){var reduced = {count:0,name:''};values.forEach(function(val){reduce.count+=1;reduce.name=val.name;});return reduced};"
	 * ; MapReduceOptions options = new
	 * MapReduceOptions().outputCollection("ddd"); BasicDBList list =
	 * repository.mapReduce(inputCollectionName, mapFunction, reduceFunction,
	 * options); for(int i=0;i<list.size();i++){ BasicDBObject object =
	 * (BasicDBObject)list.get(i); System.out.println(object); } }
	 */

}
