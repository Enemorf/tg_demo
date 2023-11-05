package pro.sky.tg_demo.listener;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.tg_demo.model.NotificationTask;
import pro.sky.tg_demo.service.TgBotService;

import java.util.List;

@RestController
public class TgBotListener implements UpdatesListener
{
    private Logger logger = LoggerFactory.getLogger(TgBotListener.class);
    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private TgBotService tgBotService;

    @PostConstruct
    public void init()
    {
        telegramBot.setUpdatesListener(this);
    }

    //Listen TG-Bot and choose options
    @Override
    public int process(List<Update> updates)
    {
        updates.forEach( update ->
        {
            logger.info("NEW MESSAGE: "+ update.message().text() + " FROM " + update.message().chat().id());

            if(update.message().text().equals("/start"))
            {
                sendMessageTg(update.message().chat().id(), "Добро пожаловать в мой первый Telegram-бот!\n" +
                        "Он мало что умеет (пока что), но он только начинает жить и развиваться =)\n" +
                        "Вот что он умеет:\n" +
                        "1. Отвечать на сообщение \"/start\"\n" +
                        "2. Выводить сообщение в заданное время, при условии, что формат сообщения будет " +
                        "\"ДД.ММ.ГГГГ ЧЧ:ММ СвоёСообщение\"\n" +
                        "Хорошего дня!");
            }
            else
            {
                sendMessageTg(update.message().chat().id(),tgBotService.parseMessage(update));
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void sendMessageTg(Long chatId, String text)
    {
        logger.info("Send message to id " + chatId);
        telegramBot.execute(new SendMessage(chatId,text));
    }

    public void sendMessageTg(NotificationTask task)
    {
        logger.info("Send message to id " + task.getChatId());
        telegramBot.execute(new SendMessage(task.getChatId(),task.getMessageText()));
    }
}
