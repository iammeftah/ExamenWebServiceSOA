package wav.hmed.productservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import wav.hmed.productservice.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByPriceLessThan(Double price);
    Optional<Product> findByName(String name);
}