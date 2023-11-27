package application;

public class Month<T extends Comparable<T>> implements Comparable<Month<T>> {

	private T position;
	private CLinkedList<Day<Integer>> dayList = new CLinkedList<>();

	public Month(T position) {
		this.position = position;
	}

	@Override
	public int compareTo(Month o) {
		if (position.equals(o.getPosition()))
			return 0;
		else if (Integer.parseInt((String) position) > Integer.parseInt((String) o.getPosition()))
			return -1;
		return 1;
	}

	@Override
	public String toString() {
		return "Month: " + position;
	}

	// Setters and getters
	public CLinkedList<Day<Integer>> getDayList() {
		return dayList;
	}

	public void setDayList(CLinkedList<Day<Integer>> daysList) {
		this.dayList = daysList;
	}

	public T getPosition() {
		return position;
	}

	public void setPosition(T position) {
		this.position = position;
	}
}
