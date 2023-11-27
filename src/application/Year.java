package application;

public class Year<T extends Comparable<T>> implements Comparable<Year<T>> {

	private T position;
	private CLinkedList<Month<Integer>> monthList = new CLinkedList<>();

	public Year(T position) {
		this.position = position;
	}

	@Override
	public int compareTo(Year<T> o) {
		if (position.equals(o.getPosition()))
			return 0;
		else if (Integer.parseInt((String) position) > Integer.parseInt((String) o.getPosition()))
			return -1;
		return 1;
	}

	@Override
	public String toString() {
		return "Year: " + position;
	}

	// Setters and getters
	public T getPosition() {
		return position;
	}

	public void setPosition(T position) {
		this.position = position;
	}

	public CLinkedList<Month<Integer>> getMonthList() {
		return monthList;
	}

	public void setMonthList(CLinkedList<Month<Integer>> monthList) {
		this.monthList = monthList;
	}

}
