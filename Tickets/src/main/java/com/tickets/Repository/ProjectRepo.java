package com.tickets.Repository;

import com.tickets.Entity.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepo extends MongoRepository<Project,String> {

}
