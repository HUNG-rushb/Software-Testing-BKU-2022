package book;

import connection.GetConnection;
import connection.GetResultSet;

import java.lang.ref.PhantomReference;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Author {
    private String name;
    private String surname;
    private String birthday;
    private String nationality;
    private String Sex;
    private int id;

    private Author(Builder builder){
        this.name = builder.name;
        this.surname = builder.surname;
        this.birthday = builder.birthday;
        this.nationality = builder.nationality;
        this.Sex = builder.Sex;
        this.id = builder.id;

        try{
            final  String INSERT_AUTHOR_SQL = "INSERT INTO author(id, first_name, last_name, nationality, sex, birth_day)\n"
                    + "	VALUES (?,?,?,?,?,?);";
            ResultSet resultSet = GetResultSet.getInstance().getResultSet("SELECT * FROM author");
            int size = 0;
            while (resultSet.next()){
                size++;
            }
            PreparedStatement preparedStatement = GetConnection.getConnection().prepareStatement(INSERT_AUTHOR_SQL);
            preparedStatement.setInt(1,(size + 1));
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,surname);
            preparedStatement.setString(4,nationality);
            preparedStatement.setString(5,Sex);
            preparedStatement.setString(6,birthday);
            preparedStatement.executeUpdate();
            this.id = (size+1);


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static class Builder{
        private String name;
        private String surname;
        private String birthday;
        private String nationality;
        private String Sex;
        private int id;

        public Builder withName(String name){
            this.name = name;
            return this;
        }

        public Builder withSurname(String surname){
            this.surname = surname;
            return this;
        }

        public Builder withBirthday(String birthday){
            this.birthday = birthday;
            return this;
        }

        public Builder withNationality(String nationality){
            this.nationality = nationality;
            return this;
        }

        public Builder withSex(String Sex){
            this.Sex = Sex;
            return this;
        }

        public Author build(){
            return new Author(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setSex(String Sex) {
        this.Sex = Sex;
    }

    public void setId(int id) {
        this.id = id;
    }
}
