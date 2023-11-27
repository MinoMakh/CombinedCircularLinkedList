package application;

public class CLinkedList<T extends Comparable<T>> {

	private Node<T> head;

	// Inserting a new node sorted
	public void insertSorted(T data) {
		Node<T> newNode = new Node<>(data);
		Node<T> current = head;
		Node<T> prev = head;
		if (head == null) { // The list is empty
			head = newNode;
			head.setNext(head);
			return;
		}

		do {
			prev = prev.getNext();
		} while (prev.getNext() != head);

		do {
			if (current.getData().compareTo(data) < 0) {
				if (current == head) { // The element to insert its in the head
					prev.setNext(newNode);
					newNode.setNext(current);
					head = newNode;
					return;

				}

				else { // The element to insert is between two elements
					prev.setNext(newNode);
					newNode.setNext(current);
					return;
				}
			}
			current = current.getNext();
			prev = prev.getNext();

		} while (current != head);
		prev.setNext(newNode); // The element to insert is at last
		newNode.setNext(head);
	}

	// Printing the list
	public void traverse() {
		Node<T> current = head;
		if (current != null) {
			do {
				System.out.print(current.getData() + " -> ");
				current = current.getNext();

			} while (current != head);
		} else {
			System.out.println("The list is empty.");
		}
		System.out.println("");
	}

	// Searching for a node
	public Node<T> search(T data) {
		Node<T> current = head;
		if (current != null) {
			do {
				if (current.getData().compareTo(data) == 0) {
					return current;
				} else {
					current = current.getNext();
				}
			} while (current != head);
		}

		return null;
	}

	// Removing a node from the list
	public boolean delete(T data) {
		Node<T> current = head;
		Node<T> prev = head;
		do {
			prev = prev.getNext();
		} while (prev.getNext() != head);

		if (current != null) {
			do {
				if (current.getData().compareTo(data) == 0) {

					if (current == head && prev == head) { // The list only has one element.
						head = null;
					}

					else if (current == head) { // The element to delete is the head
						prev.setNext(head.getNext());
						head = head.getNext();

					} else { // Any other case
						prev.setNext(current.getNext());
					}
					return true;
				} else {
					prev = prev.getNext();
					current = current.getNext();
				}
			} while (current != head);
		}
		return false;
	}

	// Setters and Getters
	public Node<T> getHead() {
		return head;
	}

	public void setHead(Node<T> head) {
		this.head = head;
	}
}