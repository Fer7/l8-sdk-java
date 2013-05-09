package com.l8smartlight.sdk.core;

import java.util.List;

public interface L8Manager {

	public List<L8> discoverL8s() throws L8Exception;
	
}
