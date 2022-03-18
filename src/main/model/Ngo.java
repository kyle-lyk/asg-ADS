package main.model;

import java.util.List;

public class Ngo extends User{
        
    private String ngoUsername;
    private String ngoPassword;

    public Ngo(String ngoUsername, String ngoPassword) {
        super(ngoUsername, ngoPassword, "NGO");
        this.ngoUsername = ngoUsername;
        this.ngoPassword = ngoPassword;
    }

    public static Ngo createNgo(String ngoUsername, String ngoPassword) {
        Database.writeData("ngo_acc", List.of(ngoUsername, ngoPassword));
        return new Ngo(ngoUsername, ngoPassword);
    }
        
}
