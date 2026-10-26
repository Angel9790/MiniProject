package model;
import model.annotation.Id;

public class Customer implements Cloneable {
    private static long customerCounter = 100;

@Id
private final String customerId;
    private String name;
    private String email;
    private String mobile;
    private Address address;

    public static class Address {
        private final String line;
        private final String city;
        private final String pincode;

        public Address(String line, String city, String pincode) {
            this.line = line;
            this.city = city;
            this.pincode = pincode;
        }

        public String getLine()    { return line; }
        public String getCity()    { return city; }
        public String getPincode() { return pincode; }

        @Override
        public String toString() {
            return line + ", " + city + " - " + pincode;
        }
    }

    public Customer(String name, String email, String mobile, Address address) {
        this.customerId = generateCustomerId();
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
    }

    public Customer(String name, String email, String mobile) {
        this(name, email, mobile, null);
    }

    private static String generateCustomerId() {
        return "CUST" + (++customerCounter);
    }

    public String getCustomerId() { return customerId; }
    public String getName()       { return name; }
    public String getEmail()      { return email; }
    public String getMobile()     { return mobile; }
    public Address getAddress()   { return address; }

    @Override
    public Customer clone() {
        try {
            Customer copy = (Customer) super.clone();
            if (address != null) {      // deep copy of the address
                copy.address = new Address(address.line, address.city, address.pincode);
            }
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public String toString() {
        return customerId + " | " + name + " | " + email + " | " + mobile;
    }
}