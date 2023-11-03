package pro.sky.tg_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.tg_demo.model.NotificationTask;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationTaskRepository extends JpaRepository<NotificationTask,Long> {
    List<NotificationTask> findByTimeToSendEquals(LocalDateTime time);
}
