package hashTable;

import java.util.ArrayList;

public class ChainingHT<K,V> extends HashTable<K,V> {
    /***
     * In this class we implement Hash Table with chaining methodology.
     * Params:
     * 1. load factor: checking for load factor every time inserting a new (key, value) pair,
     *    double the hash table size when load factor is over 0.7
     */
    public static class HashNode<k, v> {
        k key;
        v value;
        HashNode<k, v> next; // point to the next node;

        public HashNode(k k, v v) {
            key = k;
            value = v;
        }

        public String toString() {
            return "Key: " + key + " , value: " + value;
        }
    }

    private ArrayList<HashNode<K, V>> bucketArray;

    public ChainingHT(int bucket) {
        super(bucket);
        bucketArray = new ArrayList<>();

        for (int i = 0; i < bucket; i++){
            bucketArray.add(null);
        }

    }

    // This help function behaves as a hashing function that 1. finding if an input key exists,
    // or 2. insert a new hashed index into the bucket.
    private int getBucketIndex(K key) {
        int hashCode = Math.abs(key.hashCode());
        return hashCode % getNumBucket();
    }

    public V remove(K key) {
        int bucketIndex = getBucketIndex(key);
        HashNode<K, V> head = bucketArray.get(bucketIndex);

        HashNode<K, V> prev = null;
        while (head != null) {
            // if key found, break out of the loop
            if (head.key.equals(key)) {
                break;
            }
            prev = head;
            head = head.next;
        }

        if (head == null) {return null;}
        decreSize();

        if (prev != null) {
            prev.next = head.next;
        } else {
            bucketArray.set(bucketIndex, head.next);
        }
        return head.value;
    }

    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        HashNode<K, V> head = bucketArray.get(bucketIndex);

        while (head != null) {
            if (head.key.equals(key)) {
                return head.value;
            }
            head = head.next;
        }
        return null;
    }

    public V getOrDefault(K key, V def) {
        V val = get(key);
        if (val != null) {
            return val;
        } return def;
    }

    public void add(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        HashNode<K, V> head = bucketArray.get(bucketIndex);

        // Check whether the bucket head exists.
        // If the input key value also exist, update it.
        while (head != null) {
            if (head.key.equals(key)) {
                head.value = value;
                return;
            }
            head = head.next;
        }

        // Insert the key into the bucket Chain
        increSize();
        head = bucketArray.get(bucketIndex);
        HashNode<K, V> newNode = new HashNode<K, V>(key, value);
        newNode.next = head;
        bucketArray.set(bucketIndex, newNode);

        // Check for the load factor: # of nodes / # of bucket
        if ((1.0*size()) / getNumBucket() >= 0.99) {
            ArrayList<HashNode<K, V>> temp = bucketArray;
            bucketArray = new ArrayList<>();
            resize();
            for (int i = 0; i < getNumBucket(); i++) {
                bucketArray.add(null);
            }

            for (HashNode<K, V> headNode : temp) {
                while (headNode != null) {
                    add(headNode.key, headNode.value);
                    headNode = headNode.next;
                }
            }
        }
    }

    public void printTH() {
        for (HashNode<K, V> node : bucketArray) {
            if (node != null) {
                HashNode<K,V> curr = node;
                while (curr != null) {
                    System.out.print(curr + " ");
                    curr = curr.next;
                }
                System.out.println();
            }
        }
    }
}
