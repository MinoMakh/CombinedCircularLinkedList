package application;

public class Node<T extends Comparable<T>> implements Comparable<Node> {

	private Node<T> next;
	private T data;

	public Node(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return data + " ";
	}

	@Override
	public int compareTo(Node o) {
		if (this.data.equals(data))
			return 0;
		return -1;
	}

	// Setters and Getters
	public Node<T> getNext() {
		return next;
	}

	public void setNext(Node<T> next) {
		this.next = next;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	
}
