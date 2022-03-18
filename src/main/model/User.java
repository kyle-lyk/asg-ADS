package main.model;

public abstract class User {
    protected String username;
    protected String password;
    protected String identity;

    public User(String username, String password, String identity) {
        this.username = username;
        this.password = password;
        this.identity = identity;
    }

    // Getters

    public String get_username() {
        return this.username;
    }

    public String get_password() {
        return this.password;
    }

    public String get_identity() {
        return this.identity;
    }

    // Setters

    public void set_username(String username) {
        this.username = username;
    }

    public void set_password(String password) {
        this.password = password;
    }

    public void set_identity(String identity) {
        this.identity = identity;
    }
    
}
