package com.test;

import java.util.Date;

import com.util.DateTime;

public class TestDateTime {
	
	public static void main(String[] args) {
		
		Date randomDate=DateTime.randomDate("2010-09-20","2010-09-21"); 
		
		System.out.println(randomDate);
		
	}

}
