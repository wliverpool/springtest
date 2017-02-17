package com.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBatchTest {

	private ClassPathXmlApplicationContext context;
	private JobLauncher jobLauncher;
	private Job helloJob;
	private Job csvJob;
	private Job xmlJob;
	private Job fileJob;
	private Job jdbcJob;

	@Before
	public void before() {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		helloJob = (Job) context.getBean("helloWorldJob");
		csvJob = (Job) context.getBean("csvJob");
		xmlJob = (Job) context.getBean("xmlFileReadAndWriterJob");
		fileJob = (Job) context.getBean("fixedLengthJob");
		jdbcJob = (Job) context.getBean("jdbcjob");
	}

	@After
	public void after() {
		context.close();
	}

	@Test
	public void testHelloJob() throws Exception {
		JobExecution result = jobLauncher.run(helloJob, new JobParameters());
		System.out.println(result.toString());
		assertEquals(result.getExitStatus().getExitCode(), ExitStatus.COMPLETED.getExitCode());
	}

	@Test
	public void testCsvJob() throws Exception {
		JobExecution result = jobLauncher.run(csvJob, new JobParameters());
		System.out.println(result.toString());
		assertEquals(result.getExitStatus().getExitCode(), ExitStatus.COMPLETED.getExitCode());
	}

	@Test
	public void testXMLJob() throws Exception {
		JobExecution result = jobLauncher.run(xmlJob,
				new JobParametersBuilder()
						.addString("inputFilePath",
								"/Users/mittermeyer/Documents/git/springtest/src/main/resources/inputXML.xml")
						.addString("outputFilePath", "/Users/mittermeyer/Documents/git/springtest/output.xml")
						.toJobParameters());
		// 运行结果输出
		System.out.println(result.toString());
		assertEquals(result.getExitStatus().getExitCode(), ExitStatus.COMPLETED.getExitCode());
	}

	@Test
	public void testFileJob() throws Exception {
		JobExecution result = jobLauncher.run(fileJob, new JobParametersBuilder()
				.addString("inputFilePath",
						"/Users/mittermeyer/Documents/git/springtest/src/main/resources/fixedLengthInputFile.txt")
				.addString("outputFilePath", "/Users/mittermeyer/Documents/git/springtest/fixedLengthOutputFile.txt")
				.toJobParameters());
		// 运行结果输出
		System.out.println(result.toString());
		assertEquals(result.getExitStatus().getExitCode(), ExitStatus.COMPLETED.getExitCode());
	}
	
	@Test
	public void testJdbcJob()throws Exception{
		JobExecution result = jobLauncher.run(jdbcJob, new JobParametersBuilder()
				.addLong("id",1001L)
				.toJobParameters());
		// 运行结果输出
		System.out.println(result.toString());
		assertEquals(result.getExitStatus().getExitCode(), ExitStatus.COMPLETED.getExitCode());
	}

}
