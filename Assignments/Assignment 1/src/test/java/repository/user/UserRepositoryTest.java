package repository.user;

import database.DBConnectionFactory;
import model.User;
import model.builder.UserBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.role.RoleRepository;
import repository.role.RoleRepositoryMySQL;

import java.sql.Connection;

import static database.Constants.Roles.EMPLOYEE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UserRepositoryTest {

    private static UserRepository userRepository;
    private static RoleRepository roleRepository;

    @BeforeClass
    public static void setup(){
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        roleRepository = new RoleRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection, roleRepository);
    }

    @Before
    public void cleanup(){
        userRepository.removeAll();
    }

    /*
    This is basically a test for both save and find methods
     */
    @Test
    public void findByUsernameAndPassword() throws AuthenticationException {
        String username = "Tom@yahoo.com";
        String pass = "password1!";
        UserBuilder builder = new UserBuilder();
        User user = builder.setUsername(username).setPassword(pass).build();
        userRepository.save(user, EMPLOYEE);

        User foundUser = userRepository.findByUsernameAndPassword(username, pass).getResult();
        assertEquals(foundUser.getUsername(), foundUser.getUsername());
    }

    @Test
    public void update() throws AuthenticationException {
        String username = "Tom@yahoo.com";
        String pass = "password1!";
        UserBuilder builder = new UserBuilder();
        User user = builder.setUsername(username).setPassword(pass).build();
        userRepository.save(user, EMPLOYEE);

        String newPass = "password2!";
        user.setPassword(newPass);
        userRepository.update(username, newPass);

        assertFalse(userRepository.findByUsernameAndPassword(username, newPass).hasErrors());
    }
}
