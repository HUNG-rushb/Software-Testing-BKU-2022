 package Account;

import user.Address;

import java.util.Scanner;

public class Account {
Scanner scanner = new Scanner(System.in);
public void createAccount(){
System.out.println("Enter your name: ");
String name = scanner.nextLine();
System.out.println("Enter your surname: ");
String surname = scanner.nextLine();
System.out.println("Enter your favorite category: ");
String favoriteCategory = scanner.nextLine();
System.out.println("Enter you favorite author: ");
String favoriteAuthor = scanner.nextLine();
//if(resultSet Author name contains string || result Author surname contains Strng){
//Author favoriteAuthor = resultSet where AuthorName = string || resultSet where authorSurname = String
//else{ favorite author = Author.Build.withName(string);
Address address = createAddress();
}

public Address createAddress(){
System.out.println("Enter your country: ");
String country = scanner.nextLine();
System.out.println("Enter your city: ");
String city = scanner.nextLine();
System.out.println("Enter your street: ");
String street = scanner.nextLine();
System.out.println("Enter your Postal Code");
String postalCode = scanner.nextLine();
Address address = new Address.Builder().withState(country)
.withCity(city)
.withStreet(street)
.withPostalCode(postalCode)
.build();
//if !ResultSet contains address { Resultset . add address
return address;
}
}
