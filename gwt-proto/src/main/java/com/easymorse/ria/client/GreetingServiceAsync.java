package com.easymorse.ria.client;

import com.easymorse.ria.beans.User;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void createUser(String userName, AsyncCallback<User> callback);
}
