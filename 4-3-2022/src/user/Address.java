package user;

import connection.GetConnection;

import java.sql.PreparedStatement;

public class Address {
    private String state;
    private String city;
    private String street;
    private String postalCode;
    private int id;

    private makeAddress(Builder builder){
        this.state = builder.state;
        this.city = builder.city;
        this.street = builder.street;
        this.postalCode = builder.postalCode;
        this.id = builder.id;

    }

    public static class Builder{
        private String state;
        private String city;
        private String street;
        private String postalCode;
        private int id;

        public Builder withState(String state){
            this.state = state;
            return this;
        }
        public Builder withCity(String city){
            this.city = city;
            return this;
        }
        public Builder withStreet(String street){
            this.street = street;
            return this;
        }
        public Builder withPostalCode(String postalCode){
            this.postalCode = postalCode;
            return this;
        }
        public Builder withId(int id){
            this.id = id;
            return this;
        }

        public Address build(){
            return new Address(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
