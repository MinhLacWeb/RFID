
package DTO;


/**
 *
 * @author Admin
 */
public class Tagread {
    String  tag_read_id,product_instance_id,time;

    
    public Tagread(String tag_read_id, String product_instance_id, String date) {
        this.tag_read_id = tag_read_id;
        this.product_instance_id = product_instance_id;
        this.time = date;
    }

    public String getTag_read_id() {
        return tag_read_id;
    }

    public String getProduct_instance_id() {
        return product_instance_id;
    }

    public String getDate() {
        return time;
    }

    public void setTag_read_id(String tag_read_id) {
        this.tag_read_id = tag_read_id;
    }

    public void setProduct_instance_id(String product_instance_id) {
        this.product_instance_id = product_instance_id;
    }

    public void setDate(String date) {
        this.time = date;
    }

  

}
