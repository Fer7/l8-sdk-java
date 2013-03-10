package com.l8smartlight.sdk;

import java.util.ArrayList;
import java.util.List;

import com.l8smartlight.sdk.core.L8Exception;
import com.l8smartlight.sdk.impl.RESTfulL8;

public class L8Manager 
{
	public static List<L8> discoverL8s() throws L8Exception
	{
		List<L8> foundL8s = new ArrayList<L8>();

		// 1. Look for L8s connected via USB port
		// 2. Look for L8s connected via bluetooth
		// 3. If no L8s found, emulate a device with the RESTFul API.
		if (foundL8s.size() == 0) {
			foundL8s.add(L8Manager.createEmulatedL8());
		}
		return foundL8s;
	}
	
	public static L8 createEmulatedL8() throws L8Exception 
	{
		return new RESTfulL8();
	}
	
}
