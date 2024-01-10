package sebastiao.tomas.faturantia.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="fatura")
public class Fatura{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="fat_id")
    private int id;

    @Column(name="fat_name")
    private String title;

    @Column(name="fat_store")
    private String store;

    @Column(name="fat_location")
    private String storeLocation;

    @Column(name="fat_datePurchase")
    private String datePurchase;

    @OneToOne
    @JoinColumn(name = "fat_gar_id", referencedColumnName = "gar_id")
    @JsonManagedReference("garantia-fatura")
    private Guarantee guarantee;

    @ManyToOne
    @JoinColumn(name = "fat_user_id", referencedColumnName = "user_id")
    @JsonBackReference("user-fatura")
    private User user;

    public Fatura(){
    }
   
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Guarantee getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(Guarantee guarantee) {
        this.guarantee = guarantee;
    }

    public int getFatId() {
        return id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storelocation) {
        this.storeLocation = storelocation;
    }

    public String getDatePurchase() {
        return datePurchase;
    }

    public void setDatePurchase(String datePurchase) {
        this.datePurchase = datePurchase;
    }
}

