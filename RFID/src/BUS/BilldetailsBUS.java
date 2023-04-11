/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DTO.Billdetails;
import DAO.BilldetailsDAO;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class BilldetailsBUS {
    public ArrayList<Billdetails> billdetailsl ;
    public BilldetailsBUS(int i)
    {
        listBilldetails();
    }
    
    public BilldetailsBUS()
    {
        
    }
    public Billdetails get(String bill_id)
    {
        for(Billdetails bd : billdetailsl )
        {
            if(bd.getBill_id().equals(bill_id))
            {
                return bd;
            }
        }
        return null;
    }
    public void listBilldetails()
    {
        BilldetailsDAO billdetailsDAO = new BilldetailsDAO();
        billdetailsl = new ArrayList<>();
        billdetailsl = billdetailsDAO.list();
    }
    public void addBilldetails(Billdetails a)
    {
        billdetailsl.add(a);
        BilldetailsDAO billdetailsDAO = new BilldetailsDAO();
        billdetailsDAO.add(a);
    }

    public void setBilldetails(Billdetails s)
    {
        for(int i = 0 ; i < billdetailsl.size() ; i++)
        {
            if(billdetailsl.get(i).getBill_id().equals(s.getBill_id()))
            {
                billdetailsl.set(i, s);
                BilldetailsDAO billdetailsDAO = new BilldetailsDAO();
                billdetailsDAO.set(s);
                return;
            }
        }
    }
    
    public boolean check(String bill_id)
    {
        for(Billdetails bd : billdetailsl)
        {
            if(bd.getBill_id().equals(bill_id))
            {
                return true;
            }
        }
        return false;
    }
    
    
    public ArrayList<Billdetails> getList() {
        listBilldetails();
        return billdetailsl;
    }
}