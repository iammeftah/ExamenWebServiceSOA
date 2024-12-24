package wav.hmed.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import wav.hmed.orderservice.model.Order;
import wav.hmed.orderservice.service.OrderService;
import wav.hmed.orderservice.dto.ProductResponse;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GraphQLController {
    private final OrderService orderService;

    @QueryMapping
    public Order order(@Argument Long id) {
        return orderService.getOrder(id);
    }

    @QueryMapping
    public List<Order> allOrders() {
        return orderService.getAllOrders();
    }

    @QueryMapping
    public ProductResponse productById(@Argument Long id) {
        return orderService.getProduct(id);
    }

    @MutationMapping
    public Order createOrder(@Argument OrderInput input) {
        Order order = new Order();
        order.setProductId(input.getProductId());
        order.setQuantity(input.getQuantity());
        order.setUserId(input.getUserId());
        return orderService.createOrder(order);
    }

    @MutationMapping
    public Order updateOrderStatus(@Argument Long id, @Argument String status) {
        return orderService.updateOrderStatus(id, status);
    }

    @SchemaMapping
    public ProductResponse product(Order order) {
        return orderService.getProduct(order.getProductId());
    }
}