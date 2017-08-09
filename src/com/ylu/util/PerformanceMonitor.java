package com.ylu.util;

import com.ylu.douyuFormat.Logger;

public class PerformanceMonitor {
	
	int recvMessageTimes;
	int messageCount;
	
	
	private PerformanceMonitor(){
		
	}
	
	public static PerformanceMonitor getInstance(){
		return Holder.monitor;
	}
	
	public static class Holder{
		static PerformanceMonitor monitor = new PerformanceMonitor();
	}
	
	public synchronized void recv(){
		recvMessageTimes ++;
	}
	
	public synchronized void msg(int n){
		messageCount += n;
	}
	
	public synchronized void report(){
		Logger.v("************************************\n,Received %d messages \n, %d times read\n, %d msgs per time",messageCount,recvMessageTimes,messageCount/recvMessageTimes);
	}
}
