package sg.nus.edu.shopping.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(generator = "customer-id-generator")
    @GenericGenerator(name = "customer-id-generator", strategy = "sg.nus.edu.shopping.generator.CustomerIDGenerator")
    private String id;

    private String address;

    @Column(unique = true)
    private String userName;

    private String phoneNumber;
    
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\\\\\\\-\\\\\\\\[\\\\\\\\]{};':\\\\\\\"\\\\\\\\\\\\\\\\|,.<>\\\\\\\\/?]).{6,10}$", message = "Password must contain at least 6 characters at most 10 characters, including uppercase, lowercase letters, special character and numbers.")
    private String password;

    private String email;

    private String name;

    private String gender;

    private LocalDate birthDate;

    @OneToMany(mappedBy = "customer") @JsonIgnore
    private List<PurchaseRecord> purchaseRecords;

    @OneToMany(mappedBy = "customer") @JsonIgnore
    private List<ShoppingCart> shoppingCarts;

    @OneToMany(mappedBy = "payment_customer" , cascade = CascadeType.ALL) 
    @JsonIgnore
    private List<PaymentDetail> paymentDetails;

    @OneToMany(mappedBy = "customer") @JsonIgnore
    private List<Review> reviews;


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

    public String getCustomerId() {
        return id;
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


    @JsonIgnore
    public List<ShoppingCart> getShoppingCart() {
        return shoppingCarts;
    }


    public void setShoppingCart(List<ShoppingCart> shoppingCart) {
        this.shoppingCarts = shoppingCart;
    }

    public void removeProductFromCart(ShoppingCart cart) {
        shoppingCarts.remove(cart);
        cart.setCustomer(null);
    }

    public void addProductToCart(ShoppingCart cart) {
        shoppingCarts.add(cart);
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

    public void setCustomerId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<PurchaseRecord> getPurchaseRecords() {
        return purchaseRecords;
    }

    public void setPurchaseRecords(List<PurchaseRecord> purchaseRecords) {
        this.purchaseRecords = purchaseRecords;
    }

    public List<ShoppingCart> getShoppingCarts() {
        return shoppingCarts;
    }

    public void setShoppingCarts(List<ShoppingCart> shoppingCarts) {
        this.shoppingCarts = shoppingCarts;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
