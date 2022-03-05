package user.add;

import book.Author;
import book.Book;
import connection.GetConnection;
import connection.GetResultSet;
import panel.Panel;
import user.Admin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddBook {
    Scanner scanner = new Scanner(System.in);
    private static AddBook instance;
    List<Integer> TheListOfSelectedID = new ArrayList<>();
    private AddBook(){}
    public static AddBook getInstance(){
        if (instance == null){
            instance = new AddBook();
        }
        return instance;
    }

    public void add(Admin admin){
        try {
            System.out.println("Type 1 to add quantity for an existing book || Type 2 to add a new book || Type 3 to go back");
            String userInputString = scanner.nextLine();
            int userInput = Integer.parseInt(userInputString);
            switch (userInput) {
                case 1:
                    addQuantity(admin);
                    break;
                case 2:
                    addNewBook(admin);
                    break;
                case 3:
                    Panel.getInstance().showPanel(admin);
                    break;
                default:
                    System.out.println("Wrong input");
                    add(admin);
            }
        }catch (Exception e){
            System.out.println("Please enter a number");
            add(admin);
        }
    }

    public void addQuantity(Admin admin){
        list_all_books();
        System.out.println("Type ID of the book you want to update || Type 0 to go back");
        String userInputString = scanner.nextLine();
        int userInput = Integer.parseInt(userInputString);
        if (userInput == 0){
            Panel.getInstance().showPanel(admin);
        }
        else if (!TheListOfSelectedID.contains(userInput)){
            System.out.println("Wrong ID");
            add(admin);
        }
        else{
            System.out.println("Enter quantity you want to add");
            String userInputString1 = scanner.nextLine();
            int quantityToAdd = Integer.parseInt(userInputString1);
            Admin.getInstance().addBookQuantity(userInput,quantityToAdd);
            System.out.println("Quantity added successfully");
            System.out.println();
            Panel.getInstance().showPanel(admin);
        }
    }

    public void addNewBook(Admin admin){
        System.out.println("Enter book title: ");
        String title = scanner.nextLine();
        System.out.println("Enter author name: ");
        String authorName = scanner.nextLine();
        System.out.println("Enter author surname: ");
        String authorSurname = scanner.nextLine();
        System.out.println("Enter author nationality: ");
        String authorNationality = scanner.nextLine();
        System.out.println("Enter author sex");
        String authorSex = scanner.nextLine();
        System.out.println("Enter author birthday");
        String authorBirthday = scanner.nextLine();
        System.out.println("Enter book category");
        String category = scanner.nextLine();
        System.out.println("Enter cost price ");
        String costPriceString = scanner.nextLine();
        double costPrice = Double.parseDouble(costPriceString);
        System.out.println("Enter price to sell");
        String priceToSellString = scanner.nextLine();
        double priceToSell = Double.parseDouble(priceToSellString);
        System.out.println("Enter quantity");
        String quantityString = scanner.nextLine();
        int quantity = Integer.parseInt(quantityString);
        Author author = null;
        Book book = null;
        if (!authorExist(authorName,authorSurname)){
            author = new Author.Builder()
                    .withName(authorName)
                    .withSurname(authorSurname)
                    .withNationality(authorNationality)
                    .withSex(authorSex)
                    .withBirthday(authorBirthday)
                    .build();
            createNewBook((getBookLength()+1),title,author.getId(),priceToSell,quantity,0,costPrice,category);
            Panel.getInstance().showPanel(admin);
        }
        else{
            System.out.println("Author already exist in our DATABASE");
            if (!bookExist(title)){
                try{
                   ResultSet resultSet = GetResultSet.getInstance().getResultSet("SELECT * FROM AUTHOR");
                   int id = 0;
                   while (resultSet.next()) {
                       if (resultSet.getString("first_name").equalsIgnoreCase(authorName) && resultSet.getString("last_name").equalsIgnoreCase(authorSurname)) {
                           id = resultSet.getInt("id");
                           createNewBook((getBookLength()+1),title,id,priceToSell,quantity,0,costPrice,category);
                       }
                   }
                   Panel.getInstance().showPanel(admin);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("We already have this book on our DATABASE");
            }
        }
    }

    public boolean authorExist(String name, String surname){
        try{
            ResultSet resultSet = GetResultSet.getInstance().getResultSet("SELECT * FROM author");
            while (resultSet.next()){
                if (resultSet.getString("first_name").equalsIgnoreCase(name)
                && resultSet.getString("last_name").equalsIgnoreCase(surname)){
                    return true;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void list_all_books(){
        try{
            Statement statement = GetConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("Select * FROM book");

            while (resultSet.next()){
                int bookId = resultSet.getInt("id");
                TheListOfSelectedID.add(bookId);
                Statement statementAuthor = GetConnection.getConnection().createStatement();
                ResultSet resultSetAuthor = statementAuthor.executeQuery("Select * from author where id =" +resultSet.getInt("author_id"));
                String authorName= "";
                String authorSurname = "";
                while (resultSetAuthor.next()){
                    authorName = resultSetAuthor.getString("first_name");
                    authorSurname = resultSetAuthor.getString("last_name");
                }
                System.out.println(resultSet.getInt("id")+"\t"+
                        resultSet.getString("title")+"\t"+
                        authorName + " " + authorSurname + "\t $"+
                        resultSet.getDouble("price")+ "\t In Stock: "+
                        resultSet.getInt("quantity")+"\t Number of Sales: "+
                        resultSet.getInt("nr_of_sales"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean bookExist(String title){
        try{
            ResultSet resultSet = GetResultSet.getInstance().getResultSet("SELECT * FROM book");
            while (resultSet.next()){
                if (resultSet.getString("title").equalsIgnoreCase(title)){
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public int getBookLength(){
        int size = 0;
        try{
            ResultSet resultSet = GetResultSet.getInstance().getResultSet("SELECT * FROM book");
            while (resultSet.next()){
                size++;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return size;
    }

    public void createNewBook(int id, String title, int author_id, double price, int quantity, int nrOfSales, double costPrice, String category){
        try{
            final  String INSERT_BOOK_SQL = "INSERT INTO book(id, title, author_id, price, quantity, nr_of_sales, cost_price, category)\n"
                    + "	VALUES (?,?,?,?,?,?,?,?);";

            PreparedStatement preparedStatement = GetConnection.getConnection().prepareStatement(INSERT_BOOK_SQL);
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,title);
            preparedStatement.setInt(3,author_id);
            preparedStatement.setDouble(4,price);
            preparedStatement.setInt(5,quantity);
            preparedStatement.setInt(6,nrOfSales);
            preparedStatement.setDouble(7,costPrice);
            preparedStatement.setString(8,category);
            preparedStatement.executeUpdate();
            Admin.getInstance().updateTotalSpent(costPrice*quantity);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
