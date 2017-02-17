package com.job;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.pojo.StudentPojo;

/**
 * 业务处理类。
 * 
 * @author 吴福明
 */
@Component("fixedLengthProcessor")
public class FixedLengthProcessor implements ItemProcessor<StudentPojo, StudentPojo> {

	/**
	 * 对取到的数据进行简单的处理。
	 * 
	 * @param student
	 *            处理前的数据。
	 * @return 处理后的数据。
	 * @exception Exception
	 *                处理是发生的任何异常。
	 */
	public StudentPojo process(StudentPojo student) throws Exception {
		/* 合并ID和名字 */
		student.setName(student.getID() + "--" + student.getName());
		/* 年龄加2 */
		student.setAge(student.getAge() + 2);
		/* 分数加10 */
		student.setScore(student.getScore() + 10);
		/* 将处理后的结果传递给writer */
		return student;
	}

}