package zarvis.bakery.models;


public class Truck {
	private String guid;
	private int load_capacity;
	private Location location;
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public int getLoad_capacity() {
		return load_capacity;
	}
	public void setLoad_capacity(int load_capacity) {
		this.load_capacity = load_capacity;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	
	
}
