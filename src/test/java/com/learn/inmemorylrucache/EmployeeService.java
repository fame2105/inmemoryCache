package com.learn.inmemorylrucache;


import com.learn.inmemorylrucache.model.Employee;
import com.learn.inmemorylrucache.service.AbstractMapService;
import com.learn.inmemorylrucache.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService extends AbstractMapService<Employee, Long> implements CrudService<Employee, Long> {
    @Override
    public List<Employee> findAll() {
        return super.findAll();
    }

    @Override
   public Employee findById(Long aLong) {
        return super.findById(aLong);
    }

    @Override
    public Employee save(Employee object) {
        return super.save(object);
    }

    @Override
    public void deleteById(Long aLong) {
        super.deleteById(aLong);
    }

    @Override
    public void delete(Employee object) {
        super.delete(object);
    }
}
