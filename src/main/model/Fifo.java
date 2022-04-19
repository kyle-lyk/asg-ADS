package main.model;

import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * This class contains the logic for the 
 * first-in-first-out queue (FIFO)
 */
public class Fifo<E> {
    private LinkedHashSet<String> NgoList = new LinkedHashSet<String>();
        
	/**
	 * Enqueues the NGO that is passed to the method.
	 * @param NGO NGO's name
	 */
    public void enqueue(String NGO) {
		NgoList.add(NGO);
    }

	/**
     * Gets the NGO list.
     * @return NGO list
     */
    public LinkedHashSet<String> getNgoList() {
    	return NgoList;
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