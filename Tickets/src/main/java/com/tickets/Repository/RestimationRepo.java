package com.tickets.Repository;


import com.tickets.Entity.ReestimatedTicket;

import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.*;
public interface RestimationRepo extends MongoRepository<ReestimatedTicket,String> {
    Optional<List<ReestimatedTicket>> findByTicket_Id(String ticketId);
    Optional<List<ReestimatedTicket>> findByAssignedTo(String userId);
    Optional<List<ReestimatedTicket>> findByAssignedToAndStatus(String userId, String status);
}