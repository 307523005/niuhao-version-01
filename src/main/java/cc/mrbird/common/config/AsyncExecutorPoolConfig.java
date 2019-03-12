package cc.mrbird.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 功能描述: 配置默认的线程池
 *
 * @auther: 钮豪
 * @date: 2018/11/1 17:58
 */

//@Configuration
public class AsyncExecutorPoolConfig implements AsyncConfigurer {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(10);
        //配置最大线程数
        executor.setMaxPoolSize(30);
        //配置队列大小
        executor.setQueueCapacity(100);
        //线程池维护线程所允许的空闲时间
        executor.setKeepAliveSeconds(300);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("-getAsyncExecutor-");
        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
        // CallerRunsPolicy：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }

    /**
     * 异步任务中异常处理
     *
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandler() {

            @Override
            public void handleUncaughtException(Throwable arg0, Method arg1, Object... arg2) {
                logger.error("====AsyncExecutorPoolConfig======" + arg0.getMessage() + "===========", arg0);
                logger.error("exception method:" + arg1.getName());
            }
        };
    }
}
/*
//第二种方式
@Configuration
public class AsyncExecutorPoolConfig extends AsyncConfigurerSupport {
    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(20);
        //配置最大线程数
        executor.setMaxPoolSize(30);
        //配置队列大小
        executor.setQueueCapacity(100);
        //线程池维护线程所允许的空闲时间
        executor.setKeepAliveSeconds(300);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("asyncTaskExecutor-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
*/