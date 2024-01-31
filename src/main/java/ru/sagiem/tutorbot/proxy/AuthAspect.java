package ru.sagiem.tutorbot.proxy;

import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.sagiem.tutorbot.entity.user.Action;
import ru.sagiem.tutorbot.entity.user.Role;
import ru.sagiem.tutorbot.entity.user.User;
import ru.sagiem.tutorbot.repository.UserRepository;
import ru.sagiem.tutorbot.service.manager.auth.AuthManager;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sagiem.tutorbot.telegram.Bot;

@Aspect
@Component
@Order(100)
@AllArgsConstructor
public class AuthAspect {
    private final UserRepository userRepository;
    private final AuthManager authManager;

    @Pointcut("execution(* ru.sagiem.tutorbot.service.UpdateDispatcher.distribute(..))")
    public void distributeMethodPointcut(){}

    @Around("distributeMethodPointcut()")
    public Object authMethodAdvice(ProceedingJoinPoint joinPoint)
            throws Throwable {
        Update update = (Update) joinPoint.getArgs()[0];
        User user;
        if (update.hasMessage()) {
            user = userRepository.findById(update.getMessage().getChatId()).orElseThrow();
        } else if (update.hasCallbackQuery()) {
            user = userRepository.findById(update.getCallbackQuery().getMessage().getChatId()).orElseThrow();
        } else {
            return joinPoint.proceed();
        }

        if (user.getRole() != Role.EMPTY) {
            return joinPoint.proceed();
        }

        if (user.getAction() == Action.AUTH) {
            return joinPoint.proceed();
        }
        return authManager.answerMessage(update.getMessage(),
                (Bot) joinPoint.getArgs()[1]);
    }
}
