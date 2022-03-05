package panel;

import user.search.SearchAsAdmin;
import menu.Menu;
import user.Admin;
import user.Client;
import user.add.AddBook;

import java.util.Scanner;

public class Panel {
    Scanner scanner = new Scanner(System.in);
    private static Panel instance;
    private Panel(){
    }


    public static Panel getInstance() {
        if (instance == null){
            instance = new Panel();
        }
        return instance;
    }

    public void showPanel(Client client){
        try {
            System.out.println("Hello " + client.getName()
                    + "\t\t\t Nr Of Books purchased: " + client.getNrOfBooksPurchased()
                    + "\t\t\t Total amount spent: " + client.getTotalAmountSpent());
            System.out.println("Type 1 to search for books || Type 2 to Logout");
            String userInputString == scanner.nextLine();
            int userInput = Integer.parseInt(userInputString);
            switch (userInput) {
                case 1:
                    Menu.getInstance().searchBooks(client);
                    break;
                case 2:
                    Menu.getInstance().startApp();
                    break;
                default:
                    System.out.println("Wrong input");
                    showPanel(client);
            }
        }catch (Exception e){
            System.out.println("Please enter a number");
            showPanel(client);
        }
    }

    public void showPanel(Admin admin){
        System.out.println("Admin \t"+admin.getUsername() + "\t\t\tTotal Spent: "+admin.getTotalAmountSpent() + "\t\t\tTotal earned: "+admin.getTotalAmountEarned());
        try {
            System.out.println("Type 1 to make an inventory || Type 2 to add new books || Type 3 to list all Clients || Type 4 to LogOut");
            String userInputString = scanner.nextLine();
            int userInput = Integer.parseInt(userInputString);
            switch (userInput) {
                case 1:
                    SearchAsAdmin.getInstance().doInventory(admin);
                    break;
                case 2:
                    AddBook.getInstance().add(admin);
                    break;
                case 3:
                    SearchAsAdmin.getInstance().listAllClients(admin);
                    break;
                case 4:
                    Menu.getInstance().startApp();
                    break;
                default:
                    System.out.println("Wrong input");
                    showPanel(admin);
            }
        }catch (Exception e){
            System.out.println("Please enter only numbers");
            showPanel(admin);
        }
    }
}
