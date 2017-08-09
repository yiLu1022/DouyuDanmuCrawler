package com.ylu.douyuDanmu;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadDispatcher {
	private final Executor executor;
	private static final int CORE_POOL_SIZE = 10;
	private static final int MAX_POOL_SIZE = 20;
	private static final int KEEP_ALIVE_TIME = 5;
	private static final TimeUnit unit = TimeUnit.SECONDS;
	private static final RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.AbortPolicy();
	
	public ThreadDispatcher(){
		executor = new ThreadPoolExecutor(CORE_POOL_SIZE,
				MAX_POOL_SIZE,
				KEEP_ALIVE_TIME,
				unit,
				new ArrayBlockingQueue<Runnable>(3),HANDLER ); 
	}
	
	public void sync(final Runnable runnable){
		
		final Lock lock = new ReentrantLock();
		final Condition doneCondition = lock.newCondition();
		final boolean[] done = {false};
		async(new Runnable(){
			
			public void run() {
				
				runnable.run();
				
				lock.lock();
				done[0] = true;
				doneCondition.signal();
				lock.unlock();
				
			}
		});
		lock.lock();
		while(!done[0]){
			try {
				doneCondition.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		lock.unlock();
		
	}
	
	public void async(Runnable runnable){
		executor.execute(runnable);
	}
	
	
}
