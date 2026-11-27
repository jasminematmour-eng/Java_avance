package net.yassmine.customerservice.repository;

import net.yassmine.customerservice.entities.Customer;
import net.yassmine.customerservice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository <Customer, Long > {
}
