/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DTO.Account;
import DAO.AccountDAO;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class AccountBUS {
    public ArrayList<Account> accountl ;
    public AccountBUS(int i)
    {
        listAccount();
    }
    
    public AccountBUS()
    {
        
    }
    public Account get(String username)
    {
        for(Account a : accountl )
        {
            if(a.getUsername().equals(username))
            {
                return a;
            }
        }
        return null;
    }
    public void listAccount()
    {
        AccountDAO accountDAO = new AccountDAO();
        accountl = new ArrayList<>();
        accountl = accountDAO.list();
    }
    public void addBill(Account a)
    {
        accountl.add(a);
        AccountDAO accountDAO = new AccountDAO();
        accountDAO.add(a);
    }

    public void setBill(Account s)
    {
        for(int i = 0 ; i < accountl.size() ; i++)
        {
            if(accountl.get(i).getUsername().equals(s.getUsername()))
            {
                accountl.set(i, s);
                AccountDAO accountDAO = new AccountDAO();
                accountDAO.set(s);
                return;
            }
        }
    }
    
    public boolean check(String username)
    {
        for(Account a : accountl)
        {
            if(a.getUsername().equals(username))
            {
                return true;
            }
        }
        return false;
    }
    
    
    public ArrayList<Account> getList() {
        listAccount();
        return accountl;
    }
}