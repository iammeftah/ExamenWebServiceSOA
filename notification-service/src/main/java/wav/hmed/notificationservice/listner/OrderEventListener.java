package wav.hmed.notificationservice.listner;

import com.hmed.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventListener {
    private final NotificationService notificationService;

    @KafkaListener(topics = "order-events", groupId = "notification-group")
    public void handleOrderEvent(String orderEvent) {
        notificationService.processOrderNotification(orderEvent);
    }
}