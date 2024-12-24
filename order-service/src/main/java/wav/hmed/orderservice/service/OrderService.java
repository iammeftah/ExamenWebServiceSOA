package wav.hmed.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import wav.hmed.orderservice.model.Order;
import wav.hmed.orderservice.model.ProductResponse;
import wav.hmed.orderservice.repository.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final WebClient.Builder webClientBuilder;

    public Order createOrder(Order order) {
        // Verify product exists and has enough quantity
        ProductResponse product = getProduct(order.getProductId());
        if (product == null || product.getQuantity() < order.getQuantity()) {
            throw new RuntimeException("Product not available in requested quantity");
        }

        Order savedOrder = orderRepository.save(order);

        // Send notification via Kafka
        kafkaTemplate.send("order-events",
                String.format("New order created: %s", savedOrder.getId()));

        return savedOrder;
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateOrderStatus(Long id, String status) {
        Order order = getOrder(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public ProductResponse getProduct(Long id) {
        return webClientBuilder.build()
                .get()
                .uri("http://product-service/api/products/" + id)
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .block();
    }
}