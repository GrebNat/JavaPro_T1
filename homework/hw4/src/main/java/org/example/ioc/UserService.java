package org.example.ioc;

import org.example.dto.User;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;

@Component
public class UserService {

    private final IUserDao userDao;

    public UserService(IUserDao userDao) {
        this.userDao = userDao;
    }

    public Integer createUser(String username) {
        return userDao.createUser(username);
    }

    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public boolean deleteUser(Integer id) {
        return userDao.deleteUser(id);
    }
}
