package zarvis.bakery;

import zarvis.bakery.models.BakeryJsonWrapper;
import zarvis.bakery.utils.Util;

public class TestClass {
	public static void main(String[] args) {
		BakeryJsonWrapper wrapper = Util.getWrapper();
		System.out.println(wrapper.getBakeries().get(0).hasAllProducts(wrapper.getOrders().get(2)));
		
	}
}
