package com.batch.springbatchexample.batch;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.file.FlatFileParseException;

public class DataSkipper implements SkipPolicy {

	@Override
	public boolean shouldSkip(Throwable exception, int skipCount) throws SkipLimitExceededException {
		if(exception instanceof FlatFileParseException && skipCount < 2) {
			return true;
		}
		return false;
	}

}
