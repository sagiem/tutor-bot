package ru.sagiem.tutorbot.service.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.sagiem.tutorbot.service.manager.FeedbackManager;
import ru.sagiem.tutorbot.service.manager.HelpManager;
import ru.sagiem.tutorbot.telegram.Bot;

import static ru.sagiem.tutorbot.service.data.CallbackData.*;

@Service
@AllArgsConstructor
public class CallbackQueryHandler {
    private final FeedbackManager feedbackManager;
    private final HelpManager helpManager;
    public BotApiMethod<?> answer(CallbackQuery callbackQuery, Bot bot) {
        String callbackData = callbackQuery.getData();
        switch (callbackData){
            case FEEDBACK -> {
                return feedbackManager.answerCallbackQuery(callbackQuery);
            }
            case HELP -> {
                return helpManager.answerCallbackQuery(callbackQuery);
            }

        }
        return null;
    }
}
