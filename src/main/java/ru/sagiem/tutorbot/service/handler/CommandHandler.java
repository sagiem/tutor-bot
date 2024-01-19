package ru.sagiem.tutorbot.service.handler;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.sagiem.tutorbot.telegram.Bot;

import static ru.sagiem.tutorbot.service.data.Command.*;

@Service
public class CommandHandler {
    public BotApiMethod<?> answer(Message message, Bot bot) {
        String command = message.getText();
        switch (command) {
            case START -> {
                return start(message);
            }
            case FEEDBACK -> {
                return feedback(message);
            }
            case HELP -> {
                return help(message);
            }
        }

        return null;
    }

    private BotApiMethod<?> help(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text("""
                        📍 Доступные команды:
                        - start
                        - help
                        - feedback
                                                
                        📍 Доступные функции:
                        - Расписание
                        - Домашнее задание
                        - Контроль успеваемости
                        """)
                .build();
    }

    private BotApiMethod<?> feedback(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text("Контактный e-mail: admin@sagiem.ru")
                .build();
    }

    private BotApiMethod<?> start(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text("""
                        🖖Приветствую в Tutor-Bot, инструменте для упрощения взаимодействия репититора и ученика.
                                                
                        Что бот умеет?
                        📌 Составлять расписание
                        📌 Прикреплять домашние задания
                        📌 Ввести контроль успеваемости
                        """)
                .build();
    }
}
