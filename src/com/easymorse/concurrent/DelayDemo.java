package com.easymorse.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DelayDemo {

	private int count;

	private ScheduledExecutorService executorService;

	public DelayDemo() {
		this.executorService = Executors.newScheduledThreadPool(5);
		this.executorService.scheduleWithFixedDelay(new Runnable() {

			@Override
			public void run() {
				int index = ++count;
				System.out.println(index + " - run task ...");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(index + " - task end.");
			}
		}, 1000, 1000, TimeUnit.MILLISECONDS);
	}

	public static void main(String[] args) {
		new DelayDemo();
	}
}
