/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Productline;
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
public class ProductlineDAO {
    private  MySQLConnect mySQL = new MySQLConnect();
    public ProductlineDAO() {
    }
    public ArrayList<Productline> list()
    {
        ArrayList<Productline> productLinel = new ArrayList<>();
        try {
           
            String sql = "SELECT * FROM productline ";
            ResultSet rs = mySQL.executeQuery(sql);
            while(rs.next())
            {
                String product_line_id= rs.getString("product_line_id");
                String name = rs.getString("name");
                int price = rs.getInt("price");    
                int stock = rs.getInt("stock");
                Productline p = new Productline(product_line_id, name, price,stock);
                productLinel.add(p);
            }
            rs.close();
            mySQL.disConnect();
            
        } catch (SQLException ex) {
            Logger.getLogger(Productline.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return productLinel;
    }

    public void set(Productline p) {
            MySQLConnect mySQL = new MySQLConnect();
            String sql = "UPDATE productline SET ";
            sql += "name='"+p.getName()+"', ";
            sql += "price='"+p.getPrice()+"', ";
            sql += "stock='"+p.getStock()+"'";
            sql += " WHERE product_line_id='"+p.getProduct_line_id()+"'";
            System.out.println(sql);
            mySQL.executeUpdate(sql);
    }

    public void add(Productline p) {
        MySQLConnect mySQL = new MySQLConnect();
         String sql = "INSERT INTO productline VALUES (";
                sql += "'"+p.getProduct_line_id()+"',";
                sql += "'"+p.getName()+"',";
                sql += "'"+p.getPrice()+"',";
                sql += "'"+p.getStock()+"')";
         System.out.println(sql);
         mySQL.executeUpdate(sql);
    }
}
