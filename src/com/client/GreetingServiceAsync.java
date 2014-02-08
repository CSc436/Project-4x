package com.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.shared.Request;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(Request input, AsyncCallback<Request[]> callback)
			throws IllegalArgumentException;
}
