package main.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
 
/**
 * This class contains the logic for
 * manpower amount comparison used in priority queue.
 */
class NgoManpower implements Comparable<NgoManpower> {
    String Ngo;
    int Manpower;
 
    /**
     * Constructs NgoManpower object which contains 
     * the NGO's name and manpower amount.
     * @param Ngo NGO's name
     * @param Manpower manpower amount
     */
    NgoManpower(String Ngo, int Manpower)
    {
        this.Ngo = Ngo;
        this.Manpower = Manpower;
    }
 
    /**
     * Compares the manpower amount.
     * @param n NgoManpower object
     */
    @Override public int compareTo(NgoManpower n)
    {
        if (Manpower > n.Manpower) {
            return -1;
        }
        else if (Manpower == n.Manpower) {
            return 0;
        }
        else {
            return 1;
        }
    }
}
 
/**
 * This class contains the logic for the 
 * priority queue.
 */
public class Priority<E> {
    LinkedList<NgoManpower> NgoList = new LinkedList<NgoManpower>();
    boolean duplicateFlag = false;

    /**
	 * Enqueues the NGO that is passed to the method
     * if it does not exist in the queue.
     * @param NGO NGO's name
     * @param Manpower manpower amount
	 */
    public void enqueue(String NGO, Integer Manpower) {
        duplicateFlag = false;
        for (NgoManpower n : NgoList) {
            if (n.Ngo.equals(NGO)) {
                duplicateFlag = true;
            }
        }

        if (!duplicateFlag) {
            NgoList.add(new NgoManpower(NGO, Manpower));
            Collections.sort(NgoList);
        }
    }

    /**
	 * Dequeues the NGO that is at the front of the queue
	 * and returns the dequeued element.
	 * @return first element of list
	 */
    public String dequeuePoll() {
        String firstElement = NgoList.getFirst().Ngo;
        NgoList.remove();
        return firstElement;
    }

    /**
     * Gets the NGO list.
     * @return NGO list
     */
    public String getNgoList() {
        List<String> ngoNameList = new ArrayList<String>();
        for (NgoManpower n : NgoList) {
            ngoNameList.add(n.Ngo);
        }
        return Arrays.toString(ngoNameList.toArray());
    }
}