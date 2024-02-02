package ru.sagiem.tutorbot.service.manager.profile;

import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Var;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sagiem.tutorbot.repository.UserRepository;
import ru.sagiem.tutorbot.service.factory.AnswerMethodFactory;
import ru.sagiem.tutorbot.service.manager.AbstractManager;
import ru.sagiem.tutorbot.telegram.Bot;

@Component
@AllArgsConstructor
public class ProfileManager extends AbstractManager {

    private final AnswerMethodFactory methodFactory;
    private final UserRepository userRepository;
    @Override
    public BotApiMethod<?> answerCommand(Message message, Bot bot) {
        return showProfile(message);
    }



    @Override
    public BotApiMethod<?> answerMessage(Message message, Bot bot) {
        return null;
    }

    @Override
    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, Bot bot) {
        return null;
    }

    private BotApiMethod<?> showProfile(Message message) {
        Long charId = message.getChatId();
        StringBuffer text = new StringBuffer("\uD83D\uDC64 Профиль\n");
        var user = userRepository.findById(charId).orElseThrow();
        var details = user.getUserDetails();

        if(details.getUsername() == null)
            text.append("\uFE0FИмя пользователя - ").append(details.getUsername());
        else
            text.append("\uFE0FИмя пользователя - ").append(details.getFirstName());

        text.append("\n\uFE0FРоль - ").append(user.getRole().name());
        text.append("\n\uFE0FВаш униальный токен - ").append(user.getToken().toString());
        text.append("\n\n⚠\uFE0F - токен необходим для того, чтобы ученик или преподаватель могли установиться между собой связь");


        return methodFactory.getSendMessage(
                charId,
                text.toString(),
                null
        );
    }
}
