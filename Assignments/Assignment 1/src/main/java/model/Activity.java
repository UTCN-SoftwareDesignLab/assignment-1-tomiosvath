package model;

import java.util.Date;

public class Activity {
    private String name;
    private String operation;
    private Date date;

    public Activity(String name, String operation, Date date) {
        this.name = name;
        this.operation = operation;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getOperation() {
        return operation;
    }

    public Date getDate() {
        return date;
    }
}
