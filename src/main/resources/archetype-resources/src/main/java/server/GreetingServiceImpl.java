#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.server;

import org.hibernate.Session;

import ${package}.beans.User;
import ${package}.client.GreetingService;
import ${package}.shared.FieldVerifier;
import ${package}.utils.AbstractPersistentRemoteServiceServlet;
import ${package}.utils.HibernateUtils;

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
