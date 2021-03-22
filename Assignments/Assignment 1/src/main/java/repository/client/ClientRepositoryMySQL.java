package repository.client;

import database.JDBConnectionWrapper;
import model.Account;
import model.Client;
import model.builder.ClientBuilder;
import model.validation.Notification;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Schemas.PRODUCTION;
import static database.Constants.Tables.CLIENT;

public class ClientRepositoryMySQL implements ClientRepository {

    private final Connection connection;

    public ClientRepositoryMySQL(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Client> findAll() {
        List <Client> clients = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                clients.add(createClientFromResult(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }


    @Override
    public Client findByName(String name) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchClient = "SELECT * FROM " + CLIENT + " where `name`=\'" + name + "\'";
            ResultSet clientResult = statement.executeQuery(fetchClient);
            if (clientResult.next())
                return createClientFromResult(clientResult);
            else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(Client client, float balance) {
        AccountRepository accountRepository = new AccountRepositoryMySQL(connection);
        Account account = accountRepository.createAccount(balance);
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO " + CLIENT + " values (null, ?, ?, ?, ?, ?, ?)");
            insertStatement.setString(1, client.getName());
            insertStatement.setString(2, client.getIdCardNo());
            insertStatement.setString(3, client.getNumCode());
            insertStatement.setString(4, client.getAddress());
            insertStatement.setInt(5, client.getTelephone());
            insertStatement.setInt(6, account.getId());

            insertStatement.executeUpdate();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();

            return true;
        }
        catch (SQLException ignored) {
        }
        return false;
    }

    public boolean updateClient(String name, Client newClient) {
        Client oldClient = findByName(name);
        if (oldClient == null) {
            System.out.println("User couldn't be found");
            return false;
        }
        else{
            try {
                PreparedStatement statement = connection
                        .prepareStatement("UPDATE " + CLIENT + " SET name = ?, idCardNo = ?, numCode = ?, address = ?, telephone = ? WHERE name = ?");
                statement.setString(1, newClient.getName());
                statement.setString(2, newClient.getIdCardNo());
                statement.setString(3, newClient.getNumCode());
                statement.setString(4, newClient.getAddress());
                statement.setInt(5, newClient.getTelephone());
                statement.setString(6, name);

                statement.executeUpdate();
                return true;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return false;
        }
    }

    public boolean updateBalance(String name, float balance){
        AccountRepository accountRepository = new AccountRepositoryMySQL(connection);
        Client client = findByName(name);
        if (client == null){
            return false;
        }
        else{
            return accountRepository.update(client.getId(), balance);
        }
    }

    @Override
    public boolean delete(String name) {
        Client client = findByName(name);
        if (client != null) {
            try{
                PreparedStatement preparedStatement = connection
                        .prepareStatement("DELETE FROM " + CLIENT + " WHERE name = ? ");
                preparedStatement.setString(1, name);
                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from client where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Client createClientFromResult(ResultSet res){
        try {
            int id = res.getInt("id");
            String name = res.getString("name");
            String idCardNo = res.getString("idCardNo");
            String numCode = res.getString("numCode");
            String address = res.getString("address");
            int telephone = res.getInt("telephone");
            int accountNo = res.getInt("accountNo");

            return new Client(id, name, idCardNo, numCode, address, telephone, accountNo);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Client client = new Client(0, "Tom", "SX140209", "1921490214", "Cluj-Napoca", 965812309, 0);

        JDBConnectionWrapper jdbConnectionWrapper = new JDBConnectionWrapper(PRODUCTION);

        ClientRepositoryMySQL clientRepositoryMySQL = new ClientRepositoryMySQL(jdbConnectionWrapper.getConnection());

        System.out.println(clientRepositoryMySQL.save(client, 500));
    }
}
