
package DTO;

public class Billdetails {
    String  bill_id,product_instance_id;
    
    public Billdetails(String bill_id, String product_instance_id) {
        this.bill_id = bill_id;
        this.product_instance_id = product_instance_id;
    }

    public String getBill_id() {
        return bill_id;
    }

    public String getProduct_instance_id() {
        return product_instance_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public void setProduct_instance_id(String product_instance_id) {
        this.product_instance_id = product_instance_id;
    }

}
