package ru.sagiem.tutorbot.telegram;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("telegram-bot")
public class TelegramProperties {

    private String username;
    private String token;
    private String path;
}
