package com.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;

import com.dao.mongodb.AbstractRepository;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.pojo.mongodb.Person;

public class SpringMongoTest {

	private AbstractRepository repository;
	private ClassPathXmlApplicationContext context;

	@Before
	public void before() {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		repository = (AbstractRepository) context.getBean("personRepository");
	}

	@After
	public void after() {
		context.close();
	}

	/*@Test
	public void testInsert() {
		Person person = new Person("no3", 3, "carragher", "dc");
		boolean flag = repository.insert(person);
		assertTrue(flag);
	}

	@Test
	public void testUpdateSet() {
		boolean flag = repository.updateSet("name", "gerrard", "loc", "mc,amc");
		assertTrue(flag);
	}

	@Test
	public void testUpdateInc() {
		boolean flag = repository.updateSet("name", "owen", "no", 5);
		assertTrue(flag);
	}*/

	@Test
	public void testFindAll() {
		List<Person> persons = repository.findAll();
		for (int i = 0; i < persons.size(); i++) {
			System.out.println(persons.get(i));
		}
		assertTrue(persons.size() > 0);
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
