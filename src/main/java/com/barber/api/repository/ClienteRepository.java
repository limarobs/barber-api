package com.barber.api.repository;


import com.barber.api.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
