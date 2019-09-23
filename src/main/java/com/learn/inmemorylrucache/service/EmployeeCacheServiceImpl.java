package com.learn.inmemorylrucache.service;


import com.learn.inmemorylrucache.exceptions.KeyNotFoundException;
import com.learn.inmemorylrucache.model.Employee;
import com.learn.inmemorylrucache.util.LRUCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmployeeCacheServiceImpl implements CacheService{

    Logger logger = LoggerFactory.getLogger(EmployeeCacheServiceImpl.class.getName());

    private LRUCache cache;
    private final int cacheSize = 3;

    public EmployeeCacheServiceImpl() {
        this.cache = new LRUCache(cacheSize);
    }

    // handles both save and update
    public void save(Long key, Employee employee) {
        cache.put(key, employee);
    }

    public Employee get(Long key) throws KeyNotFoundException {
        return (Employee) cache.get(key);
    }

    public void flushCache() {
        logger.info("Cache is flushed");
        cache.flush();
    }
}
