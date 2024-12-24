package wav.hmed.orderservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import wav.hmed.orderservice.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
   List<Order> findByUserId(String userId);
   List<Order> findByStatus(String status);
}