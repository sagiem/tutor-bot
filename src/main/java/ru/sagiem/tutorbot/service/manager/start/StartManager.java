package ru.sagiem.tutorbot.service.manager.start;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sagiem.tutorbot.service.factory.AnswerMethodFactory;
import ru.sagiem.tutorbot.service.factory.KeyboardFactory;
import ru.sagiem.tutorbot.service.manager.AbstractManager;
import ru.sagiem.tutorbot.telegram.Bot;

import java.util.List;

import static ru.sagiem.tutorbot.service.data.CallbackData.FEEDBACK;
import static ru.sagiem.tutorbot.service.data.CallbackData.HELP;

@Component
@AllArgsConstructor
public class StartManager extends AbstractManager {

    private final AnswerMethodFactory answerMethodFactory;
    private final KeyboardFactory keyboardFactory;

    @Override
    public SendMessage answerCommand(Message message, Bot bot) {
        return answerMethodFactory.getSendMessage(
                message.getChatId(),
                """
                        🖖Приветствую в Tutor-Bot, инструменте для упрощения взаимодействия репититора и ученика.
                                                
                        Что бот умеет?
                        📌 Составлять расписание
                        📌 Прикреплять домашние задания
                        📌 Ввести контроль успеваемости
                        """,
                keyboardFactory.getInlineKeyboard(
                        List.of("Помощь", "Обратная связь"),
                        List.of(2),
                        List.of(HELP, FEEDBACK))
                );
    }

    @Override
    public BotApiMethod<?> answerMessage(Message message, Bot bot) {
        return null;
    }

    @Override
    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, Bot bot) {
        return null;
    }


}
