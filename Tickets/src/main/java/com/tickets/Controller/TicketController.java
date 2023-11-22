package com.tickets.Controller;

import com.tickets.DTO.RequestBodyTicket;
import com.tickets.DTO.ResponseToSend;
import com.tickets.Entity.Priority;
import com.tickets.Entity.Project;
import com.tickets.Entity.Ticket;
import com.tickets.Entity.User;
import com.tickets.Exception.TicketCreationException;
import com.tickets.Exception.TicketNotFoundException;
import com.tickets.Exception.UserNotFoundException;
import com.tickets.Repository.TicketRepo;
import com.tickets.service.TicketService;
;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/tickets")
public class TicketController {
    private static final Logger log = LoggerFactory.getLogger(TicketController.class);
    @Autowired
    private TicketService ticketService;

    /**
     * to create a new ticket
     * @param requestBodyTicket
     * @return created ticket
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createTicket(@RequestBody RequestBodyTicket requestBodyTicket) {
        log.info("inside create ticket");
        Map<String, Object> response = new HashMap<>();

        HttpStatus status = null;

Ticket newTicket = new Ticket(requestBodyTicket);
//        System.out.println(newTicket);
//        Ticket createdTicket = ticketService.createTicket(newTicket);
//        System.out.println(createdTicket);
//          response.put("message", "Ticket created successfully");
//            response.put("data", createdTicket);
//            status = HttpStatus.CREATED;
        try {
            log.info("inside ticket creation try block");
            Ticket createdTicket = ticketService.createTicket(newTicket);
            response.put("message", "Ticket created successfully");
            response.put("data", createdTicket);
            status = HttpStatus.CREATED;
        } catch (TicketCreationException e) {
            response.put("message", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        } catch (Exception e) {
            response.put("message", "An error occurred while creating a new ticket");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, status);
    }


    /**
     * gives the list of tickets
     * @return
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getTickets() {
        log.info("inside get all tickets");
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = null;

        try {
            List<Ticket> tickets = ticketService.getTickets();
            response.put("message", "Success");
            response.put("data", tickets);
            status = HttpStatus.OK;
        } catch (TicketNotFoundException e) {
            response.put("message", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        } catch (Exception e) {
            response.put("message", "An error occurred");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, status);
    }


    /**
     * is to find a ticket by its id
     * @param id
     * @return ticket
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getTicketById(@PathVariable String id) {
        log.info("inside get Tickets by id");
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = null;

        try {
            Optional<Ticket> ticket = ticketService.getTicketById(id);
            response.put("message", "Success");
            response.put("data", ticket);
            status = HttpStatus.OK;
        } catch (TicketNotFoundException e) {
            response.put("message", e.getMessage());
            response.put("data", null);
            status = HttpStatus.NOT_FOUND;
        } catch (Exception e) {
            response.put("message", "An error occurred");
            response.put("data", null);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, status);
    }


    /**
     * update a ticket
     *
     *
     * @return new ticket
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateTicket( @RequestBody RequestBodyTicket responseTicket) {
        log.info("inside update ticket method");
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.CREATED;
       System.out.println(responseTicket);
         Ticket updatedTickket = new Ticket(responseTicket);
        //System.out.println(updatedTickket);
        try {
            Ticket updatedTicket = ticketService.updateTicket( updatedTickket);

            if (updatedTicket != null) {
                response.put("message", "Ticket updated successfully");
                response.put("data", updatedTicket);
                status = HttpStatus.OK;
            } else {
                response.put("message", "Ticket not found with id: " + updatedTicket.getId());
                response.put("data", null);
                status = HttpStatus.NOT_FOUND;
            }
        } catch (TicketNotFoundException e) {
            response.put("message", e.getMessage());
            response.put("data", null);
            status = HttpStatus.NOT_FOUND;
        } catch (Exception e) {
            response.put("message", "An error occurred");
            response.put("data", null);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, status);
    }



    /**
     * delete a ticket by ticket id
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseToSend<String> deleteTicket(@PathVariable String id) {
        log.info("inside delete ticket");
        try {
            boolean status = ticketService.deleteTicket(id);
            if (status) {
                return new ResponseToSend<>("Ticket with ID " + id + " has been deleted.", null, HttpStatus.OK);
            } else {
                return new ResponseToSend<>("Error occurred while deleting.", null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseToSend<>("An error occurred", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * this is to get all the tickets from user id
     * @param userId
     * @return list of tickets
     */
    @GetMapping("/byAssignee/{userId}")
    public ResponseEntity<Map<String, Object>> getTicketsByAssignee(@PathVariable String userId) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status;

        try {
            List<Ticket> tickets = ticketService.getTicketsByAssignee(userId);
            response.put("message", "Success");
            response.put("data", tickets);
            status = HttpStatus.OK;
        } catch (UserNotFoundException e) {
            response.put("message", e.getMessage());
            response.put("data", null);
            status = HttpStatus.NOT_FOUND;
        } catch (Exception e) {
            response.put("message", "An error occurred");
            response.put("data", null);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, status);
    }


    /**
     * this is to get all the tickets from project id
     * @param projectId
     * @return list of tickets
     */
    @GetMapping("/byProject/{projectId}")
    public ResponseToSend<List<Ticket>> getTicketsByProject(@PathVariable String projectId) {
        log.info("get tickets list  by project id" );
        try {
            List<Ticket> tickets = ticketService.getTicketsByProject(projectId);
            return new ResponseToSend<>("Success", tickets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseToSend<>("An error occurred", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * this is to get tickets by parent id
     * @param parentId
     * @return
     */

    @GetMapping("/byParent/{parentId}")
    public ResponseToSend<List<Ticket>> getTicketsByParent(@PathVariable String parentId) {
        try {
            List<Ticket> tickets = ticketService.getTicketsByParent(parentId);
            return new ResponseToSend<>("Success", tickets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseToSend<>("An error occurred", null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
