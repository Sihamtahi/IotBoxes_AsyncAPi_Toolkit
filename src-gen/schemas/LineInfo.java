package schemas;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

/**
 */
public class LineInfo implements Cloneable {
	
	/**
	 * 
	 * identifier of the subsystem
	 */
	@SerializedName("id")
	private String id;
	
	/**
	 * 
	 * Info of presses in this subsystem
	 */
	@SerializedName("presses")
	private java.util.List<PressInfo> presses = new java.util.ArrayList<PressInfo>();
	
		
	private LineInfo() {
	}
		
	public static final LineInfoBuilder newBuilder() {
		return LineInfoBuilder.newBuilder();
	}
	
	public String toJson() {
		return toJson(false);
	}
	
	public String toJson(boolean pretty) {
		Gson gson = pretty ? new GsonBuilder().setPrettyPrinting().create() : new Gson();
		return gson.toJson(this);
	}
	
	public static LineInfo fromJson(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, LineInfo.class);
	}
	
	public String getId() {
		return this.id;
	}
	
	public java.util.List<PressInfo> getPresses() {
		return this.presses;
	}
	
	protected Object clone() throws CloneNotSupportedException {
		LineInfo clone = new LineInfo();
		clone.id = this.id;
		clone.presses = this.presses;
		return clone;
	}
	
	public static class LineInfoBuilder {
		
		private LineInfo instance = new LineInfo();
		
		private static LineInfoBuilder newBuilder() {
			return new LineInfoBuilder();
		}
		
		public LineInfoBuilder withId(String id) {
			this.instance.id = id;
			return this;
		}
		
		public LineInfoBuilder addToPresses(PressInfo pressesElt) {
			this.instance.presses.add(pressesElt);
			return this;
		}
		
		public LineInfo build() {
			try {
				return (LineInfo) instance.clone();
			} catch (CloneNotSupportedException e) {
				throw new RuntimeException("Unable to build: " + this, e);
			}
		}
	}
}
