package study.spring_boot_c.domain.notification.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.spring_boot_c.domain.notification.domain.entity.CarrotNotification;

public interface CarrotNotificationRepository extends JpaRepository<CarrotNotification,Long> {
}
