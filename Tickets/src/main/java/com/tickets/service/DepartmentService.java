package com.tickets.service;

import com.tickets.Entity.Department;
import com.tickets.Repository.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepository;

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(String id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public Department updateDepartment(String id, Department department) {
        Department existingDepartment = getDepartmentById(id);
        if (existingDepartment != null) {
            existingDepartment.setName(department.getName());
            existingDepartment.setProjects(department.getProjects());
            return departmentRepository.save(existingDepartment);
        }
        return null;
    }

    public void deleteDepartmentById(String id) {
        departmentRepository.deleteById(id);
    }
}

