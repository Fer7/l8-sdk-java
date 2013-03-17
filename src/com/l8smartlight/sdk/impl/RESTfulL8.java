package com.l8smartlight.sdk.impl;

import org.json.simple.JSONObject;

import com.l8smartlight.sdk.L8;
import com.l8smartlight.sdk.core.L8Exception;
import com.l8smartlight.sdk.core.Sensor;
import com.l8smartlight.sdk.core.Color;

import es.develappers.rest.RESTfulClient;
import es.develappers.rest.Response;

public class RESTfulL8 implements L8 
{
	
	//private final String SIMULAT8R_BASE_URL = "http://192.168.1.165:8888/l8-server-simulator";
	private final String SIMULAT8R_BASE_URL = "http://http://54.228.218.122/l8-server-simulator";
	private RESTfulClient client = null;
	private String simulat8rToken = null;
	
	protected String printColor(Color color) 
	{
		String alphaRedGreenBlue = Integer.toHexString(color.getRGB());
		String redGreenBlue = alphaRedGreenBlue.substring(2);
		return "#" + redGreenBlue; 
	}
	
	protected Color parseColor(String color) 
	{
		Integer red = Integer.valueOf(color.substring(1, 2), 16);
		Integer green = Integer.valueOf(color.substring(3, 5), 16);
		Integer blue = Integer.valueOf(color.substring(5), 16);
		return new Color(red, green, blue);
	}
	
	protected Color readColor(JSONObject json, String name)
	{
		String stringValue = (String)json.get(name);
		return parseColor(stringValue);
	}
	
	protected float readFloat(JSONObject json, String name)
	{
		String stringValue = (String)json.get(name);
		float floatValue = Double.valueOf(stringValue).floatValue();
		return floatValue;
	}
	
	protected int readInt(JSONObject json, String name)
	{
		return (int)readFloat(json, name);
	}
	
	protected boolean readBool(JSONObject json, String name)
	{
		String value = (String)json.get(name);
		return value.equals("1");		
	}
	
	protected String join(String[] input, String delimiter)
	{
	    StringBuilder sb = new StringBuilder();
	    for (String value : input) {
	        sb.append(value);
	        sb.append(delimiter);
	    }
	    int length = sb.length();
	    if (length > 0) {
	        sb.setLength(length - delimiter.length());
	    }
	    return sb.toString();
	}	
	
	protected String printMatrix(Color[][] matrix)
	{
		String[] rowColors =  new String[matrix.length*matrix.length];
		int idx = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				rowColors[idx++] = printColor(matrix[i][j]);
			}
		}
		return join(rowColors, "-");	
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
			for (int i = 0; i < 8; i++) {
				message.put("frame" + i, "");
				message.put("frame" + i + "_duration", 0);
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
						matrix[i][j] = readColor(json, ledName);
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
				return readColor(json, ledName);
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
				return readColor(json, "superled");
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
			message.put(sensor.getName()+ "_sensor_enabled", "1");
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
			message.put(sensor.getName()+ "_sensor_enabled", "0");
			Response response = this.client.put("/l8s/" + this.simulat8rToken, message);
			if (response.getCode() != 200) {
				throw new L8Exception("Error disabling simulat8r sensor");
			}		
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	@Override	
	public Sensor.Status readSensor(Sensor sensor) throws L8Exception 
	{
		try {
			Response response = this.client.get("/l8s/" + this.simulat8rToken + "/sensor/" + sensor.getName());
			if (response.getCode() == 200) {
				JSONObject json = (JSONObject)response.getJSON();
				
				if (sensor.equals(Sensor.TEMPERATURE)) {
					float celsiusValue = readFloat(json, sensor.getName() + "_celsius_data");
					float fahrenheitValue = readFloat(json, sensor.getName() + "_fahrenheit_data");
					return new Sensor.TemperatureStatus(celsiusValue, fahrenheitValue);
				}
				if (sensor.equals(Sensor.ACCELERATION)) {

					float rawX = readFloat(json, sensor.getName() + "_sensor_data_rawX");
					float rawY = readFloat(json, sensor.getName() + "_sensor_data_rawY");
					float rawZ = readFloat(json, sensor.getName() + "_sensor_data_rawZ");
					int shake = readInt(json, sensor.getName() + "_sensor_data_shake");
					int orientation = readInt(json, sensor.getName() + "_sensor_data_orientation");
					
					return new Sensor.AccelerationStatus(rawX, rawY, rawZ, shake, orientation);
				}
				
				int value = readInt(json, sensor.getName() + "_data");
				if (sensor.equals(Sensor.AMBIENTLIGHT)) {
					return new Sensor.AmbientLightStatus(value);
				}
				if (sensor.equals(Sensor.PROXIMITY)) {
					return new Sensor.ProximityStatus(value);
				}
				if (sensor.equals(Sensor.NOISE)) {
					return new Sensor.NoiseStatus(value);
				}
				return new Sensor.Status();
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
				return readBool(json, sensor.getName() + "_enabled");
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
				return readBool(json, "bluetooth_enabled");
			}		
			throw new L8Exception("Error querying simulat8r bluetooth");
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	@Override	
	public int getBatteryStatus() throws L8Exception 
	{
		try {
			Response response = this.client.get("/l8s/" + this.simulat8rToken + "/battery_status");
			if (response.getCode() == 200) {
				JSONObject json = (JSONObject)response.getJSON();
				return readInt(json, "battery_status");
			}		
			throw new L8Exception("Error reading simulat8r battery status");
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	@Override
	public int getButton() throws L8Exception
	{
		try {
			Response response = this.client.get("/l8s/" + this.simulat8rToken + "/button");
			if (response.getCode() == 200) {
				JSONObject json = (JSONObject)response.getJSON();
				return readInt(json, "button");
			}		
			throw new L8Exception("Error reading simulat8r button");
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}
	
	@Override
	public int getMemorySize() throws L8Exception
	{
		try {
			Response response = this.client.get("/l8s/" + this.simulat8rToken + "/memory_size");
			if (response.getCode() == 200) {
				JSONObject json = (JSONObject)response.getJSON();
				return readInt(json, "memory_size");
			}		
			throw new L8Exception("Error reading simulat8r memory size");
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}

	@Override
	public int getFreeMemory() throws L8Exception
	{
		try {
			Response response = this.client.get("/l8s/" + this.simulat8rToken + "/free_memory");
			if (response.getCode() == 200) {
				JSONObject json = (JSONObject)response.getJSON();
				return readInt(json, "free_memory");
			}		
			throw new L8Exception("Error reading simulat8r free memory");
		} catch (Exception e) {
			throw new L8Exception(e);
		}		
	}
	
	@Override
	public String getId() throws L8Exception
	{
		return simulat8rToken;
	}
	
	@Override
	public L8.Version getVersion() throws L8Exception
	{
		try {
			Response response = this.client.get("/l8s/" + this.simulat8rToken + "/version");
			if (response.getCode() == 200) {
				JSONObject json = (JSONObject)response.getJSON();
				int hardwareVersion = readInt(json, "hardware_version");
				int softwareVersion = readInt(json, "software_version");
				return new L8.Version(hardwareVersion, softwareVersion);
			}		
			throw new L8Exception("Error reading simulat8r version");
		} catch (Exception e) {
			throw new L8Exception(e);
		}		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void setAnimation(L8.Animation animation) throws L8Exception
	{
		try {
			JSONObject message = new JSONObject();
			for (int i = 0; i < 8; i++) {
				if (i < animation.getFrames().size()) {
					L8.Frame frame = animation.getFrames().get(i);
					message.put("frame" + i, printMatrix(frame.getMatrix()));
					message.put("frame" + i + "_duration", frame.getDuration());
				} else {
					message.put("frame" + i, "");
					message.put("frame" + i + "_duration", 0);
				}
			}
			Response response = this.client.put("/l8s/" + this.simulat8rToken, message);
			if (response.getCode() != 200) {
				throw new L8Exception("Error setting animation");
			}
		} catch (Exception e) {
			throw new L8Exception(e);
		}
	}

	@Override
	public String getConnectionURL() throws L8Exception
	{
		return SIMULAT8R_BASE_URL + "/l8s/" + getId();
	}
	
}
