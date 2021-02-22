package schemas;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

/**
 */
public class PressInfo implements Cloneable {
	
	/**
	 * 
	 * Identifier of the press
	 */
	@SerializedName("id")
	private String id;
	
	/**
	 * Timestamp
	 */
	@SerializedName("ts")
	private String Timestamp;
	
	/**
	 * 
	 * Pressure of the press in Pascals
	 */
	@SerializedName("value")
	private Double value;
	
		
	private PressInfo() {
	}
		
	public static final PressInfoBuilder newBuilder() {
		return PressInfoBuilder.newBuilder();
	}
	
	public String toJson() {
		return toJson(false);
	}
	
	public String toJson(boolean pretty) {
		Gson gson = pretty ? new GsonBuilder().setPrettyPrinting().create() : new Gson();
		return gson.toJson(this);
	}
	
	public static PressInfo fromJson(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, PressInfo.class);
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getTimestamp() {
		return this.Timestamp;
	}
	
	public Double getValue() {
		return this.value;
	}
	
	protected Object clone() throws CloneNotSupportedException {
		PressInfo clone = new PressInfo();
		clone.id = this.id;
		clone.Timestamp = this.Timestamp;
		clone.value = this.value;
		return clone;
	}
	
	public static class PressInfoBuilder {
		
		private PressInfo instance = new PressInfo();
		
		private static PressInfoBuilder newBuilder() {
			return new PressInfoBuilder();
		}
		
		public PressInfoBuilder withId(String id) {
			this.instance.id = id;
			return this;
		}
		
		public PressInfoBuilder withTimestamp(String Timestamp) {
			this.instance.Timestamp = Timestamp;
			return this;
		}
		
		public PressInfoBuilder withValue(Double value) {
			this.instance.value = value;
			return this;
		}
		
		public PressInfo build() {
			try {
				return (PressInfo) instance.clone();
			} catch (CloneNotSupportedException e) {
				throw new RuntimeException("Unable to build: " + this, e);
			}
		}
	}
}
