/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Staff;
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
public class StaffDAO {
    private  MySQLConnect mySQL = new MySQLConnect();
    public StaffDAO() {
    }
    public ArrayList<Staff> list()
    {
        ArrayList<Staff> staffl = new ArrayList<>();
        try {
           
            String sql = "SELECT * FROM staff ";
            ResultSet rs = mySQL.executeQuery(sql);
            while(rs.next())
            {
                String staff_id= rs.getString("staff_id");
                String name = rs.getString("name");
                String username = rs.getString("username");
                int status = rs.getInt("status");    
                Staff b = new Staff(staff_id, name, username,status);
                staffl.add(b);
            }
            rs.close();
            mySQL.disConnect();
            
        } catch (SQLException ex) {
            Logger.getLogger(Staff.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return staffl;
    }

    public void set(Staff s) {
            MySQLConnect mySQL = new MySQLConnect();
            String sql = "UPDATE staff SET ";
            sql += "name='"+s.getName()+"', ";
            sql += "username='"+s.getUsername()+"', ";
            sql += "status='"+s.getStatus()+"', ";
            sql += " WHERE staff_id='"+s.getStaff_id()+"'";
            System.out.println(sql);
            mySQL.executeUpdate(sql);
    }

    public void add(Staff s) {
        MySQLConnect mySQL = new MySQLConnect();
         String sql = "INSERT INTO staff VALUES (";
                sql += "'"+s.getStaff_id()+"',";
                sql += "'"+s.getName()+"',";
                sql += "'"+s.getUsername()+"',";
                sql += "'"+s.getStatus()+"')";
         System.out.println(sql);
         mySQL.executeUpdate(sql);
    }
}
