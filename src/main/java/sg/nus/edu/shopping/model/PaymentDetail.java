package sg.nus.edu.shopping.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity
public class PaymentDetail {

    @Id
    private String cardNumber;

    private String holderName;

    private String expiryDate;

    private String bankName;

    @ManyToOne @JsonIgnore
    private Customer payment_customer;


    public PaymentDetail() {

    }

    public PaymentDetail(String cardNumber, String expiryDate, String bankName) {
        this.setCardNumber(cardNumber);
        this.setExpiryDate(expiryDate);
        this.setBankName(bankName);
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNum) {
        this.cardNumber = cardNum;
    }

    public Customer getPayment_customer() {
        return payment_customer;
    }

    public void setPayment_customer(Customer payment_customer) {
        this.payment_customer = payment_customer;
    }
}
