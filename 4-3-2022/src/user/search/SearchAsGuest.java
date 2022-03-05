package user.search;

import user.buy.BuyAsGuest;
import connection.GetConnection;
import menu.Menu;
import user.Client;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SearchAsGuest implements Search{
    Scanner scanner = new Scanner(System.in);
    private final String LIST_ALL_BOOKS = "SELECT * FROM book";
    private final String LIST_ALL_AUTHORS = "SELECT * FROM author";
    private static SearchAsGuest instance;

    private SearchAsGuest(){}
    public static SearchAsGuest getInstance(){
        if (instance == null){
            instance = new SearchAsGuest();
        }
        return instance;
    }

    @Override
    public void listAllBooks(Client... client) {
        try {
            Statement statement = GetConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(LIST_ALL_BOOKS);
            int size = 0;
            List<Integer> listOfSelectedID = new ArrayList<>();


            while (resultSet.next()) {
                int bookId = resultSet.getInt("id");
                listOfSelectedID.add(bookId);
                Statement statementAuthor = GetConnection.getConnection().createStatement();
                ResultSet resultSetAuthor = statementAuthor.executeQuery("Select * from author where id =" + resultSet.getInt("author_id"));
                String authorName = "";
                String authorSurname = "";
                while (resultSetAuthor.next()) {
                    authorName = resultSetAuthor.getString("first_name");
                    authorSurname = resultSetAuthor.getString("last_name");
                }
                System.out.println(resultSet.getInt("id") + "\t" +
                        resultSet.getString("title") + "\t\t\t\t" +
                        authorName + " " + authorSurname + "\t\t\t\t $" +
                        resultSet.getDouble("price") + "\t\t\t In Stock: " +
                        resultSet.getInt("quantity") + "\t\t\t Number of Sales: " +
                        resultSet.getInt("nr_of_sales"));
                size++;
            }
            selectBookAfterList(size, listOfSelectedID);

        }catch (NumberFormatException e) {
            System.out.println("Please type a number");
            listAllBooks();
        }catch (ArrayIndexOutOfBoundsException e){{
            System.out.println("Please type a number");
            listAllBooks();
        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void selectBookAfterList(int size, List<Integer> list, Client...client){
        try {
            if (size == 0) {
                System.out.println("No books in the list");
                Menu.getInstance().searchBooks();
            } else {

                System.out.println("Type ID of book to buy || Type 0 to go back");
                String userInputString = scanner.nextLine();
                int userInput = Integer.parseInt(userInputString);

                if (!list.contains(userInput)) {
                    System.out.println("ID\t" + userInput + "  does not exist in the list");
                    Menu.getInstance().searchBooks();
                } else {
                    if (userInput == 0) {
                        Menu.getInstance().startApp();
                    } else {
                        BuyAsGuest.getInstance().buyBook(userInput);
                    }
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){
            listAllBooks(client);
        }
        catch (NumberFormatException e){
            System.out.println("Pleae type a number");
            selectBookAfterList(size, list, client[0]);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void searchByTitle(Client...client){
        try {
            Statement statement = GetConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(LIST_ALL_BOOKS);
            System.out.println("Enter title: ");
            String title = scanner.nextLine();
            int size = 0;
            List <Integer> idList = new ArrayList<>();
            while (resultSet.next()) {
                String bookTitle = resultSet.getString("title");
                if (bookTitle.equalsIgnoreCase(title)) {
                    int bookId = resultSet.getInt("id");
                    idList.add(bookId);
                    Statement statementAuthor = GetConnection.getConnection().createStatement();
                    ResultSet resultSetAuthor = statementAuthor.executeQuery("Select * from author where id =" + resultSet.getInt("author_id"));
                    size++;
                    String authorName = "";
                    String authorSurname = "";
                    while (resultSetAuthor.next()) {
                        authorName = resultSetAuthor.getString("first_name");
                        authorSurname = resultSetAuthor.getString("last_name");
                    }
                    System.out.println(resultSet.getInt("id") + "\t" +
                            resultSet.getString("title") + "\t" +
                            authorName + " " + authorSurname + "\t $" +
                            resultSet.getDouble("price") + "\t In Stock: " +
                            resultSet.getInt("quantity") + "\t Number of Sales: " +
                            resultSet.getInt("nr_of_sales"));
                }
            }
            selectBookAfterList(size,idList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void searchByAuthor(Client...client){
        try {
            Statement statement = GetConnection.getConnection().createStatement();
            ResultSet listOfAuthors = statement.executeQuery(LIST_ALL_AUTHORS);
            System.out.println("Enter author: ");
            String author = scanner.nextLine();
            int id = 0;
            int size = 0;
            String authorName = "";
            String authorSurname = "";
            while (listOfAuthors.next()){
                if (listOfAuthors.getString("first_name").equalsIgnoreCase(author) ||
                        listOfAuthors.getString("last_name").equalsIgnoreCase(author)){
                    id = listOfAuthors.getInt("id");
                    authorName = listOfAuthors.getString("first_name");
                    authorSurname = listOfAuthors.getString("last_name");
                }
            }
            Statement statementBook = GetConnection.getConnection().createStatement();
            ResultSet listAllBooks = statementBook.executeQuery("Select * FROM book where author_id ="+id);
            List <Integer>idList = new ArrayList<>();
            while (listAllBooks.next()){
                size++;
                int bookId = listAllBooks.getInt("id");
                idList.add(bookId);
                System.out.println(listAllBooks.getInt("id") + "\t" +
                        listAllBooks.getString("title") + "\t" +
                        authorName + " " + authorSurname + "\t $" +
                        listAllBooks.getDouble("price") + "\t In Stock: " +
                        listAllBooks.getInt("quantity") + "\t Number of Sales: " +
                        listAllBooks.getInt("nr_of_sales"));
            }
            selectBookAfterList(size, idList);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void searchByCategory(Client...client){
        try {
            Statement statement = GetConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(LIST_ALL_BOOKS);
            System.out.println("Enter category: ");
            String title = scanner.nextLine();
            List <Integer> idList = new ArrayList<>();
            int size = 0;
            while (resultSet.next()) {
                String bookTitle = resultSet.getString("category");
                if (bookTitle.equalsIgnoreCase(title)) {
                    int bookId = resultSet.getInt("id");
                    idList.add(bookId);
                    Statement statementAuthor = GetConnection.getConnection().createStatement();
                    ResultSet resultSetAuthor = statementAuthor.executeQuery("Select * from author where id =" + resultSet.getInt("author_id"));
                    size++;
                    String authorName = "";
                    String authorSurname = "";
                    while (resultSetAuthor.next()) {
                        authorName = resultSetAuthor.getString("first_name");
                        authorSurname = resultSetAuthor.getString("last_name");
                    }
                    System.out.println(resultSet.getInt("id") + "\t" +
                            resultSet.getString("title") + "\t" +
                            authorName + " " + authorSurname + "\t" +
                            resultSet.getDouble("price") + "\t In Stock: " +
                            resultSet.getInt("quantity") + "\t Number of Sales: " +
                            resultSet.getInt("nr_of_sales"));
                }
            }
            selectBookAfterList(size, idList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
