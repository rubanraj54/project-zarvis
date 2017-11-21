package zarvis.bakery.models;

public class Date {
	private int day;
	private int hour;
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	@Override
	public String toString() {
		return "Date [day=" + day + ", hour=" + hour + "]";
	}
	
	
}
