package main.model;

/**
 * User is an abstract class that cannot be used to create objects.
 * To access it, it must be inherited from another class. (Eg. Donor, Ngo)
 * User class contains username, password, identity of an user.
 */
public abstract class User {
    protected String username;
    protected String password;
    protected String identity;

    /**
     * Constructs a user with the username, password, and identity.
     * @param username user's username
     * @param password user's password
     * @param identity user's identity
     */
    public User(String username, String password, String identity) {
        this.username = username;
        this.password = password;
        this.identity = identity;
    }

    // Getters

    /**
     * Get the user's username.
     * @return user's username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Get the user's password.
     * @return user's password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Get the user's identity.
     * @return user's identity
     */
    public String getIdentity() {
        return this.identity;
    }

    // Setters

    /**
     * Set the user's username.
     * @param username user's username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set the user's password.
     * @param password user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set the user's identity.
     * @param identity user's identity
     */
    public void setIdentity(String identity) {
        this.identity = identity;
    }
    
}
