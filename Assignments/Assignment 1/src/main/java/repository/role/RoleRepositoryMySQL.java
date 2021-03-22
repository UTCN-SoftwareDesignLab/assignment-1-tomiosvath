package repository.role;

import model.Role;
import model.builder.RoleBuilder;

import java.sql.*;

import static database.Constants.Tables.ROLE;

public class RoleRepositoryMySQL implements RoleRepository {

    private final Connection connection;

    public RoleRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addRole(String role) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO " + ROLE + " values (null, ?)");
            insertStatement.setString(1, role);
            insertStatement.executeUpdate();
        } catch (SQLException ignored) {
        }
    }

    @Override
    public Role findRoleById(int roleId) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchRoleSql = "Select * from " + ROLE + " where `id`=\'" + roleId + "\'";
            ResultSet roleResultSet = statement.executeQuery(fetchRoleSql);
            roleResultSet.next();
            String roleName = roleResultSet.getString("role");
            return new RoleBuilder().setId(roleResultSet.getInt("id")).setName(roleName).build();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Role findRoleByName(String name) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchRoleSql = "Select * from " + ROLE + " where `role`=\'" + name + "\'";
            ResultSet roleResultSet = statement.executeQuery(fetchRoleSql);
            roleResultSet.next();
            String roleName = roleResultSet.getString("role");
            return new RoleBuilder().setId(roleResultSet.getInt("id")).setName(roleName).build();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}


