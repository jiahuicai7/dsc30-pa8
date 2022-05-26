/*
 * Name: Jiahui Cai
 * PID: A16637420
 */

import java.util.Arrays;

/**
 * This is a Hashtable that has method of insert, delete, lookup, size, capacity,
 * getlog, rehash, hashstring.
 * 
 * @author Jiahui Cai
 * @since 23/05/2022
 */
public class HashTable implements IHashTable {

    /* the bridge for lazy deletion */
    private static final String BRIDGE = new String("[BRIDGE]".toCharArray());
    private static final int DEFAULT_CAP = 15;
    private static final int MIN_CAP = 5;
    private static final double LOAD_FACTOR_LIM = 0.55;
    private static final int SHIFT_LEFT = 5;
    private static final int SHIFT_RIGHT = 27;
    private static final int DOUBLE_CAP = 2;

    /* instance variables */
    private int size; // number of elements stored
    private String[] table; // data table
    private int rehashNum; // rehash numbers.
    private double loadFactor; // Loadfactor.
    private int collisionNum; // Collision times.
    private String statsLog; // Statslog.

    /**
     * This is the constructor for Hashtable using this, which takes in a default capacity of 15.
     */
    public HashTable() {
        this(DEFAULT_CAP);
    }

    /**
     * This is the coonstructor for Hashtable, which initialize some variables to be 0 and
     * set the initial capacity to be the capacity that is given.
     * @param capacity
     * @throws IllegalArgumentException if the capacity is smaller than 5.
     */
    public HashTable(int capacity) {
        // Consider the case of exception.
        if(capacity < MIN_CAP) {
            throw new IllegalArgumentException();
        }
        // Initialize variables all to be 0/empty.
        this.size = 0;
        table = new String[capacity];
        rehashNum = 0;
        loadFactor = 0;
        collisionNum = 0;
        statsLog = "";
    }

    /**
     * This method insert a value that is not present in the hashtable into the hash table.
     * @param value value to insert
     * @return true if the insertion is successful, false otherwise.
     * @throws NullPointerException if value is null.
     */
    @Override
    public boolean insert(String value) {
        // Consider the case of exception.
        if(value == null) {
            throw new NullPointerException();
        }
        // If the value already exist, do nothing and return false.
        if(this.lookup(value)) {
            return false;
        }
        // If load factor is greater than 0.55, rehash the table.
        if((double)this.size/(double)this.table.length > LOAD_FACTOR_LIM) {
            this.rehash();
        }
        // Hash the value to get an index.
        int bucket = this.hashString(value);
        int index = 0;
        // Loop through the table and find the first empty space.
        while(index < this.capacity()) {
            if(this.table[bucket] == BRIDGE || this.table[bucket] == null) {
                // Insert the value.
                this.table[bucket] = value;
                break;
            }
            // If cannot insert, there is a collision. increment collision by 1.
            collisionNum ++;
            // Update the hash index
            bucket = (bucket + 1) % this.capacity();
            index ++;
        }
        // Increase size by 1.
        this.size ++;
        return true;
    }

    /**
     * This method delete the value if the value exist in the hash table.
     * @param value value to delete
     * @return true if the deletion is successful, false otherwise.
     * @throws NullPointerException if value is null.
     */
    @Override
    public boolean delete(String value) {
        // Consider the case of exception.
        if(value == null) {
            throw new NullPointerException();
        }
        // If the value does not exist in the hash table, do nothing and return false.
        if(!this.lookup(value)) {
            return false;
        }
        // Hash the value and get an index.
        int bucket = this.hashString(value);
        int index = 0;
        // If encounter empty since start, stop looping.
        while(this.table[bucket] != null && index < this.capacity()) {
            // if we found the value
            if((this.table[bucket] != null ||this.table[bucket] != BRIDGE) &&
                    this.table[bucket].equals(value)) {
                // Set the value to be BRIDGE.
                this.table[bucket] = BRIDGE;
                break;
            }
            // Update the hash index
            bucket = (bucket + 1) % this.capacity();
            index ++ ;
        }
        // Decrease size by 1.
        this.size --;
        return true;
    }

    /**
     * This method checks whether a value exist in a hash table.
     * @param value value to look up
     * @return true if we found the value, false otherwise.
     * @throws NullPointerException if value is null.
     */
    @Override
    public boolean lookup(String value) {
        // Consider the case of exception.
        if(value == null) {
            throw new NullPointerException();
        }
        // Hash the value and get an index.
        int bucket = this.hashString(value);
        int index = 0;
        // If encounter empty since start, stop looping.
        while(this.table[bucket] != null && index < this.capacity()) {
            // if we found the value
            if((this.table[bucket] != null ||this.table[bucket] != BRIDGE) &&
                    this.table[bucket].equals(value)) {
                // return true.
                return true;
            }
            // Update the hash index
            bucket = (bucket + 1) % this.capacity();
            index ++;
        }
        // Else, return false.
        return false;
    }

    /**
     * This method retrun how many values are in the hash table.
     * @return the size of the hash table.
     */
    @Override
    public int size() {
        // Return the size.
        return this.size;
    }

    /**
     * This method returns the total capacity of the hash table.
     * @return the capacity of the hash table.
     */
    @Override
    public int capacity() {
        /// Return the capacity.
        return this.table.length;
    }

    /**
     * This method returns the recorded log.
     * @return the recorded log.
     */
    public String getStatsLog() {
        // Return the log.
        return this.statsLog;
    }

    /**
     * This method doubles the capacity of the hash table.
     */
    private void rehash() {
        // Update the number of rehash, record the loadfactor.
        rehashNum ++;
        loadFactor = (double) this.size/this.capacity();
        // Update the log.
        statsLog += "Before rehash # " + rehashNum + ": load factor " +
                String.format("%.2f", loadFactor) + ", " + collisionNum + " collision(s).\n";
        // Reset collisions to be 0.
        collisionNum = 0;

        // create an exact same table
        String[] tempTable = new String[this.capacity()];
        for(int i = 0; i < this.capacity(); i ++) {
            tempTable[i] = this.table[i];
        }

        // Create a table with doubled capacity.
        String[] tempTableFinal = new String[this.capacity() * DOUBLE_CAP];
        // Set size to be 0.
        this.size = 0;
        // Reassign table to be the new one.
        this.table = tempTableFinal;
        // Loop through the original table and insert element into new table.
        for(int i = 0; i < tempTable.length; i ++) {
            if(tempTable[i] != null && tempTable[i] != BRIDGE) {
                this.insert(tempTable[i]);
            }
        }
    }

    /**
     * This method hash the value into an index.
     * @param value
     * @return the hashed index.
     */
    private int hashString(String value) {
        // Initialize hash value.
        int hashValue = 0;
        // Loop through the value.
        for(int i = 0; i < value.length(); i++) {
            // Shift 5 to the left.
            int leftShiftValue = hashValue << SHIFT_LEFT;
            // Shift 27 to the right.
            int rightShiftValue = hashValue >>> SHIFT_RIGHT;
            // According to the lecture slides.
            hashValue = (leftShiftValue | rightShiftValue) ^ value.charAt(i);
        }
        return Math.abs(hashValue % this.capacity());
    }

    /**
     * Returns the string representation of the hash table.
     * This method internally uses the string representation of the table array.
     * DO NOT MODIFY. You can use it to test your code.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return Arrays.toString(table);
    }
}
