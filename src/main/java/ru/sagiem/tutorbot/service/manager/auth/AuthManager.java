package ru.sagiem.tutorbot.service.manager.auth;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.sagiem.tutorbot.entity.user.Action;
import ru.sagiem.tutorbot.entity.user.Role;
import ru.sagiem.tutorbot.repository.UserRepository;
import ru.sagiem.tutorbot.service.factory.AnswerMethodFactory;
import ru.sagiem.tutorbot.service.factory.KeyboardFactory;
import ru.sagiem.tutorbot.service.manager.AbstractManager;
import ru.sagiem.tutorbot.telegram.Bot;

import java.util.List;
import static ru.sagiem.tutorbot.service.data.CallbackData.AUTH_TEACHER;
import static ru.sagiem.tutorbot.service.data.CallbackData.AUTH_STUDENT;

@Slf4j
@Service
@AllArgsConstructor
public class AuthManager extends AbstractManager {
    private final UserRepository userRepository;
    private final AnswerMethodFactory methodFactory;
    private final KeyboardFactory keyboardFactory;

    @Override
    public BotApiMethod<?> answerCommand(Message message, Bot bot) {
        return null;
    }

    @Override
    public BotApiMethod<?> answerMessage(Message message, Bot bot) {
        Long chatId = message.getChatId();
        var user = userRepository.findById(chatId).orElseThrow();
        user.setAction(Action.AUTH);
        userRepository.save(user);

        return methodFactory.getSendMessage(
                chatId,
                """
                        Выберите свою роль
                        """,
                keyboardFactory.getInlineKeyboard(
                        List.of("Ученик", "Учитель"),
                        List.of(2),
                        List.of(AUTH_STUDENT, AUTH_TEACHER)
                )
        );
    }

    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, Bot bot) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        var user = userRepository.findById(chatId).orElseThrow();
        if (AUTH_TEACHER.equals(callbackQuery.getData())) {
            user.setRole(Role.TEACHER);
        } else {
            user.setRole(Role.STUDENT);
        }
        user.setAction(Action.FREE);
        userRepository.save(user);

        try {
            bot.execute(
                    methodFactory.getAnswerCallbackQuery(
                            callbackQuery.getId(),
                            """
                                    Авторизация прошла успешна, повторите предыдущий запрос!"""
                    ));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
        return methodFactory.getDeleteMessage(
                chatId,
                messageId
        );
    }
}
