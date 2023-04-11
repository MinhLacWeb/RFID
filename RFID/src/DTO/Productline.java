
package DTO;


/**
 *
 * @author Admin
 */
public class Productline {
    String  product_line_id,name;
    float price;
    int stock;
    public Productline(String product_line_id, String name, float price,int stock) {
        this.product_line_id = product_line_id;
        this.name = name;
        this.price = price;
        this.stock = stock;


    }

    public void setProduct_line_id(String product_line_id) {
        this.product_line_id = product_line_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getProduct_line_id() {
        return product_line_id;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public float getPrice() {
        return price;
    }



}
