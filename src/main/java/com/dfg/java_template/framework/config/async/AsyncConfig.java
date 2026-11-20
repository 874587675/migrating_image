package com.dfg.java_template.framework.config.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步配置类
 */

@Configuration
@EnableAsync
public class AsyncConfig {
    private final int CPU = Runtime.getRuntime().availableProcessors();

    /**
     * CPU密集型任务线程池
     * 计算密集型任务线程池（加密、图像处理、复杂算法）
     */
    @Bean(name = "cpuTaskExecutor")
    public ThreadPoolTaskExecutor cpuTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CPU + 1);  // 核心线程数
        executor.setMaxPoolSize(CPU * 2);   // 最大线程数
        executor.setQueueCapacity(200);     // 队列容量
        executor.setKeepAliveSeconds(30);   // 线程空闲时间
        executor.setThreadNamePrefix("cpu-task-"); // 线程前缀名
        // 拒绝策略：由调用者线程执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize(); // 初始化
        return executor;
    }


    /**
     * IO密集型任务线程池
     * I/O密集型任务线程池（文件读写、网络请求）
     */
    @Bean("ioTaskExecutor")
    public ThreadPoolTaskExecutor ioTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CPU * 4);
        executor.setMaxPoolSize(CPU * 8);
        executor.setQueueCapacity(1000);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("io-task-");
        // 拒绝策略：记录日志或抛出异常
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }

    /**
     * 混合型任务线程池
     */
    @Bean("mixedTaskExecutor")
    public ThreadPoolTaskExecutor mixedTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CPU * 2);
        executor.setMaxPoolSize(CPU * 4);
        executor.setQueueCapacity(500);
        executor.setKeepAliveSeconds(45);
        executor.setThreadNamePrefix("mixed-task-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
