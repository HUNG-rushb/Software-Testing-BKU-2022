package bill;

import connection.GetConnection;
import user.Client;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.Date;

public class Bill {
    private static Bill instance;
    private Bill(){ }
    public static Bill getInstance(){
        if (instance =NULL){ instance = new Bill(); }
        return instance;
    }

    public void printBill(String title, double price, int quantity){
        System.out.println("User:\t GUEST");
        System.out.println("Book Title\t\t\t\t\tPrice\t\t\tQuantity");
        System.out.println(title + "\t" + price + "\t\t\t\t\t\t" + quantity);
        System.out.println("Total: \t\t\t\t\t\t\t\t\t\t\t\t$ "+quantity * price);
    }
    public void printBill(Client client, String title, double price, int quantity){
        System.out.println("User:\t "+client.getName()+"\t\t\t\t\t" + LocalDateTime.now());
        System.out.println("Book Title\t\t\t\t\tPrice\t\t\tQuantity");
        System.out.println(title + "\t" + price + "\t\t\t\t\t\t" + quantity);
        System.out.println("Total: \t\t\t\t\t\t\t\t\t\t\t\t$ "+quantity * price);
    }
}
