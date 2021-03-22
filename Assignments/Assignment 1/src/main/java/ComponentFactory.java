import database.DBConnectionFactory;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.activity.ActivityRepository;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.role.RoleRepository;
import repository.role.RoleRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.activity.ActivityService;
import service.client.ClientService;
import service.client.ClientServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;

import java.sql.Connection;

public class ComponentFactory {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ActivityRepository activityRepository;

    private final ClientService clientService;
    private final AuthenticationService authenticationService;
    private final AccountService accountService;
    private final ActivityService activityService;

    private static ComponentFactory instance;

    public static ComponentFactory instance(Boolean componentsForTests) {
        if (instance == null) {
            instance = new ComponentFactory(componentsForTests);
        }
        return instance;
    }

    private ComponentFactory(Boolean componentsForTests) {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(componentsForTests).getConnection();
        this.roleRepository = new RoleRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, roleRepository);
        this.accountRepository = new AccountRepositoryMySQL(connection);
        this.activityRepository = new ActivityRepository(connection);

        this.clientService = new ClientServiceImpl(new ClientRepositoryMySQL(connection));
        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.roleRepository);
        this.accountService = new AccountService(accountRepository);
        this.activityService = new ActivityService(activityRepository);
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RoleRepository getRightsRolesRepository() {
        return roleRepository;
    }

    public ClientService getClientService(){ return clientService; }

    public AccountService getAccountService() {
        return accountService;
    }

    public ActivityService getActivityService(){ return activityService;}
}
