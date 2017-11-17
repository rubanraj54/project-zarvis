package zarvis.bakery.models;

import java.util.ArrayList;

public class Bakery {
	private String guid;
	private String name;
	private Location location;
	private int kneading_machines;
	private int dough_prep_tables;
	private ArrayList<Oven> ovens = new ArrayList<Oven>();
	private ArrayList<Product> products = new ArrayList<Product>();
	private ArrayList<Truck>  trucks = new ArrayList<Truck>();
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public int getKneading_machines() {
		return kneading_machines;
	}
	public void setKneading_machines(int kneading_machines) {
		this.kneading_machines = kneading_machines;
	}
	public int getDough_prep_tables() {
		return dough_prep_tables;
	}
	public void setDough_prep_tables(int dough_prep_tables) {
		this.dough_prep_tables = dough_prep_tables;
	}
	public ArrayList<Oven> getOvens() {
		return ovens;
	}
	public void setOvens(ArrayList<Oven> ovens) {
		this.ovens = ovens;
	}
	public ArrayList<Product> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
	public ArrayList<Truck> getTrucks() {
		return trucks;
	}
	public void setTrucks(ArrayList<Truck> trucks) {
		this.trucks = trucks;
	}
	
	
	
	

}
