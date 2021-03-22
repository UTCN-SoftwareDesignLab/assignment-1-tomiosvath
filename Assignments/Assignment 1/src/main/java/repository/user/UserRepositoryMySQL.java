package repository.user;

import database.JDBConnectionWrapper;
import model.Client;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.role.RoleRepository;
import repository.role.RoleRepositoryMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.USER;

public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final RoleRepository roleRepository;


    public UserRepositoryMySQL(Connection connection, RoleRepository rightsRolesRepository) {
        this.connection = connection;
        this.roleRepository = rightsRolesRepository;
    }


    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password) throws AuthenticationException {
        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql = "Select * from `" + USER + "` where `username`=\'" + username + "\' and `password`=\'" + password + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            if (userResultSet.next()) {
                User user = new UserBuilder()
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRole(roleRepository.findRoleById(userResultSet.getInt("roleId")))
                        .build();
                findByUsernameAndPasswordNotification.setResult(user);
                return findByUsernameAndPasswordNotification;
            } else {
                findByUsernameAndPasswordNotification.addError("Invalid email or password!");
                return findByUsernameAndPasswordNotification;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AuthenticationException();
        }
    }

    @Override
    public boolean save(User user, String roleName) {
        Role role = roleRepository.findRoleByName(roleName);
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO user values (null, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.setInt(3, role.getId());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }


    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from user where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean delete(User user){
        try{
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM user WHERE username = ?");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(String username, String password) {
        try{
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("UPDATE " + USER + " SET password = ?  WHERE username = ?");
            insertUserStatement.setString(1, password);
            insertUserStatement.setString(2, username);
            insertUserStatement.executeUpdate();

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /*public static void main(String[] args) {
        User user = new UserBuilder().setUsername("Tom").setPassword("password").build();

        JDBConnectionWrapper jdbConnectionWrapper = new JDBConnectionWrapper(PRODUCTION);
        RoleRepository roleRepository = new RoleRepositoryMySQL(jdbConnectionWrapper.getConnection());
        UserRepositoryMySQL userRepositoryMySQL = new UserRepositoryMySQL(jdbConnectionWrapper.getConnection(), roleRepository);

        userRepositoryMySQL.save(user, "administrator");
    }*/
}
