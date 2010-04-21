package com.easymorse.rpc.client;

import com.easymorse.rpc.beans.User;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback);

	void helloUser(String input, AsyncCallback<User> callback);
}
