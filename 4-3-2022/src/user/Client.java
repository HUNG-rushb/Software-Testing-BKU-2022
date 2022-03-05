package user;

import book.Author;
import connection.GetConnection;
import panel.Panel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Client extends User{

    private String name;
    private String surname;
    private int address;
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
    private boolean loggedIn;

    private Client(Builder builder) throws SQLException {

        final  String INSERT_CLIENT_SQL = "INSERT INTO client(id, first_name, last_name, sex, address_id, phone_number, email, nr_books_purchased, total_amount_spent, username, password)\n"
                + "	VALUES (?,?,?,?,?,?,?,?,?,?,?);";

        this.name = builder.name;
        this.surname = builder.surname;
        this.address = builder.address;
        this.phoneNumber = builder.phoneNumber;
        this.email = builder.email;
        this.favoriteCategory = builder.favoriteCategory;
        this.nrOfBooksPurchased = builder.nrOfBooksPurchased;
        this.totalAmountSpent = builder.totalAmountSpent;
        this.username = builder.username;
        this.password = builder.password;
        this.favoriteAuthor = builder.favoriteAuthor;
        this.sex = builder.sex;
        this.id = builder.id;

    }

    public static class Builder{
        private String name;
        private String surname;
        private int address;
        private String phoneNumber;
        private String email;
        private String favoriteCategory;
        private int nrOfBooksPurchased;
        private double totalAmountSpent;
        private String username;
        private String password;
        private Author favoriteAuthor;
        private String sex;
        private int id;

        public Builder withName(String name){
            this.name = name;
            return this;
        }

        public Builder withSurname(String surname){
            this.surname = surname;
            return this;
        }
        public Builder withId(int id){
            this.id = id;
            return this;
        }

        public Builder withUsername(String username){
            this.username = username;
            return this;
        }

        public Builder withPassword(String password){
            this.password = password;
            return this;
        }
        public Builder withAddress(int address){
            this.address = address;
            return this;
        }
        public Builder withPhoneNumber(String phoneNumber){
            this.phoneNumber = phoneNumber;
            return this;
        }
        public Builder withEmail(String email){
            this.email = email;
            return this;
        }
        public Builder withFavoriteCategory(String favoriteCategory){
            this.favoriteCategory = favoriteCategory;
            return this;
        }
        public Builder withNrOfBooksPurchased(int nrOfBooksPurchased){
            this.nrOfBooksPurchased = nrOfBooksPurchased;
            return this;
        }
        public Builder withTotalAmountSpent(double totalAmountSpent){
            this.totalAmountSpent = totalAmountSpent;
            return this;
        }
        public Builder withFavoriteAuthor(Author favoriteAuthor){
            this.favoriteAuthor = favoriteAuthor;
            return this;
        }
        public Builder withSex(String sex){
            this.sex = sex;
            return this;
        }

        public Client build(){
            try {
                return new Client(this);

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public int getNrOfBooksPurchased() {
        return nrOfBooksPurchased;
    }

    public double getTotalAmountSpent() {
        return totalAmountSpent;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void addNrOfBooksPurchased(int number){
        try {
            int numberOfBooksPurchased = 0;
            ResultSet resultSet = GetConnection.getConnection().createStatement().executeQuery("SELECT * FROM client");
            while (resultSet.next()) {
                if (resultSet.getString("first_name").equalsIgnoreCase(this.name)
                        && resultSet.getString("last_name").equalsIgnoreCase(this.surname)) {
                    numberOfBooksPurchased = resultSet.getInt("nr_books_purchased");
                }
            }
            GetConnection.getConnection().createStatement().executeUpdate("UPDATE client SET nr_books_purchased = "
                    + (numberOfBooksPurchased + number) + " WHERE id = " +this.id);
            this.nrOfBooksPurchased+=number;

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addTotalAmountSpent(double number){
        try {
            double totalAmountSpent = 0;
            ResultSet resultSet = GetConnection.getConnection().createStatement().executeQuery("SELECT * FROM client");
            while (resultSet.next()) {
                if (resultSet.getString("first_name").equalsIgnoreCase(this.name)
                        && resultSet.getString("last_name").equalsIgnoreCase(this.surname)) {
                    totalAmountSpent = resultSet.getDouble("total_amount_spent");
                }
            }
            GetConnection.getConnection().createStatement().executeUpdate("UPDATE client SET total_amount_spent = "
                    + (totalAmountSpent + number) + " WHERE id = " +this.id);
            this.totalAmountSpent+=number;

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showPanel(User user) {
        Panel.getInstance().showPanel(this);
    }
}
