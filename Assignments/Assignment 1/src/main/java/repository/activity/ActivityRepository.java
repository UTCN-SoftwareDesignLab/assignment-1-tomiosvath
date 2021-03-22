package repository.activity;


import database.Constants;
import database.JDBConnectionWrapper;
import model.Activity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityRepository {
    private final Connection connection;


    public ActivityRepository(Connection connection) {
        this.connection = connection;
    }

    public Boolean save(String name, String operation){
        try {
            java.util.Date date = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            PreparedStatement insertActivityStatement = connection
                    .prepareStatement("INSERT INTO activity values (null, ?, ?, ?)");
            insertActivityStatement.setString(1, name);
            insertActivityStatement.setString(2, operation);
            insertActivityStatement.setDate(3, sqlDate);
            insertActivityStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Activity> report(String userName) {
        List<Activity> activities = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            String fetch = "SELECT * FROM activity WHERE `name`=\'" + userName + "\'";
            ResultSet resultSet = statement.executeQuery(fetch);

            while (resultSet.next()){
                activities.add(generateActivityFromSQL(resultSet));
            }
            return activities;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public Activity generateActivityFromSQL(ResultSet resultSet) throws SQLException {
        return new Activity(resultSet.getString("name"), resultSet.getString("operation"), resultSet.getDate("opDate"));
    }

    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from activity where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        JDBConnectionWrapper jdbConnectionWrapper = new JDBConnectionWrapper(Constants.Schemas.PRODUCTION);
        ActivityRepository activityRepository = new ActivityRepository(jdbConnectionWrapper.getConnection());

        //activityRepository.save("Tom", "Test operation");
        activityRepository.report("Employee1!@yahoo.com");
    }

}
