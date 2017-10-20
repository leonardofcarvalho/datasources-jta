package br.com.datasources.repository.customer2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository2 extends JpaRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);
    List<Customer> findAll();

}
