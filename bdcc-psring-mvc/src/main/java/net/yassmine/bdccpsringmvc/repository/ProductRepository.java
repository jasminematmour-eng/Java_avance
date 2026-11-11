package net.yassmine.bdccpsringmvc.repository;

import net.yassmine.bdccpsringmvc.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
