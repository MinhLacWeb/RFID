/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Tagread;
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
public class TagreadDAO {
    private  MySQLConnect mySQL = new MySQLConnect();
    public TagreadDAO() {
    }
    public ArrayList<Tagread> list()
    {
        ArrayList<Tagread> tagreadl = new ArrayList<>();
        try {
           
            String sql = "SELECT * FROM tagread ";
            ResultSet rs = mySQL.executeQuery(sql);
            while(rs.next())
            {
                String tag_read_id= rs.getString("tag_read_id");
                String product_instance_id = rs.getString("product_instance_id");    
                String date = rs.getString("time");
                Tagread tr = new Tagread(tag_read_id, product_instance_id,date);
                tagreadl.add(tr);
            }
            rs.close();
            mySQL.disConnect();
            
        } catch (SQLException ex) {
            Logger.getLogger(Tagread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tagreadl;
    }

    public void set(Tagread tr) {
            MySQLConnect mySQL = new MySQLConnect();
            String sql = "UPDATE tagread SET ";            
            sql += "product_instance_id='"+tr.getProduct_instance_id()+"', ";
            sql += "time='"+tr.getDate()+"', ";
            sql += " WHERE tag_read_id='"+tr.getTag_read_id()+"'";
            System.out.println(sql);
            mySQL.executeUpdate(sql);
    }

    public void add(Tagread tr) {
        MySQLConnect mySQL = new MySQLConnect();
         String sql = "INSERT INTO tagread VALUES (";
                sql += "'"+tr.getTag_read_id()+"',";
                sql += "'"+tr.getProduct_instance_id()+"', ";
                sql += "'"+tr.getDate()+"')";
         System.out.println(sql);
         mySQL.executeUpdate(sql);
    }
}
