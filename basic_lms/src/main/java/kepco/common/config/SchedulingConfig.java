package kepco.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
@ConditionalOnProperty(prefix = "global.scheduling", name="enable", havingValue="true", matchIfMissing = true)
public class SchedulingConfig implements SchedulingConfigurer {
	
	@Value("${spring.task.scheduling.pool.size:1}")
	private int poolSize;
	
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();

        scheduler.setPoolSize(poolSize);
        scheduler.setThreadNamePrefix("scheduled-task-");
        scheduler.setDaemon(true);
        scheduler.initialize();

        taskRegistrar.setTaskScheduler(scheduler);
	}

}
