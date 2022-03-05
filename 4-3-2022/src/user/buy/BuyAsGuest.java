package user.buy;

import bill.Bill;
import book.Book;
import connection.GetConnection;
import connection.GetResultSet;
import menu.Menu;
import user.Admin;
import user.Client;
import user.search.SearchAsGuest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class BuyAsGuest implements Buy {
Scanner scanner = new Scanner(System.in);
int addressSize = 0;
int userSize = 0;
private static BuyAsGuest instance;
private BuyAsGuest(){}
public static BuyAsGuest getInstance(){
if (instance == null){
instance = new BuyAsGuest();
}
return instance;
}
@Override
public void buyBook(int id, Client...client) {
final String LIST_BOOKS_BY_ID_SQL = "SELECT * FROM book WHERE id= "+id;
try {
ResultSet resultSet = GetResultSet.getInstance().getResultSet(LIST_BOOKS_BY_ID_SQL);
while (resultSet.next()){
int book_id = resultSet.getInt("id");
String title = resultSet.getString("title");
double price = resultSet.getDouble("price");
double cost_price = resultSet.getDouble("cost_price");
int inStock = resultSet.getInt("quantity");
int numberOfSales = resultSet.getInt("nr_of_sales");
String category = resultSet.getString("category");
Book book = new Book.Builder()
.withId(book_id)
.withTitle(title)
.withPrice(price)
.withCostPrice(cost_price)
.withNumberOfSales(numberOfSales)
.withCategory(category)
.withQuantity(inStock)
.build();
System.out.println("You selected " + title + "with cost $ " + price + "\t\tIn stock: " + inStock);
System.out.println("Enter quantity: ");
String userInputString = scanner.nextLine();
int quantity = Integer.parseInt(userInputString);
Bill.getInstance().printBill(title, price, quantity);
newClientDetails();
book.updateQuantity(quantity);
book.updateNrOfSales(quantity);
Admin.getInstance().getAdmin().updateTotalEarned(price * quantity);
Menu.getInstance().startApp();
}
}catch (ArrayIndexOutOfBoundsException e){
SearchAsGuest.getInstance().listAllBooks(client);
}
catch (NumberFormatException e){
System.out.println("Please type a number");
buyBook(id, client);
}
catch (Exception e){
e.printStackTrace();
}
}

public void newClientDetails(){
System.out.println();
System.out.println("Enter your name: ");
String name = scanner.nextLine();
System.out.println("Enter your surname: ");
String surname = scanner.nextLine();
System.out.println("Enter your country: ");
String country = scanner.nextLine();
System.out.println("Enter your city: ");
String city = scanner.nextLine();
System.out.println("Enter your street: ");
String street = scanner.nextLine();
System.out.println("Enter your Postal Code");
String postalCode = scanner.nextLine();
if (!addressExistOnDatabase(country,city, street,postalCode )){
insertAddressOnDatabase(country, city, street, postalCode);
}
System.out.println("Enter your phone Number");
String phoneNumber = scanner.nextLine();
if (!clientExistOnDatabase(name,surname)){
insertUserOnDatabase(name, surname, phoneNumber);
}

System.out.println("Thank you " + name +" for your order");
System.out.println("It will arrive in "+street+ " " + city + " " + country +", " + postalCode + " in 72 hours");
System.out.println("We will call you on " + phoneNumber + " soon");
System.out.println();
}

public boolean addressExistOnDatabase(String country, String city, String street, String postalCode){
final String SELECT_ALL_ADDRESS_SQL = "Select * FROM address";
try {
ResultSet resultSet = GetResultSet.getInstance().getResultSet(SELECT_ALL_ADDRESS_SQL);
while (resultSet.next()) {
addressSize++;
if (resultSet.getString("country").equalsIgnoreCase(country)
&& resultSet.getString("city").equalsIgnoreCase(city)
&& resultSet.getString("street").equalsIgnoreCase(street)
&& resultSet.getString("postal_code").equalsIgnoreCase(postalCode)){
System.out.println("This Address already exist in our Database");
return true;
}
}
}catch (Exception e){
e.printStackTrace();
}
return false;
}

public boolean clientExistOnDatabase(String name, String surname){
final String SELECT_ALL_CLIENT_SQL = "Select * FROM client";
try {
ResultSet resultSet = GetResultSet.getInstance().getResultSet(SELECT_ALL_CLIENT_SQL);
while (resultSet.next()) {
userSize++;
if (resultSet.getString("first_name").equalsIgnoreCase(name)
&& resultSet.getString("last_name").equalsIgnoreCase(surname)){
System.out.println("This client already exist");
return true;
}
}
}catch (Exception e){
e.printStackTrace();
}
return false;
}

private void insertAddressOnDatabase(String country, String city, String street, String postalCode) {
final String INSERT_USERS_SQL = "INSERT INTO address(id, country, city, street, postal_code)\n"
+ "	VALUES (?,?,?,?,?);";
try {
PreparedStatement preparedStatement = GetConnection.getConnection().prepareStatement(INSERT_USERS_SQL);
preparedStatement.setInt(1, (addressSize + 1));
preparedStatement.setString(2, country);
preparedStatement.setString(3, city);
preparedStatement.setString(4, street);
preparedStatement.setString(5, postalCode);
preparedStatement.executeUpdate();
}
catch (Exception e){
e.printStackTrace();
}
}

private void insertUserOnDatabase(String name, String surname, String phoneNumber) {
final String INSERT_USERS_SQL = "INSERT INTO client(id, first_name, last_name, phone_number)\n"
+ "	VALUES (?,?,?,?);";
try {
PreparedStatement preparedStatement = GetConnection.getConnection().prepareStatement(INSERT_USERS_SQL);
preparedStatement.setInt(1, (userSize + 1));
preparedStatement.setString(2, name);
preparedStatement.setString(3, surname);
preparedStatement.setString(4, phoneNumber);
preparedStatement.executeUpdate();
}
catch (Exception e){
e.printStackTrace();
}
}
}
