package com.test;

import java.util.regex.Pattern;

import org.bson.Document;
import org.junit.Test;

import com.dao.mongodb.MongoDBUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.pojo.mongodb.User;

public class MongoDBTest {

	public static void main(String[] args) {
		//mongodb://root:root@192.168.122.130:27017/admin
		MongoClientURI uri = new MongoClientURI("mongodb://192.168.3.12:40000/admin");
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase("liverpool");
		MongoCollection<Document> col = db.getCollection("person");
		BasicDBObject cond = new BasicDBObject();
		//分页
		cond.put("no", new BasicDBObject("$gt", 1).append("$lte",23));
		MongoCursor<Document> cursor = col.find(cond).skip(0).limit(10).iterator();
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
		System.out.println("=====================================================================");
		Pattern p = Pattern.compile("8");
		cond = new BasicDBObject();
		cond.put("name", new BasicDBObject("$regex",p));
		cursor = col.find(cond).iterator();
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
		client.close();

	}

	/*@Test
	public void addUsers() {
		for (int i = 0; i < 20000; i++) {
			User user = new User();
			user.setName("user" + i);
			user.setAge(2);
			user.setUpdateTimes(0);
			// 保存用户信息
			MongoDBUtil.saveUser(user);
			System.out.println("------------" + i);
		}
	}

	@Test
	public void deleteAllUsers() {
		MongoDBUtil.deleteAllUsers();
	}

	@Test
	public void queryAllUsers() {
		MongoDBUtil.queryAllUsers();
	}

	@Test
	public void updateUser() {
		User user = new User();
		user.setName("user19999");
		user.setAge(39);
		user.setUpdateTimes(3);
		MongoDBUtil.update(user);
	}

	@Test
	public void queryUserByName() {
		MongoDBUtil.queryUserByName("user19999");
	}*/
	
	@Test
	public void groupUser(){
		MongoCursor<Document> cursor = MongoDBUtil.groupUser();
		while (cursor.hasNext()) {
			Document doc  = cursor.next();
			System.out.println(doc.getDouble("_id")+","+doc.getInteger("count")+","+doc.getDouble("name"));
		}
	}

}
