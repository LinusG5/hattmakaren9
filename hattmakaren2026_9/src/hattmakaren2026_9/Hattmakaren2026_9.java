/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hattmakaren2026_9;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException;

/**
 *
 * @author Linus
 */
public class Hattmakaren2026_9 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("TEST");
        System.out.println("TEST2");
        
        // Här skriver vi koden som ansluter till MySQL
        String url = "jdbc:mysql://localhost:3306/hattmakaren";
        String anvandare = "hattAdmin26";
        String lösenord = "hattAdmin26PW";
 
        try {
            // Här försöker vi öppna dörren till databasen
            Connection minKoppling = DriverManager.getConnection(url, anvandare, lösenord);
            System.out.println("Snyggt! Anslutningen till hattmakaren lyckades.");
        } catch (SQLException e) {
            // Om något går fel skrivs felet ut här
            System.out.println("Hoppsan, något gick fel: " + e.getMessage());
        }
    }
}
   
    
    
    

