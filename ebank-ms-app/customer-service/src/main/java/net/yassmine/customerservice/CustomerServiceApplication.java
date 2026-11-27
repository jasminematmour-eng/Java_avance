package net.yassmine.customerservice;

import net.yassmine.customerservice.entities.Customer;
import net.yassmine.customerservice.service.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

public class CustomerServiceApplication{
    public static void main(String[] args){
        SpringApplication.run(CustomerServiceApplication.class, args);

    }
    @Bean
    CommandLineRunner commandLineRunner(CustomerService customerService ){
        return args -> {
            List<String> names = List.of("yassmine","salma","salah");
            names.forEach(name->{
                customerService.saveCustomer(Customer.builder()
                                .name(name).email(name+"@gmail.com")
                        .build());
            });
        };
    }

}