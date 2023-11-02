package pro.sky.tg_demo.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.tg_demo.model.NotificationTask;
import pro.sky.tg_demo.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TgBotService implements UpdatesListener
{
    private final NotificationTaskRepository notificationTaskRepository;

    @Autowired
    private TelegramBot telegramBot;

    private Logger logger = LoggerFactory.getLogger(TgBotService.class);

    public TgBotService (NotificationTaskRepository notificationTaskRepository)
    {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @PostConstruct
    public void init()
    {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {

        updates.forEach( update -> {
            logger.info("Take message: {}",update);

            //Check "/start" message or another message
            if(CheckStart(update))
            {
                SendMessageTg(update.message().chat().id(),"Этот Telegram-бот отвечает на /start");
            }
            else
            {
                ParseMessage(update);
            }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
    private boolean CheckStart(Update update)
    {
        return update.message().text().equals("/start");
    }
    private void SendMessageTg(Long chatId, String text)
    {
        SendResponse response = telegramBot.execute(new SendMessage(chatId,text));
    }

    private void ParseMessage(Update update)
    {
        Pattern pattern = Pattern.compile("([\\d\\.\\:\\s]{16})(\\s)([\\W+]+)");
        Matcher matcher = pattern.matcher(update.message().text());

        if (matcher.matches())
        {
            LocalDateTime date = LocalDateTime.parse(matcher.group(1), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            String message = matcher.group(3);
            NotificationTask nt = notificationTaskRepository.save(new NotificationTask(update.message().chat().id(),message,date));
            SendMessageTg(update.message().chat().id(), "Complete!");
        }
        else
        SendMessageTg(update.message().chat().id(), "NO COMPLETE!");
    }
}
