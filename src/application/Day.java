package application;

public class Day<T extends Comparable<T>> implements Comparable<Day<T>> {

	private T position;
	private T israeliLines;
	private T gazaPowerPlant;
	private T egyptiansLines;
	private T overallDemand;
	private T powerCutsHoursDay;
	private T totalDailySupply;
	private T temp;
	private Date<T> date;

	public Day(Date date, T israeliLines, T gazaPowerPlant, T egyptiansLines, T overallDemand, T powerCutsHoursDay,
			T totalDailySupply, T temp) {
		this.date = date;
		this.israeliLines = israeliLines;
		this.gazaPowerPlant = gazaPowerPlant;
		this.egyptiansLines = egyptiansLines;
		this.overallDemand = overallDemand;
		this.powerCutsHoursDay = powerCutsHoursDay;
		this.totalDailySupply = totalDailySupply;
		this.temp = temp;
		position = (T) date.getDay();
	}

	@Override
	public int compareTo(Day o) {
		if (position.equals(o.getPosition()))
			return 0;
		else if (Integer.parseInt((String) position) > Integer.parseInt((String) o.getPosition()))
			return -1;
		return 1;
	}

	@Override
	public String toString() {
		return "Date: " + date.toString() + "\nIsraeliLines: " + israeliLines + "\nGazaPowerPlant: " + gazaPowerPlant + "\nEgyptiansLines: "
				+ egyptiansLines + "\nOverallDemand: " + overallDemand + "\nPowerCutsHoursDay: " + powerCutsHoursDay
				+ "\nTotalDailySupply: " + totalDailySupply + "\nTemperature: " + temp + "\n";
	}

	// Setters and Getters
	public T getIsraeliLines() {
		return israeliLines;
	}

	public void setIsraeliLines(T israeliLines) {
		this.israeliLines = israeliLines;
	}

	public T getGazaPowerPlant() {
		return gazaPowerPlant;
	}

	public void setGazaPowerPlant(T gazaPowerPlant) {
		this.gazaPowerPlant = gazaPowerPlant;
	}

	public T getEgyptiansLines() {
		return egyptiansLines;
	}

	public void setEgyptiansLines(T egyptiansLines) {
		this.egyptiansLines = egyptiansLines;
	}

	public T getOverallDemand() {
		return overallDemand;
	}

	public void setOverallDemand(T overallDemand) {
		this.overallDemand = overallDemand;
	}

	public T getPowerCutsHoursDay() {
		return powerCutsHoursDay;
	}

	public void setPowerCutsHoursDay(T powerCutsHoursDay) {
		this.powerCutsHoursDay = powerCutsHoursDay;
	}

	public T getTotalDailySupply() {
		return totalDailySupply;
	}

	public void setTotalDailySupply(T totalDailySupply) {
		this.totalDailySupply = totalDailySupply;
	}

	public T getTemp() {
		return temp;
	}

	public void setTemp(T temp) {
		this.temp = temp;
	}

	public Date<T> getDate() {
		return date;
	}

	public void setDate(Date<T> date) {
		this.date = date;
	}

	public T getPosition() {
		return position;
	}

	public void setPosition(T position) {
		this.position = position;
	}
}
