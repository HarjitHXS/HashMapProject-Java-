import java.util.*;

/**
 * @authors Carlos Rodriguez and Harjit Singh
 */


public class Chain<K, V> {
    /**
     * hashTable = The HashTable of type array where each index will have a LinkedList
     * 			   of type HMNode(Entry)<K, V>
     * numElements = The total number of elements found in the HashTable
     * CAPACITY = the initial size of the array
     */
    private LinkedList<HMNode<K, V>>[] hashTable;
    private int numElements;
    private static final int CAPACITY = 5;
    private static final double LOAD_THRESHOLD = 3.0;




    //Node class
    private static class HMNode<K, V> implements Map.Entry<K, V>{
        /**
         * K key = Key of the HashTable
         * V value = Value found using key
         */

        private K key;
        private V value;

        //Default Constructor
        public HMNode() {
            this.key = null;
            this.value = null;
        }

        //Constructing a node that stores the key, value and links to next node
        public HMNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        //Getters
        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public boolean equals(Object obj) {
            if(this == obj) {
                return true;
            }
            if(obj.getClass() != this.getClass()) {
                return false;
            }

            HMNode<K, V> chainObj = (HMNode<K, V>) obj;
            return this.value.equals(chainObj.value);
        }
        /**
         * Returns a string representation of the Entry
         * @return = string representation of Entry
         */
        public String toString() {
            return this.key.toString() + " = " + this.value.toString();
        }

        /**
         * Returns old value found in the node
         * @param value = The new value to store
         * @return = Previous value stored
         */
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }

    //Default Constructor
    @SuppressWarnings("unchecked")
    public Chain(){
        this.hashTable = new LinkedList[CAPACITY];
        this.numElements = 0;
    }
    /**
     * Returns the value obtained
     * @param key = Key to search the value in
     * @return = Value found, null otherwise
     */
    public V get(K key) {
        int index = this.hashCode(key);
        // Search the list at table[index] to find the key.
        for (HMNode<K, V> nextItem : hashTable[index]) {
            if (nextItem.key.equals(key)) {
                return nextItem.value;
            }
        }

        // assert: key is not in the table.
        return null;
    }
    /**
     * Add a value to the table
     * @param key = Key to find and add the value in
     * @param value = Value to add
     * @return = Value entered if key present, null otherwise
     */
    public V put(K key, V value) {
        int index = this.hashCode(key);
        if (index < 0) {
            index += this.hashTable.length;
        }
        //Creating a linked list if the table's current index is null
        if(this.hashTable[index] == null) {
            this.hashTable[index] = new LinkedList<HMNode<K, V>>();
        }

        //Assuming the table current index is not null
        for(HMNode<K, V> nextItem : this.hashTable[index]) {
            if(nextItem.key.equals(key)) {
                this.hashTable[index].add(new HMNode<K, V>(key, value));
                this.numElements++;
                //To check if the key is found and rehash is needed
                if (this.numElements > (LOAD_THRESHOLD * this.hashTable.length)) {
                    rehash();
                }
                return value;
            }
        }
        //If the key does not match therefore does not exist:
        this.hashTable[index].addFirst(new HMNode<K, V>(key, value));
        this.numElements++;

        if (this.numElements > (LOAD_THRESHOLD * this.hashTable.length)) {
            rehash();
        }
        return null;
    }

    /**
     * Returns whether the key entered is present or not in the Hash Table
     * @param key = The key to search
     * @return True if found, false otherwise
     */
    public boolean containsKey(K key){
        int index = this.hashCode(key);

        for(HMNode< K, V > entry : hashTable[index]){
            if(entry.key.equals(key)){
                return true;
            }
        }
        return false;
    }
    /**
     * Returns the total of values in the Hash Table
     * @return = Total number of values
     */
    public int size() {
        return this.numElements;
    }


    public boolean isEmpty() {
        if(this.numElements == 0)
            return true;
        return false;
    }
    public int getIndex(K key){
        return key.hashCode(); //Returns the hashCode of the Key. hashCode() is a built-in Java function
    }
    /**
     * Generates a hashcode
     * @param key = The key to hash
     * @return = The index obtained from the hash
     */
    public int hashCode(K key){
        int inde = getIndex(key);
        int ix =(inde & 0x7fffffff) % hashTable.length;
        return ix;
    }

    /**
     * Remove one key from the HashTable
     */
    public V remove(K key) {
        int index = this.hashCode(key);
        Iterator<HMNode<K, V>> iter = hashTable[index].iterator();
        while (iter.hasNext()) {
            HMNode<K, V> nextItem = iter.next();
            // If the search is successful, return the value.
            if (nextItem.key.equals(key)) {
                V returnValue = nextItem.value;
                iter.remove();
                return returnValue;
            }
        }
        return null; // Key not in table
    }
    /**
     * Expands table size when loadFactor exceeds LOAD_THRESHOLD
     */
    public void rehash() {
        // Save a reference to oldTable
        LinkedList<HMNode<K, V>>[] oldTable = this.hashTable;
        // Double capacity of this table
        this.hashTable = new LinkedList[2 * oldTable.length + 1];

        // Reinsert all items in oldTable into expanded table.
        this.numElements = 0;
        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] != null) {
                for (HMNode<K, V> nextEntry : oldTable[i]) {
                    // Insert entry in expanded table
                    put(nextEntry.key, nextEntry.value);
                }
            }
        }
    }
    /**
     * Returns all the keys
     * @return = All of the keys of the Hash Table
     */
    public Set<K> keySet(){
        HashSet<K> allKeys = new HashSet<K>();
        for(int i = 0; i < this.hashTable.length; i++) {
            if(this.hashTable[i] != null) {
                K foundKey = this.hashTable[i].get(0).getKey();
                allKeys.add(foundKey);
            }
        }
        return allKeys;
    }

    /**
     * Returns ArrayList of all the value in One Room
     * @return All of the Value of the key in Hash Table
     */
    public ArrayList<String> getALL(K key) {
        int index = this.hashCode(key);
        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0; i < this.hashTable[index].size(); i++) {
            temp.add(this.hashTable[index].get(i) +"\n");

        }

        return temp;
    }
}
