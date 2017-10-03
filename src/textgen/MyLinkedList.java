package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		head.next = tail;
		tail.prev = head;
		head.prev = null;
		tail.next = null;
		
		size = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// TODO: Implement this method		
		add(this.size(), element);
		if(element == get(this.size() - 1)) {
			return true;
		}
		else {
			return false;
		}
		
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		LLNode<E> node = null;
		if(index < 0 || index >= this.size()) {
			throw new IndexOutOfBoundsException("The index provided is out of bounds");
		}
		else if(index < ((this.size() - 1) / 2)) {
			node = this.head;
			for(int i = 0; i <= index; i++) {
				if(node.next != null) {
					node = node.next;
				}
				else {
					throw new NullPointerException("A node with a null value has been encountered");
				}
			}			
		}
		else {
			node = this.tail;
			for(int i = this.size() - 1; i >= index; i--) {
				if(node.prev != null) {
					node = node.prev;
				}
				else {
					throw new NullPointerException("A node with a null value has been encountered");
				}
			}
		}
		return node.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		LLNode<E> node = null;
		if(index < 0 || index > this.size()) {
			throw new IndexOutOfBoundsException("The index provided is out of bounds");
		}
		else if(element == null) {
			throw new NullPointerException("The element has a null");
		}
		else if(index < (this.size() / 2)) {
			node = this.head;
			for(int i = 0; i < index; i++) {
				if(node.next != null) {
					node = node.next;
					
				}
				else {
					
					throw new NullPointerException("A node with a null value has been encountered");
				}
			}			
		}
		else {
			node = this.tail;
			for(int i = this.size(); i >= index; i--) {				
				if(node.prev != null) {
					node = node.prev;					
				}
				else {
					throw new NullPointerException("A node with a null value has been encountered");
				}
			}
		}
		LLNode<E> newNode = new LLNode<E>(element);
		newNode.next = node.next;
		node.next.prev = newNode;
		newNode.prev = node;
		node.next = newNode;		
		size++;
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return this.size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		LLNode<E> node = null;
		if(index < 0 || index >= this.size()) {
			throw new IndexOutOfBoundsException("The index provided is out of bounds");
		}
		else if(index < (this.size() / 2)) {
			node = this.head;
			for(int i = 0; i < index; i++) {
				if(node.next != null) {
					node = node.next;
					
				}
				else {
					
					throw new NullPointerException("A node with a null value has been encountered");
				}
			}			
		}
		else {
			node = this.tail;
			for(int i = this.size(); i >= index; i--) {				
				if(node.prev != null) {
					node = node.prev;					
				}
				else {
					throw new NullPointerException("A node with a null value has been encountered");
				}
			}
		}
		LLNode<E> removeNode = node.next;
		removeNode.next.prev = node;
		node.next = removeNode.next;	
		size--;
		return removeNode.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		LLNode<E> node = null;
		if(index < 0 || index > this.size()) {
			throw new IndexOutOfBoundsException("The index provided is out of bounds");
		}
		else if(element == null) {
			throw new NullPointerException("The element has a null");
		}
		else if(index < (this.size() / 2)) {
			node = this.head;
			for(int i = 0; i < index; i++) {
				if(node.next != null) {
					node = node.next;
					
				}
				else {
					
					throw new NullPointerException("A node with a null value has been encountered");
				}
			}			
		}
		else {
			node = this.tail;
			for(int i = this.size(); i >= index; i--) {				
				if(node.prev != null) {
					node = node.prev;					
				}
				else {
					throw new NullPointerException("A node with a null value has been encountered");
				}
			}
		}
		E oldElement = node.next.data;
		node.next.data = element;				
		return oldElement;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
