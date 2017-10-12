package com.job;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Component("chunkListener")
public class MyChunkListener implements ChunkListener{

	@Override
	public void beforeChunk(ChunkContext context) {
		System.out.println("===============beforeChunk=====================");
	}

	@Override
	public void afterChunk(ChunkContext context) {
		System.out.println("===============afterChunk=====================");
	}

	@Override
	public void afterChunkError(ChunkContext context) {
		System.out.println("===============afterChunkError=====================");
	}

}
