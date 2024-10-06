package sg.nus.edu.shopping.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(generator = "customer-id-generator")
    @GenericGenerator(name = "customer-id-generator", strategy = "sg.nus.edu.shopping.generator.CustomerIDGenerator")
    private String userId;

    private String address;

    @Column(unique = true)
    private String userName;

    private String phoneNumber;
    
    @Pattern(regexp = "^[a-zA-Z0-9]{9,20}$", message = "Password must contain at least 9 characters at most 20 characters, including uppercase, lowercase letters, and numbers.")
    private String password;

    private String email;

    private String name;

    private String gender;

    private LocalDate birthDate;

    @OneToMany(mappedBy = "record_customer")
    private List<PurchaseRecord> purchaseRecord;

    @OneToMany(mappedBy = "cart_customer")
    private List<ShoppingCart> shoppingCart;

    @OneToMany(mappedBy = "payment_customer")
    private List<PaymentDetail> paymentDetails;


    public Customer() {
        super();
    }

    public Customer(String address, String userName,
                    String phoneNumber, String password,
                    String email, String name,
                    String gender) {
        this.address = address;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;
        this.name = name;
        this.gender = gender;
    }

    public String getUserId() {
        return userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNum) {
        this.phoneNumber = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public List<ShoppingCart> getShoppingCart() {
        return shoppingCart;
    }


    public void setShoppingCart(List<ShoppingCart> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }


    public List<PurchaseRecord> getPurchaseRecord() {
        return purchaseRecord;
    }


    public void setPurchaseRecord(List<PurchaseRecord> purchaseRecord) {
        this.purchaseRecord = purchaseRecord;
    }


    public List<PaymentDetail> getPaymentDetails() {
        return paymentDetails;
    }


    public void setPaymentDetails(List<PaymentDetail> paymentDetail) {
        this.paymentDetails = paymentDetail;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
