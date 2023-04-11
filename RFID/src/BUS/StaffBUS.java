/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DTO.Staff;
import DAO.StaffDAO;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class StaffBUS {
    public ArrayList<Staff> staffl ;
    public StaffBUS(int i)
    {
        listAccount();
    }
    
    public StaffBUS()
    {
        
    }
    public Staff get(String staff_id)
    {
        for(Staff s : staffl )
        {
            if(s.getStaff_id().equals(staff_id))
            {
                return s;
            }
        }
        return null;
    }
    public void listAccount()
    {
        StaffDAO staffDAO = new StaffDAO();
        staffl = new ArrayList<>();
        staffl = staffDAO.list();
    }
    public void addStaff(Staff a)
    {
        staffl.add(a);
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.add(a);
    }

    public void setStaff(Staff s)
    {
        for(int i = 0 ; i < staffl.size() ; i++)
        {
            if(staffl.get(i).getStaff_id().equals(s.getStaff_id()))
            {
                staffl.set(i, s);
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.set(s);
                return;
            }
        }
    }
    
    public boolean check(String staff_id)
    {
        for( Staff s : staffl)
        {
            if(s.getStaff_id().equals(staff_id))
            {
                return true;
            }
        }
        return false;
    }
    
    
    public ArrayList<Staff> getList() {
        listAccount();
        return staffl;
    }
}