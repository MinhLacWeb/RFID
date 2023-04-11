/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author LacKhaiMinh
 */
public class Staff {
    String staff_id,name,username;
    int status;

    public Staff(String staff_id, String name, String username, int status) {
        this.staff_id = staff_id;
        this.name = name;
        this.username = username;
        this.status = status;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public int getStatus() {
        return status;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
