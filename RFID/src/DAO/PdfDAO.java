/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Pdf;
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
public class PdfDAO {
    private  MySQLConnect mySQL = new MySQLConnect();
    public PdfDAO() {
    }
    public ArrayList<Pdf> list(String bill_id)
    {
        ArrayList<Pdf> Pdfl = new ArrayList<>();
        try {
           
            String sql = "SELECT productline.name,productline.price, COUNT(productline.product_line_id) AS 'Count' FROM billdetails INNER JOIN productinstancerfid ON billdetails.product_instance_id = productinstancerfid.product_instance_id INNER JOIN productline ON productinstancerfid.product_line_id = productline.product_line_id  WHERE bill_id = '" +bill_id+ "' GROUP BY productline.product_line_id";
            ResultSet rs = mySQL.executeQuery(sql);
            while(rs.next())
            {
                String name= rs.getString("name");
                float price= rs.getFloat("price");
                int count = rs.getInt("count");    
                Pdf pdf = new Pdf(name,price,count);
                Pdfl.add(pdf);
            }
            rs.close();
            mySQL.disConnect();
            
        } catch (SQLException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Pdfl;
    }
}
