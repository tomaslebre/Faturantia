package pt.iade.sebastiaorusu.myapplication.models;

import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class FatItem implements Serializable {
    private int id;
    private Button Image;
    private Button PDF;
    private String title;
    private String store;
    private String storeLocation;
    private String datePurchase;
    private Calendar dateofpurchaseCalendar;

    public FatItem() {
        this(0, "", "", "", Calendar.getInstance());
    }

    public FatItem(int id, String title, String store, String storeLocation, Calendar dateofpurchaseCalendar) {
        this.id = id;
        this.title = title;
        this.store = store;
        this.storeLocation = storeLocation;
        this.dateofpurchaseCalendar = dateofpurchaseCalendar;

    }

    public static ArrayList<FatItem> List(){
        ArrayList<FatItem> items = new ArrayList<FatItem>();
        items.add(new FatItem(1, "Item 1", "Store 1", "Store Location 1", Calendar.getInstance()));
        items.add(new FatItem(2, "Item 2", "Store 2", "Store Location 2", Calendar.getInstance()));

        return items;
    }


    public void save() {
        // TODO: Send the object's data to our web server and update the database there.

        if (id == 0) {
            // This is a brand new object and must be a INSERT in the database.

            // Simulate doing an insert and getting an ID back from the web server.
            id = new Random().nextInt(1000) + 1;
        } else {
            // This is an update to an existing object and must use UPDATE in the database.
        }
    }


    public int getId() {
        return id;
    }

    public Button getImage() {
        return Image;
    }

    public void setImage(Button image) {
        Image = image;
    }

    public Button getPDF() {
        return PDF;
    }

    public void setPDF(Button PDF) {
        this.PDF = PDF;
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


    public Calendar getDateofpurchaseCalendar() {
        return dateofpurchaseCalendar;
    }

    public void setDateofpurchaseCalendar(Calendar dateofpurchaseCalendar) {
        this.dateofpurchaseCalendar = dateofpurchaseCalendar;
    }

    public String getDatePurchase() {
        return datePurchase;
    }

    public void setDatePurchase(String datePurchase) {
        this.datePurchase = datePurchase;
    }
}
