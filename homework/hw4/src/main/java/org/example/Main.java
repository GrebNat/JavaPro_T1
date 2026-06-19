package org.example;


import org.example.dto.User;
import org.example.ioc.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) throws SQLException {

        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("templates/spring-context.xml");
        UserService userService = context.getBean(UserService.class);

        userService.createUser("Nata4");

        Integer lastId = userService.getAllUsers().stream()
                .map(User::getId)
                .max(Integer::compareTo).get();

        System.out.println(userService.getUserById(lastId).getUsername());

        userService.deleteUser(lastId);

        System.out.println(userService.getUserById(lastId));
    }
}