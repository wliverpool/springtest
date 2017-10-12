package com.job;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component("stepExecutionListener")
public class MyStepExecutionListener implements StepExecutionListener {

	@Override
	public void beforeStep(StepExecution stepExecution) {
		System.out.println("===============beforeStep=====================");
		JobParameters parameters = stepExecution.getJobExecution().getJobParameters();
		String paramId = parameters.getString("paramId");
		System.out.println("===============paramId:" + paramId + "=====================");
		//step变量
		stepExecution.getExecutionContext().put("test", "test");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println("===============afterStep=====================");
		String test = stepExecution.getExecutionContext().getString("test");
		System.out.println("===============test:" + test + "=====================");
		return ExitStatus.COMPLETED;
	}
	
	

}
