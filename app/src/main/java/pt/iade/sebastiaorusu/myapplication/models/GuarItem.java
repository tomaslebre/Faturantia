package pt.iade.sebastiaorusu.myapplication.models;

import android.widget.EditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class GuarItem implements Serializable {
    private int id;
    private String title;
    private Calendar expDateCalendar;
    private EditText expDateEdit;
    private boolean importantCheck;

    private Calendar remDateCalendar;
    private String notes;

    public GuarItem() {
        this(0, "", Calendar.getInstance(), false, Calendar.getInstance(), "");
    }

    public GuarItem(int id, String title, Calendar exp_date, boolean important, Calendar rem_date, String notes) {
        this.id = id;
        this.title = title;
        this.expDateCalendar = exp_date;
        this.importantCheck = important;
        this.remDateCalendar = rem_date;
        this.notes = notes;
    }


    public static ArrayList<GuarItem> List(){
        ArrayList<GuarItem> items = new ArrayList<GuarItem>();
        items.add(new GuarItem(1, "Item 1", new GregorianCalendar(2022, 4, 24), true, new GregorianCalendar(2022, 3, 24), "Item Notes 1"));
        items.add(new GuarItem(2, "Item 2", new GregorianCalendar(2022, 4, 24), false, new GregorianCalendar(2022, 3, 24), "Item Notes 2"));

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getExp_date() {
        return expDateCalendar;
    }

    public void setExp_date(Calendar exp_date) {
        this.expDateCalendar = exp_date;
    }

    public boolean isImportant() {
        return importantCheck;
    }

    public void setImportant(boolean important) {
        this.importantCheck = important;
    }

    public Calendar getRem_date() {
        return remDateCalendar;
    }

    public void setRem_date(Calendar rem_date) {
        this.remDateCalendar = rem_date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getExpDateCalendar() {
        return expDateCalendar;
    }

    public void setExpDateCalendar(Calendar expDateCalendar) {
        this.expDateCalendar = expDateCalendar;
    }

    public EditText getExpDateEdit() {
        return expDateEdit;
    }

    public void setExpDateEdit(EditText expDateEdit) {
        this.expDateEdit = expDateEdit;
    }

    public boolean isImportantCheck() {
        return importantCheck;
    }

    public void setImportantCheck(boolean importantCheck) {
        this.importantCheck = importantCheck;
    }

    public Calendar getRemDateCalendar() {
        return remDateCalendar;
    }

    public void setRemDateCalendar(Calendar remDateCalendar) {
        this.remDateCalendar = remDateCalendar;
    }
}
