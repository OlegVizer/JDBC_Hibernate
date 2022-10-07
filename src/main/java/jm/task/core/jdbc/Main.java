package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    private final static UserService userService = new UserServiceImpl();

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        userService.createUsersTable();

        userService.saveUser("Bob", "Smith", (byte) 30);
        userService.saveUser("Rob", "Moor", (byte) 31);
        userService.saveUser("Ann", "Macgroth", (byte) 32);
        userService.saveUser("Lisa", "Stillman", (byte) 33);

        userService.removeUserById(2);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}