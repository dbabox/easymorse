package com.easymorse;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadDemo2 {
	public static void main(String[] args) {
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
		new Thread(new MyProducer(queue)).start();
		new Thread(new MyConsumer(queue)).start();
	}
}

class MyProducer implements Runnable {

	private static int count;

	private BlockingQueue<String> queue;

	public MyProducer(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				this.queue.put("s" + count++);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			try {
				Thread.sleep(1000 * 2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

class MyConsumer implements Runnable {

	private BlockingQueue<String> queue;

	public MyConsumer(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("c ->" + queue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
