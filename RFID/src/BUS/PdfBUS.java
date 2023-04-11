/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DTO.Pdf;
import DAO.PdfDAO;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class PdfBUS {
    public ArrayList<Pdf> Pdfl ;
    public PdfBUS(int i,String bill_id)
    {
        listPdf(bill_id);
    }
    
    public PdfBUS()
    {
        
    }
    public void listPdf(String bill_id)
    {
        PdfDAO pdfDAO = new PdfDAO();
        Pdfl = new ArrayList<>();
        Pdfl = pdfDAO.list(bill_id);
    }  
    
    public ArrayList<Pdf> getList(String bill_id) {
        listPdf(bill_id);
        return Pdfl;
    }
}