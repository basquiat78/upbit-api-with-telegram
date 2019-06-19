package io.basquiat.schedule.config;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 
 * ThreadPoolTaskScheduler Setup
 * 
 * created by basquiat
 *
 */
@Configuration
public class ScheduleConfiguration {

	@Value("${thread.pool.size}")
	private int THREAD_POOL_SIZE;
	
	@Bean
	public ThreadPoolTaskScheduler schedulerExecutor() {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(THREAD_POOL_SIZE);
		taskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
		return taskScheduler;
	}
	
}
