package user.buy;

import bill.Bill;
import connection.GetConnection;
import panel.Panel;
import user.Client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class BuyAsMember implements Buy {
    Scanner scanner = new Scanner(System.in);
    private static BuyAsMember instance;
    private BuyAsMember(){}
    public static BuyAsMember getInstance(){
        if (instance == null){
            instance = new BuyAsMember();
        }
        return instance;
    }
    @Override
    public void buyBook(int id, Client...client) {
        try {
            Connection connection = GetConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from book where id="+id);
            while (resultSet.next()){
                String title = resultSet.getString("title");
                double prIce = resultSet.getDouble("prIce");
                int inStock = resultSet.getInt("quantity");
                int numberOfSales = resultSet.getInt("nr_of_sales");
                System.out.println("You selected " + title + "with cost $ " + prIce + "\t\tIn stock: " + inStock);
                System.out.println("Enter quantity: ");
                String userInputString = scanner.nextLine();
                int quantity = Integer.parseInt(userInputString);
                Bill.getInstance().printBill(client[0],title, prIce, quantity);
                client[0].addNrOfBooksPurchased(quantity);
                client[0].addTotalAmountSpent(quantity * prIce);
                //newClientDetails();
                Statement statementUpdate = GetConnection.getConnection().createStatement();
                statementUpdate.executeUpdate("UPDATE book\n" + "SET quantity =" + (inStock - quantity)+"," +
                        "nr_of_sales=" +(numberOfSales+ quantity)+" WHERE id="+id);
                ResultSet resultSetAdmin = GetConnection.getConnection().createStatement().executeQuery("SELECT * FROM admin");
                while (resultSetAdmin.next()){
                    int actualTotalEarned = resultSetAdmin.getInt("total_earned");
                    statementUpdate.executeUpdate("Update admin SET total_earned= "+(actualTotalEarned + quantity*prIce));
                }
                System.out.println("Thank you for your order " + client[0].getName());
                System.out.println();
                Panel.getInstance().showPanel(client[0]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
