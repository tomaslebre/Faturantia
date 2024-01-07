package pt.iade.sebastiaorusu.myapplication.models;

import android.os.Looper;
import android.util.Log;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import android.os.Handler;

import pt.iade.sebastiaorusu.myapplication.utilities.CalendarJsonAdapter;
import pt.iade.sebastiaorusu.myapplication.utilities.WebRequest;

public class FatItem implements Serializable {
    private int id;
    private Button Image;
    private Button PDF;
    private String title;
    private String store;
    private String storeLocation;
    private String datePurchase;
    @JsonAdapter(CalendarJsonAdapter.class)
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

    public static void List(int userId, FatItem.ListResponse response) {
        new Thread(() -> {
            try {
                WebRequest req = new WebRequest(new URL(
                        WebRequest.LOCALHOST + "/api/fatura/" + userId + "/list"));
                String resp = req.performGetRequest();

                // Assume the root of the response is a JsonArray.
                JsonArray arr = new Gson().fromJson(resp, JsonArray.class);
                ArrayList<FatItem> items = new ArrayList<>();
                for (JsonElement elem : arr) {
                    items.add(new Gson().fromJson(elem, FatItem.class));
                }

                // Switch to main thread to update UI components.
                new Handler(Looper.getMainLooper()).post(() -> {
                    response.response(items);
                });

            } catch (Exception e) {
                // Handle exceptions and make sure to switch to main thread if updating UI
                Log.e("FatItem", "Web request failed: " + e.toString());
            }
        }).start();
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

    public interface ListResponse {
        public void response(ArrayList<FatItem> items);
    }
}

