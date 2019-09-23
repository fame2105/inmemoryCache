package com.learn.inmemorylrucache.util;

import com.learn.inmemorylrucache.exceptions.KeyNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {

    private class DNode {
        Long key;
        Object value;
        DNode prev;
        DNode next;
    }

    private Map<Long, DNode> hashtable = new HashMap<Long, DNode>();
    private DNode head, tail;
    private int totalItemsInCache;
    private int maxCapacity;

    public LRUCache(int maxCapacity) {

        // Cache starts empty and capacity is set by client
        totalItemsInCache = 0;
        this.maxCapacity = maxCapacity;

        head = new DNode();
        head.prev = null;

        tail = new DNode();
        tail.next = null;

        head.next = tail;
        tail.prev = head;
    }

    /*
      Retrieve an item from the cache
      //get
    */
    public Object get(Long key) throws KeyNotFoundException {

        DNode node = hashtable.get(key);
        boolean itemFoundInCache = node != null;

        if(!itemFoundInCache){
            throw new KeyNotFoundException("No value found for this key " + key);
        }

        // Item has been accessed. Move to the front of the list.
        moveToHead(node);

        return node.value;
    }

    //add, update
    public void put(Long key, Object value) {

        DNode node = hashtable.get(key);
        boolean itemFoundInCache = node != null;

        if(!itemFoundInCache){

            // Create a new node
            DNode newNode = new DNode();
            newNode.key = key;
            newNode.value = value;

            // Add to the hashtable and the doubly linked list
            hashtable.put(key, newNode);
            addNode(newNode);

            // We just added an item to the cache
            totalItemsInCache++;

            // If over capacity evict an item with LRU cache eviction policy
            if(totalItemsInCache > maxCapacity){
                removeLRUEntryFromStructure();
            }

        } else {
            // If item is in cache just update and move it to the head
            node.value = value;
            moveToHead(node);
        }

    }

    public void flush(){
        totalItemsInCache = 0;
        this.hashtable = new HashMap<>();
        head.next = tail;
        tail.prev = head;
    }

    /*
      Remove the least used entry from the doubly linked
      list as well as the hashtable. Hence it is evicted
      from the whole LRUCache structure
    */
    private void removeLRUEntryFromStructure() {
        DNode tail = popTail();
        hashtable.remove(tail.key);
        --totalItemsInCache;
    }

    /*
      Insertions to the doubly linked list will always
      be right after the dummy head
    */
    private void addNode(DNode node){
        node.prev = head;
        node.next = head.next;

        head.next.prev = node;

        head.next = node;
    }

    /*
      Remove the given node from the doubly linked list
    */
    private void removeNode(DNode node){

        // Grab reference to the prev and next of the node
        DNode savedPrev = node.prev;
        DNode savedNext = node.next;

        savedPrev.next = savedNext;
        savedNext.prev = savedPrev;
    }

    /*
      Move a node to the head of the doubly linked lsit
    */
    private void moveToHead(DNode node){
        removeNode(node);
        addNode(node);
    }

    /*
      Pop the last item from the structure
    */
    private DNode popTail(){
        DNode itemBeingRemoved = tail.prev;
        removeNode(itemBeingRemoved);
        return itemBeingRemoved;
    }

}
