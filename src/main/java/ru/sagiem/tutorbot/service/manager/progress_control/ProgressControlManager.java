package ru.sagiem.tutorbot.service.manager.progress_control;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sagiem.tutorbot.service.factory.AnswerMethodFactory;
import ru.sagiem.tutorbot.service.factory.KeyboardFactory;
import ru.sagiem.tutorbot.service.manager.AbstractManager;
import ru.sagiem.tutorbot.telegram.Bot;

import java.util.List;
import static ru.sagiem.tutorbot.service.data.CallbackData.*;

@Component
@AllArgsConstructor
public class ProgressControlManager extends AbstractManager {

    private final AnswerMethodFactory answerMethodFactory;
    private final KeyboardFactory keyboardFactory;

    @Override
    public BotApiMethod<?> answerCommand(Message message, Bot bot) {
        return mainMenu(message);
    }

    @Override
    public BotApiMethod<?> answerMessage(Message message, Bot bot) {
        return null;
    }

    @Override
    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, Bot bot) {
        String callbackData = callbackQuery.getData();
        switch (callbackData){
            case PROGRESS -> {
                return mainMenu(callbackQuery);
            }
            case PROGRESS_STAT -> {
                return stat(callbackQuery);
            }
        }
        return null;
    }

    private BotApiMethod<?> mainMenu(CallbackQuery callbackQuery){
        return answerMethodFactory.getEditMessageText(
                callbackQuery,
                """
                        Здесь вы можете увидеть
                        """,
                keyboardFactory.getInlineKeyboard(
                        List.of("Статистика успеваимости"),
                        List.of(1),
                        List.of(PROGRESS_STAT)
                )
        );
    }

    private BotApiMethod<?> mainMenu(Message message){
        return answerMethodFactory.getSendMessage(
                message.getChatId(),
                """
                        Здесь вы можете увидеть
                        """,
                keyboardFactory.getInlineKeyboard(
                        List.of("Статистика успеваимости"),
                        List.of(1),
                        List.of(PROGRESS_STAT)
                )
        );
    }

    private BotApiMethod<?> stat(CallbackQuery callbackQuery){
        return answerMethodFactory.getEditMessageText(
                callbackQuery,
                "Здесь будет статистика",
                keyboardFactory.getInlineKeyboard(
                        List.of("Назад"),
                        List.of(1),
                        List.of(PROGRESS)
                )
        );
    }
}
