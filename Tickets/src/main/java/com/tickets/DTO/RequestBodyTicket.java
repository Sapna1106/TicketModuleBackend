package com.tickets.DTO;

import com.tickets.Entity.Priority;
import com.tickets.Entity.Project;
import com.tickets.Entity.Ticket;
import com.tickets.Entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class RequestBodyTicket {
    private  String id;
    private String customId;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private String endTime;
    private String projectIn;
    private String stage;
    private String priority;

    private String blockedBy;
    private String parent;

    private List<String> usersAssignedTo;
    private Map<String, Object> customFields;

    @Override
    public String toString() {
        return "RequestBodyTicket{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", endTime='" + endTime + '\'' +
                ", projectIn='" + projectIn + '\'' +
                ", stage='" + stage + '\'' +
                ", priority='" + priority + '\'' +
                ", blockedBy='" + blockedBy + '\'' +
                ", parent='" + parent + '\'' +
                ", usersAssignedTo=" + usersAssignedTo +
                ", customFields=" + customFields +
                '}';
    }
}

