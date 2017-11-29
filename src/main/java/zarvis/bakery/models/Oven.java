package zarvis.bakery.models;

public class Oven {
	
	private String guid;
	private int cooling_rate;
	private int heating_rate;
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public int getCooling_rate() {
		return cooling_rate;
	}
	public void setCooling_rate(int cooling_rate) {
		this.cooling_rate = cooling_rate;
	}
	public int getHeating_rate() {
		return heating_rate;
	}
	public void setHeating_rate(int heating_rate) {
		this.heating_rate = heating_rate;
	}
	@Override
	public String toString() {
		return "Oven [guid=" + guid + ", cooling_rate=" + cooling_rate + ", heating_rate=" + heating_rate + "]";
	}

}
