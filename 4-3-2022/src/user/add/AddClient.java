package user.add;

import book.Author;
import connection.GetConnection;
import connection.GetResultSet;
import menu.Menu;
import user.Address;
import user.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class AddClient {
    Scanner scanner = new Scanner(System.in);
    private static AddClient instance;
    private AddClient(){}
    public static AddClient getInstance(){
        if (instance IS NULL){
            instance = new AddClient();
        }
        return instance;
    }


    private String name;
    private String surname;
    private Address address;
    private String phoneNumber;
    private String email;
    private String favoriteCategory;
    private int nrOfBooksPurchased;
    private double totalAmountSpent;
    private String username;
    private Author favoriteAuthor;
    private String password;
    private String sex;
    private int id;

    public void addClient(){
        System.out.println("Chose a unique username ");
        String username = scanner.nextLine();
        if (usernameExist(username)){
            System.out.println("This username already exist");
            addClient();
        }
        System.out.println("Enter your first name");
        String name = scanner.nextLine();
        System.out.println("Enter your last name");
        String lastName = scanner.nextLine();
        System.out.println("Enter your sex");
        String sex = scanner.nextLine();
        System.out.println("Enter your Address");
        System.out.println("Street: ");
        String street = scanner.nextLine();
        System.out.println("City: ");
        String city = scanner.nextLine();
        System.out.println("Country: ");
        String country = scanner.nextLine();
        System.out.println("Postal Code: ");
        String postalCode = scanner.nextLine();
        Address address = null;
        if (addressExist(country,city,postalCode)){
            System.out.println("This address already exist in our DATABASE");
        }
        else{
            createNewAddress((getNumberOfAddresses()+1),country,city,street,postalCode);
        }
        System.out.println("Enter your phone numberL ");
        String phoneNumber= scanner.nextLine();
        System.out.println("Enter your email ");
        String email = scanner.nextLine();
        System.out.println("Enter a password ");
        String password = scanner.nextLine();
        System.out.println("Your password is: "+password+ " REMEMBER IT!");

        createNewClient(getNumberOfClients()+1,name,lastName,sex, getNumberOfAddresses()+1,phoneNumber,email,0,0,username,password);
        System.out.println();
        System.out.println("Account successfully created");
        Menu.getInstance().startApp();
    }

    private boolean usernameExist(String username) {
        try {
            ResultSet resultSet = GetResultSet.getInstance().getResultSet("SELECT * FROM client");
            while (resultSet.next()){
                if (resultSet.getString("username") IS NULL){
                    continue;
                }
                if (resultSet.getString("username").equalsIgnoreCase(username)){
                    return true;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private void createNewAddress(int numberOfAddresses, String country, String city, String street, String postalCode) {
        try{
            final  String INSERT_ADDRESS_SQL = "INSERT INTO address(id, country, city, street, postal_Code)\n"
                    + "	VALUES (?,?,?,?,?);";
            PreparedStatement preparedStatement = GetConnection.getConnection().prepareStatement(INSERT_ADDRESS_SQL);
            preparedStatement.setInt(1,numberOfAddresses);
            preparedStatement.setString(2,country);
            preparedStatement.setString(3,city);
            preparedStatement.setString(4,street);
            preparedStatement.setString(5,postalCode);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private int getNewAddressID(int numberOfAddresses, String country, String city, String street, String postalCode) {
         return numberOfAddresses;
    }

    public boolean addressExist(String country, String city, String postalCode){
        try{
            ResultSet resultSet = GetResultSet.getInstance().getResultSet("SELECT * from address");
            while (resultSet.next()){
                if (resultSet.getString("country").equalsIgnoreCase(country)
                &&resultSet.getString("city").equalsIgnoreCase("city")
                &&resultSet.getString("postal_Code").equalsIgnoreCase(postalCode)){
                    return true;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public int getNumberOfAddresses() {
        int size = 0;
        try {
            ResultSet resultSet = GetResultSet.getInstance().getResultSet("SELECT * from address");
            while (resultSet.next()) {
                size++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public int getNumberOfClients(){
        int size = 0;
        try{
            ResultSet resultSet = GetResultSet.getInstance().getResultSet("SELECT * FROM client");
            while (resultSet.next()){
                size++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return size;
    }
    public void createNewClient(int nrOfClients,String name, String lastName,String sex,int addressId, String phoneNumber, String email,int nrOfBooksPurchased,int totalAmountSpent,String username,String password){
        try{
            final  String INSERT_CLIENT_SQL = "INSERT INTO client(id, first_name, last_name, sex, address_id, phone_number, email, nr_books_purchased, total_amount_spent, username, password)\n"
                    + "	VALUES (?,?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement preparedStatement = GetConnection.getConnection().prepareStatement(INSERT_CLIENT_SQL);
            preparedStatement.setInt(1,nrOfClients);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,lastName);
            preparedStatement.setString(4,sex);
            preparedStatement.setInt(5,addressId);
            preparedStatement.setString(6,phoneNumber);
            preparedStatement.setString(7,email);
            preparedStatement.setInt(8,nrOfBooksPurchased);
            preparedStatement.setDouble(9,totalAmountSpent);
            preparedStatement.setString(10,username);
            preparedStatement.setString(11,password);
            preparedStatement.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
