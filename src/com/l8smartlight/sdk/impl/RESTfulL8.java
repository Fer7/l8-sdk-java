package com.l8smartlight.sdk.impl;

import java.awt.Color;

import org.json.simple.JSONObject;

import com.l8smartlight.sdk.L8;
import com.l8smartlight.sdk.core.ConnectionType;
import com.l8smartlight.sdk.core.L8Exception;
import com.l8smartlight.sdk.core.Sensor;

import es.develappers.rest.RESTfulClient;
import es.develappers.rest.Response;

public class RESTfulL8 implements L8 
{
	
	private final String SIMULAT8R_BASE_URL = "http://localhost:8888/l8-server-simulator";
	private RESTfulClient client = null;
	private String simulat8rToken = null;
	
	protected String printColor(Color color) 
	{
		String alphaRedGreenBlue = Integer.toHexString(color.getRGB());
		String redGreenBlue = alphaRedGreenBlue.substring(2);
		return "#" + redGreenBlue; 
	}
	
	protected Color readColor(String color) 
	{
		Integer red = Integer.valueOf(color.substring(1, 2), 16);
		Integer green = Integer.valueOf(color.substring(3, 5), 16);
		Integer blue = Integer.valueOf(color.substring(5), 16);
		return new Color(red, green, blue);
	}
	
	public RESTfulL8() throws L8Exception 
	{
		try {
			this.client = new RESTfulClient(SIMULAT8R_BASE_URL);
			this.createSimulator();
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	protected void createSimulator() throws L8Exception 
	{
		try {
			Response response = this.client.post("/l8s");
			if (response.getCode() == 201) {
				JSONObject json = (JSONObject)response.getJSON();
				this.simulat8rToken = (String)json.get("id");
			} else {
				throw new L8Exception("Error creating a new simulat8r");
			}		
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	@Override
	public ConnectionType getConnectionType() 
	{
		return ConnectionType.RESTful;
	}	
	
	@Override
	@SuppressWarnings("unchecked")
	public void setMatrix(Color[][] colorMatrix) throws L8Exception 
	{
		try {
			JSONObject message = new JSONObject();
			for (int i = 0; i< colorMatrix.length; i++) {
				for (int j = 0; j < colorMatrix.length; j++) {
					message.put("led" + i + j, printColor(colorMatrix[i][j]));
				}
			}
			Response response = this.client.put("/l8s/" + this.simulat8rToken, message);
			if (response.getCode() != 200) {
				throw new L8Exception("Error updating simulat8r matrix");
			}		
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void clearMatrix() throws L8Exception 
	{
		try {
			Color[][] matrix = new Color[8][8];
			JSONObject message = new JSONObject();
			for (int i = 0; i< matrix.length; i++) {
				for (int j = 0; j < matrix.length; j++) {
					message.put("led" + i + j, printColor(Color.BLACK));
				}
			}
			Response response = this.client.put("/l8s/" + this.simulat8rToken, message);
			if (response.getCode() != 200) {
				throw new L8Exception("Error updating simulat8r matrix");
			}		
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	@Override
	public Color[][] readMatrix() throws L8Exception 
	{
		try {
			Response response = this.client.get("/l8s/" + this.simulat8rToken + "/led");
			if (response.getCode() == 200) {
				JSONObject json = (JSONObject)response.getJSON();
				Color[][] matrix = new Color[8][8];			
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						String ledName = "led" + i + "" + j;
						String value = (String)json.get(ledName);
						matrix[i][j] = readColor(value);
					}
				}
				return matrix;
			}		
			throw new L8Exception("Error reading simulat8r matrix");
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}	

	@Override
	@SuppressWarnings("unchecked")
	public void setLED(int x, int y, Color color) throws L8Exception 
	{
		try {
			JSONObject message = new JSONObject();
			message.put("led" + x + y, printColor(color));
			Response response = this.client.put("/l8s/" + this.simulat8rToken, message);
			if (response.getCode() != 200) {
				throw new L8Exception("Error updating simulat8r LED {" + x + "," + y + "}");
			}		
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	@Override
	public void clearLED(int x, int y) throws L8Exception 
	{
		setLED(x, y, Color.BLACK);
	}

	@Override
	public Color readLED(int x, int y) throws L8Exception  
	{
		try {
			String ledName = "led" + x + "" + y;
			Response response = this.client.get("/l8s/" + this.simulat8rToken + "/led/" + ledName);
			if (response.getCode() == 200) {
				JSONObject json = (JSONObject)response.getJSON();
				String value = (String)json.get(ledName);
				return readColor(value);
			}		
			throw new L8Exception("Error reading simulat8r LED {" + x + "," + y + "}");
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
		
	@Override
	@SuppressWarnings("unchecked")	
	public void setSuperLED(Color color) throws L8Exception 
	{
		try {
			JSONObject message = new JSONObject();
			message.put("superled", printColor(color));
			Response response = this.client.put("/l8s/" + this.simulat8rToken + "/superled", message);
			if (response.getCode() != 200) {
				throw new L8Exception("Error updating simulat8r superLED");
			}		
		} catch (Exception e) {
			throw new L8Exception(e);
		}		
	}
	
	@Override
	public void clearSuperLED() throws L8Exception 
	{
		setSuperLED(Color.BLACK);
	}
	
	@Override
	public Color readSuperLED() throws L8Exception 
	{
		try {
			Response response = this.client.get("/l8s/" + this.simulat8rToken + "/superled");
			if (response.getCode() == 200) {
				JSONObject json = (JSONObject)response.getJSON();
				String value = (String)json.get("superled");
				return readColor(value);
			}		
			throw new L8Exception("Error reading simulat8r superLED");
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void enableSensor(Sensor sensor) throws L8Exception 
	{
		try {
			JSONObject message = new JSONObject();
			message.put(sensor.getName()+ "_enabled", "1");
			Response response = this.client.put("/l8s/" + this.simulat8rToken, message);
			if (response.getCode() != 200) {
				throw new L8Exception("Error enabling simulat8r sensor");
			}		
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void disableSensor(Sensor sensor) throws L8Exception 
	{
		try {
			JSONObject message = new JSONObject();
			message.put(sensor.getName()+ "_enabled", "0");
			Response response = this.client.put("/l8s/" + this.simulat8rToken, message);
			if (response.getCode() != 200) {
				throw new L8Exception("Error disabling simulat8r sensor");
			}		
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	@Override	
	public float readSensor(Sensor sensor) throws L8Exception 
	{
		try {
			Response response = this.client.get("/l8s/" + this.simulat8rToken + "/sensor/" + sensor.getName());
			if (response.getCode() == 200) {
				JSONObject json = (JSONObject)response.getJSON();
				String value = (String)json.get(sensor.getName() + "_data");
				return Double.valueOf(value).floatValue();
			}		
			throw new L8Exception("Error reading simulat8r sensor");
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}

	@Override	
	public boolean isSensorEnabled(Sensor sensor) throws L8Exception 
	{
		try {
			Response response = this.client.get("/l8s/" + this.simulat8rToken + "/sensor/" + sensor.getName() + "/enabled");
			if (response.getCode() == 200) {
				JSONObject json = (JSONObject)response.getJSON();
				String value = (String)json.get(sensor.getName() + "_enabled");
				return value.equals("1");
			}		
			throw new L8Exception("Error querying simulat8r sensor");
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	@Override	
	public boolean isBluetoothEnabled() throws L8Exception 
	{
		try {
			Response response = this.client.get("/l8s/" + this.simulat8rToken + "/bluetooth_enabled");
			if (response.getCode() == 200) {
				JSONObject json = (JSONObject)response.getJSON();
				String value = (String)json.get("bluetooth_enabled");
				return value.equals("1");
			}		
			throw new L8Exception("Error querying simulat8r bluetooth");
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	@Override	
	public float getBatteryStatus() throws L8Exception 
	{
		try {
			Response response = this.client.get("/l8s/" + this.simulat8rToken + "/battery_status");
			if (response.getCode() == 200) {
				JSONObject json = (JSONObject)response.getJSON();
				String value = (String)json.get("battery_status");
				return Double.valueOf(value).floatValue();
			}		
			throw new L8Exception("Error reading simulat8r battery status");
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}

}
