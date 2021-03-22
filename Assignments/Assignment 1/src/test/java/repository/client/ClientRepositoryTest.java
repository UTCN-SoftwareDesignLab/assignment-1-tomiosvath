package repository.client;

import database.DBConnectionFactory;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;

public class ClientRepositoryTest {
    private static ClientRepository clientRepository;
    private final String name = "Tom";
    //private final String idCard = "SX120120";


    @BeforeClass
    public static void setup(){
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        clientRepository = new ClientRepositoryMySQL(connection);
    }

    @Before
    public void cleanup(){
        clientRepository.removeAll();
    }

    @Test
    public void findAll(){
        ClientBuilder builder = new ClientBuilder();
        Client client = builder.setName(name).build();
        clientRepository.save(client, 500);
        assertEquals(1, clientRepository.findAll().size());
    }

    @Test
    public void save(){
        ClientBuilder builder = new ClientBuilder();
        Client client = builder.setName(name).build();
        Client client2 = builder.setName("Tom2").build();
        clientRepository.save(client, 500);
        clientRepository.save(client2, 200);
        assertEquals(2, clientRepository.findAll().size());
    }

    @Test
    public void delete(){
        ClientBuilder builder = new ClientBuilder();
        Client client = builder.setName(name).build();
        ClientBuilder builder2 = new ClientBuilder();
        Client client2 = builder2.setName("Alex").build();
        clientRepository.save(client, 500);
        clientRepository.save(client2, 200);
        clientRepository.delete(name);
        assertEquals(1, clientRepository.findAll().size());
    }

    @Test
    public void findByName(){
        ClientBuilder builder = new ClientBuilder();
        Client client = builder.setName(name).build();
        clientRepository.save(client, 500);
        Client client2 = clientRepository.findByName(name);
        assertEquals(client.getName(), client2.getName());
    }

    @Test
    public void update(){
        String oldNum = "SX120120";
        String newNum = "SX999999";
        ClientBuilder builder = new ClientBuilder();
        Client client = builder.setName(name).setNumCode(oldNum).setTelephone(0711111111).setAddress("Cluj").setIdCardNo("1239").build();
        clientRepository.save(client, 500);
        client.setNumCode(newNum);
        clientRepository.updateClient(name, client);
        assertEquals(newNum, clientRepository.findByName(name).getNumCode());
    }
}
