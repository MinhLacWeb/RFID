/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Customer;
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
public class CustomerDAO {
    private  MySQLConnect mySQL = new MySQLConnect();
    public CustomerDAO() {
    }
    public ArrayList<Customer> list()
    {
        ArrayList<Customer> customerl = new ArrayList<>();
        try {
           
            String sql = "SELECT * FROM customer ";
            ResultSet rs = mySQL.executeQuery(sql);
            while(rs.next())
            {
                String customer_id= rs.getString("customer_id");
                String name = rs.getString("name");    
                Customer c = new Customer(customer_id, name);
                customerl.add(c);
            }
            rs.close();
            mySQL.disConnect();
            
        } catch (SQLException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return customerl;
    }

    public void add(Customer c) {
        MySQLConnect mySQL = new MySQLConnect();
         String sql = "INSERT INTO customer VALUES (";
                sql += "'"+c.getCustomer_id()+"',";
                sql += "'"+c.getName()+"')";
         System.out.println(sql);
         mySQL.executeUpdate(sql);
    }
    
    public void delete(String customer_id) {
        System.out.println(customer_id);
        MySQLConnect mySQL = new MySQLConnect();
        String sql = "DELETE FROM customer WHERE customer_id = '"+customer_id+"' ";
        System.out.println(sql);
        mySQL.executeUpdate(sql);
    }
    
    public void set(Customer c) {
        MySQLConnect mySQL = new MySQLConnect();
        String sql = "UPDATE customer SET ";
        sql += "customer_id='"+c.getCustomer_id()+"', ";
        sql += "name='"+c.getName()+"'";
        sql += " WHERE customer_id='"+c.getCustomer_id()+"'";
        System.out.println(sql);
        mySQL.executeUpdate(sql);
    }

}
