package com.learn.inmemorylrucache;

import com.learn.inmemorylrucache.exceptions.KeyNotFoundException;
import com.learn.inmemorylrucache.model.Employee;
import com.learn.inmemorylrucache.service.EmployeeService;
import com.learn.inmemorylrucache.util.LRUCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.*;

@RunWith(MockitoJUnitRunner.class)
public class InmemoryLrucacheApplicationTests {

    @InjectMocks
    private EmployeeService employeeService;
    private static final int cacheSize = 3;
    private static LRUCache cache = LRUCache.getInstance(cacheSize);

    @Test(expected = KeyNotFoundException.class)
    public void saveDataIntoCache() throws KeyNotFoundException {
        List<Employee> employees = getEmployeeData();
        Employee e1 = employees.get(0);
        Employee e2 = employees.get(1);
        Employee e3 = employees.get(2);
        Employee e4 = employees.get(3);
        Employee e5 = employees.get(4);

        cache.put(String.valueOf(e1.getId()), e1);
        cache.put(String.valueOf(e2.getId()), e2);
        cache.put(String.valueOf(e3.getId()), e3);
        cache.put(String.valueOf(e4.getId()), e4);
        cache.put(String.valueOf(e5.getId()), e5);

        Employee cacheEmployee5 = (Employee) cache.get(String.valueOf(e5.getId()));
        Employee cacheEmployee4 = (Employee) cache.get(String.valueOf(e4.getId()));
        Employee cacheEmployee3 = (Employee) cache.get(String.valueOf(e3.getId()));

        assertEquals(e5.getName(), cacheEmployee5.getName());
        assertEquals(e4.getName(), cacheEmployee4.getName());
        assertEquals(e3.getName(), cacheEmployee3.getName());
        // Since Cache size is configured to be 3 so e1 and e2 won't be available in the cache as per Cache eviction policy -> LRU.
        Employee cacheEmployee1 = (Employee) cache.get(String.valueOf(e1.getId()));
    }

    @Test
    public void updateCacheData() throws KeyNotFoundException{
        Employee e1 = getEmployeeData().get(0);
        cache.put(String.valueOf(e1.getId()), e1);

        Employee cacheEmployee1 = (Employee) cache.get(String.valueOf(e1.getId()));
        assertEquals(e1.getName(), cacheEmployee1.getName());

        e1.setName("updatedName");
        employeeService.save(e1);

        cacheEmployee1 = (Employee) cache.get(String.valueOf(e1.getId()));
        assertEquals(e1.getName(), cacheEmployee1.getName());
    }

    @Test(expected = KeyNotFoundException.class)
    public void deleteDataFromCache() throws KeyNotFoundException{
        List<Employee> employees = getEmployeeData();
        Employee e1 = employees.get(0);
        Employee e2 = employees.get(1);
        Employee e3 = employees.get(2);

        cache.put(String.valueOf(e1.getId()), e1);
        cache.put(String.valueOf(e2.getId()), e2);
        cache.put(String.valueOf(e3.getId()), e3);

        assertEquals(3, cache.getTotalItemsInCache());

        cache.remove(String.valueOf(e1.getId()));

        assertEquals(2, cache.getTotalItemsInCache());

        // will throw keyNotFoundException when trying to access the deleted object from cache
        cache.get(String.valueOf(e1.getId()));
    }

    @Test()
    public void flushCache(){
        List<Employee> employees = getEmployeeData();
        Employee e1 = employees.get(0);
        Employee e2 = employees.get(1);
        Employee e3 = employees.get(2);
        Employee e4 = employees.get(3);
        Employee e5 = employees.get(4);

        cache.put(String.valueOf(e1.getId()), e1);
        cache.put(String.valueOf(e2.getId()), e2);
        cache.put(String.valueOf(e3.getId()), e3);
        cache.put(String.valueOf(e4.getId()), e4);
        cache.put(String.valueOf(e5.getId()), e5);

        assertEquals(3, cache.getTotalItemsInCache());
        cache.flush();
        assertEquals(0, cache.getTotalItemsInCache());
    }

    private List<Employee> getEmployeeData() {
        cache.flush();
        Employee employee1 = new Employee();
        employee1.setName("Name1");
        employee1.setDepartment("department1");
        employee1.setSalary(19029292l);
        employee1.setId(employeeService.save(employee1).getId());

        Employee employee2 = new Employee();
        employee2.setName("Name2");
        employee2.setDepartment("department2");
        employee2.setSalary(29029292l);
        employee2.setId(employeeService.save(employee2).getId());

        Employee employee3 = new Employee();
        employee3.setName("Name3");
        employee3.setDepartment("department3");
        employee3.setSalary(39029292l);
        employee3.setId(employeeService.save(employee3).getId());

        Employee employee4 = new Employee();
        employee4.setName("Name4");
        employee4.setDepartment("department4");
        employee4.setSalary(49029292l);
        employee4.setId(employeeService.save(employee4).getId());

        Employee employee5 = new Employee();
        employee5.setName("Name5");
        employee5.setDepartment("department5");
        employee5.setSalary(59029292l);
        employee5.setId(employeeService.save(employee5).getId());

        return Arrays.asList(employee1, employee2, employee3, employee4, employee5);
    }
}
