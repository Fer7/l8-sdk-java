package com.l8smartlight.sdk.impl;

import java.awt.Color;

import com.l8smartlight.sdk.L8;
import com.l8smartlight.sdk.core.ConnectionType;
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
		System.out.println("bluetooth::disableSensor");
	}
	
	@Override	
	public float readSensor(Sensor sensor) throws L8Exception
	{
		System.out.println("bluetooth::readSensor");
		return 0.0f;
	}

	@Override	
	public boolean isSensorEnabled(Sensor sensor) throws L8Exception
	{
		System.out.println("bluetooth::isSensorEnabled");
		return true;
	}
	
	@Override	
	public boolean isBluetoothEnabled() throws L8Exception
	{
		System.out.println("bluetooth::isBluetoothEnabled");
		return false;
	}
	
	@Override	
	public float getBatteryStatus() throws L8Exception
	{
		System.out.println("bluetooth::getBatteryStatus");
		return 0.0f;
	}
	
}