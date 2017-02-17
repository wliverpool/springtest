package com.job;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class WriteTasklet implements Tasklet{
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 是Tasklet实现业务逻辑的地方。最终返回执行状态。
	 */
	@Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext context)throws Exception {
        System.out.println(message);
        return RepeatStatus.FINISHED;
    }

}
