
package com.miop.api;

/**
 * Simple data structure for grouping two values together.  Required by some API calls.
 * 
 * @param <N> first element in the pair.
 * @param <V> second element in the pair.
 */
public class Pair<N, V> {
    /**
     * The first element in the pair.
     */
    //FIXME:  should be private
    public N first;
    /**
     * The second element in the pair.
     */
    //FIXME:  should be private
    public V second;

    /**
     * Constructor.
     * 
     * @param name the first value in the pair.
     * @param value the second value in the pair.
     */
    public Pair(N name, V value) {
      this.first = name;
      this.second = value;
    }
    
    /**
     * Set the first element in the pair
     * 
     * @param first the object to set
     */
    public void setFirst(N first) {
        this.first = first;
    } 

    /**
     * @return the first object in the pair
     */
    public N getFirst() {
        return first;
    }

    /**
     * Set the second element in the pair
     * 
     * @param second the object to set
     */
    public void setSecond(V second) {
        this.second = second;
    }

    /**
     * @return the second object in the pair
     */
    public V getSecond() {
        return second;
    }
}
