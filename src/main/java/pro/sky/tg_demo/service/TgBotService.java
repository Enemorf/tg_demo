package pro.sky.tg_demo.service;

import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.tg_demo.model.NotificationTask;
import pro.sky.tg_demo.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TgBotService
{
    private final NotificationTaskRepository notificationTaskRepository;
    private Logger logger = LoggerFactory.getLogger(TgBotService.class);

    public TgBotService (NotificationTaskRepository notificationTaskRepository)
    {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    public String ParseMessage(Update update)
    {
        logger.info("Start ParseMessage func");

        Pattern pattern = Pattern.compile("([\\d\\.\\:\\s]{16})(\\s)([\\W+]+)");
        Matcher matcher = pattern.matcher(update.message().text());

        if (matcher.matches())
        {
            LocalDateTime date = LocalDateTime.parse(matcher.group(1), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            String message = matcher.group(3);
            notificationTaskRepository.save(new NotificationTask(update.message().chat().id(),message,date));

            return "Ваше напоминание записано!\n" +
                    "Ровно в "+date+" я пришлю сообщение:\n" +
                    message;
        }
        else
             return "Я не понял, что вы ввели!";

    }


    public List<NotificationTask> ChooseNowDateTime()
    {
        return notificationTaskRepository.findByTimeToSendEquals(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    }
}
