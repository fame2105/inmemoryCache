package com.learn.inmemorylrucache.service;

import com.learn.inmemorylrucache.exceptions.KeyNotFoundException;
import com.learn.inmemorylrucache.model.Employee;

public interface CacheService {
    void save(Long key, Employee employee);
    Employee get(Long key) throws KeyNotFoundException;
    void flushCache();
}
