package com.tickets.DTO;

import com.tickets.Entity.ReestimatedTicket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReestimatedTicketDTO {

    private String id;
    private String ticketId;
    private String reason;
    private String newDate;
    private String reestimatedByName;
    private String assignedToName;

    public ReestimatedTicketDTO(ReestimatedTicket reestimatedTicket) {
        this.id = reestimatedTicket.getId();
        this.ticketId = reestimatedTicket.getTicket().getId();

        this.reason = reestimatedTicket.getReason();
        this.newDate = reestimatedTicket.getNewDate().toString();
       this.reestimatedByName = reestimatedTicket.getRestimatedBy().getFirstName();
        this.assignedToName = reestimatedTicket.getAssignedTo().getFirstName();
    }

    @Override
    public String toString() {
        return "ReestimatedTicketDTO{" +
                "id='" + id + '\'' +
                ", ticketId='" + ticketId + '\'' +
                ", reason='" + reason + '\'' +
                ", newDate='" + newDate + '\'' +
                ", reestimatedByName='" + reestimatedByName + '\'' +
                ", assignedToName='" + assignedToName + '\'' +
                '}';
    }
}

