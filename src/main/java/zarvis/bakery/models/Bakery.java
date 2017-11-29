package zarvis.bakery.models;

import jade.core.AID;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Bakery {
	private String guid;
	private String name;
	private Location location;
	private AID aid;
	
	private List<Oven> ovens;
	private List<Product> products;
	private List<Truck>  trucks;

	private List<DoughPrepTable>  dough_prep_tables;

	private List<KneedingMachine> kneading_machines;

	public List<DoughPrepTable> getDough_prep_tables() {
		return dough_prep_tables;
	}

	public void setDough_prep_tables(List<DoughPrepTable> dough_prep_tables) {
		this.dough_prep_tables = dough_prep_tables;
	}

	public List<KneedingMachine> getKneading_machines() {
		return kneading_machines;
	}

	public void setKneading_machines(List<KneedingMachine> kneading_machines) {
		this.kneading_machines = kneading_machines;
	}

	public AID getAid() {
		return aid;
	}

	public void setAid(AID aid) {
		this.aid = aid;
	}

	public Product getProduct(String guid){
		for (Product product : products) {
			if (product.getGuid().equals(guid)) {
				return product;
			}
		}
		return null;
	}
	
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
	public List<Oven> getOvens() {
		return ovens;
	}
	public void setOvens(ArrayList<Oven> ovens) {
		this.ovens = ovens;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
	public List<Truck> getTrucks() {
		return trucks;
	}
	public void setTrucks(ArrayList<Truck> trucks) {
		this.trucks = trucks;
	}
	
	public int missingProductCount(Order order) {
		List<String> productids = getProductIds();
		int missingProductCount = 0;
		for(String id : order.getProducts().keySet()){
			if(!productids.contains(id)) {
				missingProductCount++;
			}
		}
		return missingProductCount;
		
	}
	
	public List<String> getProductIds(){
		return getProducts().stream().map(Product::getGuid).collect(Collectors.toList());
	}
	
	@Override
	public String toString() {
		return "Bakery [guid=" + guid + ", name=" + name + ", location=" + location + ", kneading_machines="
				+ kneading_machines + ", dough_prep_tables=" + dough_prep_tables + ", ovens=" + ovens + ", products="
				+ products + ", trucks=" + trucks + "]";
	}
	
	
	
}
