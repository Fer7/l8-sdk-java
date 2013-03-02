package com.l8smartlight.sdk;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.l8smartlight.sdk.core.L8Exception;
import com.l8smartlight.sdk.core.Sensor;
import com.l8smartlight.sdk.impl.RESTfulL8;

public class L8Manager 
{
	private static Logger logger = LogManager.getLogger(L8Manager.class.getName());	
	
	public static List<L8> discoverL8s() 
	{
		List<L8> foundL8s = new ArrayList<>();

		// TODO: Busca en las proximidades todos los L8s disponibles, sean del tipo que sean.
		// 1. Primero por USB.
		// 2. Por Bluetooth.
		
		/*
		// TODO: Algún parámetro de configuración debe definir si se permite o no devolver uno simulado.
		boolean allowEmulation = true;
		if (allowEmulation && foundL8s.size() == 0) {
			foundL8s.add(L8Manager.createEmulatedL8());
		}
		*/
		
		return foundL8s;
	}
	
	public static L8 createEmulatedL8() throws L8Exception 
	{
		return new RESTfulL8();
	}
	
	public static void main(String[] args) 
	{
		try {
			
			L8 l8 = L8Manager.createEmulatedL8();
			l8.clearMatrix();
			l8.setLED(0, 3, Color.CYAN);
			
			l8.readLED(0, 3);
			
			l8.clearLED(0, 3);
			
			Color[][] matrix = new Color[8][8];
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[i].length; j++) {
					matrix[i][j] = Color.RED;
				}
			}
			l8.setMatrix(matrix);
			
			l8.readMatrix();
			
			l8.setSuperLED(Color.BLUE);
			
			l8.readSuperLED();
			
			l8.clearSuperLED();
			
			l8.enableSensor(Sensor.AMBIENTLIGHT);

			l8.disableSensor(Sensor.PROXIMITY);

			Sensor.TemperatureStatus temperature = (Sensor.TemperatureStatus)l8.readSensor(Sensor.TEMPERATURE);
			L8Manager.logger.info("read temperature sensor: " + temperature);
			
			Sensor.ProximityStatus proximity = (Sensor.ProximityStatus)l8.readSensor(Sensor.PROXIMITY);
			L8Manager.logger.info("read proximity sensor: " + proximity);

			Sensor.AccelerationStatus acceleration = (Sensor.AccelerationStatus)l8.readSensor(Sensor.ACCELERATION);
			L8Manager.logger.info("read acceleration sensor: " + acceleration);			
			
			L8Manager.logger.info("bluetooth enabled? " + l8.isBluetoothEnabled());
			
			L8Manager.logger.info("battery status: " + l8.getBatteryStatus());
			
			l8.setLED(4, 5, Color.LIGHT_GRAY);
			l8.setLED(7, 6, Color.BLUE);
			l8.setLED(1, 3, Color.GREEN);
			
			l8.getButton();
			
			l8.getMemorySize();
			
			l8.getFreeMemory();
			
			l8.getVersion();
			
			L8Manager.logger.info("id: " + l8.getId());
			
		} catch (L8Exception e) {
			e.printStackTrace();
		}
	}
	
}
