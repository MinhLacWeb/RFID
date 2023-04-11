/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Billdetails;
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
public class BilldetailsDAO {
    private  MySQLConnect mySQL = new MySQLConnect();
    public BilldetailsDAO() {
    }
    public ArrayList<Billdetails> list()
    {
        ArrayList<Billdetails> billdetailsl = new ArrayList<>();
        try {
           
            String sql = "SELECT * FROM billdetails ";
            ResultSet rs = mySQL.executeQuery(sql);
            while(rs.next())
            {
                String bill_id= rs.getString("bill_id");
                String product_instance_id = rs.getString("product_instance_id");    
                Billdetails bd = new Billdetails(bill_id, product_instance_id);
                billdetailsl.add(bd);
            }
            rs.close();
            mySQL.disConnect();
            
        } catch (SQLException ex) {
            Logger.getLogger(Billdetails.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return billdetailsl;
    }

    public void set(Billdetails bd) {
            MySQLConnect mySQL = new MySQLConnect();
            String sql = "UPDATE billdetails SET ";
            sql += "product_instance_id='"+bd.getProduct_instance_id()+"', ";
            sql += " WHERE bill_id='"+bd.getBill_id()+"'";
            System.out.println(sql);
            mySQL.executeUpdate(sql);
    }

    public void add(Billdetails bd) {
        MySQLConnect mySQL = new MySQLConnect();
         String sql = "INSERT INTO billdetails VALUES (";
                sql += "'"+bd.getBill_id()+"',";
                sql += "'"+bd.getProduct_instance_id()+"')";
         System.out.println(sql);
         mySQL.executeUpdate(sql);
    }
}
