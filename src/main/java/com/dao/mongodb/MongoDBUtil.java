package com.dao.mongodb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.pojo.mongodb.User;

public final class MongoDBUtil {
	private static MongoClient mongoClient = null;
	private static MongoDatabase database = null;
	private static ServerAddress serverAddress = null;
	private static MongoCredential credentials = null;
	private static List<ServerAddress> addressLists = new ArrayList<ServerAddress>();
	private static List<MongoCredential> credentialsLists = new ArrayList<MongoCredential>();

	public static void init() {
		try {
			
			// mongoClient = new MongoClient("192.168.32.129",27017);
			serverAddress = new ServerAddress("192.168.122.130", 27017);
			addressLists.add(serverAddress);
			// credentials = MongoCredential.createCredential("test1", "test",
			// "test1".toCharArray());
			credentials = MongoCredential.createCredential("root", "admin", "root".toCharArray());//MongoCredential.createMongoCRCredential("root", "admin", "root".toCharArray());
			credentialsLists.add(credentials);
			mongoClient = new MongoClient(addressLists, credentialsLists);
		} catch (MongoException e) {
			System.out.println(e.toString());
		}
		if (null != mongoClient) {
			database = mongoClient.getDatabase("liverpool");
		}

	}

	public static MongoClient getMongoClient() {
		if (null == mongoClient) {
			init();
		}

		return mongoClient;
	}

	/**
	 * 获取database
	 * 
	 * @return
	 */
	public static MongoDatabase getDatabase() {
		if (null == mongoClient) {
			init();
		}
		return database;
	}

	/**
	 * 获取User Collection
	 * 
	 * @return
	 */
	public static MongoCollection<Document> getUserCollection() {
		if (null == database) {
			database = getDatabase();
		}
		if (null != database) {
			return database.getCollection("data");
		}
		return null;
	}

	/**
	 * 删除所有用户
	 */
	public static void deleteAllUsers() {
		System.out.println("删除User Collection中所有数据...");
		MongoCollection<Document> collection = getUserCollection();

		FindIterable<Document> cursor = collection.find();
		while (cursor.iterator().hasNext()) {
			collection.deleteOne(cursor.iterator().next());
		}
		System.out.println("====================================");
	}

	/**
	 * 查询所有用户
	 */
	public static void queryAllUsers() {
		System.out.println("查询User Collection中所有数据：");

		MongoCollection<Document> collection = getUserCollection();

		// 方法一
		/*
		 * MongoCursor<Document> cur = collection.find().iterator(); try{ while
		 * (cur.hasNext()) { System.out.println(cur.next().toJson()); }
		 * }catch(Exception e){ System.out.println(e.getMessage()+e); }finally {
		 * cur.close(); }
		 */
		// 方法二
		for (Document cursor : collection.find()) {
			System.out.println(cursor.toJson());
		}

		System.out.println("================================================================");
	}

	/**
	 * 保存用户信息
	 * 
	 * @param user
	 */
	public static void saveUser(User user) {
		System.out.println("保存用户信息：" + user.toString());
		MongoCollection<Document> userCollection = getUserCollection();
		userCollection.insertOne(user.toBasicDBObject());
		System.out.println("================================================================");

	}

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 */
	public static void update(User user) {
		System.out.println("更新用户信息：" + user.toString());
		MongoCollection<Document> userCollection = getUserCollection();
		userCollection.updateMany(new Document().append("name", user.getName()), user.toBasicDBObject());
		System.out.println("================================================================");
	}

	/**
	 * 增加用户更新次数
	 * 
	 * @param userName
	 *            用户名
	 */
	public static void incUserUpdateTimes(String userName) {
		MongoCollection<Document> userCollection = getUserCollection();
		BasicDBObject newDocument = new BasicDBObject().append("$inc", new BasicDBObject().append("updateTimes", 1));
		userCollection.updateOne(new BasicDBObject().append("name", userName), newDocument);
	}

	/**
	 * 更新用户名
	 * 
	 * @param oldName
	 *            旧用户名
	 * @param newName
	 *            新用户名
	 */
	public static void updateUserName(String oldName, String newName) {
		MongoCollection<Document> userCollection = getUserCollection();
		BasicDBObject newDocument = new BasicDBObject().append("$set", new BasicDBObject().append("name", newName));
		userCollection.updateOne(new BasicDBObject().append("name", oldName), newDocument);
	}

	/**
	 * 更新User Age
	 * 
	 * @param userName
	 *            用户名
	 * @param age
	 *            Age
	 */
	public static void updateUserAge(String userName, int age) {
		MongoCollection<Document> userCollection = getUserCollection();
		BasicDBObject newDocument = new BasicDBObject().append("$set", new BasicDBObject().append("age", age));
		userCollection.updateOne(new BasicDBObject().append("name", userName), newDocument);
	}

	public static User queryUserByName(String userName) {
		MongoCollection<Document> userCollection = getUserCollection();
		BasicDBObject query = new BasicDBObject();
		query.put("name", userName);
		User user = null;
		for (Document cursor : userCollection.find(query)) {
			System.out.println(cursor.toJson());
			Map<String, Object> userMap = (Map<String, Object>) cursor;

			user = new User();
			user.setName(String.valueOf(userMap.get("name")));
			user.setAge(Integer.valueOf(String.valueOf(userMap.get("age"))));
			user.setUpdateTimes(Integer.valueOf(String.valueOf(userMap.get("updateTimes"))));

			System.out.println(user.toString());
		}
		/*
		 * MongoCursor<Document> cursor = userCollection.find(query).iterator();
		 * while(cursor.hasNext()) { Document document = cursor.next();
		 * 
		 * Map<String, Object> userMap = (Map<String, Object>) document;
		 * 
		 * user = new User(); user.setName(String.valueOf(userMap.get("name")));
		 * user.setAge(Integer.valueOf(String.valueOf(userMap.get("age"))));
		 * user.setUpdateTimes(Integer.valueOf(String.valueOf(userMap.get(
		 * "updateTimes"))));
		 * 
		 * System.out.println(user.toString()); }
		 */

		return user;
	}

	public static void queryUserByAge(List<Integer> ageList) {
		MongoCollection<Document> userCollection = getUserCollection();
		BasicDBObject query = new BasicDBObject();
		query.put("age", new BasicDBObject("$in", ageList));
		FindIterable<Document> cursor = userCollection.find(query);
		while (cursor.iterator().hasNext()) {
			System.out.println(cursor.iterator().next());
		}
	}

	public static void queryUserByGreatThanAge(int age) {
		MongoCollection<Document> userCollection = getUserCollection();
		BasicDBObject query = new BasicDBObject();
		query.put("age", new BasicDBObject("$gt", age));
		FindIterable<Document> cursor = userCollection.find(query);
		while (cursor.iterator().hasNext()) {
			System.out.println(cursor.iterator().next());
		}
	}

	public static void queryUserByLessThanAge(int age) {
		MongoCollection<Document> userCollection = getUserCollection();
		BasicDBObject query = new BasicDBObject();
		query.put("age", new BasicDBObject("$lt", age));
		FindIterable<Document> cursor = userCollection.find(query);
		while (cursor.iterator().hasNext()) {
			System.out.println(cursor.iterator().next());
		}
	}

	public static void queryUserNotEquireAge(int age) {
		MongoCollection<Document> userCollection = getUserCollection();
		BasicDBObject query = new BasicDBObject();
		query.put("age", new BasicDBObject("$ne", age));
		FindIterable<Document> cursor = userCollection.find(query);
		while (cursor.iterator().hasNext()) {
			System.out.println(cursor.iterator().next());
		}
	}

	public static void deleteUserByName(String userName) {
		MongoCollection<Document> userCollection = getUserCollection();
		BasicDBObject document = new BasicDBObject();
		document.put("name", userName);
		userCollection.deleteMany(document);
	}

	public static void deleteUserByNameList(List<String> nameList) {
		MongoCollection<Document> userCollection = getUserCollection();
		BasicDBObject query = new BasicDBObject();
		query.put("name", new BasicDBObject("$in", nameList));
		userCollection.deleteMany(query);
	}
	
	public static MongoCursor<Document> groupUser(){
		MongoCollection<Document> userCollection = getUserCollection();
		List<BasicDBObject> all = new ArrayList<BasicDBObject>();
		//根据age字段求count以及平均值
		BasicDBObject cond = new BasicDBObject("$group",new BasicDBObject("_id","$age").append("count", new BasicDBObject("$sum",1)).append("avg", new BasicDBObject("$avg","$age")));
		all.add(cond);
		return userCollection.aggregate(all).iterator();
	}

	public static void setDatabase(MongoDatabase database) {
		MongoDBUtil.database = database;
	}

}