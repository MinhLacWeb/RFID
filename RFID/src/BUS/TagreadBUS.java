/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DTO.Tagread;
import DAO.TagreadDAO;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class TagreadBUS {
    public ArrayList<Tagread> tagreadl ;
    public TagreadBUS(int i)
    {
        listTagread();
    }
    
    public TagreadBUS()
    {
        
    }
    public Tagread get(String tag_read_id)
    {
        for(Tagread tr : tagreadl )
        {
            if(tr.getTag_read_id().equals(tag_read_id))
            {
                return tr;
            }
        }
        return null;
    }
    public void listTagread()
    {
        TagreadDAO tagreadDAO = new TagreadDAO();
        tagreadl = new ArrayList<>();
        tagreadl = tagreadDAO.list();
    }
    public void addTagread(Tagread a)
    {
        tagreadl.add(a);
        TagreadDAO tagreadDAO = new TagreadDAO();
        tagreadDAO.add(a);
    }

    public void setTagread(Tagread s)
    {
        for(int i = 0 ; i < tagreadl.size() ; i++)
        {
            if(tagreadl.get(i).getTag_read_id().equals(s.getTag_read_id()))
            {
                tagreadl.set(i, s);
                TagreadDAO tagreadDAO = new TagreadDAO();
                tagreadDAO.set(s);
                return;
            }
        }
    }
    
    public boolean check(String tag_read_id)
    {
        for(Tagread tr : tagreadl)
        {
            if(tr.getTag_read_id().equals(tag_read_id))
            {
                return true;
            }
        }
        return false;
    }
    
    
    public ArrayList<Tagread> getList() {
        listTagread();
        return tagreadl;
    }
}