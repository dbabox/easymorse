package com.easymorse.marshal;

import java.sql.Connection;
import java.sql.DriverManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hello world!
 * 
 */
public class App {

	public static boolean testConnection() {

		Connection connection;
		boolean returnValue = false;

		try {
			connection = DriverManager
					.getConnection("jdbc:derby:derbyDB;create=true");
			returnValue = connection != null;
			connection.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return returnValue;
	}

	public static boolean testHibernate() {
		boolean returnValue = false;
		Person person = new Person();
		person.setName("zhangsan");

		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();

		Session session = sessionFactory.openSession();
		session.getTransaction().begin();
		session.save(person);
		session.getTransaction().commit();
		session.close();

		sessionFactory.close();
		returnValue=person.getId()!=null;

		return returnValue;
	}

	public static void main(String[] args) {
		System.out.println("Hello World!");
	}
}
