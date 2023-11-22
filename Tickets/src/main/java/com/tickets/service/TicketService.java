package com.tickets.service;

import com.tickets.Entity.Project;
import com.tickets.Entity.Ticket;
import com.tickets.Entity.User;
import com.tickets.Exception.TicketCreationException;
import com.tickets.Exception.TicketNotFoundException;
import com.tickets.Exception.UserNotFoundException;
import com.tickets.Repository.ProjectRepo;
import com.tickets.Repository.TicketRepo;
import com.tickets.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@Service
public class TicketService {
    @Autowired
    private TicketRepo ticketRepository;

    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private UserRepo userRepo;
    /**
     * to create a new ticket
     * @param ticket
     * @return created ticket
     */
    public Ticket createTicket(@RequestBody Ticket ticket) {
        String projectId=ticket.getProjectIn().getId();
        Project project = projectRepo.findById(projectId).orElse(null);
           ticket.setProjectIn(project);

        try {
            System.out.println("inside try ");
            if (project != null) {
                System.out.println("project is not null");
                List<Ticket> ticketsForProject = ticketRepository.findByProjectIn_Id(projectId);

                if (!ticketsForProject.isEmpty()) {
                    System.out.println("lsit is not empty");
                    Ticket lastTicket = ticketsForProject.get(ticketsForProject.size() - 1);
                    String lastCustomId = lastTicket.getCustomId();

                    System.out.println(lastCustomId);
                    String projectInitials = lastCustomId.split("-")[0];
                    int lastTicketNumber = Integer.parseInt(lastCustomId.split("-")[1]);

                    // Increment the number for the new ticket
                    int newTicketNumber = lastTicketNumber + 1;
                    ticket.setCustomId(projectInitials+"-"+newTicketNumber);
                   // System.out.println(ticket.getCustomId());

                    Ticket ticket1= ticketRepository.save(ticket);
                    User createdBy = ticket1.getCreatedBy();
                    System.out.println("created by is "+createdBy);
                    if (createdBy != null && createdBy.getId() != null) {
                        createdBy = userRepo.findById(createdBy.getId()).orElse(null);
                        ticket1.setCreatedBy(createdBy);
                        System.out.println("ticket is "+ticket1);
                    }
                    return  ticket1;

                }else {
                    ticket.setCustomId(project.getInitials().toUpperCase()+"-1");
                   // System.out.println("list is  empty");
                   // System.out.println("want to genrated coustomised id: "+project.getInitials().toUpperCase()+"-1");

                   Ticket ticket1= ticketRepository.save(ticket);
                    User createdBy = ticket1.getCreatedBy();
                    System.out.println("created by is "+createdBy);
                    if (createdBy != null && createdBy.getId() != null) {
                        createdBy = userRepo.findById(createdBy.getId()).orElse(null);
                        ticket1.setCreatedBy(createdBy);
                        System.out.println("ticket is "+ticket1);
                    }
                    return  ticket1;

                }

            }else{
                System.out.println("else  "+project.getInitials()+"-1");
                return  null;
            }

        } catch (Exception e) {

            throw new TicketCreationException("Failed to create ticket. Please try again later.");
        }
    }

    /**
     * gives the list of tickets
     * @return
     */
    public List<Ticket> getTickets() {
        try {
            return ticketRepository.findAll();
        } catch (Exception e) {
            throw new TicketNotFoundException("Falied to Load tickets ");
        }
    }

    /**
     * is to find a ticket by its id
     * @param id
     * @return ticket
     */
    public Optional<Ticket> getTicketById(String id) throws TicketNotFoundException {
        System.out.println(id);
        Optional<Ticket> existingTicket = ticketRepository.findById(id);

        if (existingTicket.isPresent()) {
            return existingTicket;
        } else {
            throw new TicketNotFoundException("Ticket not found with id: " + id);
        }
    }


    /**
     * update a ticket
     *
     * @param ticket
     * @return new ticket
     */
    public Ticket updateTicket( Ticket ticket) throws TicketNotFoundException {
        System.out.println(ticket.getProjectIn().getId());

        Optional<Project> project =projectRepo.findById(ticket.getProjectIn().getId());
        System.out.println("project is :"+project.get());
        ticket.setProjectIn(project.get());
        System.out.println(ticket);
        Optional<Ticket> existingTicket = ticketRepository.findById(ticket.getId());
        if (existingTicket.isPresent()) {
//            Ticket updatedTicket = existingTicket.get();
//            updatedTicket.setName(ticket.getName());
//            updatedTicket.setDescription(ticket.getDescription());
//            updatedTicket.setName(ticket.getName());
//            updatedTicket.setDescription(ticket.getDescription());
//            updatedTicket.setCreatedBy(ticket.getCreatedBy());
//            if(ticket.getStartDate()!=null)
//                updatedTicket.setStartDate(ticket.getStartDate());
//            if(ticket.getEndDate()!=null)
//                updatedTicket.setEndDate(ticket.getEndDate());
//            if(ticket.getCustomId()!=null)
//                updatedTicket.setCustomId(ticket.getCustomId());
//            if(ticket.getEndTime()!=null)
//                updatedTicket.setEndTime(ticket.getEndTime());
//            if(ticket.getProjectIn()!=null)
//                updatedTicket.setProjectIn(ticket.getProjectIn());
//            if(ticket.getStage()!=null)
//                updatedTicket.setStage(ticket.getStage());
//            if(ticket.getPriority()!=null)
//                updatedTicket.setPriority(ticket.getPriority());
//            if(ticket.getParent()!=null)
//                updatedTicket.setParent(ticket.getParent());
//            if(ticket.getCreatedBy()!=null)
//                updatedTicket.setCreatedBy(ticket.getCreatedBy());
//            if(ticket.getBlockedBy()!=null)
//                updatedTicket.setBlockedBy(ticket.getBlockedBy());
//            if(ticket.getUsersAssignedTo()!=null)
//                updatedTicket.setUsersAssignedTo(ticket.getUsersAssignedTo());
//            if(ticket.getCustomFields()!=null)
//                updatedTicket.setCustomFields(ticket.getCustomFields());

            Ticket ticket1= ticketRepository.save(ticket);
            User createdBy = ticket1.getCreatedBy();

            if (createdBy != null && createdBy.getId() != null) {
                createdBy = userRepo.findById(createdBy.getId()).orElse(null);
                ticket1.setCreatedBy(createdBy);
                System.out.println("ticket is "+ticket1);
            }
            return  ticket1;
        } else {
            throw new TicketNotFoundException("Ticket not found with id: " + ticket.getId());
        }
    }


    /**
     * delete a ticket by ticket id
     * @param id
     * @return
     */
    public Boolean deleteTicket( String id) {
        try {
            ticketRepository.deleteById(id);
            return  true;
        }catch (Exception e){
            return  false;
        }
    }

    /**
     * this is to get all the tickets from user id
     * @param userId
     * @return list of tickets
     */
    public List<Ticket> getTicketsByAssignee( String userId) {
        try {
            System.out.println("hi this is find by user id");
            return ticketRepository.findByUsersAssignedTo_Id(userId);
        } catch (Exception e) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
    }

    /**
     * this is to get all the tickets from project id
     * @param projectId
     * @return list of tickets
     */
    public List<Ticket> getTicketsByProject( String projectId) {
        return ticketRepository.findByProjectIn_Id(projectId);
    }

    /**
     * this is to get tickets by parent id
     * @param parentId
     * @return
     */
    public List<Ticket> getTicketsByParent( String parentId) {
        return ticketRepository.findByParent_Id(parentId);
    }

    public void deleteTicketsByProjectId(String projectId) {
        ticketRepository.deleteByProjectIn_Id(projectId);
    }
}
