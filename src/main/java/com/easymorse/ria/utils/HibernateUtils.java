package com.easymorse.ria.utils;

import org.apache.log4j.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtils {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HibernateUtils.class);

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			return new AnnotationConfiguration().configure()
					.buildSessionFactory();
		} catch (Throwable ex) {
			logger
					.error(
							"buildSessionFactory() - Initial SessionFactory creation failed." + ex, ex); //$NON-NLS-1$
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
