package ru.sagiem.tutorbot.proxy;

import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.sagiem.tutorbot.entity.user.Action;
import ru.sagiem.tutorbot.entity.user.Role;
import ru.sagiem.tutorbot.entity.user.UserDetails;
import ru.sagiem.tutorbot.repository.UserDetailsRepository;
import ru.sagiem.tutorbot.repository.UserRepository;

import java.time.LocalDateTime;


@Aspect
@Component
@Order(10)
@AllArgsConstructor
public class UserCreationAspect {
    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;

    @Pointcut("execution(* ru.sagiem.tutorbot.service.UpdateDispatcher.distribute(..))")
    public void distributeMethodPointcut(){}

    @Around("distributeMethodPointcut()")
    public Object distributeMethodAdvice(ProceedingJoinPoint joinPoint)
            throws Throwable{
        Update update = (Update) joinPoint.getArgs()[0];
        User telegramUser;
        if (update.hasMessage()) {
            telegramUser = update.getMessage().getFrom();
        } else if (update.hasCallbackQuery()) {
            telegramUser = update.getCallbackQuery().getFrom();
        } else {
            return joinPoint.proceed();
        }

        if (userRepository.existsById(telegramUser.getId())) {
            return joinPoint.proceed();
        }

        UserDetails details = UserDetails.builder()
                .firstName(telegramUser.getFirstName())
                .username(telegramUser.getUserName())
                .lastName(telegramUser.getLastName())
                .registeredAt(LocalDateTime.now())
                .build();
        userDetailsRepository.save(details);
        ru.sagiem.tutorbot.entity.user.User newUser =
                ru.sagiem.tutorbot.entity.user.User.builder()
                        .chatId(telegramUser.getId())
                        .action(Action.FREE)
                        .role(Role.EMPTY)
                        .userDetails(details)
                        .build();
        userRepository.save(newUser);

        return joinPoint.proceed();
    }


}
