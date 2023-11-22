package com.tickets.Repository;

import com.tickets.Entity.Department;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartmentRepo extends MongoRepository<Department,String> {
}
