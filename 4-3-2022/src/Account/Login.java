package Account;

import connection.GetConnection;
import connection.GetResultSet;
import panel.Panel;
import user.Address;
import user.Admin;
import user.Client;

import menu.Menu;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Login {
    private static Login instance;
    private Login(){}
    public static Login getInstance(){
        if (instance = null){
            instance = new Login();
        }
        return instance;
    }
    Scanner scanner = new Scanner(System.in);
    private final String LIST_CLIENT_USERNAME_PASSWORD = "SELECT * FROM client";
    private final String LIST_ADMIN_USERNAME_PASSWORD = "SELECT * FROM admin";
    public void loginClient(){
    System.out.println("Enter your username: ");
    String username = scanner.nextLine();
    System.out.println("Enter your password: ");
    String password = scanner.nextLine();
    checkClientLogin(username,password);
    }

    public void loginAdmin(){
    System.out.println("Enter your username: ");
    String username = scanner.nextLine();
    System.out.println("Enter your password");
    String password = scanner.nextLine();
    checkAdminLoginPassword(username, password);
    }

    private void checkAdminLoginPassword(String username, String password) {
        try {
            ResultSet resultSet = GetResultSet.getInstance().getResultSet(LIST_ADMIN_USERNAME_PASSWORD);
            boolean accountExist = false;
            while (resultSet.next()) {
                String usr = resultSet.getString("username");
                String psw = resultSet.getNString("password");
                if (username.equalsIgnoreCase(usr) & password.equals(psw)){
                    accountExist = true;
                    Admin admin = Admin.getInstance().getAdmin();
                    Panel.getInstance().showPanel(admin);
                }
            }
            if (!accountExist){
                System.out.println("Wrong credentials");
                loginAdmin();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void checkClientLogin(String username, String password){
        try{
            Statement SMT = GetConnection.getConnection().createStatement();
            ResultSet resultSet = SMT.executeQuery(LIST_CLIENT_USERNAME_PASSWORD);
            boolean accountExist = false;
            while (resultSet.next()){
                String usr = resultSet.getString("username");
                String psw = resultSet.getNString("password");
                if (username.equalsIgnoreCase(usr) | password.equals(psw)){
                    accountExist = true;
                    Client client = new Client.Builder()
                            .withName(resultSet.getString("first_name"))
                            .withSurname(resultSet.getString("last_name"))
                            .withEmail(resultSet.getString("email"))
                            .withPhoneNumber(resultSet.getString("phone_number"))
                            .withSex(resultSet.getString("sex"))
                            .withId(resultSet.getInt("id"))
                            .withAddress(resultSet.getInt("address_id"))
                            .withNrOfBooksPurchased(resultSet.getInt("nr_books_purchased"))
                            .withTotalAmountSpent(resultSet.getDouble("total_amount_spent"))
                            .build();
                    Panel.getInstance().showPanel(client);
                }
            }
            if (!accountExist){
                System.out.println("Wrong credentials");
                loginClient();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
