package com.example.ead_assignment_mobile;

public class RegistrationData {

    private Object id;
    private String nic;
    private String passwordHash;
    private String firstName;
    private String lastName;

    private String role;

    private String accountStatus;


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public RegistrationData(Object id,String nic, String passwordHash, String firstName, String lastName,String role,String accountStatus) {
        this.id = id;
        this.nic = nic;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.accountStatus = accountStatus;
    }

    public String getNicNumber() {
        return nic;
    }

    public void setNicNumber(String nicNumber) {
        this.nic = nicNumber;
    }

    public String getPassword() {
        return passwordHash;
    }

    public void setPassword(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}