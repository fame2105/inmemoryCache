package com.learn.inmemorylrucache.bootstrap;

import com.learn.inmemorylrucache.model.Employee;
import com.learn.inmemorylrucache.service.CacheService;
import com.learn.inmemorylrucache.service.EmployeeCacheServiceImpl;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final CacheService cacheService;

    public DataLoader() {
        this.cacheService = new EmployeeCacheServiceImpl();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // cache size is 3 so only employee 3,4,5 will be saved as per LRU cache eviction policy


        Employee employee1 = new Employee();
        employee1.setName("Name1");
        employee1.setDepartment("department1");
        employee1.setSalary(19029292l);
        employee1.setId(1l);

        Employee employee2 = new Employee();
        employee2.setName("Name2");
        employee2.setDepartment("department2");
        employee2.setSalary(29029292l);
        employee2.setId(2l);

        Employee employee3 = new Employee();
        employee3.setName("Name3");
        employee3.setDepartment("department3");
        employee3.setSalary(39029292l);
        employee3.setId(3l);

        Employee employee4 = new Employee();
        employee4.setName("Name4");
        employee4.setDepartment("department4");
        employee4.setSalary(49029292l);
        employee4.setId(4l);

        Employee employee5 = new Employee();
        employee5.setName("Name5");
        employee5.setDepartment("department5");
        employee5.setSalary(59029292l);
        employee5.setId(5l);

        cacheService.save(employee1.getId(), employee1);
        cacheService.save(employee2.getId(), employee2);
        cacheService.save(employee3.getId(), employee3);
        cacheService.save(employee4.getId(), employee4);
        cacheService.save(employee5.getId(), employee5);

        System.out.println(cacheService.get(3l).getName());
        System.out.println(cacheService.get(4l).getName());
        employee4.setName("qweq");
        System.out.println(cacheService.get(4l).getName());

        cacheService.flushCache();
        //will throw error
        System.out.println(cacheService.get(employee5.getId()));

    }


}
