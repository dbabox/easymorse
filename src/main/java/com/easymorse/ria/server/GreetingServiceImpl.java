package com.easymorse.ria.server;

import org.hibernate.Session;

import com.easymorse.ria.beans.User;
import com.easymorse.ria.client.GreetingService;
import com.easymorse.ria.shared.FieldVerifier;
import com.easymorse.ria.utils.AbstractPersistentRemoteServiceServlet;
import com.easymorse.ria.utils.HibernateUtils;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends AbstractPersistentRemoteServiceServlet implements
		GreetingService {

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}
		
		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");
		return "Hello, " + input+"||"+this.createUser(input).getId() + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	@Override
	public User createUser(String userName) throws IllegalArgumentException {
		User user = new User();
		user.setName(userName);
		Session session = HibernateUtils.getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
		return (User) this.getBeanManager().clone(user);
	}
}
