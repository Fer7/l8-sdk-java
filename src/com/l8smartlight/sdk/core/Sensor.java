package com.l8smartlight.sdk.core;

public class Sensor 
{
	public static Sensor PROXIMITY = new Sensor("proximity");
	public static Sensor TEMPERATURE = new Sensor("temperature");  
	public static Sensor NOISE = new Sensor("noise");  
	public static Sensor AMBIENTLIGHT = new Sensor("ambientlight");  
	public static Sensor ACCELERATION = new Sensor("acceleration");
	
	protected String name;
	protected Status status;
	
	public Status getStatus() 
	{
		return status;
	}

	public void setStatus(Status status) 
	{
		this.status = status;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public Sensor(String name) 
	{
		this.name = name;
	}
	
	public static class Status
	{
		protected boolean enabled;

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
		
		public Status(boolean enabled) 
		{
			this.enabled = enabled;
		}
		
	}
	
	public static class FloatStatus extends Status
	{
		protected float value;

		public float getValue() 
		{
			return value;
		}

		public void setValue(float value) 
		{
			this.value = value;
		}
		
		public FloatStatus(boolean enabled, float value) 
		{
			super(enabled);
			this.value = value;
		}
		
		public String toString() 
		{
			return "enabled: " + enabled + " {" + value + "}";
		}		
	}
	
	public static class IntegerStatus extends Status
	{
		protected int value;

		public int getValue() 
		{
			return value;
		}

		public void setValue(int value) 
		{
			this.value = value;
		}
		
		public IntegerStatus(boolean enabled, int value) 
		{
			super(enabled);
			this.value = value;
		}		
		
		public String toString() 
		{
			return "enabled: " + enabled + " {" + value + "}";
		}
	}
	
	public static class TemperatureStatus extends Status
	{
		protected float celsiusValue;
		protected float fahrenheitValue;
		
		public float getCelsiusValue() 
		{
			return celsiusValue;
		}

		public void setCelsiusValue(float celsiusValue) 
		{
			this.celsiusValue = celsiusValue;
		}

		public float getFahrenheitValue() 
		{
			return fahrenheitValue;
		}

		public void setFahrenheitValue(float fahrenheitValue) 
		{
			this.fahrenheitValue = fahrenheitValue;
		}

		public TemperatureStatus(boolean enabled, float celsiusValue, float fahrenheitValue) 
		{
			super(enabled);
			this.celsiusValue = celsiusValue;
			this.fahrenheitValue = fahrenheitValue;
		}
		
		public String toString() 
		{
			return "enabled: " + enabled + " {" + celsiusValue + " celsius, " + fahrenheitValue + " fahrenheit}";
		}
	}
	
	public static class AmbientLightStatus extends IntegerStatus
	{
		public AmbientLightStatus(boolean enabled, int value) 
		{
			super(enabled, value);
		}
	}
	
	public static class ProximityStatus extends IntegerStatus
	{
		public ProximityStatus(boolean enabled, int value) 
		{
			super(enabled, value);
		}
	}
	
	public static class NoiseStatus extends IntegerStatus
	{
		public NoiseStatus(boolean enabled, int value) 
		{
			super(enabled, value);
		}
	}
	
	public static class AccelerationStatus extends Status
	{
		protected float rawX;
		protected float rawY;
		protected float rawZ;
		protected int shake;
		protected int orientation;
		
		public float getRawX() 
		{
			return rawX;
		}

		public void setRawX(float rawX) 
		{
			this.rawX = rawX;
		}

		public float getRawY() 
		{
			return rawY;
		}

		public void setRawY(float rawY) 
		{
			this.rawY = rawY;
		}

		public float getRawZ() 
		{
			return rawZ;
		}

		public void setRawZ(float rawZ) 
		{
			this.rawZ = rawZ;
		}

		public int getShake() 
		{
			return shake;
		}

		public void setShake(int shake) 
		{
			this.shake = shake;
		}

		public int getOrientation() 
		{
			return orientation;
		}

		public void setOrientation(int orientation) 
		{
			this.orientation = orientation;
		}

		public AccelerationStatus(boolean enabled, float rawX, float rawY, float rawZ, int shake, int orientation) 
		{
			super(enabled);
			this.rawX = rawX;
			this.rawY = rawY;
			this.rawZ = rawZ;
			this.shake = shake;
			this.orientation = orientation;
		}
		
		public String toString() 
		{
			return "enabled: " + enabled + " {rawX: " + rawX + ", rawY: " + rawY + ", rawZ: " + rawZ + ", shake: " + shake + ", orientation: " + orientation + "}";
		}
	}
	
}
