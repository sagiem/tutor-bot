package ru.sagiem.tutorbot.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sagiem.tutorbot.service.handler.CallbackQueryHandler;
import ru.sagiem.tutorbot.service.handler.CommandHandler;
import ru.sagiem.tutorbot.service.handler.MessageHandler;
import ru.sagiem.tutorbot.telegram.Bot;

@Service
@AllArgsConstructor
@Slf4j
public class UpdateDispatcher {

    private final CallbackQueryHandler callbackQueryHandler;
    private final MessageHandler messageHandler;
    private final CommandHandler commandHandler;

    public BotApiMethod<?> distribute(Update update, Bot bot) {
        if (update.hasCallbackQuery())
            return callbackQueryHandler.answer(update.getCallbackQuery(), bot);

        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText() && message.getText().charAt(0) == '/')
                    return commandHandler.answer(message, bot);
            return messageHandler.answer(message, bot);
        }

        log.info("Не поддерживаемая операция: " + update);
        return null;
    }
}
