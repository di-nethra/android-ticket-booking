package com.example.ead_assignment_mobile;

public class LoginRequest {
    private String nic;
    private String passwordhash;

    public LoginRequest(String nic, String passwordhash) {
        this.nic = nic;
        this.passwordhash = passwordhash;
    }
}
