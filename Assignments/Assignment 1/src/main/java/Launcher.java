import controller.AdminController;
import controller.LoginController;
import controller.UserController;
import view.AdminView;
import view.LoginView;
import view.UserView;

public class Launcher {

    public static void main(String[] args) {
        ComponentFactory componentFactory = ComponentFactory.instance(false);
        AdminController adminController = new AdminController(new AdminView(), componentFactory.getAuthenticationService(), componentFactory.getActivityService());
        UserController userController = new UserController(new UserView(), componentFactory.getClientService(), componentFactory.getAccountService(), componentFactory.getActivityService());
        new LoginController(new LoginView(), adminController, userController, componentFactory.getAuthenticationService());
    }

}
