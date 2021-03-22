package service.account;

import model.Account;
import model.validation.Notification;
import repository.account.AccountRepository;

public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Notification<Account> findById(int id){
        Notification<Account> notification = new Notification<>();
        Account account = accountRepository.findById(id);
        if (account == null){
            notification.addError("An error occured");
        }
        else{
            notification.setResult(account);
        }

        return notification;
    }
}
