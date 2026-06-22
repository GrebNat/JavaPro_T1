package org.example;


import org.example.config.ApplicationContext;
import org.example.dto.User;
import org.example.ioc.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;
import java.util.Comparator;

@ComponentScan
public class Main {
    public static void main(String[] args) throws SQLException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        UserService userService = context.getBean(UserService.class);

        System.out.println("Создали пользователя: " + userService.createUser("Nata6"));

        Integer lastId = userService.getAllUsers().stream()
                .map(User::getId)
                .max(Integer::compareTo).get();

        System.out.println("Получили пользователя: " + userService.getUserById(lastId).getUsername());
        System.out.println("Удалили пользователя: " + userService.deleteUser(lastId));
        System.out.println("Попытались получить пользователя: " + userService.getUserById(lastId));
    }
}