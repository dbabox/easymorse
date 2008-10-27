package com.easymorse.marshal;


public class HelloWorldServiceImpl implements HelloWorldService {

	public HelloWorldServiceImpl() {
		System.out.println("-------------->new HelloWorldService start");
	}

	// @Override
	public void sayHello() {
		System.out.println("Hello!!!!!!!!!!!!!!!!");
	}
}
