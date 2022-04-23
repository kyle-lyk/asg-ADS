package main.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class contains the logic for the 
 * first-in-first-out queue (FIFO)
 */
public class Fifo<E> {
    private Queue<String> NgoList = new LinkedList<String>();
        
	/**
	 * Enqueues the NGO that is passed to the method.
	 * @param NGO NGO's name
	 */
    public void enqueue(String NGO) {
		// Ensure only unique value are added
		if (!NgoList.contains(NGO)) 
			NgoList.add(NGO);
    }

	/**
     * Gets the NGO list.
     * @return NGO list
     */
    public LinkedList<String> getNgoList() {
    	return (LinkedList<String>) NgoList;
    }

	/**
	 * Dequeues the NGO that is at the front of the queue
	 * and returns the dequeued element.
	 * @return first element of set
	 */
	public String dequeuePoll() {
		Iterator iter = NgoList.iterator();
		if (iter != null && iter.hasNext()) {
            String firstElement = iter.next().toString();
			NgoList.remove(firstElement);
			return firstElement;
		}
		return null;
	}
}