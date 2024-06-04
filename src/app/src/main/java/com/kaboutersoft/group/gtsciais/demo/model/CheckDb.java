package com.kaboutersoft.group.gtsciais.demo.model;
 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id; 


@Entity
public class CheckDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String server;
    private String db;
    private String table;
    private String auth;
    
   
    // Getters and setters
    // Constructors
    // Other methods as needed

    // Example of constructors and getters/setters
    public CheckDb() {}

    public CheckDb(String server, String db, String table, String auth) {
        this.server = server;
        this.db = db;
        this.table = table;
        this.auth = auth;
    }

    public String getServer() {
        return this.server;
    }

    public String getDb() {
        return this.db;
    }

    public String getTable() {
        return this.table;
    }

    public String getAuth() {
        return this.auth;
    }

   
}
