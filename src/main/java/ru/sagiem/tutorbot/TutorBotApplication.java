package ru.sagiem.tutorbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.sagiem.tutorbot.telegram.TelegramProperties;

@SpringBootApplication
@EnableConfigurationProperties(TelegramProperties.class)
public class TutorBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutorBotApplication.class, args);
	}

}
