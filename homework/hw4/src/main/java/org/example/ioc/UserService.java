package org.example.ioc;

import org.example.dto.User;

import java.sql.*;
import java.util.*;

public class UserService {

    private final IUserDao userDao;

    public UserService(IUserDao userDao) {
        this.userDao = userDao;
    }

    public Integer createUser(String username) {
        return userDao.createUser(username);
    }

    public User getUserById(Integer id) throws SQLException {
        return userDao.getUserById(id);
    }

    public List<User> getAllUsers() throws SQLException {
        return userDao.getAllUsers();
    }

    public boolean deleteUser(Integer id) {
        return userDao.deleteUser(id);
    }
}
