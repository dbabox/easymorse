package com.easymorse.ria.utils;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.core.hibernate.HibernateUtil;
import net.sf.gilead.gwt.GwtConfigurationHelper;
import net.sf.gilead.gwt.PersistentRemoteService;

public abstract class AbstractPersistentRemoteServiceServlet extends
		PersistentRemoteService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AbstractPersistentRemoteServiceServlet() {
		HibernateUtil hibernateUtil = new HibernateUtil(HibernateUtils
				.getSessionFactory());
		PersistentBeanManager persistentBeanManager = GwtConfigurationHelper
				.initGwtStatelessBeanManager(hibernateUtil);
		setBeanManager(persistentBeanManager);
	}

}
