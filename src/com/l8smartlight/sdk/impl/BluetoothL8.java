package com.l8smartlight.sdk.impl;

import java.awt.Color;

import com.l8smartlight.sdk.L8;
import com.l8smartlight.sdk.core.L8Exception;
import com.l8smartlight.sdk.core.Sensor;

public class BluetoothL8 implements L8 
{

	@Override
	public ConnectionType getConnectionType() 
	{
		return ConnectionType.Bluetooth;
	}
	
	@Override
	public void setMatrix(Color[][] colorMatrix) throws L8Exception 
	{
		System.out.println("bluetooth::setMatrix");
	}

	@Override
	public void clearMatrix() throws L8Exception 
	{
		System.out.println("bluetooth::clearMatrix");
	}
	
	@Override
	public Color[][] readMatrix() throws L8Exception 
	{
		System.out.println("bluetooth::readMatrix");
		return null;
	}
	
	@Override
	public void setLED(int x, int y, Color color) throws L8Exception 
	{
		System.out.println("bluetooth::setLED");
	}

	@Override
	public void clearLED(int x, int y) throws L8Exception 
	{
		System.out.println("bluetooth::clearLED");
	}

	@Override
	public Color readLED(int x, int y) throws L8Exception 
	{
		System.out.println("bluetooth::readLED");
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
		System.out.println("bluetooth::enableSensor");
	}
	
	@Override
	public void disableSensor(Sensor sensor) throws L8Exception
	{
		System.out.println("bluetooth::disableSensor");
	}
	
	@Override
	public Sensor.Status readSensor(Sensor sensor) throws L8Exception
	{
		System.out.println("bluetooth::readSensor");
		return new Sensor.TemperatureStatus(0.0f, 0.0f);
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
	public int getBatteryStatus() throws L8Exception
	{
		System.out.println("bluetooth::getBatteryStatus");
		return 0;
	}
	
	@Override
	public int getButton() throws L8Exception
	{
		System.out.println("bluetooth::getButton");
		return 0;		
	}
	
	@Override
	public int getMemorySize() throws L8Exception
	{
		System.out.println("bluetooth::getMemorySize");
		return 0;		
	}

	@Override
	public int getFreeMemory() throws L8Exception
	{
		System.out.println("bluetooth::getFreeMemory");
		return 0;		
	}
	
	@Override
	public String getId() throws L8Exception
	{
		System.out.println("bluetooth::getId");
		return null;
	}
	
	@Override
	public L8.Version getVersion() throws L8Exception
	{
		System.out.println("bluetooth::getVersion");
		return null;
	}
	
}
