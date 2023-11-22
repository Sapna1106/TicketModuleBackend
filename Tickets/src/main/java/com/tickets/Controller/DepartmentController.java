package com.tickets.Controller;

import com.tickets.Entity.Department;
import com.tickets.Entity.User;
import com.tickets.Repository.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    private DepartmentRepo departmentRepo;
    @PostMapping()
    public Department createDepartment(@RequestBody Department department) {
        return departmentRepo.save(department);
    }

    @GetMapping
    public List<Department> getDepartments() {
        return departmentRepo.findAll();
    }
}
