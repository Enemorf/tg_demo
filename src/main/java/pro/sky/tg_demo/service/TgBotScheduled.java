package pro.sky.tg_demo.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.tg_demo.listener.TgBotListener;

@Service
public class TgBotScheduled
{
    private final TgBotListener tgBotListener;
    private final TgBotService tgBotService;

    public TgBotScheduled (TgBotListener tgBotListener, TgBotService tgBotService)
    {
        this.tgBotListener = tgBotListener;
        this.tgBotService = tgBotService;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void chooseCurrDateTime()
    {
        tgBotService.chooseNowDateTime().forEach(note ->
        {
            tgBotListener.sendMessageTg(note);
        });
    }

}
