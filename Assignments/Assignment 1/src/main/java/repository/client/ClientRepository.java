package repository.client;

import model.Client;
import model.validation.Notification;

import java.util.List;

public interface ClientRepository {
    List<Client> findAll();

    Client findByName(String name);

    boolean save(Client client, float balance);

    boolean delete(String name);

    boolean updateClient(String name, Client newClient);

    boolean updateBalance(String name, float balance);

    void removeAll();
}
