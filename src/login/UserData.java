package login;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserData {
    private Connection connect = null;
    private PreparedStatement statement = null;
    private ResultSet rs = null;

    public UserData() {
        try {
            // Loads the SQLite driver (Note: you must include the JAR in the classpath when compiling and running)
            Class.forName("org.sqlite.JDBC").newInstance();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createUser(User user) throws SQLException {
        try {
            connect = DriverManager.getConnection("jdbc:sqlite:D:\\Code\\Login\\src\\login\\login.sqlite");
            statement = connect.prepareStatement("INSERT INTO users " +
                    "VALUES (?, ?);");
            statement.setString(1, user.getName());
            statement.setString(2, user.getPass());

            statement.executeUpdate();
            System.out.println("createUser executed");
        }
        finally {
            close();
        }
    }

    public User retrieveUser(String name) throws SQLException {
        User user = null;
        try {
            connect = DriverManager.getConnection("jdbc:sqlite:D:\\Code\\Login\\src\\login\\login.sqlite");
            statement = connect.prepareStatement("SELECT * FROM users " +
                    "WHERE name = ?;");
            statement.setString(1, name);

            rs = statement.executeQuery();
            rs.next();

            int i = 1;
            String retrievedName = rs.getString(i++);
            String retrievedPass = rs.getString(i);

            user = new User(retrievedName, retrievedPass);
        }
        finally {
            close();
        }
        return user;
    }

    public List<User> retrieveAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        try {
            connect = DriverManager.getConnection("jdbc:sqlite:D:\\Code\\Login\\src\\login\\login.sqlite");
            statement = connect.prepareStatement("SELECT * FROM users;");

            rs = statement.executeQuery();

            while (rs.next()) {
                int i = 1;
                String retrievedName = rs.getString(i++);
                String retrievedPass = rs.getString(i);

                User user = new User(retrievedName, retrievedPass);
                users.add(user);
            }
        }
        finally {
            close();
        }
        return users;
    }

    public void updateUser(User user) {

    }

    public void deleteUser(User user) {

    }

    // Close the resultSet and connection
    private void close() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
