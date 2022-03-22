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

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getIdentity() {
        return this.identity;
    }

    // Setters

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
    
}
