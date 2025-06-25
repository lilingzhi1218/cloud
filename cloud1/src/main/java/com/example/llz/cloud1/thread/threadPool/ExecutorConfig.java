package com.example.llz.cloud1.thread.threadPool;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ExecutorConfig {

    public Executor taskExecutor(){
        ThreadPoolTaskExecutor e = new ThreadPoolTaskExecutor();
        e.setCorePoolSize(5);//如果是CPU密集型，建议是核心数+1；如果是IO密集型尽可能多一点，可以使核心数*2
        e.setMaxPoolSize(10);//如果是CPU密集型，略微调高一点，如corePollSize*2，但通常没有明显提升；如果是IO密集型尽可能多一点，corePollSize*10，甚至更高，具体看IO等待时间
        e.setQueueCapacity(1000);//队列数
        e.setKeepAliveSeconds(1000);//线程空闲时间
        e.setAllowCoreThreadTimeOut(true);//允许超时时间作用与核心线程
        e.setThreadNamePrefix("task-async");//线程前缀名
        e.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());//配置拒绝策略
        /**
         * AbortPolicy‌（直接抛异常）：这是默认的拒绝策略。当任务无法被线程池执行时，会抛出RejectedExecutionException异常。
         *              这种策略适用于一些比较重要的业务场景，因为抛出异常可以让开发者及时发现并处理。
         * CallerRunsPolicy‌（调用者执行）：当任务无法被线程池执行时，会直接在调用者线程中运行这个任务。
         *              如果调用者线程正在执行一个任务，则会创建一个新线程来执行被拒绝的任务。
         *              这种策略适用于那些不希望任务被丢弃，但又不想让整个系统崩溃的场景。
         * DiscardPolicy‌（直接丢弃任务）：当任务无法被线程池执行时，任务将被丢弃，不抛出异常，也不执行任务。
         *              这种策略适用于一些不重要的业务场景，如统计一些无关紧要的数据。
         * DiscardOldestPolicy‌（丢弃列队中最早的任务）：当任务无法被线程池执行时，线程池会丢弃队列中最旧的任务，然后尝试再次提交当前任务。
         *              这种策略适用于那些可以容忍丢弃最早进入队列的任务的场景。
         */
        return e;
    }
}
