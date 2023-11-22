package com.tickets.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReestimatedTicket {
    @Id
    private String id;
    private Date newDate;
    private String Reason;
    private String  status="Pending";
    @DBRef
    private Ticket ticket;
    @DBRef
    private User restimatedBy;
    @DBRef
    private User assignedTo;
}
