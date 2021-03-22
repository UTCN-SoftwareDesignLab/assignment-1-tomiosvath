package service.client;

import model.Client;
import model.validation.ClientValidator;
import model.validation.Notification;
import repository.client.ClientRepository;

import java.util.List;


public class ClientServiceImpl implements ClientService{
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Override
    public Client findById(Long id) {
        return null;
    }

    @Override
    public Notification<Boolean> save(Client client, float balance) {
        ClientValidator clientValidator = new ClientValidator(client);
        boolean clientValid = clientValidator.validate();

        Notification<Boolean> clientRegisterNotification = new Notification<>();

        if (!clientValid){
            clientValidator.getErrors().forEach(clientRegisterNotification::addError);
            clientRegisterNotification.setResult(Boolean.FALSE);
        }
        else{
            clientRegisterNotification.setResult(clientRepository.save(client, balance));
        }

        return clientRegisterNotification;
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Notification<Boolean> delete(String name) {
        Notification<Boolean> clientDeleteNotification = new Notification<>();
        if (clientRepository.delete(name)){
            clientDeleteNotification.setResult(true);
        }
        else{
            clientDeleteNotification.addError("User couldn't be deleted");
        }

        return clientDeleteNotification;
    }

    public Notification<Boolean> updateClient(String name, Client newClient){
        Notification<Boolean> updateNotification = new Notification<>();
        if (clientRepository.updateClient(name, newClient)){
            updateNotification.setResult(true);
        }
        else{
            updateNotification.addError("User couldn't be updated");
        }

        return updateNotification;
    }

    public Notification<Boolean> updateBalance(String name, float balance){
        Notification<Boolean> updateNotification = new Notification<>();
        if (clientRepository.updateBalance(name, balance)){
            updateNotification.setResult(true);
        }
        else{
            updateNotification.addError("Balance couldn't be updated");
        }

        return updateNotification;
    }

    public Notification<Client> findByName(String name){
        Notification<Client> notification = new Notification<>();
        Client client = clientRepository.findByName(name);
        if (client == null){
            notification.addError("Couldn't find user");
        }
        else{
            notification.setResult(client);
        }

        return notification;
    }
}
