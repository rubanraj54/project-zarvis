package zarvis.bakery.models;

public class Oven {
	
	private int guid;
	private int cooling_rate;
	private int heating_rate;
	
	public int getGuid() {
		return guid;
	}
	public void setGuid(int guid) {
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

}
