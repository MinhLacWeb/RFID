/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Productinstancerfid;
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
public class ProductinstancerfidDAO {
    private  MySQLConnect mySQL = new MySQLConnect();
    public ProductinstancerfidDAO() {
    }
    public ArrayList<Productinstancerfid> list()
    {
        ArrayList<Productinstancerfid> productl = new ArrayList<>();
        try {
           
            String sql = "SELECT * FROM productinstancerfid ";
            ResultSet rs = mySQL.executeQuery(sql);
            while(rs.next())
            {
                String product_instance_id= rs.getString("product_instance_id");
                String product_line_id = rs.getString("product_line_id");
                int is_purchased = rs.getInt("is_purchased");            
                Productinstancerfid p = new Productinstancerfid(product_instance_id, product_line_id, is_purchased);
                productl.add(p);
            }
            rs.close();
            mySQL.disConnect();
            
        } catch (SQLException ex) {
            Logger.getLogger(Productinstancerfid.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return productl;
    }

    public void set(Productinstancerfid p) {
            MySQLConnect mySQL = new MySQLConnect();
            String sql = "UPDATE productinstancerfid SET ";
            sql += "product_line_id='"+p.getProduct_line_id()+"', ";
            sql += "is_purchased='"+p.getIs_purchased()+"' ";
            sql += " WHERE product_instance_id='"+p.getProduct_instance_id()+"'";
            System.out.println(sql);
            mySQL.executeUpdate(sql);
    }

    public void add(Productinstancerfid p) {
        MySQLConnect mySQL = new MySQLConnect();
         String sql = "INSERT INTO productinstancerfid VALUES (";
                sql += "'"+p.getProduct_instance_id()+"',";
                sql += "'"+p.getProduct_line_id()+"',";
                sql += "'0')";
         System.out.println(sql);
         mySQL.executeUpdate(sql);
    }
}
