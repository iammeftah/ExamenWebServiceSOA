package wav.hmed.notificationservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {
    public void processOrderNotification(String orderEvent) {
        log.info("Processing notification: {}", orderEvent);

    }
}