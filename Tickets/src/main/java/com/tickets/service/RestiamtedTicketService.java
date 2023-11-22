package com.tickets.service;

import com.tickets.DTO.ReestimatedTicketDTO;
import com.tickets.Entity.ReestimatedTicket;
import com.tickets.Entity.Ticket;
import com.tickets.Entity.User;
import com.tickets.Exception.ReestimationCreationException;
import com.tickets.Exception.TicketNotFoundException;
import com.tickets.Repository.RestimationRepo;
import com.tickets.Repository.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class RestiamtedTicketService {

    @Autowired
    private RestimationRepo reestimatedTicketRepository;

    @Autowired
    private TicketRepo ticketRepository;


    public ReestimatedTicketDTO createReestimatedTicket( String id, ReestimatedTicket reestimatedTicket) {
        Optional<Ticket> currentTicket=ticketRepository.findById(id);
        if (currentTicket.get() != null) {
            System.out.println("inside if ");
            System.out.println(reestimatedTicket);
            reestimatedTicket.setTicket(currentTicket.get());

            User assignedTo = currentTicket.get().getCreatedBy();
            reestimatedTicket.setAssignedTo(assignedTo);


            try {
                System.out.println("inside try");
              return new ReestimatedTicketDTO(reestimatedTicketRepository.save(reestimatedTicket));
            } catch (Exception e) {
                throw new ReestimationCreationException("Failed to create reestimated ticket. Please try again later.");
            }

        } else {
            throw new TicketNotFoundException("ticket not found");
        }

//        try {
//            System.out.println("re-estimation try" );
//            System.out.println(reestimatedTicket);
//            return reestimatedTicketRepository.save(reestimatedTicket);
//        } catch (Exception e) {
//            System.out.println("Re-estimation error: "+e);
//            throw new ReestimationCreationException("Failed to create ticket. Please try again later.");
//        }
    }

//
//    public Optional<List<ReestimatedTicket>> getReestimatedTicketById(String id) {
//        return reestimatedTicketRepository.findByTicket_Id(id);
//    }
//
//
    public Optional<List<ReestimatedTicket>> getReestimatedTicketByUserId(String id) {
        return reestimatedTicketRepository.findByAssignedToAndStatus(id,"Pending");
    }
//
    public ReestimatedTicket updateReestimatedTicket(String id, ReestimatedTicket reestimatedTicket) {
        Optional<ReestimatedTicket> existingReestimatedTicket = reestimatedTicketRepository.findById(id);

        if (existingReestimatedTicket.isPresent()) {
            ReestimatedTicket updatedTicket = existingReestimatedTicket.get();

            if ("Approved".equals(reestimatedTicket.getStatus())) {
                // If the status is "Approved," update the status and endDate
                updatedTicket.setStatus(reestimatedTicket.getStatus());

                // Extract the ticket ID from the reestimatedTicket
                String ticketId = updatedTicket.getTicket().getId();

                // Find the corresponding ticket
                Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
                if (ticket != null) {
                    System.out.println(updatedTicket.getNewDate());
                    ticket.setEndDate(updatedTicket.getNewDate());
                    System.out.println(ticket.getEndDate());
                    ticketRepository.save(ticket);
                }
            } else {
                updatedTicket.setStatus(reestimatedTicket.getStatus());
            }

            return reestimatedTicketRepository.save(updatedTicket);
        } else {
            return null;
        }
    }
//
////    public ReestimatedTicket updateReestimatedTicket(String id, ReestimatedTicket reestimatedTicket) {
////        Optional<ReestimatedTicket> existingReestimatedTicket = reestimatedTicketRepository.findById(id);
////
////        if (existingReestimatedTicket.isPresent()) {
////            ReestimatedTicket updatedTicket = existingReestimatedTicket.get();
////
////            if (reestimatedTicket.getStatus() != null && !reestimatedTicket.getStatus().isEmpty()) {
////                updatedTicket.setStatus(reestimatedTicket.getStatus());
////            }
////
////            Ticket ticket = ticketRepository.findById(id).orElse(null);
////            if (ticket != null) {
////                ticket.setEndDate(reestimatedTicket.getNewDate());
////                ticketRepository.save(ticket);
////            }
////
////            return reestimatedTicketRepository.save(updatedTicket);
////        } else {
////            return null;
////        }
////    }
//
//    public String deleteReestimatedTicket(String id) {
//        reestimatedTicketRepository.deleteById(id);
//        return "Reestimated Ticket with ID " + id + " has been deleted.";
//    }
}