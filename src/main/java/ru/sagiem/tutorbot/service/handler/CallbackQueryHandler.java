package ru.sagiem.tutorbot.service.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.sagiem.tutorbot.service.manager.auth.AuthManager;
import ru.sagiem.tutorbot.service.manager.feedback.FeedbackManager;
import ru.sagiem.tutorbot.service.manager.help.HelpManager;
import ru.sagiem.tutorbot.service.manager.progress_control.ProgressControlManager;
import ru.sagiem.tutorbot.service.manager.task.TaskManager;
import ru.sagiem.tutorbot.service.manager.timetable.TimetableManager;
import ru.sagiem.tutorbot.telegram.Bot;

import static ru.sagiem.tutorbot.service.data.CallbackData.*;

@Service
@AllArgsConstructor
public class CallbackQueryHandler {
    private final FeedbackManager feedbackManager;
    private final HelpManager helpManager;
    private final TimetableManager timetableManager;
    private final TaskManager taskManager;
    private final ProgressControlManager progressControlManager;
    private final AuthManager authManager;

    public BotApiMethod<?> answer(CallbackQuery callbackQuery, Bot bot) {
        String callbackData = callbackQuery.getData();
        String keyWord = callbackData.split("_")[0];
        if (TIMETABLE.equals(keyWord))
            return timetableManager.answerCallbackQuery(callbackQuery, bot);

        if (TASK.equals(keyWord))
            return taskManager.answerCallbackQuery(callbackQuery, bot);

        if (PROGRESS.equals(keyWord))
            return progressControlManager.answerCallbackQuery(callbackQuery, bot);

        if (AUTH.equals(keyWord))
            return authManager.answerCallbackQuery(callbackQuery, bot);

        switch (callbackData) {
            case FEEDBACK -> {
                return feedbackManager.answerCallbackQuery(callbackQuery, bot);
            }
            case HELP -> {
                return helpManager.answerCallbackQuery(callbackQuery, bot);
            }

        }
        return null;
    }
}
