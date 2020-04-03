package com.devs.ticketapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devs.ticketapp.model.Cola;

@Repository
public interface ColaRepository extends JpaRepository<Cola, Integer> {

}
