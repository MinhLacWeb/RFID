
package DTO;


/**
 *
 * @author Admin
 */
public class Account {
    String  username,password;
    int status;

    public Account(String username, String password,int status) {
        this.username = username;
        this.password = password;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    public int getStatus() {
        return status;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
    
}