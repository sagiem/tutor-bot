package ru.sagiem.tutorbot.service.manager;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sagiem.tutorbot.telegram.Bot;

public abstract class AbstractManager {

    public abstract BotApiMethod<?> answerCommand(Message message, Bot bot);
    public abstract BotApiMethod<?> answerMessage(Message message, Bot bot);
    public abstract BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, Bot bot);
}
