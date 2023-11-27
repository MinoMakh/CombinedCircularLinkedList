package application;

public class Date<T>{

	private T day;
	private T month;
	private T year;

	public Date(T day, T month, T year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public String toString() {
		return year + "-" + month + "-" + day;
	}
	
	// Setters and Getters
	public T getDay() {
		return day;
	}

	public void setDay(T day) {
		this.day = day;
	}

	public T getMonth() {
		return month;
	}

	public void setMonth(T month) {
		this.month = month;
	}

	public T getYear() {
		return year;
	}

	public void setYear(T year) {
		this.year = year;
	}
}
