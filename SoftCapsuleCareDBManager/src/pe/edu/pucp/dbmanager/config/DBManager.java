/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.dbmanager.config;

import java.sql.DriverManager;
import java.sql.Connection;

public class DBManager {
    
    private Connection con;
    private static DBManager dbManager;
    private final String db = "DBTest";
    private final String url = "jdbc:mysql://pruebaprog3.c7qeiwgs42xd.us-east-1.rds.amazonaws.com:3306/DBTest";
    private final String username = "admin";
    private final String password = "pucpprogra3#";
    
    public Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);     
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return con;
    }
    
    public static DBManager getInstance(){
        if(dbManager == null){
            createInstance();
        }
        return dbManager;
    }
    
    private synchronized static void createInstance(){
        if(dbManager == null){
            dbManager = new DBManager();
        }
    }
}
