package ru.sagiem.tutorbot.service.manager.task;

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
public class TaskManager extends AbstractManager {

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
        switch (callbackData) {
            case TASK -> {
                return mainMenu(callbackQuery);
            }
            case TASK_CREATE -> {
                return create(callbackQuery);
            }
        }
        return null;
    }


    private BotApiMethod<?> mainMenu(Message message) {
        return answerMethodFactory.getSendMessage(
                message.getChatId(),
                """
                        🗂 Вы можете добавить домашнее задание вашему ученику
                        """,
                keyboardFactory.getInlineKeyboard(
                        List.of("Прикрепить домашнее задание"),
                        List.of(1),
                        List.of(TASK_CREATE)
                )
        );
    }

    private BotApiMethod<?> mainMenu(CallbackQuery callbackQuery) {
        return answerMethodFactory.getEditMessageText(
                callbackQuery,
                """
                        🗂 Вы можете добавить домашнее задание вашему ученику
                        """,
                keyboardFactory.getInlineKeyboard(
                        List.of("Прикрепить домашнее задание"),
                        List.of(1),
                        List.of(TASK_CREATE)
                )
        );
    }

    private BotApiMethod<?> create(CallbackQuery callbackQuery) {
        return answerMethodFactory.getEditMessageText(
                callbackQuery,
                """
                        👤 Выберете ученика, которому хотите дать домашнее задание
                        """,
                keyboardFactory.getInlineKeyboard(
                        List.of("Назад"),
                        List.of(1),
                        List.of(TASK)
                )
        );
    }
}
