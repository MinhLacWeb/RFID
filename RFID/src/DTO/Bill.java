
package DTO;


/**
 *
 * @author Admin
 */
public class Bill {
    String  bill_id,staff_id,staff_name,customer_id,date,total;
    
    public Bill(String bill_id,String staff_id,String staff_name,String customer_id, String date, String total) {
        this.bill_id = bill_id;
        this.staff_id = staff_id;
        this.staff_name = staff_name;
        this.customer_id = customer_id;
        this.date = date;
        this.total = total;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }
    
    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }


    public String getCustomer_id() {
        return customer_id;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getBill_id() {
        return bill_id;
    }

    public String getDate() {
        return date;
    }

    public String getTotal() {
        return total;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    


}
