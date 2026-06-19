package org.example.ioc;

import com.zaxxer.hikari.HikariDataSource;
import org.example.config.DataSourceConfig;
import org.example.dto.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements IUserDao {

    private HikariDataSource hikariDataSource;

    public UserDao(DataSourceConfig dataSourceConfig) {
        this.hikariDataSource = dataSourceConfig.hikariDataSource();
    }

    private Connection getConnection() {
        try {
            return hikariDataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer createUser(String username) {
        String sql = "INSERT INTO users (username) VALUES (?)";

        try (PreparedStatement stmt = getConnection()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, username);
            stmt.executeUpdate();

            stmt.getGeneratedKeys().next();
            return stmt.getGeneratedKeys().getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании пользователя", e);
        }
    }

    public User getUserById(Integer id) {
        String sql = "SELECT id, username FROM users WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("id"), rs.getString("username"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении пользователя", e);
        }

        return null;
    }

    public List<User> getAllUsers() {
        String sql = "SELECT id, username FROM users";
        List<User> users = new ArrayList<>();

        try (Statement stmt = getConnection().createStatement()){
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("username")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении всех пользователей", e);
        }

        return users;
    }

    public boolean deleteUser(Integer id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении пользователя", e);
        }
    }
}
