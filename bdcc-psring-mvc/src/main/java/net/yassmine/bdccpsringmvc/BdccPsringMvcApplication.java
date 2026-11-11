package net.yassmine.bdccpsringmvc;

import net.yassmine.bdccpsringmvc.entities.Product;
import net.yassmine.bdccpsringmvc.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BdccPsringMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(BdccPsringMvcApplication.class, args);
    }

    @Bean
    public CommandLineRunner start(ProductRepository productRepository){
        return args -> {
            productRepository.save(Product.builder()
                    .name("computer")
                    .price(5400)
                    .quantity(12)
                    .build());
            productRepository.save(Product.builder()
                    .name("printer")
                    .price(1200)
                    .quantity(11)
                    .build());
            productRepository.save(Product.builder()
                    .name("smart phone")
                    .price(8400)
                    .quantity(33)
                    .build());
            productRepository.findAll().forEach(p->{
            System.out.println(p.toString());

        });
    };

}
