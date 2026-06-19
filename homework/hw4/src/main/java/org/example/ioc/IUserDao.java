package org.example.ioc;

import org.example.dto.User;

import java.util.List;

public interface IUserDao {
    Integer createUser(String username);

    User getUserById(Integer id);

    List<User> getAllUsers();

    boolean deleteUser(Integer id);
}
