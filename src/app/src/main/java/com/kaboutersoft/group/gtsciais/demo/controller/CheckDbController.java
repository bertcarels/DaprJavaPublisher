package com.kaboutersoft.group.gtsciais.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.kaboutersoft.group.gtsciais.demo.model.CheckDb;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


@RestController
@RequestMapping("/checkdb")
public class CheckDbController {

    private static final Logger logger = LoggerFactory.getLogger(CheckDbController.class);

   

    @Autowired
    public CheckDbController() {
    }


    @PostMapping
    public CheckDb createCheckDb(@RequestBody CheckDb checkDb) {

        logger.info("Running query on:");
        logger.info("server: " + checkDb.getServer());
        logger.info("db: " + checkDb.getDb());
        logger.info("table: " + checkDb.getTable());
        logger.info("auth: " + checkDb.getAuth());

        try {

             // Set up the Azure SQL Database DataSource
           SQLServerDataSource ds = new SQLServerDataSource();
           ds.setServerName(checkDb.getServer() +".database.windows.net");
           ds.setDatabaseName(checkDb.getDb());
           ds.setAuthentication(checkDb.getAuth());

           try (Connection connection = ds.getConnection();
               Statement stmt = connection.createStatement();
               ResultSet rs = stmt.executeQuery("SELECT SUSER_SNAME()")) {
               if (rs.next()) {
                  logger.info("You have successfully logged on as: " + rs.getString(1));
               }
           }


           // Establish the connection
           Connection connection = ds.getConnection();

           // Execute a simple query
           Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery("SELECT TOP 5 * FROM " + checkDb.getTable() );

           // Process the results
           while (resultSet.next()) {
               System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
           }

           // Close resources
           resultSet.close();
           statement.close();
           connection.close();



        } catch (Exception e) {
            e.printStackTrace();
        }

          
        return checkDb;
    }


}
