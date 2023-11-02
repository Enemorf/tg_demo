package pro.sky.tg_demo.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notification_task")
public class NotificationTask
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_task_seq")
    @SequenceGenerator(name = "notification_task_seq", sequenceName = "notification_task_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "chatid")
    private Long chatId;

    @Column(name = "messagetext")
    private String messageText;

    @Column(name = "timetosend")
    private LocalDateTime timeToSend;

    public NotificationTask(){}

    public NotificationTask (Long chatId, String messageText, LocalDateTime timeToSend)
    {
        this.chatId = chatId;
        this.messageText = messageText;
        this.timeToSend = timeToSend;
    }
}
