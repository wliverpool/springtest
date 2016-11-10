package com.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.dao.mongodb.AbstractRepository;
import com.dao.mongodb.BatchUpdateOptions;
import com.dao.mongodb.BatchUpdateUtil;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.pojo.mongodb.ClassInfo;
import com.pojo.mongodb.Person;
import com.pojo.mongodb.Student;

public class SpringMongoTest {

	private AbstractRepository repository;
	private ClassPathXmlApplicationContext context;
	private BatchUpdateUtil batchUpdate;
	private MongoTemplate mongoTemplate;

	@Before
	public void before() {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		repository = (AbstractRepository) context.getBean("personRepository");
		batchUpdate = (BatchUpdateUtil) context.getBean("batchUpdate");
		mongoTemplate = (MongoTemplate) context.getBean("mongoTemplate");
	}

	@After
	public void after() {
		context.close();
	}

	@Test
	public void testInsert() {
		Person person = new Person("no13", 13, "traore", "dc");
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
	}

	@Test
	public void testFindAll() {
		List<Person> persons = repository.findAll();
		for (int i = 0; i < persons.size(); i++) {
			System.out.println(persons.get(i));
		}
		assertTrue(persons.size() > 0);
	}

	@Test
	public void testAggregation() {
		repository.aggregate();
	}

	@Test
	public void testBatchUpdate() {
		List<BatchUpdateOptions> list = new ArrayList<>();
		list.add(new BatchUpdateOptions(Query.query(Criteria.where("no").is(3)), Update.update("no", 23), true, true));
		list.add(new BatchUpdateOptions(Query.query(Criteria.where("_id").is("no16")), Update.update("name", "hammann"),
				true, true));
		int n = batchUpdate.batchUpdate(batchUpdate.getTemplate(), Person.class, list);
		assertTrue(n > 0);
		System.out.println("受影响行数:" + n);
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

	@Test
	public void testRefFind() {
		List<Student> students = mongoTemplate.find(
				Query.query(Criteria.where("classObj.$id").is(new ObjectId("5822cb1ede082a11609bf8cd"))),
				Student.class);
		for (Student s : students) {
			System.out.println(s.getStuName());
			System.out.println(s.getId());
			System.out.println(s.getClassObj().getClassName());
			System.out.println(s.getClassObj().getOpenDate());
		}
	}

	@Test
	public void testRefInsert() {
		ClassInfo classObj = new ClassInfo();
		classObj.setClassName("五年级二班");
		classObj.setOpenDate(new Date());
		mongoTemplate.save(classObj);

		Student student = new Student();
		student.setStuName("李学生");
		ClassInfo classObj2 = mongoTemplate.findOne(Query.query(Criteria.where("className").is("五年级二班")),
				ClassInfo.class);
		// System.out.println(classObj2.getClassName());
		student.setClassObj(classObj2);
		mongoTemplate.save(student);

		List<Student> students = classObj2.getStudents();
		if (students == null) {
			students = new ArrayList<>();
		}

		students.add(student);

		Student student2 = new Student();
		student2.setStuName("王学生");
		student2.setClassObj(classObj2);
		students.add(student2);

		classObj2.setStudents(students);

		mongoTemplate.save(student);
		mongoTemplate.save(student2);
		mongoTemplate.save(classObj2);

	}

}
