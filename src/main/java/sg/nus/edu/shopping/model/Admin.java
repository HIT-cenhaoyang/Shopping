package sg.nus.edu.shopping.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
//Author: Cen Haoyang
public class Admin {
    @Id
    @Column(unique = true)
    @GeneratedValue(generator = "admin-id-generator")
    @GenericGenerator(name = "admin-id-generator", strategy = "sg.nus.edu.shopping.generator.AdminIDGenerator")
    private String id;

    private String userName;

    private String password;

    private String email;

    public Admin() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
