package com.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.pojo.User;

public class JDK8Test {
	
	@Test
	public void testStreamForEachIterator(){
		List<String> title = Arrays.asList("Java8","In","Action");
		Stream<String> s = title.stream();
		s.forEach(System.out::println);
		//同一个流只能消费一次，消费2次会抛异常,需要iterator中重新获取
		//s.forEach(System.out::println);
	}
	
	@Test
	public void testStream(){
		User user1 = getUser(1L);
		User user2 = getUser(2L);
		User user3 = getUser(3L);
		User user4 = getUser(4L);
		User user5 = getUser(5L);
		User user6 = getUser(6L);
		List<User> users = new ArrayList<>();
		users.add(user1);
		users.add(user2);
		users.add(user3);
		users.add(user4);
		users.add(user5);
		users.add(user6);
		/**
		 * filter——接受Lambda，从流中排除某些元素。通过传递lambda d -> d.getId() > 2，选择出id>2。
		   map——接受一个Lambda，将元素转换成其他形式或提取信息。通过传递方法引用User::getUsername，提取userName。
		   limit——截断流，使其元素不超过给定数量。
		   collect——将流转换为其他形式。流被转换为一个列表。
		 */
		List<String> getThreeUserName = users.stream().filter(d -> d.getId() > 2)
				.map(User::getUsername).limit(3).collect(Collectors.toList());
		System.out.println(getThreeUserName);
	}
	
	@Test
	public void testStreamReduce(){
		User user1 = getUser(1L);
		User user2 = getUser(2L);
		User user3 = getUser(3L);
		User user4 = getUser(4L);
		User user5 = getUser(5L);
		User user6 = getUser(6L);
		List<User> users = new ArrayList<>();
		users.add(user1);
		users.add(user2);
		users.add(user3);
		users.add(user4);
		users.add(user5);
		users.add(user6);
		//查找list中id最大的一个
		System.out.println(users.stream().reduce(users.get(0),(a,b) -> a.getId()>=b.getId() ? a:b));
	}
	
	@Test
	public void testStreamUtils(){
		User user1 = getUser(1L);
		User user2 = getUser(2L);
		User user3 = getUser(3L);
		User user4 = getUser(4L);
		User user5 = getUser(5L);
		User user6 = getUser(6L);
		List<User> users = new ArrayList<>();
		users.add(user1);
		users.add(user2);
		users.add(user3);
		users.add(user4);
		users.add(user5);
		users.add(user6);
		//随机获取一个
		System.out.println(users.stream().findAny().get().getUsername());
		//总数
		System.out.println(users.stream().count());
		//跳过前3个
		System.out.println(users.stream().skip(3).collect(Collectors.toList()));
	}
	
	@Test
	public void testStreamMatch(){
		User user1 = getUser(1L);
		User user2 = getUser(2L);
		User user3 = getUser(3L);
		User user4 = getUser(4L);
		User user5 = getUser(5L);
		User user6 = getUser(6L);
		List<User> users = new ArrayList<>();
		users.add(user1);
		users.add(user2);
		users.add(user3);
		users.add(user4);
		users.add(user5);
		users.add(user6);
		//是否存在满足条件的元素
		boolean flag = users.stream().anyMatch(u -> u.getId()>3);
		System.out.println(flag);
		//是否都满足条件
		flag = users.stream().allMatch(u -> u.getId()>3);
		System.out.println(flag);
		//是否都不满足条件
		flag = users.stream().noneMatch(u -> u.getId()>3);
		System.out.println(flag);
	}
	
	public User getUser(Long id){
		User user = new User();
		user.setId(id);
		user.setUsername("test" + id);
		user.setPassword("password" + id);
		user.setLocked(false);
		user.setSalt("salt" + id);
		return user;
	}

}
