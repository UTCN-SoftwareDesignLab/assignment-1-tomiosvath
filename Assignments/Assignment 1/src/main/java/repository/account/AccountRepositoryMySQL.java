package repository.account;

import model.Account;

import java.sql.*;

import static database.Constants.Tables.ACCOUNT;

public class AccountRepositoryMySQL implements AccountRepository{

    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Account findById(int id) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchIdSql = "Select * from " + ACCOUNT + " where `id`=\'" + id + "\'";
            ResultSet resultSet = statement.executeQuery(fetchIdSql);
            resultSet.next();
            return new Account(resultSet.getInt("id"), resultSet.getFloat("balance"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account createAccount(float balance){
        Account account = new Account(balance);
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO " + ACCOUNT + " values (null, ?)", Statement.RETURN_GENERATED_KEYS);
            insertStatement.setFloat(1, balance);
            insertStatement.executeUpdate();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();

            if (generatedKeys.next()){
                account.setId(generatedKeys.getInt(1));
            }

            return account;
        }
        catch (SQLException ignored) {
        }
        return null;
    }

    public boolean update(int id, float balance){
        try{
            PreparedStatement statement = connection.prepareStatement("UPDATE " + ACCOUNT + " SET balance = ? WHERE id = ?");
            statement.setFloat(1, balance);
            statement.setInt(2, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from account where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
