package com.learn.inmemorylrucache;

import com.learn.inmemorylrucache.exceptions.KeyNotFoundException;
import com.learn.inmemorylrucache.model.Employee;
import com.learn.inmemorylrucache.service.CacheService;
import com.learn.inmemorylrucache.service.EmployeeCacheServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class InmemoryLrucacheApplicationTests {

    private CacheService cacheService;

    public InmemoryLrucacheApplicationTests(){
        cacheService = new EmployeeCacheServiceImpl();
    }

    @Test(expected = KeyNotFoundException.class)
    public void addDataIntoCache() throws KeyNotFoundException {
        Employee employee1 = new Employee();
        employee1.setName("Name1");
        employee1.setDepartment("department1");
        employee1.setSalary(19029292l);
        employee1.setId(1l);
        cacheService.save(employee1.getId(), employee1);

        Employee employee2 = new Employee();
        employee2.setName("Name2");
        employee2.setDepartment("department2");
        employee2.setSalary(29029292l);
        employee2.setId(2l);
        cacheService.save(employee2.getId(), employee2);

        Employee employee3 = new Employee();
        employee3.setName("Name3");
        employee3.setDepartment("department3");
        employee3.setSalary(39029292l);
        employee3.setId(3l);
        cacheService.save(employee3.getId(), employee3);

        Employee employee4 = new Employee();
        employee4.setName("Name4");
        employee4.setDepartment("department4");
        employee4.setSalary(49029292l);
        employee4.setId(4l);
        cacheService.save(employee4.getId(), employee4);

        Employee employee5 = new Employee();
        employee5.setName("Name5");
        employee5.setDepartment("department5");
        employee5.setSalary(59029292l);
        employee5.setId(5l);
        cacheService.save(employee5.getId(), employee5);

        //Since cache size is 3 employee1 will not be stored due to cache eviction policy of LRU Cache
        Employee emp1 = cacheService.get(employee1.getId());
        assertNull(emp1);

        Employee emp2 = cacheService.get(employee2.getId());
        assertNull(emp2);
    }

    @Test(expected = KeyNotFoundException.class)
    public void getDataFromCache() throws KeyNotFoundException{
        Employee employee1 = new Employee();
        employee1.setName("Name1");
        employee1.setDepartment("department1");
        employee1.setSalary(19029292l);
        employee1.setId(1l);
        cacheService.save(employee1.getId(), employee1);

        Employee employee2 = new Employee();
        employee2.setName("Name2");
        employee2.setDepartment("department2");
        employee2.setSalary(29029292l);
        employee2.setId(2l);
        cacheService.save(employee2.getId(), employee2);

        Employee employee3 = new Employee();
        employee3.setName("Name3");
        employee3.setDepartment("department3");
        employee3.setSalary(39029292l);
        employee3.setId(3l);
        cacheService.save(employee3.getId(), employee3);

        Employee employee4 = new Employee();
        employee4.setName("Name4");
        employee4.setDepartment("department4");
        employee4.setSalary(49029292l);
        employee4.setId(4l);
        cacheService.save(employee4.getId(), employee4);

        assertEquals(employee3.getName(), cacheService.get(employee3.getId()).getName());
        assertNull(cacheService.get(employee1.getId()));
    }

    @Test
    public void updateCacheData() throws KeyNotFoundException{
        Employee employee1 = new Employee();
        employee1.setName("Name1");
        employee1.setDepartment("department1");
        employee1.setSalary(19029292l);
        employee1.setId(1l);
        cacheService.save(employee1.getId(), employee1);

        employee1.setName("updatedName");
        assertEquals(employee1.getName(), cacheService.get(employee1.getId()).getName());
        employee1.setName("updatedNametwice");
        assertEquals(employee1.getName(), cacheService.get(employee1.getId()).getName());
    }

    @Test()
    public void flushCache() throws KeyNotFoundException {
        Employee employee1 = new Employee();
        employee1.setName("Name1");
        employee1.setDepartment("department1");
        employee1.setSalary(19029292l);
        employee1.setId(1l);
        cacheService.save(employee1.getId(), employee1);

        Employee employee2 = new Employee();
        employee2.setName("Name2");
        employee2.setDepartment("department2");
        employee2.setSalary(29029292l);
        employee2.setId(2l);
        cacheService.save(employee2.getId(), employee2);

        Employee employee3 = new Employee();
        employee3.setName("Name3");
        employee3.setDepartment("department3");
        employee3.setSalary(39029292l);
        employee3.setId(3l);
        cacheService.save(employee3.getId(), employee3);

        assertEquals(employee1.getName(), cacheService.get(employee1.getId()).getName());
        assertEquals(employee2.getName(), cacheService.get(employee2.getId()).getName());
        assertEquals(employee3.getName(), cacheService.get(employee3.getId()).getName());

        cacheService.flushCache();
        // causes exception -> KeyNotFound
        cacheService.get(employee1.getId());


    }

}
