package com.tickets.Controller;

import com.tickets.DTO.ReestimatedTicketDTO;
import com.tickets.Entity.ReestimatedTicket;
import com.tickets.Entity.Ticket;
import com.tickets.Entity.User;
import com.tickets.Exception.ReestimationCreationException;
import com.tickets.service.RestiamtedTicketService;
import com.tickets.service.TicketService;
import com.tickets.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/tickets")
public class ReestimatedTicketController {

    @Autowired
    private RestiamtedTicketService reestimatedTicketService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @PostMapping("/{id}/re-estimation")
    public ResponseEntity<Map<String, Object>> createReestimatedTicket( @PathVariable String id, @RequestBody ReestimatedTicket reestimatedTicket) {
        System.out.println("inide create restimation ticket");
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = null;

        try {
            System.out.println("inised try block");
           ReestimatedTicketDTO createdReestimatedTicket= reestimatedTicketService.createReestimatedTicket(id,reestimatedTicket);
            response.put("message", "Reestimated ticket created successfully");
            response.put("data", createdReestimatedTicket);
            status = HttpStatus.CREATED;


        } catch (ReestimationCreationException e) {
            System.out.println("exception");
            response.put("message", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        } catch (Exception e) {
            response.put("message", "An error occurred while creating a reestimated ticket");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, status);
    }

//    //    by ticket id
//    @GetMapping("/{id}/re-estimation/history")
//    public ResponseEntity<Object> getReestimatedTicketById(@PathVariable String id) {
//        Map<String, Object> response = new HashMap<>();
//        HttpStatus status = HttpStatus.OK;
//
//        try {
//            Optional<List<ReestimatedTicket>> reestimatedTickets = reestimatedTicketService.getReestimatedTicketById(id);
//
//            if (reestimatedTickets.isPresent()) {
//                response.put("message", "Success");
//                response.put("data", reestimatedTickets.get());
//            } else {
//                response.put("message", "Reestimated Tickets not found for ticket ID: " + id);
//                response.put("data", null);
//                status = HttpStatus.NOT_FOUND;
//            }
//        } catch (Exception e) {
//            response.put("message", "An error occurred");
//            response.put("data", null);
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//
//        return new ResponseEntity<>(response, status);
//    }
//
//    //    by logged in user id
    @GetMapping("/{id}/re-estimation")
    public ResponseEntity<Object> getReestimatedTicketByUserId(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = null;

        try {
            Optional<List<ReestimatedTicket>> reestimatedTickets = reestimatedTicketService.getReestimatedTicketByUserId(id);

            if (reestimatedTickets.isPresent()) {
                response.put("message", "Success");
                response.put("data", reestimatedTickets.get());
                status=HttpStatus.CREATED;
            } else {
                response.put("message", "Reestimated Tickets not found for ticket ID: " + id);
                response.put("data", null);
                status = HttpStatus.NOT_FOUND;
            }
        } catch (Exception e) {
            response.put("message", "An error occurred");
            response.put("data", null);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, status);
    }
//
//
//
    @PutMapping("/{id}/re-estimation")
    public ResponseEntity<Object> updateReestimatedTicket(@PathVariable String id, @RequestBody ReestimatedTicket reestimatedTicket) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.CREATED;

        try {
            ReestimatedTicket updatedReestimatedTicket = reestimatedTicketService.updateReestimatedTicket(id, reestimatedTicket);

            if (updatedReestimatedTicket != null) {
                response.put("message", "Ticket updated successfully");
                response.put("data", updatedReestimatedTicket);
                status = HttpStatus.OK;
            } else {
                response.put("message", "Reestimated Ticket not found with id: " + id);
                response.put("data", null);
                status = HttpStatus.NOT_FOUND;
            }
        } catch (Exception e) {
            response.put("message", "An error occurred");
            response.put("data", null);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(response, status);
    }
//
//
//
//
//    @DeleteMapping("/re-estimation/{id}")
//    public ResponseEntity<Object> deleteReestimatedTicket(@PathVariable String id) {
//        Map<String, Object> response = new HashMap<>();
//        HttpStatus status = HttpStatus.OK;
//
//        try {
//            String deletionResult = reestimatedTicketService.deleteReestimatedTicket(id);
//
//            if (deletionResult.equals("Deleted")) {
//                response.put("message", "Reestimated Ticket deleted successfully");
//                response.put("data", null);
//            } else if (deletionResult.equals("Not Found")) {
//                response.put("message", "Reestimated Ticket not found with ID: " + id);
//                response.put("data", null);
//                status = HttpStatus.NOT_FOUND;
//            }
//        } catch (Exception e) {
//            response.put("message", "An error occurred");
//            response.put("data", null);
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//
//        return new ResponseEntity<>(response, status);
//    }


}