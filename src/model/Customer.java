package model;

import java.sql.Timestamp;

public class Customer {

    private Integer ID;
    private String name;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private Integer divisionID;
    private String division;
    private String country;

    public Customer(Integer inputCustomerID, String inputName, String inputAddress, String inputPostalCode,
                    String inputPhoneNumber, String inputDivision, Integer inputDivID, String inputCountry) {
        ID = inputCustomerID;
        name = inputName;
        address = inputAddress;
        postalCode = inputPostalCode;
        phoneNumber = inputPhoneNumber;
        division = inputDivision;
        divisionID = inputDivID;
        country = inputCountry;

    }


    // Getters
    public Integer getCustomerID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDivision() {
        return division;
    }

    public String getCountry() { return country; }

    public Integer getDivisionID() { return divisionID; }
}
