package zarvis.bakery.models;

import java.util.ArrayList;
import java.util.List;

public class BakeryJsonWrapper {
	
	private MetaInfo meta;
	private List<Bakery> bakeries;
	private List<Customer> customers;
	private List<Order> orders;
	private StreetNetwork street_network;
	
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

	public StreetNetwork getStreet_network() {
		return street_network;
	}

	public void setStreet_network(StreetNetwork street_network) {
		this.street_network = street_network;
	}

	@Override
	public String toString() {
		return "BakeryJsonWrapper [meta=" + meta + ", bakeries=" + bakeries + ", customers=" + customers + ", orders="
				+ orders + "]";
	}

	public List<Order> getOrderByIdCustomer(String customerId){
		List<Order> orders = new ArrayList<Order>();
		for(Order order : this.orders){
			if(order.getCustomer_id().equals(customerId))
				orders.add(order);
		}
		return orders;
	}

	public Order getOrderById(String guid){

		for(Order order : this.orders){
			if(order.getGuid().equals(guid))
			{
				return order;
			}

		}
		return null;
	}

}
