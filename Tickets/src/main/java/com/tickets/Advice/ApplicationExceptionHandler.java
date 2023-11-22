package com.tickets.Advice;

import com.tickets.DTO.ResponseToSend;
import com.tickets.Entity.Ticket;
import com.tickets.Exception.TicketCreationException;
import com.tickets.Exception.TicketNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseToSend<Ticket> handleMethodArgumentTypeMismatch(TicketNotFoundException ex) {
        String message = "Invalid input. Please provide a valid integer value.";
        return new ResponseToSend<>(message,null, HttpStatus.BAD_REQUEST);
   }

}
