package com.l8smartlight.sdk.core;

public class Sensor 
{
	public static Sensor PROXIMITY = new Sensor("proximity_sensor");
	public static Sensor TEMPERATURE = new Sensor("temperature_sensor");  
	public static Sensor NOISE = new Sensor("noise_sensor");  
	public static Sensor AMBIENTLIGHT = new Sensor("ambientlight_sensor");  
	public static Sensor ACCELERATION = new Sensor("acceleration_sensor");  
	
	protected String name;
	protected String value;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Sensor(String name) {
		this.name = name;
	}
}
