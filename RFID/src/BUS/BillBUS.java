/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DTO.Bill;
import DAO.BillDAO;
import java.util.ArrayList;
import static jdk.nashorn.internal.objects.NativeString.toUpperCase;

/**
 *
 * @author Admin
 */
public class BillBUS {
    public ArrayList<Bill> billl ;
    public BillBUS(int i)
    {
        listBill();
    }
    
    public BillBUS()
    {
        
    }
    public Bill get(String bill_id)
    {
        for(Bill b : billl )
        {
            if(b.getBill_id().equals(bill_id))
            {
                return b;
            }
        }
        return null;
    }
    public void listBill()
    {
        BillDAO billDAO = new BillDAO();
        billl = new ArrayList<>();
        billl = billDAO.list();
    }
    public void addBill(Bill a)
    {
        billl.add(a);
        BillDAO billDAO = new BillDAO();
        billDAO.add(a);
    }

    
    public boolean check(String bill_id)
    {
        for(Bill b : billl)
        {
            if(b.getBill_id().equals(bill_id))
            {
                return true;
            }
        }
        return false;
    }
    
    
    public ArrayList<Bill> getList() {
        listBill();
        return billl;
    }
    
    public ArrayList<Bill> search(String bill_id,String staff_name,String customer_id,String date,String total)
    {
        ArrayList<Bill> search = new ArrayList<>();
        bill_id = bill_id.isEmpty()?bill_id = "": bill_id;
        staff_name = staff_name.isEmpty()?staff_name = "": staff_name;
        customer_id = customer_id.isEmpty()?customer_id = "": customer_id;
        date = date.isEmpty()?date = "": date;
        total = total.isEmpty()?total = "": total;
        for(Bill b : billl)
        {
            if( toUpperCase(b.getBill_id()).contains(bill_id) && 
                toUpperCase(b.getStaff_name()).contains(staff_name) &&
                toUpperCase(b.getCustomer_id()).contains(customer_id) && 
                toUpperCase(b.getDate()).contains(date) &&
                toUpperCase(b.getTotal()).contains(total))
            {
                search.add(b);
            }
        }
        return search;
    }
}