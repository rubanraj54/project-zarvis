package zarvis.bakery.models;
import java.util.List;


public class Order {
	
	private String guid;
	private String customer_id;
	private Date order_date;
	private Date delivery_date;
	private Product products;
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	public Date getDelivery_date() {
		return delivery_date;
	}
	public void setDelivery_date(Date delivery_date) {
		this.delivery_date = delivery_date;
	}
	public Product getProducts() {
		return products;
	}
	public void setProducts(Product products) {
		this.products = products;
	}

}
