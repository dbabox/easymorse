package com.easymorse.rpc.server;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.core.hibernate.HibernateUtil;
import net.sf.gilead.gwt.GwtConfigurationHelper;
import net.sf.gilead.gwt.PersistentRemoteService;

import org.hibernate.Session;

import com.easymorse.rpc.beans.User;
import com.easymorse.rpc.client.GreetingService;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends PersistentRemoteService implements
		GreetingService {

	public GreetingServiceImpl() {
		HibernateUtil hibernateUtil = new HibernateUtil(MyHibernateUtil
				.getSessionFactory());
		PersistentBeanManager persistentBeanManager = GwtConfigurationHelper
				.initGwtStatelessBeanManager(hibernateUtil);
		setBeanManager(persistentBeanManager);
	}

	public String greetServer(String input) {
		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");
		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	@Override
	public User helloUser(String userName) {
		User user = new User();
		user.setName(userName);
		Session session = MyHibernateUtil.getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
		return (User) this.getBeanManager().clone(user);
	}
}
