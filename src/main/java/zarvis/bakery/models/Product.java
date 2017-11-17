package zarvis.bakery.models;

public class Product {

	private String id;	
			
	private int dough_prep_time;	
			
	private int resting_time;	
			
	private int item_prep_time;
			
	private int breads_per_oven;
			
	private int baking_time;	
			
	private int baking_temp;
			
	private int cooling_rate;
			
	private int boxing_temp;	
			
	private int breads_per_box;	
			
	private int production_cost;
			
	private int sales_price	;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDough_prep_time() {
		return dough_prep_time;
	}

	public void setDough_prep_time(int dough_prep_time) {
		this.dough_prep_time = dough_prep_time;
	}

	public int getResting_time() {
		return resting_time;
	}

	public void setResting_time(int resting_time) {
		this.resting_time = resting_time;
	}

	public int getItem_prep_time() {
		return item_prep_time;
	}

	public void setItem_prep_time(int item_prep_time) {
		this.item_prep_time = item_prep_time;
	}

	public int getBreads_per_oven() {
		return breads_per_oven;
	}

	public void setBreads_per_oven(int breads_per_oven) {
		this.breads_per_oven = breads_per_oven;
	}

	public int getBaking_time() {
		return baking_time;
	}

	public void setBaking_time(int baking_time) {
		this.baking_time = baking_time;
	}

	public int getBaking_temp() {
		return baking_temp;
	}

	public void setBaking_temp(int baking_temp) {
		this.baking_temp = baking_temp;
	}

	public int getCooling_rate() {
		return cooling_rate;
	}

	public void setCooling_rate(int cooling_rate) {
		this.cooling_rate = cooling_rate;
	}

	public int getBoxing_temp() {
		return boxing_temp;
	}

	public void setBoxing_temp(int boxing_temp) {
		this.boxing_temp = boxing_temp;
	}

	public int getBreads_per_box() {
		return breads_per_box;
	}

	public void setBreads_per_box(int breads_per_box) {
		this.breads_per_box = breads_per_box;
	}

	public int getProduction_cost() {
		return production_cost;
	}

	public void setProduction_cost(int production_cost) {
		this.production_cost = production_cost;
	}

	public int getSales_price() {
		return sales_price;
	}

	public void setSales_price(int sales_price) {
		this.sales_price = sales_price;
	}
	
	

}
