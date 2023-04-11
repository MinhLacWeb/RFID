/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Bill;
import ConnectMysql.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class BillDAO {
    private  MySQLConnect mySQL = new MySQLConnect();
    public BillDAO() {
    }
    DecimalFormat df = new DecimalFormat("#");
    
    public ArrayList<Bill> list()
    {
        ArrayList<Bill> billl = new ArrayList<>();
        
        try {
            df.setMaximumFractionDigits(340);
            String sql = "SELECT * FROM bill ";
            ResultSet rs = mySQL.executeQuery(sql);
            while(rs.next())
            {
                String bill_id= rs.getString("bill_id");
                String staff_id = rs.getString("staff_id");
                String staff_name = rs.getString("staff_name");
                String customer_id = rs.getString("customer_id");
                String date = rs.getString("date");
                String total = rs.getString("total");    
               
                Bill b = new Bill(bill_id,staff_id,staff_name,customer_id,date,df.format(Double.parseDouble(total)));
                billl.add(b);
            }
            rs.close();
            mySQL.disConnect();
            
        } catch (SQLException ex) {
            Logger.getLogger(Bill.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return billl;
    }

    public void add(Bill b) {
        MySQLConnect mySQL = new MySQLConnect();
         String sql = "INSERT INTO bill VALUES (";
                sql += "'"+b.getBill_id()+"',";
                sql += "'"+b.getStaff_id()+"',";
                sql += "'"+b.getStaff_name()+"',";
                sql += "'"+b.getCustomer_id()+"',";
                sql += "'"+b.getDate()+"',";
                sql += "'"+b.getTotal()+"')";
         System.out.println(sql);
         mySQL.executeUpdate(sql);
    }
}
