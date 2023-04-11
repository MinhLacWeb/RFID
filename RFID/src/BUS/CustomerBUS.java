/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DTO.Customer;
import DAO.CustomerDAO;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class CustomerBUS {
    public ArrayList<Customer> customerl ;
    public CustomerBUS(int i)
    {
        listCustomer();
    }
    
    public CustomerBUS()
    {
        
    }
    public Customer get(String Phone)
    {
        for(Customer c : customerl )
        {
            if(c.getCustomer_id().equals(Phone))
            {
                return c;
            }
        }
        return null;
    }
    public void listCustomer()
    {
        CustomerDAO customerDAO = new CustomerDAO();
        customerl = new ArrayList<>();
        customerl = customerDAO.list();
    }
    public void addCustomer(Customer a)
    {   
        customerl.add(a);
        CustomerDAO customerDAO = new CustomerDAO();
        customerDAO.add(a);
    }
    
    public void deleteCustomer(String customer_id)
    {
        for(Customer c : customerl )
        {   
            if(c.getCustomer_id().equals(customer_id))
            {
                customerl.remove(c);
                CustomerDAO customerDAO = new CustomerDAO();
                customerDAO.delete(customer_id);
                return;
            }
        }
    }
    
    public void setCustomer(Customer s)
    {
        for(int i = 0 ; i < customerl.size() ; i++)
        {
            if(customerl.get(i).getCustomer_id().equals(s.getCustomer_id()))
            {
                customerl.set(i, s);
                CustomerDAO customerDAO = new CustomerDAO();
                customerDAO.set(s);
                return;
            }
        }
    }

    
    public boolean check(String Phone)
    {
        for(Customer c : customerl)
        {
            if(c.getCustomer_id().equals(Phone))
            {
                return true;
            }
        }
        return false;
    }
    
    
    public ArrayList<Customer> getList() {
        listCustomer();
        return customerl;
    }
}