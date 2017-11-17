package zarvis.bakery.models;

import java.util.List;

public class BakeryJsonWrapper {
	
	private MetaInfo meta;
	private List<Bakery> bakeries;
	private List<Customer> customers;
	private List<Order> orders;
	
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<Bakery> getBakeries() {
		return bakeries;
	}

	public void setBakeries(List<Bakery> bakeries) {
		this.bakeries = bakeries;
	}


	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public MetaInfo getMeta() {
		return meta;
	}

	public void setMeta(MetaInfo meta) {
		this.meta = meta;
	}
	
}
