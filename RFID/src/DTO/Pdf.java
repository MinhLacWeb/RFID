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
public class Pdf {
    String name;
    int count;
    double price;
    public Pdf(String name,double price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }
}
