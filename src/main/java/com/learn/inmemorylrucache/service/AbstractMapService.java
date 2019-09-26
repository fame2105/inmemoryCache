package com.learn.inmemorylrucache.service;
import com.learn.inmemorylrucache.model.Base;

import java.util.*;

public abstract class AbstractMapService<T extends Base, ID extends Long> {

    protected Map<Long, T> map = new HashMap<>();

    public List<T> findAll(){
        return new ArrayList<>(map.values());
    }

   public  T findById(ID id) {
        return map.get(id);
    }

    /**
     * Upsert functionality, Saves the new object , but if the id for that object exists already, then updates it.
     * @param object
     * @return
     */
   public  T save(T object){
        if(object != null) {
            if(object.getId() == null){
                object.setId(getNextId());
            }

            map.put(object.getId(), object);
        } else {
            throw new RuntimeException("Object cannot be null");
        }

        return object;
    }

   public void deleteById(ID id){
        map.remove(id);
    }

    public void delete(T object){
        map.entrySet().removeIf(entry -> entry.getValue().equals(object));
    }

    /**
     * Utility method for generating new Ids when saving data in Map
     * @return
     */
    private Long getNextId(){
        Long nextId = null;
        try {
            nextId = Collections.max(map.keySet()) + 1;
        } catch (NoSuchElementException e) {
            nextId = 1L;
        }
        return nextId;
    }
}
