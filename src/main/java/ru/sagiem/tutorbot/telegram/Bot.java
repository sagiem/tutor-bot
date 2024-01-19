package ru.sagiem.tutorbot.telegram;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sagiem.tutorbot.service.UpdateDispatcher;

@Component
@AllArgsConstructor
public class Bot extends TelegramWebhookBot {

    private final TelegramProperties telegramProperties;
    private final UpdateDispatcher updateDispatcher;


    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {

        return updateDispatcher.distribute(update, this);
    }

    @Override
    public String getBotPath() {
        return telegramProperties.getPath();
    }

    @Override
    public String getBotUsername() {
        return telegramProperties.getUsername();
    }
}
