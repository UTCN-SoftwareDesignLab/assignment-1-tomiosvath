package model;

public class Account {
    private int id;
    private float balance;

    public Account(float balance){
        this.balance = balance;
    }

    public Account(int id, float balance){
        this.id = id;
        this.balance = balance;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
