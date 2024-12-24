package wav.hmed.orderservice.controller;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wav.hmed.orderservice.model.Order;
import wav.hmed.orderservice.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @CircuitBreaker(name = "orderService", fallbackMethod = "createOrderFallback")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    public ResponseEntity<Order> createOrderFallback(Order order, Exception e) {
        // Fallback logic when product service is down
        order.setStatus("PENDING_CONFIRMATION");
        return ResponseEntity.ok(order);
    }
}