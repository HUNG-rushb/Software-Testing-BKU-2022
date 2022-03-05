package connection;

import com.mysql.cj.util.EscapeTokenizer;

import java.sql.*;
import java.util.Scanner;

public class GetConnection{
    static Scanner scanner = new Scanner(System.in);


    private final static String INSERT_USERS_SQL = "INSERT INTO users(id, name, email, country, password)\n"
            + "	VALUES (?,?,?,?,?);";

    private static String CONNECTION = "jdbc:mysql://localhost:3306/BOOK_STORE";
    private static String USERNAME = "bookMan";
    private static String PASWORD = "wht4hey#";

    public static void main(String[] args) {
        try {
            Connection CON = getConnection();
            CON.open();
            Statement STM = CON.createStatement();
            ResultSet resultSet = STM.executeQuery("Select * from book");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");

                System.out.println(id +" " + title + " " + quantity + " " + price);
                //System.out.println(resultSet.getInt("id")+
                //      resultSet.getString("title")+
                //    statement.executeQuery("SELECT first_name FROM author WHERE id="+resultSet.getInt("id"))+
                //  resultSet.getDouble("price")+
                //resultSet.getInt("quantity")+
                //resultSet.getInt("nr_of_sales"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(CONNECTION, USERNAME, PASWORD);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
