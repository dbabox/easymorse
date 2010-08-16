package com.easymorse.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(2);

		executorService.submit(new Runnable() {
			@Override
			public void run() {
				doSomething(1);
			}
		});

		executorService.submit(new Runnable() {
			@Override
			public void run() {
				doSomething(2);
			}
		});

		executorService.submit(new Runnable() {
			@Override
			public void run() {
				doSomething(3);
			}
		});

		executorService.shutdown();
		System.out.println(">>main thread end.");

	}

	private static void doSomething(int id) {
		System.out.println("start do " + id + " task ...");
		try {
			Thread.sleep(1000 * 2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("start do " + id + " finished.");
	}

}
