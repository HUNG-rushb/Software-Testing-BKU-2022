package database;


public class Database {
    private static Database instance;
    private Database(){}
    public static Database getInstance(){
        if (instance == null){
            instance = new Database();
        }
        return instance;
    }

    public void startDatabase(){

    }

    public void enterDefaultBooks(){

    }

    public void enterDefaultClientAccounts(){

    }

    public void enterDefaultAdminAccount(){

    }

}
