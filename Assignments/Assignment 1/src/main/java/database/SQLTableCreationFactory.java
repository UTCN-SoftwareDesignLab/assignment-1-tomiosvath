package database;

import static database.Constants.Tables.*;

public class SQLTableCreationFactory {

    public String getCreateSQLForTable(String table) {
        switch (table) {
            case CLIENT:
                return "CREATE TABLE IF NOT EXISTS client (" +
                        "  id int NOT NULL AUTO_INCREMENT," +
                        "  name varchar(50) NOT NULL," +
                        "  idCardNo varchar(8) NOT NULL," +
                        "  numCode varchar(13) NOT NULL," +
                        "  address varchar(100) NOT NULL," +
                        "  telephone int NOT NULL," +
                        "  accountNo int NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  CONSTRAINT accountNo" +
                        "    FOREIGN KEY (accountNo)" +
                        "    REFERENCES account (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE);";

            case ACCOUNT:
                return "CREATE TABLE IF NOT EXISTS account (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  balance FLOAT NOT NULL, " +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE KEY id_UNIQUE (id));";


            case USER:
                return "CREATE TABLE IF NOT EXISTS user (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  username VARCHAR(200) NOT NULL," +
                        "  password VARCHAR(64) NOT NULL," +
                        "  roleId INT NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  CONSTRAINT roleId" +
                        "    FOREIGN KEY (roleId)" +
                        "    REFERENCES role (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE);";

            case ROLE:
                return "CREATE TABLE IF NOT EXISTS role (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  role VARCHAR(100) NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE KEY id_UNIQUE (id));";

            case ACTIVITY:
                return "CREATE TABLE IF NOT EXISTS activity(" +
                        " id INT NOT NULL AUTO_INCREMENT, " +
                        " name VARCHAR(100) NOT NULL, " +
                        " operation VARCHAR(100) NOT NULL," +
                        " opDate DATE NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE KEY id_UNIQUE (id));";

            default:
                return "";

        }
    }

}
