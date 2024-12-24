package wav.hmed.orderservice.model;

import lombok.Data;

@Data
public class OrderInput {
    private Long productId;
    private Integer quantity;
    private String userId;
}