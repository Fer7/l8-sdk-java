package com.l8smartlight.sdk.impl;

import java.awt.Color;

import com.l8smartlight.sdk.L8;
import com.l8smartlight.sdk.core.L8Exception;
import com.l8smartlight.sdk.core.Sensor;

public class USBL8 implements L8 
{

	@Override
	public ConnectionType getConnectionType() 
	{
		return ConnectionType.USB;
	}	
	
	@Override
	public void setMatrix(Color[][] colorMatrix) throws L8Exception 
	{
		System.out.println("usb::setMatrix");
	}

	@Override
	public void clearMatrix() throws L8Exception 
	{
		System.out.println("usb::clearMatrix");
	}
	
	@Override
	public Color[][] readMatrix() throws L8Exception 
	{
		System.out.println("bluetooth::readMatrix");
		return null;
	}
	
	@Override
	public void setLED(int x, int y, Color color) 
	{
		System.out.println("usb::setLED");
	}
	
	@Override
	public void clearLED(int x, int y) throws L8Exception 
	{
		System.out.println("usb::clearLED");
	}
	
	@Override
	public Color readLED(int x, int y) throws L8Exception 
	{
		System.out.println("usb::readLED");
		return null;
	}
	
	@Override
	public void setSuperLED(Color color) throws L8Exception {
		System.out.println("bluetooth::setSuperLED");
	}
	
	@Override
	public void clearSuperLED() throws L8Exception {
		System.out.println("bluetooth::clearSuperLED");
	}		
	
	@Override
	public Color readSuperLED() throws L8Exception {
		System.out.println("bluetooth::readSuperLED");
		return null;
	}
	
	@Override
	public void enableSensor(Sensor sensor) throws L8Exception 
	{
		System.out.println("usb::enableSensor");
	}
	
	@Override	
	public void disableSensor(Sensor sensor) throws L8Exception
	{
		System.out.println("usb::disableSensor");
	}
	
	@Override	
	public Sensor.Status readSensor(Sensor sensor) throws L8Exception
	{
		System.out.println("usb::readSensor");
		return new Sensor.TemperatureStatus(0.0f, 0.0f);
	}

	@Override	
	public boolean isSensorEnabled(Sensor sensor) throws L8Exception
	{
		System.out.println("usb::isSensorEnabled");
		return true;
	}
	
	@Override	
	public boolean isBluetoothEnabled() throws L8Exception
	{
		System.out.println("usb::isBluetoothEnabled");
		return false;
	}
	
	@Override
	public int getBatteryStatus() throws L8Exception
	{
		System.out.println("usb::getBatteryStatus");
		return 0;
	}
	
	@Override
	public int getButton() throws L8Exception
	{
		System.out.println("usb::getButton");
		return 0;		
	}
	
	@Override
	public int getMemorySize() throws L8Exception
	{
		System.out.println("usb::getMemorySize");
		return 0;		
	}

	@Override
	public int getFreeMemory() throws L8Exception
	{
		System.out.println("usb::getFreeMemory");
		return 0;		
	}
	
	@Override
	public String getId() throws L8Exception
	{
		System.out.println("usb::getId");
		return null;
	}
	
	@Override
	public L8.Version getVersion() throws L8Exception
	{
		System.out.println("usb::getVersion");
		return null;
	}	
	
}
