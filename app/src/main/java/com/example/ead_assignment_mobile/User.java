package com.example.ead_assignment_mobile;

public class User {
    private String nic;
    private String firstName;
    private String lastName;
    private String role;
    private String accountStatus;

    public User(String nic, String firstName, String lastName, String role, String accountStatus) {
        this.nic = nic;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.accountStatus = accountStatus;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
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
}
