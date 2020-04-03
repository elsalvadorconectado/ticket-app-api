package com.devs.ticketapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devs.ticketapp.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
