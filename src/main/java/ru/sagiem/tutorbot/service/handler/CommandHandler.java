package ru.sagiem.tutorbot.service.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sagiem.tutorbot.service.manager.feedback.FeedbackManager;
import ru.sagiem.tutorbot.service.manager.help.HelpManager;
import ru.sagiem.tutorbot.service.manager.start.StartManager;
import ru.sagiem.tutorbot.telegram.Bot;

import static ru.sagiem.tutorbot.service.data.Command.*;

@Service
@AllArgsConstructor
public class CommandHandler {
    private final FeedbackManager feedbackManager;
    private final HelpManager helpManager;
    private final StartManager startManager;

    public BotApiMethod<?> answer(Message message, Bot bot) {
        String command = message.getText();
        switch (command) {
            case START -> {
                return startManager.answerCommand(message, bot);
            }
            case FEEDBACK_COMMAND -> {
                return feedbackManager.answerCommand(message, bot);
            }
            case HELP_COMMAND -> {
                return helpManager.answerCommand(message, bot);
            }
            default -> {
                return defaultAnswer(message);
            }
        }

    }

    private BotApiMethod<?> defaultAnswer(Message message) {
        return SendMessage.builder()
                .text("Неподдерживаемая комманда")
                .chatId(message.getChatId())
                .build();
    }
}
