package com.tickets.Repository;

import com.tickets.Entity.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TicketRepo extends MongoRepository<Ticket, String> {
    List<Ticket> findByUsersAssignedTo_Id(String userId);
    List<Ticket> findByProjectIn_Id(String projectId);
    List<Ticket> findByParent_Id(String parentId);
    void deleteByProjectIn_Id(String projectId);
}