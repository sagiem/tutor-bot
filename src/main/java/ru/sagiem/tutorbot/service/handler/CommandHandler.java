package ru.sagiem.tutorbot.service.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sagiem.tutorbot.service.manager.feedback.FeedbackManager;
import ru.sagiem.tutorbot.service.manager.help.HelpManager;
import ru.sagiem.tutorbot.service.manager.profile.ProfileManager;
import ru.sagiem.tutorbot.service.manager.progress_control.ProgressControlManager;
import ru.sagiem.tutorbot.service.manager.start.StartManager;
import ru.sagiem.tutorbot.service.manager.task.TaskManager;
import ru.sagiem.tutorbot.service.manager.timetable.TimetableManager;
import ru.sagiem.tutorbot.telegram.Bot;

import java.awt.print.PageFormat;

import static ru.sagiem.tutorbot.service.data.Command.*;

@Service
@AllArgsConstructor
public class CommandHandler {
    private final FeedbackManager feedbackManager;
    private final HelpManager helpManager;
    private final StartManager startManager;
    private final TimetableManager timetableManager;
    private final TaskManager taskManager;
    private final ProgressControlManager progressControlManager;
    private final ProfileManager profileManager;

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
            case TIMETABLE -> {
                return timetableManager.answerCommand(message, bot);
            }
            case TASK -> {
                return taskManager.answerCommand(message, bot);
            }
            case PROGRESS -> {
                return progressControlManager.answerCommand(message, bot);
            }
            case PROFILE -> {
                return profileManager.answerCommand(message, bot);
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
