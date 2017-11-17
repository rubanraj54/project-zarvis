package zarvis.bakery.models;

public class MetaInfo {
	
	private int orders;
	private Customer customers;
	private int bakeries;
	private int duration_days;
	private int trucks;
	private int products;

	public int getOrders() {
		return orders;
	}

	public int getBakeries() {
		return bakeries;
	}
	
	public Customer getCustomers() {
		return customers;
	}

	public void setCustomers(Customer customers) {
		this.customers = customers;
	}

	public void setBakeries(int bakeries) {
		this.bakeries = bakeries;
	}

	public int getDuration_days() {
		return duration_days;
	}

	public void setDuration_days(int duration_days) {
		this.duration_days = duration_days;
	}

	public int getTrucks() {
		return trucks;
	}

	public void setTrucks(int trucks) {
		this.trucks = trucks;
	}

	public int getProducts() {
		return products;
	}

	public void setProducts(int products) {
		this.products = products;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}
}
