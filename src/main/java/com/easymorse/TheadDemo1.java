package com.easymorse;

import java.util.ArrayList;
import java.util.List;

class Consumer implements Runnable {

	private static int consumerCount;

	private List<String> contents;

	private int number;

	public Consumer(List<String> contents) {
		this.contents = contents;
		++consumerCount;
		number = consumerCount;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (contents) {
				if (contents.isEmpty()) {
					try {
						contents.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("c" + number + " -> "
							+ contents.remove(0));
				}
			}
		}
	}
}

class Producer implements Runnable {

	private static int count;

	private static int producerCount;

	private List<String> contents;

	private int number;

	public Producer(List<String> contents) {
		this.contents = contents;
		++producerCount;
		this.number=producerCount;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (contents) {
				String s="s" + (count++);
				this.contents.add(s);
				System.out.println("p" + number + " -> " + s);
				this.contents.notifyAll();
			}
			try {
				Thread.sleep(1000 * 2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

/**
 * Thead demo1
 * 
 */
public class TheadDemo1 {

	public static void main(String[] args) {
		List<String> contents = new ArrayList<String>();
		new Thread(new Consumer(contents)).start();
		new Thread(new Consumer(contents)).start();
		new Thread(new Producer(contents)).start();
		new Thread(new Producer(contents)).start();
	}
}
