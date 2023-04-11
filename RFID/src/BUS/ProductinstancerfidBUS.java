/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DTO.Productinstancerfid;
import DAO.ProductinstancerfidDAO;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ProductinstancerfidBUS {
    public ArrayList<Productinstancerfid> Productl ;
    public ProductinstancerfidBUS(int i)
    {
        listProductinstancerfid();
    }
    
    public ProductinstancerfidBUS()
    {
        
    }
    public Productinstancerfid get(String product_instance_id)
    {
        for(Productinstancerfid p : Productl )
        {
            if(p.getProduct_instance_id().equals(product_instance_id))
            {
                return p;
            }
        }
        return null;
    }
    public void listProductinstancerfid()
    {
        ProductinstancerfidDAO productinstancerfidDAO = new ProductinstancerfidDAO();
        Productl = new ArrayList<>();
        Productl = productinstancerfidDAO.list();
    }
    public void addProductinstancerfid(Productinstancerfid a)
    {
        Productl.add(a);
        ProductinstancerfidDAO productinstancerfidDAO = new ProductinstancerfidDAO();
        productinstancerfidDAO.add(a);
    }

    public void setProductinstancerfid(Productinstancerfid s)
    {
        for(int i = 0 ; i < Productl.size() ; i++)
        {
            if(Productl.get(i).getProduct_instance_id().equals(s.getProduct_instance_id()))
            {
                Productl.set(i, s);
                ProductinstancerfidDAO productinstancerfidDAO = new ProductinstancerfidDAO();
                productinstancerfidDAO.set(s);
                return;
            }
        }
    }
    
    public boolean check(String product_instance_id)
    {
        for(Productinstancerfid p : Productl)
        {
            if(p.getProduct_instance_id().equals(product_instance_id))
            {
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<Productinstancerfid> search(String product_instance_id,String product_line_id,int is_purchased)
    {
        ArrayList<Productinstancerfid> search = new ArrayList<>();
        product_instance_id = product_instance_id.isEmpty()?product_instance_id = "": product_instance_id;
        product_line_id = product_line_id.isEmpty()?product_line_id = "": 
        product_line_id;
        
        for(Productinstancerfid p : Productl)
        {
            if( p.getProduct_instance_id().contains(product_instance_id) && 
                p.getProduct_line_id().contains(product_line_id))
                
            {
                search.add(p);
            }
        }
        return search;
    }
    public ArrayList<Productinstancerfid> getList() {
        listProductinstancerfid();
        return Productl;
    }
}