package user;

import connection.GetResultSet;
import connection.GetStatement;;
import java.sql.ResultSet;
import java.sql.Statement;

public class Admin{
    private int id;
    private String username;
    private String email;
    private String password;
    private double totalAmountEarned;
    private double totalAmountSpent;
    private Statement statement;

    private static Admin instance;
    private Admin(){}
    public static Admin getInstance(){
        if (instance == null){
            instance = new Admin();
        }
        return instance;
    }

    public Admin(int id,String email, String username, String password){
        this.username = username;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public void totalAmountSpent(){

    }

    public Admin getAdmin(){
        final String SELECT_ADMIN_COLUMNS_SQL = "SELECT * FROM admin";
        try{
            ResultSet resultSet = GetResultSet.getInstance().getResultSet(SELECT_ADMIN_COLUMNS_SQL);
            while (resultSet.next()){
                this.id = resultSet.getInt("id");
                this.username = resultSet.getString("username");
                this.password = resultSet.getString("password");
                this.email = resultSet.getString("email");
                this.totalAmountSpent = resultSet.getDouble("total_spent");
                this.totalAmountEarned = resultSet.getDouble("total_earned");
            }
            return this;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void updateTotalSpent(double number){
        final String UPDATE_TOTAL_SPENT_SQL = "UPDATE admin SET total_spent = "+(this.totalAmountSpent + number)+" WHERE id = "+this.id;
        try{
            this.statement = GetStatement.getInstance().getStatement();
            this.statement.executeUpdate(UPDATE_TOTAL_SPENT_SQL);
            totalAmountSpent+=number;

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateTotalEarned(double number){
        final String UPDATE_TOTAL_EARNED_SQL = "UPDATE admin SET total_earned = "+(this.totalAmountEarned + number)+" WHERE id = "+this.id;
        try{
            this.statement = GetStatement.getInstance().getStatement();
            this.statement.executeUpdate(UPDATE_TOTAL_EARNED_SQL);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addBookQuantity(int id, int quantity){
        try{

            this.statement = GetStatement.getInstance().getStatement();
            ResultSet resultSet = GetResultSet.getInstance().getResultSet("SELECT * FROM book WHERE id ="+id);
            while (resultSet.next()){
                int nrOfBooks = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");
                double costPrice = resultSet.getDouble("cost_price");
                if (resultSet.getInt("id") == id){
                    GetStatement.getInstance().getStatement().executeUpdate("UPDATE book SET quantity = "+(nrOfBooks + quantity) + " WHERE id = "+id);
                    this.updateTotalSpent(costPrice * quantity);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getTotalAmountEarned() {
        return totalAmountEarned;
    }

    public void setTotalAmountEarned(double totalAmountEarned) {
        this.totalAmountEarned = totalAmountEarned;
    }

    public double getTotalAmountSpent() {
        return totalAmountSpent;
    }

    public void setTotalAmountSpent(double totalAmountSpent) {
        this.totalAmountSpent = totalAmountSpent;
    }
}
