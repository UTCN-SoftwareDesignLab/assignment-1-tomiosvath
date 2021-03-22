package database;


public class Constants {
    public static class Schemas {
        public static final String TEST = "test_bank";
        public static final String PRODUCTION = "bank";

        public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
        //public static final String[] SCHEMAS = new String[]{TEST};
    }

    public static class Tables {
        public static final String ACCOUNT = "account";
        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String CLIENT = "client";
        public static final String ACTIVITY = "activity";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{ACCOUNT, CLIENT, ROLE, USER, ACTIVITY};
        //public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{ACTIVITY};
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE};
    }

}

