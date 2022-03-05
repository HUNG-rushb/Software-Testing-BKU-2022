package user.search;

import connection.GetResultSet;
import panel.Panel;
import user.Admin;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SearchAsAdmin {
    final String listAllBooksSQL = "SELECT * FROM book";
    final String listAllAuthorSQL = "SELECT * FROM author";
    private static SearchAsAdmin instance;
    private SearchAsAdmin(){}
    public static SearchAsAdmin getInstance(){
        if (instance == null){
            instance = new SearchAsAdmin();
        }
        return instance;
    }
    public void doInventory(Admin admin){
        try {
            List<Integer>quantityList = new ArrayList<>();
            List <Double>costPriceList = new ArrayList<>();
            List<Double>priceList = new ArrayList<>();
            List<Integer>nrOfSalesList = new ArrayList<>();
            ResultSet resultSet = GetResultSet.getInstance().getResultSet(listAllBooksSQL);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                double costPrice = resultSet.getDouble("cost_price");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                int nrOfSales = resultSet.getInt("nr_of_sales");

                quantityList.add(quantity);
                costPriceList.add(costPrice * quantity);
                priceList.add(price * quantity);
                nrOfSalesList.add(nrOfSales);
                System.out.println(id+"\t"+title+"\t\t\t\t\t"+"\t\t\t Cost Price: $ "+costPrice+"\t\t\t In Stock: "
                        +quantity+"\t\t\t Nr of Sales: "+nrOfSales+"\t\t\t Price: $ "+price);

            }
            printReport(quantityList,costPriceList,priceList,nrOfSalesList);
            Panel.getInstance().showPanel(admin);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void printReport(List<Integer> quantityList,List<Double>costPriceList,List<Double>priceList, List<Integer>nrOfSalesList){
        int numberOfAllBooks = quantityList.stream().mapToInt(Integer::intValue).sum();
        int nrOfAllBookSold = nrOfSalesList.stream().mapToInt(Integer::intValue).sum();
        double totalCostSpentForBooksInStock = costPriceList.stream().mapToDouble(Double::doubleValue).sum();
        double totalValueOfBooksInStock = priceList.stream().mapToDouble(Double::doubleValue).sum();
        System.out.println();
        System.out.println("Actual report\t\t\t\t\t\t\t\t"+ LocalDateTime.now());
        System.out.println("Total books in magazine: \t\t"+numberOfAllBooks);
        System.out.println("Total cost of books in magazine:\t\t$ " + totalCostSpentForBooksInStock);
        System.out.println("Total value of books in magazine:\t\t$ "+totalValueOfBooksInStock);
        System.out.println("Total number of sold books\t\t" + nrOfAllBookSold);
        System.out.println();
    }

    public void listAllClients(Admin admin) {
        try {
            ResultSet resultSet = GetResultSet.getInstance().getResultSet("Select * FROM client");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int addressId = resultSet.getInt("address_id");
                String phoneNumber = resultSet.getString("phone_number");
                int nrOfBooksPurchased = resultSet.getInt("nr_books_purchased");
                double amountSpent = resultSet.getDouble("total_amount_spent");
                String street ="";
                String city = "";
                ResultSet resultSetAddress = GetResultSet.getInstance().getResultSet("SELECT * from address");
                while (resultSetAddress.next()){
                    if (resultSetAddress.getInt("id")==addressId){
                        street = resultSetAddress.getString("street");
                        city = resultSetAddress.getString("city");
                    }
                }
                System.out.println(id + "\t" +firstName+" "+lastName+"\t\t\t" +
                        street+"\t"+city+"\t\t\tPhone Number: "+phoneNumber+"\t\t\tBook Purchased: "+nrOfBooksPurchased+"\t\t\tAmount spent: "+amountSpent);
            }
            Panel.getInstance().showPanel(admin);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
