package pj1;
/* SList.java */

/**
 * The SList class is a singly-linked implementation of the linked list
 * abstraction. SLists are mutable data structures, which can grow at either
 * end.
 *
 * @author Kathy Yelick and Jonathan Shewchuk
 **/

public class SList {

	private SListNode head;
	private int size;

	/**
	 * SList() constructs an empty list.
	 **/

	public SList() {
		size = 0;
		head = null;
	}

	/**
	 * isEmpty() indicates whether the list is empty.
	 * 
	 * @return true if the list is empty, false otherwise.
	 **/

	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * length() returns the length of this list.
	 * 
	 * @return the length of this list.
	 **/

	public int length() {
		return size;
	}

	/**
	 * insertFront() inserts item "obj" at the beginning of this list.
	 * 
	 * @param obj
	 *            the item to be inserted.
	 **/

	public void insertFront(short red, short green, short blue, int runLengths) {
		head = new SListNode(red, green, blue, runLengths, head);
		size++;
	}

	/**
	 * insertEnd() inserts item "obj" at the end of this list.
	 * 
	 * @param obj
	 *            the item to be inserted.
	 **/

	public void insertEnd(short red, short green, short blue, int runLengths) {
		if (head == null) {
			head = new SListNode(red, green, blue, runLengths);
		} else {
			SListNode node = head;
			while (node.next != null) {
				node = node.next;
			}
			node.next = new SListNode(red, green, blue, runLengths);
		}
		size++;
	}



	public SListNode getHead() {
		return head;
	}

	public void setHead(SListNode head) {
		this.head = head;
	}

	/**
	 * toString() converts the list to a String.
	 * 
	 * @return a String representation of the list.
	 **/

	public String toString() {
		return "";
	}
}
