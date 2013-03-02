package com.l8smartlight.sdk;

import java.awt.Color;

import com.l8smartlight.sdk.core.L8Exception;
import com.l8smartlight.sdk.core.Sensor;

public interface L8 
{
	
	public enum ConnectionType 
	{
		Bluetooth, USB, RESTful
	}	
	
	public ConnectionType getConnectionType();
	
	public static class Version
	{
		int hardware;
		int software;
		
		public int getHardware() 
		{
			return hardware;
		}
		
		public void setHardware(int hardware) 
		{
			this.hardware = hardware;
		}
		
		public int getSoftware() 
		{
			return software;
		}
		
		public void setSoftware(int software) 
		{
			this.software = software;
		}
		
		public Version(int hardware, int software) 
		{
			this.hardware = hardware;
			this.software = software;
		}
		
		public String toString()
		{
			return "{hardware: " + hardware + ", software: " + software + "}";
		}
	}	
	
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

	public Sensor.Status readSensor(Sensor sensor) throws L8Exception;
	
	public void disableSensor(Sensor sensor) throws L8Exception;
	
	public boolean isSensorEnabled(Sensor sensor) throws L8Exception;
	
	public boolean isBluetoothEnabled() throws L8Exception;
	
	public int getBatteryStatus() throws L8Exception;

	public int getButton() throws L8Exception;
	
	public int getMemorySize() throws L8Exception;

	public int getFreeMemory() throws L8Exception;
	
	public String getId() throws L8Exception;
	
	public L8.Version getVersion() throws L8Exception;
}
