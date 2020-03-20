package com.batch.springbatchexample.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.batch.springbatchexample.batch.DataSkipper;
import com.batch.springbatchexample.model.User;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	@Autowired
	JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public SkipPolicy skipPolicy() {
		return new DataSkipper();
	}
	
	@Bean
	public Job job(
			ItemReader<User> itemReader,
			ItemProcessor<User, User> itemProcessor,
			ItemWriter<User> itemWriter) {
		
		Step step = stepBuilderFactory.get("loadFile")
				.<User, User>chunk(100)
				.reader(itemReader).faultTolerant().skipPolicy(skipPolicy())
				.processor(itemProcessor)
				.writer(itemWriter)
				.build();
		
		return jobBuilderFactory.get("load")
				.incrementer(new RunIdIncrementer())
				.flow(step)
				.end()
				.build();
	}
	
	@Bean
	public FlatFileItemReader<User> itemReader(){
	
		FlatFileItemReader<User> fileItemReader = new FlatFileItemReader<User>();
		fileItemReader.setResource(new ClassPathResource("users.csv"));
		fileItemReader.setName("CSV Reader");
		fileItemReader.setLinesToSkip(1);
		fileItemReader.setLineMapper(lineMapper());
		return fileItemReader;
	}

	@Bean
	public LineMapper<User> lineMapper() {
		
		DefaultLineMapper<User> defaultLineMapper = new DefaultLineMapper<User>();
		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		delimitedLineTokenizer.setNames(new String[] {"id", "name", "salary"});
		
		BeanWrapperFieldSetMapper<User> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<User>();
		beanWrapperFieldSetMapper.setTargetType(User.class);
		
		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
		defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
		
		return defaultLineMapper;
	}

}
