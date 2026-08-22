package com.sunrisedental.model;

/**
 * Represents a user who can access the Sunrise Dental Clinic system.
 */
public class User {

    private final int userId;
    private final String username;
    private final String passwordHash;
    private final String role;

    public User(
            final int userId,
            final String username,
            final String passwordHash,
            final String role) {

        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getRole() {
        return role;
    }
}