package repository.account;

import database.DBConnectionFactory;
import model.Account;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.assertEquals;

public class AccountRepositoryTest {

    private static AccountRepository accountRepository;

    @BeforeClass
    public static void setup(){
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        accountRepository = new AccountRepositoryMySQL(connection);
    }

    @Before
    public void cleanup(){
        accountRepository.removeAll();
    }

    @Test
    public void findById(){
        float balance = 100;
        Account account = accountRepository.createAccount(balance);
        Account found = accountRepository.findById(account.getId());
        assertEquals(account.getId(), found.getId());
        assertEquals(account.getBalance(), account.getBalance(), 0.0);
    }

    @Test
    public void createAccount(){
        float balance = 100;
        Account account = accountRepository.createAccount(balance);
        assertEquals(account.getBalance(), balance, 0.0);
    }

    @Test
    public void update(){
        float balance = 100;
        Account account = accountRepository.createAccount(balance);
        float balance2 = 200;
        accountRepository.update(account.getId(), balance2);
        Account newAccount = accountRepository.findById(account.getId());
        assertEquals(newAccount.getBalance(), balance2, 0.0);
    }


}
