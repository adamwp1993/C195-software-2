package model;

import java.sql.Timestamp;

/**
 * Customer
 *
 * @author Adam Petersen
 */
public class Customer {

    private Integer ID;
    private String name;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private Integer divisionID;
    private String division;
    private String country;

    /**
     * Constructor for Customer Object
     *
     * @param inputCustomerID ID of customer
     * @param inputName name of customer
     * @param inputAddress address of customer
     * @param inputPostalCode postal code of customer
     * @param inputPhoneNumber phone number of customer
     * @param inputDivision division of customer
     * @param inputDivID Division ID of customer
     * @param inputCountry Country of customer
     */
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

    /**
     * @return customer ID
     */
    public Integer getCustomerID() {
        return ID;
    }

    /**
     * @return name of customer
     */
    public String getName() {
        return name;
    }

    /**
     * @return address of customer
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return postal code of customer
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @return phone number of customer
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @return customers division
     */
    public String getDivision() {
        return division;
    }

    /**
     * @return country of customer
     */
    public String getCountry() {
        return country;
    }

    /**
     * @return division of customer
     */
    public Integer getDivisionID() {
        return divisionID;
    }
}
