package repository.account;

import model.Account;

public interface AccountRepository {

    Account findById(int id);

    Account createAccount(float balance);

    boolean update(int id, float balance);

    void removeAll();
}
