package model;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

/**
 * Appointment class used to represent one appointment in the DB.
 *
 * @author Adam Petersen
 */
public class Appointment {

    private Integer appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp startDateTime;
    private Timestamp endDateTime;
    private Timestamp createDate;
    private String createBy;
    private Timestamp lastUpdateDateTime;
    private String lastUpdateBy;
    private Integer customerID;
    private Integer userID;
    private Integer contactID;
    private String contactName;

    /**
     * Appointment constructor
     *
     * @param inputAppointmentID appointment ID (Primary Key).
     * @param inputContactID  contact ID (Foregin Key).
     * @param inputContactName Name of contact.
     * @param inputCreateBy Name of user who created the appointment in the DB.
     * @param inputCreateDate Date appointment was created.
     * @param inputCustomerID customer ID (Foreign Key).
     * @param inputDescription appointment description.
     * @param inputEndDateTime end date/time of appointment.
     * @param inputLastUpdateBy Last person to update the appointment.
     * @param inputLastUpdateDateTime Time/Date of last update.
     * @param inputLocation Appointment Location.
     * @param inputStartDateTime Start Date/time of app
     * @param inputTitle Appointment title
     * @param inputType Appointment Type
     * @param inputUserID User ID(Foreign Key).
     *
     */
    public Appointment(Integer inputAppointmentID, String inputTitle, String inputDescription, String inputLocation,
                       String inputType, Timestamp inputStartDateTime, Timestamp inputEndDateTime,
                       Timestamp inputCreateDate, String inputCreateBy, Timestamp inputLastUpdateDateTime,
                       String inputLastUpdateBy, Integer inputCustomerID, Integer inputUserID, Integer inputContactID,
                       String inputContactName) {

        appointmentID = inputAppointmentID;
        title = inputTitle;
        description = inputDescription;
        location = inputLocation;
        type = inputType;
        startDateTime = inputStartDateTime;
        endDateTime = inputEndDateTime;
        createDate = inputCreateDate;
        createBy = inputCreateBy;
        lastUpdateDateTime = inputLastUpdateDateTime;
        lastUpdateBy = inputLastUpdateBy;
        customerID = inputCustomerID;
        userID = inputUserID;
        contactID = inputContactID;
        contactName = inputContactName;

    }

    //
    /**
     * @return ID of the appointment
     */
    public Integer getAppointmentID() {
        return appointmentID;
    }

    /**
     * @return title of the appointment
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return description of appointment
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return location of appointment
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return type of the appointment
     */
    public String getType() {
        return  type;
    }

    /**
     * @return start datetime of appointment
     */
    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    /**
     * @return end datetime of appointment
     */
    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    /**
     * @return create date of appointment
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * @return who created appointment
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * @return last update of appointment
     */
    public Timestamp getLastUpdateDateTime() {
        return lastUpdateDateTime;
    }

    /**
     * @return datetime of last update
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     * @return customer ID
     */
    public Integer getCustomerID() {
        return customerID;
    }

    /**
     * @return user ID
     */
    public Integer getUserID() {
        return userID;
    }

    /**
     * @return contact ID
     */
    public Integer getContactID() {
        return contactID;
    }

    /**
     * @return name of contact
     */
    public String getContactName() {
        return contactName;
    }
}
