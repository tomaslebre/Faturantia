package pt.iade.sebastiaorusu.myapplication.models;

import android.widget.Button;

import java.util.Date;

public class FatItem {
    private int id;
    private Button Image;
    private Button PDF;
    private String title;
    private String store;
    private String storelocation;
    private String dateofpurchase;

    public FatItem() {
        this(0, "", "", "", "");
    }

    public FatItem(int id, String title, String store, String storelocation, String dateofpurchase) {
        this.id = id;
        this.title = title;
        this.store = store;
        this.storelocation = storelocation;
        this.dateofpurchase = dateofpurchase;

    }
}
