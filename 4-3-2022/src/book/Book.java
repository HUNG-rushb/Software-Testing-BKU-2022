package book;

import connection.GetConnection;
import connection.GetResultSet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Book {
    private int id;
    private String title;
    private Author author;
    private double costPrice;
    private double price;
    private int nrOfSales;
    private int quantity;
    private String category;

    private Book(Builder builder){
        this.id = builder.id;
        this.title = builder.title;
        this.author = builder.author;
        this.costPrice = builder.costPrice;
        this.price = builder.price;
        this.nrOfSales = builder.nrOfSales;
        this.quantity = builder.quantity;
        this.category = builder.category;


    }
    public static class Builder{
        private int id;
        private String title;
        private Author author;
        private double costPrice;
        private double price;
        private int nrOfSales;
        private int quantity;
        private String category;

        public Builder withTitle(String title){
            this.title = title;
            return this;
        }

        public Builder withAuthor(Author author){
            this.author = author;
            return this;
        }


        public Builder withCostPrice (double costPrice){
            this.costPrice = costPrice;
            return this;
        }
        public Builder withCategory(String category){
            this.category = category;
            return this;
        }

        public Builder withPrice(double price){
            this.price = price;
            return this;
        }

        public Builder withNumberOfSales(int nrOfSales){
            this.nrOfSales = nrOfSales;
            return this;
        }

        public Builder withId(int id){
            this.id = id;
            return this;
        }
        public Builder withQuantity(int quantity){
            this.quantity = quantity;
            return this;
        }

        public Book build(){
            return new Book(this);
        }
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public double getPrice() {
        return price;
    }

    public int getNrOfSales() {
        return nrOfSales;
    }

    public void updateQuantity(int number){
        final String UPDATE_QUANTITY_SQL = "UPDATE book SET quantity= "+(this.quantity - number)+" WHERE id = "+this.id;
        try {
            Connection connection = GetConnection.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(UPDATE_QUANTITY_SQL);
            this.quantity-= number;


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateNrOfSales(int number){
        final String UPDATE_NUMBER_OF_SALES_SQL = "UPDATE book SET nr_of_sales = "+(this.nrOfSales + number)+" WHERE id = "+this.id;
        try {
            Statement statement = GetConnection.getConnection().createStatement();
            statement.executeUpdate(UPDATE_NUMBER_OF_SALES_SQL);
            this.nrOfSales+= number;


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNrOfSales(int nrOfSales) {
        this.nrOfSales = nrOfSales;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
