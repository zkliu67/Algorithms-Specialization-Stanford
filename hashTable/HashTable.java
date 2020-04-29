package hashTable;

// Implement with Chaining

import java.util.ArrayList;
import java.util.Iterator;

public abstract class HashTable<K, V>{

    private int numBucket; // Current capacity of bucket;
    private int size;

    public HashTable(int bucket) {
        numBucket = bucket;
    }

    public boolean isEmpty() {return size() == 0;}

    public int size() {return size;}
    public void increSize() {size ++;}
    public void decreSize() {size --;}
    public void resize() {
        numBucket = 2*numBucket;
        size = 0;
    }

    public int getNumBucket() {return numBucket;}

    // This help function behaves as a hashing function that 1. finding if an input key exists,
    // or 2. insert a new hashed index into the bucket.

    public abstract V remove(K key);
    public abstract V get(K key);

    public V getOrDefault(K key, V def) {
        V val = get(key);
        if (val != null) {
            return val;
        } return def;
    }

    public abstract void add(K key, V value);

    public abstract void printTH();
}
