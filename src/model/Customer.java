package model;

import java.sql.Timestamp;

public class Customer {

    private Integer ID;
    private String name;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    private String division;
    private String country;

    public Customer(Integer inputCustomerID, String inputName, String inputAddress, String inputPostalCode,
                    String inputPhoneNumber, Timestamp inputCreateDate, String inputCreatedBy,
                    Timestamp inputLastUpdate, String inputLastUpdateBy, String inputDivision, String inputCountry) {
        ID = inputCustomerID;
        name = inputName;
        address = inputAddress;
        postalCode = inputPostalCode;
        phoneNumber = inputPhoneNumber;
        createDate = inputCreateDate;
        createdBy = inputCreatedBy;
        lastUpdate = inputLastUpdate;
        lastUpdateBy = inputLastUpdateBy;
        division = inputDivision;
        country = inputCountry;
    }


    // Getters
    public int getCustomerID() {
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

    public Timestamp getCreateDate() {
        return createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public String getDivision() {
        return division;
    }

    public String getCountry() { return country; }
}
