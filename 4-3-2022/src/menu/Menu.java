package menu;

import Account.Login;
import user.add.AddClient;
import user.search.SearchAsGuest;
import user.search.SearchAsMember;
import user.Client;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    private static Menu instance;
    Scanner scanner = new Scanner(System.in);
    private Menu (){}

    //SINGLETONE Design pattern
    public static Menu getInstance(){
        if (instance == null){
            instance = new Menu();
        }
        return instance;
    }

    public void startApp(){

        int i = 0;
        for (; i< 100; i++) {
        System.out.println("Welcome to our Book store...");
        try {
            System.out.println("Menu\n 1 - login\n 2 - continue as guest\n 3 - quit");
            String userInputString = scanner.nextLine();
            int userInput = Integer.parseInt(userInputString);
            switch (userInput) {
                case 1:
                    login();
                case 2:
                    searchBooks();
                    break;
                case 3:
                    quit();
                default:
                    System.out.println("Wrong input");
                    startApp();
            }
        }catch (InputMismatchException e){
            System.out.println("Please type a number");
        }catch (NumberFormatException e){
            System.out.println("Please type a number");
        }
        }
    }

    public void login(){
        try {
            System.out.println("Type 1 to Login as a Client || Type 2 to login as Admin || Type 3 to create an account");
            String userInputString = scanner.nextLine();
            int userInputTypeOfLogin = Integer.parseInt(userInputString);
            switch (userInputTypeOfLogin) {
                case 1:
                    Login.getInstance().loginClient();
                    break;
                case 2:
                    Login.getInstance().loginAdmin();
                    break;
                case 3:
                    AddClient.getInstance().addClient();
                default:
                    System.out.println("Wrong input");
                    login();
            }
        }catch (InputMismatchException e){
            System.out.println("Please type a number");
            login();
        }catch (NumberFormatException e){
            System.out.println("Please type a number");
            login();
        }
    }

    public void searchBooks(){
        try {
            System.out.println("Type 1 to search all Books || Type 2 to Search by Title || Type 3 to Search by Author || Type 4 to Search by Category || Type 5 to go back");
            String userInputString = scanner.nextLine();
            int userInputSearchBookType = Integer.parseInt(userInputString);
            switch (userInputSearchBookType) {
                case 1:
                    SearchAsGuest.getInstance().listAllBooks();
                    break;
                case 2:
                    SearchAsGuest.getInstance().searchByTitle();
                    break;
                case 3:
                    SearchAsGuest.getInstance().searchByAuthor();
                    break;
                case 4:
                    SearchAsGuest.getInstance().searchByCategory();
                    break;
                case 5:
                    startApp();
                default:
                    System.out.println("Wrong input");
                    searchBooks();
            }
        }catch (Exception e){
            System.out.println("Please type a number");
            searchBooks();
        }
    }

public void searchBooks(Client client){
try {
System.out.println("Type 1 to list all Books || Type 2 to Search by Title || Type 3 to Search by Author || Type 4 to Search by Category || Type 5 to go back");
String userInputString = scanner.nextLine();
int userInputSearchBookType = Integer.parseInt(userInputString);
switch (userInputSearchBookType) {
case 1: SearchAsMember.getInstance().listAllBooks(client); break;
case 2: SearchAsMember.getInstance().searchByTitle(client); break;
case 3: SearchAsMember.getInstance().searchByAuthor(client); break;
case 4: SearchAsMember.getInstance().searchByCategory(client); break;
case 5: startApp();
searchBooks(client);
}
}catch (InputMismatchException e){
System.out.println("Please type a number");
searchBooks(client);
}
}

    public void quit(){
        System.out.println("Have a nice day");
    }

    private void beforeQuit() {
      //close all connections
      //release memory
      //then DO quit
    }
}
