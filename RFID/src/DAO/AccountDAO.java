/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Account;
import ConnectMysql.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class AccountDAO {
    private  MySQLConnect mySQL = new MySQLConnect();
    public AccountDAO() {
    }
    public ArrayList<Account> list()
    {
        ArrayList<Account> accountl = new ArrayList<>();
        try {
           
            String sql = "SELECT * FROM account ";
            ResultSet rs = mySQL.executeQuery(sql);
            while(rs.next())
            {
                String username= rs.getString("username");
                String password = rs.getString("password");
                int status = rs.getInt("status");
                Account a = new Account(username, password,status);
                accountl.add(a);
            }
            rs.close();
            mySQL.disConnect();
            
        } catch (SQLException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return accountl;
    }

    public void set(Account a) {
            MySQLConnect mySQL = new MySQLConnect();
            String sql = "UPDATE account SET ";
            sql += "password='"+a.getPassword()+"', ";
            sql += "status='"+a.getStatus()+"', ";
            sql += " WHERE username='"+a.getUsername()+"'";
            System.out.println(sql);
            mySQL.executeUpdate(sql);
    }

    public void add(Account a) {
        MySQLConnect mySQL = new MySQLConnect();
         String sql = "INSERT INTO account VALUES (";
                sql += "'"+a.getUsername()+"',";
                sql += "'"+a.getPassword()+"',";
                sql += "'"+a.getStatus()+"')";
         System.out.println(sql);
         mySQL.executeUpdate(sql);
    }
}
