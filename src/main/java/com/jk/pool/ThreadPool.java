package com.jk.pool;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

	public static void execute(Runnable command) {
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(55);
		fixedThreadPool.execute(command);
	}
	private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(55,55, 300, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());  
	public static ThreadPoolExecutor execute2(Runnable runnable){
		  System.out.println("==================start==================");  
	        Random rm = new Random();  
	        executor.execute(runnable);  
	        executor.shutdown();//只是不能再提交新任务，等待执行的任务不受影响  
	        System.out.println("==================end====================");  
	        return executor;
	}
	
}
