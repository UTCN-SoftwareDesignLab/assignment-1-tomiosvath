package service.client;

import model.Client;
import model.validation.Notification;

import java.util.List;

public interface ClientService {

    Client findById(Long id);

    Notification<Boolean> save(Client client, float balance);

    List <Client> findAll();

    Notification<Boolean> delete(String name);

    Notification<Boolean> updateClient(String name, Client newClient);

    Notification<Boolean> updateBalance(String name, float balance);

    Notification<Client> findByName(String name);
}
