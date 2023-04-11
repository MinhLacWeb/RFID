/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DTO.Productline;
import DAO.ProductlineDAO;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ProductlineBUS {
    public ArrayList<Productline> Productlinel ;
    public ProductlineBUS(int i)
    {
        listProductline();
    }
    
    public ProductlineBUS()
    {
        
    }
    public Productline get(String product_line_id)
    {
        for(Productline p : Productlinel )
        {
            if(p.getProduct_line_id().equals(product_line_id))
            {
                return p;
            }
        }
        return null;
    }
    public void listProductline()
    {
        ProductlineDAO productlineDAO = new ProductlineDAO();
        Productlinel = new ArrayList<>();
        Productlinel = productlineDAO.list();
    }
    public void addProductline(Productline a)
    {
        Productlinel.add(a);
        ProductlineDAO productlineDAO = new ProductlineDAO();
        productlineDAO.add(a);
    }

    public void setProductline(Productline s)
    {
        for(int i = 0 ; i < Productlinel.size() ; i++)
        {
            if(Productlinel.get(i).getProduct_line_id().equals(s.getProduct_line_id()))
            {
                Productlinel.set(i, s);
                ProductlineDAO productlineDAO = new ProductlineDAO();
                productlineDAO.set(s);
                return;
            }
        }
    }
    
    public boolean check(String product_line_id)
    {
        for(Productline p : Productlinel)
        {
            if(p.getProduct_line_id().equals(product_line_id))
            {
                return true;
            }
        }
        return false;
    }
    
    
    public ArrayList<Productline> getList() {
        listProductline();
        return Productlinel;
    }
}