package net.yassmine.bdccpsringmvc.web;

import net.yassmine.bdccpsringmvc.entities.Product;
import net.yassmine.bdccpsringmvc.repository.ProductRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductRepository productRepository
            ;
    @GetMapping("/index")
    public String index(Model model){
        List<Product> products = productRepository.findAll();
        model.addAttribute("productList", products);
        return "products";
    }

}
