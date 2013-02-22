package com.l8smartlight.sdk;

import java.awt.Color;

import com.l8smartlight.sdk.core.ConnectionType;
import com.l8smartlight.sdk.core.L8Exception;
import com.l8smartlight.sdk.core.Sensor;

public interface L8 
{

	public ConnectionType getConnectionType();
	
	public void setMatrix(Color[][] colorMatrix) throws L8Exception;
		
	public void clearMatrix() throws L8Exception;
	
	public Color[][] readMatrix() throws L8Exception;
	
	public void setLED(int x, int y, Color color) throws L8Exception;
	
	public void clearLED(int x, int y) throws L8Exception;
	
	public Color readLED(int x, int y) throws L8Exception;
	
	public void setSuperLED(Color color) throws L8Exception;
	
	public void clearSuperLED() throws L8Exception;
	
	public Color readSuperLED() throws L8Exception;
	
	public void enableSensor(Sensor sensor) throws L8Exception;

	public float readSensor(Sensor sensor) throws L8Exception;
	
	public void disableSensor(Sensor sensor) throws L8Exception;
	
	public boolean isSensorEnabled(Sensor sensor) throws L8Exception;
	
	public boolean isBluetoothEnabled() throws L8Exception;
	
	public float getBatteryStatus() throws L8Exception;
	
}
