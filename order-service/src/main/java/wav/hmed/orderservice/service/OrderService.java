package wav.hmed.orderservice.service;


import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import wav.hmed.orderservice.client.ProductClient;
import wav.hmed.orderservice.model.Order;
import wav.hmed.orderservice.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public Order createOrder(Order order) {
        // Verify product exists
        productClient.getProduct(order.getProductId());

        Order savedOrder = orderRepository.save(order);

        // Send notification via Kafka
        kafkaTemplate.send("order-events",
                String.format("New order created: %s", savedOrder.getId()));

        return savedOrder;
    }
}